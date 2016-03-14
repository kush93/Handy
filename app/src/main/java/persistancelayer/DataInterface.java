package persistancelayer;

import java.util.List;

/**
 * Created by rishabhbhandari on 2016-02-20.
 * <p/>
 * Updated by Matthias on 2016-03-12 for more methods required by main listview.
 */
public interface DataInterface {


    //void addData(String noteName, String labelName,String note);
    boolean insertData(String time, String name, String label, String textNote, String filePath, String noteType);

    List<String> getData(String noteType);

    boolean updateData(String id, String time, String noteName, String noteLabel, String textNote, String filePath, String noteType);

}
