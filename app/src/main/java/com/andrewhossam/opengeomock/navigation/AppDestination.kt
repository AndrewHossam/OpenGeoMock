package com.andrewhossam.opengeomock.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Single

sealed interface AppDestination {
    @Serializable
    data object HomeGraph : AppDestination

    @Serializable
    data object MapScreen : AppDestination

    @Serializable
    data object SettingsScreen : AppDestination

    @Serializable
    data object AboutScreen : AppDestination

    @Serializable
    data object FeedbackScreen : AppDestination

    @Serializable
    data object LicenseScreen : AppDestination

    @Serializable
    data object PrivacyPolicyScreen : AppDestination

    @Serializable
    data object TermsOfServiceScreen : AppDestination

    @Serializable
    data object OpenSourceLicensesScreen : AppDestination
}

sealed interface NavigationAction {
    data class Navigate(
        val destination: AppDestination,
        val navOptions: NavOptionsBuilder.() -> Unit = {},
    ) : NavigationAction

    data object NavigateUp : NavigationAction
}

interface Navigator {
    val startDestination: AppDestination
    val navigationActions: Flow<NavigationAction>

    suspend fun navigate(
        destination: AppDestination,
        navOptions: NavOptionsBuilder.() -> Unit = {},
    )

    suspend fun navigateUp()
}

@Single
class DefaultNavigator(
    override val startDestination: AppDestination = AppDestination.HomeGraph,
) : Navigator {
    private val _navigationActions = Channel<NavigationAction>()
    override val navigationActions = _navigationActions.receiveAsFlow()

    override suspend fun navigate(
        destination: AppDestination,
        navOptions: NavOptionsBuilder.() -> Unit,
    ) {
        _navigationActions.send(NavigationAction.Navigate(destination, navOptions))
    }

    override suspend fun navigateUp() {
        _navigationActions.send(NavigationAction.NavigateUp)
    }
}
