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
 * @file InterpreterBaseException.java
 * @brief Base exception for interpreter runtime errors.
 */

package org.jbasic;

import org.antlr.v4.runtime.ParserRuleContext;

/**
 * @brief Base exception for interpreter runtime errors.
 */
@SuppressWarnings("serial")
public abstract class InterpreterBaseException extends RuntimeException {

    private final int line;
    private final int positionInLine;

    public InterpreterBaseException(String message, ParserRuleContext context) {
        super(message);
        this.line = context.getStart().getLine();
        this.positionInLine = context.getStart().getCharPositionInLine();
    }

    @Override
    public String getMessage() {
        return CoreUtils.formatErrorMessage(this.line, this.positionInLine, super.getMessage());
    }
}
