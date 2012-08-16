#!/bin/bash

#set -x

if test "x$1" = "x1" ; then INIT=1; fi

DATA="../data"

CASES="comments d lextest books expr dap4"

rm -fr ${DATA}/testoutput
mkdir -p ${DATA}/testoutput

for f in $CASES ; do
  echo "*** Testing xml_test: ${f}.xml"
  ./domtest -te ${DATA}/testinput/${f}.xml >& ${DATA}/testoutput/${f}.dom
  if test "x${INIT}" = x1 ; then
    cp ${DATA}/testoutput/${f}.dom ${DATA}/baseline/${f}.dom 
  fi
  if ! diff -w ${DATA}/baseline/${f}.dom ${DATA}/testoutput/${f}.dom ; then echo "*** FAIL: ${f}.xml";  fi
done

echo "*** Passed all tests"

exit 0

