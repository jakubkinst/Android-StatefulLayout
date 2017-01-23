package cz.kinst.jakub.view;

import android.databinding.BindingAdapter;
import android.view.View;

import java.util.HashMap;
import java.util.Map;


public class StateController {
	private Map<String, View> mStateMap = new HashMap<>();
	private String mState = StatefulLayout.State.CONTENT;
	private StatefulLayout.OnStateChangeListener mListener;


	private StateController() {
	}


	@BindingAdapter("stateController")
	public static void setStateController(final StatefulLayout statefulLayout, StateController stateController) {
		statefulLayout.setStateController(stateController);
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


	void setOnStateChangeListener(final StatefulLayout.OnStateChangeListener listener) {
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
