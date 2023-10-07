package link.peipei.countmoney.workflow.home.record.employee

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun EmployItem(
    name: String,
    position: String,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val firstChar = name.trim().first().toString().uppercase()

    ListItem(
        modifier = Modifier.clickable {
            onItemClick()
        },
        headlineContent = {
            Text(
                text = name,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        supportingContent = {
            Text(
                text = position,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f))
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = firstChar,
                    color = MaterialTheme.colorScheme.onSecondary,
                    textAlign = TextAlign.Center
                )
            }
        },
        trailingContent = {
            Box {
                var expanded by remember { mutableStateOf(false) }
                Icon(Icons.Default.MoreVert, "", modifier = Modifier.clickable {
                    expanded = true
                })
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("删除") },
                        onClick = {
                            expanded = false
                            onDeleteClick()
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.Delete,
                                contentDescription = null
                            )
                        })

                }
            }
        }
    )

}