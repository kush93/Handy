package presentationlayer;

import android.graphics.Bitmap;
import persistancelayer.NoteInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthias on 16-03-12.
 */
public class SampleNote implements NoteInterface
{
	private String title;
	private String contents;
	private List<String> tags;
	private String time;
	private boolean isPinned;

	public SampleNote(String title, String contents, List<String> tags, String time, boolean isPinned)
	{
		this.title = title;
		this.contents = contents;
		this.tags = tags;
		this.time = time;
		this.isPinned = isPinned;
	}

	static List<SampleNote> getSampleNotes()
	{
		List<SampleNote> sampleNotes = new ArrayList<>();

		sampleNotes.add(new SampleNote(null, null, null, null, false));

		List<String> tags = new ArrayList<>();
		sampleNotes.add(new SampleNote("My Sample Note 1", "And Some Contents 1", tags, "2014", false));

		tags.add("foo");
		sampleNotes.add(new SampleNote("My Sample Note 2", "And Some Contents 2", tags, "2014", true));

		tags.add("boo");
		sampleNotes.add(new SampleNote("My Sample Note 3", "And Some Contents 3", tags, null, true));

		tags.add("NA");
		sampleNotes.add(new SampleNote("My Sample Note 3", "And Some Contents 3", tags, "2014", false));

		return sampleNotes;
	}

	@Override
	public boolean hasImages()
	{
		return false;
	}

	@Override
	public List<Bitmap> getImages()
	{
		return null;
	}

	@Override
	public boolean isPinned()
	{
		return isPinned;
	}

	@Override
	public boolean hasTitle()
	{
		return title != null;
	}

	@Override
	public String getTitle()
	{
		return null;
	}

	@Override
	public boolean hasContents()
	{
		return contents != null;
	}

	@Override
	public String getContents()
	{
		return contents;
	}

	@Override
	public boolean hasTag()
	{
		return tags != null && !tags.isEmpty();
	}

	@Override
	public List<String> getTags()
	{
		return tags;
	}

	@Override
	public boolean hasLastEditedTime()
	{
		return time != null;
	}

	@Override
	public String getLastEditedTime()
	{
		return time;
	}
}
