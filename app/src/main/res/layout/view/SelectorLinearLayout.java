package layout.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;


/**
 * Created by Leos on 16. 7. 2015.
 */
public class SelectorLinearLayout extends LinearLayout
{
	private static final int[] ATTR_LIST_SELECTOR = {android.R.attr.listSelector};

	private final Drawable mSelector;


	public SelectorLinearLayout(Context context)
	{
		this(context, null);
	}


	public SelectorLinearLayout(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}


	public SelectorLinearLayout(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);

		TypedArray a = context.obtainStyledAttributes(attrs, ATTR_LIST_SELECTOR, 0, 0);
		mSelector = a.getDrawable(0);
		a.recycle();

		if(mSelector != null)
		{
			setWillNotDraw(false);
			mSelector.setCallback(this);
		}
	}


	@Override
	public void jumpDrawablesToCurrentState()
	{
		super.jumpDrawablesToCurrentState();

		final Drawable d = mSelector;
		if(d != null)
		{
			d.jumpToCurrentState();
		}
	}


	@Override
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void drawableHotspotChanged(float x, float y)
	{
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
		{
			return;
		}

		super.drawableHotspotChanged(x, y);

		final Drawable d = mSelector;
		if(d != null)
		{
			d.setHotspot(x, y);
		}
	}


	@Override
	public void draw(Canvas canvas)
	{
		super.draw(canvas);

		final Drawable d = mSelector;
		if(d != null)
		{
			d.setBounds(0, 0, getWidth(), getHeight());
			d.draw(canvas);
		}
	}


	@Override
	protected void drawableStateChanged()
	{
		super.drawableStateChanged();

		final Drawable d = mSelector;
		if(d != null && d.isStateful())
		{
			d.setState(getDrawableState());
		}
	}


	@Override
	protected boolean verifyDrawable(Drawable who)
	{
		return who == mSelector || super.verifyDrawable(who);
	}
}