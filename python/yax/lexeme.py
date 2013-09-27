# Copyright 2009, UCAR/Unidata
#   See the COPYRIGHT file for more information.

class Lexeme:

  def __init__(self, name=None, open=None, close=None, legal=None, attr=None) :
    self.name = name
    self.open = open
    self.close = close
    self.attr = attr
    self.setAttributes(legal)
  # end __init__

  def setAttributes(self,alist):
    self.legalAttributes = alist
    if(self.legalAttributes != None) :
      # check for duplicates
      for i in range(0,len(self.legalAttributes)) :
        for j in range(i+1,len(self.legalAttributes)) :
          assert (self.legalAttributes[i] != self.legalAttributes[j])
  #end attribute

  def __str__(self) :
    text = "{0} open={1} close={2} a={3}".format(self.name,str(self.open),str(self.close),str(self.attr))
    if(self.legalAttributes != None and len(self.legalAttributes) > 0) :
      text += " attributes="
      for s in self.legalAttributes :
        text += " " + s
    return text
  #end __str__

# end class Lexeme

