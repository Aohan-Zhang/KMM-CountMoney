package link.peipei.countmoney.workflow.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.moriatsushi.insetsx.systemBars
import link.peipei.countmoney.workflow.home.event.EventPageTab
import link.peipei.countmoney.workflow.home.record.RecordPageTab

val LocalGlobalNavigator = compositionLocalOf<Navigator?> { null }

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomePage() {
    val navigator = LocalNavigator.currentOrThrow
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current
    val keyboardManager = LocalSoftwareKeyboardController.current
    CompositionLocalProvider(LocalGlobalNavigator provides navigator) {
        TabNavigator(EventPageTab) {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                contentWindowInsets = WindowInsets.systemBars,
                topBar = {
                    HomeTopBar(scrollBehavior)
                },
                bottomBar = {
                    HomeBottomBar(listOf(EventPageTab, RecordPageTab))
                }
            ) {
                Box(
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        focusManager.clearFocus(true)
                        keyboardManager?.hide()
                    }.padding(it)
                ) {
                    CurrentTab()
                }
            }
        }
    }

}