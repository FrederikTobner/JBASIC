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
 * @file TypeException.java
 * @brief Type mismatch error.
 */

package org.jbasic;

/**
 * @brief Type mismatch error.
 */
@SuppressWarnings("serial")
public class TypeException extends InterpreterException {

    /// Constructor of a type exception
    /// @param message The error message of the type exception
    public TypeException(String message) {
        super(message);
    }

}
