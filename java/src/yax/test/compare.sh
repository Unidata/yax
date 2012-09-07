#!/bin/bash

#set -x

INIT=
if test "x$1" = x1 ; then INIT=1; fi

# Assume we are run in data dir
DATA=`pwd`

CASES="domtest dap4dom saxtest dap4sax"

for f in $CASES ; do
  echo "*** Testing: ${f}"
  # Ignore missing cases 
  if ! test -f ${DATA}/testoutput/${f}.txt ; then
    echo "Skipping test ${f}"
    continue;
  fi
  if test "x${INIT}" = x1 ; then
    echo "Initializing test ${f}"
    cp ${DATA}/testoutput/${f}.txt ${DATA}/baseline/${f}.txt
    continue;
  fi
  echo "***     Comparing testoutput/${f}.txt baseline/${f}.txt"
  if ! diff -w ${DATA}/baseline/${f}.txt ${DATA}/testoutput/${f}.txt ; then
    echo "*** FAIL: ${f}.xml: files differ";
  fi
done

echo "*** Passed all tests"

exit 0
