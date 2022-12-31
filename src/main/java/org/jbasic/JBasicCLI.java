/****************************************************************************
 * Copyright (C) 2022 by Frederik Tobner                                    *
 *                                                                          *
 * This file is part of JBASIC.                                             *
 *                                                                          *
 * Permission to use, copy, modify, and distribute this software and its    *
 * documentation under the terms of the GNU General Public License is       *
 * hereby granted.                                                          *
 * No representations are made about the suitability of this software for   *
 * any purpose.                                                             *
 * It is provided "as is" without express or implied warranty.              *
 * See the <"https://www.gnu.org/licenses/gpl-3.0.html">GNU General Public  *
 * License for more details.                                                *
 ****************************************************************************/

 /**
 * @file JBasicCli.java
 * @brief The command line interface of the interpreter
 */

package org.jbasic;

import org.jbasic.interpreter.JBasicInterpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @brief The command line interface of the interpreter
 */
public class JBasicCLI {

    /**
     * Main entry point of the interpreter
     * @param args The arguments that were provided by the user
     */
    public static void main(String[] args) {
        InputStream inputStream = null;
        JBasicInterpreter interpreter = null;
        try {
            if (args.length != 1) {
                System.out.println("Usage: JBASIC <script>");
                System.exit(-1);
            }
            // Open file under specified path
            inputStream = new FileInputStream(args[0]);
            // Creates a new JBasicInterpreter object instance using the standard input, output and error output stream
            interpreter = new JBasicInterpreter(System.in, System.out, System.err);
            // Interprets file content
            interpreter.run(inputStream);
            interpreter.clear();

        }
        catch (IOException exception) {
            System.out.println("Error running script: " + exception.getMessage());
            System.exit(-1);
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            if (interpreter != null) {
                interpreter.clear();
            }
        }
    }
}
