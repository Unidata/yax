#This software is released under the terms of the Apache License version 2.
#For details of the license, see http://www.apache.org/licenses/LICENSE-2.0.

#Valgrind
#VG=-v

# Paths of interest
TESTDIR=./data
TESTINPUTS=${TESTDIR}/testinput
TESTOUTPUTS=${TESTDIR/testoutput
TESTBASELINE=${TESTDIR/baseline

XMLLIB=-L/usr/local/lib -lxml2
XMLINCL=-I/usr/local/include/libxml2

LIBS=libyax.a ${XMLLIB} -lm
INCL=-I. ${XMLINCL}

# The following set of CFLAGS may not work with all compilers
# If so, then just remove any that fail.
CFLAGS=-g -O0 -Wall -Wdeclaration-after-statement
CC=gcc

GRAMMARS=dap4.y calc.y

LIBSRC=saxeventhandler.c
LIBHDRS=sax.h

SAXTEST=\
saxtest.c \
options.c

DAP4TEST=\
dap4.tab.c \
dap4eventhandler.c \
dap4test.c \
dap4keyword.c \
options.c

CALCTEST=\
calc.tab.c \
calceventhandler.c \
calctest.c \
calckeyword.c \
options.c

.PHONEY: check

all: libyax.a

clean:: testclean
	rm -f libyax.a *.o

testclean::
	rm -f ./saxtest ./dap4test ./calctest

libyax.a: ${LIBSRC} ${LIBHDRS}
	${CC} -c ${CFLAGS} ${INCL} ${LIBSRC}
	ar -cr libyax.a ${LIBSRC:.c=.o}

check: saxtest dap4test calctest
	for t in $^ ; do (sh ./test.sh -p $$t ${VG} ) ; done

saxtest: libyax.a saxtest.c 
	${CC} ${CFLAGS} -o saxtest ${INCL} saxtest.c options.c ${LIBS}
#	${CC} -E ${CFLAGS} -I. dap4test.c ${D4SRC} >saxtest.txt

dap4test: libyax.a ${DAP4TEST}
	${CC} ${CFLAGS} -c ${INCL} ${DAP4TEST}
	${CC} ${CFLAGS} -o dap4test ${DAP4TEST:.c=.o} ${LIBS}
#	${CC} -E ${CFLAGS} ${INCLUDES} dap4.tab.c >dap4test.txt

calctest: libyax.a ${CALCTEST}
	${CC} ${CFLAGS} -c ${INCL} ${CALCTEST}
	${CC} ${CFLAGS} -o calctest ${CALCTEST:.c=.o} ${LIBS}
#	${CC} -E ${CFLAGS} ${INCLUDES} calc.tab.c >calctest.txt

generate::
	bison -t -d -v -pdap4 dap4.y
	bison -t -d -v -pcalc calc.y

# Only invoke if gperf program is available
# Remove the line numbers for easier debug with gdb
perfect::
	rm -f dap4_perfect.c 
	gperf dap4.gperf | sed -e '/^[#]line/d' >dap4_perfect.c
	rm -f calc_perfect.c 
	gperf calc.gperf | sed -e '/^[#]line/d' >calc_perfect.c
init::
	cp -f ${TESTOUTPUTS}/*.sax ${TESTBASELINE}
	cp -f ${TESTOUTPUTS}/dap4.dap4 ${TESTBASELINE}
	cp -f ${TESTOUTPUTS}/calc.calc ${TESTBASELINE}
