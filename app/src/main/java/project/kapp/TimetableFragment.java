package project.kapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TimetableFragment extends Fragment {

    View view;

    RelativeLayout rl_gv;

    public SQLiteDatabase db_timetable;
    public Cursor cursor_timetable;
    public SimpleCursorAdapter TableAdapter_timetable=null;

    String[] item_timetable = {"학년반을 선택하세요","2학년1반","2학년2반","2학년3반","2학년4반","2학년5반","2학년6반","2학년7반","2학년8반","2학년9반"};
    String[] name_timetable = new String[] {"set","class1","class2","class3","class4","class5","class6","class7"};

    int[] num_timetable = new int[]{R.id.text1,R.id.text2,R.id.text3,R.id.text4,R.id.text5,R.id.text6,R.id.text7,R.id.text8};

    @SuppressLint("SdCardPath")
    public static final String ROOT_DIR = "/data/data/project.kapp/databases/";

    ProductDBHelper mHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setDB();

        view = inflater.inflate(R.layout.f_timetable, container, false);

        rl_gv = (RelativeLayout)view.findViewById(R.id.tt_rl1);

        mHelper=new ProductDBHelper(getActivity().getApplicationContext());
        db_timetable=mHelper.getWritableDatabase();

        final Spinner timetable_spinner = (Spinner) view.findViewById(R.id.tt_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, item_timetable);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        timetable_spinner.setAdapter(adapter);
        timetable_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

                timetable_spinner.getSelectedItem();
                if(position<=0)  {rl_gv.setVisibility(view.GONE);}
                else if(position<=1)  {data21(); rl_gv.setVisibility(view.VISIBLE);}
                else if(position<=2)  {data22(); rl_gv.setVisibility(view.VISIBLE);}
                else if(position<=3)  {data23(); rl_gv.setVisibility(view.VISIBLE);}
                else if(position<=4)  {data24(); rl_gv.setVisibility(view.VISIBLE);}
                else if(position<=5)  {data25(); rl_gv.setVisibility(view.VISIBLE);}
                else if(position<=6)  {data26(); rl_gv.setVisibility(view.VISIBLE);}
                else if(position<=7)  {data27(); rl_gv.setVisibility(view.VISIBLE);}
                else if(position<=8)  {data28(); rl_gv.setVisibility(view.VISIBLE);}
                else if(position<=9)  {data29(); rl_gv.setVisibility(view.VISIBLE);}
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }});

        return view;
    }
    public void setDB() {
        File folder = new File(ROOT_DIR);
        if(folder.exists()) {}
        else {folder.mkdirs();}
        AssetManager assetManager = getResources().getAssets();
        File outfile = new File(ROOT_DIR+"TimeTable.db");
        InputStream is = null;
        FileOutputStream fo = null;
        long filesize = 0;

        try{
            is = assetManager.open("TimeTable.db", AssetManager.ACCESS_BUFFER);
            filesize = is.available();

            if (outfile.length() >= 0) {
                byte[] tempdata = new byte[(int) filesize];
                is.read(tempdata);
                is.close();
                outfile.createNewFile();
                fo = new FileOutputStream(outfile);
                fo.write(tempdata);
                fo.close();}
            else{Toast.makeText(getActivity(), "db있음", Toast.LENGTH_LONG).show();}}

        catch (IOException e) {Toast.makeText(getActivity(), "db이동실패", Toast.LENGTH_LONG).show();}
    }

    private void nodata() {

    }
    @SuppressWarnings("deprecation")
    private void data21(){
        cursor_timetable=db_timetable.rawQuery("Select * from twoa where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }

    @SuppressWarnings("deprecation")
    public void data22(){
        cursor_timetable=db_timetable.rawQuery("Select * from twob where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }

    @SuppressWarnings("deprecation")
    public void data23(){
        cursor_timetable=db_timetable.rawQuery("Select * from twoc where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }

    @SuppressWarnings("deprecation")
    public void data24(){
        cursor_timetable=db_timetable.rawQuery("Select * from twod where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }

    @SuppressWarnings("deprecation")
    public void data25(){
        cursor_timetable=db_timetable.rawQuery("Select * from twoe where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }

    @SuppressWarnings("deprecation")
    public void data26(){
        cursor_timetable=db_timetable.rawQuery("Select * from twof where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }

    @SuppressWarnings("deprecation")
    public void data27(){
        cursor_timetable=db_timetable.rawQuery("Select * from twog where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }

    @SuppressWarnings("deprecation")
    public void data28(){
        cursor_timetable=db_timetable.rawQuery("Select * from twoh where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }

    @SuppressWarnings("deprecation")
    public void data29(){
        cursor_timetable=db_timetable.rawQuery("Select * from twoi where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }

    class ProductDBHelper extends SQLiteOpenHelper{
        public ProductDBHelper(Context context) {super(context, "TimeTable.db", null, 1);}
        public void onCreate(SQLiteDatabase db) {}
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    }
}

