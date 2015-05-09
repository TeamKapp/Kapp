package project.kapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Date;

/**
 * Created by FullOfOrange on 2015. 5. 6..
 */
public class customDialog extends Dialog implements View.OnClickListener{
    
    DatePicker datePicker;
    EditText editText;
    Button canbtn, setbtn;
    private String ddayname;
    int getyear,getmonth,getdate;
    
    public customDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.d_datepicker);
        
        datePicker = (DatePicker)findViewById(R.id.datepicker);
        datePicker.setSpinnersShown(true);
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth){
                getyear = year;
                getmonth = monthOfYear;
                getdate = dayOfMonth;
            }
        });
        editText = (EditText)findViewById(R.id.edittext);
        canbtn = (Button)findViewById(R.id.btn1);
        setbtn = (Button)findViewById(R.id.btn2);
        canbtn.setOnClickListener(this);
        setbtn.setOnClickListener(this);
        
    }
    public String ddayName(){
        return ddayname;
    }
    
    public void getDate(int a, int b, int c){
        a = getyear;
        b = getmonth;
        c = getdate;
    }
    public void onClick(View v){

    }
}
