package com.example.phoneinfogetter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.phoneinfogetter.ui.theme.PhoneInfoGetterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val deviceInfo = getDeviceInfo(context = this) // ✅ Get info here
        setContent {
            PhoneInfoGetterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DeviceInfoList(
                        deviceInfo = deviceInfo,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun DeviceInfoList(deviceInfo: Map<String, String>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        for ((key, value) in deviceInfo) {
            Text(text = "$key: $value")
        }
    }
}

// ✅ Safe Preview (can't access Context, so use dummy data)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PhoneInfoGetterTheme {
        val dummyInfo = mapOf(
            "Brand" to "Pixel",
            "Model" to "7 Pro",
            "Android Version" to "13"
        )
        DeviceInfoList(deviceInfo = dummyInfo)
    }
}
