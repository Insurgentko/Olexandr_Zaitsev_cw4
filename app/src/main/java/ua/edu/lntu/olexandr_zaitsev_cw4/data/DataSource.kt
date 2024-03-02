package ua.edu.lntu.olexandr_zaitsev_cw4.data

import ua.edu.lntu.olexandr_zaitsev_cw4.R

object DataSource {
    val typesSubscriptions = listOf(
        R.string.classic,
        R.string.student,
        R.string.family,
        R.string.duo,
        R.string.privileges
    )

    val durationOptions = listOf(
        Pair(R.string.month, 1),
        Pair(R.string.six_month, 6),
        Pair(R.string.twelve_month, 12)
    )
}