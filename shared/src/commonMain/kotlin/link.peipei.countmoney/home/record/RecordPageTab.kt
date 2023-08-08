package link.peipei.countmoney.home.record

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

internal object RecordPageTab : Tab {
    @Composable
    override fun Content() {
        RecordPage(rememberScreenModel())
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
                icon = if (tabNavigator==this) selectedIcon else unselectedIcon,
            )
        }

}