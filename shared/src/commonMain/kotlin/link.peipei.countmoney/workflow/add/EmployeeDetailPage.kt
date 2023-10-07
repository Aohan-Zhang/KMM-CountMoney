package link.peipei.countmoney.workflow.add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.moriatsushi.insetsx.safeArea
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import link.peipei.countmoney.core_ui.view.TextLoadingButton

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun EmployeeDetailPage(
    uiState: EmployeeDetailUiState,
    event: SharedFlow<EmployeeDetailEvent>,
    employPageInteraction: EmployPageInteraction,
    onActionClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardManager = LocalSoftwareKeyboardController.current
    val navigator = LocalNavigator.currentOrThrow
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(event) {
        event.collectLatest {
            when (it) {
                is SnackBarEvent -> {
                    snackbarHostState.showSnackbar(it.message)
                }

                is UpdateResultEvent -> {
                    if (it.result) {
                        navigator.pop()
                    } else {
                        snackbarHostState.showSnackbar("网络出错啦，请重试")
                    }
                }
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                title = {
                    Text(uiState.title, fontWeight = FontWeight.Bold)
                },
                windowInsets = WindowInsets.safeArea.only(
                    WindowInsetsSides.Top + WindowInsetsSides.Horizontal
                ),
                navigationIcon = {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "close page",
                        modifier = Modifier.clip(RoundedCornerShape(32.dp)).clickable {
                            navigator.pop()
                        }.padding(16.dp)
                    )
                },
                actions = {
                    TextLoadingButton(
                        text = uiState.actionName,
                        isLoading = uiState.isUpdating,
                        enable = !uiState.isUpdating
                    ) {
                        focusManager.clearFocus()
                        keyboardManager?.hide()
                        onActionClick()
                    }
                }
            )
        }
    ) {
        EmployeeDetailContent(Modifier.padding(it), uiState, employPageInteraction)
    }
}