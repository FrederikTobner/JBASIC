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

    /// Creates a new False Value
    public static final Value CreateFalseValue = new Value(0);
    /// Creates a new true value
    public static final Value CreateTrueValue = new Value(1);
    /// Creates a new not a number value
    public static final Value CreateNotANumberValue = new Value(null);

    /// Underlying value
    private final Object value;

    public Value(String value) {
        this.value = value;
    }

    public Value(double value) {
        this.value = value;
    }

    private Value(Object value) {
        this.value = value;
    }

    public double internalNumber() {
        return (double)value;
    }

    public String internalString() {
        return (String)value;
    }

    public boolean isAString() {
        return value instanceof String;
    }

    public boolean isANumber() {
        return value instanceof Double;
    }

    public boolean isNotANumber() {
        return !(value instanceof Double);
    }

    public boolean isTrue(ParserRuleContext context) {
        assertNumber(context);
        return internalNumber() != 0;
    }

    public boolean isFalse(ParserRuleContext context) {
        assertNumber(context);
        return internalNumber() == 0;
    }

    private void assertNumber(ParserRuleContext context) {
        if (!isANumber()) {
            TypeException typeException = new TypeException("Couldn't evaluate numeric expression. Value \"" + value + "\" is not a number");
            CoreUtils.addLocation(typeException, context);
            throw typeException;
        }
    }

    public Value multiply(Value right, ParserRuleContext context) {
        return arithmeticEvaluation(right, (l, r) -> l * r, context);
    }

    public Value divide(Value right, ParserRuleContext context) {
        return arithmeticEvaluation(right, (l, r) -> l / r, context);
    }

    public Value modulo(Value right, ParserRuleContext context) {
        return arithmeticEvaluation(right, (l, r) -> l % r, context);
    }

    public Value add(Value right, ParserRuleContext context) {
        if (isAString() && right.isAString()) {
            return new Value(internalString() + right.internalString());
        }
        else if (isAString() && right.isANumber()) {
            return new Value(internalString() + CoreUtils.numericalOutputFormat.format(right.internalNumber()));
        }
        else if (isANumber() && right.isAString()) {
            return new Value(CoreUtils.numericalOutputFormat.format(internalNumber()) + right.internalString());
        }
        else {
            return arithmeticEvaluation(right, Double::sum, context);
        }
    }

    public Value subtract(Value right, ParserRuleContext context) {
        return arithmeticEvaluation(right, (l, r) -> l - r, context);
    }

    private Value arithmeticEvaluation(Value right, BiFunction<Double, Double, Double> operator, ParserRuleContext context) {
        assertNumber(context);
        right.assertNumber(context);
        return new Value(operator.apply(internalNumber(), right.internalNumber()));
    }

    public Value greaterThen(Value right, ParserRuleContext context) {
        return relEval(right, (l, r) -> l > r, context);
    }

    public Value greaterThenEqual(Value right, ParserRuleContext context) {
        return relEval(right, (l, r) -> l >= r, context);
    }

    public Value lessThen(Value right, ParserRuleContext context) {
        return relEval(right, (l, r) -> l < r, context);
    }

    public Value lessThenEqual(Value right, ParserRuleContext context) {
        return relEval(right, (l, r) -> l <= r, context);
    }

    public Value equal(Value right, ParserRuleContext context) {
        if (isANumber() && right.isANumber()) {
            return relEval(right, Objects::equals, context);
        } else if (isAString() && right.isAString()) {
            return internalString().equals(right.internalString()) ? CreateTrueValue : CreateFalseValue;
        }
        return CreateFalseValue;
    }

    public Value notEqual(Value right, ParserRuleContext context) {
        Value eq = equal(right, context);
        return eq.equal(CreateTrueValue, context) == CreateTrueValue ? CreateFalseValue : CreateTrueValue;
    }

    private Value relEval(Value right, BiFunction<Double, Double, Boolean> comparison, ParserRuleContext context) {
        assertNumber(context);
        right.assertNumber(context);
        if (comparison.apply(internalNumber(), right.internalNumber())) {
            return CreateTrueValue;
        }
        return CreateFalseValue;
    }

    public Value not(ParserRuleContext context) {
        assertNumber(context);
        if (internalNumber() == 0) {
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
        return isTrue(context) && right.isTrue(context) ? CreateTrueValue : CreateFalseValue;
    }

    /**
     * Interprets the value as part of an or expression
     * @param right The value right of the currently evaluated value
     * @param context The parsing context of the Value instance
     * @return True if the value or the right value are truthy
     */
    public Value or(Value right, ParserRuleContext context) {
        return isTrue(context) || right.isTrue(context) ? CreateTrueValue : CreateFalseValue;
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

        if (this.isNotANumber() != value1.isNotANumber()) return false;
        return Objects.equals(value, value1.value);
    }

    /// Computes the hashcode of a value instance
    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (isNotANumber() ? 1 : 0);
        return result;
    }

}