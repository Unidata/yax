/* Copyright 2009, UCAR/Unidata and OPeNDAP, Inc.
   See the COPYRIGHT file for more information. */

package yax.lex;

/* Enumerate the kinds of Sax Events received by the SaxEventHandler */

public enum SaxEventType {
    STARTDOCUMENT,
    ENDDOCUMENT,
    STARTELEMENT,
    ENDELEMENT,
    ATTRIBUTE,
    CHARACTERS;
}
