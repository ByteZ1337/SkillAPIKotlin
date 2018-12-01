package com.sucy.skill.api.attribute

import com.sucy.skill.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class AttributeValueTest {

    private lateinit var subject: AttributeValue

    @Before
    fun setUp() {
        subject = AttributeValue()
    }

    @Test
    fun getTotal() {
        assertEquals(0, subject.total)
    }

    @Test
    fun getTotalAllPieces() {
        subject.addBase(3, SOURCE)
        subject.addBonus(4, SOURCE)
        subject.addMultiplier(5.0, SOURCE)
        assertEquals(19, subject.total)
    }

    @Test
    fun addBaseOneSource() {
        subject.addBase(5, SOURCE)
        assertEquals(5, subject.total)
    }

    @Test
    fun addBaseMultipleSources() {
        subject.addBase(5, SOURCE)
        subject.addBase(3, "other")
        assertEquals(8, subject.total)
    }

    @Test
    fun addBaseOverlappingSource() {
        subject.addBase(5, SOURCE)
        subject.addBase(3, SOURCE)
        assertEquals(3, subject.total)
    }

    @Test
    fun addBonusOneSource() {
        subject.addBonus(5, SOURCE)
        assertEquals(5, subject.total)
    }

    @Test
    fun addBonusMultipleSources() {
        subject.addBonus(5, SOURCE)
        subject.addBonus(3, "other")
        assertEquals(8, subject.total)
    }

    @Test
    fun addBonusOverlappingSource() {
        subject.addBonus(5, SOURCE)
        subject.addBonus(3, SOURCE)
        assertEquals(3, subject.total)
    }

    @Test
    fun addMultiplierOneSource() {
        subject.addBase(5, SOURCE)
        subject.addMultiplier(1.4, SOURCE)
        assertEquals(7, subject.total)
    }

    @Test
    fun addMultiplierMultipleSources() {
        subject.addBase(5, SOURCE)
        subject.addMultiplier(1.4, SOURCE)
        subject.addMultiplier(2.0, "other")
        assertEquals(14, subject.total)
    }

    @Test
    fun addMultiplierOverlappingSource() {
        subject.addBase(5, SOURCE)
        subject.addMultiplier(1.4, SOURCE)
        subject.addMultiplier(2.0, SOURCE)
        assertEquals(10, subject.total)
    }

    @Test
    fun clear() {
        getTotalAllPieces()
        subject.clear(SOURCE)
        assertEquals(0, subject.total)
    }

    @Test
    fun clearDifferentSource() {
        getTotalAllPieces()
        subject.clear("other")
        assertEquals(19, subject.total)
    }

    @Test
    fun clearByPrefix() {
        getTotalAllPieces()
        subject.clearByPrefix("test")
        assertEquals(0, subject.total)
    }

    @Test
    fun clearByPrefixNoMatch() {
        getTotalAllPieces()
        subject.clearByPrefix("other")
        assertEquals(19, subject.total)
    }

    @Test
    fun clearByPrefixPartialMatch() {
        getTotalAllPieces()
        subject.addBase(7, "other")
        subject.clearByPrefix("test")
        assertEquals(7, subject.total)
    }

    companion object {
        private const val SOURCE = "test-source"
    }
}