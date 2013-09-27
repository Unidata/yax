%define parser_class_name {Dap4SaxParser}
%define api.push-pull push

%code imports {
import sys
}

%code lexer {
def yylex(self) : return ("0",None)
def yyerror(self,s) : sys.stderr.write(s+'\n')
}

/* Following should be same for both sax and dom parsers */
%debug
%error-verbose

/* For python, avoid token names starting with underscore */
%token  DATASET DATASET_
%token  GROUP GROUP_
%token  ENUMERATION ENUMERATION_
%token  ENUMCONST ENUMCONST_
%token  NAMESPACE NAMESPACE_
%token  DIMENSION DIMENSION_
%token  DIM DIM_
%token  ENUM ENUM_
%token  MAP MAP_
%token  STRUCTURE STRUCTURE_
%token  VALUE VALUE_
%token  ATTRIBUTE ATTRIBUTE_

/* atomictype lexemes */
%token  CHAR CHAR_
%token  BYTE BYTE_
%token  INT8 INT8_
%token  UINT8 UINT8_
%token  INT16 INT16_
%token  UINT16 UINT16_
%token  INT32 INT32_
%token  UINT32 UINT32_
%token  INT64 INT64_
%token  UINT64 UINT64_
%token  FLOAT32 FLOAT32_
%token  FLOAT64 FLOAT64_
%token  STRING STRING_
%token  URL URL_
%token  OPAQUE OPAQUE_

/* Standard attributes */
%token  ATTR_BASE ATTR_BASETYPE ATTR_DAPVERSION ATTR_DDXVERSION
%token  ATTR_ENUM ATTR_HREF ATTR_NAME ATTR_NAMESPACE
%token  ATTR_SIZE ATTR_TYPE ATTR_VALUE 
%token  ATTR_NS ATTR_XMLNS
%token  UNKNOWN_ATTR UNKNOWN_ELEMENT UNKNOWN_ELEMENT_

%token  TEXT

/* Error cases */
%token  ERROR UNKNOWN UNEXPECTED

%start dataset

%%
dataset:
	DATASET group_attr_list group_body DATASET_
	;

group:
	GROUP group_attr_list group_body GROUP_
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
	ENUMERATION enum_attr_list enumconst_list ENUMERATION_
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
	ENUMCONST enumconst_attr_list ENUMCONST_
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
	NAMESPACE ATTR_HREF NAMESPACE_
	;

dimdef:
	DIMENSION dimdef_attr_list metadatalist DIMENSION_
	;

dimdef_attr_list:
	  ATTR_NAME ATTR_SIZE
	| ATTR_SIZE ATTR_NAME
	;

dimref:
	DIM dimref_attr_list DIM_
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
	atomictype ATTR_NAME variabledef atomictype_
	;


atomictype:
	  CHAR
	| BYTE
	| INT8
	| UINT8
	| INT16
	| UINT16
	| INT32
	| UINT32
	| INT64
	| UINT64
	| FLOAT32
	| FLOAT64
	| STRING
	| URL
	| OPAQUE
	| ENUM ATTR_ENUM
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
	| ENUM_
	;

variabledef:
	  /*empty*/
	| variabledef dimref
	| variabledef mapref
	| variabledef metadata
	;

mapref:
	MAP ATTR_NAME MAP_
	;

structurevariable:
	STRUCTURE ATTR_NAME structuredef STRUCTURE_
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
	ATTRIBUTE attribute_attr_list
                   namespace_list
                   value_list
		   ATTRIBUTE_
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
	  VALUE ATTR_VALUE VALUE_
	| VALUE text_list VALUE_
	;

text_list:
	  /*empty*/
	| text_list TEXT
	;

%%
