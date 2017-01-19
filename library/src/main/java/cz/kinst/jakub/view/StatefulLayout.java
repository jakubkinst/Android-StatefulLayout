package cz.kinst.jakub.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


public class StatefulLayout extends BaseStatefulLayout {

	private String mInitialState;
	private int mTextAppearance;
	private int mOfflineViewResource, mEmptyViewResource, mProgressViewResource;
	private int mCustomEmptyDrawableId, mCustomOfflineDrawableId;
	private String mCustomEmptyText, mCustomOfflineText;
	private TextView mDefaultEmptyText, mDefaultOfflineText;


	public class State extends BaseStatefulLayout.State {
		public static final String PROGRESS = "progress";
		public static final String OFFLINE = "offline";
		public static final String EMPTY = "empty";
	}


	public StatefulLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SflStatefulLayout);
		mTextAppearance = a.getResourceId(R.styleable.SflStatefulLayout_stateTextAppearance, R.style.sfl_TextAppearanceStateDefault);

		mOfflineViewResource = a.getResourceId(R.styleable.SflStatefulLayout_offlineLayout, R.layout.default_placeholder_offline);
		mEmptyViewResource = a.getResourceId(R.styleable.SflStatefulLayout_emptyLayout, R.layout.default_placeholder_empty);
		mProgressViewResource = a.getResourceId(R.styleable.SflStatefulLayout_progressLayout, R.layout.default_placeholder_progress);


		// get custom texts if set
		if(a.hasValue(R.styleable.SflStatefulLayout_emptyText))
			mCustomEmptyText = a.getString(R.styleable.SflStatefulLayout_emptyText);
		if(a.hasValue(R.styleable.SflStatefulLayout_offlineText))
			mCustomOfflineText = a.getString(R.styleable.SflStatefulLayout_offlineText);

		// get initial state if set
		if(a.hasValue(R.styleable.SflStatefulLayout_state)) {
			mInitialState = a.getString(R.styleable.SflStatefulLayout_state);
		}
		if(mInitialState == null)
			mInitialState = State.CONTENT;

		if(a.hasValue(R.styleable.SflStatefulLayout_offlineImageDrawable)) {
			mCustomOfflineDrawableId = a.getResourceId(R.styleable.SflStatefulLayout_offlineImageDrawable, 0);
		}

		if(a.hasValue(R.styleable.SflStatefulLayout_emptyImageDrawable)) {
			mCustomEmptyDrawableId = a.getResourceId(R.styleable.SflStatefulLayout_emptyImageDrawable, 0);
		}

		a.recycle();
	}


	@Override
	protected void initialize() {
		super.initialize();

		setStateView(State.OFFLINE, LayoutInflater.from(getContext()).inflate(mOfflineViewResource, null));
		setStateView(State.EMPTY, LayoutInflater.from(getContext()).inflate(mEmptyViewResource, null));
		setStateView(State.PROGRESS, LayoutInflater.from(getContext()).inflate(mProgressViewResource, null));

		// set custom empty text
		mDefaultEmptyText = ((TextView) getEmptyView().findViewById(R.id.state_text));
		if(mDefaultEmptyText != null) {
			mDefaultEmptyText.setTextAppearance(getContext(), mTextAppearance);
			if(mCustomEmptyText != null)
				setEmptyText(mCustomEmptyText);
		}

		// set custom offline text
		mDefaultOfflineText = ((TextView) getOfflineView().findViewById(R.id.state_text));
		if(mDefaultOfflineText != null) {
			mDefaultOfflineText.setTextAppearance(getContext(), mTextAppearance);
			if(mCustomOfflineText != null)
				setOfflineText(mCustomOfflineText);
		}

		// set custom drawables
		if(mCustomOfflineDrawableId != 0)
			setOfflineImageResource(mCustomOfflineDrawableId);
		if(mCustomEmptyDrawableId != 0)
			setEmptyImageResource(mCustomEmptyDrawableId);

		if(mInitialState != null)
			setState(mInitialState);
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


	public void setEmptyText(@StringRes int resourceId) {
		setEmptyText(getResources().getString(resourceId));
	}


	public void setEmptyText(CharSequence emptyText) {
		if(mDefaultEmptyText != null)
			mDefaultEmptyText.setText(emptyText);
	}


	public void setEmptyImageDrawable(Drawable drawable) {
		TintableImageView image = ((TintableImageView) getEmptyView().findViewById(R.id.state_image));
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
		if(mDefaultOfflineText != null)
			mDefaultOfflineText.setText(offlineText);
	}


	public void setOfflineImageDrawable(Drawable drawable) {
		TintableImageView image = ((TintableImageView) getOfflineView().findViewById(R.id.state_image));
		if(image != null) {
			image.setVisibility(drawable != null ? VISIBLE : GONE);
			image.setImageDrawable(drawable);
		}
	}


	public void setOfflineImageResource(@DrawableRes int resourceId) {
		setOfflineImageDrawable(getResources().getDrawable(resourceId));
	}


	public void setTextColor(@ColorInt int color) {
		((TintableImageView) findViewById(R.id.state_image)).setTintColor(color);
		((TintableImageView) findViewById(R.id.state_image)).setTintColor(color);
		((TextView) findViewById(R.id.state_text)).setTextColor(color);
		((TextView) findViewById(R.id.state_text)).setTextColor(color);
	}


	public View getProgressView() {
		return getStateView(State.PROGRESS);
	}


	public View getOfflineView() {
		return getStateView(State.OFFLINE);
	}


	public View getEmptyView() {
		return getStateView(State.EMPTY);
	}


	public void setOfflineView(View offlineView) {
		setStateView(State.OFFLINE, offlineView);
	}


	public void setEmptyView(View emptyView) {
		setStateView(State.EMPTY, emptyView);
	}


	public void setProgressView(View progressView) {
		setStateView(State.PROGRESS, progressView);
	}


}
