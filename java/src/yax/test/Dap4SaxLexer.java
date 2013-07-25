/* Copyright 2009, UCAR/Unidata and OPeNDAP, Inc.
   See the COPYRIGHT file for more information. */

package yax.test;

import yax.lex.*;

import org.xml.sax.*;

import java.io.IOException;
import java.util.*;

import static yax.test.Dap4Parser.*;

public class Dap4SaxLexer extends SaxLexer implements Dap4Parser.Lexer
{
    //////////////////////////////////////////////////
    // Constants

    //////////////////////////////////////////////////
    // static types

    //////////////////////////////////////////////////
    // static fields

    static Map<String,Lexeme> elementmap;
    static Map<String,Lexeme> attributemap;

    static {
        elementmap = new HashMap<String,Lexeme>();
        attributemap = new HashMap<String, Lexeme>();
	elementmap.put("Group",
			new Lexeme("Group",GROUP_,_GROUP,
			new String[]{"name","dapversion","ddxversion","ns","base","xmlns"}));
	elementmap.put("Enumeration",
			new Lexeme("Enumeration",ENUMERATION_,_ENUMERATION,
			new String[]{"name","basetype"}));
	elementmap.put("EnumConst",
			new Lexeme("EnumConst",ENUMCONST_,_ENUMCONST,
			new String[]{"name","value"}));
	elementmap.put("Namespace",
			new Lexeme("Namespace",NAMESPACE_,_NAMESPACE,
			new String[]{"href"}));
	elementmap.put("Dimension",
			new Lexeme("Dimension",DIMENSION_,_DIMENSION,
			new String[]{"name","size"}));
	elementmap.put("Dim",
			new Lexeme("Dim",DIM_,_DIM,
			new String[]{"name","size"}));
	elementmap.put("Enum",
			new Lexeme("Enum",ENUM_,_ENUM,
			new String[]{"enum","name"}));
	elementmap.put("Map",
			new Lexeme("Map",MAP_,_MAP,
			new String[]{"name"}));
	elementmap.put("Structure",
			new Lexeme("Structure",STRUCTURE_,_STRUCTURE,
			new String[]{"name"}));
	elementmap.put("Value",
			new Lexeme("Value",VALUE_,_VALUE,
			new String[]{"value"}));
	elementmap.put("Attribute",
			new Lexeme("Attribute",ATTRIBUTE_,_ATTRIBUTE,
			new String[]{"name","type","namespace"}));

	elementmap.put("Char",
			new Lexeme("Char",CHAR_,_CHAR,
			new String[]{"name"}));
	elementmap.put("Byte",
			new Lexeme("Byte",BYTE_,_BYTE,
			new String[]{"name"}));
	elementmap.put("Int8",
			new Lexeme("Int8",INT8_,_INT8,
			new String[]{"name"}));
	elementmap.put("UInt8",
			new Lexeme("UInt8",UINT8_,_UINT8,
			new String[]{"name"}));
	elementmap.put("Int16",
			new Lexeme("Int16",INT16_,_INT16,
			new String[]{"name"}));
	elementmap.put("UInt16",
			new Lexeme("UInt16",UINT16_,_UINT16,
			new String[]{"name"}));
	elementmap.put("Int32",
			new Lexeme("Int32",INT32_,_INT32,
			new String[]{"name"}));
	elementmap.put("UInt32",
			new Lexeme("UInt32",UINT32_,_UINT32,
			new String[]{"name"}));
	elementmap.put("Int64",
			new Lexeme("Int64",INT64_,_INT64,
			new String[]{"name"}));
	elementmap.put("UInt64",
			new Lexeme("UInt64",UINT64_,_UINT64,
			new String[]{"name"}));
	elementmap.put("Float32",
			new Lexeme("Float32",FLOAT32_,_FLOAT32,
			new String[]{"name"}));
	elementmap.put("Float64",
			new Lexeme("Float64",FLOAT64_,_FLOAT64,
			new String[]{"name"}));
	elementmap.put("String",
			new Lexeme("String",STRING_,_STRING,
			new String[]{"name"}));
	elementmap.put("URL",
			new Lexeme("URL",URL_,_URL,
			new String[]{"name"}));
	elementmap.put("Opaque",
			new Lexeme("Opaque",OPAQUE_,_OPAQUE,
			new String[]{"name"}));

	attributemap.put("base",new Lexeme("base",ATTR_BASE));
	attributemap.put("basetype",new Lexeme("basetype",ATTR_BASETYPE));
	attributemap.put("dapversion",new Lexeme("dapversion",ATTR_DAPVERSION));
	attributemap.put("ddxversion",new Lexeme("ddxversion",ATTR_DDXVERSION));
	attributemap.put("enum",new Lexeme("enum",ATTR_ENUM));
	attributemap.put("href",new Lexeme("href",ATTR_HREF));
	attributemap.put("name",new Lexeme("name",ATTR_NAME));
	attributemap.put("namespace",new Lexeme("namespace",ATTR_NAMESPACE));
	attributemap.put("size",new Lexeme("size",ATTR_SIZE));
	attributemap.put("type",new Lexeme("type",ATTR_TYPE));
	attributemap.put("value",new Lexeme("value",ATTR_VALUE));
	attributemap.put("ns",new Lexeme("ns",ATTR_NS));
	attributemap.put("xmlns",new Lexeme("xmlns",ATTR_XMLNS));

    };



    //////////////////////////////////////////////////
    // Instance variables
    
    String document = null;
    SaxLexer saxlexer = null;

    SaxToken[] tokens = null;
    int ntokens = 0;
    int next = 0;
    
    boolean initialized = false;

    SaxToken lval = null;

    //////////////////////////////////////////////////
    // Constructor(s)

    public Dap4SaxLexer(String document)
	throws LexException
    {
	super(document);
    }

    //////////////////////////////////////////////////
    // Abstract method overrides

    public String[] orderedAttributes(String elementname)
    {
	Lexeme lexeme = elementmap.get(elementname);
	if(lexeme == null)
	    return null;
	return lexeme.attributesInOrder;
    }

    //////////////////////////////////////////////////
    // Bison Parser Lex interface

    public Object getLVal() {return lval;}

    // Probable subclass override
    public void yyerror(String msg) {System.err.println(msg);}

    public int
    yylex()
	throws LexException
    {
	if(!initialized) initialize();
			
	int bisontoken = -1;
	while(bisontoken < 0) {
	    Lexeme lexeme = null;
	    if(next >= ntokens)
	        return 0; // signals eof
	    lval = tokens[next++];    
	    switch (lval.type) {

	    case OPEN:
	        lexeme = elementmap.get(lval.name);
		if(lexeme == null)
		    throw new LexException("Unknown element: "+lval.name);
		bisontoken = lexeme.open;
		break;

	    case CLOSE:
	        lexeme = elementmap.get(lval.name);
		if(lexeme == null)
		    throw new LexException("Unknown element: "+lval.name);
		bisontoken = lexeme.close;
		break;

	    case ATTRIBUTE:
	        lexeme = attributemap.get(lval.name);
		if(lexeme == null)
		    throw new LexException("Unknown attribute: "+lval.name);
		bisontoken = lexeme.atoken;
		break;

	    case CDATA:
	    case TEXT:
                // Suppress white space
                String text = lval.text.trim();
                if(text != null && text.length() > 0)
		    bisontoken = TEXT;
		break;

	    default:
		break;
	    }
	}
	return bisontoken;
    }

    //////////////////////////////////////////////////
    // Protected

    void initialize()
	throws LexException
    {
        super.lex();
	ntokens = super.getTokenCount();
	tokens = super.getTokens(new SaxToken[ntokens]);
	next = 0;
	initialized = true;    
    }
} // class BisonLexer
