package link.peipei.countmoney.workflow.home.record

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import co.touchlab.kermit.Logger
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

internal object RecordPageTab : Tab {

    @Composable
    override fun Content() {
        Logger.d("dadadada")
        val viewModel = rememberScreenModel<RecordPageViewModel>()
        val employeeUiState by viewModel.uiState.collectAsState()
        RecordPage(employeeUiState, viewModel::refresh, viewModel::delete)
    }

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "记录"
            val selectedIcon = painterResource("icons/list_alt_fill_24dp.xml")
            val unselectedIcon = painterResource("icons/list_alt_24dp.xml")
            val tabNavigator = LocalTabNavigator.current.current

            return TabOptions(
                index = 1u,
                title = title,
                icon = if (tabNavigator == this) selectedIcon else unselectedIcon,
            )
        }

}