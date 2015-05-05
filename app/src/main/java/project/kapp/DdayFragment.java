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

import java.util.Calendar;

public class DdayFragment extends Fragment{

    public static DdayFragment newInstance(int position) {

        DdayFragment f = new DdayFragment();
        Bundle b = new Bundle();
        b.putInt("position", position);

        f.setArguments(b);

        return f;
    }
    TextView d_daysat, d_daysusi;
    private int curyear, curmonth, curdate, ddayyear, ddaymonth, ddaydate;
    SharedPreferences ddayset;
    SharedPreferences.Editor ddayeditor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ddayset = getActivity().getSharedPreferences("ddayset", 0);
        ddayeditor = ddayset.edit();
        
        Calendar calendar = Calendar.getInstance();
        curyear = calendar.get(Calendar.YEAR);
        curmonth = calendar.get(Calendar.MONTH);
        curdate = calendar.get(Calendar.DATE);

        final View view = inflater.inflate(R.layout.f_dday, container, false);
        View.OnClickListener ol = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.dday_ll2:
                        DatePickerDialog dialog = new DatePickerDialog(getActivity(), dateListener, curyear, curmonth, curdate);
                        dialog.show();
                        dDayCustom();
                        break;
                }
            }
        };
        RelativeLayout tl = (RelativeLayout) view.findViewById(R.id.dday_ll2);
        tl.setOnClickListener(ol);
        
        d_daysat = (TextView) view.findViewById(R.id.dday_txt_sat);
        d_daysat.setText("D - " + dDaySAT());
        d_daysusi = (TextView) view.findViewById(R.id.dday_txt_test);
        dDayCustom();
        return view;
    }

    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    ddayyear=year;
                    ddaymonth=monthOfYear;
                    ddaydate=dayOfMonth;
                    ddayeditor.putInt("year",ddayyear);
                    ddayeditor.putInt("month",ddaymonth);
                    ddayeditor.putInt("date",ddaydate);
                    ddayeditor.commit();
                    dDayCustom();
                }
            };
    public void dDayCustom(){
        Calendar customtime = Calendar.getInstance();
        int ddayCustomyear = ddayset.getInt("year", curyear);
        int ddayCustommonth = ddayset.getInt("month", curmonth);
        int ddayCustomdate = ddayset.getInt("date", curdate);
        customtime.set(ddayCustomyear, ddayCustommonth, ddayCustomdate);
        long d_day2 = (customtime.getTimeInMillis() - System.currentTimeMillis())/(1000*24*60*60);
        d_daysusi.setText("D - " + d_day2);
    }
    
    public long dDaySAT(){
        Calendar sattime = Calendar.getInstance();
        sattime.set(2015, 10, 12); //0을 1월로 인식 - 2월=1, 3월=2...
        long d_day1 = (sattime.getTimeInMillis() - System.currentTimeMillis())/(1000*24*60*60);
        if(d_day1>=0) {}
        else {d_day1 = 0;}
        return d_day1;
    }
}
