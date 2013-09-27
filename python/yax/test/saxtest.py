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

# Simple subclass of SaxEventHandler
class SaxTestHandler(SaxEventHandler) :

  def __init__(self, document) :
    SaxEventHandler.__init__(self,document)

  def orderedAttributes(self, element) : return None

  # Overide super class methods
  def yyevent(self, token):
    trace = util.saxtrace(token,0);
    sys.stdout.write("saxtest: {0}\n".format(trace))
    sys.stdout.flush();
  # end yyevent
#end class SaxTestHandler

def main():
  flags = util.FLAG_NONE;

  try:
    opts, args = getopt.getopt(sys.argv[1:], "let", ["help", "output="])
  except getopt.GetoptError as err:
    # print help information and exit:
    print(err) # will print something like "option -a not recognized"
    sys.exit(1)

  flags = util.FLAG_NOCR # always
  for o, a in opts:
    if o == "-t":
      flags = flags + util.FLAG_TRIMTEXT
    elif o == "-l":
      flags = flags + util.FLAG_ELIDETEXT
    elif o == "-e":
      flags = flags + util.FLAG_ESCAPE

  if (len(args) == 0) :
    sys.stderr.write("no input\n")
    sys.exit(1)

  # read the document as utf
  document = codecs.open(args[0], encoding='utf-8').read()

  # Create handler
  handler = SaxTestHandler(document)

  # Parse the document
  handler.parse()

  sys.stdout.write("No error\n");
  sys.exit(0);
# end main()

if __name__ == '__main__':
  main()

