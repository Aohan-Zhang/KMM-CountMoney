package link.peipei.countmoney.workflow.store.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.media.compose.BindMediaPickerEffect
import dev.icerock.moko.media.compose.rememberMediaPickerControllerFactory
import dev.icerock.moko.media.compose.toImageBitmap
import dev.icerock.moko.media.picker.CanceledException
import dev.icerock.moko.media.picker.MediaPickerController
import dev.icerock.moko.media.picker.MediaSource
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import link.peipei.countmoney.core_ui.view.LoadingButton
import link.peipei.countmoney.core_ui.view.TextLoadingButton
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun CreateStorePage(
    uiState: CreateStoreUiState,
    createEvent: SharedFlow<Boolean>,
    onTitleInput: (String) -> Unit,
    onIndustryInput: (String) -> Unit,
    onScopeInput: (String) -> Unit,
    onDesInput: (String) -> Unit,
    onCreateClick: () -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow

    val permissionFactory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController =
        remember(permissionFactory) { permissionFactory.createPermissionsController() }

    val factory = rememberMediaPickerControllerFactory()
    val picker = remember(factory) { factory.createMediaPickerController() }

    val snackbarHostState = remember { SnackbarHostState() }

    var image: ImageBitmap? by remember { mutableStateOf(null) }

    val focusManager: FocusManager = LocalFocusManager.current


    image?.let {
        Image(bitmap = it, contentDescription = null)
    }
    val interactionSource = remember { MutableInteractionSource() }


    BindEffect(controller)

    BindMediaPickerEffect(picker)

    LaunchedEffect(createEvent) {
        createEvent.collectLatest {
            if (it) {
                navigator.pop()
            } else {
                snackbarHostState.showSnackbar("创建失败，请重试")
            }
        }
    }

    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            focusManager.clearFocus()
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "close page",
                        modifier = Modifier.clip(RoundedCornerShape(32.dp)).clickable {
                            navigator.pop()
                        }.padding(16.dp)
                    )
                },
                title = {
                    Text(" 创建店铺")
                },
                actions = {
                    TextLoadingButton(
                        text = "创建店铺",
                        isLoading = uiState.isLoading,
                        enable = !uiState.isLoading
                    ) {
                        onCreateClick()
                    }
                }
            )
        }) {
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .size(200.dp)
                    .clip(
                        RoundedCornerShape(64.dp)
                    )
                    .clickable {
                        coroutineScope.launch {
                            selectImage(controller, picker, snackbarHostState)?.let { photo ->
                                image = photo
                            }
                        }
                    }
            ) {
                if (image == null) {
                    val painter = painterResource("pic/original_shop_illustration.jpg")
                    Image(
                        painter,
                        null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Image(
                        bitmap = image!!,
                        null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                if (image == null) {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f))
                    )
                    Text(
                        "点击上传照片",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

            }

            Spacer(Modifier.size(16.dp))
            OutlinedTextField(
                isError = uiState.title.showError,
                supportingText = if (uiState.title.showError) {
                    { Text(uiState.title.message) }
                } else {
                    null
                },
                value = uiState.title.content,
                onValueChange = onTitleInput,
                label = { Text("店铺名") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (uiState.title.shouldShowCleanButton() && !uiState.underSubmit) {
                        TrailingIcon {
                            onTitleInput("")
                        }
                    }
                }
            )
            Spacer(Modifier.size(16.dp))
            OutlinedTextField(
                isError = uiState.industry.showError,
                supportingText = if (uiState.industry.showError) {
                    { Text(uiState.industry.message) }
                } else {
                    null
                },
                value = uiState.industry.content,
                onValueChange = onIndustryInput,
                label = { Text("行业") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (uiState.industry.shouldShowCleanButton() && !uiState.underSubmit) {
                        TrailingIcon {
                            onTitleInput("")
                        }
                    }
                }
            )
            Spacer(Modifier.size(16.dp))
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = uiState.scope.showError,
                supportingText = if (uiState.scope.showError) {
                    { Text(uiState.scope.message) }
                } else {
                    null
                },
                value = uiState.scope.content,
                onValueChange = onScopeInput,
                label = { Text("规模") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (uiState.scope.shouldShowCleanButton() && !uiState.underSubmit) {
                        TrailingIcon {
                            onTitleInput("")
                        }
                    }
                }
            )
            Spacer(Modifier.size(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth().height(160.dp),
            ) {
                OutlinedTextField(
                    value = uiState.des.content,
                    onValueChange = onDesInput,
                    label = { Text("描述") },
                    modifier = Modifier.fillMaxSize(),
                )
                Text(
                    uiState.desIndicatorText,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
                )
            }

        }

    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TrailingIcon(onClick: () -> Unit) {
    val icon = painterResource("icons/ic_cancel_24.xml")
    Icon(
        icon,
        null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.clickable {
            onClick()
        }
    )
}

suspend fun selectImage(
    controller: PermissionsController,
    picker: MediaPickerController,
    snackbarHostState: SnackbarHostState
): ImageBitmap? {

    return try {
        controller.providePermission(Permission.GALLERY)
        picker.pickImage(MediaSource.GALLERY).toImageBitmap()
    } catch (e: DeniedAlwaysException) {
        snackbarHostState.showSnackbar("我们需要您授权照片权限，以便选择照片。请在设置中手动打开权限")
        null
    } catch (e: DeniedException) {
        snackbarHostState.showSnackbar("我们需要您授权照片权限，以便选择照片。")
        null
    } catch (e: CanceledException) {
        null
    }

}