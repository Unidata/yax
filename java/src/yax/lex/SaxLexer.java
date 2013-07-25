/* Copyright 2009, UCAR/Unidata and OPeNDAP, Inc.
   See the COPYRIGHT file for more information. */

package yax.lex;


import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

abstract public class SaxLexer extends DefaultHandler
{
    //////////////////////////////////////////////////
    // Constants
    static Charset UTF8 = Charset.forName("UTF-8");

    static final String LOAD_EXTERNAL_DTD
	= "http://apache.org/xml/features/nonvalidating/load-external-dtd";

    //////////////////////////////////////////////////
    // static types

    //////////////////////////////////////////////////
    // static fields

    //////////////////////////////////////////////////
    // Instance variables
    
    String document;
    
    // Sax parser state
    Locator locator = null;
    SAXParserFactory spf = null;
    SAXParser parser = null;
    ByteArrayInputStream input = null;

    // Lexer state
    List<SaxToken> tokens = null;
    SaxToken token = null; // last token constructed

    //////////////////////////////////////////////////
    // Constructor(s)

    public SaxLexer(String document)
	throws LexException
    {
	try {
	    spf = SAXParserFactory.newInstance();
	    spf.setValidating(false);
            spf.setNamespaceAware(true);
	    spf.setFeature(LOAD_EXTERNAL_DTD,false);
	    parser = spf.newSAXParser();
	} catch (Exception e) {
	    throw new LexException(e);
	}
	// Set up for the parse
	input = new ByteArrayInputStream(document.getBytes(UTF8));
	tokens = new ArrayList<SaxToken>();
    }

    //////////////////////////////////////////////////
    // Abstract methods

    // get list of legal attribute names in desired order
    abstract public String[] orderedAttributes(String element);

    //////////////////////////////////////////////////
    // Get/Set

    public int getTokenCount() {return tokens.size();}

    public List<SaxToken> getTokens() {return tokens;}

    public SaxToken[] getTokens(SaxToken[] tokenarray)
    {
	return tokens.toArray(tokenarray);
    }

    //////////////////////////////////////////////////
    // Misc.
    void fail(String msg)
	throws SAXException
    {
	if(locator != null) {
	    msg = String.format("[%d.%d] ",
			locator.getLineNumber(),
			locator.getColumnNumber())
		  + msg;
	}
	throw new SAXException(msg);	
    }

    //////////////////////////////////////////////////
    // Public API

    public void lex()
	throws LexException
    {
	try {
	    parser.parse(input,this);
	} catch (IOException ioe) {
	    throw new LexException(ioe);
	} catch (SAXException se) {
	    throw new LexException(se);
	}
    }

    //////////////////////////////////////////////////
    // DefaultHandler Overrides

    @Override
    public void setDocumentLocator(Locator locator)
    {
	this.locator = locator;
    }

    @Override
    public void startDocument()
	throws SAXException
    {
	tokens.clear();
	tokens.add(new SaxToken(Type.DOCTYPE));
    }

    @Override
    public void endDocument()
	throws SAXException
    {
    }

    @Override
    public void startElement(String nsuri, String name, String qualname,
		      Attributes attributes)
	throws SAXException
    {
	token = new SaxToken(Type.OPEN,name,qualname,nsuri);
	tokens.add(token);
	// Let subclass tell us what and in what order
	// to generate the attributes
	String[] userorder = orderedAttributes(name);
	int nattr = attributes.getLength();
	if(userorder != null) {
	    for(String uname: userorder) {
	        String value = null;
	        for(int i=0;i<nattr;i++) {
		    String aname = attributes.getLocalName(i);
		    if("".equals(aname)) aname = attributes.getQName(i);
		    if(uname.equals(aname)) {
		        value = attributes.getValue(i);
		        break;
		    }
		}
		if(value != null) {// found it
		    token = new SaxToken(Type.ATTRIBUTE,uname);
		    token.value = value;
		    tokens.add(token);
		}		
	    }
	} else {// no attribute ordering; just include as is
	    for(int i=0;i<nattr;i++) {
		String aname = attributes.getLocalName(i);
		if("".equals(aname)) aname = attributes.getQName(i);
		String value = attributes.getValue(i);
		token = new SaxToken(Type.ATTRIBUTE,aname);
	        token.value = value;
		tokens.add(token);
	    }
	}
    }

    @Override
    public void endElement(String nsuri, String name, String qualname)
	throws SAXException
    {
	token = new SaxToken(Type.CLOSE,name,qualname,nsuri);
	tokens.add(token);
    }

    @Override
    public void characters(char[] ch, int start, int length)
	throws SAXException
    {
	token = new SaxToken(Type.TEXT);
	token.text = new String(ch,start,length);
	tokens.add(token);
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length)
	throws SAXException
    {
	// should never see this since not validating, but ignore
    }

    // Error handling

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
	System.err.println("Sax error: "+e);	
    }

    @Override
    public void warning(SAXParseException e)
	throws SAXException
    {
	System.err.println("Sax error: "+e);	
    }

} // class SaxLexer
