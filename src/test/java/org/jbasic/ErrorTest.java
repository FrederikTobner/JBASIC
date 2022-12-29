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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ErrorTest extends JBasicBaseTest {

    @Test
    public void testArrayDimensionsMismatchError() {
        this.test("error/array_dimensions_mismatch.bas",
                (result) -> assertEquals("Error at [2, 6]: The dimensions that were specified do not match the dimensions of the array",
                        result.error.trim()));
    }

    @Test
    public void testArrayDimensionsUnsupportedError() {
        this.test("error/array_dimension_unsupported.bas",
                (result) -> assertEquals("Error at [1, 10]: Dimensions can not have numbers after the digit",
                        result.error.trim()));
    }

    @Test
    public void testFunctionArityError() {
        this.test("error/function_arity.bas",
                (result) -> assertEquals("Error at [1, 10]: ABS can not be called with 2 arguments",
                        result.error.trim()));
    }

    @Test
    public void testInvalidLabelError() {
        this.test("error/invalid_label.bas",
                (result) -> assertEquals("Error at [1, 0]: Digits are not allowed in a label",
                        result.error.trim()));
    }

    @Test
    public void testSubroutineArityError() {
        this.test("error/subroutine_arity.bas",
                (result) -> assertEquals("Error at [6, 0]: Subroutine expects 0 arguments but was called with 1",
                        result.error.trim()));
    }

    @Test
    public void testSubroutineRedefinitionError() {
        this.test("error/subroutine_redefinition.bas",
                (result) -> assertEquals("Error at [6, 0]: A subroutine with the name Greet is already defined in the script",
                        result.error.trim()));
    }

    @Test
    public void testSyntaxError() {
        this.test("error/syntax_error.bas",
                (result) -> assertEquals("Error at [2, 4]: Syntax error",
                        result.error.trim()));
    }

    @Test
    public void testTypeError() {
        this.test("error/type_error.bas",
                (result) -> assertEquals("Error at [2, 6]: Could not evaluate arithmetic expression. Value is not a number",
                        result.error.trim()));
    }

    @Test
    public void testUndefinedLabelError() {
        this.test("error/undefined_label.bas",
                (result) -> assertEquals("Error at [1, 0]: A label called UNDEFINED is not defined",
                        result.error.trim()));
    }

    @Test
    public void testUndefinedSubroutineError() {
        this.test("error/undefined_subroutine.bas",
                (result) -> assertEquals("Error at [1, 0]: A subroutine with the nameUNDEFINED is not defined in the script",
                        result.error.trim()));
    }

    @Test
    public void testUndefinedVariableError() {
        this.test("error/undefined_variable.bas",
                (result) -> assertEquals("Error at [1, 6]: undefined is not defined",
                        result.error.trim()));
    }

    @Test
    public void testViolatedNumericalSuffixError() {
        this.test("error/violated_numerical_suffix.bas",
                (result) -> assertEquals("Error at [1, 8]: Type suffix does not match specified type",
                        result.error.trim()));
    }

    @Test
    public void testViolatedStringSuffixError() {
        this.test("error/violated_string_suffix.bas",
                (result) -> assertEquals("Error at [1, 8]: Type suffix does not match specified type",
                        result.error.trim()));
    }

}
