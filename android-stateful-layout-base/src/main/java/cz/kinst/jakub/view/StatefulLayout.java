package cz.kinst.jakub.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


/**
 * Created by Jakub Kinst (jakub@kinst.cz)
 */
public class StatefulLayout extends FrameLayout {

	public static final String SAVED_INSTANCE_STATE = "instanceState";
	private static final String SAVED_STATE = "stateful_layout_state";

	private Map<String, View> mStateViews = new HashMap<>();
	private String mState;
	private OnStateChangeListener mOnStateChangeListener;
	private boolean mInitialized;
	private boolean mDirtyFlag = false;


	public interface OnStateChangeListener {
		void onStateChange(String state);
	}


	public StatefulLayout(Context context) {
		super(context);
	}


	public StatefulLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	public StatefulLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if(!mInitialized)
			onSetupContentState();
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
			if (mState == null)
				restoreInstanceState(bundle);
			state = bundle.getParcelable(SAVED_INSTANCE_STATE);
		}
		super.onRestoreInstanceState(state);
	}


	public void setStateView(String state, View view) {
		if(mStateViews.containsKey(state)) {
			removeView(mStateViews.get(state));
		}
		mStateViews.put(state, view);
		if(view.getParent() == null) {
			addView(view);
		}
		view.setVisibility(GONE);
		mDirtyFlag = true;
	}


	@NonNull
	public String getState() {
		return mState;
	}


	public void setState(String state) {
		if(getStateView(state) == null) {
			throw new IllegalStateException(String.format("Cannot switch to state \"%s\". This state was not defined or the view for this state is null.", state));
		}

		if(mState != null && mState.equals(state) && !mDirtyFlag)
			return;

		View previousView = mStateViews.get(mState);
		if (previousView != null) {
			previousView.setVisibility(View.GONE);
		}
		mState = state;
		mStateViews.get(state).setVisibility(View.VISIBLE);

		if(mOnStateChangeListener != null)
			mOnStateChangeListener.onStateChange(state);

		mDirtyFlag = false;
	}


	public void setOnStateChangeListener(OnStateChangeListener listener) {
		mOnStateChangeListener = listener;
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


	@Nullable
	public View getStateView(String state) {
		return mStateViews.get(state);
	}


	public void clearStates() {
		for(String state : new HashSet<>(mStateViews.keySet())) {
			View view = mStateViews.get(state);
			if(!state.equals(State.CONTENT)) {
				removeView(view);
				mStateViews.remove(state);
			}
		}
	}


	public void setStateController(StateController stateController) {
		clearStates();
		for(String state : stateController.getStates().keySet()) {
			setStateView(state, stateController.getStates().get(state));
		}
		stateController.setOnStateChangeListener(new StatefulLayout.OnStateChangeListener() {
			@Override
			public void onStateChange(String state) {
				setState(state);
			}
		});
		setState(stateController.getState());
	}


	@CallSuper
	protected void onSetupContentState() {
		if(getChildCount() != 1 + mStateViews.size()) {
			throw new IllegalStateException("Invalid child count. StatefulLayout must have exactly one child.");
		}
		View contentView = getChildAt(mStateViews.size());
		removeView(contentView);
		setStateView(State.CONTENT, contentView);
		setState(State.CONTENT);
		mInitialized = true;
	}


	public static class State {
		public static final String CONTENT = "content";
	}


	public static class StateController {
		private Map<String, View> mStateMap = new HashMap<>();
		private String mState = State.CONTENT;
		private OnStateChangeListener mListener;


		private StateController() {
		}


		public static Builder create() {
			return new Builder();
		}


		public Map<String, View> getStates() {
			return mStateMap;
		}


		public String getState() {
			return mState;
		}


		public void setState(String newState) {
			mState = newState;
			if(mListener != null)
				mListener.onStateChange(newState);
		}


		void setOnStateChangeListener(final OnStateChangeListener listener) {
			mListener = listener;
		}


		public static class Builder {
			StateController mStateController = new StateController();


			public Builder withState(String state, View stateView) {
				mStateController.mStateMap.put(state, stateView);
				return this;
			}


			public StateController build() {
				return mStateController;
			}

		}
	}
}
