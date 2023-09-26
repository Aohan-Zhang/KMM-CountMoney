package link.peipei.countmoney.workflow.add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeDetailContent(
    modifier: Modifier = Modifier,
    uiState: EmployeeDetailUiState,
    employPageInteraction: EmployPageInteraction
) {
    var openDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(uiState.date.timestamp)
    val focusManager = LocalFocusManager.current
    if (openDialog) {
        DatePickerDialog(
            onDismissRequest = { openDialog = false },
            confirmButton = {
                TextButton({
                    employPageInteraction.onHireDateUpdate(datePickerState.selectedDateMillis)
                    openDialog = false
                }) {
                    Text("确定")
                }
            },
            dismissButton = {
                TextButton({
                    openDialog = false
                }) {
                    Text("取消")
                }
            },
        ) {
            DatePicker(state = datePickerState, title = {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "入职时间",
                    fontWeight = FontWeight.SemiBold
                )
            })
        }
        focusManager.clearFocus(true)
    }
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                focusManager.clearFocus(true)
            }
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(
            text = "基本信息",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 16.dp),
            fontWeight = FontWeight.SemiBold
        )
        OutlinedTextField(
            isError = uiState.name.showError,
            supportingText = if (uiState.name.showError) {
                { Text(uiState.name.message) }
            } else {
                null
            },
            value = uiState.name.toString(),
            onValueChange = employPageInteraction::onNameUpdate,
            label = { Text("姓名") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 8.dp, end = 8.dp)
        )
        OutlinedTextField(
            isError = uiState.phone.showError,
            supportingText = if (uiState.phone.showError) {
                { Text(uiState.phone.message) }
            } else {
                null
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = uiState.phone.toString(),
            onValueChange = employPageInteraction::onPhoneUpdate,
            label = { Text("手机号") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 8.dp, end = 8.dp)
        )
        OutlinedTextField(
            isError = uiState.position.showError,
            supportingText = if (uiState.position.showError) {
                { Text(uiState.position.message) }
            } else {
                null
            },
            value = uiState.position.toString(),
            onValueChange = employPageInteraction::onPositionUpdate,
            label = { Text("职位") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 8.dp, end = 8.dp)
        )
        val date = uiState.date
        OutlinedTextField(
            value = "${date.year}/${date.month.ordinal}/${date.dayOfMonth}",
            onValueChange = {},
            label = { Text(uiState.dateDes) },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 8.dp, end = 8.dp)
                .onFocusChanged {
                    if (it.isFocused) {
                        openDialog = true
                    }
                },
            readOnly = true
        )
        Text(
            text = "性别",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 16.dp),
            fontWeight = FontWeight.SemiBold
        )
        Row(Modifier.selectableGroup()) {
            RadioButton(
                selected = uiState.gender == 1,
                onClick = { employPageInteraction.onGenderUpdate(true) },
                modifier = Modifier.semantics { contentDescription = "男" }
            )
            Text(
                text = "男",
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
            Spacer(Modifier.width(16.dp))
            RadioButton(
                selected = uiState.gender != 1,
                onClick = { employPageInteraction.onGenderUpdate(false) },
                modifier = Modifier.semantics { contentDescription = "女" }
            )
            Text(
                text = "女",
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
        }

        Text(
            text = "薪资 ￥${uiState.basicSalary}",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 16.dp),
            fontWeight = FontWeight.SemiBold
        )

        Slider(
            value = (uiState.basicSalary.toFloat() / 100),
            onValueChange = {
                employPageInteraction.onSalaryUpdate(it.toInt() * 100)
            },
            valueRange = 0f..100f,
            steps = 0,
            modifier = Modifier.semantics { contentDescription = "Localized Description" },
        )



        Text(
            text = "补贴 ￥${uiState.allowance}",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 16.dp),
            fontWeight = FontWeight.SemiBold
        )

        Slider(
            value = uiState.allowance.toFloat() / 100,
            onValueChange = {
                employPageInteraction.onAllowanceUpdate(it.toInt() * 100)
            },
            valueRange = 0f..100f,
            steps = 0,
            modifier = Modifier.semantics { contentDescription = "Localized Description" },
        )



        Text(
            text = "总绩效 ￥${uiState.bonus}",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 16.dp),
            fontWeight = FontWeight.SemiBold
        )

        Slider(
            value = uiState.bonus.toFloat() / 100,
            onValueChange = {
                employPageInteraction.onBonusUpdate(it.toInt() * 100)
            },
            valueRange = 0f..100f,
            steps = 0,
            modifier = Modifier.semantics { contentDescription = "Localized Description" },
        )

    }
}