# Copyright 2009, UCAR/Unidata and OPeNDAP, Inc.
# See the COPYRIGHT file for more information.

import saxeventtype
from xml.sax import SAXException
from saxeventhandler import SaxEventHandler
from lexeme import Lexeme
from Dap4SaxParser import *

def static () :
  global elementmap, attributemap

  elementmap = {}
  attributemap = {}

  elementmap[u"Dataset"] = Lexeme(u"Dataset",DATASET,DATASET_,
                        (u"name",u"dapversion",u"ddxversion",u"ns",u"base",u"xmlns"))
  elementmap[u"Group"] = Lexeme(u"Group",GROUP,GROUP_,
                        (u"name"))
  elementmap[u"Enumeration"] = Lexeme(u"Enumeration",ENUMERATION,ENUMERATION_,
                        (u"name",u"basetype"))
  elementmap[u"EnumConst"] = Lexeme(u"EnumConst",ENUMCONST,ENUMCONST_,
                        (u"name",u"value"))
  elementmap[u"Namespace"] = Lexeme(u"Namespace",NAMESPACE,NAMESPACE_,
                        (u"href"))
  elementmap[u"Dimension"] = Lexeme(u"Dimension",DIMENSION,DIMENSION_,
                        (u"name",u"size"))
  elementmap[u"Dim"] = Lexeme(u"Dim",DIM,DIM_,
                        (u"name",u"size"))
  elementmap[u"Enum"] = Lexeme(u"Enum",ENUM,ENUM_,
                        (u"enum",u"name"))
  elementmap[u"Map"] = Lexeme(u"Map",MAP,MAP_,
                        (u"name"))
  elementmap[u"Structure"] = Lexeme(u"Structure",STRUCTURE,STRUCTURE_,
                 (u"name"))
  elementmap[u"Value"] = Lexeme(u"Value",VALUE,VALUE_,
                        (u"value"))
  elementmap[u"Attribute"] = Lexeme(u"Attribute",ATTRIBUTE,ATTRIBUTE_,
                        (u"name",u"type",u"namespace"))
  elementmap[u"Char"] = Lexeme(u"Char",CHAR,CHAR_,
                        (u"name"))
  elementmap[u"Byte"] = Lexeme(u"Byte",BYTE,BYTE_,
                        (u"name"))
  elementmap[u"Int8"] = Lexeme(u"Int8",INT8,INT8_,
                        (u"name"))
  elementmap[u"UInt8"] = Lexeme(u"UInt8",UINT8,UINT8_,
                        (u"name"))
  elementmap[u"Int16"] = Lexeme(u"Int16",INT16,INT16_,
                        (u"name"))
  elementmap[u"UInt16"] = Lexeme(u"UInt16",UINT16,UINT16_,
                        (u"name"))
  elementmap[u"Int32"] = Lexeme(u"Int32",INT32,INT32_,
                        (u"name"))
  elementmap[u"UInt32"] = Lexeme(u"UInt32",UINT32,UINT32_,
                        (u"name"))
  elementmap[u"Int64"] = Lexeme(u"Int64",INT64,INT64_,
                        (u"name"))
  elementmap[u"UInt64"] = Lexeme(u"UInt64",UINT64,UINT64_,
                        (u"name"))
  elementmap[u"Float32"] = Lexeme(u"Float32",FLOAT32,FLOAT32_,
                        (u"name"))
  elementmap[u"Float64"] = Lexeme(u"Float64",FLOAT64,FLOAT64_,
                        (u"name"))
  elementmap[u"String"] = Lexeme(u"String",STRING,STRING_,
                                  (u"name"))
  elementmap[u"URL"] = Lexeme(u"URL",URL,URL_,
                        (u"name"))
  elementmap[u"Opaque"] = Lexeme(u"Opaque",OPAQUE,OPAQUE_,
                        (u"name"))

  # Always insert the lowercase name
  attributemap[u"base"] = Lexeme(u"base",attr=ATTR_BASE)
  attributemap[u"basetype"] = Lexeme(u"basetype",attr=ATTR_BASETYPE)
  attributemap[u"dapversion"] = Lexeme(u"dapversion",attr=ATTR_DAPVERSION)
  attributemap[u"ddxversion"] = Lexeme(u"ddxversion",attr=ATTR_DDXVERSION)
  attributemap[u"enum"] = Lexeme(u"enum",attr=ATTR_ENUM)
  attributemap[u"href"] = Lexeme(u"href",attr=ATTR_HREF)
  attributemap[u"name"] = Lexeme(u"name",attr=ATTR_NAME)
  attributemap[u"namespace"] = Lexeme(u"namespace",attr=ATTR_NAMESPACE)
  attributemap[u"size"] = Lexeme(u"size",attr=ATTR_SIZE)
  attributemap[u"type"] = Lexeme(u"type",attr=ATTR_TYPE)
  attributemap[u"value"] = Lexeme(u"value",attr=ATTR_VALUE)
  attributemap[u"ns"] = Lexeme(u"ns",attr=ATTR_NS)
  attributemap[u"xmlns"] = Lexeme(u"xmlns",attr=ATTR_XMLNS)

static() # initialize static constants

#########################

class Dap4SaxEventHandler (SaxEventHandler) :

  #########################
  # Constructor(s)

  def __init__(self, document, pushparser) :
    SaxEventHandler.__init__(self,document)
    self.pushparser = pushparser
    self.accepted = False
    self.textok = False

  #########################
  # Abstract method overrides

  # Push the token to the parser
  # @raise SAXException if parser return YYABORT

  def yyevent(self,saxtoken) :
    global elementmap, attributemap    
    if(self.accepted) :
      raise SAXException("yyevent called after parser has accepted")
    yaxevent = saxtoken.event
    name = saxtoken.name
    yytoken = 0
    element = None
    attr = None

    if name in elementmap:
      element = elementmap[name]
    else: element = None
    xx = ATTRIBUTE
    #switch(yaxevent)
    if (yaxevent == saxeventtype.STARTELEMENT) :
      if(element == None) : # undefined
        yytoken = UNKNOWN_ELEMENT
      else:
        yytoken = element.open
        # Check for the special case of <Value>
        if(yytoken == VALUE_) :
          self.textok = True

    elif (yaxevent == saxeventtype.ATTRIBUTE) :
      if name.lower() in attributemap:
        attr = attributemap[name.lower()]
      else:
        attr = None
      if(attr == None) :
        yytoken = UNKNOWN_ATTR
      else :
        yytoken = attr.attr

    elif (yaxevent == saxeventtype.ENDELEMENT) :
      if(element == None) : # undefined
        yytoken = UNKNOWN_ELEMENT_
      else :
        yytoken = element.close
        self.textok = False

    elif (yaxevent == saxeventtype.CHARACTERS) :
      if(not self.textok) :
        return # ignore
      yytoken = TEXT

    elif (yaxevent == saxeventtype.STARTDOCUMENT) :
      return # ignore

    elif (yaxevent == saxeventtype.ENDDOCUMENT) :
      yytoken = EOF

    else : #default
      self.pushparser.yyerror("unknown event type: %s\n".format(yaxevent.name))
      yytoken = ERROR

    status = 0
    try :
      status = self.pushparser.push_parse(yytoken,saxtoken)
    except SAXException as se :
      raise se
    except Exception as e :
      raise SAXException("Parse failure:"+str(e))
    if(status == YYABORT) :
      raise SAXException("YYABORT")
    elif(status == YYACCEPT) :
      accepted = True
  # end yyevent

# end class Dap4SaxEventHandler
  
