#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#ifdef HAVE_GETOPT_H
#include <getopt.h>
#endif

#define MAXNONOPTS 4096

/* Capture getopt info in static variables */
static char optionkind[256];
static char option_set[256];
static long optionint[256];
static char* optionstr[256];
static char* nonoptions[MAXNONOPTS];

int
options(const char* optlist0, int argc, char** argv)
{
    int i,c;
    const char* p;
    char* q;
    char optlist[256*2];

    memset((void*)optionkind,0,sizeof(optionkind));
    memset((void*)option_set,0,sizeof(option_set));
    memset((void*)optionint,0,sizeof(optionint));
    memset((void*)optionstr,0,sizeof(optionstr));
    memset((void*)nonoptions,0,sizeof(nonoptions));

    q = optlist;
    p = optlist0;
    while((c=*p++)) {
	if((c >= 'a' && c <= 'z')
	    || (c >= 'A' && c <= 'Z')) {
	    *q++ = c;
	    /* see if followed by : or # */
	    if(*p == ':' || *p == '#') {
		optionkind[c] = *p++;
		*q++ = ':';
	    } else
		optionkind[c] = 1;
	} else
	    goto fail;
    }
    *q = '\0';

    opterr = 1; /* Let getopt report */
    while ((c = getopt(argc, argv, optlist)) != EOF) {
	switch(c) {
	case '?':
	    fprintf(stderr,"unknown option\n");
	    goto fail;
	default:
	    if(!optionkind[c]) {
	        fprintf(stderr,"unknown option\n");
		goto fail;
	    }
	    option_set[c] = 1;
	    if(optionkind[c] == ':') {
		optionstr[c] = optarg;
	    } else if(optionkind[c] == '#') {
		optionint[c] = atoi(optarg);
	    }
      }
    }
    argc -= optind;
    argv += optind;
    if(argc >= MAXNONOPTS) {
	fprintf(stderr,"Too many command line arguments\n");
	goto fail;
    }
    for(i=0;i<argc;i++) {
	nonoptions[i] = argv[i];
    }    
    nonoptions[i] = NULL; /* signal end of the non-option arguments */

    return 1;

fail:
    return 0;
}

/* API */
int
optionset(int c)
{
    if(c >= 0 && c < 256 && option_set[c])
	return 1;
    return 0;
}

int
ioption(int c)
{
    return ((c >= 0 && c < 256) ? optionint[c] : 0);
}

const char*
soption(int c)
{
    return ((c >= 0 && c < 256) ? optionstr[c] : NULL);
}

const char*
nonoption(int i)
{
    if(i< 0 || i >= MAXNONOPTS) return NULL;
    return nonoptions[i];
}
