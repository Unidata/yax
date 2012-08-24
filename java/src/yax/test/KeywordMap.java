/* Copyright 2009, UCAR/Unidata and OPeNDAP, Inc.
   See the COPYRIGHT file for more information. */

package yax.test;

import java.util.HashMap;
import java.util.Map;

public class KeywordMap
{
    static public class Keyword
    {
        String name;
        int opentag;
        int closetag;
        int attrtag;
        public Keyword(String name, int opentag, int closetag, int attrtag)
        {
            this.name = name;
            this.opentag = opentag;
            this.closetag = closetag;
            this.attrtag = attrtag;
        }
    };
    
    Map<String,Keyword> keywords;
    
    public KeywordMap()
    {
        keywords = new HashMap<String,Keyword>();
    }
    
    public void add(Keyword keyword)
    {
	keywords.put(keyword.name,keyword);
    }

    public void add(String name, int opentag, int closetag, int attrtag)
    {
	add(new Keyword(name,opentag,closetag,attrtag));
    }

    public Keyword get(String name)
    {
	if(name == null) return null;
	return keywords.get(name);
    }
    

} // class KeywordMap    
    
