package link.peipei.countmoney.workflow.home

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import link.peipei.countmoney.data.di.homeModule
import link.peipei.countmoney.theme.AppTheme
import link.peipei.countmoney.workflow.login.LoginPage
import link.peipei.countmoney.workflow.login.LoginScreen
import org.kodein.di.compose.withDI

@Composable
fun App() {
    withDI(homeModule) {
        AppTheme {
            Navigator(LoginScreen)
        }
    }
}