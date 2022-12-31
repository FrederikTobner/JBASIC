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

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.jbasic.interpreter.JBasicInterpreter;

import java.io.FileInputStream;
import java.io.FileReader;
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

        if (args.length != 1) {
            System.out.println("Usage: JBASIC <script>");
            System.exit(64);
        }
        if ("--version".equals(args[0]) || "-v".equals(args[0])) {
            showVersion();
            return;
        }
        runScript(args[0]);
    }

    /**
     * Runs a JBASIC script
     * @param path The path where the script is stored
     */
    private static void runScript(String path) {
        InputStream inputStream = null;
        JBasicInterpreter interpreter = null;
        try {
            // Open file under specified path
            inputStream = new FileInputStream(path);
            // Creates a new JBasicInterpreter object instance using the standard input, output and error output stream
            interpreter = new JBasicInterpreter(System.in, System.out, System.err);
            // Interprets file content
            interpreter.run(inputStream);
            interpreter.clear();

        }
        catch (IOException exception) {
            System.out.println("Error running script: " + exception.getMessage());
            System.exit(74);
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

    /**
     * Reads the project version from the pom and displays it in the console
     */
    private static void showVersion() {
        // Reading the version from the pom
        MavenXpp3Reader reader = new MavenXpp3Reader();
        try {
            Model model = reader.read(new FileReader("pom.xml"));
            System.out.println("Version: " + model.getVersion());
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
            System.exit(74);
        }
    }
}
