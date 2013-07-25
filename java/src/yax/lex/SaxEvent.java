/* Copyright 2009, UCAR/Unidata and OPeNDAP, Inc.
   See the COPYRIGHT file for more information. */

package yax.lex;

import org.xml.sax.Locator;

/**
Define a class to hold information provided to each kind of event
*/
public class SaxEvent
{
    public Type type = null;
    public SaxEventType event = null;
    public String name = null;
    public String fullname = null;
    public String namespace = null;

    public String value = null; // for attributes
    public String text = null;  // for text

    public String publicid = null;
    public String systemid = null;

    public Locator locator = null;

    public SaxEvent(SaxEventType event, Locator locator)
    {
	this.event = event;
	this.locator = locator;
	this.type = event.getType();
    }

    public SaxEvent(SaxEventType event, Locator locator, String name)
    {
        this(event,locator);
	this.name=name;
    }

    public SaxEvent(SaxEventType event, Locator locator,
                    String name, String fullname, String uri)
    {
	this(event,locator,name);
	this.fullname = fullname;
	this.namespace = uri;
    }

    public String toString()
    {
	String text = (type == null ? "undefined" : type.toString());
	return text;
    }

} // class SaxEvent

