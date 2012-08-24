/* Copyright 2009, UCAR/Unidata and OPeNDAP, Inc.
   See the COPYRIGHT file for more information. */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

#include "yxlist.h"
#include "yax.h"
#include "dap4.h"
#include "dap4.tab.h"

#undef TRACE

struct dap4_keyword {
	char *name;
	int opentag;
	int closetag;
	int attrtag;
};

/* Interface to the perfect hash function */
const struct dap4_keyword* dap4_keyword_lookup(const char*,unsigned int);

/*Forward*/

/**************************************************/

int
dap4lex(YYSTYPE *lvalp, DAP4parser* parser)
{
    yax_err err = YAX_OK;
    static yax_token yaxtoken_instance;
    yax_token* yaxtoken = &yaxtoken_instance;
    const struct dap4_keyword* keyword;
    int yytoken = UNKNOWN;

    while(yytoken == UNKNOWN) {
	err = yax_nexttoken(parser->yaxlexer,yaxtoken);
	if(err) goto done;

#ifdef TRACE
	{
	char* trace = yax_trace(parser->yaxlexer,yaxtoken);
	printf("yylex: charno=%d token: %s\n",yaxtoken->charno,trace);
	fflush(stdout);
	if(trace) free(trace);
	}
#endif

	if(yaxtoken->name != NULL)
	    keyword = dap4_keyword_lookup(yaxtoken->name,
					  strlen(yaxtoken->name));

	/* If the keyword is unknown and not an attribute, then fail */
	if(keyword == NULL && yaxtoken->tokentype != YAX_ATTRIBUTE) {
	    char msg[1024];
	    strcpy(msg,"unknown token: |");
	    strncat(msg,yaxtoken->name,16);
	    strcat(msg,"...|\n");
	    dap4error(parser,msg);
	    yytoken = UNEXPECTED;
	    goto done;
	}

	switch(yaxtoken->tokentype) {

	case YAX_OPEN: 
	    /* Since keyword is defined, this means the opentag is legal */
	    yytoken = keyword->opentag;
	    /* Check for the special case of <Value> */
	    if(keyword->opentag == VALUE_)
		parser->textok = 1;
	    break;

	case YAX_EMPTYCLOSE:
	case YAX_CLOSE:
	    /* Since keyword is defined, this means the closetag is legal */
	    yytoken = keyword->closetag;
	    parser->textok = 0;
	    break;

	case YAX_ATTRIBUTE:
	    if(keyword == NULL)
		yytoken = UNKNOWN_ATTR;
	    else
	        /* Since keyword is defined,
                   this means the attribute is legal */
	        yytoken = keyword->attrtag;
	    break;

	case YAX_TEXT:
	    if(parser->textok)
		yytoken = TEXT;
	    break;

	case YAX_COMMENT:
	    break;

	case YAX_EOF:
	    yytoken = 0;
	    break;

	default: {
	    char msg[1024];
	    snprintf(msg,sizeof(msg),"unknown token type: %d\n",yaxtoken->tokentype);
	    dap4error(parser,msg);
	    yytoken = ERROR;
	    } break;
	}
    }

done:
    if(err) {
	dap4error(parser,yax_errstring(err));
	return ERROR;
    }
    return yytoken;
}

int
dap4error(DAP4parser* parser, const char* msg)
{
    fprintf(stderr,"%s\n",msg);
    return 0;
}

