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

    public Value(long value) {
        this.value = value;
    }

    private Value(Object value, boolean isNaN) {
        this.value = value;
        this.isNaN = isNaN;
    }

    public long internalNumber() {
        return (long)value;
    }

    public String internalString() {
        return (String)value;
    }

    public boolean isString() {
        return value instanceof String;
    }

    public boolean isNumber() {
        return value instanceof Long;
    }

    public boolean isNaN() {
        return isNaN;
    }

    public boolean isTrue() {
        assertNumber();
        return internalNumber() != 0;
    }

    public boolean isFalse() {
        assertNumber();
        return internalNumber() == 0;
    }

    private void assertNumber() {
        if (!isNumber()) {
            throw new TypeException("Couldn't evaluate numeric expression. Value \"" + value + "\" is not a number");
        }
    }

    public Value multiply(Value right) {
        return arithmeticEvaluation(right, (l, r) -> l * r);
    }

    public Value divide(Value right) {
        return arithmeticEvaluation(right, (l, r) -> l / r);
    }

    public Value modulo(Value right) {
        return arithmeticEvaluation(right, (l, r) -> l % r);
    }

    public Value add(Value right) {
        if (isString() && right.isString()) {
            return new Value(internalString() + right.internalString());
        }
        else if (isString() && right.isNumber()) {
            return new Value(internalString() + right.internalNumber());
        }
        else if (isNumber() && right.isString()) {
            return new Value(internalNumber() + right.internalString());
        }
        else {
            return arithmeticEvaluation(right, Long::sum);
        }
    }

    public Value subtract(Value right) {
        return arithmeticEvaluation(right, (l, r) -> l - r);
    }

    private Value arithmeticEvaluation(Value right, BiFunction<Long, Long, Long> operator) {
        assertNumber();
        right.assertNumber();
        return new Value(operator.apply(internalNumber(), right.internalNumber()));
    }

    public Value greaterThen(Value right) {
        return relEval(right, (l, r) -> l > r);
    }

    public Value greaterThenEqual(Value right) {
        return relEval(right, (l, r) -> l >= r);
    }

    public Value lessThen(Value right) {
        return relEval(right, (l, r) -> l < r);
    }

    public Value lessThenEqual(Value right) {
        return relEval(right, (l, r) -> l <= r);
    }

    public Value equal(Value right) {
        if (isNumber() && right.isNumber()) {
            return relEval(right, Objects::equals);
        } else if (isString() && right.isString()) {
            return internalString().equals(right.internalString()) ? TRUE : FALSE;
        }
        return FALSE;
    }

    public Value notEqual(Value right) {
        Value eq = equal(right);
        return eq.equal(TRUE) == TRUE ? FALSE : TRUE;
    }

    private Value relEval(Value right, BiFunction<Long, Long, Boolean> comparison) {
        assertNumber();
        right.assertNumber();
        if (comparison.apply(internalNumber(), right.internalNumber())) {
            return TRUE;
        }
        return FALSE;
    }

    public Value not() {
        assertNumber();
        if (internalNumber() == 0) {
            return TRUE;
        }
        return FALSE;
    }

    public Value and(Value right) {
        return isTrue() && right.isTrue() ? TRUE : FALSE;
    }

    public Value or(Value right) {
        return isTrue() || right.isTrue() ? TRUE : FALSE;
    }

    public Value expression(Value right) {
        assertNumber();
        right.assertNumber();
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