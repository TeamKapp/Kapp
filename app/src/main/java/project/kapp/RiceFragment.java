package project.kapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

@SuppressLint("SdCardPath")
public class RiceFragment extends Fragment implements View.OnClickListener{

    String address = "http://hes.cne.go.kr/sts_sci_md00_003.do?schulCode=N100000131&schulCrseScCode=4&schulKndScCode=04&schMmealScCode=0";
    String sdcardpath = "/sdcard/大Kongjugoapp/";

    Calendar cal = Calendar.getInstance();
    File dir = new File(sdcardpath);
    static boolean ran = false;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_에이MAX_OFF_PATH = 250;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        if (!dir.exists())
            dir.mkdir();

        view = inflater.inflate(R.layout.f_rice, container, false);

        ricedate = (TextView) view.findViewById(R.id.noti_txt);

        seedaterice = (ViewGroup)view.findViewById(R.id.date_rice);
        seedaterice.setVisibility(view.GONE);

        seemorerice_layout = (RelativeLayout) view.findViewById(R.id.rice_ll_seemore);
        seemorerice_layout.setVisibility(view.GONE);

        seemoreExitBtn = (Button) view.findViewById(R.id.btn_rice_exitseemore);
        seemoreExitBtn.setOnClickListener(this);

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        taskstartmanager(1,1);
        outputmanage(pd);

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
            case (R.id.btn_rice_exitseemore):
                seemorerice_layout.setVisibility(view.GONE);
                break;
        }
    }
    void outputmanage(int date) {
        tv = (TextView) view.findViewById(R.id.rice_mor_txt);
        tv.setText(taskstartmanager(0,date).replace('\n', ' '));

        tv = (TextView) view.findViewById(R.id.rice_lau_txt);
        tv.setText(taskstartmanager(1,date).replace('\n', ' '));

        tv = (TextView) view.findViewById(R.id.rice_din_txt);
        tv.setText(taskstartmanager(2,date).replace('\n', ' '));

    }
    String taskstartmanager(int mtime, int mdate) {
        Context ct = getActivity();
        netload nl =new netload();
        if (!ran && nl.Checknetwork(ct)) {// 네트워크 체크
            Meallib ml = new Meallib(address);
            ml.start();
            try { ml.join();// 불러옴을 확인
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            File d = new File(sdcardpath + "nowday.txt");// 날짜데이터
            FileWriter fw;
            try {
                fw = new FileWriter(d);
                fw.write((cal.get(Calendar.MONTH) + 1) + "");
                fw.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 3; i++) {// 파일로 저장
                for (int j = 1; j < 32; j++) {
                    File f = new File(sdcardpath + i + "," + j + "일" + ".txt");
                    try {
                        fw = new FileWriter(f);
                        fw.write(Meallib.parsed[i][j]);
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            ran = true;
            return Meallib.parsed[mtime][mdate];
        }
        else {// 네트워크 문제
            StringBuffer sb = new StringBuffer();
            try {// 저장일시 읽어들이기
                FileInputStream fis = new FileInputStream(sdcardpath + "nowday.txt");
                int n;
                while ((n = fis.available()) > 0) {
                    byte b[] = new byte[n];
                    if (fis.read(b) == -1)
                        break;
                    sb.append(new String(b));
                }
                fis.close();
            }
            catch (FileNotFoundException e) {
                System.err.println("Could not find file" + sdcardpath + "nowday.txt");
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            if (sb.toString().equals((cal.get(Calendar.MONTH) + 1) + "")) {// 현재 유효한 데이터임을
                // 확인함

                sb.delete(0, sb.length());
                try {// 저장일시 읽어들이기
                    FileInputStream fis = new FileInputStream(sdcardpath + mtime + "," + mdate + "일" + ".txt");
                    int n;
                    while ((n = fis.available()) > 0) {
                        byte b[] = new byte[n];
                        if (fis.read(b) == -1)
                            break;
                        sb.append(new String(b));
                    }
                    fis.close();
                } catch (FileNotFoundException e) {
                    System.err.println("Could not find file" + sdcardpath + "nowday.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return sb.toString();
            } else {
                return "인터넷 연결이 필요합니다.";


            }
        }
    }
    protected int yoon() {
        int thisyear = cal.get(Calendar.YEAR);
        int feb;
        if ((thisyear % 4 == 0) && (thisyear % 100 != 0)
                || (thisyear % 400 == 0)) {
            feb = 29;
        }
        else
            feb = 28;
        switch (cal.get(Calendar.MONTH)+1) {
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

    private void CF_dateon() {
        seedaterice.setAlpha(0f);
        seedaterice.setVisibility(View.VISIBLE);
        seedaterice.animate().alpha(1f).setDuration(mShortAnimationDuration).setListener(null);
    }
    private void CF_dateoff() {

    }
    public static RiceFragment newInstance(int position) {

        RiceFragment f = new RiceFragment();
        Bundle b = new Bundle();
        b.putInt("position", position);

        f.setArguments(b);

        return f;
    }
}
