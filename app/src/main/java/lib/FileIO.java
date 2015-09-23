package lib;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by devnote on 15. 9. 21.
 */
public class FileIO {
    public void filepathcheck(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            Log.v("pathcheck", path + "not exist");
            dir.mkdir();
        }
    }

    public void savefile(String path, String filename, String put) {
        File f = new File(path + filename);// 날짜데이터파일 저장
        FileWriter fw;
        Log.v("fileWriter", path + filename + " writing started");
        try {
            fw = new FileWriter(f);
            fw.write(put);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String readfile(String path, String filename) {
        File f = new File(path + filename);
        if (!f.exists()) {
            Log.v("filecheck", filename + "not exist");
            savefile(path, filename, "NULL");
        }

        StringBuffer sb = new StringBuffer();
        Log.v("fileWriter", filename + " reading started");
        try {// 저장일시 읽어들이기
            FileInputStream fis = new FileInputStream(path + filename);
            int n;
            while ((n = fis.available()) > 0) {
                byte b[] = new byte[n];
                if (fis.read(b) == -1)
                    break;
                sb.append(new String(b));
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Could not find file" + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
