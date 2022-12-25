package org.jbasic;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The command line.
 */
public class JBasicCli {

    public static void main(String[] args) {
        InputStream inputStream = null;
        Interpreter interpreter = null;
        try {
            if (args.length == 0) {
                System.out.println("Usage: JBASIC <script>");
                System.exit(-1);
            }
            inputStream = new FileInputStream(args[0]);
            interpreter = new Interpreter(System.in, System.out, System.err);
            interpreter.run(inputStream);
            interpreter.clear();

        }
        catch (IOException e) {
            System.out.println("Error running program: " + e.getMessage());
            System.exit(-1);
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (interpreter != null) {
                interpreter.clear();
            }
        }
    }

}
