package cz.kinst.jakub.sample.statefullayout;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.kinst.jakub.sample.statefullayout.databinding.FragmentTestBinding;
import cz.kinst.jakub.view.SimpleStatefulLayout;


/**
 * Created by jakubkinst on 07/12/15.
 */
public class TestFragment extends Fragment {
	private FragmentTestBinding mBinding;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_test, container, false);
		mBinding.setState(SimpleStatefulLayout.State.OFFLINE);
		return mBinding.getRoot();
	}
}
