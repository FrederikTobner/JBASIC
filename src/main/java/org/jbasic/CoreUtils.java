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

package org.jbasic;

import org.antlr.v4.runtime.ParserRuleContext;

import java.text.DecimalFormat;

/**
 * Utility methods.
 */
public class CoreUtils {

    public static final DecimalFormat numericalOutputFormat = new DecimalFormat("0.#");

    public static String formatErrorMessage(int line, int positionInLine, String message) {
        return "Error at [" + line + ", " + positionInLine + "]: " + message;
    }

    public static void addLocation(InterpreterException exception, ParserRuleContext context) {
        exception.setLocation(context.getStart().getLine(), context.getStart().getCharPositionInLine());
    }

    public static double atanh(double a) {
        final double mult;
        // check the sign bit of the raw representation to handle -0
        if (Double.doubleToRawLongBits(a) < 0) {
            a = Math.abs(a);
            mult = -0.5d;
        } else {
            mult = 0.5d;
        }
        return mult * Math.log((1.0d + a) / (1.0d - a));
    }

}