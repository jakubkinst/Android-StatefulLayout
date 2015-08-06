package layout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.stereocast.R;
import com.stereocast.entity.Event;
import com.stereocast.listener.PurchaseConnector;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by jakubkinst on 14/07/15.
 */
public class PurchasePopupView extends LinearLayout implements CountdownTextView.OnCountdownFinishedListener
{
	@Bind(R.id.view_purchase_popup_title)
	CountdownTextView mCountdown;
	@Bind(R.id.view_purchase_popup_button_primary)
	Button mButtonPrimary;
	@Bind(R.id.view_purchase_popup_button_secondary)
	Button mButtonSecondary;
	private Event mEvent;
	private PurchaseConnector mPurchaseConnector;


	public PurchasePopupView(Context context)
	{
		super(context);
		init();
	}


	public PurchasePopupView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}


	public void setEvent(Event event, PurchaseConnector purchaseConnector)
	{
		mEvent = event;
		long target = event.getPurchaseWindowEnd().getTime();
		mCountdown.startCountdown(new Date(target));
		mPurchaseConnector = purchaseConnector;
		if(purchaseConnector.isEventPurchased(event))
			setVisibility(GONE);
		else
		{
			setVisibility(VISIBLE);
			mButtonPrimary.setText(String.format(getContext().getString(R.string.track_state_buy_all), purchaseConnector.getEventPrice()));
		}

	}


	@Override
	public void onCountdownFinished()
	{
		dismiss();
	}


	@OnClick(R.id.view_purchase_popup_button_primary)
	void buyEntireShow()
	{
		mPurchaseConnector.purchaseEvent(mEvent);
	}


	@OnClick(R.id.view_purchase_popup_button_secondary)
	void dismiss()
	{
		setVisibility(GONE);
	}


	private void init()
	{
		setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		LayoutInflater.from(getContext()).inflate(R.layout.view_purchase_popup, this);
		ButterKnife.bind(this, this);
		mCountdown.setOnCountdownFinishedListener(this);
	}
}
