package cz.kinst.jakub.sample.statefullayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import cz.kinst.jakub.view.StatefulLayout;


public class CustomizationsActivity extends AppCompatActivity {

	private static final String STATE_CUSTOM = "custom";
	StatefulLayout mStatefulLayout;


	public static Intent newIntent(Context context) {
		Intent intent = new Intent(context, CustomizationsActivity.class);
		return intent;
	}


	public void showContent(View view) {
		mStatefulLayout.showContent();
	}


	public void showOffline(View view) {
		mStatefulLayout.showOffline();
	}


	public void showProgress(View view) {
		mStatefulLayout.showProgress();
	}


	public void showEmpty(View view) {
		mStatefulLayout.showEmpty();
	}


	public void showCustom(View view) {
		mStatefulLayout.setState(STATE_CUSTOM);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customizations);
		mStatefulLayout = (StatefulLayout) findViewById(R.id.stateful);
		mStatefulLayout.setStateView(STATE_CUSTOM, LayoutInflater.from(this).inflate(R.layout.custom_state, null));
	}
}
