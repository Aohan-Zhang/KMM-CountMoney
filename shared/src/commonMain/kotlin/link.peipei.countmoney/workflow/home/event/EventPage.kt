package link.peipei.countmoney.workflow.home.event

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun EventPage(eventPageViewModel: EventPageViewModel, modifier: Modifier = Modifier) {
    val isLogging by eventPageViewModel.isLogin.collectAsState()
    val userResponse by eventPageViewModel.userResponse.collectAsState()
    Column {
        Text(text = isLogging.toString())

        Text(text = userResponse.toString())
        Button(onClick = {
            eventPageViewModel.logout()
        }) {
            Text(text = "登出")
        }
        Box(modifier=Modifier.fillMaxWidth()) {
            SwipeToDismissListItems()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SwipeToDismissListItems() {
    val dismissState = rememberDismissState()
    SwipeToDismiss(
        state = dismissState,
        background = {
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissValue.Default -> MaterialTheme.colorScheme.surface
                    DismissValue.DismissedToEnd -> Color.Green
                    DismissValue.DismissedToStart -> Color.Red
                }
            )
            Box(Modifier.fillMaxSize().background(color))
        },
        dismissContent = {
            Card {
                ListItem(
                    headlineContent = {
                        Text("Cupcake")
                    },
                    supportingContent = { Text("Swipe me left or right!") }
                )
            }
        }
    )
}