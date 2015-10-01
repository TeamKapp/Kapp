package project.anonymous.kapp;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by FullOfOrange on 2015. 5. 6..
 */
public class customDialog extends Dialog{
    
    DatePicker datePicker;
    EditText editText;
    Button canbtn, setbtn;
    private String ddayname;
    
    public customDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.d_datepicker);
        datePicker = (DatePicker)findViewById(R.id.datepicker);
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth){
            }
        });
        editText = (EditText)findViewById(R.id.edittext);
        canbtn = (Button)findViewById(R.id.btn1);
        setbtn = (Button)findViewById(R.id.btn2);
        
    }
    public String ddayName(){
        ddayname = editText.getText().toString();
        return ddayname;
    }
    public long ddayDate(){
        Calendar c = Calendar.getInstance();
        c.set(datePicker.getYear(), datePicker.getMonth(),datePicker.getDayOfMonth());
        return c.getTimeInMillis();
    }

    public void setOkClickListener(View.OnClickListener okClickListener){
        setbtn.setOnClickListener(okClickListener);
    }
    public void setCancleClickListener(View.OnClickListener cancleClickListener){
        canbtn.setOnClickListener(cancleClickListener);
    }
}
