package link.peipei.countmoney.core_ui.view

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    text: String = "发送验证码",
    iconSource: String = "icons/ic_loading_small.xml",
    enable: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        )
    )
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enable
    ) {
        if (isLoading) {
            Icon(
                modifier = Modifier.graphicsLayer {
                    rotationZ = angle
                },
                painter = painterResource(
                    iconSource
                ),
                contentDescription = null
            )
            Spacer(Modifier.size(8.dp))
        }
        Text(text)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TextLoadingButton(
    modifier: Modifier = Modifier,
    text: String = "发送验证码",
    iconSource: String = "icons/ic_loading_small.xml",
    enable: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        )
    )
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                if (enable) {
                    onClick()
                }
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isLoading) {
            Icon(
                modifier = Modifier.graphicsLayer {
                    rotationZ = angle
                },
                painter = painterResource(
                    iconSource
                ),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
            Spacer(Modifier.size(8.dp))
        }
        Text(text, color = MaterialTheme.colorScheme.primary)
    }
}