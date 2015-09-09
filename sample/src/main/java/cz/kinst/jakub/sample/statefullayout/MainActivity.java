package cz.kinst.jakub.sample.statefullayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cz.kinst.jakub.view.StatefulLayout;


public class MainActivity extends AppCompatActivity {

	StatefulLayout mStatefulView;


	public void showContent(View view) {
		mStatefulView.showContent();
	}


	public void showOffline(View view) {
		mStatefulView.showOffline();
	}


	public void showProgress(View view) {
		mStatefulView.showProgress();
	}


	public void showEmpty(View view) {
		mStatefulView.showEmpty();
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mStatefulView = (StatefulLayout) findViewById(R.id.stateful);
	}

}
