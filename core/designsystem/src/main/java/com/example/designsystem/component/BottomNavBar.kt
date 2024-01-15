package com.example.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.designsystem.icons.EatIcons
import com.example.designsystem.theme.EatTheme
import com.example.designsystem.theme.Gray700
import com.example.designsystem.theme.Gray900
import com.example.designsystem.theme.Typography

@Composable
fun BottomAppNavBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.tertiary,
        content = content
    )
}

@Composable
fun RowScope.BottomNavAppBarItem(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selected: Boolean = true,
    alwaysShowLabel: Boolean = true,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable (() -> Unit)? = null,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) { selectedIcon } else { icon },
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.secondaryContainer,
            selectedTextColor = MaterialTheme.colorScheme.secondaryContainer,
            indicatorColor = MaterialTheme.colorScheme.onSecondaryContainer,
            unselectedIconColor = Gray700,
            unselectedTextColor = Gray700,
            disabledIconColor = Gray900,
            disabledTextColor = Gray900
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewItem() {
    val selected = listOf(EatIcons.HomeBorder, EatIcons.CollectionBorder)
    val icons = listOf(EatIcons.HomeOutlined, EatIcons.CollectionOutlined)
    val menu = listOf("홈 화면", "컬렉션")
    EatTheme {
        BottomAppNavBar {
            menu.forEachIndexed { index, item ->
                BottomNavAppBarItem(
                    onClick = { /*TODO*/ },
                    selected = index == 0,
                    icon = {
                        Icon(imageVector = icons[index], contentDescription = "Icon")
                    },
                    selectedIcon = {
                        Icon(imageVector = selected[index], contentDescription = "Icon")
                    },
                    label = {
                        Text(
                            text = item,
                            fontWeight = FontWeight.Bold,
                            style = Typography.labelMedium
                        )
                    }
                )
            }
        }
    }
}
