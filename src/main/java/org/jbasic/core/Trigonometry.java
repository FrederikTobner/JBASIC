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
 * @file Trigonometry.java
 * @brief Trigonometric functions.
 */

package org.jbasic.core;

/**
 * @brief Trigonometric functions.
 */
public class Trigonometry {

    /**
     * Area tangent hyperbolicus, the inverse functions of tangent hyperbolicus
     *
     * @param value The value applied to the function
     */
    public static double areaTangentHyperbolicus(double value) {
        final double multiplicand;
        if (Double.doubleToRawLongBits(value) < 0) {
            value = Math.abs(value);
            multiplicand = -0.5d;
        }
        else {
            multiplicand = 0.5d;
        }
        return multiplicand * Math.log((1.0d + value) / (1.0d - value));
    }

    /**
     * Inverse hyperbolic sine, the inverse functions of the hyperbolic sine function
     *
     * @param value The value applied to the function
     */
    public static double inverseSineHyperbolicus(double value) {
        double sgn = 1.0D;
        if (value < 0.0D) {
            sgn = -1.0D;
            value = -value;
        }
        return sgn * Math.log(value + Math.sqrt(value * value + 1.0D));
    }

    /**
     * Calculate the inverse hyperbolic secant of an angle
     *
     * @param value The value applied to the function
     */
    public static double inverseSecantHyperbolicus(double value) {
        return Math.log((Math.sqrt(1 - value * value) + 1) / value);
    }
}
