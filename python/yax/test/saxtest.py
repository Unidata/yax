#This software is released under the terms of the Apache License version 2.
#For details of the license, see http://www.apache.org/licenses/LICENSE-2.0.

import os
import sys
import getopt
import codecs
import xml.sax
import StringIO
sys.path.append(os.path.join(os.getcwd(),"yax"))

import saxeventhandler
from saxevent import SaxEvent
from saxeventhandler import SaxEventHandler
import saxeventtype
import util
import testoptions

EXPECTED_VERSION = "2.00"

# Simple subclass of SaxEventHandler
class SaxTestHandler(SaxEventHandler) :

  def __init__(self, document) :
    SaxEventHandler.__init__(self,document)

  def orderedAttributes(self, element) : return None

  # Overide super class methods
  def yyevent(self, token):
    trace = util.saxtrace(token,self.flags);
    sys.stdout.write("saxtest: {0}\n".format(trace))
    sys.stdout.flush();
  # end yyevent
#end class SaxTestHandler

def main():

  if(EXPECTED_VERSION != saxeventhandler.getVersion()):
    sys.stderr.write("Version mismatch: {0} :: {1}\n",
                     EXPECTED_VERSION, saxeventhandler.getVersion())
    sys.exit(1)

  testoptions.getOptions(sys.argv);

  # Create handler
  handler = SaxTestHandler(testoptions.document)
  handler.setFlags(testoptions.flags)
  if(testoptions.saxtrace):
    handler.setTrace(True)

  # Parse the document
  handler.parse()

  sys.stdout.write("No error\n");
  sys.exit(0);
# end main()

if __name__ == '__main__':
  main()

