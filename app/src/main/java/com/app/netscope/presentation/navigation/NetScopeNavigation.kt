package com.app.netscope.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.netscope.presentation.scanner.ScannerScreen
import com.app.netscope.presentation.tools.ToolsScreen
import com.app.netscope.presentation.wifi.WifiScreen
import com.app.netscope.presentation.reports.ReportsScreen

@Composable
fun NetScopeNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        NavHost(
            navController = navController,
            startDestination = Screen.Scanner.route
        ) {
            composable(Screen.Scanner.route) {
                ScannerScreen()
            }
            composable(Screen.Tools.route) {
                ToolsScreen()
            }
            composable(Screen.Wifi.route) {
                WifiScreen()
            }
            composable(Screen.Reports.route) {
                ReportsScreen()
            }
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: String) {
    object Scanner : Screen("scanner", "Scanner", "search")
    object Tools : Screen("tools", "Tools", "build")
    object Wifi : Screen("wifi", "Wi-Fi Info", "wifi")
    object Reports : Screen("reports", "Reports", "assessment")
}

val bottomNavItems = listOf(
    Screen.Scanner,
    Screen.Tools,
    Screen.Wifi,
    Screen.Reports
)
