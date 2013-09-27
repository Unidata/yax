# A Bison parser, made by GNU Bison 3.0.

# Skeleton implementation for Bison LALR(1) parsers in Python

# Copyright (C) 2013 Free Software Foundation, Inc.

# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.

# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.

# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.

# As a special exception, you may create a larger work that contains
# part or all of the Bison parser skeleton and distribute that work
# under terms of your choice, so long as that work isn't itself a
# parser generator using the skeleton or a modified version thereof
# as a parser skeleton.  Alternatively, if you modify or redistribute
# the parser skeleton itself, you may (at your option) remove this
# special exception, which will cause the skeleton and the resulting
# Bison output files to be licensed under the GNU General Public
# License without this special exception.

# This special exception was added by the Free Software Foundation in
# version 2.2 of Bison.

# A Bison parser, automatically generated from
#  <tt>./yax/test/dap4.sax.y</tt>.
#
# @author LALR (1) parser skeleton written by Dennis Heimbigner,
#          derived from the Java parser skeleton.

# Parser skeleton starts here

# First part of user declarations.

# "./yax/test/Dap4SaxParser.py":44 # python.skel:412

# "./yax/test/Dap4SaxParser.py":46 # python.skel:413

# Import modules required for parser operation
import sys
import traceback
# Additional, user specified, imports
# #                    "%code imports" blocks.
# "./yax/test/dap4.sax.y":4 # python.skel:419

import sys

# "./yax/test/Dap4SaxParser.py":57 # python.skel:419


# %code top


##################################################
# Module Level Declarations
##################################################

# Returned by a Bison action in order to stop the parsing process and
# return success (<tt>true</tt>).
YYACCEPT = 0

# Returned by a Bison action in order to stop the parsing process and
# return failure (<tt>false</tt>).
YYABORT = 1
# Returned by a Bison action in order to start error recovery without
# printing an error message.
YYERROR = 2


# Returned by a Bison action in order to request a new token.
YYPUSH_MORE = 4

# Internal return codes that are not supported for user semantic
# actions.

YYERRLAB = 3
YYNEWSTATE = 4
YYDEFAULT = 5
YYREDUCE = 6
YYERRLAB1 = 7
YYRETURN = 8

YYGETTOKEN = 9 # Signify that a new token
               # is expected when doing push-parsing.


# Define a symbol for use with our fake switch statement in yyaction ()
YYACTION = 10

# Map internal labels to strings for those that occur in the "switch".
LABELNAMES = (
"YYACCEPT",
"YYABORT",
"YYERROR",
"YYERRLAB",
"YYNEWSTATE",
"YYDEFAULT",
"YYREDUCE",
"YYERRLAB1",
"YYRETURN",
"YYGETTOKEN",
"YYACTION"
)

# For Python, the Token identifiers are define
# at the module level rather than inside
# the Lexer class. Given python's name scoping,
# this simplifiesthings.

# Token returned by the scanner to signal the end of its input.
EOF = 0


# Tokens.
DATASET = 258
DATASET_ = 259
GROUP = 260
GROUP_ = 261
ENUMERATION = 262
ENUMERATION_ = 263
ENUMCONST = 264
ENUMCONST_ = 265
NAMESPACE = 266
NAMESPACE_ = 267
DIMENSION = 268
DIMENSION_ = 269
DIM = 270
DIM_ = 271
ENUM = 272
ENUM_ = 273
MAP = 274
MAP_ = 275
STRUCTURE = 276
STRUCTURE_ = 277
VALUE = 278
VALUE_ = 279
ATTRIBUTE = 280
ATTRIBUTE_ = 281
CHAR = 282
CHAR_ = 283
BYTE = 284
BYTE_ = 285
INT8 = 286
INT8_ = 287
UINT8 = 288
UINT8_ = 289
INT16 = 290
INT16_ = 291
UINT16 = 292
UINT16_ = 293
INT32 = 294
INT32_ = 295
UINT32 = 296
UINT32_ = 297
INT64 = 298
INT64_ = 299
UINT64 = 300
UINT64_ = 301
FLOAT32 = 302
FLOAT32_ = 303
FLOAT64 = 304
FLOAT64_ = 305
STRING = 306
STRING_ = 307
URL = 308
URL_ = 309
OPAQUE = 310
OPAQUE_ = 311
ATTR_BASE = 312
ATTR_BASETYPE = 313
ATTR_DAPVERSION = 314
ATTR_DDXVERSION = 315
ATTR_ENUM = 316
ATTR_HREF = 317
ATTR_NAME = 318
ATTR_NAMESPACE = 319
ATTR_SIZE = 320
ATTR_TYPE = 321
ATTR_VALUE = 322
ATTR_NS = 323
ATTR_XMLNS = 324
UNKNOWN_ATTR = 325
UNKNOWN_ELEMENT = 326
UNKNOWN_ELEMENT_ = 327
TEXT = 328
ERROR = 329
UNKNOWN = 330
UNEXPECTED = 331


# %code requires


##################################################
# Table data and methods
##################################################

# Whether the given <code>yypact_</code> value indicates a defaulted state.
# @param yyvalue   the value to check

def yy_pact_value_is_default_ (yyvalue) :
  return yyvalue == yypact_ninf_

# Whether the given <code>yytable_</code>
# value indicates a syntax error.
# @param yyvalue the value to check

def yy_table_value_is_error_ (yyvalue) :
  return yyvalue == yytable_ninf_

##################################################
# Define the parsing tables
##################################################

yypact_ = (
       8,   -50,     2,    73,   -50,   -50,   -50,   -50,   -50,   -50,
     -50,   -50,    -4,   -50,   -50,   -48,     6,   -39,   -10,   -50,
     -50,   -50,   -50,   -50,   -50,   -50,   -50,   -50,   -50,   -50,
     -50,   -50,   -50,   -50,   -50,   -50,   -50,   -50,   -50,   -50,
      14,   -50,   -50,   -50,    73,    16,    23,    48,    18,    22,
     -50,   -50,   -50,    36,   -50,    -1,   -50,   -50,   -49,    54,
     -50,   -50,   -50,    -6,    76,   -50,   -50,   -50,    78,    40,
     -50,    20,    32,    94,   -50,   -50,   -50,   -50,    10,   -50,
     -50,   -50,   -50,    44,   -50,    41,   -50,    45,   -50,   -50,
     -50,   -50,   -50,   -50,   -50,   -50,   -50,   -50,   -50,   -50,
     -50,   -50,   -50,   -50,   -50,   -50,   -50,   -50,   -50,   -50,
     -50,   -50,    96,    98,    47,   -50,   -50,   100,   -50,   -50,
      92,   -17,   -50,   -50,   -50,   -50
  )

yydefact_ = (
       0,     4,     0,    12,     1,     9,     6,     7,     5,     8,
      10,    11,     0,     2,     4,     0,     0,     0,     0,    84,
      38,    39,    40,    41,    42,    43,    44,    45,    46,    47,
      48,    49,    50,    51,    52,    17,    13,    14,    15,    35,
       0,    36,    16,    82,    12,     0,     0,     0,     0,     0,
      80,    53,    76,    26,    70,     0,    20,    19,     0,     0,
      21,    30,    31,     0,     0,    85,    87,    86,    88,     0,
       3,     0,     0,     0,    18,    22,    29,    81,     0,    75,
      77,    78,    79,     0,    27,     0,    69,     0,    54,    55,
      56,    57,    58,    59,    60,    61,    62,    63,    64,    65,
      66,    67,    68,    71,    37,    72,    73,    24,    25,    23,
      33,    34,     0,     0,    92,    83,    89,     0,    32,    28,
       0,     0,    74,    90,    91,    93
  )

yypgoto_ = (
     -50,   -50,   -50,   104,    80,   -50,   -50,   -50,    63,   -50,
     -50,   -50,   -50,   -50,    57,   -50,    64,   -50,   -50,   -50,
     -50,   -50,   -50,   -50,   -50,    -3,   -50,   -50,   -50,   -50,
     -50
  )

yydefgoto_ = (
      -1,     2,    35,     3,    12,    36,    47,    59,    60,    73,
      68,    84,    37,    50,    80,   112,    38,    39,    40,   104,
      69,   105,    41,    64,    63,    42,    43,    53,    85,   116,
     121
  )

yytable_ = (
      13,    14,     4,    15,    14,    70,    15,   124,    76,    16,
      45,     1,    16,    17,    71,    46,    17,    18,    72,    19,
      18,    19,    51,    20,    19,    21,    20,    22,    21,    23,
      22,    24,    23,    25,    24,    26,    25,    27,    26,    28,
      27,    29,    28,    30,    29,    31,    30,    32,    31,    33,
      32,    34,    33,    52,    34,    78,   125,    58,    86,    87,
      77,    82,    74,    58,   114,    19,   106,   115,    88,    48,
      89,    49,    90,   110,    91,   111,    92,    54,    93,    56,
      94,    57,    95,    61,    96,    62,    97,   107,    98,    83,
      99,    78,   100,    17,   101,   108,   102,    18,    79,    65,
      66,    19,    67,    20,   109,    21,   113,    22,   117,    23,
     119,    24,   118,    25,   120,    26,   123,    27,    44,    28,
     122,    29,    75,    30,    55,    31,   103,    32,    81,    33,
       5,    34,     6,     7,     0,     0,     8,     0,     0,     0,
       0,     9,    10,    11
  )

yycheck_ = (
       4,     5,     0,     7,     5,     6,     7,    24,    14,    13,
      58,     3,    13,    17,    63,    63,    17,    21,    67,    25,
      21,    25,    61,    27,    25,    29,    27,    31,    29,    33,
      31,    35,    33,    37,    35,    39,    37,    41,    39,    43,
      41,    45,    43,    47,    45,    49,    47,    51,    49,    53,
      51,    55,    53,    63,    55,    15,    73,     9,    18,    19,
      63,    64,     8,     9,    23,    25,    69,    26,    28,    63,
      30,    65,    32,    63,    34,    65,    36,    63,    38,    63,
      40,    58,    42,    65,    44,    63,    46,    67,    48,    11,
      50,    15,    52,    17,    54,    63,    56,    21,    22,    63,
      64,    25,    66,    27,    10,    29,    62,    31,    63,    33,
      12,    35,    16,    37,    67,    39,    24,    41,    14,    43,
      20,    45,    59,    47,    44,    49,    69,    51,    64,    53,
      57,    55,    59,    60,    -1,    -1,    63,    -1,    -1,    -1,
      -1,    68,    69,    70
  )

yystos_ = (
       0,     3,    78,    80,     0,    57,    59,    60,    63,    68,
      69,    70,    81,     4,     5,     7,    13,    17,    21,    25,
      27,    29,    31,    33,    35,    37,    39,    41,    43,    45,
      47,    49,    51,    53,    55,    79,    82,    89,    93,    94,
      95,    99,   102,   103,    80,    58,    63,    83,    63,    65,
      90,    61,    63,   104,    63,    81,    63,    58,     9,    84,
      85,    65,    63,   101,   100,    63,    64,    66,    87,    97,
       6,    63,    67,    86,     8,    85,    14,   102,    15,    22,
      91,    93,   102,    11,    88,   105,    18,    19,    28,    30,
      32,    34,    36,    38,    40,    42,    44,    46,    48,    50,
      52,    54,    56,    91,    96,    98,   102,    67,    63,    10,
      63,    65,    92,    62,    23,    26,   106,    63,    16,    12,
      67,   107,    20,    24,    24,    73
  )

yyr1_ = (
       0,    77,    78,    79,    80,    80,    80,    80,    80,    80,
      80,    80,    81,    81,    81,    81,    81,    81,    82,    83,
      83,    84,    84,    85,    86,    86,    87,    87,    88,    89,
      90,    90,    91,    92,    92,    93,    93,    94,    95,    95,
      95,    95,    95,    95,    95,    95,    95,    95,    95,    95,
      95,    95,    95,    95,    96,    96,    96,    96,    96,    96,
      96,    96,    96,    96,    96,    96,    96,    96,    96,    96,
      97,    97,    97,    97,    98,    99,   100,   100,   100,   100,
     101,   101,   102,   103,   104,   104,   104,   104,   105,   105,
     106,   106,   107,   107
  )

yyr2_ = (
       0,     2,     4,     4,     0,     2,     2,     2,     2,     2,
       2,     2,     0,     2,     2,     2,     2,     2,     4,     2,
       2,     1,     2,     3,     2,     2,     0,     2,     3,     4,
       2,     2,     3,     1,     1,     1,     1,     4,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     2,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       0,     2,     2,     2,     3,     4,     0,     2,     2,     2,
       0,     2,     1,     5,     0,     2,     2,     2,     0,     2,
       3,     3,     0,     2
  )


yytoken_number_ = (
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
     315,   316,   317,   318,   319,   320,   321,   322,   323,   324,
     325,   326,   327,   328,   329,   330,   331
  )

# YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
#   First, the terminals, then, starting at yyntokens_, nonterminals.
yytname_ = (
  "$end", "error", "$undefined", "DATASET", "DATASET_", "GROUP", "GROUP_",
  "ENUMERATION", "ENUMERATION_", "ENUMCONST", "ENUMCONST_", "NAMESPACE",
  "NAMESPACE_", "DIMENSION", "DIMENSION_", "DIM", "DIM_", "ENUM", "ENUM_",
  "MAP", "MAP_", "STRUCTURE", "STRUCTURE_", "VALUE", "VALUE_", "ATTRIBUTE",
  "ATTRIBUTE_", "CHAR", "CHAR_", "BYTE", "BYTE_", "INT8", "INT8_", "UINT8",
  "UINT8_", "INT16", "INT16_", "UINT16", "UINT16_", "INT32", "INT32_",
  "UINT32", "UINT32_", "INT64", "INT64_", "UINT64", "UINT64_", "FLOAT32",
  "FLOAT32_", "FLOAT64", "FLOAT64_", "STRING", "STRING_", "URL", "URL_",
  "OPAQUE", "OPAQUE_", "ATTR_BASE", "ATTR_BASETYPE", "ATTR_DAPVERSION",
  "ATTR_DDXVERSION", "ATTR_ENUM", "ATTR_HREF", "ATTR_NAME",
  "ATTR_NAMESPACE", "ATTR_SIZE", "ATTR_TYPE", "ATTR_VALUE", "ATTR_NS",
  "ATTR_XMLNS", "UNKNOWN_ATTR", "UNKNOWN_ELEMENT", "UNKNOWN_ELEMENT_",
  "TEXT", "ERROR", "UNKNOWN", "UNEXPECTED", "$accept", "dataset", "group",
  "group_attr_list", "group_body", "enumdef", "enum_attr_list",
  "enumconst_list", "enumconst", "enumconst_attr_list", "namespace_list",
  "namespace", "dimdef", "dimdef_attr_list", "dimref", "dimref_attr_list",
  "variable", "simplevariable", "atomictype", "atomictype_", "variabledef",
  "mapref", "structurevariable", "structuredef", "metadatalist",
  "metadata", "attribute", "attribute_attr_list", "value_list", "value",
  "text_list", None
  )

yyrline_ = (
       0,    64,    64,    68,    71,    73,    74,    75,    76,    77,
      78,    79,    82,    84,    85,    86,    87,    88,    92,    96,
      97,   101,   102,   107,   111,   112,   115,   117,   121,   125,
     129,   130,   134,   138,   139,   143,   144,   149,   154,   155,
     156,   157,   158,   159,   160,   161,   162,   163,   164,   165,
     166,   167,   168,   169,   173,   174,   175,   176,   177,   178,
     179,   180,   181,   182,   183,   184,   185,   186,   187,   188,
     191,   193,   194,   195,   199,   203,   206,   208,   209,   210,
     213,   215,   219,   223,   229,   231,   232,   233,   236,   238,
     242,   243,   246,   248
  )

# YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.
yytranslate_table_ = (
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    47,    48,    49,    50,    51,    52,    53,    54,
      55,    56,    57,    58,    59,    60,    61,    62,    63,    64,
      65,    66,    67,    68,    69,    70,    71,    72,    73,    74,
      75,    76
  )

def yytranslate_ (t) :
  if (t >= 0 and t <= yyuser_token_number_max_) :
    return yytranslate_table_[t]
  else :
    return yyundef_token_

  # Return whether error recovery is being done.  In this state, the parser
  # reads token until it reaches a known state, and then restarts normal
  # operation.
  def recovering (self) :
    return self.yyerrstatus_ == 0

# Table variable related constants
yylast_ = 143
yynnts_ = 31
yyempty_ = -2
yyfinal_ = 4
yyterror_ = 1
yyerrcode_ = 256
yyntokens_ = 77

yyuser_token_number_max_ = 331
yyundef_token_ = 2

yypact_ninf_ = -50
yytable_ninf_ = -1

##################################################
# Auxilliary Classes
##################################################



class YYStack :

  def __init__ (self) :
    self.size = 16
    self.height = -1
    self.stateStack=[]
    self.valueStack=[]
    

  def push (self, state, value, ) :
    self.height += 1
    self.stateStack.append(state)
    self.valueStack.append(value)
    

  def pop (self, num) :
    if (num > 0) :
      for i in range(num) :
        self.valueStack.pop()
        self.stateStack.pop()
        
    self.height -= num

  def stateAt (self, i) :
    return self.stateStack[self.height - i]

  
  def valueAt (self, i) :
    return self.valueStack[self.height - i]

  # Print the state stack on the debug stream.
  # Note: needs to be renamed for Python
  def yyprint (self, out) :
    out.write ("Stack now")
    for x in self.stateStack[:] :
        out.write (' ')
        out.write (str(x))
    out.write ('\n')
# end class YYStack

##################################################
# Class Lexer
##################################################

# This class defines the  Communication interface between the
# scanner and the Bison-generated parser <tt>Dap4SaxParser</tt>.
#
# For Python there are some things to note.
# 1. Lexer is defined as a class because Java-like interfaces
#    are not supported.
# 2. The lexer class is defined at module scope.
# 3. Python allows for the return of multiple values, so
#    yylex can return the token and lval all at one time.
#    Location information is still obtained by calling
#    Lexer methods.
# 4. The lexer also supports the user's yyerror method,
#    but see the note at the push_parse method.

class Lexer :

  def __init__ (self) :
    pass


  # Entry point for the scanner.
  # Returns two values: (1) the token identifier corresponding
  # to the next token, and 2) the semantic value associated with the token.
  # 
  # @return the token identifier corresponding to the next token
  #          and the semantic value.

  def yylex (self) :
    return (0,None)

  # Entry point for error reporting.  Emits an error
  # 
  # in a user-defined way.
  #
  # @param msg The string for the error message.
  # 

  def yyerror (self, msg) :
    s = msg
    
    sys.stderr.write(s+'\n')


# If the user specifies %code lexer ...
# Then insert it here
class YYLexer (Lexer) :
  # #                    "%code lexer" blocks.
  # "./yax/test/dap4.sax.y":8 # python.skel:695
  
  def yylex(self) : return ("0",None)
  def yyerror(self,s) : sys.stderr.write(s+'\n')
  
  # "./yax/test/Dap4SaxParser.py":565 # python.skel:695
  


# The yyaction functions now all become global 
# We also need a class to act as a call by reference
# return for yyval.
class YYVAL:
  def __init__(self,yyval): self.yyval = yyval
#end YYVAL

yyswitch = {}


# "./yax/test/Dap4SaxParser.py":579 # python.skel:709

##################################################
# Primary Parser Class
##################################################

class Dap4SaxParser :
  # Version number for the Bison executable that generated this parser.
  bisonVersion = "3.0"

  # Name of the skeleton that generated this parser.
  bisonSkeleton = "./yax/python.skel"



  ##################################################
  # Class Dap4SaxParser API
  ##################################################

  # Instance Variables

  # Return whether verbose error messages are enabled.
  def getErrorVerbose(self) :
    return self.yyErrorVerbose

  # Set the verbosity of error messages.
  # @param verbose True to request verbose error messages.
  def setErrorVerbose(self, verbose) :
    self.yyErrorVerbose = verbose

  # Return the <tt>PrintStream</tt> on which the debugging output is
  # printed.

  def getDebugStream (self) :
    return self.yyDebugStream

  # Set the <tt>PrintStream</tt> on which the debug output is printed.
  # @param s The stream that is used for debugging output.
  def setDebugStream(self, s) :
    self.yyDebugStream = s

  # Answer the verbosity of the debugging output 0 means that all kinds of
  # output from the parser are suppressed.
  def getDebugLevel (self) :
    return self.yydebug

  # Set the verbosity of the debugging output 0 means that all kinds of
  # output from the parser are suppressed.
  # @param level The verbosity level for debugging output.
  def setDebugLevel (self, level) :
    self.yydebug = level

  ##################################################
  # Class Dap4SaxParser Constructor
  ##################################################

  # Instantiates the Bison-generated parser.
  # 

  def __init__  (self):


    self.yylexer = YYLexer()

    self.yyDebugStream = sys.stderr
    self.yydebug = 0
    self.yyerrstatus_ = 0
    self.push_parse_initialized = False
    # True if verbose error messages are enabled.
    self.yyErrorVerbose = True
  # end __init__


    # Lookahead and lookahead in internal form.
    self.yychar = yyempty_
    self.yytoken = 0

    # State.
    self.yyn = 0
    self.yylen = 0
    self.yystate = 0
    self.yystack = YYStack ()
    self.label = YYNEWSTATE

    # Error handling.
    self.yynerrs_ = 0
    
    # Semantic value of the lookahead.
    self.yylval = None


  ##################################################
  # User defined action invocation.
  ##################################################

  # For python, pass in the yyerror function
  # to simplify access so the caller does not need to prefix it.
  def yyaction (self, yyn, yystack, yylen, yyerror) :
    yylval = None
    

    # If YYLEN is nonzero, implement the default value of the action:
    #   '$$ = $1'.  Otherwise, use the top of the stack.
    #    Otherwise, the following line sets YYVAL to garbage.
    #    This behavior is undocumented and Bison
    #    users should not rely upon it.
    if (yylen > 0) :
      yyval = yystack.valueAt (yylen - 1)
    else :
      yyval = yystack.valueAt (0)

    self.yy_reduce_print (yyn, yystack)

    # Simulate a switch in python using a dictionary
    # that maps states to functions defining the user defined
    # actions. (See just be for the YYParser class definition
    # above). This depends on the fact that passing mutable objects
    # into a function allows the function to modify that object.
    # Note that the action body is indentation sensitive

    if (yyn in yyswitch) :
      action = yyswitch[yyn]
      yyvalp = YYVAL(yyval)
      status = action(yyvalp, yyn, yystack, yylen, yyerror)
      yyval = yyvalp.yyval
      if(status != None) : return status
    else: # no such action index; ignore
      pass

    self.yy_symbol_print ("-> $$ =",
                          yyr1_[yyn], yyval)

    yystack.pop (yylen)
    yylen = 0

    # Shift the result of the reduction.
    yyn = yyr1_[yyn]
    tmp = yyntokens_ # quote problem
    yystate = yypgoto_[yyn - tmp] + yystack.stateAt (0)
    if (0 <= yystate
        and yystate <= yylast_
        and yycheck_[yystate] == yystack.stateAt (0)) :
      yystate = yytable_[yystate]
    else :
      yystate = yydefgoto_[yyn - tmp]

    yystack.push (yystate, yyval)
    return YYNEWSTATE
  # end yyaction

  ##################################################
  # Debug output for rule reduction
  # Report on the debug stream that the rule yyrule is going to be reduced.
  ##################################################

  def yy_reduce_print (self, yyrule, yystack) :
    if (self.yydebug == 0) :
      return

    yylno = yyrline_[yyrule]
    yynrhs = yyr2_[yyrule]
    # Print the symbols being reduced, and their result.
    self.yycdebug ("Reducing stack by rule " + str(yyrule - 1)
               + " (line " + str(yylno) + "), ")

    # The symbols being reduced.
    for yyi in range(yynrhs) :
      self.yy_symbol_print ("   $" + str(yyi + 1) + " =",
                       yystos_[yystack.stateAt(yynrhs - (yyi + 1))],
                       (yystack.valueAt (yynrhs-(yyi + 1))))
  # end yy_reduce_print





  # Primary push parser API method
  # Push parse given input from an external lexer.
  # Position provided rather than Location.
  #
  # @param yylextoken current token
  # @param yylexval current lval
  # @param (Optional) location=None current position;
  #ignored if location tracking is disabled.
  #
  # @return <tt>YYACCEPT, YYABORT, YYPUSH_MORE</tt>

  def push_parse (self, yylextoken, yylexval) :



    if (not self.push_parse_initialized) :
      self.push_parse_initialize ()
      self.yycdebug ("Starting parse\n")
      self.yyerrstatus_ = 0
      # Initialize the stack.
      self.yystack.push (self.yystate,
                             self.yylval )

    else :
      self.label = YYGETTOKEN
    push_token_consumed = True



    ##################################################
    # Begin code common to push and pull parsing
    ##################################################

    while True :
      #sys.stderr.write("label=("+str(self.label)+")="
      #+LABELNAMES[self.label]+'\n')

      # For python we need to simulate switch using if statements
      # Because we have the enclosing while loop, we can exit the switch
      # using continue instead of break (assumes that there is no code
      # following the switch).
      # switch label :
      # New state.  Unlike in the C/C++ skeletons, the state is already
      # pushed when we come here.
      if self.label == YYNEWSTATE : # case YYNEWSTATE
        self.yycdebug ("Entering state " + str(self.yystate) + '\n')
        if (self.yydebug > 0) :
          self.yystack.yyprint (self.yyDebugStream)

        # Accept?
        if (self.yystate == yyfinal_) :
          self.label = YYACCEPT
          continue

        # Take a decision.  First try without lookahead.
        # Quote problem
        tmp = self.yystate
        self.yyn = yypact_[tmp]
        if (yy_pact_value_is_default_ (self.yyn)) :
            self.label = YYDEFAULT
            continue; # break switch

        self.label = YYGETTOKEN # Cheat to simulate fall thru
      elif self.label == YYGETTOKEN : # case YYGETTOKEN
        # Read a lookahead token.
        if (self.yychar == yyempty_) :
          if ( not push_token_consumed) :
            return YYPUSH_MORE
          self.yycdebug ("Reading a token: ")
          self.yychar = yylextoken
          self.yylval = yylexval
          push_token_consumed = False

        # Convert token to internal form.
        if (self.yychar <= EOF) :
          self.yychar = EOF
          self.yytoken = EOF
          self.yycdebug ("Now at end of input.\n")
        else :
          self.yytoken = yytranslate_ (self.yychar)
          self.yy_symbol_print ("Next token is",
                                self.yytoken,
                                self.yylval
                                
                                )

        # If the proper action on seeing token YYTOKEN is to reduce or to
        # detect an error, then take that action.
        self.yyn += self.yytoken
        tmp = self.yyn # Quote problem
        if (self.yyn < 0 
            or yylast_ < self.yyn
            or yycheck_[tmp] != self.yytoken) :
          self.label = YYDEFAULT

        # <= 0 means reduce or error.
        elif (yytable_[tmp] <= 0) :
          self.yyn = yytable_[tmp]
          if (yy_table_value_is_error_ (self.yyn)) :
            self.label = YYERRLAB
          else :
            self.yyn = -self.yyn
            self.label = YYREDUCE
        else :
          tmp = self.yyn # Quote problem
          self.yyn = yytable_[tmp]
          # Shift the lookahead token.
          self.yy_symbol_print ("Shifting",
                                self.yytoken,
                                self.yylval)

          # Discard the token being shifted.
          self.yychar = yyempty_

          # Count tokens shifted since error after three, turn off error
          # status.
          if (self.yyerrstatus_ > 0) :
              self.yyerrstatus_ -= 1

          self.yystate = self.yyn
          self.yystack.push (self.yystate, self.            yylval)
          self.label = YYNEWSTATE
      # end case YYNEWSTATE

      #-----------------------------------------------------------.
      #| yydefault -- do the default action for the current state.  |
      #-----------------------------------------------------------
      elif self.label == YYDEFAULT : #case YYDEFAULT
        tmp = self.yystate # Quote problem
        self.yyn = yydefact_[tmp]
        if (self.yyn == 0) :
          self.label = YYERRLAB
        else :
          self.label = YYREDUCE
      # end case YYDEFAULT

      #-----------------------------.
      #| yyreduce -- Do a reduction.  |
      #-----------------------------
      elif self.label == YYREDUCE : #case YYREDUCE
        tmp = self.yyn # Quote problem
        self.yylen = yyr2_[tmp]
        self.label = self.yyaction (self.yyn,         self.yystack, self.yylen, self.yylexer.yyerror)
        self.yystate = self.yystack.stateAt (0)
      # end case YYDEFAULT

      #------------------------------------.
      #| yyerrlab -- here on detecting error |
      #------------------------------------
      elif self.label == YYERRLAB: #case YYERRLAB
        # If not already recovering from an error, report this error.
        if (self.yyerrstatus_ == 0) :
          self.yynerrs_ += 1
          if (self.yychar == yyempty_) :
            self.yytoken = yyempty_
          tmp = self.yysyntax_error (self.yystate, self.yytoken)
          self.yyerror (tmp)

        
        if (self.yyerrstatus_ == 3) :
          # If just tried and failed to reuse lookahead token after an
          # error, discard it.

          if (self.yychar <= EOF) :
            # Return failure if at end of input.
            if (self.yychar == EOF) :
              self.label = YYABORT
              continue
          else :
            self.yychar = yyempty_

        # Else will try to reuse lookahead token after
        # shifting the error token.
        self.label = YYERRLAB1
      # end case YYERRLAB

      #-------------------------------------------------.
      #| errorlab -- error raised explicitly by YYERROR.  |
      #-------------------------------------------------
      elif self.label == YYERROR : #case YYERROR
        
        # Do not reclaim the symbols of the rule which action triggered
        # this YYERROR.
        self.yystack.pop (self.yylen)
        self.yylen = 0
        self.yystate = self.yystack.stateAt (0)
        self.label = YYERRLAB1
      # end case YYERROR

      #-------------------------------------------------------------.
      #| yyerrlab1 -- common code for both syntax error and YYERROR.  |
      #-------------------------------------------------------------
      elif self.label == YYERRLAB1 : #case YYERRLAB1
        self.yyerrstatus_ = 3 # Each real token shifted decrements this.
        while True :
          tmp = self.yystate # Quote problem
          self.yyn = yypact_[tmp]
          if ( not yy_pact_value_is_default_ (self.yyn)) :
            self.yyn += yyterror_
            tmp = self.yyn # Quote problem
            if (0 <= self.yyn and self.yyn <= yylast_ \
                and yycheck_[tmp] == yyterror_) :
              self.yyn = yytable_[tmp]
              if (0 < self.yyn) :
                break # leave while loop

            # Pop the current state because it cannot handle the
            # error token.
            if (self.yystack.height == 0) :
              self.label = YYABORT
              continue # Leave the switch

            
            self.yystack.pop (1)
            self.yystate = self.yystack.stateAt (0)
            if (self.yydebug > 0) :
              self.yystack.yyprint (self.yyDebugStream)

        if (self.label == YYABORT) :
          continue # Leave the switch.


        # Shift the error token.
        tmp = self.yyn
        self.yy_symbol_print ("Shifting", yystos_[tmp],
                         self.yylval)

        self.yystate = self.yyn
        self.yystack.push (self.yyn, self.yylval               )
        self.label = YYNEWSTATE
        continue # leave the switch
      # end case YYERRLAB1

      # Accept.
      elif self.label == YYACCEPT : # case YYACCEPT
        self.push_parse_initialized = False
        return YYACCEPT
      # end case YYACCEPT

      # Abort.
      elif self.label == YYABORT: # case YYABORT
        self.push_parse_initialized = False
        return YYABORT
      # end case YYABORT

      else :
        assert False, "Unknown State:" + str(self.label)

  # end push_parse




  # (Re-)Initialize the state of the push parser.

  def push_parse_initialize(self) :

    # Lookahead and lookahead in internal form.
    self.yychar = yyempty_
    self.yytoken = 0

    # State.
    self.yyn = 0
    self.yylen = 0
    self.yystate = 0
    self.yystack = YYStack ()
    self.label = YYNEWSTATE

    # Error handling.
    self.yynerrs_ = 0
    
    # Semantic value of the lookahead.
    self.yylval = None


    self.push_parse_initialized = True

  # end push_parse_initialize



  ##################################################
  # Class Dap4SaxParser Internal Methods
  ##################################################



  # Print an error message via the lexer.
  # @param msg The error message.
  # @param locaction (Optional) The location or position
  #                associated with the message.

  def yyerror (self, msg) :
    self.yylexer.yyerror (msg)
  # end yyerror

  def yycdebug (self, s) :
    if (self.yydebug > 0) :
      self.yyDebugStream.write (s+'\n')
  # end self.yycdebug


  # Return YYSTR after stripping away unnecessary quotes and
  # backslashes, so that it's suitable for yyerror.  The heuristic is
  # that double-quoting is unnecessary unless the string contains an
  # apostrophe, a comma, or backslash (other than backslash-backslash).
  # YYSTR is taken from yytname.
  def yytnamerr_ (self, yystr) :
    yyr = ""
    if (yystr[0] == '"')  :
      l = len(yystr)
      i = 1
      while (True) :
        if (i >= l) : break
        c = yystr[i]
        if(c == "'" or c == ',') :
          continue
        if( c == '"'):
          return yyr
        if(c == '\\') :
          i += 1
          c = yystr[i]
          if(c != '\\') :
            break
        yyr = yyr + c
        i += 1
      # end while
    elif (yystr ==  "$end") :
      return "end of input"
    return yystr;
  # end yytnamerr


  #--------------------------------.
  #| Print this symbol on YYOUTPUT.  |
  #--------------------------------

  def yy_symbol_print (self, s, yytype, yyvaluep ) :
    if (self.yydebug > 0) :
      tag = " nterm "
      if (yytype < yyntokens_) :
        tag = " token "
      if (yyvaluep is None) :
        vps = "None"
      else :
        vps = str(yyvaluep)
      tname = yytname_[yytype]
      line = s + tag + tname
      line += " ("
      
      line += vps
      line += ')'
      self.yycdebug (line)
  # end yy_symbol_print

  # Generate an error message.
  def yysyntax_error (self, yystate, tok) :
  
    if (self.yyErrorVerbose) :
      # There are many possibilities here to consider:
      # - If this state is a consistent state with a default action,
      #   then the only way this function was invoked is if the
      #   default action is an error action.  In that case, don't
      #   check for expected tokens because there are none.
      # - The only way there can be no lookahead present (in tok) is
      #   if this state is a consistent state with a default action.
      #   Thus, detecting the absence of a lookahead is sufficient to
      #   determine that there is no unexpected or expected token to
      #   report.  In that case, just report a simple 'syntax error'.
      # - Don't assume there isn't a lookahead just because this
      #   state is a consistent state with a default action.  There
      #   might have been a previous inconsistent state, consistent
      #   state with a non-default action, or user semantic action
      #   that manipulated self.yychar.  (However, self.yychar
      #   is currently out of scope during semantic actions.)
      # - Of course, the expected token list depends on states to
      #   have correct lookahead information, and it depends on the
      #   parser not to perform extra reductions after fetching a
      #   lookahead from the scanner and before detecting a syntax
      #   error.  Thus, state merging (from LALR or IELR) and default
      #   reductions corrupt the expected token list.  However, the
      #   list is correct for canonical LR with one exception: it
      #   will still contain any token that will not be accepted due
      #   to an error action in a later state.

      if (tok  != yyempty_) :
        # FIXME: This method of building the message is not compatible
        # with internationalization.
        res = "syntax error, unexpected "
        res += (self.yytnamerr_ (yytname_[tok]))
        tmp = self.yystate
        self.yyn = yypact_[tmp]
        if ( not yy_pact_value_is_default_ (self.yyn)) :
          # Start YYX at -YYN if negative to avoid negative
          # indexes in YYCHECK.  In other words, skip the first
          # -YYN actions for this state because they are default actions.
          yyxbegin = 0
          if (self.yyn < 0) :
            yyxbegin =  - self.yyn
          # Stay within bounds of both yycheck and yytname.
          yychecklim = yylast_ - self.yyn + 1
          yyxend = yychecklim
          if (yychecklim >= yyntokens_) :
            yyxend = yyntokens_
          count = 0
          for x in range(yyxbegin,yyxend) :
            tmp = self.yyn
            if (yycheck_[x + tmp] == x and x != yyterror_
                and  not yy_table_value_is_error_ (yytable_[x + tmp])) :
              count += 1
          if (count < 5) :
            count = 0
            for x in range(yyxbegin,yyxend) :
              tmp = self.yyn
              if (yycheck_[x + tmp] == x and x != yyterror_
                  and  not yy_table_value_is_error_ (yytable_[x + tmp])) :
                if (count == 0) :
                  res += ", expecting "
                else :
                  res += " or "
                count += 1
                res += (self.yytnamerr_ (yytname_[x]))
        return str(res)

    return "syntax error"
  # end yysyntax_error


# %code provides.

# User implementation code.

# "./yax/test/dap4.sax.y":251 # python.skel:1385

