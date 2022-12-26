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
 * @file Value.java
 * @brief Value class that encapsulates numeric and string values and the corresponding operations.
 */

package org.jbasic;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * @brief Value class 
 * @details The class encapsulates numeric and string values and the corresponding operations.
 */
public class Value {

    /// False value constant
    public static final Value CreateFalseValue = new Value(0);
    /// False value constant
    public static final Value CreateTrueValue = new Value(1);
    /// not a number constant
    public static final Value CreateNotANumberValue = new Value(null);

    /// Underlying value
    private final Object value;

    /** Creates a new Value object instance that has a string as the underlying value
     * @param value The underlying string value of the newly created value
     */
    public Value(String value) {
        this.value = value;
    }

    /**
     * Creates a new Value object instance that has a double as the underlying value
     * @param value The underlying double of the newly created value
     */

    public Value(double value) {
        this.value = value;
    }

    /**
     * @brief Creates a new Value object instance that has an object as the underlying value
     * @param value The underlying double of the newly created value
     * @details This method is only called by the two other public constructors of this class
     */
    private Value(Object value) {
        this.value = value;
    }

    /**
     * Gets the underlying number form the value
     * @return The underlying numerical value
     */
    public double underlyingNumber() {
        return (double)value;
    }

    /**
     * Gets the underlying string from the value
     * @return The underlying string value
     */
    public String underlyingString() {
        return (String)value;
    }

    /**
     * Determines whether this Value object instance is a string value
     * @return true if the underlying value is a string, false if not
     */
    public boolean isAStringValue() {
        return value instanceof String;
    }

    /**
     * Determines if this Value object instance is a numerical value
     * @return true if the value is numerical, false if not
     */
    public boolean isANumericalValue() {
        return value instanceof Double;
    }

    /**
     * Determines if this Value object instance is a not numerical value
     * @return false if the value is numerical, true if not
     */
    public boolean isNotANumericalValue() {
        return !(value instanceof Double);
    }

    /**
     * Determines whether the value is truthy
     * @param context The parsing context where the truthiness of the value is evaluated
     * @return true if the underlying value is truthy, false if not
     */
    public boolean isTruthy(ParserRuleContext context) {
        assertNumber(context);
        return underlyingNumber() != 0;
    }

    /**
     * Determines whether the value is falsy
     * @param context The parsing context where the falseness of the value is evaluated
     * @return true if the underlying value is falsy, false if not
     */
    public boolean isFalsy(ParserRuleContext context) {
        assertNumber(context);
        return underlyingNumber() == 0;
    }

    /**
     * Asserts the underlying value of this Value object instance is numerical
     * @param context The parsing context where type of the Value object instance is asserted
     */
    private void assertNumber(ParserRuleContext context) {
        if (!isANumericalValue()) {
            TypeException typeException = new TypeException("Couldn't evaluate numeric expression. Value \"" + value + "\" is not a number");
            CoreUtils.addLocation(typeException, context);
            throw typeException;
        }
    }

    /**
     * Multiplies this Value object instance with another object instance
     * @param right The multiplicand of the multiplication
     * @param context The parsing context of the multiplication expression
     * @return This Value object instance multiplied by the divisor 'right'
     */
    public Value multiply(Value right, ParserRuleContext context) {
        return arithmeticEvaluation(right, (l, r) -> l * r, context);
    }

    /**
     * Divides this Value object instance with another object instance
     * @param right The divisor of the division
     * @param context The parsing context of the division expression
     * @return This Value object instance divided by the divisor 'right'
     */
    public Value divide(Value right, ParserRuleContext context) {
        return arithmeticEvaluation(right, (l, r) -> l / r, context);
    }

    /**
     * Gets the remainder of the division of this Value object instance with another object instance
     * @param right The divisor of the division
     * @param context The parsing context of the division expression
     * @return This Value object instance divided by the divisor 'right'
     */
    public Value modulo(Value right, ParserRuleContext context) {
        return arithmeticEvaluation(right, (l, r) -> l % r, context);
    }

    /**
     * Performs an addition with the underlying numerical value of this object instance and the underlying numerical value of another object instance
     * @param right The addend of the addition
     * @param context The parsing context of the addition expression
     * @return The underlying numerical value of this Value object instance added to the underlying numerical value of the other Value object instance
     */
    public Value add(Value right, ParserRuleContext context) {
        if (isAStringValue() && right.isAStringValue()) {
            return new Value(underlyingString() + right.underlyingString());
        }
        else if (isAStringValue() && right.isANumericalValue()) {
            return new Value(underlyingString() + CoreUtils.numericalOutputFormat.format(right.underlyingNumber()));
        }
        else if (isANumericalValue() && right.isAStringValue()) {
            return new Value(CoreUtils.numericalOutputFormat.format(underlyingNumber()) + right.underlyingString());
        }
        else {
            return arithmeticEvaluation(right, Double::sum, context);
        }
    }

    /**
     * Performs a subtraction with the underlying numerical value of this object instance and the underlying numerical value of another object instance
     * @param right The subtrahend of the subtraction
     * @param context The parsing context of the subtraction expression
     * @return The underlying numerical value of this Value object instance subtracted with the underlying numerical value of the other Value object instance
     */
    public Value subtract(Value right, ParserRuleContext context) {
        return arithmeticEvaluation(right, (l, r) -> l - r, context);
    }

    /**
     * Performs an arithmetic evaluation on a binary expression
     * @param right The right operand in the expression
     * @param operator The operator of the expression
     * @param context The parsing context of the binary expression
     * @return The result of the arithmetic evaluation
     */
    private Value arithmeticEvaluation(Value right, BiFunction<Double, Double, Double> operator, ParserRuleContext context) {
        assertNumber(context);
        right.assertNumber(context);
        return new Value(operator.apply(underlyingNumber(), right.underlyingNumber()));
    }

    /**
     * Determines if this Value object instance is greater than another Value object instance
     * @param right The other Value object instance
     * @param context The parsing context of the 'greater-then expression'
     * @return true if this Value object instance is greater than the other Value object instance, false if not
     */
    public Value greaterThen(Value right, ParserRuleContext context) {
        return relativeEvaluate(right, (l, r) -> l > r, context);
    }

    /**
     * Determines if this Value object instance is greater than or equal to another Value object instance
     * @param right The other Value object instance
     * @param context The parsing context of the 'greater-then-equal expression'
     * @return true if this Value object instance is greater or equal to the other Value object instance, false if not
     */
    public Value greaterThenEqual(Value right, ParserRuleContext context) {
        return relativeEvaluate(right, (l, r) -> l >= r, context);
    }

    /**
     * Determines if this Value object instance is less than another Value object instance
     * @param right The other Value object instance
     * @param context The parsing context of the 'less-then expression'
     * @return true if this Value object instance is less than the other Value object instance, false if not
     */
    public Value lessThen(Value right, ParserRuleContext context) {
        return relativeEvaluate(right, (l, r) -> l < r, context);
    }

    /**
     * Determines if this Value object instance is less than or equal to another Value object instance
     * @param right The other Value object instance
     * @param context The parsing context of the 'less-then-equal expression'
     * @return true if this Value object instance is less or equal to the other Value object instance, false if not
     */
    public Value lessThenEqual(Value right, ParserRuleContext context) {
        return relativeEvaluate(right, (l, r) -> l <= r, context);
    }

    /**
     * Determines if this Value object instance is equal to another Value object instance
     * @param right The other Value object instance
     * @param context The parsing context of the 'equal expression'
     * @return true if both values are equal, false if not
     */
    public Value equal(Value right, ParserRuleContext context) {
        if (isANumericalValue() && right.isANumericalValue()) {
            return relativeEvaluate(right, Objects::equals, context);
        } else if (isAStringValue() && right.isAStringValue()) {
            return underlyingString().equals(right.underlyingString()) ? CreateTrueValue : CreateFalseValue;
        }
        return CreateFalseValue;
    }

    /**
     * Determines if this Value object instance is equal to another Value object instance
     * @param right The other Value object instance
     * @param context The parsing context of the 'not equal expression'
     * @return true if both values are not equal, false if not
     */
    public Value notEqual(Value right, ParserRuleContext context) {
        Value eq = equal(right, context);
        return eq.equal(CreateTrueValue, context) == CreateTrueValue ? CreateFalseValue : CreateTrueValue;
    }

    /**
     * Performs a relative evaluation on this Value object instance and another Value object instance
     * @param right The other Value object instance
     * @param comparison The comparison operator
     * @param context The parsing context of the relative Evaluation
     * @return The result of the relative evaluation
     */
    private Value relativeEvaluate(Value right, BiFunction<Double, Double, Boolean> comparison, ParserRuleContext context) {
        assertNumber(context);
        right.assertNumber(context);
        if (comparison.apply(underlyingNumber(), right.underlyingNumber())) {
            return CreateTrueValue;
        }
        return CreateFalseValue;
    }

    /**
     * Inverts the logical value of this Value object instance
     * @param context The parsing context of the 'not expression'
     * @return True if the Value object instance was falsy, false if it was true
     */
    public Value not(ParserRuleContext context) {
        assertNumber(context);
        if (underlyingNumber() == 0) {
            return CreateTrueValue;
        }
        return CreateFalseValue;
    }

    /**
     * Interprets the value as part of an and expression
     * @param right The value right of the currently evaluated value
     * @param context The parsing context of the Value instance
     * @return True if the value and the right value are truthy
     */
    public Value and(Value right, ParserRuleContext context) {
        return isTruthy(context) && right.isTruthy(context) ? CreateTrueValue : CreateFalseValue;
    }

    /**
     * Interprets the value as part of an or expression
     * @param right The value right of the currently evaluated value
     * @param context The parsing context of the Value instance
     * @return True if the value or the right value are truthy
     */
    public Value or(Value right, ParserRuleContext context) {
        return isTruthy(context) || right.isTruthy(context) ? CreateTrueValue : CreateFalseValue;
    }

    /**
     * Determines whether a Value instance and another java object are equal
     * @param o The object that is compared with the Value instance
     * @return A boolean value that indicates whether the two values are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Value)) return false;

        Value value1 = (Value) o;

        if (this.isNotANumericalValue() != value1.isNotANumericalValue()) return false;
        return Objects.equals(value, value1.value);
    }

    /// Computes the hashcode of a value instance
    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (isNotANumericalValue() ? 1 : 0);
        return result;
    }

}