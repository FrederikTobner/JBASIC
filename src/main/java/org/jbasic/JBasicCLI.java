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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @brief The command line interface of the interpreter
 */
public class JBasicCLI {

    /**
     * Main entry point of the interpreter
     * @param args The arguments the interpreter that were provided by the user
     */
    public static void main(String[] args) {
        InputStream inputStream = null;
        Interpreter interpreter = null;
        try {
            if (args.length != 1) {
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
