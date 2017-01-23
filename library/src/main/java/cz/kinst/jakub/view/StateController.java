package cz.kinst.jakub.view;

import android.databinding.BindingAdapter;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.view.View;

import java.util.HashMap;
import java.util.Map;


public class StateController {
	private Map<String, View> mStateMap = new HashMap<>();
	private ObservableField<String> mState = new ObservableField<>(StatefulLayout.State.CONTENT);


	private StateController() {
	}


	@BindingAdapter("stateController")
	public static void setStateController(final StatefulLayout statefulLayout, StateController stateController) {
		statefulLayout.clearStates();
		for(String state : stateController.getStates().keySet()) {
			statefulLayout.setStateView(state, stateController.getStates().get(state));
		}
		stateController.registerListener(new StatefulLayout.OnStateChangeListener() {
			@Override
			public void onStateChange(String state) {
				statefulLayout.setState(state);
			}
		});
		statefulLayout.setState(stateController.getState());
	}


	public static Builder create() {
		return new Builder();
	}


	public Map<String, View> getStates() {
		return mStateMap;
	}


	public void setState(String newState) {
		mState.set(newState);
	}


	public String getState() {
		return mState.get();
	}


	private void registerListener(final StatefulLayout.OnStateChangeListener listener) {
		mState.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
			@Override
			public void onPropertyChanged(Observable observable, int i) {
				listener.onStateChange(mState.get());
			}
		});
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
