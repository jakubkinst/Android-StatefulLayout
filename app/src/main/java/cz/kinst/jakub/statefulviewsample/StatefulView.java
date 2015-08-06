package cz.kinst.jakub.statefulviewsample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * Created by jakubkinst on 05/08/15.
 */
public class StatefulView extends FrameLayout
{
	private ViewState mViewState = null;
	private View mContent;
	private FrameLayout mContainerProgress, mContainerOffline, mContainerEmpty;


	public StatefulView(Context context)
	{
		super(context);
	}


	public StatefulView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}


	public StatefulView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}


	public void setEmptyText(CharSequence emptyText)
	{
		((TextView) findViewById(R.id.placeholder_empty_text)).setText(emptyText);
	}


	public void setOfflineText(CharSequence offlineText)
	{
		((TextView) findViewById(R.id.placeholder_offline_text)).setText(offlineText);
	}


	public void showContent()
	{
		setViewState(ViewState.CONTENT);
	}


	public void showProgress()
	{
		setViewState(ViewState.PROGRESS);
	}


	public void showOffline()
	{
		setViewState(ViewState.OFFLINE);
	}


	public void showEmpty()
	{
		setViewState(ViewState.EMPTY);
	}


	public ViewState getViewState()
	{
		return mViewState;
	}


	public void setViewState(ViewState viewState)
	{
		mViewState = viewState;
		if(mContent != null)
			mContent.setVisibility(viewState == ViewState.CONTENT ? View.VISIBLE : View.GONE);
		if(mContainerProgress != null)
			mContainerProgress.setVisibility(viewState == ViewState.PROGRESS ? View.VISIBLE : View.GONE);
		if(mContainerOffline != null)
			mContainerOffline.setVisibility(viewState == ViewState.OFFLINE ? View.VISIBLE : View.GONE);
		if(mContainerEmpty != null)
			mContainerEmpty.setVisibility(viewState == ViewState.EMPTY ? View.VISIBLE : View.GONE);
	}


	private void initialize()
	{
	}


	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();
		mContent = getChildAt(0);
		addView(LayoutInflater.from(getContext()).inflate(R.layout.view_stateful, this, false));
		mContainerProgress = (FrameLayout) findViewById(R.id.container_progress);
		mContainerOffline = (FrameLayout) findViewById(R.id.container_offline);
		mContainerEmpty = (FrameLayout) findViewById(R.id.container_empty);

	}


	@Override
	protected void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		initialize();
	}
}