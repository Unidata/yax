#!/bin/bash

#set -x

if test "x$1" = "x1" ; then INIT=1; fi

DATA="./data"

CASES="comments d lextest books expr dap4"

rm -fr ${DATA}/testoutput
mkdir -p ${DATA}/testoutput

for f in $CASES ; do
  echo "*** Testing lex_test: ${f}.xml"
  ./lextest -te ${DATA}/testinput/${f}.xml >& ${DATA}/testoutput/${f}.yax
  if test "x${INIT}" = x1 ; then
    cp ${DATA}/testoutput/${f}.yax ${DATA}/baseline/${f}.yax
  fi
  if ! diff -w ${DATA}/baseline/${f}.yax ${DATA}/testoutput/${f}.yax ; then echo "*** FAIL: ${f}.xml";  fi
done

echo "*** Passed all tests"

exit 0
