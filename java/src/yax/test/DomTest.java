package yax.test;

import yax.lex.*;

import org.apache.commons.cli.*;
import org.w3c.dom.*;

import java.io.*;

public class DomTest
{

    static public void
    main(String[] argv)
    {
        DomLexer lexer;
        int flags = Util.FLAG_NONE;
        DomEventType tokentype = null;
	Node node = null;
	
        String input;
        int i,c;

        try {

            Options options = new Options();
            options.addOption("t",false,"trim text");
            options.addOption("l",false,"Limit size of text printout");
            options.addOption("e",false,"Escape control characters");

            CommandLineParser parser = new PosixParser();
            CommandLine cmd = parser.parse(options, argv);

            argv = cmd.getArgs();

            if(argv.length == 0) {
              System.err.printf("no input\n");
              System.exit(1);
            }

            input = getinput(argv[0]);

            flags = Util.FLAG_NOCR; // always
            if(cmd.hasOption('t'))
                flags |= Util.FLAG_TRIMTEXT;
            if(cmd.hasOption('l'))
                flags |= Util.FLAG_ELIDETEXT;
            if(cmd.hasOption('e'))
                flags |= Util.FLAG_ESCAPE;

            lexer = new DomLexer(input);
            lexer.setFlags(flags);

            for(i=0;i<200;i++) {
                String trace = null;
                tokentype = lexer.nextToken();
                node = lexer.nextNode();
                trace = Util.trace(tokentype,node);
                System.out.printf("domtest: %s\n",trace);
                System.out.flush();
                if(tokentype == DomEventType.EOF)
                    break;
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


} //Domtest
