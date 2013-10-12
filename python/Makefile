# Paths of interest
SRCDIR=./yax
TESTDIR=${SRCDIR}/test
TESTIN=./data/testinput
TESTBASE=./data/baseline
TESTOUT=./data/testoutput

.PHONEY: check action

SKEL=${SRCDIR}/python.skel

SRC=\
keywordmap.py \
lexeme.py \
saxevent.py \
saxeventhandler.py \
saxeventtype.py \
util.py

TESTSRC=\
Dap4Parser.py \
dap4eventhandler.py \
dap4test.py \
CalcParser.py \
calceventhandler.py \
calctest.py \
saxtest.py \
testoptions.py

SAXTESTS=books comments d expr lextest
DAP4TESTS=dap4
CALCTESTS=calc

all: 
	for f in ${SRC} ; do (python -mpy_compile yax/$$f) done
	for f in ${TESTSRC} ; do (python -mpy_compile ${TESTDIR}/$$f) done

clean::
	rm -fr ${TESTOUT} 

${TESTOUT}:
	mkdir -p ${TESTOUT}

check: saxtest dap4test calctest

saxtest::
	@for t in ${SAXTESTS} ; do ${MAKE} TEST=sax CASE=$$t action ; done

dap4test: ${DAP4SRC}
	@for t in ${DAP4TESTS} ; do ${MAKE} TEST=dap4 CASE=$$t action ; done

calctest: ${CALCSRC}
	@for t in ${CALCTESTS} ; do ${MAKE} TEST=calc CASE=$$t action ; done

action: ${TESTOUT}
	@rm -f tmp
	python ${TESTDIR}/${TEST}test.py -xe -tp ${TESTIN}/${CASE}.xml >& tmp
	@sed -e 's/(line [0-9][0-9]*)//' < tmp > ${TESTOUT}/${CASE}.sax
	@if diff -wBb ${TESTBASE}/${CASE}.sax ${TESTOUT}/${CASE}.sax;\
	then echo "Passed: ${TEST}" ; else echo "Failed: ${TEST}" ; fi

generate::
	bison -S${SKEL} -t -v -pDap4 -o ${TESTDIR}/Dap4Parser.py ${TESTDIR}/dap4.y
	bison -S${SKEL} -t -v -pCalc -o ${TESTDIR}/CalcParser.py ${TESTDIR}/calc.y

dist::
	python setup.py sdist

init::
	cp -f ${TESTOUTPUTS}/*.sax ${TESTBASELINE}
