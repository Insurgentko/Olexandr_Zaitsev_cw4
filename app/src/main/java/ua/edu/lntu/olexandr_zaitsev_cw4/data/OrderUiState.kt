package ua.edu.lntu.olexandr_zaitsev_cw4.data

data class OrderUiState(
    /** Selected type of subscribe (Famyly, Duo, Classic) */
    val duration: String = "",
    /** Selected duration of subscribe (1, 6, 12) */
    val type: Int = 0,
    /** Total price for the order */
    val price: String = "",
)
