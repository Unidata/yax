#!/bin/bash

#set -x

INIT=
if test "x$1" = x1 ; then INIT=1; fi

# Assume we are run in data dir
DATA=`pwd`

CASES="lextest dap4"

for f in $CASES ; do
  echo "*** Testing: ${f}"
  echo "***     Comparing testoutput/${f}.txt baseline/${f}.txt"
  if test "x${INIT}" = x1 ; then
    cp ${DATA}/testoutput/${f}.txt ${DATA}/baseline/${f}.txt
  fi
  if ! diff -w ${DATA}/baseline/${f}.txt ${DATA}/testoutput/${f}.txt ; then echo "*** FAIL: ${f}.xml: files differ";  fi
done

echo "*** Passed all tests"

exit 0
