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
 * @file UndefinedVariableException.java
 * @brief Error for an undefined variable ...
 */
package org.jbasic.error.variable;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jbasic.error.InterpreterBaseException;

/**
 * @brief Error for an undefined variable ...
 */
@SuppressWarnings("serial")
public class UndefinedVariableException extends InterpreterBaseException {
    public UndefinedVariableException(String message, ParserRuleContext context) {
        super(message, context);
    }
}
