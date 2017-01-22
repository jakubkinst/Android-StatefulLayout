package cz.kinst.jakub.sample.statefullayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import cz.kinst.jakub.view.SimpleStatefulLayout;


public class SimpleActivity extends AppCompatActivity {

	private static final String STATE_CUSTOM = "custom";
	SimpleStatefulLayout mStatefulLayout;


	public static Intent newIntent(Context context) {
		return new Intent(context, SimpleActivity.class);
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
		setContentView(R.layout.activity_simple);
		mStatefulLayout = (SimpleStatefulLayout) findViewById(R.id.stateful);
		mStatefulLayout.setOfflineRetryOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(SimpleActivity.this, "Retrying...", Toast.LENGTH_SHORT).show();
			}
		});
		mStatefulLayout.setStateView(STATE_CUSTOM, LayoutInflater.from(this).inflate(R.layout.custom_state, null));
	}
}
