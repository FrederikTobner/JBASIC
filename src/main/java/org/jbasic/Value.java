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

package org.jbasic;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Value class that encapsulates numeric and string values and the
 * corresponding operations.
 */
public class Value {

    public static final Value FALSE = new Value(0);
    public static final Value TRUE = new Value(1);
    public static final Value NaN = new Value(null, true);

    private final Object value;
    private boolean isNaN;

    public Value(String value) {
        this.value = value;
    }

    public Value(double value) {
        this.value = value;
    }

    private Value(Object value, boolean isNaN) {
        this.value = value;
        this.isNaN = isNaN;
    }

    public double internalNumber() {
        return (double)value;
    }

    public String internalString() {
        return (String)value;
    }

    public boolean isString() {
        return value instanceof String;
    }

    public boolean isNumber() {
        return value instanceof Double;
    }

    public boolean isNaN() {
        return isNaN;
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
        if (!isNumber()) {
            TypeException typeException = new TypeException("Couldn't evaluate numeric expression. Value \"" + value + "\" is not a number");
            Utils.addLocation(typeException, context);
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
        if (isString() && right.isString()) {
            return new Value(internalString() + right.internalString());
        }
        else if (isString() && right.isNumber()) {
            return new Value(internalString() + Utils.numericalOutputFormat.format(right.internalNumber()));
        }
        else if (isNumber() && right.isString()) {
            return new Value(Utils.numericalOutputFormat.format(internalNumber()) + right.internalString());
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
        if (isNumber() && right.isNumber()) {
            return relEval(right, Objects::equals, context);
        } else if (isString() && right.isString()) {
            return internalString().equals(right.internalString()) ? TRUE : FALSE;
        }
        return FALSE;
    }

    public Value notEqual(Value right, ParserRuleContext context) {
        Value eq = equal(right, context);
        return eq.equal(TRUE, context) == TRUE ? FALSE : TRUE;
    }

    private Value relEval(Value right, BiFunction<Double, Double, Boolean> comparison, ParserRuleContext context) {
        assertNumber(context);
        right.assertNumber(context);
        if (comparison.apply(internalNumber(), right.internalNumber())) {
            return TRUE;
        }
        return FALSE;
    }

    public Value not(ParserRuleContext context) {
        assertNumber(context);
        if (internalNumber() == 0) {
            return TRUE;
        }
        return FALSE;
    }

    public Value and(Value right, ParserRuleContext context) {
        return isTrue(context) && right.isTrue(context) ? TRUE : FALSE;
    }

    public Value or(Value right, ParserRuleContext context) {
        return isTrue(context) || right.isTrue(context) ? TRUE : FALSE;
    }

    public Value expression(Value right, ParserRuleContext context) {
        assertNumber(context);
        right.assertNumber(context);
        return new Value(Math.round(Math.pow(internalNumber(), right.internalNumber())));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Value)) return false;

        Value value1 = (Value) o;

        if (isNaN != value1.isNaN) return false;
        return Objects.equals(value, value1.value);
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (isNaN ? 1 : 0);
        return result;
    }

}