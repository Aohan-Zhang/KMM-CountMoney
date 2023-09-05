package link.peipei.countmoney.workflow.store

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

object StoreSelectionScreen: Screen {
    @Composable
    override fun Content() {
        StoreSelectionPage()
    }
}