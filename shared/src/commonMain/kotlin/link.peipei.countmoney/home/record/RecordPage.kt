@file:OptIn(ExperimentalResourceApi::class)

package link.peipei.countmoney.home.record

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

data class RecordPageTabs(
    val name: String,
    val selectedIcon: Painter,
    val unselectedIcon: Painter,
)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RecordPage(recordPageViewModel: RecordPageViewModel) {
    var state by remember { mutableStateOf(0) }
    val tabs = listOf(
        RecordPageTabs(
            "货品",
            painterResource("icons/storefront_fill_24dp.xml"),
            painterResource("icons/storefront_24dp.xml")
        ),
        RecordPageTabs(
            "员工",
            painterResource("icons/face_fill_24dp.xml"),
            painterResource("icons/face_24dp.xml")
        )
    )
    Column {
        TabRow(
            selectedTabIndex = state,
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ) {
            tabs.forEachIndexed { index, tab ->
                LeadingIconTab(
                    selected = state == index,
                    onClick = { state = index },
                    text = {
                        Text(
                            text = tab.name,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    icon = {
                        if (state == index) {
                            Icon(tab.selectedIcon, contentDescription = tab.name)
                        } else {
                            Icon(tab.unselectedIcon, contentDescription = tab.name)

                        }
                    },
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(16.dp)
        ) {

            items(50) {
                Card(modifier = Modifier.fillMaxWidth().height(220.dp)) { }
            }
        }
    }

}