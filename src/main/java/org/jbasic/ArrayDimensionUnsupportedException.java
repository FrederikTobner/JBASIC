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
 * @brief Error for unsupported array dimensions ...
 */
package org.jbasic;

import org.antlr.v4.runtime.ParserRuleContext;

/**
 * @brief Error for unsupported array dimensions ...
 */
@SuppressWarnings("serial")
public class ArrayDimensionUnsupportedException extends InterpreterBaseException {
    public ArrayDimensionUnsupportedException(String message, ParserRuleContext context) {
        super(message, context);
    }
}
