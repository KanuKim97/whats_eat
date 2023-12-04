package com.example.whats_eat.ui.preview

import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Galaxy S21 Ultra",
    showBackground = true,
    device = "spec:width=1440px, height=3200px, dpi=516"
)
@Preview(
    name = "Galaxy S21 Plus",
    showBackground = true,
    device = "spec:width=1080px, height=2400px, dpi=392"
)
@Preview(
    name = "Galaxy S21",
    showBackground = true,
    device = "spec:width=1080px, height=2400px, dpi=420"
)
annotation class DevicePreview
