package cz.kinst.jakub.sample.statefullayout;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cz.kinst.jakub.sample.statefullayout.databinding.ActivityDataBindingBinding;
import cz.kinst.jakub.sample.statefullayout.utility.DummyContentLoader;
import cz.kinst.jakub.sample.statefullayout.utility.NetworkUtility;
import cz.kinst.jakub.view.StatefulLayout;


public class DataBindingActivity extends AppCompatActivity {

	private ActivityDataBindingBinding mBinding;


	public static Intent newIntent(Context context) {
		Intent intent = new Intent(context, DataBindingActivity.class);
		return intent;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
		if(!NetworkUtility.isOnline(this))
			mBinding.setState(StatefulLayout.State.OFFLINE);
		else {
			// load content
			mBinding.setState(StatefulLayout.State.PROGRESS);
			DummyContentLoader.loadDummyContent(new DummyContentLoader.OnDummyContentLoaded() {
				@Override
				public void onDummyContentLoaded(String content) {
					mBinding.setContent(content);
					mBinding.setState(StatefulLayout.State.CONTENT);
				}
			});
		}
	}
}
