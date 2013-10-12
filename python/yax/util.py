# Copyright 2009, UCAR/Unidata.
# See the COPYRIGHT file for more information.

import sys
import string
import saxeventtype

#Provide methods for unescaping entities in text.

MAXTEXT = 12

DEFAULTTRANSTABLE = (
    ("amp","&"),
    ("lt","<"),
    ("gt",">"),
    ("quot","\""),
    ("apos","'"),
    )

# Common Flag Set
FLAG_NONE       =  0
FLAG_ESCAPE     =  1 #convert \n,\r, etc to \\ form
FLAG_NOCR       =  2 # elide \r
FLAG_ELIDETEXT  =  4 # only print the first 12 characters of text
FLAG_TRIMTEXT   =  8 #remove leading and trailing whitespace
                     # if result is empty, then ignore
FLAG_TRACE      = 16 # Trace the DomLexer tokens
DEFAULTFLAGS   = (FLAG_ELIDETEXT+FLAG_ESCAPE+FLAG_NOCR+FLAG_TRIMTEXT)

# Characters legal as first char of an element or attribute name
def namechar1(c) :
  return (
     string.find(":_?",c) >= 0
     or ord(c) > 127
     or string.find("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ",c) >= 0)
#end namechar1    

# Characters legal as greater than first char of an element or attribute name */
def namecharn(c) :
  return (namechar1(c)
          or string.find("-.",c) >= 0
          or string.find("0123456789",c) >= 0)
# end namecharn

#
# Provide a procedure for generating a string
# representing the contents of an Token instance.
# Primarily for use for debugging.
# @param type token type
# @param node token node 
# @param (optional) flags to control trace output
    
#def domtrace(type, node, flags=DEFAULTFLAGS) :
#  result = ""
#  name = "UNDEFINED"
#  value = ""
#  nodetype = 0
#
#  if(node != None) :
#    name = node.getNodeName()
#    value = node.getNodeValue()
#    nodetype = node.getNodeType()
#  result += ('['+nodetypeName(nodetype)+']')
#  result += (type.name())
#
#  # switch: probably could use lambda dictionary/list
#  if(type == OPEN or type == CLOSE) :
#    result += (": element=|")
#    result += (name)
#    result += ("|")
#  elif(type == COMMENT or type == TEXT) :
#    result += (" text=")
#    result = addtext(result,value,flags)
#    trans = unescape(value)
#    result += (" translation=")
#    result = addtext(result,trans,flags)
#  elif(type == ATTRIBUTE) :
#    result += (": name=")
#    result = addtext(result,name,flags)
#    result += (" value=")
#    result = addtext(result,value,flags)
#  elif(type == CDATA) :
#    result += (": text=")
#    result = addtext(result,value,flags)
#  elif(type == DOCTYPE or type == PROLOG) :
#    result += (": name=")
#    result = addtext(result,name,flags)
#    result += (" value=")
#    result = addtext(result,value,flags)
#  elif(type == EOF) :
#    pass
#  elif(type == UNDEFINED) :
#    pass
#  else:
#    assert False ,  "Unexpected tokentype"
#  return result
##end trace()

# Unescape entities in a string.
# The translations argument is in envv form
# with position n being the entity name and
# position n+1 being the translation and last
# position being None.
def unescape(s, translations=None):
  if(translations == None) :
    translations = DEFAULTTRANSTABLE
  if(s == None) :
    nchars = 0
  else:
    nchars = len(s)
  u = ""
  p = 0
  q = 0
  stop = (nchars)
  entity = ""
  while(q < stop) :
    c = s[q]
    q += 1
    # switch
    if(c == '&') : # see if this is a legitimate entity 
      entity = ""
      # move forward looking for a semicolon 
      found = True
      count = 0
      while True :
        if(q+count >= nchars) : break
        c = s[q+count]
        if(c == ';') : break
        if((count==0 and not namechar1(c)) 
            or (count > 0 and not namecharn(c))) :
          found=False # not a legitimate entity 
          break
        entity += (c)
        count += 1
      if(q+count >= nchars or count == 0 or not found) :
        # was not in correct form for entity 
        u += ('&')
      else : # looks legitimate 
        test = str(entity)
        replacement = None
        for trans in translations :
          if(trans[0] == (test)) :
            replacement = trans[1]
            break
        if(replacement == None) : # no translation, ignore 
          u += ('&')
        else : # found it 
          q += (count+1)  # skip input entity, including trailing semicolon 
          u += (replacement)
  
    else :
      u += (c)
  return u
#end unescape

def addtext(prefix, txt, flags):
  dst = prefix
  shortened = False
  if(txt == None) :
      dst += ("None")
      return dst
  if((flags & FLAG_TRIMTEXT) != 0) :
    txt = string.strip(txt)
  nchars = len(txt)
  if((flags & FLAG_ELIDETEXT) != 0 and nchars > MAXTEXT) :
    nchars = MAXTEXT
    shortened = True
  dst += ('|')
  for i in range(0,nchars) :
    c = txt[i]
    nchars -= 1
    if(nchars < 0) : continue
    if((flags & FLAG_NOCR) != 0 and c == '\r') :
      continue
    elif((flags & FLAG_ESCAPE) != 0 and ord(c) < ord(' ')) :
      dst += ('\\')
      # switch (c)
      if(c == '\n') : dst += 'n'
      elif(c == '\r') : dst += 'r'
      elif(c == '\f') : dst += 'f'
      elif(c == '\t') : dst += 't'
      else  : # convert to octal
        uc = ord(c)
        oct = ((uc >> 6) & 077)
        dst += chr(ord('0')+ oct)
        oct = ((uc >> 3) & 077)
        dst += chr(ord('0')+ oct)
        oct = ((uc) & 077)
        dst += chr(ord('0')+ oct)
    else :
      dst += c
  if(shortened) :
      dst += ("...")
  dst += ('|')
  return dst
#end addtext


# Convert an org.w3c.dom.Node nodetype to a string for debugging.
# @param nodetype of interest
# @return String name corresponding to the nodetype

def nodetypeName(nodetype) :
  # switch (nodetype)
  if   (nodetype == ATTRIBUTE_NODE) : return "ATTRIBUTE_NODE"
  elif (nodetype == CDATA_SECTION_NODE) : return "CDATA_SECTION_NODE"
  elif (nodetype == COMMENT_NODE) : return "COMMENT_NODE"
  elif (nodetype == DOCUMENT_FRAGMENT_NODE) : return "DOCUMENT_FRAGMENT_NODE"
  elif (nodetype == DOCUMENT_NODE) : return "DOCUMENT_NODE"
  elif (nodetype == DOCUMENT_TYPE_NODE) : return "DOCUMENT_TYPE_NODE"
  elif (nodetype == ELEMENT_NODE) : return "ELEMENT_NODE"
  elif (nodetype == ENTITY_NODE) : return "ENTITY_NODE"
  elif (nodetype == ENTITY_REFERENCE_NODE) : return "ENTITY_REFERENCE_NODE"
  elif (nodetype == NOTATION_NODE) : return "NOTATION_NODE"
  elif (nodetype == PROCESSING_INSTRUCTION_NODE) : return "PROCESSING_INSTRUCTION_NODE"
  elif (nodetype == TEXT_NODE) : return "TEXT_NODE"
  else :
    return "UNDEFINED"
#end nodetypeName()

# Trace a SAX event token
def saxtrace(token, flags=DEFAULTFLAGS) :
  result = ""
  name = token.name
  value = token.value
  text = token.text
  event = token.event

  result += ("["+saxeventtype.tostring(event)+"] ")

  # switch(event)
  if (event == saxeventtype.STARTELEMENT
      or event == saxeventtype.ENDELEMENT) :
    result += (": element=|")
    result += (name)
    result += ("|")
  elif (event == saxeventtype.CHARACTERS) :
    result += (" text=")
    result = addtext(result,text,flags)
    trans = unescape(text)
    result += (" translation=")
    result = addtext(result,trans,flags)
  elif (event == saxeventtype.ATTRIBUTE) :
    result += (": name=")
    result = addtext(result,name,flags)
    result += (" value=")
    result = addtext(result,value,flags)
  elif (event == saxeventtype.STARTDOCUMENT) :
    pass
  elif (event == saxeventtype.ENDDOCUMENT) :
    pass
  else :
    assert False, "Unexpected event: "+str(event)
  return result
#end trace

# end class Util
