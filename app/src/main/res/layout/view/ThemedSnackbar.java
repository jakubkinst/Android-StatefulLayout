package layout.view;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.stereocast.R;


/**
 * Created by jakubkinst on 25/06/15.
 */
public class ThemedSnackbar
{
	public static Snackbar make(View view, CharSequence text, int duration)
	{
		if(view == null)
			return null;
		Snackbar snackbar = Snackbar.make(view, text, duration);
		snackbar.getView().setBackgroundColor(view.getResources().getColor(R.color.snackbar_bg));
		return snackbar;
	}


	public static Snackbar make(View view, int resId, @Snackbar.Duration int duration)
	{
		return make(view, view.getResources().getText(resId), duration);
	}

}