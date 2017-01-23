package cz.kinst.jakub.sample.statefullayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}


	public void showSwitch(View view) {
		startActivity(CustomizationsActivity.newIntent(this));
	}


	public void showSimple(View view) {
		startActivity(SimpleActivity.newIntent(this));
	}


	public void showLoading(View view) {
		startActivity(LoadingActivity.newIntent(this));
	}


	public void showDataBinding(View view) {
		startActivity(DataBindingActivity.newIntent(this));
	}


	public void showDataBindingController(View view) {
		startActivity(DataBindingControllerActivity.newIntent(this));
	}


	public void showFragmentTest(View view) {
		startActivity(TestFragmentActivity.newIntent(this));
	}

}
