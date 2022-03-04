package dev.logickoder.exams.ui.shared.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.logickoder.exams.ui.NavGraph
import dev.logickoder.exams.ui.Navigation

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController, items = Navigation.navigation())
        }
    ) {
        NavGraph(navController = navController, modifier = Modifier.padding(it))
    }
}