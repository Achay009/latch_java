package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Latch {

    static  boolean hadError = false;

    static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    public static void main(String[] args) throws IOException {
	    if (args.length > 1){
            System.out.println("Usage : lch [script]");
        }else if (args.length == 1){
	        runFile(args[0]);
        }else{
	        runPrompt();
        }

    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;){
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
            hadError = false;
        }
    }

    private static void runFile(String path ) throws IOException{
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hadError) System.exit(65);
    }

    private static void run(String source) {
        System.out.println(" Scanning ARGS:  "+ "[" + source + "]");
        logger.info(" Scanning ARGS:  "+ "[" + source + "]");
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        for (Token token : tokens){
            System.out.println(token);
        }
    }

    static void error(int line, String message){
       report(line, "", message);
    }

    static void report(int line , String where, String message){
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        logger.warning("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
}
