# Copyright 2013, UCAR/Unidata.
# See the COPYRIGHT file for more information.

# Define a class to hold information provided to each kind of event

import saxeventtype

class SaxEvent:

  def __init__(self, event, locator=None, name=None, fullname=None, uri=None,
               text=None, value=None):
    self.event = event
    self.locator = locator
    self.name = name
    self.fullname = fullname
    self.namespace = uri
    self.text = text  # for text
    self.value = value # for attributes
    self.publicid = None
    self.systemid = None
  # end __init__

  def __str__(self) :
    if self.event == None :
      text = "undefined"
    else :
      text = saxeventtype.tostring(self.event)
    return text
  # end __str__

# class SaxEvent

