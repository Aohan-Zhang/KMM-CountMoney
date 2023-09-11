package link.peipei.countmoney.workflow.store.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel

object CreateStoreScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<CreateStoreViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        CreateStorePage(
            uiState,
            viewModel.onCreateStoreEvent,
            onTitleInput = viewModel::updateTitle,
            onIndustryInput = viewModel::updateIndustry,
            onScopeInput = viewModel::updateScope,
            onDesInput = viewModel::updateDes,
            onCreateClick = viewModel::create
        )
    }
}