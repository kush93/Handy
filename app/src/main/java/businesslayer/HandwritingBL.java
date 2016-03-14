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

/**
 * Created by Ian on 21/02/2016.
 * 
 * Business Layer for Handwriting functionality
 * Implements the "Business Logic" of the Handwriting functionality
 * HandwritingView.java calls these methods
 *
 */
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

    // Business Logic implementation of changing color
    // Returns int of color that has been parsed from the string newColor
    public int changeColor(String newColor) {
        return Color.parseColor(newColor);
    }

    // Business Logic implementation of determining whether user is in "eraser" mode or "pen" mode
    // If in eraser mode, eraseState is true, thus set color to white (as background as white)
    // Else, return previously set color (which was passed from HandwritingView.java)
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
                //fileLocation = destination.getCanonicalPath();
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
        /**
        List<String> tempList = new ArrayList<String>();
        tempList = dataBaseHelper.getData(type);
        int listSize = tempList.size();
        String tempStr;

        for(int i=0; i<listSize;i++) {
            tempStr = tempList.get(i);
            if(tempStr.contains("handwritingNote")) {
                handwritingList.add(tempStr);
            }
        }
         **/
        handwritingList=dataBaseHelper.getData(type);
        return handwritingList;
    }
}
