package link.peipei.countmoney.home.event

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

internal object EventPageTab : Tab {
    @Composable
    override fun Content() {
        EventPage(rememberScreenModel())
    }

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "活动"
            val selectedIcon = painterResource("icons/chart_data_fill_24dp.xml")
            val unselectedIcon = painterResource("icons/chart_data_24dp.xml")
            val tabNavigator = LocalTabNavigator.current.current
            return TabOptions(
                index = 0u,
                title = title,
                icon = if (tabNavigator==this) selectedIcon else unselectedIcon,
            )
        }

}