package yax.test;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.w3c.dom.Node;
import yax.lex.Type;
import yax.lex.Util;

import java.io.FileReader;
import java.io.IOException;

public abstract class Dap4Test
{

    Dap4Lexer dap4Lexer = null;

    static public void
    main(String[] argv)
    {
        int flags = Util.FLAG_NONE;
        Type tokentype = null;
	Node[] nodep = new Node[]{null};

        String input;
        int i,c;
	Dap4Parser parser = null;

        try {

            Options options = new Options();
            options.addOption("w",false,"trim whitespace ");
            options.addOption("l",false,"Limit size of text printout");
            options.addOption("e",false,"Escape control characters");
            options.addOption("t",false,"Trace parser");
            options.addOption("T",false,"Trace lexer");

            CommandLineParser clparser = new PosixParser();
            CommandLine cmd = clparser.parse(options, argv);

            argv = cmd.getArgs();

            if(argv.length == 0) {
              System.err.printf("no input\n");
              System.exit(1);
            }

            input = getinput(argv[0]);

            flags = Util.FLAG_NOCR; // always
            if(cmd.hasOption('w'))
                flags |= Util.FLAG_TRIMTEXT;
            if(cmd.hasOption('l'))
                flags |= Util.FLAG_ELIDETEXT;
            if(cmd.hasOption('e'))
                flags |= Util.FLAG_ESCAPE;
              if(cmd.hasOption('T'))
                flags |= Util.FLAG_TRACE;

	    parser = new Dap4Parser(input,flags);
             if(cmd.hasOption('t'))
                parser.setDebugLevel(1);

	    if(!parser.parse()) {
		System.err.println("Parse failed");
	    }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("No error");
        System.exit(0);
    }

    static String
    getinput(String filename)
        throws IOException
    {
        StringBuilder buf = new StringBuilder();
        FileReader file = new FileReader(filename);
        int c;

        while((c=file.read()) > 0) {
            buf.append((char)c);
        }
        return buf.toString();
    }
} // class Dap4Parser



