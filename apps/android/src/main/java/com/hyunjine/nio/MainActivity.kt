package com.hyunjine.nio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.hyunjine.clothes.list.ClothesScreen
import com.hyunjine.common.ui.theme.NioTheme
import com.hyunjine.common.ui.theme.white
import com.hyunjine.focus.main.FocusScreen
import com.hyunjine.timer.main.TimerMainScreen
import com.hyunjine.timer.running.TimerRunningScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

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
) {
    val backStack = rememberNavBackStack(NioScreen.Home)
    val modifier = Modifier
        .fillMaxSize()
        .background(white)
    NavDisplay(
        backStack = backStack,
        transitionSpec = {
            ContentTransform(
                targetContentEnter = EnterTransition.None,
                initialContentExit = ExitTransition.None
            )
        },
        popTransitionSpec = {
            ContentTransform(
                targetContentEnter = EnterTransition.None,
                initialContentExit = ExitTransition.None
            )
        },
        predictivePopTransitionSpec = {
            ContentTransform(
                targetContentEnter = EnterTransition.None,
                initialContentExit = ExitTransition.None
            )
        },
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            // Add the default decorators for managing scenes and saving state
            rememberSaveableStateHolderNavEntryDecorator(),
            // Then add the view model store decorator
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<NioScreen.Home> {
                HomeScreen(
                    modifier = modifier,
                    onClick = { screen -> backStack.add(screen) }
                )
            }
            entry<NioScreen.Lock> {
                FocusScreen(modifier = modifier)
            }
            entry<NioScreen.Timer> {
                TimerMainScreen(
                    modifier = modifier.statusBarsPadding(),
                    onBack = { backStack.removeLastOrNull() },
                    onTimerSelected = {
                        backStack.add(
                            TimerRunningScreen(
                                timerState = it.state
                            )
                        )
                    }
                )
            }
            entry<TimerRunningScreen> { screen ->
                screen(
                    onBack = { backStack.removeLastOrNull() }
                )
            }
            entry<NioScreen.Clothes> {
                ClothesScreen(modifier = modifier)
            }
        }
    )
}

sealed interface NioScreen: NavKey {
    val name: String

    @Serializable
    data object Home: NioScreen {
        override val name: String = "홈"
    }
    @Serializable
    data object Clothes: NioScreen {
        override val name: String = "옷"
    }
    @Serializable
    data object Lock: NioScreen {
        override val name: String = "집중"
    }
    @Serializable
    data object Timer: NioScreen {
        override val name: String = "타이머"
    }
}