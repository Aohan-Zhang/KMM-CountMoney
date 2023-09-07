package link.peipei.countmoney.workflow.store.add

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

object CreateStoreScreen:Screen {
    @Composable
    override fun Content() {
        CreateStorePage()
    }
}