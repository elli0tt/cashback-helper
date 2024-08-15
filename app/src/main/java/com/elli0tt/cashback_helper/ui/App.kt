package com.elli0tt.cashback_helper.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elli0tt.cashback_helper.ui.screen.cashback.add_card.AddBankCardWithCashbackCategoriesScreen
import com.elli0tt.cashback_helper.ui.screen.cashback.table.CashbackCategoriesTableScreen
import com.elli0tt.cashback_helper.ui.screen.Screens
import org.koin.androidx.compose.KoinAndroidContext

@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    KoinAndroidContext {
        NavHost(
            navController = navController,
            startDestination = Screens.CashbackCategoriesTable
        ) {
            composable<Screens.CashbackCategoriesTable> {
                CashbackCategoriesTableScreen(
                    onNavigateToAddBankCardWithCashbackCategories = {
                        navController.navigate(route = Screens.AddBankCardWithCashbackCategories)
                    }
                )
            }
            composable<Screens.AddBankCardWithCashbackCategories> {
                AddBankCardWithCashbackCategoriesScreen()
            }
        }
    }
}

