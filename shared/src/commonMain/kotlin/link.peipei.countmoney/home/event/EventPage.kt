package link.peipei.countmoney.home.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EventPage(eventPageViewModel: EventPageViewModel, modifier: Modifier = Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier,
        contentPadding = PaddingValues(16.dp)
    ) {

        items(50) {
            Card(modifier = Modifier.fillMaxWidth().height(220.dp)) { }
        }
    }
}