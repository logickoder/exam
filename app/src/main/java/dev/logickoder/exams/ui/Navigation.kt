package dev.logickoder.exams.ui

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.logickoder.exams.R
import dev.logickoder.exams.ui.screens.create_new_exam.CreateNewExamFirstScreen
import dev.logickoder.exams.ui.screens.create_new_exam.CreateNewExamSecondScreen
import dev.logickoder.exams.ui.screens.dashboard.DashboardScreen
import dev.logickoder.exams.ui.screens.exams.ExamsScreen
import dev.logickoder.exams.ui.shared.components.EmptyScreen
import dev.logickoder.exams.ui.shared.components.bottomBarNavigate

typealias Navigate = (Navigator) -> Unit

enum class Navigator {
    Forward,
    Backward
}

sealed class Navigation(
    val title: String,
    @DrawableRes val icon: Int,
    val route: String,
    val parent: Navigation? = null
) {
    object Page1 : Navigation("Page 1", R.drawable.ic_android, "/page1/")
    object Page2 : Navigation("Page 2", R.drawable.ic_android, "/page2/")
    object Dashboard : Navigation("Dashboard", R.drawable.ic_dashboard, "/dashboard/")
    object ExamsSection : Navigation("Exams", R.drawable.ic_exams, "/exams") {
        object Exams : Navigation("Exams", icon, "$route/")
        object CreateNewExam1 :
            Navigation("Create New Exam", icon, "$route/create_page_1/")

        object CreateNewExam2 :
            Navigation("Create New Exam", icon, "$route/create_page_2/")
    }

    object Page5 : Navigation("Page 5", R.drawable.ic_android, "/page5/")

    companion object {
        fun navigation() = listOf(Page1, Page2, Dashboard, ExamsSection.Exams, Page5)
    }
}

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = Navigation.Dashboard.route, modifier = modifier) {
        composable(Navigation.Page1.route) {
            EmptyScreen()
        }
        composable(Navigation.Page2.route) {
            EmptyScreen()
        }
        dashboardGraph(navController)
        examsGraph(navController)
        composable(Navigation.Page5.route) {
            EmptyScreen()
        }
    }
}

fun NavGraphBuilder.dashboardGraph(navController: NavController) {
    composable(Navigation.Dashboard.route) {
        DashboardScreen { navigator ->
            when (navigator) {
                Navigator.Forward -> {
                    navController.bottomBarNavigate(Navigation.ExamsSection.route)
                    navController.navigate(Navigation.ExamsSection.CreateNewExam1.route)
                }
                else -> {}
            }
        }
    }
}

fun NavGraphBuilder.examsGraph(navController: NavController) {
    navigation(
        startDestination = Navigation.ExamsSection.Exams.route,
        route = Navigation.ExamsSection.route
    ) {
        composable(Navigation.ExamsSection.Exams.route) {
            ExamsScreen { navigator ->
                when (navigator) {
                    Navigator.Forward ->
                        navController.navigate(Navigation.ExamsSection.CreateNewExam1.route)
                    else -> {}
                }
            }
        }
        composable(Navigation.ExamsSection.CreateNewExam1.route) {
            CreateNewExamFirstScreen { navigator ->
                when (navigator) {
                    Navigator.Forward ->
                        navController.navigate(Navigation.ExamsSection.CreateNewExam2.route)
                    Navigator.Backward -> navController.navigateUp()
                }
            }
        }
        composable(Navigation.ExamsSection.CreateNewExam2.route) {
            CreateNewExamSecondScreen { navigator ->
                when (navigator) {
                    Navigator.Forward ->
                        navController.bottomBarNavigate(Navigation.ExamsSection.Exams.route)
                    Navigator.Backward -> navController.navigateUp()
                }
            }
        }
    }
}