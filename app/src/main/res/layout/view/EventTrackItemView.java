package layout.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.filippudak.ProgressPieView.ProgressPieView;
import com.stereocast.BusProvider;
import com.stereocast.R;
import com.stereocast.entity.Track;
import com.stereocast.event.download.TrackDownloadCancelEvent;
import com.stereocast.event.download.TrackDownloadErrorEvent;
import com.stereocast.event.download.TrackDownloadEvent;
import com.stereocast.listener.PurchaseConnector;
import com.stereocast.utility.NavigationHandler;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by jakubkinst on 14/07/15.
 */
public class EventTrackItemView extends LinearLayout implements View.OnClickListener, View.OnLongClickListener
{
	private final PurchaseConnector mPurchaseConnector;
	private final NavigationHandler mNavigationHandler;
	@Bind(R.id.view_track_item_title)
	TextView mTitle;
	@Bind(R.id.view_track_item_subtitle)
	TextView mSubtitle;
	@Bind(R.id.view_track_item_download_percent)
	ProgressPieView mDownloadProgress;
	@Bind(R.id.view_track_item_download_button)
	TintableImageView mDownloadButton;
	@Bind(R.id.view_track_item_download_complete)
	TextView mDownloadCompleteText;
	@Bind(R.id.view_track_item_buy)
	TextView mBuyText;
	@Bind(R.id.view_track_item_waiting)
	TextView mWaitingText;
	@Bind(R.id.view_track_item_loading)
	ProgressBar mLoadingProgress;

	private Track mTrack;
	private State mState = State.READY_TO_DOWNLOAD;


	private enum State
	{
		NOT_PUBLISHED, PUBLISHED, WAITING, READY_TO_DOWNLOAD, DOWNLOADING, DOWNLOADED, LOADING
	}


	public EventTrackItemView(Context context, PurchaseConnector purchaseConnector, NavigationHandler navigationHandler)
	{
		super(context);
		setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		LayoutInflater.from(getContext()).inflate(R.layout.view_event_track_item, this);
		ButterKnife.bind(this, this);
		setOnClickListener(this);
		setOnLongClickListener(this);
		mPurchaseConnector = purchaseConnector;
		mNavigationHandler = navigationHandler;
	}


	public void onEventMainThread(final TrackDownloadErrorEvent event)
	{
		if(mTrack != null && mTrack.getId().equals(event.getTrackId()))
			resolveState();
	}


	public void onEventMainThread(final TrackDownloadCancelEvent event)
	{
		if(mTrack != null && mTrack.getId().equals(event.getTrackId()))
			resolveState();
	}


	public void onEventMainThread(final TrackDownloadEvent event)
	{
		if(mTrack != null && mTrack.getId() != null && mTrack.getId().equals(event.getTrackId()))
		{
			mDownloadProgress.setProgress(event.getPercentDownloaded());
			setState(event.isFinished() ? State.DOWNLOADED : State.DOWNLOADING);
		}
	}


	public void bind(final Track track)
	{
		mTrack = track;
		mTitle.setText(mTrack.getTitle());
		mSubtitle.setText(mTrack.getSubtitle());
		mBuyText.setText(String.format(getContext().getString(R.string.track_state_buy), mTrack.getPrice()));
		resolveState();
	}


	@Override
	public void onClick(View v)
	{
		if(getState() == State.READY_TO_DOWNLOAD)
		{
			setState(State.LOADING);
			mTrack.download();
		}
		else if(getState() == State.DOWNLOADING)
		{
			mTrack.cancelDownload();
			resolveState();
		}
		else if(getState() == State.PUBLISHED)
		{
			mPurchaseConnector.purchaseTrack(mTrack);
			resolveState();
		}
		else if(getState() == State.DOWNLOADED)
		{
			mNavigationHandler.showLibraryEvent(mTrack.getPinnedEvent().getId(), true);
		}
		else if(getState() == State.WAITING)
		{
			mNavigationHandler.showSnackBarMessage(getContext().getString(R.string.event_waiting_message));
		}
	}


	@Override
	public boolean onLongClick(View v)
	{
		if(getState() == State.DOWNLOADED)
		{
			mTrack.getDownloadedFile().delete();
			resolveState();
			return true;
		}
		return false;
	}


	public State getState()
	{
		return mState;
	}


	public void setState(State state)
	{
		mState = state;
		if(state == State.NOT_PUBLISHED)
		{
			mDownloadButton.setVisibility(GONE);
			mDownloadCompleteText.setVisibility(GONE);
			mDownloadProgress.setVisibility(GONE);
			mBuyText.setVisibility(GONE);
			mWaitingText.setVisibility(GONE);
			mLoadingProgress.setVisibility(GONE);
		}
		else if(state == State.PUBLISHED)
		{
			mDownloadButton.setVisibility(GONE);
			mDownloadCompleteText.setVisibility(GONE);
			mDownloadProgress.setVisibility(GONE);
			mBuyText.setVisibility(VISIBLE);
			mWaitingText.setVisibility(GONE);
			mLoadingProgress.setVisibility(GONE);
		}
		else if(state == State.WAITING)
		{
			mDownloadButton.setVisibility(GONE);
			mDownloadProgress.setVisibility(GONE);
			mDownloadCompleteText.setVisibility(GONE);
			mBuyText.setVisibility(GONE);
			mWaitingText.setVisibility(VISIBLE);
			mLoadingProgress.setVisibility(GONE);
		}
		else if(state == State.READY_TO_DOWNLOAD)
		{
			mDownloadButton.setVisibility(VISIBLE);
			mDownloadProgress.setVisibility(VISIBLE);
			mDownloadProgress.setProgress(0);
			mDownloadProgress.setText("");
			mDownloadCompleteText.setVisibility(GONE);
			mBuyText.setVisibility(GONE);
			mWaitingText.setVisibility(GONE);
			mLoadingProgress.setVisibility(GONE);
		}
		else if(state == State.DOWNLOADED)
		{
			mDownloadButton.setVisibility(GONE);
			mDownloadCompleteText.setVisibility(VISIBLE);
			mDownloadProgress.setVisibility(GONE);
			mBuyText.setVisibility(GONE);
			mWaitingText.setVisibility(GONE);
			mLoadingProgress.setVisibility(GONE);
		}
		else if(state == State.DOWNLOADING)
		{
			mDownloadButton.setVisibility(GONE);
			mDownloadProgress.setVisibility(VISIBLE);
			mDownloadProgress.setText("");
			mDownloadCompleteText.setVisibility(GONE);
			mBuyText.setVisibility(GONE);
			mWaitingText.setVisibility(GONE);
			mLoadingProgress.setVisibility(GONE);
		}
		else if(state == State.LOADING)
		{
			mDownloadButton.setVisibility(GONE);
			mDownloadCompleteText.setVisibility(GONE);
			mDownloadProgress.setVisibility(GONE);
			mBuyText.setVisibility(GONE);
			mWaitingText.setVisibility(GONE);
			mLoadingProgress.setVisibility(VISIBLE);
		}
	}


	@Override
	protected void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		BusProvider.get().register(this);
	}


	@Override
	protected void onDetachedFromWindow()
	{
		BusProvider.get().unregister(this);
		super.onDetachedFromWindow();
	}


	private void resolveState()
	{
		if(!mTrack.isPublished())
			setState(State.NOT_PUBLISHED);
		else if(mPurchaseConnector.isTrackBeingPurchased(mTrack))
			setState(State.LOADING);
		else if(!mPurchaseConnector.isTrackPurchased(mTrack))
			setState(State.PUBLISHED);
		else if(!mTrack.isReadyToDownload())
			setState(State.WAITING);
		else if(!mTrack.isDownloaded())
			setState(State.READY_TO_DOWNLOAD);
		else
			setState(State.DOWNLOADED);
	}
}
