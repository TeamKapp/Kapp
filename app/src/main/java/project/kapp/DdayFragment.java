package project.kapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.f_dday, container, false);

        d_daysat = (TextView) view.findViewById(R.id.dday_txt_sat);
        d_daysat.setText("D - " + dDaySAT());
        d_daysusi = (TextView) view.findViewById(R.id.dday_txt_test);
        d_daysusi.setText("D - " + dDaySUSI());

        return view;
    }

    public long dDaySAT(){
        Calendar sattime = Calendar.getInstance();
        sattime.set(2015, 10, 12); //0을 1월로 인식 - 2월=1, 3월=2...
        long d_day1 = (sattime.getTimeInMillis() - System.currentTimeMillis())/(1000*24*60*60);
        if(d_day1>=0) {}
        else {d_day1 = 0;}
        return d_day1;
    }
    public long dDaySUSI(){
        Calendar susitime = Calendar.getInstance();
        susitime.set(2014, 11, 15);
        long d_day2 = (susitime.getTimeInMillis() - System.currentTimeMillis())/(1000*24*60*60);
        if(d_day2>=0) {}
        else {d_day2 = 0;}
        return d_day2;
    }
}
