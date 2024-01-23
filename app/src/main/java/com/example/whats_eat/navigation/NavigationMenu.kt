package com.example.whats_eat.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.designsystem.icons.EatIcons

sealed class NavigationMenu(
    val route: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val screenName: String
) {
   data object Home: NavigationMenu (
       route = "Home",
       icon = EatIcons.HomeOutlined,
       selectedIcon = EatIcons.HomeBorder,
       screenName = "홈 화면"
   )

   data object Collection: NavigationMenu (
       route = "Collection",
       icon = EatIcons.CollectionOutlined,
       selectedIcon = EatIcons.CollectionBorder,
       screenName = "컬렉션"
   )
}