#!/bin/bash

#set -x

if test "x$1" = "x1" ; then INIT=1; fi

DATA="./data"

CASES="comments d lextest books expr dap4"

if ! test -f ${DATA}/testoutput; then
  mkdir -p ${DATA}/testoutput
fi

for f in $CASES ; do
  echo "*** Testing xml_test: ${f}.xml"
  ./domtest -te ${DATA}/testinput/${f}.xml >& ${DATA}/testoutput/${f}.dom
  if test "x${INIT}" = x1 ; then
    cp ${DATA}/testoutput/${f}.dom ${DATA}/baseline/${f}.dom 
  else
    echo diff ${DATA}/baseline/${f}.dom ${DATA}/testoutput/${f}.dom
    if ! diff -w ${DATA}/baseline/${f}.dom ${DATA}/testoutput/${f}.dom ; then echo "*** FAIL: ${f}.xml";  fi
  fi
done

echo "*** Passed all tests"

exit 0

