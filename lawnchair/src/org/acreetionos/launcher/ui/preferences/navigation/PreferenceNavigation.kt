package org.acreetionos.launcher.ui.preferences.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import org.acreetionos.launcher.backup.ui.CreateBackupScreen
import org.acreetionos.launcher.backup.ui.restoreBackupGraph
import org.acreetionos.launcher.backup.ui.restoreNovaBackupGraph
import org.acreetionos.launcher.preferences.BasePreferenceManager
import org.acreetionos.launcher.preferences.preferenceManager
import org.acreetionos.launcher.ui.preferences.LocalIsExpandedScreen
import org.acreetionos.launcher.ui.preferences.about.About
import org.acreetionos.launcher.ui.preferences.about.acknowledgements.Acknowledgements
import org.acreetionos.launcher.ui.preferences.components.colorpreference.ColorPreferenceModelList
import org.acreetionos.launcher.ui.preferences.components.colorpreference.ColorSelection
import org.acreetionos.launcher.ui.preferences.components.search.SearchProviderId
import org.acreetionos.launcher.ui.preferences.components.search.SearchProviderPreferenceScreen
import org.acreetionos.launcher.ui.feed.AcreetionFeedScreen
import org.acreetionos.launcher.ui.preferences.destinations.AcreetionHub
import org.acreetionos.launcher.ui.preferences.destinations.AppDrawerFoldersPreference
import org.acreetionos.launcher.ui.preferences.destinations.AppDrawerPreferences
import org.acreetionos.launcher.ui.preferences.destinations.BackupAndRestorePreference
import org.acreetionos.launcher.ui.preferences.destinations.CustomIconShapePreference
import org.acreetionos.launcher.ui.preferences.destinations.DebugMenuPreferences
import org.acreetionos.launcher.ui.preferences.destinations.DismissedPredictionAppsPreferences
import org.acreetionos.launcher.ui.preferences.destinations.DockPreferences
import org.acreetionos.launcher.ui.preferences.destinations.DummyPreference
import org.acreetionos.launcher.ui.preferences.destinations.ExperimentalFeaturesPreferences
import org.acreetionos.launcher.ui.preferences.destinations.FeatureFlagsPreference
import org.acreetionos.launcher.ui.preferences.destinations.FolderPreferences
import org.acreetionos.launcher.ui.preferences.destinations.FontSelection
import org.acreetionos.launcher.ui.preferences.destinations.GeneralPreferences
import org.acreetionos.launcher.ui.preferences.destinations.GesturePreferences
import org.acreetionos.launcher.ui.preferences.destinations.HiddenAppsPreferences
import org.acreetionos.launcher.ui.preferences.destinations.HomeScreenGridPreferences
import org.acreetionos.launcher.ui.preferences.destinations.HomeScreenPreferences
import org.acreetionos.launcher.ui.preferences.destinations.IconPackPreferences
import org.acreetionos.launcher.ui.preferences.destinations.IconPickerPreference
import org.acreetionos.launcher.ui.preferences.destinations.LauncherPopupPreference
import org.acreetionos.launcher.ui.preferences.destinations.PickAppForGesture
import org.acreetionos.launcher.ui.preferences.destinations.PredictionsPreferences
import org.acreetionos.launcher.ui.preferences.destinations.PreferencesDashboard
import org.acreetionos.launcher.ui.preferences.destinations.PrivacyShieldPreferences
import org.acreetionos.launcher.ui.preferences.destinations.AcreetionSpecificPreferences
import org.acreetionos.launcher.ui.preferences.destinations.AcreetionSettingsDashboard
import org.acreetionos.launcher.ui.preferences.destinations.QuickstepPreferences
import org.acreetionos.launcher.ui.preferences.destinations.SearchPreferences
import org.acreetionos.launcher.ui.preferences.destinations.SearchProviderPreferences
import org.acreetionos.launcher.ui.preferences.destinations.SelectAppsForDrawerFolder
import org.acreetionos.launcher.ui.preferences.destinations.SelectIconPreference
import org.acreetionos.launcher.ui.preferences.destinations.ShapePreference
import org.acreetionos.launcher.ui.preferences.destinations.SmartspacePreferences
import com.android.launcher3.util.ComponentKey
import soup.compose.material.motion.animation.materialSharedAxisXIn
import soup.compose.material.motion.animation.materialSharedAxisXOut
import soup.compose.material.motion.animation.rememberSlideDistance

inline fun <reified T> getDeepLink(route: T) where T : PreferenceRoute, T : PreferenceDeepLink = listOf(navDeepLink<T>(basePath = route.deepLink))

@Composable
fun PreferenceNavigation(
    navController: NavHostController,
    startDestination: PreferenceRoute,
    intent: Intent? = null,
) {
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val slideDistance = rememberSlideDistance()

    LaunchedEffect(intent) {
        intent?.let { navController.handleDeepLink(it) }
    }

    // TODO: navigate to nav3: https://developer.android.com/guide/navigation/navigation-3
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { materialSharedAxisXIn(!isRtl, slideDistance) },
        exitTransition = { materialSharedAxisXOut(!isRtl, slideDistance) },
        popEnterTransition = { materialSharedAxisXIn(isRtl, slideDistance) },
        popExitTransition = { materialSharedAxisXOut(isRtl, slideDistance) },
    ) {
        composable<Root> {
            val isExpandedScreen = LocalIsExpandedScreen.current

            PreferencesDashboard(
                currentRoute = Root,
                onNavigate = {
                    navController.navigate(it)
                },
            )

            LaunchedEffect(isExpandedScreen) {
                if (isExpandedScreen) {
                    navController.navigate(General) {
                        launchSingleTop = true
                        popUpTo(navController.graph.id)
                    }
                }
            }
        }
        composable<Dummy> {
            DummyPreference()
        }

        composable<General>(
            deepLinks = getDeepLink(General),
        ) { GeneralPreferences() }
        composable<GeneralFontSelection> { backStackEntry ->
            val route: GeneralFontSelection = backStackEntry.toRoute()
            val pref = preferenceManager().prefsMap[route.prefKey]
                as? BasePreferenceManager.FontPref ?: return@composable
            FontSelection(pref)
        }
        composable<GeneralIconPack>(
            deepLinks = getDeepLink(GeneralIconPack),
        ) { IconPackPreferences() }
        composable<GeneralIconShape> { backStackEntry ->
            val route: GeneralIconShape = backStackEntry.toRoute()
            ShapePreference(currentTab = route.selectedId)
        }
        composable<GeneralCustomIconShapeCreator>(
            deepLinks = getDeepLink(GeneralCustomIconShapeCreator()),
        ) { backStackEntry ->
            val route: GeneralCustomIconShapeCreator = backStackEntry.toRoute()
            CustomIconShapePreference(currentTab = route.selectedId)
        }

        composable<HomeScreen>(
            deepLinks = getDeepLink(HomeScreen),
        ) { HomeScreenPreferences() }
        composable<HomeScreenGrid>(
            deepLinks = getDeepLink(HomeScreenGrid),
        ) { HomeScreenGridPreferences() }
        composable<HomeScreenPopupEditor>(
            deepLinks = getDeepLink(HomeScreenPopupEditor),
        ) { LauncherPopupPreference() }

        composable<Dock>(
            deepLinks = getDeepLink(Dock),
        ) { DockPreferences() }
        composable<DockSearchProvider>(
            deepLinks = getDeepLink(DockSearchProvider),
        ) { SearchProviderPreferences() }

        composable<Smartspace>(
            deepLinks = getDeepLink(Smartspace),
        ) { SmartspacePreferences(fromWidget = false) }
        composable<SmartspaceWidget> { SmartspacePreferences(fromWidget = true) }

        composable<AppDrawer>(
            deepLinks = getDeepLink(AppDrawer),
        ) { AppDrawerPreferences() }
        composable<AppDrawerHiddenApps>(
            deepLinks = getDeepLink(AppDrawerHiddenApps),
        ) { HiddenAppsPreferences() }
        composable<AppDrawerAppListToFolder> { backStackEntry ->
            val args = backStackEntry.arguments!!
            val folderInfoId = args.getInt("id")
            SelectAppsForDrawerFolder(folderInfoId)
        }
        composable<AppDrawerFolder>(
            deepLinks = getDeepLink(AppDrawerFolder),
        ) { AppDrawerFoldersPreference() }

        composable<Search>(
            deepLinks = getDeepLink(Search()),
        ) { backStackEntry ->
            val route: Search = backStackEntry.toRoute()
            SearchPreferences(currentTab = route.selectedId)
        }
        composable<SearchProviderPreference>(
            deepLinks = getDeepLink(SearchProviderPreference(SearchProviderId.entries.first())),
        ) { backStackEntry ->
            val route: SearchProviderPreference = backStackEntry.toRoute()
            SearchProviderPreferenceScreen(route.id)
        }

        composable<Folders>(
            deepLinks = getDeepLink(Folders),
        ) { FolderPreferences() }

        composable<Gestures>(
            deepLinks = getDeepLink(Gestures),
        ) { GesturePreferences() }
        composable<GesturesPickApp> { PickAppForGesture() }

        composable<Quickstep>(
            deepLinks = getDeepLink(Quickstep),
        ) { QuickstepPreferences() }
        composable<BackupAndRestore>(
            deepLinks = getDeepLink(BackupAndRestore),
        ) { BackupAndRestorePreference() }

        composable<AcreetionHub>(
            deepLinks = getDeepLink(AcreetionHub),
        ) { AcreetionHub() }
        composable<AcreetionFeed>(
            deepLinks = getDeepLink(AcreetionFeed),
        ) { AcreetionFeedScreen() }
        composable<PrivacyShield>(
            deepLinks = getDeepLink(PrivacyShield),
        ) { PrivacyShieldPreferences() }

        composable<AcreetionSpecificSettings>(
            deepLinks = getDeepLink(AcreetionSpecificSettings),
        ) { AcreetionSpecificPreferences() }

        composable<AcreetionQuickPanelSettings>(
            deepLinks = getDeepLink(AcreetionQuickPanelSettings),
        ) { AcreetionSettingsDashboard() }

        composable<About>(
            deepLinks = getDeepLink(About),
        ) { About() }
        composable<AboutLicenses>(
            deepLinks = getDeepLink(AboutLicenses),
        ) { Acknowledgements() }

        composable<DebugMenu> { DebugMenuPreferences() }
        composable<FeatureFlags> { FeatureFlagsPreference() }

        composable<SelectIcon> { backStackEntry ->
            val args: SelectIcon = backStackEntry.toRoute()
            val componentKey = args.componentKey
            val key = ComponentKey.fromString(componentKey)!!
            SelectIconPreference(key)
        }
        composable<IconPicker> { backStackEntry ->
            val args: IconPicker = backStackEntry.toRoute()
            IconPickerPreference(packageName = args.packageName)
        }

        composable<ExperimentalFeatures>(
            deepLinks = getDeepLink(ExperimentalFeatures),
        ) { ExperimentalFeaturesPreferences() }
        composable<Predictions>(
            deepLinks = getDeepLink(Predictions),
        ) { PredictionsPreferences() }
        composable<DismissedPredictionApps> { DismissedPredictionAppsPreferences() }
        composable<ColorSelection> { backStackEntry ->
            val screen: ColorSelection = backStackEntry.toRoute()
            val modelList = ColorPreferenceModelList.INSTANCE.get(LocalContext.current)
            val model = modelList[screen.prefKey]
            ColorSelection(
                label = stringResource(id = model.labelRes),
                preference = model.prefObject,
                dynamicEntries = model.dynamicEntries,
            )
        }

        composable<CreateBackup>(
            deepLinks = getDeepLink(CreateBackup),
        ) { CreateBackupScreen(viewModel()) }

        restoreBackupGraph()
        restoreNovaBackupGraph()
    }
}
