package layout.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.stereocast.R;


public class TintableImageView extends ImageView
{


	public TintableImageView(Context context)
	{
		super(context);
	}


	public TintableImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context, attrs, 0);
	}


	public TintableImageView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}


	public void setTintColor(@ColorInt int color)
	{
		super.setColorFilter(color, PorterDuff.Mode.SRC_IN);
	}


	public void setTintColorResource(@ColorRes int colorResource)
	{
		super.setColorFilter(getContext().getResources().getColor(colorResource), PorterDuff.Mode.SRC_IN);
	}


	private void init(Context context, AttributeSet attrs, int defStyle)
	{
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TintableImageView, defStyle, 0);
		int tintColor = a.getColor(R.styleable.TintableImageView_tint, Color.BLACK);
		a.recycle();
		setTintColor(tintColor);
	}


}