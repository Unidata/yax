#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#include "options.h"

#include "yxlist.h"
#include "yax.h"
#include "xml.h"

static char* getinput(const char* filename);

int
main(int argc, char** argv)
{
    int flags = YXFLAG_NONE;
    char* input;
    const char* file;
    XMLnode* document = NULL;

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
	xmldebug = 1;

    file = nonoption(0);
    if(file == NULL) {
      fprintf(stderr,"no input\n");
      exit(1);
    }
	
    input = getinput(file);

    if(!xml_parse(input,flags,&document)) {
	exit(1);
    }

    xml_dumplist(document->children,flags,stdout);

    xml_nodefree(document);

    exit(0);
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


