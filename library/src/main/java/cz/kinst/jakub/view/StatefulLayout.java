package cz.kinst.jakub.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * Created by Jakub Kinst (jakub@kinst.cz)
 */
public class StatefulLayout extends FrameLayout {

	public static final String SAVED_INSTANCE_STATE = "instanceState";
	private static final String SAVED_STATE = "stateful_layout_state";
	private int mCustomEmptyDrawableId = 0;
	private int mCustomOfflineDrawableId = 0;
	private int mTextAppearance;
	private State mInitialState;
	private String mCustomEmptyText;
	private String mCustomOfflineText;
	private View mOfflineView, mEmptyView, mProgressView;
	private State mState = null;
	private View mContent;
	private FrameLayout mContainerProgress, mContainerOffline, mContainerEmpty;
	private TextView mDefaultEmptyText, mDefaultOfflineText;
	private OnStateChangeListener mOnStateChangeListener;
	private boolean mInitialized;


	public enum State {
		CONTENT(0), PROGRESS(1), OFFLINE(2), EMPTY(3);
		int mValue;


		static State fromValue(int value) {
			State[] values = State.values();
			return values[value];
		}


		State(int value) {
			mValue = value;
		}


		public int getValue() {
			return mValue;
		}
	}


	public interface OnStateChangeListener {
		void onStateChange(View v, State state);
	}


	public StatefulLayout(Context context) {
		this(context, null);
	}


	public StatefulLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}


	public StatefulLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StatefulLayout);
		mTextAppearance = a.getResourceId(R.styleable.StatefulLayout_stateTextAppearance, R.style.sfl_TextAppearanceStateDefault);
		mOfflineView = LayoutInflater.from(context).inflate(a.getResourceId(R.styleable.StatefulLayout_offlineLayout, R.layout.default_placeholder_offline), null);
		mEmptyView = LayoutInflater.from(context).inflate(a.getResourceId(R.styleable.StatefulLayout_emptyLayout, R.layout.default_placeholder_empty), null);
		mProgressView = LayoutInflater.from(context).inflate(a.getResourceId(R.styleable.StatefulLayout_progressLayout, R.layout.default_placeholder_progress), null);

		// get custom texts if set
		if(a.hasValue(R.styleable.StatefulLayout_emptyText))
			mCustomEmptyText = a.getString(R.styleable.StatefulLayout_emptyText);
		if(a.hasValue(R.styleable.StatefulLayout_offlineText))
			mCustomOfflineText = a.getString(R.styleable.StatefulLayout_offlineText);

		// get initial state if set
		if(a.hasValue(R.styleable.StatefulLayout_state)) {
			mInitialState = State.fromValue(a.getInt(R.styleable.StatefulLayout_state, State.CONTENT.getValue()));
		}

		if(a.hasValue(R.styleable.StatefulLayout_offlineImageDrawable)) {
			mCustomOfflineDrawableId = a.getResourceId(R.styleable.StatefulLayout_offlineImageDrawable, 0);
		}

		if(a.hasValue(R.styleable.StatefulLayout_emptyImageDrawable)) {
			mCustomEmptyDrawableId = a.getResourceId(R.styleable.StatefulLayout_emptyImageDrawable, 0);
		}

		a.recycle();
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
		TintableImageView image = ((TintableImageView) mOfflineView.findViewById(R.id.state_image));
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

		if(mOnStateChangeListener != null) mOnStateChangeListener.onStateChange(this, state);
	}


	public void setOnStateChangeListener(OnStateChangeListener listener) {
		mOnStateChangeListener = listener;
	}


	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if(!mInitialized)
			initialize();
	}


	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(SAVED_INSTANCE_STATE, super.onSaveInstanceState());
		saveInstanceState(bundle);
		return bundle;
	}


	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if(state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
			restoreInstanceState(bundle);
			state = bundle.getParcelable(SAVED_INSTANCE_STATE);
		}
		super.onRestoreInstanceState(state);
	}


	public void saveInstanceState(Bundle outState) {
		if(mState != null)
			outState.putInt(SAVED_STATE, mState.getValue());
	}


	public State restoreInstanceState(Bundle savedInstanceState) {
		State state = State.fromValue(savedInstanceState.getInt(SAVED_STATE));
		setState(state);
		return state;
	}


	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}


	private void initialize() {

		// build layout structure
		mContent = getChildAt(0);
		addView(LayoutInflater.from(getContext()).inflate(R.layout.view_stateful, this, false));
		mContainerProgress = (FrameLayout) findViewById(R.id.container_progress);
		mContainerProgress.addView(mProgressView);
		mContainerOffline = (FrameLayout) findViewById(R.id.container_offline);
		mContainerOffline.addView(mOfflineView);
		mContainerEmpty = (FrameLayout) findViewById(R.id.container_empty);
		mContainerEmpty.addView(mEmptyView);

		// set custom empty text
		mDefaultEmptyText = ((TextView) mEmptyView.findViewById(R.id.state_text));
		if(mDefaultEmptyText != null) {
			mDefaultEmptyText.setTextAppearance(getContext(), mTextAppearance);
			if(mCustomEmptyText != null)
				setEmptyText(mCustomEmptyText);
		}

		// set custom offline text
		mDefaultOfflineText = ((TextView) mOfflineView.findViewById(R.id.state_text));
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
		mInitialized = true;
	}


	public View getProgressView() {
		return mProgressView;
	}


	public View getOfflineView() {
		return mOfflineView;
	}


	public View getEmptyView() {
		return mEmptyView;
	}


	public void setOfflineView(View offlineView) {
		mOfflineView = offlineView;
		if (mInitialized) {
			mContainerOffline.removeAllViews();
			mContainerOffline.addView(mOfflineView);
		}
	}


	public void setEmptyView(View emptyView) {
		mEmptyView = emptyView;
		if (mInitialized) {
			mContainerEmpty.removeAllViews();
			mContainerEmpty.addView(mEmptyView);
		}
	}


	public void setProgressView(View progressView) {
		mProgressView = progressView;
		if (mInitialized) {
			mContainerProgress.removeAllViews();
			mContainerProgress.addView(mProgressView);
		}
	}
}
