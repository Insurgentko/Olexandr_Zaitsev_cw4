package ua.edu.lntu.olexandr_zaitsev_cw4.data

data class OrderUiState(
    val type: String = "",
    val duration: Int = 0,
    val price: String = "",
    val pickupOptions: List<String> = listOf()
)
