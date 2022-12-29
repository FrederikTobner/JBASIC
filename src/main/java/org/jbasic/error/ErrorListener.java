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
 * @file ErrorListener.java
 * @brief Print parser errors to the given stderr..
 */

package org.jbasic.error;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.io.PrintStream;

/**
 * @brief Prints parser errors to the given stderr..
 */
public class ErrorListener extends BaseErrorListener {

    /// The standard error output stream
    private final PrintStream stderr;

    /// @brief Constructor of the error listener
    /// @param stderr The standard error output stream the error listener writes to
    public ErrorListener(PrintStream stderr) {
        this.stderr = stderr;
    }


     /// @brief Reports a syntax error
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line,
                            int characterPositionInLine,
                            String message,
                            RecognitionException exception) {
        this.stderr.println(message);
    }
}