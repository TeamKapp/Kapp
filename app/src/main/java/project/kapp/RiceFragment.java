package project.kapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import lib.Meallib;
import lib.netload;

public class RiceFragment extends Fragment implements View.OnClickListener {

    String address = "http://hes.cne.go.kr/sts_sci_md00_003.do?schulCode=N100000131&schulCrseScCode=4&schulKndScCode=04&schMmealScCode=0";
    String kongjugopath = "/storage/sdcard0/Kongjugodata/";

    Calendar cal = Calendar.getInstance();
    static boolean ran = false;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    int pd = cal.get(Calendar.DATE);
    private int mShortAnimationDuration;

    private GestureDetector gestureScanner;

    private TextView tv, riceMorning, riceLaunch, riceDinner, ricedate;
    private Button prevBtn, nextBtn, seemoreExitBtn;
    private RelativeLayout seemorerice_layout;
    private ViewGroup seedaterice;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v("KappLog", "RiceFragment Started");


        view = inflater.inflate(R.layout.f_rice, container, false);

        //ricedate = (TextView) view.findViewById(R.id.noti_txt);

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        taskstartmanager(address, 0, pd, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1).replace('\n', ' ');
        //outputmanage(pd);


        /*Button yest = (Button) view.findViewById(R.id.rice_yesterday);
        yest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (pd > 1) {
                    pd--;
                    outputmanage(pd);
                }
            }
        });

        Button toda = (Button) view.findViewById(R.id.rice_today);
        toda.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pd = cal.get(Calendar.DATE);
                outputmanage(pd);
            }
        });

        Button tomo = (Button) view.findViewById(R.id.rice_tomorrow);
        tomo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (pd < yoon()) {
                    pd++;
                    outputmanage(pd);
                }
            }
        });*/

        return view;
    }

    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    void outputmanage(int date) {/*
        tv = (TextView) view.findViewById(R.id.rice_mor_txt);
        tv.setText(taskstartmanager(address, 0, date, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1).replace('\n', ' '));

        tv = (TextView) view.findViewById(R.id.rice_lau_txt);
        tv.setText(taskstartmanager(address, 1, date, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1).replace('\n', ' '));

        tv = (TextView) view.findViewById(R.id.rice_din_txt);
        tv.setText(taskstartmanager(address, 2, date, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1).replace('\n', ' '));*/

    }

    String taskstartmanager(String addr, int mtime, int mdate, int myear, int month) {
        String mmonth = adds0tomonth(month);
        filepathcheck(kongjugopath);
        Context ct = getActivity();
        netload nl = new netload();
        if (!ran && nl.Checknetwork(ct)) {// 네트워크 체크
            Log.v("Thread Address", addr + "&schYm=" + myear + "." + mmonth+"/n/n");
            Meallib ml = new Meallib(addr + "&schYm=" + myear + "." + mmonth);
            ml.start();
            try {
                ml.join();// 불러옴을 확인
                Log.v("meallib ended", Meallib.parsed[1][1]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            savefile(kongjugopath, "Monthcheck.txt", "" + (cal.get(Calendar.MONTH) + 1));

            for (int i = 0; i < 3; i++) {// 파일로 저장
                for (int j = 1; j < yoon() + 1; j++) {

                    Log.v("meallib ended", Meallib.parsed[1][1]);
                    System.out.println("" + i + j);
                    savefile(kongjugopath, i + "," + mmonth + "월" + j + "일" + ".txt", Meallib.parsed[i][j]);
                }
            }
            ran = true;
            return Meallib.parsed[mtime][mdate];
        } else {// 네트워크 문제



            if (readfile(kongjugopath, "Monthcheck.txt").equals((cal.get(Calendar.MONTH) + 1) + "")) {// 현재 유효한 데이터인지
                // 확인함

                return readfile(kongjugopath , mtime + "," + mmonth + "월" + mdate + "일" + ".txt");
            } else {
                return "인터넷 연결이 필요합니다.";


            }
        }
    }
    private String adds0tomonth(int month){
        if(month<10)
            return "0"+month;
        else
            return month+"";

    }

    public int yoon() {
        int thisyear = cal.get(Calendar.YEAR);
        int feb;
        if ((thisyear % 4 == 0) && (thisyear % 100 != 0)
                || (thisyear % 400 == 0)) {
            feb = 29;
        } else
            feb = 28;
        switch (cal.get(Calendar.MONTH) + 1) {
            case 2:
                return feb;
            case 4:
                return 30;
            case 6:
                return 30;
            case 9:
                return 30;
            case 11:
                return 30;
            default:
                return 31;
        }
    }
    private void filepathcheck(String path){
            File dir = new File(path);
            if (!dir.exists()) {
                Log.v("pathcheck", path+"not exist");
                dir.mkdir();
            }
    }

    private void savefile(String path, String filename, String put) {
        File f = new File(filename);// 날짜데이터파일 저장
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

    private String readfile(String path, String filename) {
        File f = new File(path + filename);
        if(!f.exists()){
            Log.v("filecheck", filename+"not exist");
                    savefile(path, filename, "NULL");
        }

        StringBuffer sb = new StringBuffer();
        Log.v("fileWriter", filename+" reading started");
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
