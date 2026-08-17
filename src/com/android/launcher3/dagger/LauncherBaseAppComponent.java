/*
 * Copyright (C) 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.launcher3.dagger;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.launcher3.InvariantDeviceProfile;
import com.android.launcher3.LauncherAppState;
import com.android.launcher3.LauncherPrefs;
import com.android.launcher3.RemoveAnimationSettingsTracker;
import com.android.launcher3.backuprestore.LauncherRestoreEventLogger;
import com.android.launcher3.compose.core.widgetpicker.WidgetPickerComposeWrapper;
import com.android.launcher3.folder.FolderNameSuggestionLoader;
import com.android.launcher3.graphics.GridCustomizationsProxy;
import com.android.launcher3.graphics.ThemeManager;
import com.android.launcher3.icons.LauncherIcons.IconPool;
import com.android.launcher3.logging.DumpManager;
import com.android.launcher3.logging.StatsLogManager;
import com.android.launcher3.model.ItemInstallQueue;
import com.android.launcher3.model.LoaderCursor.LoaderCursorFactory;
import com.android.launcher3.pm.InstallSessionHelper;
import com.android.launcher3.pm.UserCache;
import com.android.launcher3.util.ApiWrapper;
import com.android.launcher3.util.DaggerSingletonTracker;
import com.android.launcher3.util.DisplayController;
import com.android.launcher3.util.DynamicResource;
import com.android.launcher3.util.InstantAppResolver;
import com.android.launcher3.util.LockedUserState;
import com.android.launcher3.util.MSDLPlayerWrapper;
import com.android.launcher3.util.PackageManagerHelper;
import com.android.launcher3.util.PluginManagerWrapper;
import com.android.launcher3.util.ScreenOnTracker;
import com.android.launcher3.util.SettingsCache;
import com.android.launcher3.util.VibratorWrapper;
import com.android.launcher3.util.WallpaperColorHints;
import com.android.launcher3.util.window.RefreshRateTracker;
import com.android.launcher3.util.window.WindowManagerProxy;
import com.android.launcher3.widget.LauncherWidgetHolder.WidgetHolderFactory;
import com.android.launcher3.widget.custom.CustomWidgetManager;
import com.android.launcher3.widget.util.WidgetSizeHandler;

import javax.inject.Named;

import org.acreetionos.launcher.DeviceProfileOverrides;
import org.acreetionos.launcher.HeadlessWidgetsManager;
import org.acreetionos.launcher.LawnchairActivityCachingLogic;
import org.acreetionos.launcher.NotificationManager;
import org.acreetionos.launcher.data.folder.service.FolderService;
import org.acreetionos.launcher.data.iconoverride.IconOverrideRepository;
import org.acreetionos.launcher.data.wallpaper.service.WallpaperService;
import org.acreetionos.launcher.font.FontCache;
import org.acreetionos.launcher.font.FontManager;
import org.acreetionos.launcher.font.googlefonts.GoogleFontsListing;
import org.acreetionos.launcher.icons.iconpack.IconPackProvider;
import org.acreetionos.launcher.icons.shape.IconShapeManager;
import org.acreetionos.launcher.preferences.PreferenceManager;
import org.acreetionos.launcher.predictions.LawnchairPredictionManager;
import org.acreetionos.launcher.preferences2.PreferenceManager2;
import org.acreetionos.launcher.smartspace.provider.SmartspaceProvider;
import org.acreetionos.launcher.theme.ThemeProvider;
import org.acreetionos.launcher.ui.preferences.components.colorpreference.ColorPreferenceModelList;
import org.acreetionos.launcher.ui.preferences.data.liveinfo.LiveInformationManager;
import org.acreetionos.launcher.util.LawnchairWindowManagerProxy;
import dagger.BindsInstance;

/**
 * Launcher base component for Dagger injection.
 *
 * This class is not actually annotated as a Dagger component, since it is not used directly as one.
 * Doing so generates unnecessary code bloat.
 *
 * See {@link LauncherAppComponent} for the one actually used by AOSP.
 */
public interface LauncherBaseAppComponent {
    DaggerSingletonTracker getDaggerSingletonTracker();
    ApiWrapper getApiWrapper();
    CustomWidgetManager getCustomWidgetManager();
    DynamicResource getDynamicResource();
    InstallSessionHelper getInstallSessionHelper();
    ItemInstallQueue getItemInstallQueue();
    RefreshRateTracker getRefreshRateTracker();
    ScreenOnTracker getScreenOnTracker();
    SettingsCache getSettingsCache();
    PackageManagerHelper getPackageManagerHelper();
    PluginManagerWrapper getPluginManagerWrapper();
    VibratorWrapper getVibratorWrapper();
    MSDLPlayerWrapper getMSDLPlayerWrapper();
    WindowManagerProxy getWmProxy();
    LauncherPrefs getLauncherPrefs();
    ThemeManager getThemeManager();
    UserCache getUserCache();
    DisplayController getDisplayController();
    WallpaperColorHints getWallpaperColorHints();
    LockedUserState getLockedUserState();
    InvariantDeviceProfile getIDP();
    IconPool getIconPool();
    RemoveAnimationSettingsTracker getRemoveAnimationSettingsTracker();
    LauncherAppState getLauncherAppState();

    LauncherRestoreEventLogger getLauncherRestoreEventLogger();
    GridCustomizationsProxy getGridCustomizationsProxy();
    FolderNameSuggestionLoader getFolderNameSuggestionLoader();
    LoaderCursorFactory getLoaderCursorFactory();
    WidgetHolderFactory getWidgetHolderFactory();
    RefreshRateTracker getFrameRateProvider();
    InstantAppResolver getInstantAppResolver();
    DumpManager getDumpManager();
    StatsLogManager.StatsLogManagerFactory getStatsLogManagerFactory();
    ActivityContextComponent.Builder getActivityContextComponentBuilder();
    WidgetPickerComposeWrapper getWidgetPickerComposeWrapper();
    WidgetSizeHandler getWidgetSizeHandler();


    // Lawnchair-specific
    
    LawnchairWindowManagerProxy getLWMP();
    DeviceProfileOverrides getDPO();
    ThemeProvider getThemeProvider();
    SmartspaceProvider getSmartspaceProvider();
    HeadlessWidgetsManager getHeadlessWidgetsManager();
    NotificationManager getNotificationManager();
    ColorPreferenceModelList getColorPreferenceModelList();
    LiveInformationManager getLiveInformationManager();
    LawnchairPredictionManager getLawnchairPredictionManager();
    PreferenceManager2 getPreferenceManager2();
    PreferenceManager getPreferenceManager();
    FontCache getFontCache();
    FontManager getFontManager();
    IconShapeManager getIconShapeManager();
    IconPackProvider getIconPackProvider();
    GoogleFontsListing getGoogleFontsListing();
    WallpaperService getWallpaperService();
    IconOverrideRepository getIconOverrideRepository();

    LawnchairActivityCachingLogic getLawnchairActivityCachingLogic();
    FolderService getFolderService();

    /** Builder for LauncherBaseAppComponent. */
    interface Builder {
        @BindsInstance Builder appContext(@ApplicationContext Context context);
        @BindsInstance Builder iconsDbName(@Nullable @Named("ICONS_DB") String dbFileName);
        @BindsInstance Builder setSafeModeEnabled(@Named("SAFE_MODE") boolean safeModeEnabled);
        LauncherBaseAppComponent build();
    }
}
