package layout.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stereocast.R;
import com.stereocast.StereocastApplication;
import com.stereocast.entity.Event;
import com.stereocast.entity.Track;
import com.stereocast.listener.PurchaseConnector;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by tomasgarba on 07/24/15.
 */
public class EventHeaderItemView extends FrameLayout
{
	private final PurchaseConnector mPurchaseConnector;
	@Bind(R.id.include_tracklist_header_title)
	TextView mTitle;
	@Bind(R.id.include_tracklist_header_subtitle)
	TextView mSubtitle;
	@Bind(R.id.include_tracklist_header_cover)
	ImageView mImageView;
	@Bind(R.id.include_tracklist_header_primary_button)
	Button mBuyCurrentButton;
	@Bind(R.id.include_event_tracklist_buy_all_bar_buy_all_button)
	TextView mBuyAllButton;
	@Bind(R.id.include_event_tracklist_buy_all_bar_all_purchased)
	TextView mAllPurchasedText;
	private Event mEvent;
	private String mTrackPrice = "";
	private String mEventPrice = "";


	public EventHeaderItemView(Context context, PurchaseConnector purchaseConnector,
							   Event event, String trackPrice,
							   String eventPrice)
	{
		super(context);
		setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

		LayoutInflater.from(getContext()).inflate(R.layout.include_tracklist_header, this);
		ButterKnife.bind(this, this);
		mPurchaseConnector = purchaseConnector;
		mEvent = event;
		mTrackPrice = trackPrice;
		mEventPrice = eventPrice;
	}


	public void bind(Event event, String trackPrice, String eventPrice)
	{
		mEvent = event;
		mTrackPrice = trackPrice;
		mEventPrice = eventPrice;

		if(mEvent != null)
		{
			Track mCurrentTrack = mEvent.getCurrentlyPlayingTrack();

			if(mCurrentTrack != null)
			{
				Glide.with(StereocastApplication.getContext())
						.load(mEvent.getCoverUrl())
						.placeholder(R.drawable.image_cover_placeholder)
						.error(R.drawable.image_cover_placeholder)
						.into(mImageView);
				mTitle.setText(mCurrentTrack.getTitle());
				mSubtitle.setText(mCurrentTrack.getSubtitle());

				if(mPurchaseConnector.isTrackPurchased(mCurrentTrack))
				{
					mBuyCurrentButton.setText(R.string.title_purchased);
					mBuyCurrentButton.setEnabled(false);
				}
				else
				{
					mBuyCurrentButton.setEnabled(true);
					mBuyCurrentButton.setText(String.format(StereocastApplication.getContext().getString(R.string.fragment_event_i_want_this_one), mTrackPrice));
				}
			}

			if(mPurchaseConnector.isEventPurchased(mEvent))
			{
				mBuyAllButton.setVisibility(View.GONE);
				mAllPurchasedText.setText(R.string.title_purchased);
				mAllPurchasedText.setVisibility(View.VISIBLE);
			}
			else
			{
				mBuyAllButton.setVisibility(View.VISIBLE);
				mBuyAllButton.setText(String.format(StereocastApplication.getContext().getString(R.string.track_state_buy_all), mEventPrice));
				mAllPurchasedText.setVisibility(View.GONE);
			}

			// decide if header should be displayed
			Date now = new Date();
			if(mEvent.getFinishedAt() != null && mEvent.getFinishedAt().before(now) && mEvent.getPurchaseWindowEnd().after(now))
			{
				this.setVisibility(View.GONE);
				setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
			}
			else
			{
				setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
				this.setVisibility(View.VISIBLE);
			}
		}
		else
		{
			// hide header when event is null, showing in Library or Download fragment
			this.setVisibility(View.GONE);
			setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
		}
	}


	@OnClick(R.id.include_tracklist_header_primary_button)
	void buyCurrentTrack()
	{
		if(mEvent != null)
		{
			Track currentTrack = mEvent.getCurrentlyPlayingTrack();
			mPurchaseConnector.purchaseTrack(currentTrack);
		}
	}


	@OnClick(R.id.include_event_tracklist_buy_all_bar_buy_all_button)
	void onPurchaseEventClicked()
	{
		if(mEvent != null)
		{
			mPurchaseConnector.purchaseEvent(mEvent);
		}
	}
}
