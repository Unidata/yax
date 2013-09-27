# Project Description
The goal of the **yax** project is to allow
the use of _YACC_ (Gnu _Bison_ actually)
to parse/process XML documents.

The basic idea is to convert an XML document to a stream of
tokens capable of being ingested by a Bison-generated
parser.  Using this stream plus a grammar written for Bison,
it is possible to carry at least the following kinds of
activities.  The term _bison grammar_ means a grammar for
some language written in the Bison/YAXX language (not a grammar
for Bison itself).

1. Validate XML documents,
2. Directly parse XML documents to
   create internal data structures,
3. Construct DOM trees.
4. Simplify implemenation of SAX-based parsers.

Activity #1, Document validation, can be achieved by parsing
the xml token stream against a Bison grammar defining the
structure of the document.  It is relatively simple to
manually convert a RELAX-NG grammar to a Bison _.y_ grammar.
Parsing a document using the .y grammar then becomes
equivalent to validating the document against the RELAX-NG
grammar.

Activity #2 is the most general case. It simultaneously
validates the XML token stream against the .y grammar
and, using Bison grammar actions, can create internal
data structures containing information extracted from
the XML document.

Activity #3, DOM trees, is actually an example of #2
where the grammar is a general grammar for xml (see the file
xml.y) and the created data structure is a DOM tree.

Activity #4 is an especially important use of yax.
Currently, building a SAX parser is quite onerous
because it is necessary to manually set up data structures
to track the state of the parse. Using yax, the existing
Bison infrastructure can be used to provide all of that
state tracking. The result is that it is easy to build
a SAX-based parser.

# Process

In order to use _yax_, it is necessary to manually produce
the following pieces of code.

First, a Bison .y grammar file must be written. This file
can be produced in a straightforward way starting with a
RELAXNG [1] grammar for the XML document (see below).

Second, a _lexer_ must be written capable of converting
XML document tokens into a form suitable for feeding
to a Bison-generated parser. There are two forms for this.

1. _Pull Parsing_: a program to implement the standard
   Bison _yylex()_ procedure. The parser repeatedly
   calls this function to obtain the next token from the
   XML document. That is, it "pulls" tokens from the lexer
   one by one.

2. _Push Parsing_: this is a new feature of Bison. It inverts
   the control so that a main program obtains tokens from the
   stream and feeds them one by one to the parser. In effect,
   the main program "pushes" tokens into the parser.
   Push parsing is the key to using Bison for SAX parsers.

# Stream sources
The stream of tokens can be constructed in a number of ways.
1. A text document can be lexically decomposed to provide
   a series of text pieces.
2. A DOM tree can be walked in prefix order to produce
   DOM tree nodes that can be converted tokens
3. A SAX parser can be used to provide a series of
   SAX events that can be converted to tokens.

Support is provided for doing this in the following languages.
1. C
2. Java
3. Python

Each operates a little differently due to language specific
quirks and available XML libraries.

# C Language Version

The C implementation uses pull-parsing exclusively.
For C, it is necessary to provide a library that can produce
a XML lexical token stream from an XML document. This stream
must provide tokens that can be "pulled" by a Bison parser.

This stream can be wrapped to create an instance
of _yylex()_ to feed tokens to a Bison grammar to
parse and process the XML document.

# Java Language Version

The Java implementation can use either push or pull parsing.
The standard Java installation includes both DOM and SAX
parsing support, so no separate library is necessary.

# Python Language Version

The Python implementation can uses push parsing only, although
one could extend it to use pull parsing.
The standard Python installation includes SAX
parsing support, so no separate library is necessary.


# References
1. http://relaxng.org
2. http://www.gnu.org/software/bison/manual/bison.html
