/**
 This software is released under the terms of the Apache License version 2.
 For details of the license, see http://www.apache.org/licenses/LICENSE-2.0.
 */

package yax.test;

import yax.*;

import org.w3c.dom.*;
import org.xml.sax.*;

import java.io.*;

public class SaxTest
{

    static final String EXPECTED_VERSION = "2.00";

    // Simple subclass of SaxEventHandler
    static public class SaxTestHandler extends SaxEventHandler
    {
        public SaxTestHandler(String document) throws SAXException
        {
            super(document);
        }

        // Define the abstract methods
        public String[] orderedAttributes(String element)
        {
            return null;
        }

        public void yyevent(SaxEvent token)
            throws SAXException
        {
            String trace = null;
            trace = Util.trace(token, getFlags());
            System.out.printf("saxtest: %s\n", trace);
            System.out.flush();
        }
    }

    static public void
    main(String[] argv)
    {
        try {
            SaxTestHandler handler;
            SaxEventType tokentype = null;

	    if(!EXPECTED_VERSION.equals(SaxEventHandler.getVersion())) {
		System.err.printf("Version mismatch: %s :: %s\n",
			EXPECTED_VERSION, SaxEventHandler.getVersion());
		System.exit(1);
	    }
            TestOptions.getoptions(argv);
            handler = new SaxTestHandler(TestOptions.document);
            handler.setFlags(TestOptions.flags);
            if(TestOptions.saxtrace)
                handler.setTrace(true);
            handler.parse();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("No error");
        System.exit(0);
    }

} //SaxTest
