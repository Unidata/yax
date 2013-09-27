# Paths of interest
SRCDIR=./yax
TESTDIR=${SRCDIR}/test
TESTDATA=${TESTDIR}/data/testinput
TESTBASE=${TESTDIR}/data/baseline
TESTOUT=${TESTDIR}/data/testoutput

.PHONEY: check

SRC=\
keywordmap.py \
lexeme.py \
saxevent.py \
saxeventhandler.py \
saxeventtype.py \
util.py

TEST=\
Dap4SaxParser.py \
dap4saxeventhandler.py \
dap4saxtest.py \
saxtest.py

all:
	for f in ${SRC} ; do (python -mpy_compile yax/$$f) done
	for f in ${TEST} ; do (python -mpy_compile ${TESTDIR}/$$f) done

check:: generate
	rm -fr ${TESTOUT}
	mkdir ${TESTOUT}
	python ${TESTDIR}/saxtest.py -le ${TESTDATA}/dap4.xml >${TESTOUT}/saxtest.txt
	diff ${TESTBASE}/saxtest.txt ${TESTOUT}/saxtest.txt
	rm -f ./tmp
	python ${TESTDIR}/dap4saxtest.py -dle ${TESTDATA}/dap4.xml >& ./tmp
	sed -e 's/(line [0-9][0-9]*)//' <./tmp > ${TESTOUT}/dap4saxtest.txt
	diff ${TESTBASE}/dap4saxtest.txt ${TESTOUT}/dap4saxtest.txt

clean::
	rm -f ${SRCDIR}/*.pyc ${TESTDIR}/*.pyc
	rm -fr build dist

generate::
	bison  -S${SRCDIR}/python.skel -v -pDap4 ${TESTDIR}/dap4.sax.y -o ${TESTDIR}/Dap4SaxParser.py

testclean::
	rm -fr ${TESTOUT}

dist::
	python setup.py sdist
