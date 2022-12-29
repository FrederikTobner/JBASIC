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

package org.jbasic.core.safeguard;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jbasic.languageModels.JBasicValue;
import org.jbasic.error.type.TypeException;
import org.jbasic.error.variable.UndefinedVariableException;

public class ValueSafeGuard {

    /**
     * Ensures that a variable is defined
     *
     * @param value
     * @param variableName
     * @param context
     */
    public static void guaranteeVariableIsDefined(JBasicValue value, String variableName, ParserRuleContext context) {
        if (value == null) {
            throw new UndefinedVariableException(
                    "A variable with the name " + variableName + "is not defined",
                    context);
        }
    }

    /**
     * Ensures that aa value is an array
     *
     * @param message
     * @param argument
     * @param context
     */
    public static void guaranteeValueIsArray(String message, JBasicValue argument, ParserRuleContext context) {
        if (!argument.isAnArrayValue()) {
            throw new TypeException(message + ". Value is not an array", context);
        }
    }

    /**
     *
     * @param message
     * @param argument
     * @param context
     */
    public static void guaranteeValueIsNumerical(String message, JBasicValue argument, ParserRuleContext context) {
        if (!argument.isANumericalValue()) {
            throw new TypeException(message + ". Value is not a number", context);
        }
    }

    /**
     *
     * @param message
     * @param argument
     * @param context
     */
    public static void guaranteeValueIsString(String message, JBasicValue argument, ParserRuleContext context) {
        if (!argument.isAStringValue()) {
            throw new TypeException(message + ". Value is not a string", context);
        }
    }
}
