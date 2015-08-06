package layout.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.widget.TextView;

import com.stereocast.R;

import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * Created by jakubkinst on 20/07/15.
 */
public class CountdownTextView extends TextView
{
	private CountDownTimer mCountDownTimer;
	private OnCountdownFinishedListener mListener;


	public interface OnCountdownFinishedListener
	{
		void onCountdownFinished();
	}


	public CountdownTextView(Context context)
	{
		super(context);
		init();
	}


	public CountdownTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}


	public CountdownTextView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init();
	}


	public void setOnCountdownFinishedListener(OnCountdownFinishedListener listener)
	{
		mListener = listener;
	}


	public void startCountdown(Date targetDate)
	{
		cancelTimer();
		mCountDownTimer = new CountDownTimer(targetDate.getTime() - new Date().getTime(), 1000)
		{
			public void onTick(long millisUntilFinished)
			{
				long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
				long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
				long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));

				if(hours > 1)
					setText(String.format(getString(R.string.fragment_show_available_remaining_hours), hours, minutes, seconds));
				else if(hours == 1)
					setText(String.format(getString(R.string.fragment_show_available_remaining_hour), hours, minutes, seconds));
				else if(minutes >= 1)
					setText(String.format(getString(R.string.fragment_show_available_remaining_minutes), minutes, seconds));
				else
					setText(String.format(getString(R.string.fragment_show_available_remaining_seconds), seconds));

			}


			public void onFinish()
			{
				if(mListener != null)
					mListener.onCountdownFinished();
			}
		}.start();
	}


	public void cancelTimer()
	{
		if(mCountDownTimer != null)
			mCountDownTimer.cancel();
	}


	@Override
	protected void onDetachedFromWindow()
	{
		cancelTimer();
		super.onDetachedFromWindow();
	}


	private void init()
	{

	}


	private String getString(@StringRes int resourceId)
	{
		return getContext().getString(resourceId);
	}
}
