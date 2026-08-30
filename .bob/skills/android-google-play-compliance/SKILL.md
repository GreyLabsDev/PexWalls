---
name: android-google-play-compliance
description: >
  Checks an Android project's compileSdk, minSdk, and targetSdk against
  current Google Play Store requirements. Fetches the live requirements from
  official Google sources before checking -- never uses a hardcoded snapshot.
  Reports PASS / WARN / FAIL for each requirement with impact explanation.
  Use when the user says SDK compliance, targetSdk check, Play Store
  requirements, minSdk policy, compileSdk check, or is my project compliant.
user-invocable: true
---

# Google Play SDK Compliance Check

Verify the project's SDK configuration against **live** Google Play requirements.
**Never use hardcoded API level thresholds from your training data.**
**Report only -- do not edit build files.**

## Workflow

<Steps>
<Step>
### Read project SDK levels

Read the project's SDK configuration from:
1. `app/build.gradle.kts` or `app/build.gradle` -- look for
   `compileSdk`, `minSdk`, `targetSdk` inside `android { defaultConfig { } }`
2. If values are defined as variables or version catalog aliases, resolve
   them from the same file or from `gradle/libs.versions.toml`

State the exact values found and the file they came from.
Also note: is Jetpack Compose used? Is Wear OS / Android TV / Android Auto
declared in the manifest? These affect which requirements apply.
</Step>
<Step>
### Fetch live Google Play requirements

**Do not rely on training-data snapshots for API level thresholds.**
Fetch the current requirements from these official sources in order:

1. **Target API level requirements (primary)**
   https://developer.android.com/google/play/requirements/target-sdk

2. **Android API levels reference**
   https://developer.android.com/tools/releases/platforms

3. **Wear OS requirements (if applicable)**
   https://developer.android.com/training/wearables/apps/packaging

4. **Android TV requirements (if applicable)**
   https://developer.android.com/training/tv/start/start

5. **Google Play policies overview**
   https://support.google.com/googleplay/android-developer/answer/9859152

Read each relevant page. Extract:
- Current minimum targetSdk required for new app submissions
- Current minimum targetSdk required for updates to existing apps
- Deadline dates for any upcoming requirement changes
- Any form-factor-specific requirements that apply to this project

If a page is unreachable, state "FETCH FAILED: <url>" and fall back to
your most recent training knowledge for that specific item only -- clearly
label it as "UNVERIFIED (training data, fetch failed, date unknown)".
</Step>
<Step>
### Check compileSdk / targetSdk / minSdk

Using the values fetched in Step 2, evaluate each requirement:

**Hard requirements (FAIL if not met -- Play Store will reject submissions):**
- targetSdk >= current Play Store minimum for new apps
- targetSdk >= current Play Store minimum for app updates
- compileSdk >= targetSdk

**Google engineering recommendations (WARN if not met -- no rejection, but action advised):**
- compileSdk should equal the latest stable Android SDK level
- targetSdk should be no more than one level below compileSdk
- minSdk >= 21 (minimum for ART, 64-bit requirement, modern Play Services)
- minSdk >= 24 if the project uses Java 8 API calls without core library desugaring
- minSdk >= 21 if Jetpack Compose is used (Compose minimum)
- Check if any upcoming deadline applies (e.g. "from August 2025, targetSdk >= 36")
  and report it as a WARN even if the project currently passes the hard requirement

For each check state: PASS / WARN / FAIL, the threshold used, and where
that threshold was sourced (URL + section name).
</Step>
<Step>
### Check manifest and build flags

Read `app/src/main/AndroidManifest.xml` and the release buildType in
`app/build.gradle.kts` / `app/build.gradle`. Check:

| Item | PASS condition |
|------|---------------|
| `android:allowBackup` | Explicitly declared (either true or false) |
| `android:usesCleartextTraffic` | Explicitly set to `false` in release, or `networkSecurityConfig` used |
| `useLibrary("org.apache.http.legacy")` | Absent when targetSdk >= 28 |
| `isMinifyEnabled` | True for release builds (WARN if false -- not a Play requirement but a security recommendation) |

Assign PASS / WARN / FAIL for each.
</Step>
<Step>
### Produce the report

```
# Google Play SDK Compliance — YYYY-MM-DD

Project: <name>
SDK read from: <file path>
  compileSdk = __
  minSdk     = __
  targetSdk  = __

Requirements fetched from:
  - <url1> (fetched / FETCH FAILED)
  - <url2> (fetched / FETCH FAILED)

## SDK Level Requirements

| Check | Threshold | Project value | Result | Source |
|-------|-----------|---------------|--------|--------|
| targetSdk (new apps)     | >= __ | __ | PASS/WARN/FAIL | <url> |
| targetSdk (app updates)  | >= __ | __ | PASS/WARN/FAIL | <url> |
| compileSdk >= targetSdk  | >= __ | __ | PASS/WARN/FAIL | Google doc |
| compileSdk (recommended) | == __ | __ | PASS/WARN | Google doc |
| Upcoming deadline (if any) | >= __ by <date> | __ | PASS/WARN | <url> |

## minSdk Checks

| Check | Threshold | Project value | Result | Notes |
|-------|-----------|---------------|--------|-------|

## Manifest & Build Checks

| Check | Expected | Found | Result |
|-------|----------|-------|--------|

## Verdict

COMPLIANT | NON-COMPLIANT | COMPLIANT WITH WARNINGS

## Actions required

(list only FAIL and WARN items, with the exact file and value to change)
```
</Step>
</Steps>

## Rules

- Always fetch live data first. Never skip Step 2.
- Never hardcode API level thresholds in this skill -- they belong on Google's pages.
- If a fetch fails, label the fallback value explicitly as unverified training data.
- Use only values read directly from project files -- do not assume defaults.
- If a project value cannot be resolved, mark it UNRESOLVED and name the file needed.
- Do not edit any project file -- report only.
- If the project targets multiple form factors, check each build.gradle separately
  and note differing requirements per form factor.
