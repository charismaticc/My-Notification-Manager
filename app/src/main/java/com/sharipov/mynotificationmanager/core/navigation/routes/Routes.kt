package com.sharipov.mynotificationmanager.core.navigation.routes

sealed class Routes(val route: String) {
    // Bottom nav bar
    data object AllNotificationRoute : Routes("all_notification_route")
    data object FavoriteRoute : Routes("favorite_route")
    data object ApplicationsRoute : Routes("applications_route")
    data object NotificationFilterRoute : Routes("notification_filter_route")
    data object SettingsRoute: Routes("settings_route")

    // Other
    data object PermissionsRoute: Routes("permissions_route")

}
