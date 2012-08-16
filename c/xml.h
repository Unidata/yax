/* Copyright 2009, UCAR/Unidata and OPeNDAP, inc.
   See the COPYRIGHT file for more information. */

#ifndef XML_H
#define XML_H

#include <stdio.h>

#include "yxlist.h"
#include "yax.h"

#define null NULL

typedef enum XMLnodetype {
XML_UNDEFINED=0,
XML_PROLOG=1,
XML_DOCTYPE=2,
XML_ELEMENT=3,
XML_ATTRIBUTE=4,
XML_TEXT=5,
XML_CDATA=6,
XML_DOCUMENT=7,
} XMLnodetype;

extern int xmldebug;

typedef struct XMLnode {
    XMLnodetype type;
    struct XMLnode* parent;
    YXlist* attributes;
    YXlist* children;
    /* Not all of the following will be defined; depends on type field */
    const char* name;
    const char* value;
    const char* text;
    /* Following is only defined for the root */
    char* strings;
} XMLnode;

/**************************************************/
/* Public API */

extern int xml_parse(const char* input, int flags, XMLnode** documentp);
extern void xml_nodefree(XMLnode*);
extern void xml_nodelistfree(YXlist*);
extern void xml_dump(XMLnode*, int, FILE*);
extern void xml_dumplist(YXlist*, int, FILE*);

#endif /*XML_H*/
