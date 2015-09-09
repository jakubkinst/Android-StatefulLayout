package cz.kinst.jakub.sample.statefullayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import cz.kinst.jakub.view.StatefulLayout;


public class MainActivity extends AppCompatActivity {

	StatefulLayout mStatefulLayout;


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


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mStatefulLayout = (StatefulLayout) findViewById(R.id.stateful);
		Log.i("STATE", mStatefulLayout.getState().name());

	}

}
