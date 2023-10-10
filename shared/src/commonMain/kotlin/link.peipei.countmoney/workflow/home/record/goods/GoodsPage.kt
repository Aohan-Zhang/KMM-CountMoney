package link.peipei.countmoney.workflow.home.record.goods

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import link.peipei.countmoney.workflow.home.record.RecordItem

@Composable
fun GoodsPage() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        repeat(10) {
            item {
                RecordItem("测试", it.toString(), {}, {})
            }
        }
    }
}