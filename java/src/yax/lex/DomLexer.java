/* Copyright 2009, UCAR/Unidata and OPeNDAP, Inc.
   See the COPYRIGHT file for more information. */

package yax.lex;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.CharArrayReader;
import java.util.Stack;

import static org.w3c.dom.Node.*;

/**
Walk a DOM tree in prefix order, left-to-right, depth first.
*/

public class DomLexer
{

    //////////////////////////////////////////////////
    // Constants and Enums

    static boolean TRACE = false;

    static public enum Error
    {
        OK("No error"),
        ENOMEM("Out of Memory"),
        EINVAL("Invalid argument"),
        EEOF("Unexpected EOF"),
        ECLOSURE("Expected /,>,or attribute name"),
        EGT("Expected >"),
        ELT("Expected <"),
        ENAMECHAR("Illegal leading name character"),
        ENATTR("Too many attributes"),
        EEQUAL("Expected '=' after attribute name"),
        EVALUE("Expected attribute value"),
        ECOMMENT("Comment is missing trailing '-->'"),
        ECDATA("Cdata is missing trailing ']]>'"),
        EPROLOG("Malformed <?xml...> prolog"),
        EDOCTYPE("Malformed <!DOCTYPE...>"),
        ENAMENULL("Name is zero length"),
        ETEXT("Non-whitespace text encountered");
    
        private final String message;
        Error(String msg) {this.message = msg;}
        public String getMessage()  { return message; }
    }

    //////////////////////////////////////////////////
    // Inner classes
    /**
     * Track the processing of a node in the DOM tree   
     */

    static class State
    {
	boolean started = false;
	Node node = null;
        NodeList children = null;
        int nchild = 0;
        int ichild = 0;

        NamedNodeMap attributes = null;
        int nattr = 0;
        int iattr = 0;

	State() {};

        State(Node node)
        {
            assert(node != null);
	    this.node = node;
	    this.children = node.getChildNodes();
	    this.nchild = (this.children == null ? 0 : this.children.getLength());
	    this.ichild = 0;
	    this.attributes = node.getAttributes();
	    this.nattr = (this.attributes == null ? 0 : this.attributes.getLength());
	    this.iattr = 0;
	}
    }

    //////////////////////////////////////////////////
    // Instance variables

    Stack<State> path = new Stack<State>();
    State state = null;

    String input = null;
    Document document = null;
    
    int flags = Util.FLAG_NONE; // in case we are tracing

    //////////////////////////////////////////////////
    // Constructors
    
    // Constructor(s)
    public DomLexer(String input)
        throws Exception
    {
        this.input = input;
        document = buildDom(input); // create DOM tree from input
        if(document == null)
	    throw new Exception("DomLexer.ctor(): no document");
        path.push(new State(document));
    }
    
    public DomLexer(Document document)
            throws Exception
    {
	if(document == null)
	    throw new Exception("DomLexer.ctor(): no document");
        this.document = document;
        path.push(new State(document));
    }

    //////////////////////////////////////////////////
    // Get/Set

    public void setFlags(int flags)
    {
        this.flags = flags;
    }

    //////////////////////////////////////////////////
    // API

    /**
     * Entry point for the scanner. Returns a Yax token type
     * and (via an array) the current node.
     * @return the yax.lex.Type corresponding to the next token
     *         and fill in token
     */
    public Type
    nextToken(Node[] nodep)
        throws Exception
    {
	Node node = null;
        Type tokentype = Type.UNDEFINED;

	while(tokentype == Type.UNDEFINED) {
	    if(state == null) {
		if(path.empty()) {
		    tokentype = Type.EOF;
		    break;
		}
		state = path.pop();
	    }		    	
	    assert state != null;

            short ntype = state.node.getNodeType();
            String ntypename = Util.nodetypeName(ntype);

	    if(!state.started) {
		state.started = true;
		node = state.node;
	        tokentype = node2token(node.getNodeType());
		break;
	    } else if(state.iattr < state.nattr) {
		node = state.attributes.item(state.iattr++);
	        tokentype = node2token(node.getNodeType());
	    } else if(state.ichild < state.nchild) {
		path.push(state);
		node = state.children.item(state.ichild++);
                state = new State(node); // moving left to right
                // do not set tokentype so we will loop again
	    } else { // we have completed processing of this node;
	             // return close if it matches an element
                     // otherwise try again
		node = state.node;
                if(node.getNodeType() == ELEMENT_NODE)
		    tokentype = Type.CLOSE;
		state = null; // cause path to be popped
	    }
	}
	if(nodep != null) nodep[0] = node;
	return tokentype;
    }
    
    //////////////////////////////////////////////////
    // Misc.

    boolean
    isEmptyElement(Node element, boolean whitespaceok)
	throws DOMException
    {
	assert element.getNodeType() == Node.ELEMENT_NODE;
	if(!whitespaceok) {
	    return element.getChildNodes().getLength() == 0;
	}
	boolean empty = true;
	NodeList children = element.getChildNodes();
	int len = children.getLength();
	for(int i=0;i<len && empty;i++) {
	    Node node = children.item(i);
	    switch(node.getNodeType()) {
	    case ELEMENT_NODE:
	    case CDATA_SECTION_NODE:
		empty = false;
		break;
	    case TEXT_NODE:
		// See if this text is whitespace
		boolean iswhite = true;
                String value = node.getNodeValue();
		for(int j=0;i<value.length();i++) {
                    char c = value.charAt(j);
		    iswhite = (c == '\0' ||  c > ' ');
		    if(!iswhite) break;
		}
		if(iswhite) continue;
		empty = false;
		break;
	    default: break; // ignore
	    }
	}
        return empty;
    }

    Document
    buildDom(String input)
        throws Exception
    {
	CharArrayReader src = new CharArrayReader(input.toCharArray());
	DocumentBuilderFactory docfactory = newFactory();
	DocumentBuilder builder = docfactory.newDocumentBuilder();
	Document document = builder.parse(new InputSource(src));
	return document;    
    }
    
    static DocumentBuilderFactory
    newFactory()
        throws java.lang.Exception
    {
	DocumentBuilderFactory docfactory = DocumentBuilderFactory.newInstance();
	docfactory.setValidating(false);
	docfactory.setValidating(false);
	docfactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
	
	if(false) {
	docfactory.setFeature("http://xml.org/sax/features/validation", false);
	docfactory.setNamespaceAware(false);
	docfactory.setFeature("http://xml.org/sax/features/namespaces", false);
	docfactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
	}
	return docfactory;
    }

   /**
     Convert an org.w3c.dom.Node nodetype to a yax.lex.Type enum.
     @param nodetype of interest
     @return yax.lex.Type enu,
     */
    static public Type node2token(short nodetype)
    {
	switch (nodetype) {
	case ATTRIBUTE_NODE: return Type.ATTRIBUTE;
	case CDATA_SECTION_NODE: return Type.CDATA;
	case COMMENT_NODE: return Type.COMMENT;
	case DOCUMENT_NODE: return Type.DOCTYPE;
	case ELEMENT_NODE: return Type.OPEN;
	case TEXT_NODE: return Type.TEXT;

	case DOCUMENT_FRAGMENT_NODE:
	case DOCUMENT_TYPE_NODE:
	case ENTITY_NODE:
	case ENTITY_REFERENCE_NODE:
	case NOTATION_NODE:
	case PROCESSING_INSTRUCTION_NODE:
	default : break;
	}
        return Type.UNDEFINED;
    }

} // class DomLexer return Type.DomLexe;
