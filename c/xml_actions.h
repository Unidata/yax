#ifndef XML_ACTIONS_H
#define XML_ACTIONS_H

#include "yxlist.h"
#include "yax.h"
#include "xml.h"

#include "xml.tab.h"

/* Whitespace characters */
#define iswhitespace(c) ((c) > '\0' && (c) <= ' ')

#define CHECK(x) {if((x)==NULL) YYABORT;}

typedef struct XMLparser {
   XMLnode* document;
   int flags;
   yax_lexer* yax;
   struct {
	int charno;
	int inopen;
   } lexer; /* extra lexer specific state */
} XMLparser;

/* from xml.y */
extern int xmlparse(XMLparser*);

/* from xml_actions.c */
extern XMLnode* xmldocument(XMLparser*, XMLnode*, XMLnode*, YXlist*);
extern XMLnode* xmlprolog(XMLparser*, yax_token);
extern XMLnode* xmldoctype(XMLparser*, yax_token);
extern XMLnode* xmlelement(XMLparser*, yax_token, yax_token, YXlist*, YXlist*);
extern XMLnode* xmlelementtext(XMLparser*, yax_token);
extern XMLnode* xmlattribute(XMLparser*, yax_token);
extern YXlist* xmlelementlist(XMLparser*, YXlist*,  XMLnode*);
extern YXlist* xmlattributelist(XMLparser*, YXlist*,  XMLnode*);

/* from xml_lexer.c */
extern int xmllex(YYSTYPE* lvalp, XMLparser*);
extern int xmlerror(XMLparser*, const char* msg);

#endif /*XML_ACTIONS_H*/
