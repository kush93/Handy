package persistancelayer;

import java.util.ArrayList;
import java.util.List;

import domain.Note;

/**
 * Created by rishabhbhandari on 2016-02-18
 */
public class TextNotePL implements DataInterface {

    static ArrayList<Note> noteList = new ArrayList<Note>();
    public Note myNote;
    static int SIZE = 0;
    int id = 1;

    public TextNotePL() {

    }


    public boolean containsNote(Note myNote) {
        boolean result = false;
        for (int i = 0; i < getNoteList().size() && !result; i++) {
            if (((Note) getNoteList().get(i)).equalsNote(myNote)) {
                result = true;
            }
        }
        return result;
    }

    public ArrayList<Note> getNoteList() {

        return noteList;

    }

    public void createNoteList(Note noteObj) {
        noteList.add(noteObj);

    }

    public void clearNoteList() {
        noteList.clear();
    }


    public static void setSIZE(int SIZE) {
        TextNotePL.SIZE = SIZE;
    }


    @Override
    public boolean insertData(String time, String noteName, String noteLabel, String noteText, String filePath, String type) {

        myNote = new Note(id, time, noteName, noteLabel, noteText, null, type);
        boolean result = noteList.add(myNote);

        id++;
        return result;


    }

    @Override
    public List<String> getData(String noteType) {

        List<String> noteData = new ArrayList<String>();

        for (int i = 0; i < noteList.size(); i++) {
            noteData.add(noteList.get(i).toString());

        }


        return noteData;
    }

    @Override
    public boolean updateData(String id, String time, String noteName, String noteLabel, String textNote, String filePath, String noteType) {


        return false;
    }
}
