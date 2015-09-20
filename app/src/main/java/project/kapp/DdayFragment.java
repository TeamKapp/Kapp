package project.kapp;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class DdayFragment extends Fragment{
    
    public Calendar calendar, mid1st, last1st, mid2nd, last2nd;
    public String ddaytxt;
    customDialog customdialog;
    TextView d_daysat, d_daysusi, d_daysusitxt, d_daycustom, d_daycustomtxt;
    private int curyear, curmonth, curdate;
    public long curmillis;
    SharedPreferences ddayset;
    SharedPreferences.Editor ddayeditor;

    public static DdayFragment newInstance(int num) {
        DdayFragment f = new DdayFragment();
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ddayset = getActivity().getSharedPreferences("ddayset", 0);
        ddayeditor = ddayset.edit();
        
        calendar = Calendar.getInstance();
        curyear = calendar.get(Calendar.YEAR);
        curmonth = calendar.get(Calendar.MONTH);
        curdate = calendar.get(Calendar.DATE);
        calendar.set(curyear,curmonth,curdate);
        curmillis = calendar.getTimeInMillis();
        final View view = inflater.inflate(R.layout.f_dday, container, false);
        View.OnClickListener ol = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.dday_llm:
                        customdialog = new customDialog(getActivity());
                        customdialog.setOkClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ddayeditor.putLong("ddaydate", customdialog.ddayDate());
                                ddayeditor.putString("ddaytxt", customdialog.ddayName());
                                ddayeditor.commit();
                                dDayCustom();
                                Toast.makeText(v.getContext(), "설정되었습니다", Toast.LENGTH_SHORT).show();
                                customdialog.dismiss();
                            }
                        });
                        customdialog.setCancleClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                customdialog.dismiss();
                            }
                        });
                        customdialog.show();
                        break;
                }
            }
        };
        RelativeLayout tl = (RelativeLayout) view.findViewById(R.id.dday_llm);
        tl.setOnClickListener(ol);

        d_daysat = (TextView) view.findViewById(R.id.dday_txt_sat);
        d_daysat.setText("D - " + dDaySAT());
        d_daysusi = (TextView) view.findViewById(R.id.dday_txt_susi);
        d_daysusitxt = (TextView) view.findViewById(R.id.dday_txt2);
        d_daycustom = (TextView) view.findViewById(R.id.dday_txt_m);
        d_daycustomtxt = (TextView) view.findViewById(R.id.dday_txtm);
        dDayCustom();
        dDaySusi();
        return view;
    }

    public void dDayCustom(){
        ddaytxt = ddayset.getString("ddaytxt", "D-day를 설정해주세요.");
        long d_day2 = ((ddayset.getLong("ddaydate", curmillis) - curmillis))/(1000*24*60*60);
        d_daycustom.setText("D - " + d_day2);
        d_daycustomtxt.setText(ddaytxt);
    }
    public void dDaySusi(){
        long curmilliss = curmillis/(1000*24*60*60);
        mid1st = Calendar.getInstance();
        mid2nd = Calendar.getInstance();
        last1st = Calendar.getInstance();
        last2nd = Calendar.getInstance();
        mid1st.set(2015, 8, 23);
        last1st.set(2015, 11, 14);
        long mid1stlong = (mid1st.getTimeInMillis())/(1000*24*60*60);
        last1st.getTimeInMillis();
        long last1stlong = (last1st.getTimeInMillis())/(1000*24*60*60);
        if(curmilliss < mid1stlong){
            d_daysusi.setText("D - " + (mid1stlong-curmilliss));
            d_daysusitxt.setText("중간고사");
        }
        else if (curmilliss == mid1stlong){
            d_daysusi.setText("D - day");
            d_daysusitxt.setText("중간고사");
        }
        else if (curmilliss < last1stlong){
            d_daysusi.setText("D - " + (last1stlong-curmilliss));
            d_daysusitxt.setText("기말고사");
        }
        else if (curmilliss == last1stlong){
            d_daysusi.setText("D - day");
            d_daysusitxt.setText("기말고사");
        }
        else {
            d_daysusi.setText("D-?");
            d_daysusitxt.setText("방학");
        }
    }
    
    public long dDaySAT(){
        Calendar sattime = Calendar.getInstance();
        sattime.set(2015, 10, 12); //0을 1월로 인식 - 2월=1, 3월=2...
        long d_day1 = (sattime.getTimeInMillis() - curmillis)/(1000*24*60*60);
        if(d_day1>=0) {}
        else {d_day1 = 0;}
        return d_day1;
    }
}
