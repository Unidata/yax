/* Element tokens */
%token <token> ANYNAME
%token <token> ATTRIBUTE
%token <token> CHOICE
%token <token> DATA
%token <token> DEFINE
%token <token> DIV
%token <token> ELEMENT
%token <token> EMPTY
%token <token> EXCEPT
%token <token> EXTERNALREF
%token <token> GRAMMAR
%token <token> GROUP
%token <token> INCLUDE
%token <token> INTERLEAVE
%token <token> LIST
%token <token> MIXED
%token <token> NAME
%token <token> NOTALLOWED
%token <token> NSNAME
%token <token> ONEORMORE
%token <token> OPTIONAL
%token <token> PARAM
%token <token> PARENTREF
%token <token> REF
%token <token> START
%token <token> TEXT
%token <token> VALUE
%token <token> ZEROORMORE
/* Attribute tokens */
%token <token> COMBINE
%token <token> DATATYPELIBRARY
%token <token> HREF
%token <token> NAME
%token <token> NS
%token <token> TYPE

pattern:
grammar_content:
include_content:
start_element:
define_element:
combine_att:
open_patterns:
open_pattern:
name_class:
except_name_class:
open_name_classes:
open_name_class:
common_atts:
other:
any:
