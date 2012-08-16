/* Copyright 2009, UCAR/Unidata
   See the COPYRIGHT file for more information.
*/

#ifndef YAX_H
#define YAX_H

#define YAX_MAX_ATTRIBUTES 16
#define YAX_MAX_ERROR_STRING 1024
#define YAX_MAX_TEXT 16

/* Define the return error codes;
   use negative values to reserve
   positive for system errors.
*/
typedef enum yax_err {
YAX_OK		= (  0),
YAX_ENOMEM	= ( -1),
YAX_EINVAL	= ( -2),
YAX_EEOF	= ( -3),
YAX_ECLOSURE	= ( -4),
YAX_EGT	= ( -5),
YAX_ELT	= ( -6),
YAX_ENAMECHAR	= ( -7),
YAX_ENATTR	= ( -8),
YAX_EEQUAL	= ( -9),
YAX_EVALUE	= (-10),
YAX_ECOMMENT	= (-11),
YAX_ECDATA	= (-12),
YAX_EPROLOG	= (-13),
YAX_EDOCTYPE	= (-14),
YAX_ENAMENULL	= (-15),
YAX_ETEXT	= (-16),
} yax_err;


/* Define the token types */
typedef enum yax_tokentype { /* assign number for trace function */
YAX_UNDEFINED	=  0,
YAX_EOF		=  1,
YAX_OPEN	=  2, /* <element...> */
YAX_CLOSE	=  3, /* </element> */
YAX_EMPTYCLOSE	=  4, /* <element.../> */
YAX_ATTRIBUTE	=  5,
YAX_TEXT	=  6,
YAX_CDATA	=  7,
YAX_PROLOG	=  8,
YAX_DOCTYPE	=  9,
YAX_COMMENT	= 10,
} yax_tokentype;


/* Common Flag Set */
#define YXFLAG_NONE	   0
#define YXFLAG_ESCAPE	   1 /*convert \n,\r, etc to \\ form */
#define YXFLAG_NOCR	   2 /* elide \r */
#define YXFLAG_ELIDETEXT   4 /* only print the first 12 characters of text */
#define YXFLAG_TRIMTEXT	   8 /*remove leading and trailing whitespace;
                                if result is empty, then ignore */

/* This xml lexer takes two inputs:
1. A state of the lexer
2. A token instance
   to record extra info such as a text
   or name or set of attributes.
*/

/* Define the state associated with a token */
typedef struct yax_token {
    yax_tokentype tokentype;
    /* Depending on token type, only some of these will have values */
    char* namespace;
    char *name; /* element or attribute name; null terminated */
    char *text; /* stext or attribute value; null terminated */
    int charno;
} yax_token;

/* Define an empty token */
extern yax_token yax_emptytoken;

/* effectively opaque */
typedef struct yax_lexer yax_lexer;

/*
 * Input is a null-terminated string of the whole xml text;
*/
extern yax_err yax_create(const char* input, int flags, yax_lexer**);
extern yax_err yax_free(yax_lexer*);

/* Allow for changing the flags after create; return previous flag set */
extern void yax_setflags(yax_lexer*,int flags);
extern int yax_getflags(yax_lexer*);

/* Obtain the next token; user provides the yax_token instance */

extern yax_err yax_nexttoken(yax_lexer*, yax_token*);

/* Unescape entities in a string.
   The translations argument is in envv form
   with position n being the entity name and
   position n+1 being the translation and last
   position being null.
   A null return value implies out of memory.
*/
extern char* yax_unescape(char* s, char** translations);

/* Convert an error code to a human readable string */
extern const char* yax_errstring(yax_err err);

/* Duplicate a token into a heap copy */
extern yax_token* yax_tokendup(yax_token);

/*
  Provide a procedure for generating a string
  representing the contents of an yax_token instance.
  Primarily for use for debugging.
  Caller must free returned string.
   A null return value implies out of memory.
*/
extern char* yax_trace(yax_lexer*, yax_token*);

/* Obtain the string space from the lexer */
extern char* yax_strings(yax_lexer*);

#endif /*YAX_H*/

