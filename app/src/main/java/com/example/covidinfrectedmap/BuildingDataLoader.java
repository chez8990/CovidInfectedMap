package com.example.covidinfrectedmap;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

public class BuildingDataLoader {
    private Context mContext;
    private String mFileName;
    public BuildingDataLoader(Context context, String fileName){
        mContext = context;
        mFileName = fileName;
    }

    public void storeData(HashSet<String> buildingNames){
        try {
            FileOutputStream fos = mContext.openFileOutput(mFileName, mContext.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(buildingNames);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public HashSet<String> readData() throws IOException, ClassNotFoundException {
        FileInputStream fis = mContext.openFileInput(mFileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        HashSet<String> buildingNames = (HashSet<String>) ois.readObject();
        return buildingNames;
//        try {
//
//        } catch (Exception e){
////            e.printStackTrace();
//            System.out.println("Object does not exist");
//            return null;
//        }

    }


}
