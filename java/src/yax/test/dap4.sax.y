%define parser_class_name {Dap4SaxParser}
%define extends {Dap4SaxTest}
%define api.push-pull push

%code lexer {
public Object getLVal() {return null;}
public int yylex() {return 0;}
public void yyerror(String s) {System.err.println(s);}
}

/* Following should be same for both sax and dom parsers */
%language "Java"
%debug
%error-verbose
%define public
%define package {yax.test}
%define throws {Exception}
%define lex_throws {Exception}

%token  DATASET_ _DATASET
%token  GROUP_ _GROUP
%token  ENUMERATION_ _ENUMERATION
%token  ENUMCONST_ _ENUMCONST
%token  NAMESPACE_ _NAMESPACE
%token  DIMENSION_ _DIMENSION
%token  DIM_ _DIM
%token  ENUM_ _ENUM
%token  MAP_ _MAP
%token  STRUCTURE_ _STRUCTURE
%token  VALUE_ _VALUE
%token  ATTRIBUTE_ _ATTRIBUTE

/* atomictype lexemes */
%token  CHAR_ _CHAR
%token  BYTE_ _BYTE
%token  INT8_ _INT8
%token  UINT8_ _UINT8
%token  INT16_ _INT16
%token  UINT16_ _UINT16
%token  INT32_ _INT32
%token  UINT32_ _UINT32
%token  INT64_ _INT64
%token  UINT64_ _UINT64
%token  FLOAT32_ _FLOAT32
%token  FLOAT64_ _FLOAT64
%token  STRING_ _STRING
%token  URL_ _URL
%token  OPAQUE_ _OPAQUE

/* Standard attributes */
%token  ATTR_BASE ATTR_BASETYPE ATTR_DAPVERSION ATTR_DDXVERSION
%token  ATTR_ENUM ATTR_HREF ATTR_NAME ATTR_NAMESPACE
%token  ATTR_SIZE ATTR_TYPE ATTR_VALUE 
%token  ATTR_NS ATTR_XMLNS
%token  UNKNOWN_ATTR UNKNOWN_ELEMENT_ _UNKNOWN_ELEMENT

%token  TEXT

/* Error cases */
%token  ERROR UNKNOWN UNEXPECTED

%start dataset

%%
dataset:
	DATASET_ group_attr_list group_body _DATASET
	;

group:
	GROUP_ group_attr_list group_body _GROUP
	;

group_attr_list:
	  /*empty*/
	| group_attr_list ATTR_NAME
	| group_attr_list ATTR_DAPVERSION
	| group_attr_list ATTR_DDXVERSION
	| group_attr_list ATTR_NS
	| group_attr_list ATTR_BASE
	| group_attr_list ATTR_XMLNS
	| group_attr_list UNKNOWN_ATTR
	;

group_body:
	  /*empty*/
	| group_body enumdef
	| group_body dimdef
	| group_body variable
	| group_body metadata
	| group_body group
	;

enumdef:
	ENUMERATION_ enum_attr_list enumconst_list _ENUMERATION
	;

enum_attr_list:
	ATTR_NAME ATTR_BASETYPE
	| ATTR_BASETYPE ATTR_NAME 
	;

enumconst_list:
	  enumconst
	| enumconst_list enumconst
	;


enumconst:
	ENUMCONST_ enumconst_attr_list _ENUMCONST
	;
	
enumconst_attr_list:
	  ATTR_NAME ATTR_VALUE
	| ATTR_VALUE ATTR_NAME
	;

namespace_list: 
	  /*empty*/
	| namespace_list namespace
	;

namespace:
	NAMESPACE_ ATTR_HREF _NAMESPACE
	;

dimdef:
	DIMENSION_ dimdef_attr_list metadatalist _DIMENSION
	;

dimdef_attr_list:
	  ATTR_NAME ATTR_SIZE
	| ATTR_SIZE ATTR_NAME
	;

dimref:
	DIM_ dimref_attr_list _DIM
	;

dimref_attr_list:
	  ATTR_NAME
	| ATTR_SIZE
	;

variable:
	  simplevariable
	| structurevariable
	;

/* Use atomic type to avoid rule explosion */
simplevariable:
	atomictype_ ATTR_NAME variabledef _atomictype
	;


atomictype_:
	  CHAR_
	| BYTE_
	| INT8_
	| UINT8_
	| INT16_
	| UINT16_
	| INT32_
	| UINT32_
	| INT64_
	| UINT64_
	| FLOAT32_
	| FLOAT64_
	| STRING_
	| URL_
	| OPAQUE_
	| ENUM_ ATTR_ENUM
	;

_atomictype:
	  _CHAR
	| _BYTE
	| _INT8
	| _UINT8
	| _INT16
	| _UINT16
	| _INT32
	| _UINT32
	| _INT64
	| _UINT64
	| _FLOAT32
	| _FLOAT64
	| _STRING
	| _URL
	| _OPAQUE
	| _ENUM
	;

variabledef:
	  /*empty*/
	| variabledef dimref
	| variabledef mapref
	| variabledef metadata
	;

mapref:
	MAP_ ATTR_NAME _MAP
	;

structurevariable:
	STRUCTURE_ ATTR_NAME structuredef _STRUCTURE
	;

structuredef:
	  /*empty*/
	| structuredef dimref
	| structuredef variable
	| structuredef metadata
	;

metadatalist:
	  /*empty*/
	| metadatalist metadata
	;

metadata:
	  attribute
	;

attribute:
	ATTRIBUTE_ attribute_attr_list
                   namespace_list
                   value_list
		   _ATTRIBUTE
	;

attribute_attr_list:
	  /*empty*/
	| attribute_attr_list ATTR_NAME
	| attribute_attr_list ATTR_TYPE
	| attribute_attr_list ATTR_NAMESPACE
	;	

value_list:
	  /*empty*/
	| value_list value
	;

value:
	  VALUE_ ATTR_VALUE _VALUE
	| VALUE_ text_list _VALUE
	;

text_list:
	  /*empty*/
	| text_list TEXT
	;

%%
