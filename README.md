# AcreetionOS Mobile Launcher

[![Build debug APK](https://github.com/AcreetionOS-Code/acreetionos-launcher/actions/workflows/ci.yml/badge.svg)](https://github.com/AcreetionOS-Code/acreetionos-launcher/actions/workflows/ci.yml)
[![Build release APK](https://github.com/AcreetionOS-Code/acreetionos-launcher/actions/workflows/acreetionos-autobuild.yml/badge.svg)](https://github.com/AcreetionOS-Code/acreetionos-launcher/actions/workflows/acreetionos-autobuild.yml)
[![Discord](https://img.shields.io/discord/803299970169700402?label=server&logo=discord)](https://discord.acreetionos.org)
[![GitHub Downloads](https://img.shields.io/github/downloads/AcreetionOS-Code/acreetionos-launcher/total.svg?label=GitHub%20Downloads&logo=github)](https://github.com/AcreetionOS-Code/acreetionos-launcher/releases)

The AcreetionOS Mobile Launcher — a privacy-first home screen for
**AcreetionOS Mobile** (built on LineageOS). Forked from Lawnchair, themed
for AcreetionOS, with all the Google/Microsoft/telemetry bits ripped out.

**AcreetionOS Mobile features:**
- 🗂️ **Custom settings dashboard** — entirely new launcher settings, plus
  AcreetionOS-specific options
- ⚡ **Samsung-style quick panel** — swipe-down control center, AcreetionOS themed
- 📰 **Side panel RSS feed** — Arch Linux + AcreetionOS news where the Google
  feed used to be, with a Discord overlay widget for [discord.acreetionos.org](https://discord.acreetionos.org)
- 🔍 **Qwant search** — default search provider, no Google
- 🛡️ **Search privacy sandbox** — sandboxed search proxied through an
  on-device VPN; no telemetry leaves your phone
- 🧹 **Zero telemetry** — Google/Microsoft tracking removed from the launcher
- 🏠 **acreetionos.org** defaults across about/hub/quickpanel/feed

## Upstream

This project is a fork of [Lawnchair](https://github.com/LawnchairLauncher/lawnchair)
(Launcher3-based). This branch (16-dev) is based on Lawnchair 16 /
Launcher3 from Android 16.

## Features

- Material 3 Expressive theming that follows your wallpaper and system colors.
- At a Glance widget support, with integration for [Smartspacer](https://github.com/KieronQuinn/Smartspacer).
- QuickSwitch support for Android Recents integration on Android 10-15 (root required).
- Global search for apps, contacts, and web results from the home screen.
- Customization options for icon packs, fonts, and color settings.

## Download

<p align="left">
  <a href="https://play.google.com/store/apps/details?id=org.acreetionos.launcher.play">
    <picture>
      <!-- Avoid image being clickable with slight workaround -->
      <source media="(prefers-color-scheme: dark)" srcset="docs/assets/badge-google-play.webp" height="60">
      <img alt="Get it on Google Play" src="docs/assets/badge-google-play.webp" height="60">
    </picture>
  </a>
  <a href="https://apt.izzysoft.de/fdroid/index/apk/org.acreetionos.launcher">
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="docs/assets/badge-izzyondroid.webp" height="60">
      <img alt="Get it on IzzyOnDroid" src="docs/assets/badge-izzyondroid.webp" height="60">
    </picture>
  </a>
  <a href="https://apps.obtainium.imranr.dev/redirect?r=obtainium://add/https://github.com/LawnchairLauncher/lawnchair/">
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="docs/assets/badge-obtainium.webp" height="60">
      <img alt="Get it on Obtainium" src="docs/assets/badge-obtainium.webp" height="60">
    </picture>
  </a>
    <a href="https://github.com/LawnchairLauncher/lawnchair/releases">
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="docs/assets/badge-github.webp" height="60">
      <img alt="Get it on GitHub" src="docs/assets/badge-github.webp" height="60">
    </picture>
  </a>
</p>

Lawnchair on Play Store will install as a different app compared to other sources. Features may be restricted to comply with Google Play’s publishing rules.

You can also [verify your installation](https://docs.lawnchair.app/getting-started/install-and-setup/verify) to check if you have installed an official build.

### Development builds

Interested in keeping yourself up-to-date with every Lawnchair development? Try our development builds!

These builds offer the latest features and bug fixes at a cost of performance and additional issues. Make backups before installing.

Download: [Obtainium][Obtainium link] • [GitHub][GitHub link] • [nightly.link][Nightly link]

## Sponsors

<p align="left">
  <a href="https://coderabbit.link/lawnchair">
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="docs/assets/sponsor-coderabbit-dark.svg" width="300">
      <img alt="CodeRabbit" src="docs/assets/sponsor-coderabbit-light.svg" width="300">
    </picture>
  </a>
</p>

[CodeRabbit](https://coderabbit.link/lawnchair) is an AI-powered code review platform that integrates directly into pull-request workflows and IDEs, examining code changes in context and suggesting improvements.

## Support Lawnchair

If you love what we do, consider [supporting us on Open Collective](https://opencollective.com/lawnchair)! Your contributions help keep Lawnchair independent and enable us to develop faster.

A huge thank you to our Core Backers ($5+):
*(These backers directly fund our Project Velocity Fund)*

[![Core Backers](https://opencollective.com/lawnchair/tiers/backer.svg?avatarHeight=64&width=890&button=false)](https://opencollective.com/lawnchair)

[Become a supporter](https://opencollective.com/lawnchair) to help us cover our operational costs, or become a Core Backer to be featured here!

## Contribute

Visit the [Lawnchair contributing guidelines](CONTRIBUTING.md) for information and tips on contributing to Lawnchair.

## Quick links

- [Website](https://lawnchair.app)
- [Documentation](https://docs.lawnchair.app/)
- [News on Telegram](https://t.me/lawnchairci)
- [Discord](https://discord.com/invite/3x8qNWxgGZ)
- [X (formerly Twitter)](https://x.com/lawnchairapp)
- [_XDA_ thread](https://xdaforums.com/t/lawnchair-customizable-pixel-launcher.3627137/)

<!-- Download links -->
[Nightly link]: https://nightly.link/LawnchairLauncher/lawnchair/workflows/ci/15-dev
[Obtainium link]: https://apps.obtainium.imranr.dev/redirect?r=obtainium://app/%7B%22id%22%3A%22org.acreetionos.launcher.nightly%22%2C%22url%22%3A%22https%3A%2F%2Fgithub.com%2Flawnchairlauncher%2Flawnchair%22%2C%22author%22%3A%22Lawnchair%20Launcher%22%2C%22name%22%3A%22Lawnchair%20(Debug)%22%2C%22preferredApkIndex%22%3A0%2C%22additionalSettings%22%3A%22%7B%5C%22includePrereleases%5C%22%3Atrue%2C%5C%22fallbackToOlderReleases%5C%22%3Afalse%2C%5C%22filterReleaseTitlesByRegEx%5C%22%3A%5C%22Lawnchair%20Nightly%5C%22%2C%5C%22filterReleaseNotesByRegEx%5C%22%3A%5C%22%5C%22%2C%5C%22verifyLatestTag%5C%22%3Afalse%2C%5C%22dontSortReleasesList%5C%22%3Afalse%2C%5C%22useLatestAssetDateAsReleaseDate%5C%22%3Afalse%2C%5C%22trackOnly%5C%22%3Afalse%2C%5C%22versionExtractionRegEx%5C%22%3A%5C%22%5C%22%2C%5C%22matchGroupToUse%5C%22%3A%5C%22%5C%22%2C%5C%22versionDetection%5C%22%3Afalse%2C%5C%22releaseDateAsVersion%5C%22%3Atrue%2C%5C%22useVersionCodeAsOSVersion%5C%22%3Afalse%2C%5C%22apkFilterRegEx%5C%22%3A%5C%22%5C%22%2C%5C%22invertAPKFilter%5C%22%3Afalse%2C%5C%22autoApkFilterByArch%5C%22%3Atrue%2C%5C%22appName%5C%22%3A%5C%22%5C%22%2C%5C%22shizukuPretendToBeGooglePlay%5C%22%3Afalse%2C%5C%22exemptFromBackgroundUpdates%5C%22%3Afalse%2C%5C%22skipUpdateNotifications%5C%22%3Afalse%2C%5C%22about%5C%22%3A%5C%22Lawnchair%20is%20a%20free%2C%20open-source%20home%20app%20for%20Android.%20(NOTE%3A%20This%20is%20the%20debug%20version%20of%20Lawnchair%2C%20for%20the%20beta%2Fstable%20versions%20see%20%5C%5C%5C%22Lawnchair%5C%5C%5C%22)%5C%22%7D%22%7D
[GitHub link]: https://github.com/LawnchairLauncher/lawnchair/releases/tag/nightly
