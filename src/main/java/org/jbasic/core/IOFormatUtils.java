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
 * @file IOFormatUtils.java
 * @brief IO formatting utility methods.
 */

package org.jbasic.core;

import java.text.DecimalFormat;


/**
 * @brief IO formatting utilities.
 * @details The IO formatting utilities of the interpreter
 */
public class IOFormatUtils {

    /// Decimal output format for numerical values (not decimal dot if the number is x.0)
    public static final DecimalFormat numericalOutputFormat = new DecimalFormat("0.#");

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