package com.elli0tt.cashback_helper.domain.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class CashbackPercentFormatterTest {

    @Test
    fun `check percent with integer part only`() {
        assertEquals("10%", CashbackPercentFormatter.format(10f))
    }

    @Test
    fun `check percent with fractional part only`() {
        assertEquals("0.1%", CashbackPercentFormatter.format(0.1f))
    }

    @Test
    fun `check percent with integer and fractional parts`() {
        assertEquals("10.1%", CashbackPercentFormatter.format(10.1f))
    }

    @Test
    fun `check percent with 2 digits after point`() {
        assertEquals("10.12%", CashbackPercentFormatter.format(10.12f))
    }
}