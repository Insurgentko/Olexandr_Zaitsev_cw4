package ua.edu.lntu.olexandr_zaitsev_cw4.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import ua.edu.lntu.olexandr_zaitsev_cw4.R
import ua.edu.lntu.olexandr_zaitsev_cw4.data.OrderUiState
import ua.edu.lntu.olexandr_zaitsev_cw4.ui.components.FormattedPriceLabel
import ua.edu.lntu.olexandr_zaitsev_cw4.ui.theme.Olexandr_Zaitsev_cw4Theme

@Composable
fun SummaryScreen(
    orderUiState: OrderUiState,
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources

    val numberOfSubscribe = resources.getQuantityString(
        R.plurals.subscribes,
        orderUiState.duration,
        orderUiState.duration
    )
    val orderSummary = stringResource(
        R.string.subscribe_details,
        numberOfSubscribe,
        orderUiState.type,
        orderUiState.duration
    )
    val newOrder = stringResource(R.string.new_subscribe)
    val items = listOf(
        Pair(stringResource(R.string.duration), numberOfSubscribe),
        Pair(stringResource(R.string.choose_type), orderUiState.type),
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            items.forEach { item ->
                Text(item.first.uppercase())
                Text(text = item.second, fontWeight = FontWeight.Bold)
                Divider(thickness = dimensionResource(R.dimen.thickness_divider))
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            FormattedPriceLabel(
                subtotal = orderUiState.price,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Row(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onSendButtonClicked(newOrder, orderSummary) }
                ) {
                    Text(stringResource(R.string.send))
                }
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onCancelButtonClicked
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun OrderSummaryPreview() {
//    Olexandr_Zaitsev_cw4Theme {
//        SummaryScreen(
//            orderUiState = OrderUiState(0, "Test", "Test", ),
//            onSendButtonClicked = { subject: String, summary: String -> },
//            onCancelButtonClicked = {},
//            modifier = Modifier.fillMaxHeight()
//        )
//    }
//}
