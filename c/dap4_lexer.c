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

#ifndef PERFECTHASH

static const struct dap4_keyword* keyword_lookup(const char*, unsigned int);

#if 0
#define NKEYS (1<<16)
#define HASHMASK (0xFFFF)
#else
#define NKEYS (1<<8)
#define HASHMASK (0xFF)
#endif
#define PREFIXLEN 8

static struct dap4_keyword* hashtable[NKEYS];

struct dap4_keyword keywords[] = {
{"Attribute", ATTRIBUTE_, _ATTRIBUTE, 0},
{"Dataset", DATASET_, _DATASET, 0},
{"Dim", DIM_, _DIM, 0},
{"Dimension", DIMENSION_, _DIMENSION, 0},
{"EnumConst", ENUMCONST_, _ENUMCONST, 0},
{"Enumeration", ENUMERATION_, _ENUMERATION, 0},
{"Group", GROUP_, _GROUP, 0},
{"Map", MAP_, _MAP, 0},
{"Namespace", NAMESPACE_, _NAMESPACE, 0},
{"Structure", STRUCTURE_, _STRUCTURE, 0},
{"Value", VALUE_, _VALUE, 0},
{"Char", CHAR_, _CHAR, 0},
{"Byte", BYTE_, _BYTE, 0},
{"Int8", INT8_, _INT8, 0},
{"UInt8", UINT8_, _UINT8, 0},
{"Int16", INT16_, _INT16, 0},
{"UInt16", UINT16_, _UINT16, 0},
{"Int32", INT32_, _INT32, 0},
{"UInt32", UINT32_, _UINT32, 0},
{"Int64", INT64_, _INT64, 0},
{"UInt64", UINT64_, _UINT64, 0},
{"Float32", FLOAT32_, _FLOAT32, 0},
{"Float64", FLOAT64_, _FLOAT64, 0},
{"String", STRING_, _STRING, 0},
{"URL", URL_, _URL, 0},
{"Opaque", OPAQUE_, _OPAQUE, 0},
{"Enum", ENUM_, _ENUM, 0},
{"base", 0, 0, ATTR_BASE},
{"basetype", 0, 0, ATTR_BASETYPE,},
{"dapVersion", 0, 0, ATTR_DAPVERSION},
{"ddxVersion", 0, 0, ATTR_DDXVERSION},
{"enum", 0, 0, ATTR_ENUM},
{"href", 0, 0, ATTR_HREF},
{"name", 0, 0, ATTR_NAME},
{"ns", 0, 0, ATTR_NS},
{"size", 0, 0, ATTR_SIZE},
{"type", 0, 0, ATTR_TYPE},
{"value", 0, 0, ATTR_VALUE},
{"xmlns", 0, 0, ATTR_XMLNS},
{NULL,0,0,0}
};
#endif /*!PERFECTHASH*/

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
#ifdef PERFECTHASH
	    keyword = dap4_keyword_lookup(yaxtoken->name,
					  strlen(yaxtoken->name));
#else
	    keyword = keyword_lookup(yaxtoken->name,
					  strlen(yaxtoken->name));
#endif

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

#ifndef PERFECTHASH

#define HASH(hash,i,n,key) \
for(hash=0,i=0;i<n;i++) {unsigned int c = key[i]; hash = (hash) + (c<<i);}; \
hash = (hash & HASHMASK) /* keep in range 0..HASHMASK-1 */

static int keyword_table_initialized = 0;

static const struct dap4_keyword*
keyword_lookup(const char* key, unsigned int keylen)
{
    /* Compute hash based on the first PREFIXLEN characters */
    unsigned int hash;
    int i;
    struct dap4_keyword* entry;
    int n;

    n = (keylen < PREFIXLEN ? keylen : PREFIXLEN);
    if(n == 0)
	return 0;
    HASH(hash,i,n,key);
    entry = hashtable[hash];
    if(entry != NULL)
	return entry;
    return NULL;
}

void
dap4_keyword_init(void)
{
    int i,j;
    unsigned int hash;
    unsigned int keylen;
    struct dap4_keyword* keys;
    struct dap4_keyword* entry;

    if(keyword_table_initialized) return;
    keyword_table_initialized = 1;
    memset((void*)&hashtable,0,sizeof(hashtable));
    keys = keywords;
    for(i=0;;i++) {
	char* keyname = keys[i].name;
	if(keyname == NULL) break; /*done*/
	keylen = strlen(keyname);
	if(keylen > PREFIXLEN) keylen = PREFIXLEN;
	HASH(hash,j,keylen,keyname);
	entry = hashtable[hash];
	if(entry != NULL) {
	    fprintf(stderr,"dap4_lexer.c: hash collision: %s :: %s\n",
		    entry->name?entry->name:"NULL",keyname?keyname:"NULL");
	    exit(1);
	}
	hashtable[hash] = &keys[i];
    }
}

void
dap4_dumphashtable(void)
{
    int i;
    int empty = 1;
    for(i=0;i<NKEYS;i++) {
	struct dap4_keyword* entry = hashtable[i];
	if(entry != NULL) {
	    fprintf(stderr,"[%d] {\"%s\",%d,%d,%d}\n",
		    i,entry->name,entry->opentag,entry->closetag,entry->attrtag);
	    empty = 0;
	}
    }
    if(empty)
	fprintf(stderr,"table is empty\n");
    fflush(stderr);
}

#endif /*!PERFECTHASH*/
