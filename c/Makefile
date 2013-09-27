# Initialize baseline
#INIT=1

# Paths of interest
GRAMMARS=./grammars
TESTINPUTS=./testdata/testinput
TESTOUTPUTS=./testdata/testoutput

#X=.exe

# The following set of CFLAGS may not work with all compilers
# If so, then just remove any that fail.
CFLAGS=-g -O0 -Wall -Wdeclaration-after-statement
CC=gcc

.PHONEY: check

all: libyax.a

clean:: testclean
	rm -f libyax.a *.o

testclean::
	rm -f lextest${X}
	rm -f domtest${X}
	rm -f dap4test${X}
	rm -f xml.y dap4.y
	rm -f dap4.output dap4.tab.c dap4.tab.h
	rm -fr xml.output xml.tab.c xml.tab.h
	rm -fr data/testoutput

OPTS=-te
check:: lextest${X} domtest${X} dap4test${X}
	./lextest.sh ${INIT}
	./domtest.sh ${INIT}
	./dap4test.sh ${INIT}

libyax.a: yax.c yax.h yxlist.c yxlist.h
	${CC} ${CFLAGS} -I. -c yax.c yxlist.c
	ar -cr libyax.a yax.o yxlist.o

traceyax${X}: traceyax.c libyax.a
	${CC} ${CFLAGS} -o traceyax${X} -I. traceyax.c libyax.a

lextest${X}: lextest.c options.c options.h libyax.a
	${CC} ${CFLAGS} -o lextest${X} -I. lextest.c options.c libyax.a

DTHDRS=xml.tab.h options.h
DTSRC=xml.tab.c xml_actions.c xml_lexer.c options.c

domtest${X}: domtest.c ${DTHDRS} ${DTSRC} libyax.a
	${CC} ${CFLAGS} -o domtest${X} -I. domtest.c ${DTSRC} libyax.a
#	${CC} -E ${CFLAGS} -I. domtest.c ${DTSRC} >domtest.txt

xml.tab.c xml.tab.h: xml.y
	bison -t -d -v -pxml xml.y

xml.y: xml.y.hdr ${GRAMMARS}/xml.y xml_actions.c
	cat xml.y.hdr ${GRAMMARS}/xml.y > xml.y

D4HDRS=dap4.tab.h options.h
D4SRC=dap4.tab.c dap4_lexer.c dap4_perfect.c options.c

dap4test${X}: dap4test.c ${D4HDRS} ${D4SRC} libyax.a
	${CC} ${CFLAGS} -o dap4test${X} -I. dap4test.c ${D4SRC} libyax.a
#	${CC} -E ${CFLAGS} -I. dap4test.c ${D4SRC} >dap4test.txt

dap4.tab.c dap4.tab.h: dap4.y
	bison -t -d -v -pdap4 dap4.y

dap4.y: dap4.y.hdr ${GRAMMARS}/dap4.y
	cat dap4.y.hdr ${GRAMMARS}/dap4.y > dap4.y

# Only invoke if gperf program is available
# Remove the line numbers for easier debug with gdb
perfect::
	rm -f dap4_perfect.c 
	gperf dap4.gperf | sed -e '/^[#]line/d' >dap4_perfect.c
