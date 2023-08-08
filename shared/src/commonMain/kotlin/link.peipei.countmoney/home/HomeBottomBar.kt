package link.peipei.countmoney.home

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import com.moriatsushi.insetsx.navigationBarsPadding
import com.moriatsushi.insetsx.safeArea

@Composable
fun HomeBottomBar(
    tabOptions: List<Tab>,
) {
    NavigationBar(
        windowInsets = WindowInsets.safeArea.only(
            WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal
        )
    ) {
        val tabNavigator = LocalTabNavigator.current
        tabOptions.forEachIndexed { _, item ->
            NavigationBarItem(
                modifier = Modifier.navigationBarsPadding(),
                icon = { Icon(item.options.icon!!, contentDescription = item.options.title) },
                selected = tabNavigator.current == item,
                onClick = { tabNavigator.current = item },
            )
        }
    }
}