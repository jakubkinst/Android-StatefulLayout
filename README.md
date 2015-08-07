# StatefulView

## Installation
### Gradle/Maven
[![Release](https://img.shields.io/github/release/jakubkinst/Android-StatefulView.svg?label=JitPack)](https://jitpack.io/#jakubkinst/Android-StatefulView)
## Usage

### Example
```xml
<com.strv.view.StatefulView
		android:id="@+id/stateful_view"
		android:layout_width="match_parent"
		android:layout_height="match_parent">

		<!--Your Content Here-->
		
	</com.strv.view.StatefulView>
```
### Optional Attributes
- `app:offlineLayout` Custom layout to show when in OFFLINE state
- `app:emptyLayout` Custom layout to show when in EMPTY state
- `app:progressLayout` Custom layout to show when in PROGRESS state
- `app:stateTextAppearance` When not using custom layouts, this is the way to style the TextViews in OFFLINE and EMPTY states.

### API
- `showContent()`
- `showProgress()`
- `showEmpty()`
- `showOffline()`
- `getViewState()` Returns current view state (CONTENT/OFFLINE/EMPTY/PROGRESS)
- `setEmptyText(String text)` If using default layouts, this will set the text displayed in the EMPTY state
- `setOfflineText(String text)` If using default layouts, this will set the text displayed in the OFFLINE state
