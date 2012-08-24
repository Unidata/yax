/* Copyright 2009, UCAR/Unidata and OPeNDAP, inc.
   See the COPYRIGHT file for more information. */

#ifndef DAP4_H
#define DAP4_H

#include <stdio.h>

#include "yxlist.h"
#include "yax.h"

#define null NULL

typedef struct DAP4parser {
    yax_lexer* yaxlexer;    
    int textok;
} DAP4parser;

/* From dap4.y via dap4.tab.c */
extern int dap4parse(DAP4parser*);
extern int dap4lex(int* lvalp, DAP4parser*);
extern int dap4error(DAP4parser*, const char*);

/* From dap4_perfect.c */
extern const struct dap4_keyword* dap4_keyword_lookup(const char* s, unsigned int len);


#endif /*DAP4_H*/
