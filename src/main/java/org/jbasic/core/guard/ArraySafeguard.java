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
 * @file ArraySafeguard.java
 * @brief Guarding functions for JBASIC arrays.
 */

package org.jbasic.core.guard;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jbasic.languageModels.JBasicValue;
import org.jbasic.error.arrays.ArrayDimensionMismatchException;
import org.jbasic.error.arrays.ArrayDimensionUnsupportedException;

/**
 * @brief Guarding functions for JBASIC arrays.
 */
public class ArraySafeguard {

    /**
     * Ensures the argument is a valid array dimension
     *
     * @param argument The argument that is safeguarded
     * @param context The parsing context of the array dimension
     */
    public static void guaranteeArrayDimensionIsValid(JBasicValue argument, ParserRuleContext context) {
        ValueTypeSafeguard.guaranteeValueIsNumerical("Could not create array", argument, context);
        if (argument.underlyingNumber() <= 0) {
            throw new ArrayDimensionUnsupportedException("Dimensions can not be negative or zero", context);
        }
        NumericalValueSafeguard.guaranteeIsWhole("Dimensions can not have numbers after the digit",
                argument.underlyingNumber(), context);
    }

    /**
     * Ensures that the amount of dimensions of an array and the specified dimensions in a statement match
     *
     * @param array The array that is accessed
     * @param specifiedDimensions The amount of specified dimensions when the array was accessed
     * @param context The parsing context where the array was accessed
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
