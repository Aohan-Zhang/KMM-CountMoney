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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.moriatsushi.insetsx.safeArea

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEmployeePage() {
    val navigator = LocalNavigator.currentOrThrow
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                title = {
                    Text("新增员工", fontWeight = FontWeight.Bold)
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
                    Text(
                        "保存",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            )
        }
    ) {
        AddEmployeeContent(Modifier.padding(it))
    }
}