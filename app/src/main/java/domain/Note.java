package domain;

/**
 * Created by rishabhbhandari on 2016-02-18.
 */
public class Note {

    public int id;
    public String time;
    public String noteName;
    public String labelName;
    public String noteText;
    public String filePath;
    public String type;
    public int x = 0;

    public Note(int id,String time, String noteName, String noteLabel, String noteText, String filePath, String type) {
        this.id=id;
        this.noteName = noteName;
        this.labelName = noteLabel;
        this.noteText = noteText;
        this.filePath = filePath;
        this.type = type;

    }

    public boolean equalsNote(Note mynote) {
        return (this.noteName.equals(mynote.noteName)) && (this.noteText.equals(mynote.noteText));
    }



    @Override
    public String toString() {
        return id+'/'+noteName+'/'+labelName+'/'+noteText+'/'+filePath+'/'+type;
    }
}
