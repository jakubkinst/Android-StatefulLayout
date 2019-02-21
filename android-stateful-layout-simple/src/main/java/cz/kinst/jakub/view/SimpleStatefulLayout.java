package cz.kinst.jakub.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.transition.TransitionManager;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cz.kinst.jakub.view.simple.R;


public class SimpleStatefulLayout extends StatefulLayout {

	private String mInitialState = State.CONTENT;
	private boolean mTransitionsEnabled = true;


	public SimpleStatefulLayout(Context context) {
		super(context);
		init(context, null);
	}


	public SimpleStatefulLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}


	public SimpleStatefulLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}


	@Override
	protected void onSetupContentState() {
		super.onSetupContentState();
		if(mInitialState != null)
			setState(mInitialState);
	}


	@Override
	public void setState(String state) {
		if(isTransitionsEnabled())
			TransitionManager.beginDelayedTransition(this);
		super.setState(state);
	}


	public void showContent() {
		setState(State.CONTENT);
	}


	public void showProgress() {
		setState(State.PROGRESS);
	}


	public void showOffline() {
		setState(State.OFFLINE);
	}


	public void showEmpty() {
		setState(State.EMPTY);
	}


	public void setTextAppearance(int textAppearance) {
		TextView offlineTextView = ((TextView) getOfflineView().findViewById(R.id.state_text));
		if(offlineTextView != null)
			offlineTextView.setTextAppearance(getContext(), textAppearance);
		TextView emptyTextView = ((TextView) getEmptyView().findViewById(R.id.state_text));
		if(emptyTextView != null)
			emptyTextView.setTextAppearance(getContext(), textAppearance);
	}


	public void setEmptyText(@StringRes int resourceId) {
		setEmptyText(getResources().getString(resourceId));
	}


	public void setEmptyText(CharSequence emptyText) {
		TextView emptyTextView = ((TextView) getEmptyView().findViewById(R.id.state_text));
		if(emptyTextView != null)
			emptyTextView.setText(emptyText);
	}


	public void setEmptyImageDrawable(Drawable drawable) {
		ImageView image = ((ImageView) getEmptyView().findViewById(R.id.state_image));
		if(image != null) {
			image.setVisibility(drawable != null ? VISIBLE : GONE);
			image.setImageDrawable(drawable);
		}
	}


	public void setEmptyImageResource(@DrawableRes int resourceId) {
		setEmptyImageDrawable(getResources().getDrawable(resourceId));
	}


	public void setOfflineText(@StringRes int resourceId) {
		setOfflineText(getResources().getString(resourceId));
	}


	public void setOfflineText(CharSequence offlineText) {
		TextView offlineTextView = ((TextView) getOfflineView().findViewById(R.id.state_text));
		if(offlineTextView != null)
			offlineTextView.setText(offlineText);
	}


	public void setOfflineImageDrawable(Drawable drawable) {
		ImageView image = ((ImageView) getOfflineView().findViewById(R.id.state_image));
		if(image != null) {
			image.setVisibility(drawable != null ? VISIBLE : GONE);
			image.setImageDrawable(drawable);
		}
	}


	public void setOfflineImageResource(@DrawableRes int resourceId) {
		setOfflineImageDrawable(getResources().getDrawable(resourceId));
	}


	public void setTextColor(@ColorInt int color) {
		((AppCompatImageView) getEmptyView().findViewById(R.id.state_image)).setSupportBackgroundTintList(ColorStateList.valueOf(color));
		((AppCompatImageView) getOfflineView().findViewById(R.id.state_image)).setSupportBackgroundTintList(ColorStateList.valueOf(color));
		((TextView) getEmptyView().findViewById(R.id.state_text)).setTextColor(color);
		((TextView) getOfflineView().findViewById(R.id.state_text)).setTextColor(color);
	}


	public View getProgressView() {
		return getStateView(State.PROGRESS);
	}


	public void setProgressView(View progressView) {
		setStateView(State.PROGRESS, progressView);
	}


	public View getOfflineView() {
		return getStateView(State.OFFLINE);
	}


	public void setOfflineView(View offlineView) {
		setStateView(State.OFFLINE, offlineView);
	}


	public View getEmptyView() {
		return getStateView(State.EMPTY);
	}


	public void setEmptyView(View emptyView) {
		setStateView(State.EMPTY, emptyView);
	}


	public boolean isTransitionsEnabled() {
		return mTransitionsEnabled;
	}


	public void setTransitionsEnabled(boolean transitionsEnabled) {
		mTransitionsEnabled = transitionsEnabled;
	}


	public void setOfflineRetryOnClickListener(OnClickListener listener) {
		getOfflineView().findViewById(R.id.state_button).setOnClickListener(listener);
	}


	public void setOfflineRetryText(@StringRes int textResourceId) {
		setOfflineRetryText(getResources().getString(textResourceId));
	}


	public void setOfflineRetryText(CharSequence text) {
		Button button = ((Button) getOfflineView().findViewById(R.id.state_button));
		if(button != null)
			button.setText(text);
	}


	private void init(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SflStatefulLayout);

		int offlineLayoutResource = a.getResourceId(R.styleable.SflStatefulLayout_offlineLayout, R.layout.sfl_default_placeholder_offline);
		int emptyLayoutResource = a.getResourceId(R.styleable.SflStatefulLayout_emptyLayout, R.layout.sfl_default_placeholder_empty);
		int progressLayoutResource = a.getResourceId(R.styleable.SflStatefulLayout_progressLayout, R.layout.sfl_default_placeholder_progress);

		setStateView(State.OFFLINE, LayoutInflater.from(getContext()).inflate(offlineLayoutResource, null));
		setStateView(State.EMPTY, LayoutInflater.from(getContext()).inflate(emptyLayoutResource, null));
		setStateView(State.PROGRESS, LayoutInflater.from(getContext()).inflate(progressLayoutResource, null));

		int textAppearance = a.getResourceId(R.styleable.SflStatefulLayout_stateTextAppearance, R.style.sfl_TextAppearanceStateDefault);
		setTextAppearance(textAppearance);

		// setState custom empty text
		if(a.hasValue(R.styleable.SflStatefulLayout_emptyText)) {
			setEmptyText(a.getString(R.styleable.SflStatefulLayout_emptyText));
		}

		// setState custom offline text
		if(a.hasValue(R.styleable.SflStatefulLayout_offlineText)) {
			setOfflineText(a.getString(R.styleable.SflStatefulLayout_offlineText));
		}

		// setState custom offline retry text
		if(a.hasValue(R.styleable.SflStatefulLayout_offlineRetryText)) {
			setOfflineRetryText(a.getString(R.styleable.SflStatefulLayout_offlineRetryText));
		}

		if(a.hasValue(R.styleable.SflStatefulLayout_offlineImageDrawable)) {
			setOfflineImageResource(a.getResourceId(R.styleable.SflStatefulLayout_offlineImageDrawable, 0));
		}

		if(a.hasValue(R.styleable.SflStatefulLayout_emptyImageDrawable)) {
			setEmptyImageResource(a.getResourceId(R.styleable.SflStatefulLayout_emptyImageDrawable, 0));
		}

		// getState initial state if setState
		if(a.hasValue(R.styleable.SflStatefulLayout_state)) {
			mInitialState = a.getString(R.styleable.SflStatefulLayout_state);
		}

		a.recycle();
	}


	public class State extends StatefulLayout.State {
		// Note: CONTENT state is inherited from parent
		public static final String PROGRESS = "progress";
		public static final String OFFLINE = "offline";
		public static final String EMPTY = "empty";
	}

}
