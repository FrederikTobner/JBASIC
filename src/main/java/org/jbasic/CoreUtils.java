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
 * @file CoreUtils.java
 * @brief Core utility methods.
 */

package org.jbasic;

import org.antlr.v4.runtime.ParserRuleContext;

import java.text.DecimalFormat;
import java.util.List;


/**
 * @brief Core utility methods
 * @details The core utility methods of the interpreter
 */
public class CoreUtils {

    /// Decimal output format for numerical values
    public static final DecimalFormat numericalOutputFormat = new DecimalFormat("0.#");

    /**
     * Adds line information and the position in the line to an InterpreterException
     *
     * @param exception The InterpreterException where that is enriched
     * @param context   The ParserRRuleContext where the error occurred
     */
    public static void addLocationToException(InterpreterBaseException exception, ParserRuleContext context) {
        exception.setLocation(context.getStart().getLine(), context.getStart().getCharPositionInLine());
    }

    public static void assertArrity(String functionName, List<basic.JBasicParser.ExpressionContext> arguments, int expectedArgumentCount) {
        if (arguments.size() != expectedArgumentCount) {
            throw new FunctionArityException(functionName + " can not be called with 0 arguments");
        }
    }

    /**
     * Area tangent hyperbolicus, the inverse functions of tangent hyperbolicus
     *
     * @param value The value applied to the function
     */
    public static double areaTangentHyperbolicus(double value) {
        final double multiplicand;
        if (Double.doubleToRawLongBits(value) < 0) {
            value = Math.abs(value);
            multiplicand = -0.5d;
        } else {
            multiplicand = 0.5d;
        }
        return multiplicand * Math.log((1.0d + value) / (1.0d - value));
    }

    /**
     * Formats an error message with the line and the position in the line where the error occurred
     *
     * @param line           The line where the error occurred
     * @param positionInLine The position in the line where the error occurred
     * @param message        The error message that is displayed
     */
    public static String formatErrorMessage(int line, int positionInLine, String message) {
        return "Error at [" + line + ", " + positionInLine + "]: " + message;
    }
}