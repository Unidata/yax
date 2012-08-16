/* Copyright 2009, UCAR/Unidata
   See the COPYRIGHT file for more information.
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include "yax.h"

/*************************************************
Limitations vis-a-vis true xml:
1. no mixed content
2. no % support
3. limited support for xml prolog and DOCTYPE
**************************************************/

#undef XDEBUG

/* Alias for '\0' */
#define EOS '\0'

/* Whitespace characters */
#define whitespace(c) ((c) > '\0' && (c) <= ' ')

/* UTF8 characters; note that this is a way
   over-relaxed test because it basically will
   take any character greater than the 7bit
   ascii.
*/
#define utf8(c) (((c) & 0x80) != 0)

/* Ascii alpha chars */
#define alpha(c) (((c) >= 'a' && (c) <= 'z') || ((c) >= 'A' && (c) <= 'Z'))

/* Ascii numeric chars */
#define digit(c) ((c) >= '0' && (c) <= '9')

/* Characters legal as first char of an element or attribute name */
#define namechar1(c) (strchr(":_?",(c)) != NULL || alpha(c) || utf8(c))

/* Characters legal as greater than first char of an element or attribute name */
#define namecharn(c) (namechar1(c) || digit(c) || strchr("-.",(c)) != NULL)

/* Characters legal as character in a non-quoted attribute value;
   note that the quote chars are handled specially in collectattribute
*/
#define valuechar(c) (!whitespace(c) && strchr("=<>/",(char)(c)) == NULL)

/* Following inXXX assume we are here "<^..." */

#define PROLOGTAG "?xml"
#define CDATATAG  "![CDATA["
#define DOCTYPETAG  "!DOCTYPE"
#define INCOMMENTTAG  "!--"
#define ATCOMMENTTAG  "<!--"
#define ENDCOMMENTTAG  "-->"
#define ENDCDATATAG  "]]>"
#define ENDPROLOGTAG  "?>"
#define ENDDOCTYPETAG  ">"

#define inprolog(lexer)	 match(lexer,PROLOGTAG)
#define incdata(lexer)	 match(lexer,CDATATAG)
#define indoctype(lexer) match(lexer,DOCTYPETAG)
#define incomment(lexer) match(lexer,INCOMMENTTAG)
#define atcomment(lexer) match(lexer,ATCOMMENTTAG)

/**************************************************/

#define SGT ">"
#define SLT "<"
#define CGT '>'
#define CLT '<'
#define CLB '['
#define CRB ']'
#define CQUOTE '"'
#define CSQUOTE '\''

#define ERROR 0

#define MAXTRACESIZE (1<<16)
#define MAXBACKUP (16)

/**************************************************/
yax_token yax_emptytoken = {0,NULL,NULL,0};

/* Define the lexer contexts vis-a-vis the tokenization	 */
typedef enum yax_context { /* assign numbers for debugging */
YX_EOF = 0,	   /* EOF^ */
YX_OPEN = 1,	   /* ^<... */
YX_TEXT = 2,	   /* >^... */
YX_EMPTYCLOSE = 3, /* ^/>... */
YX_ATTRIBUTE = 4,  /* ...^aname=value... */
YX_UNDEFINED = 5,  /* need to search for a state */
} yax_context;

struct yax_lexer {
    const char* input; /* given input */
    char* strings; /*store strings extracted from input */
    const char* ipos; /* next character to read from input*/
    char* spos; /* next character to write into strings*/
    yax_context context;
    char* lastelement;
    char* lastnamespace;
    int flags;
};

/**************************************************/
#ifdef XDEBUG
#define XFAIL(e) {err=xbreakpoint(e);goto done;}
#define XTHROW(e) ((e)!=YAX_OK?xbreakpoint(e):(e))
#define XASSERT(expr) {if(!(expr)) {xbreakpoint(YAX_EINVAL);}}
#define XTRACE(lexer) xtracecontext(lexer)
#else
#define XFAIL(e) {err=(e);goto done;}
#define XTHROW(e) (e)
#define XASSERT(expr) assert(expr)
#define XTRACE(lexer)
#endif

#ifdef XDEBUG
static yax_err xbreakpoint(yax_err err) {return err;}

static char* contextnames[] = {
"EOF",
"OPEN",
"TEXT",
"EMPTYCLOSE",
"ATTRIBUTE",
"UNDEFINED",
};

static void
xtracecontext(yax_lexer* lexer)
{ char pos[16]; int i;
for(i=0;i<15;i++) {
    int c = lexer->ipos[i];
    if(c < ' ') break;
    pos[i] = c;
}
pos[i]='\0';
fprintf(stderr,"Moving to context: %s ; @ |%s...|\n",
	contextnames[(int)lexer->context],pos);
fflush(stderr);
}
#endif
/**************************************************/
/* Input management */

/* Convenience to avoid having to write lexer-> */
/* Assumes that the yax_lexer variable is names "lexer" */
#define IPOS ((lexer)->ipos)
#define SPOS ((lexer)->spos)

#define charoffset(lexer) ((int)((lexer)->ipos - (lexer)->input))

#define backup(lexer) {(lexer)->ipos--;}
#define peek(i,lexer) ((lexer)->ipos[(i)])

#define skipn(i,lexer) {(lexer)->ipos += (i);}

/* return char at state->ipos and incr state->ipos */
static int
nextch(yax_lexer* lexer)
{
    return *(lexer)->ipos++;
}

/* skip whitespace and leave so nextch will pick up the char
   after the whitespace
*/

static void
skipwhitespace(yax_lexer* lexer)
{
    int c;
    for(;;) {
	c = nextch(lexer);
	if(!whitespace(c)) {backup(lexer); break;}
    }
}

/* Strings field Management */

#define terminate(lexer) {*((lexer)->spos++) = EOS;}

/**************************************************/
/* Forward */

static yax_err findcontext(yax_lexer* lexer);
static yax_err collecttext(yax_lexer*, yax_token*);
static yax_err collectattribute(yax_lexer*, yax_token*);
static yax_err collectcdata(yax_lexer*, yax_token*);
static yax_err collectprolog(yax_lexer*, yax_token*);
static yax_err collectdoctype(yax_lexer*, yax_token*);
static yax_err collectcomment(yax_lexer*, yax_token*);
static int match(yax_lexer* lexer, const char* s);
static yax_err collectnamespace(yax_token* token);

/**************************************************/
/*
Strings found in the input are copied into a single buffer
and are null terminated.
*/

/* Obtain the string space from the lexer */
char*
yax_strings(yax_lexer* lexer)
{
    char* strings = lexer->strings;
    lexer->strings = NULL;
    return strings;
}

yax_err
yax_nexttoken(yax_lexer* lexer, yax_token* token)
{
    yax_err err = YAX_OK;
    int closure;
    int c;
    
    /* reset token */
    token->name = NULL;
    token->text = NULL;

    token->charno = charoffset(lexer); /* default */

    if(lexer->context == YX_UNDEFINED) {
	err = findcontext(lexer);
	if(err) {XFAIL(err);}
    }
    XASSERT(lexer->context != YX_UNDEFINED);

    switch (lexer->context) {

    case YX_OPEN: /*case: <^...*/
	/*case: <^...*/
	/* Check for comments, CDATA, prolog and DOCTYPE */
	if(incomment(lexer)) {
	    /*cases:  <^!--*/
	    err = collectcomment(lexer,token);
	    if(err) XFAIL(err);
	    lexer->context = YX_UNDEFINED;
	    break;
	} else if(incdata(lexer)) {
	    /*cases:  <^![CDATA...*/
	    err = collectcdata(lexer,token);
	    if(err) XFAIL(err);
	    /* collect cdata will set the next state */
	    break;
	} else if(inprolog(lexer)) {
	    /*cases:  <^?xml...*/
	    err = collectprolog(lexer,token);
	    if(err) XFAIL(err);
	    break;
	} else if(indoctype(lexer)) {
	    /*cases:  <^!DOCTYPE...*/
	    err = collectdoctype(lexer,token);
	    if(err) XFAIL(err);
	    break;
	}
	/* cases: <^...	  |>*/
	c = nextch(lexer);
	if(c == EOS) {XFAIL(YAX_EEOF);}
	/*cases: <n^ame... , </^name... */
	closure = (c == '/' ? 1 : 0);
	if(closure) {
	    token->charno = charoffset(lexer);
	    c = nextch(lexer);
	}
	/*cases: <n^ame... , </n^ame... */
	if(!namechar1(c)) {
	    XFAIL(YAX_ENAMECHAR);
	}		
	/* capture the name in lexer->strings */
	token->name = SPOS;
	do {
	    *SPOS++ = (char)c;
	    c = nextch(lexer);
	    if(c==EOS) XFAIL(YAX_EEOF);
	} while(namecharn(c));
	terminate(lexer); /* null terminate the name in lexer->strings */
	/*cases: <name?^... , </name?^...; ? is any char */	
	if(whitespace(c)) {
	    skipwhitespace(lexer);
	    c = nextch(lexer);
	}
	/*cases: ...a^ttr=value, .../^>, ...>^ */
	if(c == '/' && peek(0,lexer) == CGT) {
	    if(closure) {XFAIL(YAX_EGT);}
	    c = nextch(lexer);
	    /*case: />^ */
	    lexer->context = YX_EMPTYCLOSE;
	    XTRACE(lexer);
	} else if(c == CGT) {
	    lexer->context = YX_TEXT;
	    XTRACE(lexer);
	} else {/* test for case: ...a^ttr=value */
	    if(namechar1(c)) {
		/*cases: a^ttr=value */
		lexer->context = YX_ATTRIBUTE;		   
		XTRACE(lexer);
		backup(lexer);
		/*cases: ^attr=value */
	    } else {
		XFAIL(YAX_ECLOSURE);
	    }
	}
	token->tokentype = (closure ? YAX_CLOSE : YAX_OPEN);
	err = collectnamespace(token);
	if(err != YAX_OK) XFAIL(err);
	if(closure) {
	    lexer->lastelement = NULL;
	    lexer->lastnamespace = NULL;
	} else {
	    lexer->lastelement = token->name;
	    lexer->lastnamespace = token->namespace;
	}
	break;

    case YX_EMPTYCLOSE: /*case: />^  */
	token->charno = charoffset(lexer) - 2;
	token->tokentype = YAX_EMPTYCLOSE;
	/* Reuse the last element name */
	token->name = lexer->lastelement;
	token->namespace = lexer->lastnamespace;
	lexer->lastelement = NULL;
	lexer->lastnamespace = NULL;
	lexer->context = YX_TEXT;
	XTRACE(lexer);
	break;	

    case YX_TEXT: /*case: >^ */
	token->tokentype = YAX_TEXT;
	err = collecttext(lexer,token);
	if(err) XFAIL(err);
	/* collect text will set the next state */
	break;

    case YX_ATTRIBUTE: /*case: <element...^attr=value...*/
	err = collectattribute(lexer,token);
	/* collect attribute will have properly set the state */
	if(err) XFAIL(err);
	token->tokentype = YAX_ATTRIBUTE;
	err = collectnamespace(token);
	if(err != YAX_OK) XFAIL(err);
	break;	

    case YX_EOF:
	token->tokentype = YAX_EOF;
	lexer->context = YX_EOF;
	XTRACE(lexer);
	break;

    default: XASSERT(0); break;
    }

done:
    return XTHROW(err);
}

/* Define an alternative to strncmp */
static int
match(yax_lexer* lexer, const char* s)
{
    const char* p;
    const char* q;
    if(s == NULL)
	return 0;
    p = lexer->ipos;	   
    q = s;
    while(*q && (*p == *q)) {p++; q++;}
    if(*q == EOS)
	return 1;
    /* else *p != *q */
    return 0;	 
}

/* set the lexer context properly */
static yax_err
findcontext(yax_lexer* lexer)
{
    yax_err err = YAX_OK;
    int c;
    assert(lexer->context == YX_UNDEFINED);
    c = nextch(lexer);
    if(c == CLT) lexer->context = YX_OPEN;
    else if(c == EOS) lexer->context = YX_EOF;
    else lexer->context = YX_TEXT;
    XTRACE(lexer);

    return err;
}

/* Assume we are here: <^!-- */
static yax_err
collectcomment(yax_lexer* lexer, yax_token* token)
{
    yax_err err = YAX_OK;
    int c;

    /*case: <^!-- */
    skipn(3,lexer);
    /*case: <!--^ */
    token->text = SPOS;
    /* scan for end of comment */
    for(;;) {
	if(match(lexer,ENDCOMMENTTAG)) {
	    /*case: <!--...^--> */
	    break;
	}
	c = nextch(lexer);
	/*case: <!--...X^, <!--...EOF */
	if(c == EOS) XFAIL(YAX_ECOMMENT);
	*SPOS++ = (char)c;
    }
    terminate(lexer);
    /*case: <!--...^--> */
    skipn(3,lexer);	 
    /*case: <!--...-->^_ */
    token->tokentype = YAX_COMMENT;
    lexer->context = YX_UNDEFINED;    
    XTRACE(lexer);
done:
    return XTHROW(err);
}

static yax_err
collectcdata(yax_lexer* lexer, yax_token* token)
{
    yax_err err = YAX_OK;
    int c;

    /*case: <^![CDATA[ */
    skipn(8,lexer);
    /*case: <![CDATA[^ */
    token->text = SPOS;
    /* scan for end of cdata */
    for(;;) {
	if(match(lexer,ENDCDATATAG))
	    break;
	c = nextch(lexer);
	/*case: <![CDATA[...X^, <![CDATA[...EOF */
	if(c == EOS) XFAIL(YAX_ECDATA);
	*SPOS++ = (char)c;
    }
    /*case: <![CDATA[...^]]> */
    terminate(lexer);
    skipn(3,lexer);	 
    /*case: <![CDATA[...]]>^ */
    token->tokentype = YAX_CDATA;
    /* Set the state to undefined */
    lexer->context = YX_UNDEFINED;
    XTRACE(lexer);

done:
    return XTHROW(err);
}

static yax_err
collectprolog(yax_lexer* lexer, yax_token* token)
{
    yax_err err = YAX_OK;
    int c;

    /*case: <^?xml */
    /* force the version to be 1.0 */
    token->name = "version";
    token->text = "1.0";

    /*case: <?xml^ */
    skipn(4,lexer);
    /* scan for trailing '?>' */
    for(;;) {
	if(match(lexer,ENDPROLOGTAG))
	    break;
	c = nextch(lexer);
	if(c == EOS) XFAIL(YAX_EPROLOG);
    }
    /*case: <?xml...^?> */
    skipn(2,lexer);
    /*case: <?xml...?>^ */
    token->tokentype = YAX_PROLOG;
    /* Set the state to undefined */
    lexer->context = YX_UNDEFINED;
    XTRACE(lexer);

done:
    return XTHROW(err);
}

static yax_err
collectdoctype(yax_lexer* lexer, yax_token* token)
{
    yax_err err = YAX_OK;
    int c;

    /*case: <^!DOCTYPE */
    skipn(8,lexer);
    /*case: <!DOCTYPE^ */
    /* Scan for starting name */
    skipwhitespace(lexer);
    c = nextch(lexer);	  
    if(!namechar1(c)) XFAIL(YAX_EDOCTYPE);
    token->name = SPOS;
    for(;;) {
	*SPOS++ = (char)c;
	c = nextch(lexer);
	if(c == EOS) XFAIL(YAX_EDOCTYPE);
	if(!namecharn(c)) break;
    }
    terminate(lexer);
    /* check for empty start name */
    if(strlen(token->name)==0)
	XFAIL(YAX_EDOCTYPE);

    /*case: ...name?^... */

    if(whitespace(c)) {
	skipwhitespace(lexer);
	c = nextch(lexer);
    }

    /*<|expected cases: ...[^..., ...>^, ...N^NNN... |]*/
    if(c == CLB) {
	/*[| collect everything upto the final ']'
	   and make it be the text field of the token */
	/*<|case: ...[^...>   |]*/
	token->text = SPOS;
	/*[| scan for ']' */
	for(;;) {
	    c = nextch(lexer);
	    if(c == EOS) XFAIL(YAX_EDOCTYPE);
	    if(c == CRB) break;
	    *SPOS++ = (char)c;
	}
	terminate(lexer);
	/*[<|case: ...]^> */
	skipwhitespace(lexer);
	/*[<|case: ...]_^> */
	c = nextch(lexer);
	/*[<|case: ...]_>^ */
	if(c != CGT) XFAIL(YAX_EDOCTYPE);
    } else if(c == CGT) {
	/*<|case: ...>^*/
	token->text = "";
    } else if(namechar1(c)) {
	/*case: ...N^NN...*/
	backup(lexer);
	token->text = SPOS;
	/*case: ...^NNN...*/
	/* ID must be either "SYSTEM" or "PUBLIC" */
	if(!match(lexer,"SYSTEM") && !match(lexer,"PUBLIC")) {
	    XFAIL(YAX_EDOCTYPE);
	}
	/* collect everything upto the final CGT
	   and make it be the text field of the token */
	for(;;) {
	    c = nextch(lexer);
	    if(c == EOS) XFAIL(YAX_EDOCTYPE);
	    if(c == CGT) break;
	    *SPOS++ = (char)c;
	}
	assert(c == CGT);
	/*<case: ...ID....>^ */
    } else {
	XFAIL(YAX_EDOCTYPE);
    }
    token->tokentype = YAX_DOCTYPE;
    /* Set the state to undefined */
    lexer->context = YX_UNDEFINED;
    XTRACE(lexer);

done:
    return XTHROW(err);
}

static yax_err
collecttext(yax_lexer* lexer, yax_token* token)
{
    yax_err err = YAX_OK;
    int nonwhite; /*1 => not all white space */
    int c;
    int flags = lexer->flags;

    /*case: >^... <*/
    token->text = SPOS;
    nonwhite = 0;
    for(;;) {
	c = nextch(lexer);
	if(c == EOS)
	    break;
	if(c == CLT)
	    break;
	if(!whitespace(c))
	    nonwhite = 1;
	/* capture character */
	*SPOS++ = (char)c;
    }/*for*/
    terminate(lexer);
    assert(c == CLT || c == EOS);
    if(flags & YXFLAG_TRIMTEXT) {
	if(nonwhite) {
	    char* pos;
	    /* remove prefixed whitespace */
	    while(whitespace(*token->text)) {
		++token->text;
	    }
	    /* remove suffixed whitespace */
	    pos = token->text + strlen(token->text);
	    do {pos--;} while(whitespace(*pos));
	    /* now move forward 1 char */
	    pos++;
	    /* re-terminate */
	    *pos = EOS;
	} else {
	    /* a zero-length trimmed text is treated as empty */
	    token->text = "";
	}
    }
    /* backup to just before the &lt; */
    backup(lexer);
    lexer->context = YX_UNDEFINED;
    XTRACE(lexer);
    return err;
}

/* Collect and return an attribute; set state for next pass */
static yax_err
collectattribute(yax_lexer* lexer, yax_token* token)
{
    yax_err err = YAX_OK;
    int c,quote;

    /*cases: ...^_attrname... */
    skipwhitespace(lexer);
    /*cases: ...^attrname... */
    XASSERT(namechar1(peek(0,lexer)));
    /* capture the attribute name */
    token->name = SPOS;
    token->text = NULL;
    for(;;) {
	c=nextch(lexer); /* get current name char */
	if(c == EOS) break;
	/*cases: ...NNN^NNN... ...NNN?^...; N is a namechar ? is any char*/
	if(!namecharn(c)) {
	    /*cases: ...NNN?^... */
	    break;
	}
	*SPOS++ = (char)c; /* capture */
    }
    terminate(lexer);
    /*cases: ...NNN?^..., EOF */
    if(whitespace(c)) {
	skipwhitespace(lexer);
	c = nextch(lexer);
    }
    if(c == EOS) {XFAIL(YAX_EEQUAL);}
    if(c != '=')  {XFAIL(YAX_EEQUAL);}
    token->text = SPOS;
    /*cases: ...NNN_=^_ ... ; _ is whitespace */
    skipwhitespace(lexer);
    /* Collect attribute value */
    /*cases: name_=_^VVV,name_=_^QVVVQ, name_=_^/>, name_=_^>;
      V is value char,Q is quote */
    c = nextch(lexer);
    quote = 0;
    if(c == CQUOTE || c == CSQUOTE) {
	quote = c;
    } else if(valuechar(c)) {
	backup(lexer);
    } else {
	XFAIL(YAX_EVALUE);
    }
    /*cases: ...name_=_^VVV ..., ...name_=_Q^VVVQ...*/
    for(;;) {
	/*cases: ...^VVV, ...^VVVQ... */
	c=nextch(lexer);
	if(c == EOS) break;
	/*cases: ...V^VV, ...V^VVQ..., ...VVVQ^...., EOF */
	if(quote && c == quote)
	    break;
	/*cases: ...V^VV, ...VVV?^; ? is not a value char */
	if(!quote && !valuechar(c))
	    break;
	/*cases: ...V^VV, ...VVV^? */
	*SPOS++ = (char)c; /* capture */
    }
    terminate(lexer);
    /*cases: ...QvalueQ^...value?^; where ? is not value char or quote */
    /* move to the next possible attribute */
    if(quote && c == quote) {
	c = nextch(lexer);
    }
    /*cases: ...QvalueQ?^...value?^; where ? is not value char or quote */
    if(whitespace(c)) {
	skipwhitespace(lexer);
	c = nextch(lexer);
    }
    if(c == '/' && peek(0,lexer) == CGT ) {
	/*cases: ..VVV/^> */
	nextch(lexer);
	/*cases: ..VVV/>^ */
	lexer->context = YX_EMPTYCLOSE;
    } else if(c == CGT ) {
	/*cases: ..VVV>^ */
	lexer->context = YX_TEXT;
    } else if(namechar1(c)) {
	backup(lexer);
	/*case: ...^aname... */
	lexer->context = YX_ATTRIBUTE;
    } else {XFAIL(YAX_ECLOSURE);}
    XTRACE(lexer);

done:
    return err;
}

static yax_err
collectnamespace(yax_token* token)
{
    if(token->name != NULL) {
	char* p = strchr(token->name,':');
	if(p == NULL)
	    token->namespace = NULL;
	else {
	    token->namespace = token->name;
	    token->name = p+1;
	    *p = EOS;
	    if(*token->namespace == EOS)
		token->namespace = NULL;
	}
	if(*token->name == EOS)
	   return YAX_ENAMENULL;
    }
    return YAX_OK;
}

yax_err
yax_create(const char* input, int flags, yax_lexer** lexerp)
{
    yax_err err = YAX_OK;
    yax_lexer* lexer = (yax_lexer*)calloc(1,sizeof(yax_lexer));
    if(lexer == NULL) return YAX_ENOMEM;
    if(input == NULL || strlen(input) == 0) return YAX_EINVAL;
    lexer->input = input;
    lexer->strings = (char*)malloc(strlen(input));
    lexer->strings[0] = EOS;
    lexer->ipos = lexer->input;
    lexer->spos = lexer->strings;
    lexer->context = YX_UNDEFINED;
    lexer->flags = flags;
    if(!err && lexerp) *lexerp = lexer;
    return err;
}

yax_err
yax_free(yax_lexer* lexer)
{
    if(lexer != NULL) {
	if(lexer->strings) free(lexer->strings);
	free(lexer);
    }
    return YAX_OK;
}

int
yax_getflags(yax_lexer* lexer)
{
    return (lexer?lexer->flags:0);
}

void
yax_setflags(yax_lexer* lexer, int flags)
{
    if(lexer != NULL) {
	lexer->flags = flags;
    }
}

#ifdef ENTITYPARSE
static int
skipentity(yax_lexer* lexer)
{
    char* save;
    int count,c;
    save = mark1(lexer);
    /* skip the entity name, but leave in the collected text */
    c=nextch(lexer); /* skip the ampersand */
    count = 1;
    for(;;) {
	c=nextch(lexer);
	if(c == EOS) break;
	if(c != ';' && !namecharn(c)) break;
	count++;
	if(c == ';') {
	    return count;
	}	    
    }
    /* wasn't an entity after all */
    setmark1(lexer,save); 
    return 1; /* always skip the ampersand */
}
#endif

/* Unescape entities in a string.
   The translations argument is in envv form
   with position n being the entity name and
   position n+1 being the translation and last
   position being null.
*/
char*
yax_unescape(char* s, char** translations)
{
    int count,found,len;
    char* u; /* returned string with entities unescaped */
    char* p; /* insertion point into u */
    char* q; /* next char from s */
    char* stop;
    char entity[16];

    if(s == NULL) len = 0;
    else len = strlen(s);
    u = (char*)malloc(len+1);
    if(u == NULL) return NULL;
    p = u;
    q = s;
    stop = (s + len);

    while(q < stop) {
	int c = *q++;
	switch (c) {
	case '&': /* see if this is a legitimate entity */
	    /* move forward looking for a semicolon; */
	    for(found=1,count=0;count < sizeof(entity);count++) {
		c = q[count];
		if(c == ';')
		    break;
		if(   (count==0 && !namechar1(c))
		   || (count > 0 && !namecharn(c))) {
		    found=0; /* not a legitimate entity */
		    break;
		}
		entity[count] = c;
	    }
	    if(count == sizeof(entity) || count == 0 || !found) {
		/* was not in correct form for entity */
		*p++ = '&';
	    } else { /* looks legitimate */
		char** pos = translations;
		char* replacement = NULL;
		entity[count] = '\0'; /* null terminate */
		/* check the translation table */
		while(*pos) {
		    if(strcmp(*pos,entity)==0) {
			replacement = pos[1];
			break;
		    }
		    pos += 2;			
		}
		if(replacement == NULL) { /* no translation, ignore */
		    *p++ = '&';
		} else { /* found it */
		    int replen = strlen(replacement);
		    q += (count+1) ; /* skip input entity, including trailing semicolon */
		    memcpy(p,replacement,replen);
		    p += replen;
		}
	    }
	    break;
	default:
	    *p++ = (char)c;
	    break;
	}
    }
    *p = '\0';
    return u;
}

static char* tokennames[] = {
"UNDEFINED",
"EOF",
"OPEN",
"CLOSE",
"EMPTYCLOSE",
"ATTRIBUTE",
"TEXT",
"CDATA",
"PROLOG",
"DOCTYPE",
"COMMENT",
};

static char* tracetranstable[] = {
"amp","&",
"lt","<",
"gt",">",
"quot","\"",
"apos","'",
NULL
};

static const char*
tokenname(yax_tokentype token)
{
    int itoken = (int)token;
    int ntokens = (sizeof(tokennames)/sizeof(char*));
    if(itoken >= 0 && itoken < ntokens) {
	return tokennames[itoken];
    }
    return "UNKNOWN";
}

static void
addtext(char* dst, const char* txt, int flags)
{
    int c;
    int len;
    char* pos;
    int shortened = 0;

    if(txt == NULL) {
	strcat(dst,"null");
	return;
    }
    len = strlen(txt);
    if(flags & YXFLAG_ELIDETEXT && len > YAX_MAX_TEXT) {
	len = YAX_MAX_TEXT;
	shortened = 1;
    }
    pos = dst + strlen(dst);
    *pos++ = '|';
    while((c=*txt++)) {
	if(len-- <= 0) continue;
	if((flags & YXFLAG_ESCAPE) && c < ' ') {
	    *pos++ = '\\';
	    switch (c) {
	    case '\n': *pos++ = 'n'; break;
	    case '\r': *pos++ = 'r'; break;
	    case '\f': *pos++ = 'f'; break;
	    case '\t': *pos++ = 't'; break;
	    default: {/* convert to octal */
		unsigned int uc = (unsigned int)c;
		unsigned int oct;
		oct = ((uc >> 6) & 077);
		*pos++ = (char)('0'+ oct);
		oct = ((uc >> 3) & 077);
		*pos++ = (char)('0'+ oct);
		oct = ((uc) & 077);
		*pos++ = (char)('0'+ oct);
		} break;
	    }
	} else if((flags & YXFLAG_NOCR) && c == '\r') {
	    continue;
	} else {
	    *pos++ = (char)c;	    
	}
    }
    if(shortened) {
	*pos++ = '.'; *pos++ = '.'; *pos++ = '.';
    }
    *pos++ = '|';
    *pos = '\0';
}

/*
  Provide a procedure for generating a string
  representing the contents of an yax_token instance.
  Primarily for use for debugging.
  Caller must free returned string.

*/

char*
yax_trace(yax_lexer* lexer, yax_token* token)
{
    char tmp[1<<12];
    char* result;
    const char* tname;
    char* trans;

    result = (char*)malloc(MAXTRACESIZE+1);
    if(result == NULL) return NULL;
    result[0] = '\0';

    snprintf(tmp,sizeof(tmp),"%d: ",token->charno);
    strcat(result,tmp);
    tname = tokenname(token->tokentype);
    strcat(result,tname);
    switch(token->tokentype) {
    case YAX_OPEN:
    case YAX_CLOSE:
    case YAX_EMPTYCLOSE:
	strcat(result,": element=|");
	strcat(result,token->name);
	strcat(result,"|");
	break;
    case YAX_TEXT:
	trans = NULL;
	strcat(result," text=");
	addtext(result,token->text,lexer->flags);
	trans = yax_unescape(token->text,tracetranstable);
	strcat(result," translation=");
	addtext(result,trans,lexer->flags);
	if(trans) free(trans);
	break;
    case YAX_ATTRIBUTE:
	strcat(result,": name=");
	addtext(result,token->name,lexer->flags);
	strcat(result," value=");
	addtext(result,token->text,lexer->flags);
	break;
    case YAX_COMMENT:
    case YAX_CDATA:
	strcat(result,": text=");
	addtext(result,token->text,lexer->flags);
	break;
    case YAX_DOCTYPE:
    case YAX_PROLOG:
	strcat(result,": name=");
	addtext(result,token->name,lexer->flags);
	strcat(result," value=");
	addtext(result,token->text,lexer->flags);
	break;
    case YAX_EOF:
	break;
    default:
	XASSERT(0);
    }
    return result;
}

/* Convert an error code to a human readable string */
const char*
yax_errstring(yax_err err)
{
    const char* msg = NULL;
    if(err == 0)
	msg = "No error";
    else if(err > 0) {
      msg = (const char *) strerror((int)err);
      if(msg == NULL) msg = "Unknown Error";
    } else {/*err < 0 */
	switch(err) {
	case YAX_ENOMEM: msg = "Out of Memory"; break;
	case YAX_EINVAL: msg = "Invalid argument"; break;
	case YAX_EEOF: msg = "Unexpected EOF"; break;
	case YAX_ECLOSURE: msg = "Expected /,>,or attribute name"; break;
	case YAX_EGT: msg = "Expected >"; break;
	case YAX_ELT: msg = "Expected <"; break;
	case YAX_ENAMECHAR: msg = "Illegal leading name character"; break;
	case YAX_ENATTR: msg = "Too many attributes"; break;
	case YAX_EEQUAL: msg = "Expected '=' after attribute name"; break;
	case YAX_EVALUE: msg = "Expected attribute value"; break;
	case YAX_ECOMMENT: msg = "Comment is missing trailing '-->'"; break;
	case YAX_ECDATA: msg = "Cdata is missing trailing ']]>'"; break;
	case YAX_EPROLOG: msg = "Malformed <?xml...> prolog"; break;
	case YAX_EDOCTYPE: msg = "Malformed <!DOCTYPE...>"; break;
	case YAX_ENAMENULL: msg = "Name is zero length"; break;
	case YAX_ETEXT: msg = "Non-whitespace text encountered"; break;

	default: msg = "Undefined error code"; break;
	}
    }
    return msg;
}

yax_token*
yax_tokendup(yax_token token)
{
    yax_token* t = (yax_token*)malloc(sizeof(yax_token));
    if(t != NULL)
	*t = token;
    return t;
}

