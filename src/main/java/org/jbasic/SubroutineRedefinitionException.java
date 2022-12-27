/**
 * Copyright (C) 2022 by Frederik Tobner                                    *
 * *
 * This file is part of JBASIC.                                             *
 * *
 * Permission to use, copy, modify, and distribute this software and its    *
 * documentation under the terms of the GNU General Public License is       *
 * hereby granted.                                                          *
 * No representations are made about the suitability of this software for   *
 * any purpose.                                                             *
 * It is provided "as is" without express or implied warranty.              *
 * See the <"https://www.gnu.org/licenses/gpl-3.0.html">GNU General Public  *
 * License for more details.                                                *
 *
 * @file SubroutineRedefinitionException.java
 * @brief Redefinition of a subroutine error.
 */

/**
 * @file SubroutineRedefinitionException.java
 * @brief Redefinition of a subroutine error.
 */

package org.jbasic;

/**
 * @brief Redefinition of a subroutine error.
 */
@SuppressWarnings("serial")
public class SubroutineRedefinitionException extends InterpreterBaseException {

    /**
     * Constructor of the SubroutineRedefinitionException
     * @param message The message of the exception
     */
    public SubroutineRedefinitionException(String message) {
        super(message);
    }
}