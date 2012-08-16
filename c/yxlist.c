/* Copyright 2009, UCAR/Unidata and OPeNDAP, Inc.
   See the COPYRIGHT file for more information. */
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "yxlist.h"

#define yxelem void*

int yxlistnull(yxelem e) {return e == NULL;}

#ifndef TRUE
#define TRUE 1
#endif
#ifndef FALSE
#define FALSE 0
#endif

#define DEFAULTALLOC 16
#define ALLOCINCR 16

YXlist* yxlistnew(void)
{
  YXlist* l;
/*
  if(!yxinitialized) {
    memset((void*)&yxDATANULL,0,sizeof(yxelem));
    yxinitialized = 1;
  }
*/
  l = (YXlist*)malloc(sizeof(YXlist));
  if(l) {
    l->alloc=0;
    l->length=0;
    l->content=NULL;
  }
  return l;
}

int
yxlistfree(YXlist* l)
{
  if(l) {
    l->alloc = 0;
    if(l->content != NULL) {free(l->content); l->content = NULL;}
    free(l);
  }
  return TRUE;
}

int
yxlistsetalloc(YXlist* l, unsigned int sz)
{
  yxelem* newcontent;
  if(l == NULL) return FALSE;
  if(sz <= 0) {sz = (l->length?2*l->length:DEFAULTALLOC);}
  if(l->alloc >= sz) {return TRUE;}
  newcontent=(yxelem*)calloc(sz,sizeof(yxelem));
  if(l->alloc > 0 && l->length > 0 && l->content != NULL) {
    memcpy((void*)newcontent,(void*)l->content,sizeof(yxelem)*l->length);
  }
  if(l->content != NULL) free(l->content);
  l->content=newcontent;
  l->alloc=sz;
  return TRUE;
}

int
yxlistsetlength(YXlist* l, unsigned int sz)
{
  if(l == NULL) return FALSE;
  if(sz > l->alloc && !yxlistsetalloc(l,sz)) return FALSE;
  l->length = sz;
  return TRUE;
}

yxelem
yxlistget(YXlist* l, unsigned int index)
{
  if(l == NULL || l->length == 0) return NULL;
  if(index >= l->length) return NULL;
  return l->content[index];
}

int
yxlistset(YXlist* l, unsigned int index, yxelem elem)
{
  if(l == NULL) return FALSE;
  if(index >= l->length) return FALSE;
  l->content[index] = elem;
  return TRUE;
}

/* Insert at position i of l; will push up elements i..|seq|. */
int
yxlistinsert(YXlist* l, unsigned int index, yxelem elem)
{
  int i; /* do not make unsigned */
  if(l == NULL) return FALSE;
  if(index > l->length) return FALSE;
  yxlistsetalloc(l,0);
  for(i=(int)l->length;i>index;i--) l->content[i] = l->content[i-1];
  l->content[index] = elem;
  l->length++;
  return TRUE;
}

int
yxlistpush(YXlist* l, yxelem elem)
{
  if(l == NULL) return FALSE;
  if(l->length >= l->alloc) yxlistsetalloc(l,0);
  l->content[l->length] = elem;
  l->length++;
  return TRUE;
}

yxelem
yxlistpop(YXlist* l)
{
  if(l == NULL || l->length == 0) return NULL;
  l->length--;  
  return l->content[l->length];
}

yxelem
yxlisttop(YXlist* l)
{
  if(l == NULL || l->length == 0) return NULL;
  return l->content[l->length - 1];
}

yxelem
yxlistremove(YXlist* l, unsigned int i)
{
  unsigned int len;
  yxelem elem;
  if(l == NULL || (len=l->length) == 0) return NULL;
  if(i >= len) return NULL;
  elem = l->content[i];
  for(i+=1;i<len;i++) l->content[i-1] = l->content[i];
  l->length--;
  return elem;  
}

/* Duplicate and return the content (null terminate) */
yxelem*
yxlistdup(YXlist* l)
{
    yxelem* result = (yxelem*)malloc(sizeof(yxelem)*(l->length+1));
    memcpy((void*)result,(void*)l->content,sizeof(yxelem)*l->length);
    result[l->length] = (yxelem)0;
    return result;
}

int
yxlistcontains(YXlist* list, yxelem elem)
{
    unsigned int i;
    for(i=0;i<yxlistlength(list);i++) {
	if(elem == yxlistget(list,i)) return 1;
    }
    return 0;
}

/* Remove element by value; only removes first encountered */
int
yxlistelemremove(YXlist* l, yxelem elem)
{
  unsigned int len;
  unsigned int i;
  int found = 0;
  if(l == NULL || (len=l->length) == 0) return 0;
  for(i=0;i<yxlistlength(l);i++) {
    yxelem candidate = l->content[i];
    if(elem == candidate) {
      for(i+=1;i<len;i++) l->content[i-1] = l->content[i];
      l->length--;
      found = 1;
      break;
    }
  }
  return found;
}




/* Extends yxlist to include a unique operator 
   which remove duplicate values; NULL values removed
   return value is always 1.
*/

int
yxlistunique(YXlist* list)
{
    unsigned int i,j,k,len;
    yxelem* content;
    if(list == NULL || list->length == 0) return 1;
    len = list->length;
    content = list->content;
    for(i=0;i<len;i++) {
        for(j=i+1;j<len;j++) {
	    if(content[i] == content[j]) {
		/* compress out jth element */
                for(k=j+1;k<len;k++) content[k-1] = content[k];	
		len--;
	    }
	}
    }
    list->length = len;
    return 1;
}

YXlist*
yxlistclone(YXlist* list)
{
    YXlist* clone = yxlistnew();
    *clone = *list;
    clone->content = yxlistdup(list);
    return clone;
}
