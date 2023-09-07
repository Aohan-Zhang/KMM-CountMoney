package link.peipei.countmoney.workflow.store

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.moriatsushi.insetsx.navigationBarsPadding
import com.moriatsushi.insetsx.statusBarsPadding
import link.peipei.countmoney.workflow.store.add.CreateStoreScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun StoreSelectionPage() {
    val snackbarHostState = remember { SnackbarHostState() }
    val navigator = LocalNavigator.currentOrThrow

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .statusBarsPadding()
                    .padding(start = 24.dp, top = 24.dp, end = 24.dp)
            ) {
                Text(
                    "门店",
                    fontSize = 46.sp,
                    fontWeight = FontWeight.Normal
                )
                Box(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .padding(24.dp),

                    ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        val icon = painterResource("icons/ic_lightbulb_fill_24.xml")
                        Icon(icon, null, tint = MaterialTheme.colorScheme.onTertiaryContainer)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            "选择你的门店，选择后可以在设置里面修改，第一次进入请创建或加入门店",
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            fontSize = 14.sp
                        )
                    }
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.size(16.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(0.7f))
                            .padding(24.dp)
                    ) {
                        Column {
                            Text(
                                "曾三鲜 保利叶语",
                                fontSize = 22.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "我是描述描述，随便再说两句，还要说两句",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(top = 8.dp)

                            )

                        }

                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(0.7f))
                            .padding(24.dp)
                    ) {
                        Column {
                            Text(
                                "集鱼 宇宙中心店",
                                fontSize = 22.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "我是描述描述，随便再说两句，还要说两句，多说一点，吃虾虾",
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
                    navigator.push(CreateStoreScreen)
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