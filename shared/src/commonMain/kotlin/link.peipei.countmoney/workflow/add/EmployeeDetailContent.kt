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
import io.ktor.util.date.GMTDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeDetailContent(
    modifier: Modifier = Modifier,
    uiState: EmployeeDetailUiState,
    employPageInteraction: EmployPageInteraction
) {
    var openDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(uiState.hireDate.timestamp)
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
            value = uiState.name.toString(),
            onValueChange = employPageInteraction::onNameUpdate,
            label = { Text("姓名") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 8.dp, end = 8.dp)
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = uiState.phone.toString(),
            onValueChange = employPageInteraction::onPhoneUpdate,
            label = { Text("手机号") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 8.dp, end = 8.dp)
        )
        OutlinedTextField(
            value = uiState.position.toString(),
            onValueChange = {},
            label = { Text("职位") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 8.dp, end = 8.dp)
        )
        val date = uiState.hireDate
        OutlinedTextField(
            enabled = uiState.hireDateEnable,
            value = "${date.year}/${date.month.ordinal}/${date.dayOfMonth}",
            onValueChange = {},
            label = { Text("入职时间") },
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
        var state by remember { mutableStateOf(uiState.gender == 1) }
        Row(Modifier.selectableGroup()) {
            RadioButton(
                selected = state,
                onClick = { state = true },
                modifier = Modifier.semantics { contentDescription = "男" }
            )
            Text(
                text = "男",
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
            Spacer(Modifier.width(16.dp))
            RadioButton(
                selected = !state,
                onClick = { state = false },
                modifier = Modifier.semantics { contentDescription = "女" }
            )
            Text(
                text = "女",
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
        }
        var incomeValue by remember { mutableStateOf(uiState.basicSalary.toFloat() / 100) }

        Text(
            text = "薪资 ￥${incomeValue.toInt() * 100}",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 16.dp),
            fontWeight = FontWeight.SemiBold
        )

        Slider(
            value = incomeValue,
            onValueChange = {
                incomeValue = it
            },
            valueRange = 0f..100f,
            steps = 0,
            modifier = Modifier.semantics { contentDescription = "Localized Description" },
        )

        var bonusValue by remember { mutableStateOf(uiState.allowance.toFloat() / 100) }


        Text(
            text = "补贴 ￥${bonusValue.toInt() * 100}",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 16.dp),
            fontWeight = FontWeight.SemiBold
        )

        Slider(
            value = bonusValue,
            onValueChange = {
                bonusValue = it
            },
            valueRange = 0f..100f,
            steps = 0,
            modifier = Modifier.semantics { contentDescription = "Localized Description" },
        )

        var performance by remember { mutableStateOf(uiState.bonus.toFloat() / 100) }


        Text(
            text = "总绩效 ￥${performance.toInt() * 100}",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 16.dp),
            fontWeight = FontWeight.SemiBold
        )

        Slider(
            value = performance,
            onValueChange = {
                performance = it
            },
            valueRange = 0f..100f,
            steps = 0,
            modifier = Modifier.semantics { contentDescription = "Localized Description" },
        )

    }
}