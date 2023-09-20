package link.peipei.countmoney.workflow.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.Navigator
import co.touchlab.kermit.Logger
import link.peipei.countmoney.data.UserManager
import link.peipei.countmoney.data.di.homeModule
import link.peipei.countmoney.theme.AppTheme
import link.peipei.countmoney.workflow.login.LoginScreen
import link.peipei.countmoney.workflow.store.list.StoreSelectionScreen
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.withDI

@Composable
fun App() {
    withDI(homeModule) {
        val userManager by rememberInstance<UserManager>()
        val isLogin by userManager.getLoggingStatusFlow().collectAsState(false)
        val currentStoreId by userManager.getUserStore().collectAsState(null)
        AppTheme {
            if (isLogin) {
                if (currentStoreId == null) {
                    Navigator(StoreSelectionScreen())
                } else {
                    Navigator(HomeScreen)
                }
            } else {
                Navigator(LoginScreen)
            }
        }
    }
}