package businesslayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import persistancelayer.NoteInterface;
import presentationlayer.NoteTakerUI;

/**
 * Created by Matthias on 16-03-12.
 */
public class TextNoteWrapper extends Activity implements NoteInterface, Serializable
{
	private String title;
	private String contents;
	private String tags;
	private String time;
	private boolean isPinned;

	//static TextNoteBL textNoteBL;
	TextNoteBL textNoteBL;
	private static String noteType = "textNote";

	public TextNoteWrapper(String title, String contents, String tags, String time, boolean isPinned) {
		this.title = title;
		this.contents = contents;
		this.tags = tags;
		this.time = time;
		this.isPinned = isPinned;

	}
	public TextNoteWrapper(Context context)
	{
		textNoteBL = new TextNoteBL(context);

	}

	public List<TextNoteWrapper> getSampleNotes(String noteType) {

		List<TextNoteWrapper> textNoteWrappers = new ArrayList<>();
		List<String> returnedNotes = new ArrayList<String>();

		returnedNotes=textNoteBL.getSavedData(noteType); // returns the data from the database for the textNote

		int listSize=returnedNotes.size();
		if(listSize>0)
		{
			for(int i=0;i<listSize;i++)
			{
				String singleNote=returnedNotes.get(i);
				String token[]=singleNote.trim().split("/");
				textNoteWrappers.add(new TextNoteWrapper(token[2],token[4],token[3],token[1],false));
			}

		}

		return textNoteWrappers;
	}

	@Override
	public void openNote(Context context)
	{
		NoteTakerUI.openNote(context, this);
	}

	@Override
	public boolean hasImages() {
		return false;
	}

	@Override
	public List<Bitmap> getImages() {
		return null;
	}

	@Override
	public boolean isPinned() {
		return isPinned;
	}

	@Override
	public boolean hasNoteTitle() {
		return title != null && !title.isEmpty();
	}

	@Override
	public String getNoteTitle() {
		return title;
	}

	@Override
	public boolean hasContents() {
		return contents != null && !contents.isEmpty();
	}

	@Override
	public String getContents() {
		return contents;
	}

	@Override
	public boolean hasTag() {
		return tags != null && !tags.isEmpty();
	}

	@Override
	public String getTag() {
		return tags;
	}

	@Override
	public boolean hasLastEditedTime() {
		return time != null && !time.isEmpty();
	}

	@Override
	public String getLastEditedTime() {
		return time;
	}
}