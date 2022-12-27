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
 * @file FunctionArityException.java
 * @brief Indicates a function call with the wrong amount of arguments.
 */
package org.jbasic;

/**
 * @brief Indicates a function call with the wrong amount of arguments.
 */
@SuppressWarnings("serial")
public class FunctionArityException extends InterpreterBaseException {
    public FunctionArityException(String message) {
        super(message);
    }
}
