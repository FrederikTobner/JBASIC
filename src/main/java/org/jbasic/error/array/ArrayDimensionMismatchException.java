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
 * @file ArrayDimensionUnsupportedException.java
 * @brief Error for mismatch array size and amount of arguments in a set-index-statement or get-index-expression...
 */
package org.jbasic.error.array;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jbasic.error.InterpreterBaseException;

/**
 * @brief Error for mismatch array size and amount of arguments in a set-index-statement or get-index-expression...
 */
@SuppressWarnings("serial")
public class ArrayDimensionMismatchException extends InterpreterBaseException {
    public ArrayDimensionMismatchException(String message, ParserRuleContext context) {
        super(message, context);
    }
}
