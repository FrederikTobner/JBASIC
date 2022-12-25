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
 * @file InterpreterException.java
 * @brief Base exception for interpreter runtime errors.
 */

package org.jbasic;

/**
 * @brief Base exception for interpreter runtime errors.
 */
@SuppressWarnings("serial")
public abstract class InterpreterException extends RuntimeException {

    private int line;
    private int positionInLine;

    public InterpreterException(String message) {
        super(message);
    }

    public void setLocation(int line, int posInLine) {
        this.line = line;
        this.positionInLine = posInLine;
    }

    @Override
    public String getMessage() {
        return CoreUtils.formatErrorMessage(line, positionInLine, super.getMessage());
    }
}
