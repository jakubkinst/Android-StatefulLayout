package cz.kinst.jakub.statefulviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity
{

	StatefulView mStatefulView;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mStatefulView = (StatefulView) findViewById(R.id.stateful);
		mStatefulView.showProgress();
	}


	public void showContent(View view)
	{
		mStatefulView.showContent();
	}


	public void showOffline(View view)
	{
		mStatefulView.showOffline();
	}


	public void showProgress(View view)
	{
		mStatefulView.showProgress();
	}


	public void showEmpty(View view)
	{
		mStatefulView.showEmpty();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if(id == R.id.action_settings)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
