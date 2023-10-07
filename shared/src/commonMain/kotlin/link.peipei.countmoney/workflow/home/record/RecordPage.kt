@file:OptIn(ExperimentalResourceApi::class)

package link.peipei.countmoney.workflow.home.record

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import link.peipei.countmoney.data.entities.EmployWithSalary
import link.peipei.countmoney.workflow.add.EmployeeDetailScreen
import link.peipei.countmoney.workflow.home.LocalGlobalNavigator
import link.peipei.countmoney.workflow.home.record.employee.EmployeePage
import link.peipei.countmoney.workflow.home.record.employee.EmployeeUiState
import link.peipei.countmoney.workflow.home.record.goods.GoodsPage
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

data class RecordPageTabs(
    val name: String,
    val selectedIcon: Painter,
    val unselectedIcon: Painter,
)

@OptIn(
    ExperimentalResourceApi::class, ExperimentalFoundationApi::class
)
@Composable
fun RecordPage(
    employeeUiState: EmployeeUiState,
    onRetryClick: () -> Unit,
    onDeleteClick: (String) -> Unit
) {
    val navigator = LocalGlobalNavigator.currentOrThrow

    val pagerState = rememberPagerState {
        2
    }
    val scope = rememberCoroutineScope()
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
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ) {
            tabs.forEachIndexed { index, tab ->
                LeadingIconTab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = tab.name,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    icon = {
                        if (pagerState.currentPage == index) {
                            Icon(tab.selectedIcon, contentDescription = tab.name)
                        } else {
                            Icon(tab.unselectedIcon, contentDescription = tab.name)

                        }
                    },
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                when (page) {
                    0 -> {
                        GoodsPage()
                    }

                    1 -> {
                        EmployeePage(employeeUiState, onRetryClick, onDeleteClick)
                    }
                }
            }
            FloatingActionButton(
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                onClick = {
                    navigator.push(EmployeeDetailScreen(EmployWithSalary.empty()))
                },
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "add"
                )
            }
        }
    }


}