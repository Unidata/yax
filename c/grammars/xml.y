%error-verbose
%pure-parser

%token <token> PROLOG DOCTYPE
%token <token> OPEN CLOSE EMPTYCLOSE
%token <token> ATTRIBUTE
%token <token> TEXT CDATA COMMENT

/* Error support tokens */
%token <token> ERROR

%type <dom> document
%type <dom> prolog optional_prolog
%type <dom> doctype optional_doctype
%type <dom> element attribute
%type <list> elementlist
%type <list> attributelist

%start document

%%

document:
	optional_prolog
	optional_doctype
	elementlist
	    {$$=xmldocument(parser,$1,$2,$3);}
	;

optional_prolog: /*empty*/ {$$=NULL;} |	prolog {$$=$1;} ;

optional_doctype: /*empty*/ {$$=NULL;} | doctype {$$=$1;} ;

prolog:
	PROLOG {$$=xmlprolog(parser,$1); CHECK($$);}
	;

doctype:
	DOCTYPE {$$=xmldoctype(parser,$1); CHECK($$);}
	;

elementlist:
	  /*empty*/ {$$=xmlelementlist(parser,null,null);}
	| elementlist element {$$=xmlelementlist(parser,$1,$2);}
	;

element:
	  OPEN attributelist EMPTYCLOSE
	    {$$=xmlelement(parser,$1,$3,$2,null); CHECK($$);}
	| OPEN attributelist elementlist CLOSE
	    {$$=xmlelement(parser,$1,$4,$2,$3); CHECK($$);}
	| TEXT {$$=xmlelementtext(parser,$1);}
	| CDATA {$$=xmlelementtext(parser,$1);}
	;

attributelist:
	  /*empty*/ {$$=xmlattributelist(parser,null,null);}
	| attributelist attribute {$$=xmlattributelist(parser,$1,$2);}

attribute:
	ATTRIBUTE {$$=xmlattribute(parser,$1);}
	;
