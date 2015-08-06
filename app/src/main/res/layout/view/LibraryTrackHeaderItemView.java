package layout.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stereocast.R;
import com.stereocast.StereocastApplication;
import com.stereocast.activity.PlayerActivity;
import com.stereocast.entity.Event;
import com.stereocast.entity.Track;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by tomasgarba on 08/03/15.
 */
public class LibraryTrackHeaderItemView extends FrameLayout
{
	@Bind(R.id.include_tracklist_header_title)
	TextView mEventTitle;
	@Bind(R.id.include_tracklist_header_subtitle)
	TextView mEventSubtitle;
	@Bind(R.id.include_tracklist_header_cover)
	ImageView mShowImage;
	@Bind(R.id.include_tracklist_header_primary_button)
	Button mPlayAllButton;

	private Track mTrack;
	private Event mEvent;


	public LibraryTrackHeaderItemView(Context context)
	{
		super(context);
		setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		LayoutInflater.from(getContext()).inflate(R.layout.include_tracklist_header, this);
		ButterKnife.bind(this, this);
	}


	public void bind(Event event)
	{
		mEvent = event;

		mPlayAllButton.setText(R.string.button_play_now);

		if(mEvent != null)
		{
			mPlayAllButton.setOnClickListener(v -> v.getContext().startActivity(PlayerActivity.newIntent(v.getContext(), mEvent.getPlayableTracks(), 0)));
			mEventTitle.setText(mEvent.getTitle());
			mEventSubtitle.setText(mEvent.getSubtitle());
			Glide.with(StereocastApplication.getContext())
					.load(mEvent.getCoverUrl())
					.placeholder(R.drawable.image_cover_placeholder)
					.error(R.drawable.image_cover_placeholder)
					.into(mShowImage);
		}
	}
}
