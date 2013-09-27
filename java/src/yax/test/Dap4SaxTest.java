package yax.test;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.w3c.dom.Node;
import yax.lex.*;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

public abstract class Dap4SaxTest
{

    //////////////////////////////////////////////////
    // Constants

    //////////////////////////////////////////////////

    static public void
    main(String[] argv)
    {
        int flags = Util.FLAG_NONE;
        SaxEventType tokentype = null;
        Node[] nodep = new Node[]{null};

        String document;
        int i, c;
        Dap4SaxParser dap4pushparser = null;
        Dap4SaxEventHandler dap4eventhandler = null;

        try {
            Options options = new Options();
            options.addOption("w", false, "trim whitespace ");
            options.addOption("l", false, "Limit size of text printout");
            options.addOption("e", false, "Escape control characters");
            options.addOption("t", false, "Trace parser");
            options.addOption("d", false, "Parser debug");
            options.addOption("T", false, "Trace lexer");

            CommandLineParser clparser = new PosixParser();
            CommandLine cmd = clparser.parse(options, argv);

            argv = cmd.getArgs();

            if(argv.length == 0) {
                System.err.printf("no input\n");
                System.exit(1);
            }

            document = getinput(argv[0]);

            flags = Util.FLAG_NOCR; // always
            if(cmd.hasOption('w'))
                flags |= Util.FLAG_TRIMTEXT;
            if(cmd.hasOption('l'))
                flags |= Util.FLAG_ELIDETEXT;
            if(cmd.hasOption('e'))
                flags |= Util.FLAG_ESCAPE;
            if(cmd.hasOption('T'))
                flags |= Util.FLAG_TRACE;

            // 1. push parser
            dap4pushparser = new Dap4SaxParser();
            if(cmd.hasOption('d'))
                dap4pushparser.setDebugLevel(1);

            // 2. event handler
            dap4eventhandler = new Dap4SaxEventHandler(document, dap4pushparser);
            if(!dap4eventhandler.parse()) {
                System.err.println("Parse failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.err.println("No error");
        System.exit(0);
    }

    static String
    getinput(String filename)
        throws IOException
    {
        StringBuilder buf = new StringBuilder();
        FileReader file = new FileReader(filename);
        int c;

        while((c = file.read()) > 0) {
            buf.append((char) c);
        }
        return buf.toString();
    }
} // class Dap4SaxTest




