# StatefulView

## Installation
### Gradle/Maven
[![Release](https://img.shields.io/github/release/jakubkinst/Android-StatefulView.svg?label=JitPack)](https://jitpack.io/#jakubkinst/Android-StatefulView/0.8.1)
## Usage

### Example
```xml
<cz.kinst.jakub.view.StatefulView
	android:id="@+id/stateful_view"
	android:layout_width="match_parent"
	android:layout_height="match_parent">
	
	<!--Your Content Here-->
		
</cz.kinst.jakub.view.StatefulView>
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
