package cz.kinst.jakub.sample.statefullayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import cz.kinst.jakub.sample.statefullayout.utility.DummyContentLoader;
import cz.kinst.jakub.sample.statefullayout.utility.NetworkUtility;
import cz.kinst.jakub.view.SimpleStatefulLayout;


public class LoadingActivity extends AppCompatActivity {

	SimpleStatefulLayout mStatefulLayout;


	public static Intent newIntent(Context context) {
		Intent intent = new Intent(context, LoadingActivity.class);
		return intent;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		mStatefulLayout = (SimpleStatefulLayout) findViewById(R.id.stateful);
		mStatefulLayout.setOfflineRetryOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				reload();
			}
		});

		reload();
	}


	private void reload() {
		if(NetworkUtility.isOnline(this)) {
			mStatefulLayout.showProgress();
			DummyContentLoader.loadDummyContent(new DummyContentLoader.OnDummyContentLoaded() {
				@Override
				public void onDummyContentLoaded(final String data) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							onDataLoaded(data);
						}
					});
				}
			});
		} else {
			mStatefulLayout.showOffline();
		}
	}


	/**
	 * Data loaded
	 */
	private void onDataLoaded(String content) {
		((TextView) findViewById(R.id.content_text)).setText(content);
		mStatefulLayout.showContent();
	}

}
