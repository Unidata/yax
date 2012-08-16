#!/bin/bash

#set -x

if test "x$1" = "x1" ; then INIT=1; fi

DATA="../data"

CASES="dap4"

rm -fr ${DATA}/testoutput
mkdir -p ${DATA}/testoutput

for f in $CASES ; do
  echo "*** Testing dap4_test: ${f}.dap4"
  ./dap4test -dte ${DATA}/testinput/${f}.xml >& ${DATA}/testoutput/${f}.dap4
  if test "x${INIT}" = x1 ; then
    cp ${DATA}/testoutput/${f}.dap4 ${DATA}/baseline/${f}.dap4 
  fi
  if ! diff -w ${DATA}/baseline/${f}.dap4 ${DATA}/testoutput/${f}.dap4 ; then echo "*** FAIL: ${f}.dap4";  fi
done

echo "*** Passed all tests"

exit 0

