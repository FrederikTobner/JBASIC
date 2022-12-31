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
 * @file NumericalValueSafeguard.java
 * @brief Guarding functions for numerical JBASIC values.
 */

package core.guard;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jbasic.error.labels.InvalidNumericalFormatException;

/**
 * @brief Guarding functions for numerical JBASIC values.
 */
public class NumericalValueSafeguard {

    /**
     * Ensures that a value is an array
     *
     * @param message The first part of the error message that is omitted if the value is not an array
     * @param underlyingValue The value that is safeguarded
     * @param context The parsing context where the value was used
     */
    public static void guaranteeIsWhole(String message, double underlyingValue, ParserRuleContext context)
            throws InvalidNumericalFormatException {
        if (underlyingValue != Math.round(underlyingValue)) {
            throw new InvalidNumericalFormatException(message, context);
        }
    }
}
