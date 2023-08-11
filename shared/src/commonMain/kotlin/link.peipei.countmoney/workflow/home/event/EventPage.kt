package link.peipei.countmoney.workflow.home.event

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EventPage(eventPageViewModel: EventPageViewModel, modifier: Modifier = Modifier) {
    Column {
        Button(onClick = { eventPageViewModel.insert() }) {
            Text(text = "insert")
        }
        Button(onClick = { eventPageViewModel.delete() }) {
            Text(text = "delete")
        }
    }

}