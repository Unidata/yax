/* Copyright 2009, UCAR/Unidata and OPeNDAP, Inc.
   See the COPYRIGHT file for more information. */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

#include "xml_actions.h"
#include "xml.tab.h"

#undef TRACE

#define UNKNOWN -1

/**************************************************/

int
xmllex(YYSTYPE* lvalp, XMLparser* parser)
{
    yax_err err = YAX_OK;
    yax_token* yaxtoken = &lvalp->token;
    int yytoken = UNKNOWN;

    while(yytoken == UNKNOWN) {
	err = yax_nexttoken(parser->yax,yaxtoken);
	if(err) goto done;

#ifdef TRACE
	{
	char* trace = yax_trace(parser->yax,yaxtoken);
	printf("yylex: charno=%d token: %s\n",yaxtoken->charno,trace);
	fflush(stdout);
	if(trace) free(trace);
	}
#endif

	switch(yaxtoken->tokentype) {

	case YAX_OPEN:
	    yytoken = OPEN;
	    parser->lexer.inopen = 1;
	    break;
	case YAX_EMPTYCLOSE:
	    parser->lexer.inopen = 0;
	    yytoken = EMPTYCLOSE;
	    break;
	case YAX_CLOSE:
	    parser->lexer.inopen = 0;
	    yytoken = CLOSE;
	    break;
	case YAX_ATTRIBUTE:
	    yytoken = ATTRIBUTE;
	    break;
	case YAX_TEXT: {
	    /* We only want to return TEXT
		when we are inside an OPEN.
		If it occurs elsewhere and is not whitespace,
		then fail.
	    */ 
	    if(parser->lexer.inopen) {
		yytoken = TEXT;
	    } else {
		char* p = yaxtoken->text;
		/* check for whitespace */
		for(;*p;p++) {if(!iswhitespace(*p)) goto fail;}
		/* suppress whitespace */
		continue; /* look for another token */
	    }
	} break;

	case YAX_PROLOG:
	    yytoken = PROLOG;
	    break;
	case YAX_DOCTYPE:
	    yytoken = DOCTYPE;
	    break;
	case YAX_COMMENT:
	    continue; /* ignore */

	case YAX_EOF:
	    yytoken = 0;
	    break;

	default: {
	    char msg[1024];
	    snprintf(msg,sizeof(msg),"unknown token type: %d\n",yaxtoken->tokentype);
	    xmlerror(parser,msg);
	    yytoken = ERROR;
	    } break;
	}
    }

done:
    parser->lexer.charno = yaxtoken->charno;
    if(err) {
	xmlerror(parser,yax_errstring(err));
	return ERROR;
    }
    return yytoken;
fail:
    yytoken = ERROR;
    goto done;
}

int
xmlerror(XMLparser* parser, const char* msg)
{
    fprintf(stderr,"%d: %s\n",parser->lexer.charno,msg);
    return 0;
}

