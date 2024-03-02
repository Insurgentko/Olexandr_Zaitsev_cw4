package ua.edu.lntu.olexandr_zaitsev_cw4.data

object DataSource {
    val typesSubscriptions = listOf(
        "Classic",
        "Student",
        "Family",
        "Duo",
        "Privileges"
    )

    val durationOptions = listOf(
        Pair("One month", 1),
        Pair("Half of year", 6),
        Pair("One year", 12)
    )
}