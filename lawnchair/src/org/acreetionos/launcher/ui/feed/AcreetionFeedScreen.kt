/*
 * Copyright 2026, AcreetionOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.acreetionos.launcher.ui.feed

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material.icons.rounded.DynamicFeed
import androidx.compose.material.icons.rounded.OpenInNew
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.RssFeed
import androidx.compose.material.icons.rounded.Timeline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.net.HttpURLConnection
import java.net.URL

data class FeedItem(
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String,
    val source: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcreetionFeedScreen(
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var selectedTab by remember { mutableIntStateOf(0) }
    var feedItems by remember { mutableStateOf<List<FeedItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    fun refreshFeeds() {
        coroutineScope.launch {
            isLoading = true
            feedItems = fetchAllFeeds()
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        refreshFeeds()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.DynamicFeed,
                            contentDescription = null,
                            tint = Color(0xFF2ECC71),
                            modifier = Modifier.size(24.dp),
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "AcreetionOS Feed & Community",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { refreshFeeds() }) {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = "Refresh",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            // Discord Widget Banner
            DiscordCommunityBanner(
                onJoinClick = {
                    openBrowser(context, "https://discord.acreetionos.org")
                },
            )

            // Tabs for Arch Linux vs AcreetionOS
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = Color(0xFF2ECC71),
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("All News", fontWeight = FontWeight.SemiBold) },
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Arch Linux", fontWeight = FontWeight.SemiBold) },
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = { Text("AcreetionOS", fontWeight = FontWeight.SemiBold) },
                )
            }

            val filteredItems = when (selectedTab) {
                1 -> feedItems.filter { it.source.contains("Arch", ignoreCase = true) }
                2 -> feedItems.filter { it.source.contains("Acreetion", ignoreCase = true) }
                else -> feedItems
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
            ) {
                if (isLoading && feedItems.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(color = Color(0xFF2ECC71))
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(filteredItems) { item ->
                            FeedItemCard(
                                item = item,
                                onClick = {
                                    openBrowser(context, item.link)
                                },
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DiscordCommunityBanner(
    onJoinClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1F29),
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2C2F48)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f),
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF5865F2)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Chat,
                        contentDescription = "Discord",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp),
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "AcreetionOS Discord",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                    Text(
                        text = "discord.acreetionos.org • Active Community",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFB0B3C8),
                    )
                }
            }

            Button(
                onClick = onJoinClick,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5865F2),
                    contentColor = Color.White,
                ),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
            ) {
                Text("Join Chat", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun FeedItemCard(
    item: FeedItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
        ),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            if (item.source.contains("Arch", ignoreCase = true)) Color(0xFF1793D1).copy(alpha = 0.2f)
                            else Color(0xFF2ECC71).copy(alpha = 0.2f),
                        )
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                ) {
                    Text(
                        text = item.source,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (item.source.contains("Arch", ignoreCase = true)) Color(0xFF1793D1) else Color(0xFF2ECC71),
                    )
                }

                if (item.pubDate.isNotBlank()) {
                    Text(
                        text = item.pubDate,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            if (item.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp,
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Read Article",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2ECC71),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Rounded.OpenInNew,
                    contentDescription = null,
                    tint = Color(0xFF2ECC71),
                    modifier = Modifier.size(14.dp),
                )
            }
        }
    }
}

private suspend fun fetchAllFeeds(): List<FeedItem> = withContext(Dispatchers.IO) {
    val items = mutableListOf<FeedItem>()

    // Fetch Arch Linux News
    try {
        val archNews = fetchRss("https://archlinux.org/feeds/news/", "Arch Linux")
        items.addAll(archNews)
    } catch (_: Exception) {
        // Fallback default item
        items.add(
            FeedItem(
                title = "Arch Linux Rolling Releases & Updates Active",
                link = "https://archlinux.org/news/",
                description = "Latest package updates, security advisories, and kernel updates from upstream Arch Linux.",
                pubDate = "Live Stream",
                source = "Arch Linux",
            ),
        )
    }

    // Fetch AcreetionOS Updates
    try {
        val acreetionNews = fetchRss("https://acreetionos.org/feed.xml", "AcreetionOS")
        items.addAll(acreetionNews)
    } catch (_: Exception) {
        items.add(
            FeedItem(
                title = "AcreetionOS 16 Mobile & Desktop Rolling Release",
                link = "https://acreetionos.org/newsletter.html",
                description = "System sovereignty, zero-telemetry foundations, Mingle Wayland Compositor, and LineageOS mobile compatibility.",
                pubDate = "Active Release",
                source = "AcreetionOS",
            ),
        )
    }

    if (items.isEmpty()) {
        items.add(
            FeedItem(
                title = "Welcome to AcreetionOS Mobile Ecosystem",
                link = "https://acreetionos.org",
                description = "Engineered for user freedom, zero telemetry, and digital independence.",
                pubDate = "Official",
                source = "AcreetionOS",
            ),
        )
    }

    items
}

private fun fetchRss(urlStr: String, sourceName: String): List<FeedItem> {
    val result = mutableListOf<FeedItem>()
    val url = URL(urlStr)
    val conn = url.openConnection() as HttpURLConnection
    conn.connectTimeout = 5000
    conn.readTimeout = 5000
    conn.requestMethod = "GET"
    conn.setRequestProperty("User-Agent", "AcreetionOS-Mobile/1.0")

    if (conn.responseCode == HttpURLConnection.HTTP_OK) {
        val xml = conn.inputStream.bufferedReader().use { it.readText() }
        val factory = XmlPullParserFactory.newInstance()
        val parser = factory.newPullParser()
        parser.setInput(StringReader(xml))

        var eventType = parser.eventType
        var insideItem = false
        var title = ""
        var link = ""
        var description = ""
        var pubDate = ""
        var currentTag = ""

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    currentTag = parser.name
                    if (currentTag.equals("item", ignoreCase = true) || currentTag.equals("entry", ignoreCase = true)) {
                        insideItem = true
                        title = ""
                        link = ""
                        description = ""
                        pubDate = ""
                    }
                }
                XmlPullParser.TEXT -> {
                    if (insideItem) {
                        val text = parser.text?.trim() ?: ""
                        when {
                            currentTag.equals("title", ignoreCase = true) -> title += text
                            currentTag.equals("link", ignoreCase = true) -> link += text
                            currentTag.equals("description", ignoreCase = true) || currentTag.equals("summary", ignoreCase = true) -> description += text
                            currentTag.equals("pubDate", ignoreCase = true) || currentTag.equals("published", ignoreCase = true) -> pubDate += text
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    val tagName = parser.name
                    if (tagName.equals("item", ignoreCase = true) || tagName.equals("entry", ignoreCase = true)) {
                        if (title.isNotBlank()) {
                            // Strip basic HTML tags from description
                            val cleanDesc = description.replace(Regex("<[^>]*>"), "").take(180)
                            result.add(
                                FeedItem(
                                    title = title,
                                    link = if (link.isNotBlank()) link else "https://acreetionos.org",
                                    description = cleanDesc,
                                    pubDate = pubDate.take(16),
                                    source = sourceName,
                                ),
                            )
                        }
                        insideItem = false
                    }
                    currentTag = ""
                }
            }
            eventType = parser.next()
        }
    }
    conn.disconnect()
    return result
}

private fun openBrowser(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}
