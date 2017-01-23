package cz.kinst.jakub.sample.statefullayout;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cz.kinst.jakub.sample.statefullayout.databinding.ActivityDataBindingControllerBinding;
import cz.kinst.jakub.sample.statefullayout.databinding.CustomGenericBinding;
import cz.kinst.jakub.view.StateController;


public class DataBindingControllerActivity extends AppCompatActivity {

	public static final String STATE_1 = "state_1";
	public static final String STATE_2 = "state_2";
	public static final String STATE_3 = "state_3";
	private StateController mStateController;


	public static Intent newIntent(Context context) {
		return new Intent(context, DataBindingControllerActivity.class);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityDataBindingControllerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding_controller);

		CustomGenericBinding state1 = CustomGenericBinding.inflate(getLayoutInflater());
		state1.setContent("1");
		CustomGenericBinding state2 = CustomGenericBinding.inflate(getLayoutInflater());
		state2.setContent("2");
		CustomGenericBinding state3 = CustomGenericBinding.inflate(getLayoutInflater());
		state3.setContent("3");

		mStateController = StateController.create()
				.withState(STATE_1, state1.getRoot())
				.withState(STATE_2, state2.getRoot())
				.withState(STATE_3, state3.getRoot())
				.build();
		binding.setStateController(mStateController);

		mStateController.setState(STATE_2);
	}
}
