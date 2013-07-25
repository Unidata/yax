#!/bin/bash

#set -x

INIT=
if test "x$1" = x1 ; then INIT=1; fi

# Assume we are run in data dir
DATA=`pwd`

CASES="domtest dap4dom saxtest dap4sax"

fail=no

for f in $CASES ; do
  echo "*** Testing: ${f}"
  # Ignore missing cases 
  if ! test -f ${DATA}/testoutput/${f}.txt ; then
    echo "Skipping test ${f}"
    continue;
  fi
  # Remove the (line xxx) to make this insensitive to minor changes to .y file
  rm -f ./original
  mv ${DATA}/testoutput/${f}.txt ./original
  sed -e 's/(line [0-9][0-9]*)//' <./original >${DATA}/testoutput/${f}.txt
  if test "x${INIT}" = x1 ; then
    echo "Initializing test ${f}"
    cp ${DATA}/testoutput/${f}.txt ${DATA}/baseline/${f}.txt
    continue;
  fi
  echo "***     Comparing testoutput/${f}.txt baseline/${f}.txt"
  if  diff -w ${DATA}/baseline/${f}.txt ${DATA}/testoutput/${f}.txt ; then
    echo "*** PASS: ${f}.xml";
  else
    echo "*** FAIL: ${f}.xml: files differ";
    fail=no
  fi
done

if test "x$fail" = "xyes" ; then
  echo "*** Failed some tests"
else
  echo "*** Passed all tests"
fi

exit 0
