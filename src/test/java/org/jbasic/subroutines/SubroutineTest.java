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

package org.jbasic.subroutines;

import org.jbasic.JBasicBaseTest;
import org.junit.Assert;
import org.junit.Test;

public class SubroutineTest extends JBasicBaseTest {

    @Test
    public void testSimple() {
        this.test("subroutine/simple.bas",
                "Jeff" + System.lineSeparator(),
                (result) -> Assert.assertEquals("Name= Hi my name is Jeff" + System.lineSeparator(), result.output));
    }

    @Test
    public void testSubroutineArityError() {
        this.test("subroutine/subroutine_arity.bas",
                (result) -> Assert.assertEquals("Error at [6, 0]: Subroutine expects 0 arguments but was called with 1",
                        result.error.trim()));
    }

    @Test
    public void testSubroutineRedefinitionError() {
        this.test("subroutine/subroutine_redefinition.bas",
                (result) -> Assert.assertEquals("Error at [6, 0]: A subroutine with the name Greet is already defined in the script",
                        result.error.trim()));
    }

    @Test
    public void testUndefinedSubroutineError() {
        this.test("subroutine/undefined_subroutine.bas",
                (result) -> Assert.assertEquals("Error at [1, 0]: A subroutine with the nameUNDEFINED is not defined in the script",
                        result.error.trim()));
    }
}
