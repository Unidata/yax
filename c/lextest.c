#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#include "options.h"

#include "yax.h"

static char* getinput(const char* filename);

int
main(int argc, char** argv)
{
    yax_err err = YAX_OK;
    yax_lexer* lexer;
    yax_token token;
    int flags = YXFLAG_NONE;

    char* input;
    int i;
    const char* file;
  
    if(!options("te",argc,argv)) {
	fprintf(stderr,"Illegal command line option");
	exit(1);
    }

    flags = YXFLAG_NONE;
    if(optionset('t'))
	flags |= YXFLAG_TRIMTEXT;
    if(optionset('e'))
	flags |= YXFLAG_ELIDETEXT;

    file = nonoption(0);
    if(file == NULL) {
      fprintf(stderr,"no input\n");
      exit(1);
    }

    input = getinput(file);

    err = yax_create(input,flags,&lexer);
    if(err) {
      fprintf(stderr,"%s\n",yax_errstring(err));
      exit(1);
    }

    memset((void*)&token,0,sizeof(token));

    for(i=0;i<200;i++)
    {
	char* trace = NULL;
	err = yax_nexttoken(lexer,&token);
	if(err)
	    break;
	trace = yax_trace(lexer,&token); printf("%s\n",trace); fflush(stdout); if(trace) free(trace);
	if(token.tokentype == YAX_EOF)
	    break;
    }
    printf("%s\n",yax_errstring(err)); fflush(stdout);
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


