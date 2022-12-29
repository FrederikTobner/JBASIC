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
import org.jbasic.error.array.ArrayDimensionMismatchException;
import org.jbasic.error.array.ArrayDimensionUnsupportedException;

public class ArraySafeGuard {

    /**
     * Ensures the argument is a valid array dimension
     *
     * @param argument The argument that is safeguarded
     * @param context The parsing context of the array dimension
     */
    public static void guaranteeArrayDimensionIsValid(JBasicValue argument, ParserRuleContext context) {
        ValueSafeGuard.guaranteeValueIsNumerical("Could not create array", argument, context);
        if (argument.underlyingNumber() <= 0) {
            throw new ArrayDimensionUnsupportedException("Dimensions can not be negative or zero", context);
        }
        if (argument.underlyingNumber() != Math.round(argument.underlyingNumber())) {
            throw new ArrayDimensionUnsupportedException("Dimensions can not have numbers after the digit", context);
        }
    }

    /**
     *
     * @param array
     * @param specifiedDimensions
     * @param context
     */
    public static void guaranteeArrayDimensionsMatch(JBasicValue array, int specifiedDimensions, ParserRuleContext context) {
        switch (specifiedDimensions) {
            case 1:
                if (!array.isAnOneDimensionalArrayValue()) {
                    throw new ArrayDimensionMismatchException(
                            "The dimensions that were specified do not match the dimensions of the array",
                            context);
                }
                break;
            case 2:
                if (!array.isATwoDimensionalArrayValue()) {
                    throw new ArrayDimensionMismatchException(
                            "The dimensions that were specified do not match the dimensions of the array",
                            context);
                }
                break;
            case 3:
                if (!array.isAThreeDimensionalArrayValue()) {
                    throw new ArrayDimensionMismatchException(
                            "The dimensions that were specified do not match the dimensions of the array",
                            context);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + specifiedDimensions);
        }
    }
}
