package project.kapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
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


    int pd = cal.get(Calendar.DATE);
    int pm = cal.get(Calendar.MONTH);
    int py = cal.get(Calendar.YEAR);

    private TextView tv;
    private View view;

    public static RiceFragment newInstance(int num) {
        RiceFragment f = new RiceFragment();
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);
        return f;
    }
    boolean c=true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v("KappLog", "RiceFragment Started");
        removefiles();

        view = inflater.inflate(R.layout.f_rice, container, false);
        outputmanage(pd,pm,py,false);

        taskStartmanager(py, pm,true);
        Button yest = (Button) view.findViewById(R.id.next_day);
        yest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                boolean b;

                if(pd==24)
                    c=true;
                if (pd < general.yoon(pm + 1, py)) {
                    pd++;
                    b =false;
                } else {
                    b =true;
                    if (pm == 12) {
                        pd = 1;
                        pm = 1;
                        py++;
                    } else {
                        pd = 1;
                        pm++;
                    }
                }
                if(pd>23&&c){
                    taskStartmanager(py, pm,false);
                    c=false;
                }
                outputmanage(pd,pm,py,b);
            }
        });

        Button toda = (Button) view.findViewById(R.id.today_date);
        toda.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pd = cal.get(Calendar.DATE);
                pm = cal.get(Calendar.MONTH);
                outputmanage(pd,pm,py,false);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }


    void outputmanage(int date, int month, int year, boolean nextmonth) {
        tv = (TextView) view.findViewById(R.id.rice_mor_txt);
        tv.setVerticalScrollBarEnabled(true);
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(getStringmanager(0, date, year, month + 1,nextmonth).replace('\n', ' '));


        tv = (TextView) view.findViewById(R.id.rice_lau_txt);
        tv.setVerticalScrollBarEnabled(true);
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(getStringmanager(1, date, year, month + 1,nextmonth).replace('\n', ' '));

        tv = (TextView) view.findViewById(R.id.rice_din_txt);
        tv.setVerticalScrollBarEnabled(true);
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(getStringmanager( 2, date, year, month + 1,nextmonth).replace('\n', ' '));

        tv = (TextView) view.findViewById(R.id.today_date);
        tv.setText(year+"년 "+(month+1)+"월 "+date+"일");
   }

Meallib ml = new Meallib();

    void taskStartmanager(int myear, int nimonth, boolean starting){
        nimonth++;
        if(!starting) {
            if (nimonth == 12) {
                nimonth = 1;
                myear++;
            } else {
                nimonth++;
            }
        }
        ml = new Meallib(address + "&schYm=" + myear + adds0tomonth(nimonth), nimonth, myear);
        netload nl = new netload();
        Context ct = getActivity();
        if(ml.isAlive())
            return;
        if (nl.Checknetwork(ct)) {
            ml.start();

        }
        else if(!new File(ml.kongjugopath+1 + "," + myear + "년" + nimonth + "월" + 1 + "일" + ".txt").exists()){
            ml.ifpath = false;
            ml.start();
        }
        else{
                return;
        }
    }

    String getStringmanager(int mtime, int mdate, int myear, int imonth, boolean ifnextmonth) {
        if(ifnextmonth){
            if(ml.isAlive()) {
                try {
                    ml.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        FileIO fio = new FileIO();
        return fio.readfile(kongjugopath, mtime + "," + myear + "년" + imonth + "월" + mdate + "일" + ".txt");

    }

    private String adds0tomonth(int month) {
        if (month < 10)
            return "0" + month;
        else
            return month + "";

    }
    private void removefiles(){
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        if(month ==1){
            year = year-1;
            month = 12;
        }
        else{
            month = month-1;
        }
        Log.i(ml.kongjugopath,"asdf");
        File f = new File(ml.kongjugopath+1 + "," + year + "년" + month + "월" + 1 + "일" + ".txt");
        if(f.exists())
        {
            for (int i = 0; i < 3; i++) {// 파일로 저장
                for (int j = 1; j < general.yoon(month, year) + 1; j++) {

                    Log.v("meallib delete..",""+i+"  "+j);
                    if(new File(ml.kongjugopath+i + "," + year + "년" + month + "월" + j + "일" + ".txt").exists())
                    new File(ml.kongjugopath+i + "," + year + "년" + month + "월" + j + "일" + ".txt").delete();
                }
            }
        }
    }




}

