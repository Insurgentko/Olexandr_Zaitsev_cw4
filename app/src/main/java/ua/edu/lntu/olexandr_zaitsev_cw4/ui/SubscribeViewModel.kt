package ua.edu.lntu.olexandr_zaitsev_cw4.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ua.edu.lntu.olexandr_zaitsev_cw4.R
import ua.edu.lntu.olexandr_zaitsev_cw4.data.OrderUiState
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val PRICE_PER_MONTH = 60.00

private const val PRICE_FOR_FAMILY = 160.00
private const val PRICE_FOR_DUO = 100.00
private const val PRICE_FOR_STUDENT = 40.00
private const val PRICE_FOR_PRIVILEGES = 30.00

class SubscribeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState(pickupOptions = pickupOptions()))
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    @Composable
    fun setDuration(Duration: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                duration = Duration,
                price = calculatePrice(duration = Duration)
            )
        }
    }

    fun setType(subscribeType: String) {
        _uiState.update { currentState ->
            currentState.copy(type = subscribeType)
        }
    }

    fun resetOrder() {
        _uiState.value = OrderUiState(pickupOptions = pickupOptions())
    }

    @Composable
    private fun calculatePrice(
        duration: Int = _uiState.value.duration,
        type: String = _uiState.value.type
    ): String {
        var calculatedPrice = duration * PRICE_PER_MONTH
        when(type){
            stringResource(R.string.student) -> calculatedPrice = duration * PRICE_FOR_STUDENT
            stringResource(R.string.family) -> calculatedPrice = duration * PRICE_FOR_FAMILY
            stringResource(R.string.duo) -> calculatedPrice = duration * PRICE_FOR_DUO
            stringResource(R.string.privileges) -> calculatedPrice = duration * PRICE_FOR_PRIVILEGES
        }
        val formattedPrice = NumberFormat.getCurrencyInstance().format(calculatedPrice)
        return formattedPrice
    }

    private fun pickupOptions(): List<String> {
        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return dateOptions
    }
}