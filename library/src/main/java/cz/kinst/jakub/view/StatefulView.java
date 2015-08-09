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
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * Created by jakubkinst on 05/08/15.
 */
public class StatefulView extends FrameLayout {
	private int mTextAppearance;
	private State mInitialState;
	private String mCustomEmptyText;
	private String mCustomOfflineText;
	private View mOfflineView, mEmptyView, mProgressView;
	private State mState = null;
	private View mContent;
	private FrameLayout mContainerProgress, mContainerOffline, mContainerEmpty;
	private TextView mDefaultEmptyText, mDefaultOfflineText;


	public StatefulView(Context context) {
		this(context, null);
	}


	public StatefulView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}


	public StatefulView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StatefulView);
		mTextAppearance = a.getResourceId(R.styleable.StatefulView_stateTextAppearance, R.style.TextAppearanceStateDefault);
		mOfflineView = LayoutInflater.from(context).inflate(a.getResourceId(R.styleable.StatefulView_offlineLayout, R.layout.default_placeholder_offline), null);
		mEmptyView = LayoutInflater.from(context).inflate(a.getResourceId(R.styleable.StatefulView_emptyLayout, R.layout.default_placeholder_empty), null);
		mProgressView = LayoutInflater.from(context).inflate(a.getResourceId(R.styleable.StatefulView_progressLayout, R.layout.default_placeholder_progress), null);

		// get custom texts if set
		if(a.hasValue(R.styleable.StatefulView_emptyText))
			mCustomEmptyText = a.getString(R.styleable.StatefulView_emptyText);
		if(a.hasValue(R.styleable.StatefulView_offlineText))
			mCustomOfflineText = a.getString(R.styleable.StatefulView_offlineText);

		// get initial state if set
		if(a.hasValue(R.styleable.StatefulView_state)) { //TODO: maybe set initial state to content if not set
			mInitialState = State.fromId(a.getInt(R.styleable.StatefulView_state, State.CONTENT.id));
		}


	}


	public void setEmptyText(@StringRes int resourceId) {
		setEmptyText(getResources().getString(resourceId));
	}


	public void setEmptyText(CharSequence emptyText) {
		if(mDefaultEmptyText != null)
			mDefaultEmptyText.setText(emptyText);
	}


	public void setEmptyImageDrawable(Drawable drawable) {
		TintableImageView image = ((TintableImageView) mEmptyView.findViewById(R.id.state_image));
		image.setVisibility(drawable != null ? VISIBLE : GONE);
		image.setImageDrawable(drawable);
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
		TintableImageView image = ((TintableImageView) mOfflineView.findViewById(R.id.state_image));
		image.setVisibility(drawable != null ? VISIBLE : GONE);
		image.setImageDrawable(drawable);
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


	public State getState() {
		return mState;
	}


	public void setState(State state) {
		mState = state;
		if(mContent != null)
			mContent.setVisibility(state == State.CONTENT ? View.VISIBLE : View.GONE);
		if(mContainerProgress != null)
			mContainerProgress.setVisibility(state == State.PROGRESS ? View.VISIBLE : View.GONE);
		if(mContainerOffline != null)
			mContainerOffline.setVisibility(state == State.OFFLINE ? View.VISIBLE : View.GONE);
		if(mContainerEmpty != null)
			mContainerEmpty.setVisibility(state == State.EMPTY ? View.VISIBLE : View.GONE);
	}


	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		initialize();


	}


	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}


	private void initialize() {
		mContent = getChildAt(0);
		addView(LayoutInflater.from(getContext()).inflate(R.layout.view_stateful, this, false));
		mContainerProgress = (FrameLayout) findViewById(R.id.container_progress);
		mContainerProgress.addView(mProgressView);
		mContainerOffline = (FrameLayout) findViewById(R.id.container_offline);
		mContainerOffline.addView(mOfflineView);
		mContainerEmpty = (FrameLayout) findViewById(R.id.container_empty);
		mContainerEmpty.addView(mEmptyView);

		mDefaultEmptyText = ((TextView) mEmptyView.findViewById(R.id.state_text));
		if(mDefaultEmptyText != null) {
			mDefaultEmptyText.setTextAppearance(getContext(), mTextAppearance);
			if(mCustomEmptyText != null)
				setEmptyText(mCustomEmptyText);
		}

		mDefaultOfflineText = ((TextView) mOfflineView.findViewById(R.id.state_text));
		if(mDefaultOfflineText != null) {
			mDefaultOfflineText.setTextAppearance(getContext(), mTextAppearance);
			if(mCustomOfflineText != null)
				setEmptyText(mCustomOfflineText);
		}

		if(mInitialState != null)
			setState(mInitialState);
	}


	public enum State {
		CONTENT(0), PROGRESS(1), OFFLINE(2), EMPTY(3);
		int id;


		State(int id) {
			this.id = id;
		}


		static State fromId(int id) {
			for(State f : values()) {
				if(f.id == id) return f;
			}
			throw new IllegalArgumentException();
		}
	}
}