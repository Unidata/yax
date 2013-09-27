# Copyright 2013, UCAR/Unidata.
#  See the COPYRIGHT file for more information.

import os
import sys
import getopt
import codecs
import xml.sax
import StringIO

sys.path.append(os.path.join(os.getcwd(),"yax"))

from saxevent import SaxEvent
from saxeventhandler import SaxEventHandler
import saxeventtype
import util
from Dap4SaxParser import Dap4SaxParser
from dap4saxeventhandler import Dap4SaxEventHandler

def main() :
  flags = util.FLAG_NONE;

  try:
    opts, args = getopt.getopt(sys.argv[1:], "wleTd", ["help", "output="])
  except getopt.GetoptError as err:
    # print help information and exit:
    print(err) # will print something like "option -a not recognized"
    sys.exit(1)

  if (len(args) == 0) :
    sys.stderr.write("no input\n")
    sys.exit(1)

  debuglevel = 0
  flags = util.FLAG_NOCR # always
  for o, a in opts:
    if o == "-w":
      flags = flags + util.FLAG_TRIMTEXT
    elif o == "-l":
      flags = flags + util.FLAG_ELIDETEXT
    elif o == "-e":
      flags = flags + util.FLAG_ESCAPE
    elif o == "-d":
      debuglevel = 1

  # push parser
  dap4pushparser = Dap4SaxParser()
  if (debuglevel > 0) :
    dap4pushparser.setDebugLevel(1);

  # read the document as utf
  document = codecs.open(args[0], encoding='utf-8').read()

  # Create handler
  handler = Dap4SaxEventHandler(document,dap4pushparser)

  # Parse the document
  handler.parse()

  sys.stdout.write("No error\n");
  sys.exit(0);
# end main()

if __name__ == '__main__':
  main()




