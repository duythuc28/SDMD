package au.edu.swin.sdmd.suncalculatorjava;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import au.edu.swin.sdmd.suncalculatorjava.calc.GeoLocation;

public class LocationFile {
    private static String filename = "au_location.txt";
//

    static void appendInput(Context context, GeoLocation location) {
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_APPEND);
            String writeText = location.getLocationName()+ "," + location.getLatitude() + "," + location.getLongitude() + "," + location.getTimeZone();
            outputStream.write(writeText.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static FileInputStream getFile(Context context) {
        FileInputStream fileInput = null;
        try {
            fileInput = context.openFileInput(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileInput;
    }

    static ArrayList<String> getFileContents(Context context) {
        ArrayList locationList = new ArrayList();
        FileInputStream fis = getFile(context);
        if(fis != null) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line;
                while ((line = br.readLine()) != null) {
                    locationList.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return locationList;

    }

    static void deleteFile(Context context) {
        context.deleteFile(filename);
    }
}
