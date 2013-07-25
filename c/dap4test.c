/* Copyright 2009, UCAR/Unidata
   See the COPYRIGHT file for more information.
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#include "dap4.h"
#include "options.h"

/**************************************************/
/* dap4.y API */
extern int dap4debug;
extern int dap4parse(DAP4parser*);

/**************************************************/

/*Forward*/
static char* getinput(const char* filename);

int
main(int argc, char** argv)
{
    int flags = YXFLAG_NONE;
    char* input;
    const char* file;
    int ok = 0;
    yax_err yaxerr;
    DAP4parser* parser;

    if(!options("ted",argc,argv)) {
	fprintf(stderr,"Illegal command line option");
	exit(1);
    }

    flags = YXFLAG_NONE;
    if(optionset('t'))
	flags |= YXFLAG_TRIMTEXT;
    if(optionset('e'))
	flags |= YXFLAG_ELIDETEXT;

    if(optionset('d'))
	dap4debug = 1;

    file = nonoption(0);
    if(file == NULL) {
      fprintf(stderr,"no input\n");
      exit(1);
    }
	
    input = getinput(file);

    parser = (DAP4parser*)calloc(1,sizeof(DAP4parser));
    if(parser == NULL) {ok=0; goto done;}
    yaxerr = yax_create(input,flags,&parser->yaxlexer);
    if(yaxerr != YAX_OK) {
	fprintf(stderr,"Could not create yax lexer: %s\n",
		yax_errstring(yaxerr));
	goto done;
    }
    if(dap4parse(parser) == 0) {
	ok = 1;	
    } else {
	ok = 0;
    }

done:
    yax_free(parser->yaxlexer);
    free(parser);
    if(ok == 0)
	fprintf(stderr,"parse failed\n");
    exit(ok?0:1);
    return ok;
}

static char*
getinput(const char* filename)
{
      static char data[1<<18];
      char* pos = data;
      int rem = sizeof(data);
      int fd = open(filename,0);
      if(fd < 0) {
	perror(filename);
	exit(1);
      }
      for(;;) {
	int count = read(fd,pos,rem);
	if(count < 0) {
	    perror(filename);
	    exit(1);
	}
	if(count == 0) break;
	pos += count;
	rem -= count;
      }
      *pos = '\0';
      return data;
}


