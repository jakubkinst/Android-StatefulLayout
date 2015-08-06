package layout.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stereocast.R;
import com.stereocast.entity.Track;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by jakubkinst on 14/07/15.
 */
public class LibraryTrackItemView extends LinearLayout implements View.OnClickListener
{
	@Bind(R.id.view_track_item_title)
	TextView mTitle;
	@Bind(R.id.view_track_item_subtitle)
	TextView mSubtitle;
	@Bind(R.id.view_track_item_position)
	TextView mPositionText;
	private Track mTrack;


	public LibraryTrackItemView(Context context)
	{
		super(context);
		setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		LayoutInflater.from(getContext()).inflate(R.layout.view_library_track_item, this);
		ButterKnife.bind(this, this);
		setOnClickListener(this);
	}


	public void bind(final Track track, final int position)
	{
		mTrack = track;

		mTitle.setText(mTrack.getTitle());
		mSubtitle.setText(mTrack.getSubtitle());
		mPositionText.setText((position + 1) + "");
	}


	@Override
	public void onClick(View v)
	{

	}
}
