package link.peipei.countmoney.workflow.home

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

object HomeScreen : Screen {
    @Composable
    override fun Content() {
        HomePage()
    }
}