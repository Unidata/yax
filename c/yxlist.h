/* Copyright 2009, UCAR/Unidata and OPeNDAP, Inc.
   See the COPYRIGHT file for more information. */
#ifndef YXLIST_H
#define YXLIST_H 1

/* Define the type of the elements in the list*/

#if defined(_CPLUSPLUS_) || defined(__CPLUSPLUS__)
#define EXTERNC extern "C"
#else
#define EXTERNC extern
#endif

EXTERNC int yxlistnull(void*);

typedef struct YXlist {
  unsigned int alloc;
  unsigned int length;
  void** content;
} YXlist;

EXTERNC YXlist* yxlistnew(void);
EXTERNC int yxlistfree(YXlist*);
EXTERNC int yxlistsetalloc(YXlist*,unsigned int);
EXTERNC int yxlistsetlength(YXlist*,unsigned int);

/* Set the ith element */
EXTERNC int yxlistset(YXlist*,unsigned int,void*);
/* Get value at position i */
EXTERNC void* yxlistget(YXlist*,unsigned int);/* Return the ith element of l */
/* Insert at position i; will push up elements i..|seq|. */
EXTERNC int yxlistinsert(YXlist*,unsigned int,void*);
/* Remove element at position i; will move higher elements down */
EXTERNC void* yxlistremove(YXlist* l, unsigned int i);

/* Tail operations */
EXTERNC int yxlistpush(YXlist*,void*); /* Add at Tail */
EXTERNC void* yxlistpop(YXlist*);
EXTERNC void* yxlisttop(YXlist*);

/* Duplicate and return the content (null terminate) */
EXTERNC void** yxlistdup(YXlist*);

/* Look for value match */
EXTERNC int yxlistcontains(YXlist*, void*);

/* Remove element by value; only removes first encountered */
EXTERNC int yxlistelemremove(YXlist* l, void* elem);

/* remove duplicates */
EXTERNC int yxlistunique(YXlist*);

/* Create a clone of a list */
EXTERNC YXlist* yxlistclone(YXlist*);

/* Following are always "in-lined"*/
#define yxlistclear(l) yxlistsetlength((l),0U)
#define yxlistextend(l,len) yxlistsetalloc((l),(len)+(l->alloc))
#define yxlistcontents(l)  ((l)==NULL?NULL:(l)->content)
#define yxlistlength(l)  ((l)==NULL?0U:(l)->length)

#endif /*YXLIST_H*/
