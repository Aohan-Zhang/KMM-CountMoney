package link.peipei.countmoney.workflow.home.record.employee

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.currentOrThrow
import link.peipei.countmoney.workflow.add.employ.EmployeeDetailScreen
import link.peipei.countmoney.workflow.home.LocalGlobalNavigator
import link.peipei.countmoney.workflow.home.record.RecordItem

@Composable
fun EmployeePage(uiState: RecordUiState, onRemove: (String) -> Unit) {
    val navigator = LocalGlobalNavigator.currentOrThrow
    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.employee) {
                Box {
                    RecordItem(it.employ.name, it.employ.position, {
                        navigator.push(EmployeeDetailScreen(it))
                    }) {
                        onRemove(it.employ.id)
                    }

                }
            }
        }
    }


}