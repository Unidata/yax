/**
This software is released under the terms of the Apache License version 2.
For details of the license, see http://www.apache.org/licenses/LICENSE-2.0.
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#include "dap4test.h"
#include "options.h"

/**************************************************/

/*Forward*/
static char* getinput(const char* filename);

/**************************************************/

int
main(int argc, char** argv)
{
    SaxFlags flags = SXFLAG_NONE;
    char* input;
    const char* file;
    int ok = 0;
    Dap4EventHandler* deh;

    if(!options("t:d",argc,argv)) {
	fprintf(stderr,"Illegal command line option");
	exit(1);
    }

    flags = 0;

    if(optionset('t')) {
	const char* s = soption('t');
	int c;
	while((c=*s++)) {
	    switch (c) {
	    case 't': flags |= SXFLAG_TRIMTEXT; break;
	    case 'l': flags |= SXFLAG_ELIDETEXT; break;
	    case 'e': flags |= SXFLAG_ESCAPE; break;
	    case 'r': flags |= SXFLAG_NOCR; break;
	    default:
		fprintf(stderr,"unknown -t flag: %c",(char)c);
	    }
	}
    }
    if(optionset('d'))
	dap4debug = 1;

    file = nonoption(0);
    if(file == NULL) {
      fprintf(stderr,"no input\n");
      exit(1);
    }
	
    input = getinput(file);

    deh = dap4_eventhandler(input);
    ok = dap4_parse(deh);
    dap4_eventhandlerfree(deh);

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


