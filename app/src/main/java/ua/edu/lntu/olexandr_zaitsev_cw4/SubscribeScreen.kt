@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ua.edu.lntu.olexandr_zaitsev_cw4.R
import ua.edu.lntu.olexandr_zaitsev_cw4.ui.StartOrderScreen
import ua.edu.lntu.olexandr_zaitsev_cw4.ui.SubscribeViewModel
import ua.edu.lntu.olexandr_zaitsev_cw4.data.DataSource
import ua.edu.lntu.olexandr_zaitsev_cw4.ui.SelectOptionScreen
import ua.edu.lntu.olexandr_zaitsev_cw4.ui.SummaryScreen

enum class SubscribeScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Type(title = R.string.choose_type),
    Summary(title = R.string.subscribe_summary)
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@Composable
fun SubscribeAppBar(
    currentScreen: SubscribeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun SubscribeApp(
    viewModel: SubscribeViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = SubscribeScreen.valueOf(
        backStackEntry?.destination?.route ?: SubscribeScreen.Start.name
    )

    Scaffold(
        topBar = {
            SubscribeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = SubscribeScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = SubscribeScreen.Start.name) {
                StartOrderScreen(
                    durationOptions = DataSource.durationOptions,
                    onNextButtonClicked = {
                        viewModel.setDuration(it)
                        navController.navigate(SubscribeScreen.Type.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(route = SubscribeScreen.Type.name) {
                val context = LocalContext.current
                SelectOptionScreen(
                    subtotal = uiState.price,
                    onNextButtonClicked = { navController.navigate(SubscribeScreen.Type.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = DataSource.typesSubscriptions.map { id -> context.resources.getString(id) },
                    onSelectionChanged = { viewModel.setType(it) },
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = SubscribeScreen.Summary.name) {
                val context = LocalContext.current
                SummaryScreen(
                    orderUiState = uiState,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onSendButtonClicked = { subject: String, summary: String ->
                        shareOrder(context, subject = subject, summary = summary)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}

private fun cancelOrderAndNavigateToStart(
    viewModel: SubscribeViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(SubscribeScreen.Start.name, inclusive = false)
}

private fun shareOrder(context: Context, subject: String, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_subscribe)
        )
    )
}
