package businesslayer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import java.io.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import persistancelayer.DataBaseHelper;


public class HandwritingBL {

    DataBaseHelper dataBaseHelper = null;

    public HandwritingBL() {} // null constructor

    public HandwritingBL(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public boolean create(String noteName, String noteLabel, String noteText, String filePath, String noteType) {
        String editedTime = DateFormat.getDateTimeInstance().format(new Date());

        boolean isInserted = dataBaseHelper.insertData(editedTime, noteName, noteLabel, noteText, filePath, noteType); // calling DataBaseHelper Method passing data
        return isInserted;
    }

	public boolean updateData(String id, String noteName, String noteLabel, String textNote, String filePath, String noteType) {
		String editedTime = DateFormat.getDateTimeInstance().format(new Date());
		boolean result = dataBaseHelper.updateData(id, editedTime, noteName, noteLabel, textNote, filePath, noteType);
		return result;
	}


    public int changeColor(String newColor) {
        return Color.parseColor(newColor);
    }


    public int setErase(int selectedColor, boolean eraseState) {
        int returnColor = selectedColor;

        if(eraseState){
            returnColor = Color.parseColor("#ffffffff");
        }

        return returnColor;
    }

    public String saveImage(Bitmap sourceImage, String existingFile) {
        // Saves to picture directory, under a subfolder called "Handy"
        String fileName = null;
        boolean folderExists = true;
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Handy";
        File outputDir = new File(path);

        // Creates subfolder "Handy" if it does not exist
        if(!outputDir.exists()){
            folderExists = outputDir.mkdir();
        }

        if(folderExists) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            sourceImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            File destination;
            if(existingFile != null) {
                fileName = existingFile;
                destination = new File(path, existingFile);
            }
            else {
                fileName = System.currentTimeMillis() + ".jpg";
                destination = new File(path, fileName);
            }

            FileOutputStream fo;
            try
            {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();

            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    public Bitmap processImage(byte[] byteArray) {
        Bitmap bmpImg = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bmpImg;
    }

    public List<String> getSavedData(String type) {
        List<String> handwritingList;
        handwritingList=dataBaseHelper.getData(type);
        return handwritingList;
    }
}
