package link.peipei.countmoney.workflow.home.record.employee

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmployItem(name: String, position: String, onItemClick: () -> Unit) {
    val firstChar = name.trim().first().toString().uppercase()
    Row(Modifier.fillMaxWidth().clickable {
        onItemClick()
    }.padding(24.dp)) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f))
                .align(Alignment.CenterVertically),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = firstChar,
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.size(16.dp))
        Column(modifier = Modifier.align(Alignment.CenterVertically).weight(1f)) {
            Text(
                text = name,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.size(2.dp))
            Text(
                text = position,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Icon(Icons.Filled.MoreVert,
            null,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(24.dp)
                .clip(CircleShape)
                .clickable { })
    }

}