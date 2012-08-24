/* Copyright 2009, UCAR/Unidata and OPeNDAP, Inc.
   See the COPYRIGHT file for more information. */

package yax.lex;

/**
Walk a DOM tree in prefix order, left-to-right, depth first.
*/

public class Token
{

    Type type = Type.UNDEFINED;
    String name = null;
    String value = null;
    String text = null;

} // class Token

