package link.peipei.countmoney.theme

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

//actual fun isSupportDynamicColor(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
//disabled dynamic color
actual fun isSupportDynamicColor(): Boolean = false

@SuppressLint("NewApi")
@Composable
actual fun appDynamicDarkColorScheme(): ColorScheme? {
    return dynamicDarkColorScheme(LocalContext.current)
}

@SuppressLint("NewApi")
@Composable
actual fun appDynamicLightColorScheme(): ColorScheme? {
    return dynamicLightColorScheme(LocalContext.current)
}