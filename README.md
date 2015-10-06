# StatefulLayout
![alt text](screen.gif)

## Installation
### Gradle/Maven
    compile 'cz.kinst.jakub:stateful-layout:1.1.1'
## Usage

### Example
```xml
<cz.kinst.jakub.view.StatefulLayout
	android:id="@+id/stateful_view"
	android:layout_width="match_parent"
	android:layout_height="match_parent">
	
	<!--Your Content Here-->
		
</cz.kinst.jakub.view.StatefulLayout>
```
### Optional Attributes
- `app:offlineText` Custom text to show when in OFFLINE state
- `app:emptyText` Custom text to show when in OFFLINE state
- `app:offlineImageDrawable` Custom image to show above the offline state text (if not using custom layout)
- `app:emptyImageDrawable` Custom image to show above the empty state text (if not using custom layout)
- `app:offlineLayout` Custom layout to show when in OFFLINE state
- `app:emptyLayout` Custom layout to show when in EMPTY state
- `app:progressLayout` Custom layout to show when in PROGRESS state
- `app:state` Initial state of the view (`content`, `progress`, `offline`, `empty`)
- `app:stateTextAppearance` When not using custom layouts, this is the way to style the TextViews in OFFLINE and EMPTY states.

### API
- `showContent()`
- `showProgress()`
- `showEmpty()`
- `showOffline()`
- `getState()` Returns current view state (CONTENT/OFFLINE/EMPTY/PROGRESS)
- `setEmptyText(String text)` If using default layouts, this will set the text displayed in the EMPTY state
- `setOfflineText(String text)` If using default layouts, this will set the text displayed in the OFFLINE state
- `setEmptyImageDrawable(Drawable drawable)` Sets custom image shown above empty text when not using custom layout
- `setEmptyImageResource(int resourceId)` Sets custom image shown above empty text when not using custom layout
- `setOfflineImageDrawable(Drawable drawable)` Sets custom image shown above offline text when not using custom layout
- `setOfflineImageResource(int resourceId)` Sets custom image shown above offline text when not using custom layout
- `setOnStateChangeListener(OnStateChangeListener listener)` Sets a listener on state change event

## License
    Copyright 2015 Jakub Kinst (jakub@kinst.cz)
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
      http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

