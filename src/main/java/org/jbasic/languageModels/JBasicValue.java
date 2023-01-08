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
  * @file JBasicValue.java
  * @brief Value class that encapsulates numeric and string values and the corresponding operations.
  */

package org.jbasic.languageModels;

import jbasic.JBasicParser.NegateExpressionContext;
import org.antlr.v4.runtime.ParserRuleContext;
import org.jbasic.core.IOFormatter;
import org.jbasic.core.guard.ValueTypeSafeguard;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * @brief Value class 
 * @details The class encapsulates arrays, numerical and string values and their corresponding operations.
 */
public class JBasicValue {

    /// False value constant
    public static final JBasicValue FalseValue = new JBasicValue(0);
    /// False value constant
    public static final JBasicValue TrueValue = new JBasicValue(1);
    /// null value constant
    public static final JBasicValue NullValue = new JBasicValue((Object)null);

    /// Underlying value
    private final Object value;

    /** Creates a new Value object instance that has a string as the underlying value
     * @param value The underlying string value of the newly created value
     */
    public JBasicValue(String value) {
        this.value = value;
    }

    /**
     * Creates a new Value object instance that has a double as the underlying value
     * @param value The underlying double of the newly created value
     */

    public JBasicValue(double value) {
        this.value = value;
    }

    /**
     * @param value The underlying double of the newly created value
     * @brief Creates a new Value object instance that has an object as the underlying value
     * @details This method is only called by the two other public constructors of this class
     */
    private JBasicValue(Object value) {
        this.value = value;
    }

    public JBasicValue(JBasicValue[] values) {
        this.value = values;
    }

    public JBasicValue(JBasicValue[][] values) {
        this.value = values;
    }

    public JBasicValue(JBasicValue[][][] values) {
        this.value = values;
    }

    /**
     * Performs an addition with the underlying numerical value of this object instance and the underlying numerical value of another object instance
     *
     * @param right The addend of the addition
     * @param context The parsing context of the addition expression
     * @return The underlying numerical value of this Value object instance added to the underlying numerical value of the other Value object instance
     */
    public JBasicValue add(JBasicValue right, ParserRuleContext context) {
        if (this.isAStringValue() && right.isAStringValue()) {
            return new JBasicValue(this.underlyingString() + right.underlyingString());
        }
        else if (this.isAStringValue() && right.isANumericalValue()) {
            return new JBasicValue(this.underlyingString() + IOFormatter.numericalOutputFormat.format(right.underlyingNumber()));
        }
        else if (this.isANumericalValue() && right.isAStringValue()) {
            return new JBasicValue(IOFormatter.numericalOutputFormat.format(this.underlyingNumber()) + right.underlyingString());
        }
        else {
            return this.arithmeticEvaluation(right, Double::sum, context);
        }
    }

    /**
     * Interprets the value as part of an and expression
     *
     * @param right   The value right of the currently evaluated value
     * @param context The parsing context of the Value instance
     * @return True if the value and the right value are truthy
     */
    public JBasicValue and(JBasicValue right, ParserRuleContext context) {
        return this.isTruthy(context) && right.isTruthy(context) ? TrueValue : FalseValue;
    }

    /**
     * Performs an arithmetic evaluation on a binary expression
     * @param right The right operand in the expression
     * @param operator The operator of the expression
     * @param context The parsing context of the binary expression
     * @return The result of the arithmetic evaluation
     */
    private JBasicValue arithmeticEvaluation(JBasicValue right, BiFunction<Double, Double, Double> operator, ParserRuleContext context) {
        ValueTypeSafeguard.guaranteeValueIsNumerical("Could not evaluate arithmetic expression", this, context);
        ValueTypeSafeguard.guaranteeValueIsNumerical("Could not evaluate arithmetic expression", right, context);
        return new JBasicValue(operator.apply(this.underlyingNumber(), right.underlyingNumber()));
    }

    /**
     * Divides this Value object instance with another object instance
     *
     * @param right   The divisor of the division
     * @param context The parsing context of the division expression
     * @return This Value object instance divided by the divisor 'right'
     */
    public JBasicValue divide(JBasicValue right, ParserRuleContext context) {
        return this.arithmeticEvaluation(right, (l, r) -> l / r, context);
    }

    /**
     * Determines if this Value object instance is equal to another Value object instance
     *
     * @param right   The other Value object instance
     * @param context The parsing context of the 'equal expression'
     * @return true if both values are equal, false if not
     */
    public JBasicValue equal(JBasicValue right, ParserRuleContext context) {
        if (this.isANumericalValue() && right.isANumericalValue()) {
            return this.compare(right, Objects::equals, context);
        }
        else if (this.isAStringValue() && right.isAStringValue()) {
            return this.underlyingString().equals(right.underlyingString()) ? TrueValue : FalseValue;
        }
        else if (this.isAnOneDimensionalArrayValue() && right.isAnOneDimensionalArrayValue()) {
            return Arrays.equals(this.underlyingOneDimensionalArray(),
                    right.underlyingOneDimensionalArray()) ? TrueValue : FalseValue;
        }
        else if (this.isATwoDimensionalArrayValue() && right.isATwoDimensionalArrayValue()) {
            return Arrays.deepEquals(this.underlyingTwoDimensionalArray(),
                    right.underlyingTwoDimensionalArray()) ? TrueValue : FalseValue;
        }
        else if (this.isAThreeDimensionalArrayValue() && right.isAThreeDimensionalArrayValue()) {
            return Arrays.deepEquals(this.underlyingThreeDimensionalArray(),
                    right.underlyingThreeDimensionalArray()) ? TrueValue : FalseValue;
        }
        return FalseValue;
    }

    /**
     * Determines whether a Value instance and another java object are equal
     *
     * @param obj The object that is compared with the Value instance
     * @return A boolean value that indicates whether the two values are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof JBasicValue)) {
            return false;
        }

        JBasicValue otherValue = (JBasicValue) obj;

        if (this.isNotANumericalValue() != otherValue.isNotANumericalValue()) {
            return false;
        }
        return Objects.equals(this.value, otherValue.value);
    }

    /**
     * Determines if this Value object instance is greater than another Value object instance
     *
     * @param right   The other Value object instance
     * @param context The parsing context of the 'greater-then expression'
     * @return true if this Value object instance is greater than the other Value object instance, false if not
     */
    public JBasicValue greaterThen(JBasicValue right, ParserRuleContext context) {
        return this.compare(right, (l, r) -> l > r, context);
    }

    /**
     * Determines if this Value object instance is greater than or equal to another Value object instance
     *
     * @param right   The other Value object instance
     * @param context The parsing context of the 'greater-then-equal expression'
     * @return true if this Value object instance is greater or equal to the other Value object instance, false if not
     */
    public JBasicValue greaterThenEqual(JBasicValue right, ParserRuleContext context) {
        return this.compare(right, (l, r) -> l >= r, context);
    }

    /// Computes the hashcode of a value instance
    @Override
    public int hashCode() {
        // TODO: Hash strings, arrays and numbers
        return 0;
    }

    /**
     * Determines whether this Value object instance is a string value
     *
     * @return true if the underlying value is a string, false if not
     */
    public boolean isAStringValue() {
        return this.value instanceof String;
    }

    /**
     * Determines if this Value object instance is a numerical value
     *
     * @return true if the value is numerical, false if not
     */
    public boolean isANumericalValue() {
        return this.value instanceof Double;
    }

    /**
     * Determines if this Value object instance is an array value
     *
     * @return true if the value is an array value, false if not
     */
    public boolean isAnArrayValue() {
        return this.isAnOneDimensionalArrayValue() ||
                this.isATwoDimensionalArrayValue() ||
                this.isAThreeDimensionalArrayValue();
    }

    /**
     * Determines if this Value object instance is a one-dimensional array value
     *
     * @return true if the value is a one-dimensional array value, false if not
     */
    public boolean isAnOneDimensionalArrayValue() {
        return this.value instanceof JBasicValue[];
    }

    /**
     * Determines if this Value object instance is a two-dimensional array value
     *
     * @return true if the value is a two-dimensional array value, false if not
     */
    public boolean isATwoDimensionalArrayValue() {
        return this.value instanceof JBasicValue[][];
    }

    /**
     * Determines if this Value object instance is a three-dimensional array value
     *
     * @return true if the value is a three-dimensional array value, false if not
     */
    public boolean isAThreeDimensionalArrayValue() {
        return this.value instanceof JBasicValue[][][];
    }

    /**
     * Determines whether the value is falsy
     *
     * @param context The parsing context where the falseness of the value is evaluated
     * @return true if the underlying value is falsy, false if not
     */
    public boolean isFalsy(ParserRuleContext context) {
        ValueTypeSafeguard.guaranteeValueIsNumerical("Only numerical can be used as conditions", this, context);
        return this.underlyingNumber() == 0;
    }

    /**
     * Determines if this Value object instance is a not numerical value
     *
     * @return false if the value is numerical, true if not
     */
    public boolean isNotANumericalValue() {
        return !(this.value instanceof Double);
    }

    /**
     * Determines whether the value is truthy
     *
     * @param context The parsing context where the truthiness of the value is evaluated
     * @return true if the underlying value is truthy, false if not
     */
    public boolean isTruthy(ParserRuleContext context) {
        ValueTypeSafeguard.guaranteeValueIsNumerical("Only numerical can be used as conditions", this, context);
        return this.underlyingNumber() != 0;
    }

    /**
     * Determines if this Value object instance is less than another Value object instance
     *
     * @param right   The other Value object instance
     * @param context The parsing context of the 'less-then expression'
     * @return true if this Value object instance is less than the other Value object instance, false if not
     */
    public JBasicValue lessThen(JBasicValue right, ParserRuleContext context) {
        return this.compare(right, (l, r) -> l < r, context);
    }

    /**
     * Determines if this Value object instance is less than or equal to another Value object instance
     * @param right The other Value object instance
     * @param context The parsing context of the 'less-then-equal expression'
     * @return true if this Value object instance is less or equal to the other Value object instance, false if not
     */
    public JBasicValue lessThenEqual(JBasicValue right, ParserRuleContext context) {
        return this.compare(right, (l, r) -> l <= r, context);
    }

    /**
     * Gets the remainder of the division of this Value object instance with another object instance
     *
     * @param right   The divisor of the division
     * @param context The parsing context of the division expression
     * @return This Value object instance divided by the divisor 'right'
     */
    public JBasicValue modulo(JBasicValue right, ParserRuleContext context) {
        return this.arithmeticEvaluation(right, (l, r) -> l % r, context);
    }

    /**
     * Multiplies this Value object instance with another object instance
     *
     * @param right   The multiplicand of the multiplication
     * @param context The parsing context of the multiplication expression
     * @return This Value object instance multiplied by the divisor 'right'
     */
    public JBasicValue multiply(JBasicValue right, ParserRuleContext context) {
        return this.arithmeticEvaluation(right, (l, r) -> l * r, context);
    }

    /**
     * Determines if this Value object instance is equal to another Value object instance
     *
     * @param right   The other Value object instance
     * @param context The parsing context of the 'not equal expression'
     * @return true if both values are not equal, false if not
     */
    public JBasicValue notEqual(JBasicValue right, ParserRuleContext context) {
        JBasicValue eq = this.equal(right, context);
        return eq.equal(TrueValue, context) == TrueValue ? FalseValue : TrueValue;
    }

    /**
     * Performs a relative evaluation on this Value object instance and another Value object instance
     * @param right The other Value object instance
     * @param comparison The comparison operator
     * @param context The parsing context of the relative Evaluation
     * @return The result of the relative evaluation
     */
    private JBasicValue compare(JBasicValue right, BiFunction<Double, Double, Boolean> comparison, ParserRuleContext context) {
        ValueTypeSafeguard.guaranteeValueIsNumerical("Only numerical values can be compared", this, context);
        ValueTypeSafeguard.guaranteeValueIsNumerical("Only numerical values can be compared", right, context);
        if (comparison.apply(this.underlyingNumber(), right.underlyingNumber())) {
            return TrueValue;
        }
        return FalseValue;
    }

    /**
     * Negates a numerical value
     *
     * @param context The parsing context of the 'negate expression'
     * @return The negated numerical value of this Value object instance
     */
    public JBasicValue negate(NegateExpressionContext context) {
        ValueTypeSafeguard.guaranteeValueIsNumerical("Only numerical values can be negated", this, context);
        return new JBasicValue(-this.underlyingNumber());
    }

    /**
     * Inverts the logical value of this Value object instance
     *
     * @param context The parsing context of the 'not expression'
     * @return True if the Value object instance was falsy, false if it was true
     */
    public JBasicValue not(ParserRuleContext context) {
        ValueTypeSafeguard.guaranteeValueIsNumerical("Only numerical values can be logically inverted", this, context);
        return this.underlyingNumber() == 0 ? TrueValue : FalseValue;
    }

    /**
     * Interprets the value as part of an or expression
     * @param right The value right of the currently evaluated value
     * @param context The parsing context of the Value instance
     * @return True if the value or the right value are truthy
     */
    public JBasicValue or(JBasicValue right, ParserRuleContext context) {
        return this.isTruthy(context) || right.isTruthy(context) ? TrueValue : FalseValue;
    }

    /**
     * Prints the value
     *
     * @param printStream The print-stream where the value is printed
     * @param aligned Determines whether the output shall be aligned using print zones
     */
    public void printValue(PrintStream printStream, boolean aligned) {
        StringBuilder outputBuilder = new StringBuilder();
        if (this.isANumericalValue()) {
            String formattedValue = IOFormatter.numericalOutputFormat.format(this.underlyingNumber());
            if ("-0".equals(formattedValue)) {
                outputBuilder = new StringBuilder("0");
            } else {
                outputBuilder = new StringBuilder(formattedValue);
            }
        }
        else if (this.isAnArrayValue()) {
            outputBuilder = new StringBuilder(outputBuilder.toString().concat("{ "));
            if (this.isAnOneDimensionalArrayValue()){
                for (int i = 0; i < this.underlyingOneDimensionalArray().length; i++) {
                    this.underlyingOneDimensionalArray()[i].printValue(printStream, false);
                    if (i > 0) {
                        outputBuilder = new StringBuilder(outputBuilder.toString().concat(", "));
                    }
                }

            }
            else if (this.isATwoDimensionalArrayValue()){
                for (int i = 0; i < this.underlyingTwoDimensionalArray().length; i++) {
                    new JBasicValue(this.underlyingTwoDimensionalArray()[i]).printValue(printStream, false);
                    if (i > 0) {
                        outputBuilder = new StringBuilder(outputBuilder.toString().concat(", "));
                    }
                }
            }
            else{
                for (int i = 0; i < this.underlyingThreeDimensionalArray().length; i++) {
                    if (i > 0) {
                        outputBuilder = new StringBuilder(outputBuilder.toString().concat(", "));
                    }
                    new JBasicValue(this.underlyingThreeDimensionalArray()[i]).printValue(printStream, false);
                }
            }
             outputBuilder = new StringBuilder(outputBuilder.toString().concat("} "));
        }
        else {
            outputBuilder = new StringBuilder(this.underlyingString());
        }
        // Aligns output to the next print zone. A print zone in basic is 14 whitespaces large
        if(aligned) {
            if (outputBuilder.length() == 0) {
                outputBuilder.append(" ".repeat(14));
            }
            else if (outputBuilder.length() % 14 != 0) {
                outputBuilder.append(" ".repeat(outputBuilder.length() % 14));
            }
        }
        printStream.print(outputBuilder);
    }

    /**
     * Performs a subtraction with the underlying numerical value of this object instance and the underlying numerical value of another object instance
     *
     * @param right   The subtrahend of the subtraction
     * @param context The parsing context of the subtraction expression
     * @return The underlying numerical value of this Value object instance subtracted with the underlying numerical value of the other Value object instance
     */
    public JBasicValue subtract(JBasicValue right, ParserRuleContext context) {
        return this.arithmeticEvaluation(right, (l, r) -> l - r, context);
    }

    /**
     * Gets the underlying number form the value
     *
     * @return The underlying numerical value
     */
    public double underlyingNumber() {
        return (double) this.value;
    }

    /**
     * Gets the underlying string from the value
     * @return The underlying string value
     */
    public String underlyingString() {
        return (String) this.value;
    }

    /**
     * Gets the underlying one-dimensional array from the value
     * @return The underlying one-dimensional array value
     */
    public JBasicValue[] underlyingOneDimensionalArray() {
        return (JBasicValue[]) this.value;
    }

    /**
     * Gets the underlying two-dimensional array from the value
     * @return The underlying two-dimensional array value
     */
    public JBasicValue[][] underlyingTwoDimensionalArray() {
        return (JBasicValue[][]) this.value;
    }

    /**
     * Gets the underlying three-dimensional array from the value
     * @return The underlying three-dimensional array value
     */
    public JBasicValue[][][] underlyingThreeDimensionalArray() {
        return (JBasicValue[][][]) this.value;
    }

}