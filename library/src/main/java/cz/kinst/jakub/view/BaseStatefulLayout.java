package cz.kinst.jakub.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Jakub Kinst (jakub@kinst.cz)
 */
public abstract class BaseStatefulLayout extends FrameLayout {

	public static final String SAVED_INSTANCE_STATE = "instanceState";
	private static final String SAVED_STATE = "stateful_layout_state";

	private Map<String, View> mViews = new HashMap<>();

	private String mState = null;

	private Map<String, FrameLayout> mContainers = new HashMap<>();

	private OnStateChangeListener mOnStateChangeListener;
	private boolean mInitialized;


	public class State {
		public static final String CONTENT = "content";
	}


	public interface OnStateChangeListener {
		void onStateChange(View v, String state);
	}


	public BaseStatefulLayout(Context context) {
		this(context, null);
	}


	public BaseStatefulLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}


	public BaseStatefulLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	public void setStateView(String state, View view) {
		mViews.put(state, view);
		FrameLayout container = mContainers.get(state);
		if(container == null) {
			container = createContainer();
			mContainers.put(state, container);
			addView(container);
		}
		if(view.getParent() == null) {
			container.removeAllViews();
			container.addView(view);
		}
	}


	public String getState() {
		return mState;
	}


	public void setState(String state) {
		mState = state;
		for(String s : mContainers.keySet()) {
			FrameLayout container = mContainers.get(s);
			container.setVisibility(s.equals(state) ? View.VISIBLE : View.GONE);
		}
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
			outState.putString(SAVED_STATE, mState);
	}


	public String restoreInstanceState(Bundle savedInstanceState) {
		String state = savedInstanceState.getString(SAVED_STATE);
		setState(state);
		return state;
	}


	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}


	protected void initialize() {
		View contentView = getChildAt(0);
		removeAllViews();
		setStateView(State.CONTENT, contentView);
		mInitialized = true;
	}


	public View getStateView(String state) {
		return mViews.get(state);
	}


	private FrameLayout createContainer() {
		FrameLayout container = new FrameLayout(getContext());
		container.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		container.setVisibility(GONE);
		return container;
	}

}
