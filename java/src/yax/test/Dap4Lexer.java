package yax.test;

import org.w3c.dom.Node;
import yax.lex.DomLexer;
import yax.lex.Type;
import yax.lex.Util;

import java.io.*;
import java.util.Stack;
import java.util.List;

import static yax.test.Dap4Parser.*;

class Dap4Lexer implements Dap4Parser.Lexer
{

    //////////////////////////////////////////////////
    // Instance Variables
    DomLexer lexer = null;
    int flags = Util.FLAG_NONE;
    Node lval = null;
    KeywordMap keywords = new KeywordMap();
    boolean textok = false;

    //////////////////////////////////////////////////
    // Constructor(s)

    public Dap4Lexer(String input, int flags)
            throws Exception
    {
	lexer = new DomLexer(input);
        this.flags = flags;
	lexer.setFlags(flags);
	defineKeywords(keywords);
    }

    //////////////////////////////////////////////////
    // Bison yylex interface

    /**
     * Method to retrieve the semantic value of the last scanned token.
     *
     * @return the semantic value of the last scanned token.
     */
    public Object getLVal()
    {
        return this.lval;
    }

    /**
     * Entry point for error reporting.  Emits an error
     * in a user-defined way.
     *
     * @param msg The string for the error message.
     */
        public void yyerror(String msg)
        {
	    System.err.printf("%s\n",msg);
        }

    /**
     * Entry point for the scanner.
     * Returns the token identifier corresponding to the
     * next token and prepares to return the semantic value
     * of the token.
     * @return the token identifier corresponding to the next token.
     */
    
    public int
    yylex()
            throws Exception
    {
	int yytoken = UNKNOWN;
	String name;
	KeywordMap.Keyword keyword;
        Node[] nodep = new Node[]{null};
        Type yaxtoken = Type.UNDEFINED;

	while(yytoken == UNKNOWN) {
	    yaxtoken = lexer.nextToken(nodep);
	    lval = nodep[0];

	    if((flags & Util.FLAG_TRACE)!=0) {
	        String trace = Util.trace(yaxtoken, lval);
	        System.err.printf("yylex: %s\n",trace);
	    }
    
	    short ttype = lval.getNodeType();
            String ttypename = Util.nodetypeName(ttype);
            name = lval.getNodeName();
	    keyword = keywords.get(name);
    
	    switch(yaxtoken) {
    
	    case OPEN: 
		if(keyword == null) {// undefined
		    yytoken = UNKNOWN_ELEMENT_;		    
		} else {
		    yytoken = keyword.opentag;
		    // Check for the special case of <Value> */
		    if(yytoken == VALUE_)
		        textok = true;
		}
		break;
    
	    case CLOSE:
		if(keyword == null) {// undefined
		    yytoken = _UNKNOWN_ELEMENT;
		} else {
	  	    yytoken = keyword.closetag;
		    textok = false;
		}
		break;
    
	    case ATTRIBUTE:
		if(keyword == null) {
		    yytoken = UNKNOWN_ATTR;
		} else {
		    yytoken = keyword.attrtag;
		}
		break;
    
	    case CDATA:
	    case TEXT:
		if(textok)
		    yytoken = TEXT;
		break;
    
	    case COMMENT:
	    case PROLOG:
	    case DOCTYPE:
		break; // ignore
    
	    case EOF:
		yytoken = EOF;
		break;
    
	    default:
	        yyerror(String.format("unknown token type: %s\n",
			yaxtoken.name()));
		yytoken = ERROR;
		break;
	    } // switch
	} // while
	return yytoken;
    } // yylex

    void
    defineKeywords(KeywordMap keywords)
    {
        // Fill in the keyword map
        keywords.add("Attribute", ATTRIBUTE_, _ATTRIBUTE, 0);
        keywords.add("Dim", DIM_, _DIM, 0);
        keywords.add("Dimension", DIMENSION_, _DIMENSION, 0);
        keywords.add("EnumConst", ENUMCONST_, _ENUMCONST, 0);
        keywords.add("Enumeration", ENUMERATION_, _ENUMERATION, 0);
        keywords.add("Group", GROUP_, _GROUP, 0);
        keywords.add("Map", MAP_, _MAP, 0);
        keywords.add("Namespace", NAMESPACE_, _NAMESPACE, 0);
        keywords.add("Structure", STRUCTURE_, _STRUCTURE, 0);
        keywords.add("Value", VALUE_, _VALUE, 0);
        keywords.add("Char", CHAR_, _CHAR, 0);
        keywords.add("Byte", BYTE_, _BYTE, 0);
        keywords.add("Int8", INT8_, _INT8, 0);
        keywords.add("UInt8", UINT8_, _UINT8, 0);
        keywords.add("Int16", INT16_, _INT16, 0);
        keywords.add("UInt16", UINT16_, _UINT16, 0);
        keywords.add("Int32", INT32_, _INT32, 0);
        keywords.add("UInt32", UINT32_, _UINT32, 0);
        keywords.add("Int64", INT64_, _INT64, 0);
        keywords.add("UInt64", UINT64_, _UINT64, 0);
        keywords.add("Float32", FLOAT32_, _FLOAT32, 0);
        keywords.add("Float64", FLOAT64_, _FLOAT64, 0);
        keywords.add("String", STRING_, _STRING, 0);
        keywords.add("URL", URL_, _URL, 0);
        keywords.add("Opaque", OPAQUE_, _OPAQUE, 0);
        keywords.add("Enum", ENUM_, _ENUM, 0);
        keywords.add("base", 0, 0, ATTR_BASE);
        keywords.add("basetype", 0, 0, ATTR_BASETYPE);
        keywords.add("dapVersion", 0, 0, ATTR_DAPVERSION);
        keywords.add("ddxVersion", 0, 0, ATTR_DDXVERSION);
        keywords.add("enum", 0, 0, ATTR_ENUM);
        keywords.add("href", 0, 0, ATTR_HREF);
        keywords.add("name", 0, 0, ATTR_NAME);
        keywords.add("ns", 0, 0, ATTR_NS);
        keywords.add("size", 0, 0, ATTR_SIZE);
        keywords.add("type", 0, 0, ATTR_TYPE);
        keywords.add("value", 0, 0, ATTR_VALUE);
        keywords.add("xmlns", 0, 0, ATTR_XMLNS);
    }

} // class Dap4Lexer



    
