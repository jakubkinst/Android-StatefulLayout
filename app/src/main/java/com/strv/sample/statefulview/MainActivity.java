package com.strv.sample.statefulview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.strv.view.StatefulView;


public class MainActivity extends AppCompatActivity
{

	StatefulView mStatefulView;


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
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mStatefulView = (StatefulView) findViewById(R.id.stateful);
		mStatefulView.showProgress();
	}

}
