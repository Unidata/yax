# Copyright 2009, UCAR/Unidata
#  See the COPYRIGHT file for more information. */

import sys
import io
import xml.sax
import xml.sax.saxutils
from xml.sax.handler import ContentHandler
from xml.sax.handler import ErrorHandler
from xml.sax.handler import EntityResolver
from xml.sax.handler import DTDHandler
from xml.sax.xmlreader import XMLReader
from xml.sax.xmlreader import InputSource
from saxevent import SaxEvent
import saxeventtype

#########################
# debug

def TRACE(arg1, arg2):
  if(False) :
    sys.stderr.write("event.{0}: {1}\n".format(str(arg1),str(arg2)))
#end TRACE

#########################

class SaxEventHandler(object,ContentHandler,
                      ErrorHandler,
                      EntityResolver,
                      DTDHandler):

  #########################
  # Constructor(s)

  def __init__(self, source):
    ContentHandler.__init__(self)
    # ErrorHandler.__init__(self) apparently does not exist
    # EntityResolver.__init__(self) apparently does not exist
    # DTDHandler.__init__(self) apparently does not exist
    if(source == None
       or (isinstance(source,unicode) and len(source) == 0)) :
      raise SAXException("SaxEventHandler: Empty document")
    self.source = source
  # end __init__

  #########################
  # Overrideable method(s)

  # Send the lexeme to the the subclass to process
  def yyevent(self, token) : pass

  #########################
  # Public API

  def parse(self) :
    if (isinstance(self.source,unicode)) :
      # Create a string source
      file = io.StringIO(self.source)
      input = InputSource(file)
      input.setEncoding("utf-8")
      input.setCharacterStream(file)
      # There is a bug in xml.sax.saxutils.prepare_input_source
      input.setByteStream(file)
      input.setSystemId(None)
    elif (isinstance(self.source,InputSource)):
      input = self.source
    else:
      raise Exception("Parse source must be either string or InputSource")

    # Create the parser/xmlreader
    parser = xml.sax.make_parser()

    # Tell the parser to use our handler(s)
    parser.setContentHandler(self)
    #parser.setErrorHandler(self)

    # Parse the document
    parser.parse(input)
  # end parse()

  #########################
  # DefaultHandler Overrides

  # The key thing to note is that we map the Sax Event
  # into a subset based on yax.lex.Type. This means
  # we feed only a subset of the possible events into
  # the subclass handler. This can be changed by
  # overriding the suppressing event handler below.


  def setDocumentLocator(self, locator):
    self.locator = locator

  def startDocument(self) :
    event = SaxEvent(saxeventtype.STARTDOCUMENT,self.locator)
    TRACE(event.event,event)
    self.yyevent(event)
  # end startDocument

  def endDocument(self) :
    event = SaxEvent(saxeventtype.ENDDOCUMENT,self.locator)
    TRACE(event.event,event)
    self.yyevent(event)
  # end endDocument()

  def startElement(self, name, attributes) :
    event = SaxEvent(saxeventtype.STARTELEMENT,self.locator,name)
    TRACE(event.event,event)
    self.yyevent(event)
    # Now pass the attributes as tokens
    nattr = attributes.getLength()
    names = attributes.getNames()
    for i in range(0,nattr) :
      aname = names[i]
      value = attributes.getValue(aname)

      event = SaxEvent(saxeventtype.ATTRIBUTE,self.locator,aname,value=value)
      TRACE(event.event,event)
      self.yyevent(event)
  # end startElement()

  def endElement(self, name) :
    event = SaxEvent(saxeventtype.ENDELEMENT,self.locator,name)
    TRACE(event.event,event)
    self.yyevent(event)
  # end endElement()

  def characters(self, content) :
    event = SaxEvent(saxeventtype.CHARACTERS,self.locator,text=content)
    TRACE(event.event,event)
    self.yyevent(event)
  # end characters()

  # Following events are suppressed

  def ignorableWhitespace(self, whitespace) :
    # should never see this since not validating
    return
  # end ignorableWhitespace()

  def endPrefixMapping(self, prefix):
    return
  # end endPrefixMapping()

  def notationDecl(name, publicId,  systemId) :
    return
  # end notationDecl()

  def processingInstruction(self, target, data):
    return
  # end processingInstruction()

  def skippedEntity(self, name) :
    return
  # end skippedEntity()

  def startPrefixMapping(self, prefix, uri):
    return
  # end startPrefixMapping()

  def unparsedEntityDecl(self, name, publicId, systemId, notationName) :
    return
  # end unparsedEntityDecl()

  #########################
  # Entity resolution

  def resolveEntity(self, publicId, systemId):
    TRACE(publicId,systemId)
    return None
  # end resolveEntity()

  #########################
  # Error handling Events

  def fatalError(e):
    raise e
  # end fatalError()

  def error(e):
    sys.stderr.write("Sax error: {0}\n".format(str(e)))
  # end error()

  def warning(e):
    sys.stderr.write("Sax warning: {0}\n".format(str(e)))
  # end warning()

  def startElementNS(self, nsname, qname, attrs):
    uri = nsname[0]
    name = nsname[1]
    return self.startElement(name,attrs)
  # end startElementNS()

  def endElementNS(self, nsname, qname):
    uri = nsname[0]
    name = nsname[1]
    return self.endElement(name)
  #end endElementNS()

# end class SaxEventHandler
