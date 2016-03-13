package presentationlayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.kushal.rihabhbhandari.R;
import persistancelayer.NoteInterface;

import java.util.List;

/**
 * Created by Matthias on 16-03-12.
 */
public class NoteListAdapter extends BaseAdapter
{
	private Context                 context;
	private List<NoteInterface>     noteList;
	private static LayoutInflater   inflater = null;

	// pin - sorted must be done before noteList is passed to here
	public NoteListAdapter(Context context, List<NoteInterface> noteList)
	{
		this.context = context;
		this.noteList = noteList;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{ return noteList.size(); }

	@Override
	public Object getItem(int position)
	{ return noteList.get(position); }

	@Override
	public long getItemId(int position)
	{ return position; }

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = convertView;
		if (view == null)
			view = inflater.inflate(R.layout.main_list_item, null);

		final NoteInterface note = (NoteInterface) getItem(position);

		ImageView iv_thumbnail  = (ImageView) view.findViewById(R.id.main_list_item_iv_thumbnail);
		ImageView iv_pin        = (ImageView) view.findViewById(R.id.main_list_item_iv_pin);
		TextView  tv_title      = (TextView)  view.findViewById(R.id.main_list_item_tv_title);
		TextView  tv_content    = (TextView)  view.findViewById(R.id.main_list_item_tv_content_line);
		TextView  tv_editTime   = (TextView)  view.findViewById(R.id.main_list_item_tv_edit_time);
		TextView  tv_tag       = (TextView)  view.findViewById(R.id.main_list_item_tv_tag);
		TextView  tv_tag_const = (TextView)  view.findViewById(R.id.main_list_item_tv_tag_const);

		tv_title.setText(note.hasNoteTitle() ? note.getNoteTitle() : "Empty Title");
		tv_editTime.setText(note.hasLastEditedTime() ? note.getLastEditedTime() : "Unknown Edit Time");

		if (!note.hasContents())
		{
			tv_content.setVisibility(View.GONE);
		}
		else
		{
			tv_content.setVisibility(View.VISIBLE);
			tv_content.setText(note.getContents());
		}

		if (note.hasImages())
			iv_thumbnail.setImageBitmap(note.getImages().get(0));

		if (!note.hasTag())
		{
			tv_tag.setVisibility(View.GONE);
			tv_tag_const.setVisibility(View.GONE);
		}
		else
		{
			tv_tag.setVisibility(View.VISIBLE);
			tv_tag_const.setVisibility(View.VISIBLE);
			tv_tag.setText(note.getTag());
		}

		iv_pin.setVisibility(note.isPinned() ? View.VISIBLE : View.INVISIBLE);

		return view;
	}
}
