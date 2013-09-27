/* Copyright 2009, UCAR/Unidata and OPeNDAP, Inc.
   See the COPYRIGHT file for more information. */

package yax.lex;


import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

abstract public class SaxEventHandler extends DefaultHandler
{
    //////////////////////////////////////////////////
    // Constants

    static boolean TRACE = false;

    static Charset UTF8 = Charset.forName("UTF-8");

    static final String LOAD_EXTERNAL_DTD
	= "http://apache.org/xml/features/nonvalidating/load-external-dtd";

    //////////////////////////////////////////////////
    // static types

    //////////////////////////////////////////////////
    // static fields

    //////////////////////////////////////////////////
    // Instance variables
    
    String document = null;
    
    // Sax parser state
    Locator locator = null;
    SAXParserFactory spf = null;
    SAXParser saxparser = null;
    ByteArrayInputStream input = null;

    //////////////////////////////////////////////////
    // Constructor(s)

    public SaxEventHandler(String document)
	throws SAXException
    {
	this.document = document;
    }

    //////////////////////////////////////////////////
    // Abstract method(s)

    // Send the lexeme to the the subclass to process
    abstract public void yyevent(SaxEvent token) throws SAXException;

    //////////////////////////////////////////////////
    // Get/Set

    //////////////////////////////////////////////////
    // Public API

    public boolean parse()
	throws Exception
    {
        // Create the sax parser that will drive us with events
        spf = SAXParserFactory.newInstance();
        spf.setValidating(false);
        spf.setNamespaceAware(true);
        spf.setFeature(LOAD_EXTERNAL_DTD,false);
        saxparser = spf.newSAXParser();
        // Set up for the parse
        input = new ByteArrayInputStream(document.getBytes(UTF8));
        try {
            saxparser.parse(input,this);
            return true;
        } catch (SAXException se) {return false;}
    }

    //////////////////////////////////////////////////
    // DefaultHandler Overrides

    // The key thing to note is that we map the Sax Event
    // into a subset based on yax.lex.DomEventType. This means
    // we feed only a subset of the possible events into
    // the subclass handler. This can be changed by
    // overriding the suppressing event handler below.

    @Override
    public void setDocumentLocator(Locator locator)
    {
	this.locator = locator;
    }

    @Override
    public void startDocument()
	throws SAXException
    {
	SaxEvent token = new SaxEvent(SaxEventType.STARTDOCUMENT,locator);
if(TRACE) System.err.printf("event.%s: %s\n",token.event.name(),token.toString());
	yyevent(token);
    }

    @Override
    public void endDocument()
	throws SAXException
    {
	SaxEvent token = new SaxEvent(SaxEventType.ENDDOCUMENT,locator);
if(TRACE) System.err.printf("event.%s: %s\n",
		token.event.name(),token.toString());
	yyevent(token);
    }

    @Override
    public void startElement(String nsuri, String name, String qualname,
		      Attributes attributes)
	throws SAXException
    {
	SaxEvent token = new SaxEvent(SaxEventType.STARTELEMENT,locator,name,qualname,nsuri);
if(TRACE) System.err.printf("event.%s: %s\n",
		token.event.name(),token.toString());
	yyevent(token);
	// Now pass the attributes as tokens
	int nattr = attributes.getLength();
        for(int i=0;i<nattr;i++) {
		String aname = attributes.getLocalName(i);
		if("".equals(aname)) aname = attributes.getQName(i);
		String value = attributes.getValue(i);
		token = new SaxEvent(SaxEventType.ATTRIBUTE,locator,aname);
	        token.value = value;
if(TRACE) System.err.printf("event.%s: %s\n",
		token.event.name(),token.toString());
	        yyevent(token);
	}
    }

    @Override
    public void endElement(String nsuri, String name, String qualname)
	throws SAXException
    {
	SaxEvent token = new SaxEvent(SaxEventType.ENDELEMENT,locator,name,qualname,nsuri);
if(TRACE) System.err.printf("event.%s: %s\n",
	    token.event.name(),token.toString());
	yyevent(token);
    }

    @Override
    public void characters(char[] ch, int start, int length)
	throws SAXException
    {
	SaxEvent token = new SaxEvent(SaxEventType.CHARACTERS,locator);
	token.text = new String(ch,start,length);
if(TRACE) System.err.printf("event.%s: %s\n",
		token.event.name(),token.toString());
	yyevent(token);
    }

    // Following events are suppressed

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length)
	throws SAXException
    {
	// should never see this since not validating
	return;
    }

    @Override
    public void endPrefixMapping(String prefix)
	throws SAXException
    {
	return;
    }

    @Override
    public void notationDecl(String name, String publicId, String systemId)
	throws SAXException
    {
	return;
    }

    @Override
    public void processingInstruction(String target, String data)
	throws SAXException
    {
	return;
    }

    @Override
    public void skippedEntity(String name)
	throws SAXException
    {
	return;
    }

    @Override
    public void startPrefixMapping(String prefix, String uri)
	throws SAXException
    {
	return;
    }

    @Override
    public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName)
	throws SAXException
    {
	return;
    }

    //////////////////////////////////////////////////
    // Entity resolution

    @Override
    public InputSource resolveEntity(String publicId, String systemId)
    {
if(TRACE) System.err.printf("event.RESOLVEENTITY: %s.%s\n",
		publicId,systemId);
	return null;
    }

    //////////////////////////////////////////////////
    // Error handling Events

    @Override
    public void fatalError(SAXParseException e)
	throws SAXException
    {
	throw e;
    }	

    @Override
    public void error(SAXParseException e)
	throws SAXException
    {
	System.err.println("Sax error: %s\n"+e);
    }

    @Override
    public void warning(SAXParseException e)
	throws SAXException
    {
	System.err.println("Sax warning: "+e);
    }



} // class SaxEventHandler
