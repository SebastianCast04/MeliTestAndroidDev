package com.example.melitest.domain.util

import java.util.Locale
import kotlin.math.floor
import kotlin.random.Random

object FakeData {

    private fun seeded(seed: String) = Random(seed.hashCode())

    fun rating(id: String): Float {
        val rating = seeded("rating:$id")
        return ((30 + rating.nextInt(21)) / 10f)
    }

    fun reviews(id: String): Int {
        val r = seeded("reviews:$id")
        return 80 + r.nextInt(3721)
    }

    fun price(id: String, domainId: String? = null): Double {
        val r = seeded("price:$id")
        val (min, max) = when {
            domainId?.contains("CELLPHONES", true) == true -> 399.0 to 1599.0
            domainId?.contains("HEADPHONES", true) == true -> 19.0  to 399.0
            else -> 15.0  to 499.0
        }
        val base = min + r.nextDouble() * (max - min)
        val dollars = floor(base).toInt()
        return dollars + 0.99
    }

    fun currencyFromSite(site: String): String = when (site.uppercase(Locale.ROOT)) {
        "MLA" -> "ARS"
        "MLB" -> "BRL"
        "MLM" -> "MXN"
        "MCO" -> "COP"
        "MLC" -> "CLP"
        "MLU" -> "UYU"
        "MLV" -> "VES"
        "MPE" -> "PEN"
        else -> {"USD"}
    }
}