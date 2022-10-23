package br.com.cursoideal.converter

import androidx.databinding.InverseMethod
import java.text.NumberFormat
import java.time.Period

private const val DOUBLE_ZERO_VALUE = 0.0
private const val INT_ZERO_VALUE = 0

object Converter {

    @InverseMethod("stringToInt")
    @JvmStatic
    fun intToString(value: Int): String = value.toString()

    @JvmStatic
    fun stringToInt(value: String): Int {
        if (value.isBlank()) {
            return INT_ZERO_VALUE
        }

        return value.toInt()
    }

    @InverseMethod("stringToDouble")
    @JvmStatic
    fun doubleToString(value: Double): String = value.toString()

    @JvmStatic
    fun stringToDouble(value: String): Double {
        if (value.isBlank()) {
            return DOUBLE_ZERO_VALUE
        }

        return value.toDouble()
    }

    @JvmStatic
    fun doubleToStringMoneyValue(value: Double): String {
        val numberFormatter = NumberFormat.getCurrencyInstance()
        return numberFormatter.format(value)
    }

    @JvmStatic
    fun semestersToStringRepresentation(value: Int): String {
        val period = Period.ofMonths(value * 6).normalized()

        period.apply {
            val yearsValue = if (years > 0) years.toString() else ""
            val monthsValue = if (months > 0) months.toString() else ""
            val yearsLabel = if (years > 1) "anos" else if (years != 0) "ano" else ""
            val monthsLabel = if (months > 1) "meses" else if (months != 0) "mês" else ""
            val and = if (yearsValue.isNotBlank() && monthsValue.isNotBlank()) "e" else ""

            return "$yearsValue $yearsLabel $and $monthsValue $monthsLabel"
        }
    }
}