package cz.kinst.jakub.sample.statefullayout;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import cz.kinst.jakub.sample.statefullayout.databinding.ActivityDataBindingBinding;
import cz.kinst.jakub.sample.statefullayout.databinding.CustomEmptyBinding;
import cz.kinst.jakub.sample.statefullayout.utility.DummyContentLoader;
import cz.kinst.jakub.sample.statefullayout.utility.NetworkUtility;
import cz.kinst.jakub.view.SimpleStatefulLayout;
import cz.kinst.jakub.view.StatefulLayout;


public class DataBindingActivity extends AppCompatActivity {

	private ActivityDataBindingBinding mBinding;


	public static Intent newIntent(Context context) {
		return new Intent(context, DataBindingActivity.class);
	}


	public void loadData() {
		mBinding.setState(SimpleStatefulLayout.State.PROGRESS);
		DummyContentLoader.loadDummyContent(new DummyContentLoader.OnDummyContentLoaded() {
			@Override
			public void onDummyContentLoaded(String content) {
				mBinding.setContent(content);
				mBinding.setState(StatefulLayout.State.CONTENT);
			}
		});
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);

		CustomEmptyBinding emptyView = CustomEmptyBinding.inflate(LayoutInflater.from(this));
		emptyView.setOnReloadListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadData();
			}
		});
		mBinding.stateful.setEmptyView(emptyView.getRoot());

		if(!NetworkUtility.isOnline(this))
			mBinding.setState(SimpleStatefulLayout.State.OFFLINE);
		else {
			mBinding.setState(SimpleStatefulLayout.State.EMPTY);
		}
	}
}
