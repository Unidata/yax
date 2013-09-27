# Copyright 2009, UCAR/Unidata
#   See the COPYRIGHT file for more information.

# Probably can get rid of this class
class KeywordMap:

  def __init__() :
    keywords = {}

  def addKeyword(keyword):
    keywords += {keyword.name:keyword}

  def add(name, opentag, closetag, attrtag) :
    addKeyword(Keyword(name,opentag,closetag,attrtag))

  def get(name):
    if(name == None) : return None
    return keywords[name]
# end class KeywordMap

class Keyword:
  def __init__(name, opentag, closetag, attrtag) :
    self.name = name
    self.opentag = opentag
    self.closetag = closetag
    self.attrtag = attrtag

#end class Keyword
