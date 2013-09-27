# Copyright 2009, UCAR/Unidata.
# See the COPYRIGHT file for more information.

class SaxParser:

  def __init__(self):
  #end __init__

  ##################################################
  # Parser API ~ "abstract" methods

  # Return whether verbose error messages are enabled.
  def getErrorVerbose(self) : return False

  # Set the verbosity of error messages.
  # @param verbose True to request verbose error messages.
  def setErrorVerbose(self, verbose) : pass

  # Return the <tt>PrintStream</tt> on which the debugging output is
  # printed.
  def getDebugStream (self) : return None

  # Set the <tt>PrintStream</tt> on which the debug output is printed.
  # @param s The stream that is used for debugging output.
  def setDebugStream(self, s) : pass

  # Answer the verbosity of the debugging output 0 means that all kinds of
  # output from the parser are suppressed.
  def getDebugLevel (self) : return 0

  # Set the verbosity of the debugging output 0 means that all kinds of
  # output from the parser are suppressed.
  # @param level The verbosity level for debugging output.
  def setDebugLevel (self, level) : pass

  def push_parse (self, yylextoken, yylexval) : return False

#class SaxParser
