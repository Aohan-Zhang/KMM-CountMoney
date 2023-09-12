package link.peipei.countmoney.workflow.store.list

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import co.touchlab.kermit.Logger
import com.moriatsushi.insetsx.navigationBarsPadding
import com.moriatsushi.insetsx.statusBarsPadding
import link.peipei.countmoney.workflow.home.HomeScreen
import link.peipei.countmoney.workflow.store.add.CreateStoreScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StoreSelectionPage(
    uiState: StoreSelectionUiState,
    onRetryClick: () -> Unit,
    onStoreClick: (Int) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val navigator = LocalNavigator.currentOrThrow
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topBarState)

    Logger.i(messageString = uiState.toString())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        "门店",
                        fontSize = mapOffsetToSp(
                            topBarState.heightOffset,
                            topBarState.heightOffsetLimit
                        ).sp,
                        fontWeight = FontWeight.Normal
                    )
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Box(
            modifier = Modifier.padding(it).fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            if (uiState.storeSelectionLoadingState.isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center).size(32.dp).clickable {
                    onRetryClick()
                })
            } else if (uiState.storeSelectionLoadingState.isError) {
                Button(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = {
                        onRetryClick()
                    }
                ) {
                    Text("重试")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .statusBarsPadding()
                        .padding(start = 24.dp, end = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.tertiaryContainer)
                                .padding(24.dp),

                            ) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                val icon = painterResource("icons/ic_lightbulb_fill_24.xml")
                                Icon(
                                    icon,
                                    null,
                                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    "选择你的门店，选择后可以在设置里面修改，第一次进入请创建或加入门店",
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                    itemsIndexed(uiState.items) { i, item ->
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    onStoreClick(i)
                                }
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(0.7f))
                                .padding(24.dp)
                        ) {
                            Column {
                                Text(
                                    item.name,
                                    fontSize = 22.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    item.des,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(top = 8.dp)

                                )

                            }

                        }
                    }

                }
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .navigationBarsPadding()
                        .padding(16.dp),
                    onClick = {
                        navigator.push(CreateStoreScreen())
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
}

fun mapOffsetToSp(current: Float, inMin: Float): Int {
    return ((current - inMin) * (46 - 24) / (0 - inMin) + 24).toInt()
}