/* Copyright 2009, UCAR/Unidata and OPeNDAP, inc.
   See the COPYRIGHT file for more information. */

#ifdef HAVE_CONFIG_H
#include "config.h"
#endif

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "xml_actions.h"

int xmldebug = 0;

/*Forward*/
static YXlist* list(YXlist*,void*);
static XMLnode* newnode(XMLparser*,XMLnodetype type);
static XMLparser* parsernew(void);
static void parserfree(XMLparser* parser);
static char* linearize(const char* s,int);
static void dumpattrlist(XMLnode* node, int flags, FILE* file);
static void dumpchildren(XMLnode* node, int flags, int depth, FILE* file);
static void dumpR(XMLnode* node, int flags, int depth, FILE* file);
static void thread(XMLnode* parent);

/**************************************************/

XMLnode*
xmldocument(XMLparser* parser, XMLnode* prolog, XMLnode* doctype, YXlist* elist)
{
    XMLnode* document = NULL;
    /* Insert the prolog and doctype into the front of the element list */
    if(doctype != NULL)
        yxlistinsert(elist,0,doctype);
    if(prolog != NULL)
        yxlistinsert(elist,0,prolog);
    /* Create the top level document node */
    document = newnode(parser,XML_DOCUMENT);
    document->children = elist;
    thread(document);
    /* Stash in the parser */
    parser->document = document;
    return NULL;   
}

XMLnode*
xmlprolog(XMLparser* parser, yax_token token)
{
    XMLnode* node = NULL;
    XMLnode* version = NULL;

    if((node = newnode(parser,XML_PROLOG)) == NULL) goto done;
    if((version = newnode(parser,XML_ATTRIBUTE)) == NULL) goto unwind;
    node->name = "?xml";
    version->name = token.name;
    version->value = token.text;
    node->attributes = yxlistnew();
    yxlistpush(node->attributes,(void*)version);
    thread(node);
done:
    return node;
unwind:
    xml_nodefree(node);
    goto done;
}

XMLnode*
xmldoctype(XMLparser* parser, yax_token token)
{
    XMLnode* node = NULL;
    if((node = newnode(parser,XML_DOCTYPE)) == NULL) goto done;
    node->name = token.name;
    node->text = token.text;
done:
    return node;
}

XMLnode*
xmlelement(XMLparser* parser, yax_token open, yax_token close,
           YXlist* attributes, YXlist* children)
{
    XMLnode* node = NULL;
    /* Check validity */
    if(strcmp(open.name,close.name) != 0) {
	xmlerror(parser,"Open-Close name mismatch");
	goto done;
    }	
    if((node = newnode(parser,XML_ELEMENT)) == NULL) goto done;
    node->name = open.name;
    node->attributes = attributes;
    node->children = children;
    thread(node);
done:
    return node;
}

XMLnode*
xmlelementtext(XMLparser* parser, yax_token text)
{
    XMLnode* node = newnode(parser,XML_TEXT);
    if(node != NULL) {
	node->name = NULL;
	node->text = text.text;
    }
    return node;
}

XMLnode*
xmlattribute(XMLparser* parser, yax_token attr)
{
    XMLnode* node = newnode(parser,XML_ATTRIBUTE);
    if(node != NULL) {
	node->name = attr.name;
	node->value = attr.text;
    }
    return node;
}

YXlist*
xmlelementlist(XMLparser* parser, YXlist* l, XMLnode* elem)
{
    l = list(l,(void*)elem);
    if(l == NULL)
        xmlerror(parser,"Out of memory");
    return l;
}

YXlist*
xmlattributelist(XMLparser* parser, YXlist* l, XMLnode* attr)
{
    l = list(l,(void*)attr);
    if(l == NULL)
        xmlerror(parser,"Out of memory");
    return l;
}

/**************************************************/
/* Support procedures */

static YXlist*
list(YXlist* list, void* elem)
{
    if(list == NULL) {
	if((list = yxlistnew()) == NULL) goto done;
    }
    if(elem != NULL)
        yxlistpush(list,elem);
done:
    return  list;
}

static XMLnode*
newnode(XMLparser* parser, XMLnodetype type)
{
    XMLnode* node = (XMLnode*)calloc(1,sizeof(XMLnode));
    if(node != NULL)
	node->type = type;
    else
        xmlerror(parser,"Out of memory");
    return node;
}

static void
thread(XMLnode* parent)
{
    int i;
    for(i=0;i<yxlistlength(parent->attributes);i++) {
	XMLnode* node = (XMLnode*)yxlistget(parent->attributes,i);
	node->parent = parent;
    }
    for(i=0;i<yxlistlength(parent->children);i++) {
	XMLnode* node = (XMLnode*)yxlistget(parent->children,i);
	node->parent = parent;
    }
}

/**************************************************/
/* Parser state management */

static XMLparser*
parsernew(void)
{
    XMLparser* parser = (XMLparser*)calloc(1,sizeof(XMLparser));
    return parser;
}

static void
parserfree(XMLparser* parser)
{
    if(parser == NULL) return;
    if(parser->document != NULL)
	xml_nodefree(parser->document);
    yax_free(parser->yax);
    free(parser);
}

/**************************************************/
/* Dump */

static char*
linearize(const char* s, int flags)
{
    int len;
    char* linear;
    int elided = 0;

    if(s == NULL) s = "";
    len = strlen(s);
    linear = (char*)malloc(1+2*len);
    if((flags & YXFLAG_TRIMTEXT) != 0) {
	const char* p = s;
	/* trim leading whitespace */
	while(iswhitespace(*p)) {p++; len--;}
	s = p;
	/* trim trailing whitespace */
	p = s+(len-1);
	while(iswhitespace(*p)) {p--; len--;}
    }
    /* If elide is set then shorten the input */
    if((flags & YXFLAG_ELIDETEXT) != 0
        && (len > YAX_MAX_TEXT + strlen("..."))) {
	int elidepoint = YAX_MAX_TEXT - 1;
	while(s[elidepoint] < ' ') elidepoint--;
	len = elidepoint+1;
	elided = 1;
    }
    if(linear != NULL) {
	int c,i;
	char* q = linear;
	for(i=0;i<len;i++) {
	    c = s[i];
	    if(c == '\r') continue;
	    if(c == '\n') {*q++ = '\\'; c = 'n';}
	    *q++ = (char)c;
	}
	*q = '\0';
	if(elided) strcat(q,"...");
    }
    return linear;
}

static void
dumpattrlist(XMLnode* node, int flags, FILE* file)
{
    int i;
    for(i=0;i<yxlistlength(node->attributes);i++) {
        XMLnode* attr = (XMLnode*)yxlistget(node->attributes,i);
	char* tmp = linearize(attr->value,flags);
	fprintf(file," %s=\"%s\"",attr->name,tmp);
	free(tmp);
    }
}

static void
dumpchildren(XMLnode* node, int flags, int depth, FILE* file)
{
    int i;
    for(i=0;i<yxlistlength(node->children);i++) {
        XMLnode* child = (XMLnode*)yxlistget(node->children,i);
        dumpR(child,flags,depth,file);
    }
}

static void
dumpR(XMLnode* node, int flags, int depth, FILE* file)
{
    char* tmp = NULL;

    if(node == NULL) return;
    switch (node->type) {

    case XML_PROLOG:
	fprintf(file,"[%02d] <?xml",depth);
	dumpattrlist(node,flags,file);
	fprintf(file,">\n");
	break;

    case XML_DOCTYPE:
	tmp = linearize(node->text,YXFLAG_NONE);
	fprintf(file,"[%02d] <!DOCTYPE %s %s>\n",depth,node->name,tmp);
	break;

    case XML_CDATA:
	tmp = linearize(node->text,flags);
	fprintf(file,"[%02d] <![CDATA[%s]>\n",depth,tmp);
	break;

    case XML_TEXT:
	tmp = linearize(node->text,flags);
	fprintf(file,"[%02d] text=|%s|\n",depth,tmp);
	break;

    case XML_ELEMENT:
	fprintf(file,"[%02d] <%s",depth,node->name);
	dumpattrlist(node,flags,file);
	fprintf(file,"%s>\n",(yxlistlength(node->children)>0?"":"/"));
	dumpchildren(node,flags,depth+1,file);
	break;

    default:
	tmp = linearize(node->text,flags);
	fprintf(file,"[%02d] unknown: name=%s value=%s text=%s\n",
		depth,node->name,node->value,tmp);	
	break;
    }
    if(tmp != NULL) free(tmp);
}

/* For now, use the YAX flags for xml */
static int
convertflags(int xmlflags)
{
    return xmlflags;
}

/**************************************************/
/* Public API */

int
xml_parse(const char* input, int flags, XMLnode** documentp)
{
    int ok = 0;
    yax_err yaxerr;
    XMLparser* parser;
    int yaxflags;

    parser = parsernew();
    if(parser == NULL) goto done;
    parser->flags = flags;
    yaxflags = convertflags(flags);
    yaxerr = yax_create(input,yaxflags,&parser->yax);
    if(yaxerr != YAX_OK) {
	fprintf(stderr,"Could not create yax lexer: %s\n",
		yax_errstring(yaxerr));
	goto done;
    }
    if(xmlparse(parser) == 0) {
	if(parser->document != NULL) {
	    parser->document->strings = yax_strings(parser->yax);
	    if(documentp) 
	        *documentp = parser->document;
	    parser->document = NULL;
	}
	ok = 1;	
    } else {
	ok = 0;
    }
done:
    parserfree(parser);
    return ok;
}

void
xml_nodefree(XMLnode* node)
{
    if(node == NULL) return;
    xml_nodelistfree(node->attributes);
    xml_nodelistfree(node->children);
    if(node->strings != NULL)
	free(node->strings);
    free(node);
}

void
xml_nodelistfree(YXlist* list)
{
    int i;
    for(i=0;i<yxlistlength(list);i++)
	xml_nodefree((XMLnode*)yxlistget(list,i));
    yxlistfree(list);
}

void
xml_dump(XMLnode* node, int flags, FILE* file)
{
    dumpR(node,flags,0,file);
    fflush(file);
}

void
xml_dumplist(YXlist* list, int flags, FILE* file)
{
    int i;
    for(i=0;i<yxlistlength(list);i++)
	xml_dump((XMLnode*)yxlistget(list,i),flags,file);
}

