/* A Bison parser, made by GNU Bison 2.4.2.  */

/* Skeleton implementation for Bison LALR(1) parsers in Java
   
      Copyright (C) 2007-2010 Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

package yax.test;
/* First part of user declarations.  */


/**
 * A Bison parser, automatically generated from <tt>F:\\svn\\yax\\java/src/yax/test/dap4.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
public class Dap4Parser extends Dap4Test
{
    /** Version number for the Bison executable that generated this parser.  */
  public static final String bisonVersion = "2.4.2";

  /** Name of the skeleton that generated this parser.  */
  public static final String bisonSkeleton = "lalr1.java";


  /** True if verbose error messages are enabled.  */
  public boolean errorVerbose = true;



  /** Token returned by the scanner to signal the end of its input.  */
  public static final int EOF = 0;

/* Tokens.  */
  /** Token number, to be returned by the scanner.  */
  public static final int GROUP_ = 258;
  /** Token number, to be returned by the scanner.  */
  public static final int _GROUP = 259;
  /** Token number, to be returned by the scanner.  */
  public static final int ENUMERATION_ = 260;
  /** Token number, to be returned by the scanner.  */
  public static final int _ENUMERATION = 261;
  /** Token number, to be returned by the scanner.  */
  public static final int ENUMCONST_ = 262;
  /** Token number, to be returned by the scanner.  */
  public static final int _ENUMCONST = 263;
  /** Token number, to be returned by the scanner.  */
  public static final int NAMESPACE_ = 264;
  /** Token number, to be returned by the scanner.  */
  public static final int _NAMESPACE = 265;
  /** Token number, to be returned by the scanner.  */
  public static final int DIMENSION_ = 266;
  /** Token number, to be returned by the scanner.  */
  public static final int _DIMENSION = 267;
  /** Token number, to be returned by the scanner.  */
  public static final int DIM_ = 268;
  /** Token number, to be returned by the scanner.  */
  public static final int _DIM = 269;
  /** Token number, to be returned by the scanner.  */
  public static final int ENUM_ = 270;
  /** Token number, to be returned by the scanner.  */
  public static final int _ENUM = 271;
  /** Token number, to be returned by the scanner.  */
  public static final int MAP_ = 272;
  /** Token number, to be returned by the scanner.  */
  public static final int _MAP = 273;
  /** Token number, to be returned by the scanner.  */
  public static final int STRUCTURE_ = 274;
  /** Token number, to be returned by the scanner.  */
  public static final int _STRUCTURE = 275;
  /** Token number, to be returned by the scanner.  */
  public static final int VALUE_ = 276;
  /** Token number, to be returned by the scanner.  */
  public static final int _VALUE = 277;
  /** Token number, to be returned by the scanner.  */
  public static final int ATTRIBUTE_ = 278;
  /** Token number, to be returned by the scanner.  */
  public static final int _ATTRIBUTE = 279;
  /** Token number, to be returned by the scanner.  */
  public static final int CHAR_ = 280;
  /** Token number, to be returned by the scanner.  */
  public static final int _CHAR = 281;
  /** Token number, to be returned by the scanner.  */
  public static final int BYTE_ = 282;
  /** Token number, to be returned by the scanner.  */
  public static final int _BYTE = 283;
  /** Token number, to be returned by the scanner.  */
  public static final int INT8_ = 284;
  /** Token number, to be returned by the scanner.  */
  public static final int _INT8 = 285;
  /** Token number, to be returned by the scanner.  */
  public static final int UINT8_ = 286;
  /** Token number, to be returned by the scanner.  */
  public static final int _UINT8 = 287;
  /** Token number, to be returned by the scanner.  */
  public static final int INT16_ = 288;
  /** Token number, to be returned by the scanner.  */
  public static final int _INT16 = 289;
  /** Token number, to be returned by the scanner.  */
  public static final int UINT16_ = 290;
  /** Token number, to be returned by the scanner.  */
  public static final int _UINT16 = 291;
  /** Token number, to be returned by the scanner.  */
  public static final int INT32_ = 292;
  /** Token number, to be returned by the scanner.  */
  public static final int _INT32 = 293;
  /** Token number, to be returned by the scanner.  */
  public static final int UINT32_ = 294;
  /** Token number, to be returned by the scanner.  */
  public static final int _UINT32 = 295;
  /** Token number, to be returned by the scanner.  */
  public static final int INT64_ = 296;
  /** Token number, to be returned by the scanner.  */
  public static final int _INT64 = 297;
  /** Token number, to be returned by the scanner.  */
  public static final int UINT64_ = 298;
  /** Token number, to be returned by the scanner.  */
  public static final int _UINT64 = 299;
  /** Token number, to be returned by the scanner.  */
  public static final int FLOAT32_ = 300;
  /** Token number, to be returned by the scanner.  */
  public static final int _FLOAT32 = 301;
  /** Token number, to be returned by the scanner.  */
  public static final int FLOAT64_ = 302;
  /** Token number, to be returned by the scanner.  */
  public static final int _FLOAT64 = 303;
  /** Token number, to be returned by the scanner.  */
  public static final int STRING_ = 304;
  /** Token number, to be returned by the scanner.  */
  public static final int _STRING = 305;
  /** Token number, to be returned by the scanner.  */
  public static final int URL_ = 306;
  /** Token number, to be returned by the scanner.  */
  public static final int _URL = 307;
  /** Token number, to be returned by the scanner.  */
  public static final int OPAQUE_ = 308;
  /** Token number, to be returned by the scanner.  */
  public static final int _OPAQUE = 309;
  /** Token number, to be returned by the scanner.  */
  public static final int ATTR_BASE = 310;
  /** Token number, to be returned by the scanner.  */
  public static final int ATTR_BASETYPE = 311;
  /** Token number, to be returned by the scanner.  */
  public static final int ATTR_DAPVERSION = 312;
  /** Token number, to be returned by the scanner.  */
  public static final int ATTR_DDXVERSION = 313;
  /** Token number, to be returned by the scanner.  */
  public static final int ATTR_ENUM = 314;
  /** Token number, to be returned by the scanner.  */
  public static final int ATTR_HREF = 315;
  /** Token number, to be returned by the scanner.  */
  public static final int ATTR_NAME = 316;
  /** Token number, to be returned by the scanner.  */
  public static final int ATTR_NAMESPACE = 317;
  /** Token number, to be returned by the scanner.  */
  public static final int ATTR_SIZE = 318;
  /** Token number, to be returned by the scanner.  */
  public static final int ATTR_TYPE = 319;
  /** Token number, to be returned by the scanner.  */
  public static final int ATTR_VALUE = 320;
  /** Token number, to be returned by the scanner.  */
  public static final int ATTR_NS = 321;
  /** Token number, to be returned by the scanner.  */
  public static final int ATTR_XMLNS = 322;
  /** Token number, to be returned by the scanner.  */
  public static final int UNKNOWN_ATTR = 323;
  /** Token number, to be returned by the scanner.  */
  public static final int UNKNOWN_ELEMENT_ = 324;
  /** Token number, to be returned by the scanner.  */
  public static final int _UNKNOWN_ELEMENT = 325;
  /** Token number, to be returned by the scanner.  */
  public static final int TEXT = 326;
  /** Token number, to be returned by the scanner.  */
  public static final int ERROR = 327;
  /** Token number, to be returned by the scanner.  */
  public static final int UNKNOWN = 328;
  /** Token number, to be returned by the scanner.  */
  public static final int UNEXPECTED = 329;



  

  /**
   * Communication interface between the scanner and the Bison-generated
   * parser <tt>Dap4Parser</tt>.
   */
  public interface Lexer {
    

    /**
     * Method to retrieve the semantic value of the last scanned token.
     * @return the semantic value of the last scanned token.  */
    Object getLVal ();

    /**
     * Entry point for the scanner.  Returns the token identifier corresponding
     * to the next token and prepares to return the semantic value
     * of the token.
     * @return the token identifier corresponding to the next token. */
    int yylex () throws Exception;

    /**
     * Entry point for error reporting.  Emits an error
     * in a user-defined way.
     *
     * 
     * @param s The string for the error message.  */
     void yyerror (String s);
  }

  /** The object doing lexical analysis for us.  */
  private Lexer yylexer;
  
  



  /**
   * Instantiates the Bison-generated parser.
   * @param yylexer The scanner that will supply tokens to the parser.
   */
  public Dap4Parser (Lexer yylexer) {
    this.yylexer = yylexer;
    
  }

  private java.io.PrintStream yyDebugStream = System.err;

  /**
   * Return the <tt>PrintStream</tt> on which the debugging output is
   * printed.
   */
  public final java.io.PrintStream getDebugStream () { return yyDebugStream; }

  /**
   * Set the <tt>PrintStream</tt> on which the debug output is printed.
   * @param s The stream that is used for debugging output.
   */
  public final void setDebugStream(java.io.PrintStream s) { yyDebugStream = s; }

  private int yydebug = 0;

  /**
   * Answer the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   */
  public final int getDebugLevel() { return yydebug; }

  /**
   * Set the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   * @param level The verbosity level for debugging output.
   */
  public final void setDebugLevel(int level) { yydebug = level; }

  private final int yylex () throws Exception {
    return yylexer.yylex ();
  }
  protected final void yyerror (String s) {
    yylexer.yyerror (s);
  }

  

  protected final void yycdebug (String s) {
    if (yydebug > 0)
      yyDebugStream.println (s);
  }

  private final class YYStack {
    private int[] stateStack = new int[16];
    
    private Object[] valueStack = new Object[16];

    public int size = 16;
    public int height = -1;

    public final void push (int state, Object value			    ) {
      height++;
      if (size == height)
        {
	  int[] newStateStack = new int[size * 2];
	  System.arraycopy (stateStack, 0, newStateStack, 0, height);
	  stateStack = newStateStack;
	  

	  Object[] newValueStack = new Object[size * 2];
	  System.arraycopy (valueStack, 0, newValueStack, 0, height);
	  valueStack = newValueStack;

	  size *= 2;
	}

      stateStack[height] = state;
      
      valueStack[height] = value;
    }

    public final void pop () {
      height--;
    }

    public final void pop (int num) {
      // Avoid memory leaks... garbage collection is a white lie!
      if (num > 0) {
	java.util.Arrays.fill (valueStack, height - num + 1, height, null);
        
      }
      height -= num;
    }

    public final int stateAt (int i) {
      return stateStack[height - i];
    }

    public final Object valueAt (int i) {
      return valueStack[height - i];
    }

    // Print the state stack on the debug stream.
    public void print (java.io.PrintStream out)
    {
      out.print ("Stack now");

      for (int i = 0; i < height; i++)
        {
	  out.print (' ');
	  out.print (stateStack[i]);
        }
      out.println ();
    }
  }

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return success (<tt>true</tt>).  */
  public static final int YYACCEPT = 0;

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return failure (<tt>false</tt>).  */
  public static final int YYABORT = 1;

  /**
   * Returned by a Bison action in order to start error recovery without
   * printing an error message.  */
  public static final int YYERROR = 2;

  /**
   * Returned by a Bison action in order to print an error message and start
   * error recovery.  Formally deprecated in Bison 2.4.2's NEWS entry, where
   * a plan to phase it out is discussed.  */
  public static final int YYFAIL = 3;

  private static final int YYNEWSTATE = 4;
  private static final int YYDEFAULT = 5;
  private static final int YYREDUCE = 6;
  private static final int YYERRLAB1 = 7;
  private static final int YYRETURN = 8;

  private int yyerrstatus_ = 0;

  /**
   * Return whether error recovery is being done.  In this state, the parser
   * reads token until it reaches a known state, and then restarts normal
   * operation.  */
  public final boolean recovering ()
  {
    return yyerrstatus_ == 0;
  }

  private int yyaction (int yyn, YYStack yystack, int yylen) throws Exception
  {
    Object yyval;
    

    /* If YYLEN is nonzero, implement the default value of the action:
       `$$ = $1'.  Otherwise, use the top of the stack.

       Otherwise, the following line sets YYVAL to garbage.
       This behavior is undocumented and Bison
       users should not rely upon it.  */
    if (yylen > 0)
      yyval = yystack.valueAt (yylen - 1);
    else
      yyval = yystack.valueAt (0);

    yy_reduce_print (yyn, yystack);

    switch (yyn)
      {
	

/* Line 354 of lalr1.java  */
/* Line 422 of "F:\\svn\\yax\\java/src/yax/test/Dap4Parser.java"  */
	default: break;
      }

    yy_symbol_print ("-> $$ =", yyr1_[yyn], yyval);

    yystack.pop (yylen);
    yylen = 0;

    /* Shift the result of the reduction.  */
    yyn = yyr1_[yyn];
    int yystate = yypgoto_[yyn - yyntokens_] + yystack.stateAt (0);
    if (0 <= yystate && yystate <= yylast_
	&& yycheck_[yystate] == yystack.stateAt (0))
      yystate = yytable_[yystate];
    else
      yystate = yydefgoto_[yyn - yyntokens_];

    yystack.push (yystate, yyval);
    return YYNEWSTATE;
  }

  /* Return YYSTR after stripping away unnecessary quotes and
     backslashes, so that it's suitable for yyerror.  The heuristic is
     that double-quoting is unnecessary unless the string contains an
     apostrophe, a comma, or backslash (other than backslash-backslash).
     YYSTR is taken from yytname.  */
  private final String yytnamerr_ (String yystr)
  {
    if (yystr.charAt (0) == '"')
      {
        StringBuffer yyr = new StringBuffer ();
        strip_quotes: for (int i = 1; i < yystr.length (); i++)
          switch (yystr.charAt (i))
            {
            case '\'':
            case ',':
              break strip_quotes;

            case '\\':
	      if (yystr.charAt(++i) != '\\')
                break strip_quotes;
              /* Fall through.  */
            default:
              yyr.append (yystr.charAt (i));
              break;

            case '"':
              return yyr.toString ();
            }
      }
    else if (yystr.equals ("$end"))
      return "end of input";

    return yystr;
  }

  /*--------------------------------.
  | Print this symbol on YYOUTPUT.  |
  `--------------------------------*/

  private void yy_symbol_print (String s, int yytype,
			         Object yyvaluep				 )
  {
    if (yydebug > 0)
    yycdebug (s + (yytype < yyntokens_ ? " token " : " nterm ")
	      + yytname_[yytype] + " ("
	      + (yyvaluep == null ? "(null)" : yyvaluep.toString ()) + ")");
  }

  /**
   * Parse input from the scanner that was specified at object construction
   * time.  Return whether the end of the input was reached successfully.
   *
   * @return <tt>true</tt> if the parsing succeeds.  Note that this does not
   *          imply that there were no syntax errors.
   */
  public boolean parse () throws Exception, Exception
  {
    /// Lookahead and lookahead in internal form.
    int yychar = yyempty_;
    int yytoken = 0;

    /* State.  */
    int yyn = 0;
    int yylen = 0;
    int yystate = 0;

    YYStack yystack = new YYStack ();

    /* Error handling.  */
    int yynerrs_ = 0;
    

    /// Semantic value of the lookahead.
    Object yylval = null;

    int yyresult;

    yycdebug ("Starting parse\n");
    yyerrstatus_ = 0;


    /* Initialize the stack.  */
    yystack.push (yystate, yylval);

    int label = YYNEWSTATE;
    for (;;)
      switch (label)
      {
        /* New state.  Unlike in the C/C++ skeletons, the state is already
	   pushed when we come here.  */
      case YYNEWSTATE:
        yycdebug ("Entering state " + yystate + "\n");
        if (yydebug > 0)
          yystack.print (yyDebugStream);

        /* Accept?  */
        if (yystate == yyfinal_)
          return true;

        /* Take a decision.  First try without lookahead.  */
        yyn = yypact_[yystate];
        if (yyn == yypact_ninf_)
          {
            label = YYDEFAULT;
	    break;
          }

        /* Read a lookahead token.  */
        if (yychar == yyempty_)
          {
	    yycdebug ("Reading a token: ");
	    yychar = yylex ();
            
            yylval = yylexer.getLVal ();
          }

        /* Convert token to internal form.  */
        if (yychar <= EOF)
          {
	    yychar = yytoken = EOF;
	    yycdebug ("Now at end of input.\n");
          }
        else
          {
	    yytoken = yytranslate_ (yychar);
	    yy_symbol_print ("Next token is", yytoken,
			     yylval);
          }

        /* If the proper action on seeing token YYTOKEN is to reduce or to
           detect an error, take that action.  */
        yyn += yytoken;
        if (yyn < 0 || yylast_ < yyn || yycheck_[yyn] != yytoken)
          label = YYDEFAULT;

        /* <= 0 means reduce or error.  */
        else if ((yyn = yytable_[yyn]) <= 0)
          {
	    if (yyn == 0 || yyn == yytable_ninf_)
	      label = YYFAIL;
	    else
	      {
	        yyn = -yyn;
	        label = YYREDUCE;
	      }
          }

        else
          {
            /* Shift the lookahead token.  */
	    yy_symbol_print ("Shifting", yytoken,
			     yylval);

            /* Discard the token being shifted.  */
            yychar = yyempty_;

            /* Count tokens shifted since error; after three, turn off error
               status.  */
            if (yyerrstatus_ > 0)
              --yyerrstatus_;

            yystate = yyn;
            yystack.push (yystate, yylval);
            label = YYNEWSTATE;
          }
        break;

      /*-----------------------------------------------------------.
      | yydefault -- do the default action for the current state.  |
      `-----------------------------------------------------------*/
      case YYDEFAULT:
        yyn = yydefact_[yystate];
        if (yyn == 0)
          label = YYFAIL;
        else
          label = YYREDUCE;
        break;

      /*-----------------------------.
      | yyreduce -- Do a reduction.  |
      `-----------------------------*/
      case YYREDUCE:
        yylen = yyr2_[yyn];
        label = yyaction (yyn, yystack, yylen);
	yystate = yystack.stateAt (0);
        break;

      /*------------------------------------.
      | yyerrlab -- here on detecting error |
      `------------------------------------*/
      case YYFAIL:
        /* If not already recovering from an error, report this error.  */
        if (yyerrstatus_ == 0)
          {
	    ++yynerrs_;
	    yyerror (yysyntax_error (yystate, yytoken));
          }

        
        if (yyerrstatus_ == 3)
          {
	    /* If just tried and failed to reuse lookahead token after an
	     error, discard it.  */

	    if (yychar <= EOF)
	      {
	      /* Return failure if at end of input.  */
	      if (yychar == EOF)
	        return false;
	      }
	    else
	      yychar = yyempty_;
          }

        /* Else will try to reuse lookahead token after shifting the error
           token.  */
        label = YYERRLAB1;
        break;

      /*---------------------------------------------------.
      | errorlab -- error raised explicitly by YYERROR.  |
      `---------------------------------------------------*/
      case YYERROR:

        
        /* Do not reclaim the symbols of the rule which action triggered
           this YYERROR.  */
        yystack.pop (yylen);
        yylen = 0;
        yystate = yystack.stateAt (0);
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------------------.
      | yyerrlab1 -- common code for both syntax error and YYERROR.  |
      `-------------------------------------------------------------*/
      case YYERRLAB1:
        yyerrstatus_ = 3;	/* Each real token shifted decrements this.  */

        for (;;)
          {
	    yyn = yypact_[yystate];
	    if (yyn != yypact_ninf_)
	      {
	        yyn += yyterror_;
	        if (0 <= yyn && yyn <= yylast_ && yycheck_[yyn] == yyterror_)
	          {
	            yyn = yytable_[yyn];
	            if (0 < yyn)
		      break;
	          }
	      }

	    /* Pop the current state because it cannot handle the error token.  */
	    if (yystack.height == 1)
	      return false;

	    
	    yystack.pop ();
	    yystate = yystack.stateAt (0);
	    if (yydebug > 0)
	      yystack.print (yyDebugStream);
          }

	

        /* Shift the error token.  */
        yy_symbol_print ("Shifting", yystos_[yyn],
			 yylval);

        yystate = yyn;
	yystack.push (yyn, yylval);
        label = YYNEWSTATE;
        break;

        /* Accept.  */
      case YYACCEPT:
        return true;

        /* Abort.  */
      case YYABORT:
        return false;
      }
  }

  // Generate an error message.
  private String yysyntax_error (int yystate, int tok)
  {
    if (errorVerbose)
      {
        int yyn = yypact_[yystate];
        if (yypact_ninf_ < yyn && yyn <= yylast_)
          {
	    StringBuffer res;

	    /* Start YYX at -YYN if negative to avoid negative indexes in
	       YYCHECK.  */
	    int yyxbegin = yyn < 0 ? -yyn : 0;

	    /* Stay within bounds of both yycheck and yytname.  */
	    int yychecklim = yylast_ - yyn + 1;
	    int yyxend = yychecklim < yyntokens_ ? yychecklim : yyntokens_;
	    int count = 0;
	    for (int x = yyxbegin; x < yyxend; ++x)
	      if (yycheck_[x + yyn] == x && x != yyterror_)
	        ++count;

	    // FIXME: This method of building the message is not compatible
	    // with internationalization.
	    res = new StringBuffer ("syntax error, unexpected ");
	    res.append (yytnamerr_ (yytname_[tok]));
	    if (count < 5)
	      {
	        count = 0;
	        for (int x = yyxbegin; x < yyxend; ++x)
	          if (yycheck_[x + yyn] == x && x != yyterror_)
		    {
		      res.append (count++ == 0 ? ", expecting " : " or ");
		      res.append (yytnamerr_ (yytname_[x]));
		    }
	      }
	    return res.toString ();
          }
      }

    return "syntax error";
  }


  /* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
     STATE-NUM.  */
  private static final byte yypact_ninf_ = -56;
  private static final byte yypact_[] =
  {
         7,   -56,    18,    36,   -56,   -56,   -56,   -56,   -56,   -56,
     -56,   -56,    -3,   -56,     1,     2,    -1,    10,   -56,   -56,
     -56,   -56,   -56,   -56,   -56,   -56,   -56,   -56,   -56,   -56,
     -56,   -56,   -56,   -56,   -56,   -56,   -56,   -56,   -56,    12,
     -56,   -56,   -56,    14,     4,    70,    16,    20,   -56,   -56,
     -56,    34,   -56,   -56,   -56,   -12,    93,   -56,   -56,   -56,
      -8,    -6,   -56,   -56,   -56,    74,    38,    22,    24,    81,
     -56,   -56,   -56,   -56,     6,   -56,   -56,   -56,   -56,    41,
     -56,    35,   -56,    44,   -56,   -56,   -56,   -56,   -56,   -56,
     -56,   -56,   -56,   -56,   -56,   -56,   -56,   -56,   -56,   -56,
     -56,   -56,   -56,   -56,   -56,   -56,   -56,   -56,    92,    97,
      43,   -56,   -56,    91,   -56,   -56,    88,   -19,   -56,   -56,
     -56,   -56
  };

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final byte yydefact_[] =
  {
         0,     3,     0,    11,     1,     8,     5,     6,     4,     7,
       9,    10,     0,     2,     0,     0,     0,     0,    83,    37,
      38,    39,    40,    41,    42,    43,    44,    45,    46,    47,
      48,    49,    50,    51,    16,    12,    13,    14,    34,     0,
      35,    15,    81,     0,     0,     0,     0,     0,    79,    52,
      75,    25,    69,    19,    18,     0,     0,    20,    29,    30,
       0,     0,    84,    86,    85,    87,     0,     0,     0,     0,
      17,    21,    28,    80,     0,    74,    76,    77,    78,     0,
      26,     0,    68,     0,    53,    54,    55,    56,    57,    58,
      59,    60,    61,    62,    63,    64,    65,    66,    67,    70,
      36,    71,    72,    23,    24,    22,    32,    33,     0,     0,
      91,    82,    88,     0,    31,    27,     0,     0,    73,    89,
      90,    92
  };

  /* YYPGOTO[NTERM-NUM].  */
  private static final byte yypgoto_[] =
  {
       -56,    99,   -56,   -56,   -56,   -56,   -56,    56,   -56,   -56,
     -56,   -56,   -56,    47,   -56,    53,   -56,   -56,   -56,   -56,
     -56,   -56,   -56,   -56,   -55,   -56,   -56,   -56,   -56,   -56
  };

  /* YYDEFGOTO[NTERM-NUM].  */
  private static final byte
  yydefgoto_[] =
  {
        -1,     2,     3,    12,    35,    45,    56,    57,    69,    65,
      80,    36,    48,    76,   108,    37,    38,    39,   100,    66,
     101,    40,    61,    60,    41,    42,    51,    81,   112,   117
  };

  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  private static final byte yytable_ninf_ = -1;
  private static final byte
  yytable_[] =
  {
         1,    13,    14,   120,    72,    73,    78,    74,    15,    16,
       1,   102,    16,    17,    75,    18,    17,    18,     4,    19,
      18,    20,    19,    21,    20,    22,    21,    23,    22,    24,
      23,    25,    24,    26,    25,    27,    26,    28,    27,    29,
      28,    30,    29,    31,    30,    32,    31,    33,    32,    67,
      33,    74,   121,    68,    82,    83,   110,    43,    49,   111,
      54,    18,    44,    46,    84,    47,    85,   106,    86,   107,
      87,    50,    88,    52,    89,    53,    90,    55,    91,    58,
      92,    59,    93,    79,    94,   104,    95,   103,    96,   105,
      97,     5,    98,     6,     7,    62,    63,     8,    64,    70,
      55,   109,     9,    10,    11,   113,   114,   115,   116,   118,
     119,    34,    71,    99,    77
  };

  /* YYCHECK.  */
  private static final byte
  yycheck_[] =
  {
         3,     4,     5,    22,    12,    60,    61,    13,    11,    15,
       3,    66,    15,    19,    20,    23,    19,    23,     0,    25,
      23,    27,    25,    29,    27,    31,    29,    33,    31,    35,
      33,    37,    35,    39,    37,    41,    39,    43,    41,    45,
      43,    47,    45,    49,    47,    51,    49,    53,    51,    61,
      53,    13,    71,    65,    16,    17,    21,    56,    59,    24,
      56,    23,    61,    61,    26,    63,    28,    61,    30,    63,
      32,    61,    34,    61,    36,    61,    38,     7,    40,    63,
      42,    61,    44,     9,    46,    61,    48,    65,    50,     8,
      52,    55,    54,    57,    58,    61,    62,    61,    64,     6,
       7,    60,    66,    67,    68,    61,    14,    10,    65,    18,
      22,    12,    56,    66,    61
  };

  /* STOS_[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
  private static final byte
  yystos_[] =
  {
         0,     3,    76,    77,     0,    55,    57,    58,    61,    66,
      67,    68,    78,     4,     5,    11,    15,    19,    23,    25,
      27,    29,    31,    33,    35,    37,    39,    41,    43,    45,
      47,    49,    51,    53,    76,    79,    86,    90,    91,    92,
      96,    99,   100,    56,    61,    80,    61,    63,    87,    59,
      61,   101,    61,    61,    56,     7,    81,    82,    63,    61,
      98,    97,    61,    62,    64,    84,    94,    61,    65,    83,
       6,    82,    12,    99,    13,    20,    88,    90,    99,     9,
      85,   102,    16,    17,    26,    28,    30,    32,    34,    36,
      38,    40,    42,    44,    46,    48,    50,    52,    54,    88,
      93,    95,    99,    65,    61,     8,    61,    63,    89,    60,
      21,    24,   103,    61,    14,    10,    65,   104,    18,    22,
      22,    71
  };

  /* TOKEN_NUMBER_[YYLEX-NUM] -- Internal symbol number corresponding
     to YYLEX-NUM.  */
  private static final short
  yytoken_number_[] =
  {
         0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,   311,   312,   313,   314,
     315,   316,   317,   318,   319,   320,   321,   322,   323,   324,
     325,   326,   327,   328,   329
  };

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
  private static final byte
  yyr1_[] =
  {
         0,    75,    76,    77,    77,    77,    77,    77,    77,    77,
      77,    78,    78,    78,    78,    78,    78,    79,    80,    80,
      81,    81,    82,    83,    83,    84,    84,    85,    86,    87,
      87,    88,    89,    89,    90,    90,    91,    92,    92,    92,
      92,    92,    92,    92,    92,    92,    92,    92,    92,    92,
      92,    92,    92,    93,    93,    93,    93,    93,    93,    93,
      93,    93,    93,    93,    93,    93,    93,    93,    93,    94,
      94,    94,    94,    95,    96,    97,    97,    97,    97,    98,
      98,    99,   100,   101,   101,   101,   101,   102,   102,   103,
     103,   104,   104
  };

  /* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
  private static final byte
  yyr2_[] =
  {
         0,     2,     4,     0,     2,     2,     2,     2,     2,     2,
       2,     0,     2,     2,     2,     2,     2,     4,     2,     2,
       1,     2,     3,     2,     2,     0,     2,     3,     4,     2,
       2,     3,     1,     1,     1,     1,     4,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     2,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     0,
       2,     2,     2,     3,     4,     0,     2,     2,     2,     0,
       2,     1,     5,     0,     2,     2,     2,     0,     2,     3,
       3,     0,     2
  };

  /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
     First, the terminals, then, starting at \a yyntokens_, nonterminals.  */
  private static final String yytname_[] =
  {
    "$end", "error", "$undefined", "GROUP_", "_GROUP", "ENUMERATION_",
  "_ENUMERATION", "ENUMCONST_", "_ENUMCONST", "NAMESPACE_", "_NAMESPACE",
  "DIMENSION_", "_DIMENSION", "DIM_", "_DIM", "ENUM_", "_ENUM", "MAP_",
  "_MAP", "STRUCTURE_", "_STRUCTURE", "VALUE_", "_VALUE", "ATTRIBUTE_",
  "_ATTRIBUTE", "CHAR_", "_CHAR", "BYTE_", "_BYTE", "INT8_", "_INT8",
  "UINT8_", "_UINT8", "INT16_", "_INT16", "UINT16_", "_UINT16", "INT32_",
  "_INT32", "UINT32_", "_UINT32", "INT64_", "_INT64", "UINT64_", "_UINT64",
  "FLOAT32_", "_FLOAT32", "FLOAT64_", "_FLOAT64", "STRING_", "_STRING",
  "URL_", "_URL", "OPAQUE_", "_OPAQUE", "ATTR_BASE", "ATTR_BASETYPE",
  "ATTR_DAPVERSION", "ATTR_DDXVERSION", "ATTR_ENUM", "ATTR_HREF",
  "ATTR_NAME", "ATTR_NAMESPACE", "ATTR_SIZE", "ATTR_TYPE", "ATTR_VALUE",
  "ATTR_NS", "ATTR_XMLNS", "UNKNOWN_ATTR", "UNKNOWN_ELEMENT_",
  "_UNKNOWN_ELEMENT", "TEXT", "ERROR", "UNKNOWN", "UNEXPECTED", "$accept",
  "group", "group_attr_list", "group_body", "enumdef", "enum_attr_list",
  "enumconst_list", "enumconst", "enumconst_attr_list", "namespace_list",
  "namespace", "dimdef", "dimdef_attr_list", "dimref", "dimref_attr_list",
  "variable", "simplevariable", "atomictype_", "_atomictype",
  "variabledef", "mapref", "structurevariable", "structuredef",
  "metadatalist", "metadata", "attribute", "attribute_attr_list",
  "value_list", "value", "text_list", null
  };

  /* YYRHS -- A `-1'-separated list of the rules' RHS.  */
  private static final byte yyrhs_[] =
  {
        76,     0,    -1,     3,    77,    78,     4,    -1,    -1,    77,
      61,    -1,    77,    57,    -1,    77,    58,    -1,    77,    66,
      -1,    77,    55,    -1,    77,    67,    -1,    77,    68,    -1,
      -1,    78,    79,    -1,    78,    86,    -1,    78,    90,    -1,
      78,    99,    -1,    78,    76,    -1,     5,    80,    81,     6,
      -1,    61,    56,    -1,    56,    61,    -1,    82,    -1,    81,
      82,    -1,     7,    83,     8,    -1,    61,    65,    -1,    65,
      61,    -1,    -1,    84,    85,    -1,     9,    60,    10,    -1,
      11,    87,    98,    12,    -1,    61,    63,    -1,    63,    61,
      -1,    13,    89,    14,    -1,    61,    -1,    63,    -1,    91,
      -1,    96,    -1,    92,    61,    94,    93,    -1,    25,    -1,
      27,    -1,    29,    -1,    31,    -1,    33,    -1,    35,    -1,
      37,    -1,    39,    -1,    41,    -1,    43,    -1,    45,    -1,
      47,    -1,    49,    -1,    51,    -1,    53,    -1,    15,    59,
      -1,    26,    -1,    28,    -1,    30,    -1,    32,    -1,    34,
      -1,    36,    -1,    38,    -1,    40,    -1,    42,    -1,    44,
      -1,    46,    -1,    48,    -1,    50,    -1,    52,    -1,    54,
      -1,    16,    -1,    -1,    94,    88,    -1,    94,    95,    -1,
      94,    99,    -1,    17,    61,    18,    -1,    19,    61,    97,
      20,    -1,    -1,    97,    88,    -1,    97,    90,    -1,    97,
      99,    -1,    -1,    98,    99,    -1,   100,    -1,    23,   101,
      84,   102,    24,    -1,    -1,   101,    61,    -1,   101,    64,
      -1,   101,    62,    -1,    -1,   102,   103,    -1,    21,    65,
      22,    -1,    21,   104,    22,    -1,    -1,   104,    71,    -1
  };

  /* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
     YYRHS.  */
  private static final short yyprhs_[] =
  {
         0,     0,     3,     8,     9,    12,    15,    18,    21,    24,
      27,    30,    31,    34,    37,    40,    43,    46,    51,    54,
      57,    59,    62,    66,    69,    72,    73,    76,    80,    85,
      88,    91,    95,    97,    99,   101,   103,   108,   110,   112,
     114,   116,   118,   120,   122,   124,   126,   128,   130,   132,
     134,   136,   138,   141,   143,   145,   147,   149,   151,   153,
     155,   157,   159,   161,   163,   165,   167,   169,   171,   173,
     174,   177,   180,   183,   187,   192,   193,   196,   199,   202,
     203,   206,   208,   214,   215,   218,   221,   224,   225,   228,
     232,   236,   237
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,    75,    75,    78,    80,    81,    82,    83,    84,    85,
      86,    89,    91,    92,    93,    94,    95,    99,   103,   104,
     108,   109,   114,   118,   119,   122,   124,   128,   132,   136,
     137,   141,   145,   146,   150,   151,   156,   161,   162,   163,
     164,   165,   166,   167,   168,   169,   170,   171,   172,   173,
     174,   175,   176,   180,   181,   182,   183,   184,   185,   186,
     187,   188,   189,   190,   191,   192,   193,   194,   195,   198,
     200,   201,   202,   206,   210,   213,   215,   216,   217,   220,
     222,   226,   230,   236,   238,   239,   240,   243,   245,   249,
     250,   253,   255
  };

  // Report on the debug stream that the rule yyrule is going to be reduced.
  private void yy_reduce_print (int yyrule, YYStack yystack)
  {
    if (yydebug == 0)
      return;

    int yylno = yyrline_[yyrule];
    int yynrhs = yyr2_[yyrule];
    /* Print the symbols being reduced, and their result.  */
    yycdebug ("Reducing stack by rule " + (yyrule - 1)
	      + " (line " + yylno + "), ");

    /* The symbols being reduced.  */
    for (int yyi = 0; yyi < yynrhs; yyi++)
      yy_symbol_print ("   $" + (yyi + 1) + " =",
		       yyrhs_[yyprhs_[yyrule] + yyi],
		       ((yystack.valueAt (yynrhs-(yyi + 1)))));
  }

  /* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
  private static final byte yytranslate_table_[] =
  {
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
      65,    66,    67,    68,    69,    70,    71,    72,    73,    74
  };

  private static final byte yytranslate_ (int t)
  {
    if (t >= 0 && t <= yyuser_token_number_max_)
      return yytranslate_table_[t];
    else
      return yyundef_token_;
  }

  private static final int yylast_ = 114;
  private static final int yynnts_ = 30;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 4;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 75;

  private static final int yyuser_token_number_max_ = 329;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */
/* Unqualified %code blocks.  */

/* Line 876 of lalr1.java  */
/* Line 13 of "F:\\svn\\yax\\java/src/yax/test/dap4.y"  */


    /**
     * Instantiates the Bison-generated parser.
     *
     */

    public Dap4Parser(String document, int flags)
	    throws Exception
    {
	this.dap4Lexer = new Dap4Lexer(document,flags);
	this.yylexer = (Dap4Parser.Lexer)this.dap4Lexer;
    }





/* Line 876 of lalr1.java  */
/* Line 1122 of "F:\\svn\\yax\\java/src/yax/test/Dap4Parser.java"  */

}


/* Line 880 of lalr1.java  */
/* Line 258 of "F:\\svn\\yax\\java/src/yax/test/dap4.y"  */


