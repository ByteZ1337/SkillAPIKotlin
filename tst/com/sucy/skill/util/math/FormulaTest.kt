package com.sucy.skill.util.math

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import com.sucy.skill.util.access.Access
import com.sucy.skill.util.math.function.Sq
import com.sucy.skill.util.math.operator.Plus
import com.sucy.skill.util.math.operator.Divide
import com.sucy.skill.util.math.operator.Times
import com.sucy.skill.util.math.value.ConstValue
import com.sucy.skill.util.math.value.VarValue
import org.junit.Test
import kotlin.test.assertEquals

/**
 * SkillAPIKotlin © 2018
 */
class FormulaTest {

    @Test
    fun getTokensConstOnly() {
        val formula = Formula("3", Access(ImmutableMap.of()))
        assertEquals(1, formula.tokens.size)
        assertEquals(ConstValue(3.0), formula.tokens[0])
    }

    @Test
    fun getTokensVarOnly() {
        val formula = Formula("test", Access(ImmutableMap.of()))
        assertEquals(1, formula.tokens.size)
        assertEquals(VarValue("test"), formula.tokens[0])
    }

    @Test
    fun getTokensOperator() {
        val formula = Formula("2+3", Access(ImmutableMap.of()))
        assertEquals(3, formula.tokens.size)
        assertEquals(ConstValue(2.0), formula.tokens[0])
        assertEquals(ConstValue(3.0), formula.tokens[1])
        assertEquals(Plus, formula.tokens[2])
    }

    @Test
    fun getTokensOperatorAndSpaces() {
        val formula = Formula("2 + 3", Access(ImmutableMap.of()))
        assertEquals(3, formula.tokens.size)
        assertEquals(ConstValue(2.0), formula.tokens[0])
        assertEquals(ConstValue(3.0), formula.tokens[1])
        assertEquals(Plus, formula.tokens[2])
    }

    @Test
    fun getTokensFunction() {
        val formula = Formula("sq(2)", Access(ImmutableMap.of()))
        assertEquals(2, formula.tokens.size)
        assertEquals(ConstValue(2.0), formula.tokens[0])
        assertEquals(Sq, formula.tokens[1])
    }

    @Test
    fun getTokensMixedOps() {
        val formula = Formula("1+2*3+4", Access(ImmutableMap.of()))
        assertEquals(7, formula.tokens.size)
        assertEquals<List<Token>>(ImmutableList.of(
                ConstValue(1.0),
                ConstValue(2.0),
                ConstValue(3.0),
                Times,
                Plus,
                ConstValue(4.0),
                Plus
        ), formula.tokens)
    }

    @Test
    fun getTokensComplex() {
        val formula = Formula("2 * (1 + sq(2 + 3) / (4 + 2))", Access(ImmutableMap.of()))
        assertEquals<List<Token>>(ImmutableList.of(
                ConstValue(2.0),
                ConstValue(1.0),
                ConstValue(2.0),
                ConstValue(3.0),
                Plus,
                Sq,
                ConstValue(4.0),
                ConstValue(2.0),
                Plus,
                Divide,
                Plus,
                Times
        ), formula.tokens)
    }

    @Test
    fun getTokensParenthesisInFunc() {
        val formula = Formula("1 + sq((2 + 3) * 2) / 4", Access(ImmutableMap.of()))
        assertEquals<List<Token>>(ImmutableList.of(
                ConstValue(1.0),
                ConstValue(2.0),
                ConstValue(3.0),
                Plus,
                ConstValue(2.0),
                Times,
                Sq,
                ConstValue(4.0),
                Divide,
                Plus
        ), formula.tokens)
    }

    @Test
    fun evaluate() {
        val formula = Formula("1 + sq((2 + 3) * 2) / 4", Access(ImmutableMap.of()))
        assertEquals(26.0, formula.evaluate(null))
    }

    @Test
    fun exponent() {
        val formula = Formula("25 * 2 ^ ( 0.2 + 1.8 )", Access(ImmutableMap.of()))
        assertEquals(100.0, formula.evaluate(null))
    }
}