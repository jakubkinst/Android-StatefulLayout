package cz.kinst.jakub.sample.statefullayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by jakubkinst on 07/12/15.
 */
public class TestFragmentActivity extends AppCompatActivity {


	public static Intent newIntent(Context context) {
		Intent intent = new Intent(context, TestFragmentActivity.class);
		return intent;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_fragment);
	}
}
