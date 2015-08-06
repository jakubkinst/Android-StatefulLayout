package layout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.stereocast.utility.UserUtility;


public class EmailSuggestionAutoCompleteTextView extends AutoCompleteTextView
{

	private int mThreshold;


	public EmailSuggestionAutoCompleteTextView(Context context)
	{
		super(context);
	}


	public EmailSuggestionAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}


	public EmailSuggestionAutoCompleteTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, UserUtility.getDeviceEmailAccounts());
		setAdapter(adapter);
	}


	@Override
	public void setThreshold(int threshold)
	{
		if(threshold < 0)
		{
			threshold = 0;
		}
		mThreshold = threshold;
	}


	@Override
	public boolean enoughToFilter()
	{
		return getText().length() >= mThreshold;
	}


	@Override
	public int getThreshold()
	{
		return mThreshold;
	}

}