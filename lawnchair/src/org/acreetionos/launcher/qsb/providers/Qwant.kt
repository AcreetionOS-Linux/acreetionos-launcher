package org.acreetionos.launcher.qsb.providers

import org.acreetionos.launcher.qsb.ThemingMethod
import com.android.launcher3.R

data object Qwant : QsbSearchProvider(
    id = "qwant",
    name = R.string.search_provider_qwant,
    icon = R.drawable.ic_search,
    themedIcon = R.drawable.ic_search,
    themingMethod = ThemingMethod.TINT,
    packageName = "com.qwant.liberte",
    action = "android.intent.action.WEB_SEARCH",
    website = "https://www.qwant.com/",
    type = QsbSearchProviderType.APP_AND_WEBSITE,
)
