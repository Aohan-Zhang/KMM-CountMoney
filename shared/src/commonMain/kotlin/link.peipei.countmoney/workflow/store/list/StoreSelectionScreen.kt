package link.peipei.countmoney.workflow.store.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel

class StoreSelectionScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<StoreSelectionViewModel>()
        val storeSelectionUiState by viewModel.pageUiState.collectAsState()
        StoreSelectionPage(storeSelectionUiState, {
            viewModel.refresh()
        }, {
            viewModel.onStoreSelected(it)
        })
    }
}