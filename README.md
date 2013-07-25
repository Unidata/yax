# Project Description
The goal of the **yax** project is to allow
the use of _YACC_ (Gnu _Bison_ actually)
to parse/process XML documents.

The key piece of software for achieving the above goal is to
provide a library that can produce an XML lexical token
stream from an XML document.

This stream can be wrapped to create an instance
of _yylex()_ to feed tokens to a Bison grammar to
parse and process the XML document.

Using the stream plus a Bison grammar, it is possible
to carry at least the following kinds of activities.

1. Validate XML documents,
2. Directly parse XML documents to
  create internal data structures,
3. Construct DOM trees.

Activity #1, Document validation, can be achieved by parsing the xml token
stream against a validation grammar. It is relatively simple
to manually convert a RELAX-NG grammar to a Bison _.y_ grammar.
Parsing a document using the .y grammar then becomes equivalent
to validating the document against the RELAX-NG grammar.

Activity #2 is the most general case. It simultaneously
validates the XML token stream against the .y grammar
and, using Bison grammar actions, can create internal
data structures containing information extracted from
the XML document.

Activity #3, DOM trees, is actually an example of #2
where the grammar is a general grammar for xml (see the file
xml.y) and the created data structure is a DOM tree.

# Overview

In order to use _yax_, it is necessary to manually produce
the following pieces of code.

1. A Bison .y grammar file. This file can be produced in a
straightforward way starting with a RELAXNG [1] grammar for
the XML document.
2. A Bison _yylex() program that can provide lexical
tokens to a Bison parser.  The yylex() program in turn wraps
the ''yax'' provided basic xml lexer to provide a stream of
xml-level tokens for the Bison generated parser.

# References
1. http://relaxng.org
