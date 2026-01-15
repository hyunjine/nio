package com.hyunjine.nio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hyunjine.clothes.list.ClothesScreen
import com.hyunjine.common.ui.theme.NioTheme
import com.hyunjine.common.ui.theme.white
import com.hyunjine.focus.main.FocusScreen
import com.hyunjine.timer.main.TimerMainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NioTheme {
                NioApp()
            }
        }
    }
}

@Composable
fun NioApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NioScreen.Home.name,
        modifier = Modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(
            route = NioScreen.Home.name
        ) {
            HomeScreen(
                onClick = { screen ->
                    navController.navigate(screen.name)
                }
            )
        }
        val modifier = Modifier
            .fillMaxSize()
            .background(white)
        composable(
            route = NioScreen.Clothes.name
        ) {
            ClothesScreen(modifier = modifier)
        }
        composable(
            route = NioScreen.Lock.name
        ) {
            FocusScreen(modifier = modifier)
        }
        composable(
            route = NioScreen.Timer.name
        ) {
            TimerMainScreen(
                modifier = modifier.statusBarsPadding(),
                onBack = { navController.popBackStack() }
            )
        }
    }
}

enum class NioScreen(val screenName: String) {
    Home("홈"),
    Clothes("옷"),
    Lock("집중 모드"),
    Timer("타이머"),
}