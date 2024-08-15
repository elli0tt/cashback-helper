package com.elli0tt.cashback_helper.domain.utils

object CashbackPercentFormatter {
    fun format(value: Float): String {
        val percent = if (value - value.toInt() >= 0.1f) {
            value.toString()
        } else {
            value.toInt().toString()
        }
        return "$percent%"
    }
}