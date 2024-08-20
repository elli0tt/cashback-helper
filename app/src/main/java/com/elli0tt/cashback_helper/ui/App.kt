package com.elli0tt.cashback_helper.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elli0tt.cashback_helper.R
import com.elli0tt.cashback_helper.ui.screen.Screens
import com.elli0tt.cashback_helper.ui.screen.cashback.add_card.AddBankCardWithCashbackCategoriesScreen
import com.elli0tt.cashback_helper.ui.screen.cashback.debug_actions.DebugActionsScreen
import com.elli0tt.cashback_helper.ui.screen.cashback.saved_cards.SavedBankCardsScreen
import com.elli0tt.cashback_helper.ui.screen.cashback.table.CashbackCategoriesTableScreen
import org.koin.androidx.compose.KoinAndroidContext

@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    val navigationBarItems = listOf(
        NavigationBarItemState(
            label = stringResource(R.string.navigation_bar_cashback_categories_label),
            onClick = { navController.navigate(route = Screens.CashbackCategoriesTable) },
            icon = Icons.Filled.AccountBox
        ),
        NavigationBarItemState(
            label = stringResource(R.string.navigation_bar_bank_cards_label),
            onClick = { navController.navigate(route = Screens.SavedBankCards) },
            icon = Icons.Filled.AccountBox
        ),
        NavigationBarItemState(
            label = "Debug actions",
            onClick = { navController.navigate(route = Screens.DebugActions) },
            icon = Icons.Filled.Build
        )
    )
    KoinAndroidContext {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    navigationBarItems.forEachIndexed { index, navigationBarItemState ->
                        NavigationBarItem(
                            selected = index == selectedItemIndex,
                            onClick = {
                                navigationBarItemState.onClick()
                                selectedItemIndex = index
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Filled.AccountBox,
                                    contentDescription = ""
                                )
                            },
                            label = { Text(text = navigationBarItemState.label) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screens.CashbackCategoriesTable,
                Modifier.padding(innerPadding)
            ) {
                cashbackCategoriesTableScreen(navController)
                addBankCardWithCashbackCategoriesScreen(navController)
                savedBankCardsScreen()
                debugActionsScreen()
            }
        }
    }
}

private data class NavigationBarItemState(
    val label: String,
    val onClick: () -> Unit,
    val icon: ImageVector
)

private fun NavGraphBuilder.cashbackCategoriesTableScreen(navController: NavHostController) =
    composable<Screens.CashbackCategoriesTable> {
        CashbackCategoriesTableScreen(
            onNavigateToAddBankCardWithCashbackCategories = {
                navController.navigate(route = Screens.AddBankCardWithCashbackCategories)
            }
        )
    }

private fun NavGraphBuilder.addBankCardWithCashbackCategoriesScreen(
    navController: NavHostController
) = composable<Screens.AddBankCardWithCashbackCategories> {
    AddBankCardWithCashbackCategoriesScreen(
        onNavigateBack = { navController.popBackStack() }
    )
}

private fun NavGraphBuilder.savedBankCardsScreen() =
    composable<Screens.SavedBankCards> {
        SavedBankCardsScreen()
    }

private fun NavGraphBuilder.debugActionsScreen() =
    composable<Screens.DebugActions> {
        DebugActionsScreen()
    }
