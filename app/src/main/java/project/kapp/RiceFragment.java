package project.kapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import lib.FileIO;
import lib.Meallib;
import lib.general;
import lib.netload;

@SuppressLint("SdCardPath")
public class RiceFragment extends Fragment implements View.OnClickListener {

    String address = "http://hes.cne.go.kr/sts_sci_md00_003.do?schulCode=N100000131&schulCrseScCode=4&schulKndScCode=04&schMmealScCode=0";
    String kongjugopath ="/sdcard/Android/kongjugoappData/";

    Calendar cal = Calendar.getInstance();
    static boolean ran = false;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    int pd = cal.get(Calendar.DATE);
    int pm = cal.get(Calendar.MONTH);
    int py = cal.get(Calendar.YEAR);
    private int mShortAnimationDuration;

    private GestureDetector gestureDetector;

    private TextView tv, riceMorning, riceLaunch, riceDinner, ricedate;
    private Button prevBtn, nextBtn, seemoreExitBtn;
    private RelativeLayout seemorerice_layout;
    private ViewGroup seedaterice;
    private View view;

    public static RiceFragment newInstance(int num) {
        RiceFragment f = new RiceFragment();
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v("KappLog", "RiceFragment Started");

        view = inflater.inflate(R.layout.f_rice, container, false);
        gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener());

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        outputmanage(pd,pm,py);


        Button yest = (Button) view.findViewById(R.id.next_day);
        yest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (pd < general.yoon(pm)) {
                    pd++;
                }
                else{
                    if(pm ==12){
                        pd = 1;
                        pm = 1;
                        py++;
                    }
                    else{
                        pd = 1;
                        pm++;
                    }
                }    
                outputmanage(pd,pm,py);
            }
        });

        Button toda = (Button) view.findViewById(R.id.today_date);
        toda.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pd = cal.get(Calendar.DATE);
                pm = cal.get(Calendar.MONTH);
                outputmanage(pd,pm,py);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }


    void outputmanage(int date, int month, int year) {
        tv = (TextView) view.findViewById(R.id.rice_mor_txt);
        tv.setVerticalScrollBarEnabled(true);
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(taskstartmanager(address, 0, date, year, month + 1).replace('\n', ' '));


        tv = (TextView) view.findViewById(R.id.rice_lau_txt);
        tv.setVerticalScrollBarEnabled(true);
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(taskstartmanager(address, 1, date, year, month + 1).replace('\n', ' '));

        tv = (TextView) view.findViewById(R.id.rice_din_txt);
        tv.setVerticalScrollBarEnabled(true);
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(taskstartmanager(address, 2, date, year, month + 1).replace('\n', ' '));

        tv = (TextView) view.findViewById(R.id.today_date);
        tv.setText(year+"년 "+(month+1)+"월 "+date+"일");
   }

    int schm;
    String parsedsave[][];

    String taskstartmanager(String addr, int mtime, int mdate, int myear, int imonth) {
        FileIO fio = new FileIO();
        String mmonth = adds0tomonth(imonth);
        fio.filepathcheck(kongjugopath);
        Context ct = getActivity();
        netload nl = new netload();
        if (nl.Checknetwork(ct) && !(schm == imonth)) {// 네트워크 체크<--true로 락걸어둠


            Log.v("Thread Address", addr + "&schYm=" + myear + mmonth + "/n/n");
            Meallib ml = new Meallib(addr + "&schYm=" + myear + mmonth, imonth);
            ml.start();
            try {
                ml.join();// 불러옴을 확인
                Log.v("alskdg","asdf");
                Log.v("meallib ended", ml.parsed[1][1]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            fio.savefile(kongjugopath, "Monthcheck.txt", mmonth);

            for (int i = 0; i < 3; i++) {// 파일로 저장
                for (int j = 1; j < general.yoon(imonth) + 1; j++) {

                    Log.v("meallib ended", j + "일" + ml.parsed[i][j]);
                    fio.savefile(kongjugopath, i + "," + mmonth + "월" + j + "일" + ".txt", ml.parsed[i][j]);
                }
            }
            schm = imonth;
            parsedsave = ml.parsed;
            return ml.parsed[mtime][mdate];

        } else {// 네트워크 문제
            if (schm==imonth) {
                return parsedsave[mtime][mdate];
            }


            if (fio.readfile(kongjugopath, "Monthcheck.txt").equals(mmonth)) {// 현재 유효한 데이터인지
                // 확인함

                return fio.readfile(kongjugopath, mtime + "," + mmonth + "월" + mdate + "일" + ".txt");
            } else {
                return "인터넷 연결이 필요합니다.";


            }
        }
    }

    private String adds0tomonth(int month) {
        if (month < 10)
            return "0" + month;
        else
            return month + "";

    }




}

