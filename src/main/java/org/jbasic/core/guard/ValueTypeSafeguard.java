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
 * @file VariableSafeguard.java
 * @brief Guarding functions for JBASIC values.
 */

package org.jbasic.core.guard;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jbasic.error.type.TypeException;
import org.jbasic.languageModels.JBasicValue;

/**
 * @brief Guarding functions for JBASIC values.
 */
public class ValueTypeSafeguard {

    /**
     * Ensures that a value is an array
     *
     * @param message The first part of the error message that is omitted if the value is not an array
     * @param value The value that is safeguarded
     * @param context The parsing context where the value was used
     */
    public static void guaranteeValueIsArray(String message, JBasicValue value, ParserRuleContext context) throws TypeException {
        if (!value.isAnArrayValue()) {
            throw new TypeException(message + ". Value is not an array", context);
        }
    }

    /**
     * Ensures that a value is a numerical value
     *
     * @param message The first part of the error message that is omitted if the value is not numerical
     * @param value The value that is safeguarded
     * @param context The parsing context where the value was used
     */
    public static void guaranteeValueIsNumerical(String message, JBasicValue value, ParserRuleContext context) throws TypeException {
        if (!value.isANumericalValue()) {
            throw new TypeException(message + ". Value is not a number", context);
        }
    }

    /**
     * Ensures that a value is a string
     *
     * @param message The first part of the error message that is omitted if the value is not a string
     * @param value The value that is safeguarded
     * @param context The parsing context where the value was used
     */
    public static void guaranteeValueIsString(String message, JBasicValue value, ParserRuleContext context) throws TypeException {
        if (!value.isAStringValue()) {
            throw new TypeException(message + ". Value is not a string", context);
        }
    }
}
