package project.kapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
    public static int DB_VERSION = 1;
    public Cursor cursor_timetable;
    public SimpleCursorAdapter TableAdapter_timetable=null;
    Spinner timetable_spinner;
    String[] item_timetable = {"학년반을 선택하세요",
            "1학년1반","1학년2반","1학년3반","1학년4반","1학년5반","1학년6반","1학년7반","1학년8반","1학년9반",
            "2학년1반","2학년2반","2학년3반","2학년4반","2학년5반","2학년6반","2학년7반","2학년8반","2학년9반",
            "3학년1반","3학년2반","3학년3반","3학년4반","3학년5반","3학년6반","3학년7반","3학년8반","3학년9반"};
    String[] name_timetable = new String[] {"set","class1","class2","class3","class4","class5","class6","class7"};

    int[] num_timetable = new int[]{R.id.text1,R.id.text2,R.id.text3,R.id.text4,R.id.text5,R.id.text6,R.id.text7,R.id.text8};

    @SuppressLint("SdCardPath")
    public static final String ROOT_DIR = "/data/data/project.kapp/databases/";

    ProductDBHelper mHelper;

    SharedPreferences ttset;
    SharedPreferences.Editor tteditor;

    public static TimetableFragment newInstance(int num) {
        TimetableFragment f = new TimetableFragment();
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ttset = getActivity().getSharedPreferences("ttset", 0);
        tteditor = ttset.edit();
        
        setDB();
        view = inflater.inflate(R.layout.f_timetable, container, false);
        rl_gv = (RelativeLayout)view.findViewById(R.id.tt_rl1);
        rl_gv.setVisibility(view.GONE);
        mHelper=new ProductDBHelper(getActivity().getApplicationContext());
        db_timetable=mHelper.getWritableDatabase();

        timetable_spinner = (Spinner) view.findViewById(R.id.tt_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, item_timetable);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setTimetable();
        timetable_spinner.setAdapter(adapter);
        timetable_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                tteditor.putInt("key", position);
                tteditor.commit();
                setTimetable();
            }
            public void onNothingSelected(AdapterView<?> parent) {}});
        return view;
    }

    public void setTimetable(){
        int position = ttset.getInt("key", 0);
        timetable_spinner.setSelection(position);
        if(position==0)  {}
        else if(position == 1)  {datas(11); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==2)  {datas(12); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==3)  {datas(13); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==4)  {datas(14); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==5)  {datas(15); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==6)  {datas(16); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==7)  {datas(17); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==8)  {datas(18); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==9)  {datas(19); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==10)  {datas(21); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==11)  {datas(22); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==12)  {datas(23); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==13)  {datas(24); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==14)  {datas(25); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==15)  {datas(26); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==16)  {datas(27); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==17)  {datas(28); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==18)  {datas(29); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==19)  {datas(31); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==20)  {datas(32); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==21)  {datas(33); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==22)  {datas(34); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==23)  {datas(35); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==24)  {datas(36); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==25)  {datas(37); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==26)  {datas(38); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==27)  {datas(39); rl_gv.setVisibility(view.VISIBLE);}
    }

    @SuppressWarnings("deprecation")
    private void datas(int datanum){
        String string = null;
        switch(datanum){
            case 11: string = "onea"; break;
            case 12: string = "oneb"; break;
            case 13: string = "onec"; break;
            case 14: string = "oned"; break;
            case 15: string = "onee"; break;
            case 16: string = "onef"; break;
            case 17: string = "oneg"; break;
            case 18: string = "oneh"; break;
            case 19: string = "onei"; break;
            case 21: string = "seconda"; break;
            case 22: string = "secondb"; break;
            case 23: string = "secondc"; break;
            case 24: string = "secondd"; break;
            case 25: string = "seconde"; break;
            case 26: string = "secondf"; break;
            case 27: string = "secondg"; break;
            case 28: string = "secondh"; break;
            case 29: string = "secondi"; break;
            case 31: string = "threea"; break;
            case 32: string = "threeb"; break;
            case 33: string = "threec"; break;
            case 34: string = "threed"; break;
            case 35: string = "threee"; break;
            case 36: string = "threef"; break;
            case 37: string = "threeg"; break;
            case 38: string = "threeh"; break;
            case 39: string = "threei"; break;
            default: string = ""; break;
        }
        cursor_timetable=db_timetable.rawQuery("Select * from "+ string +" where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
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

    class ProductDBHelper extends SQLiteOpenHelper{
        public ProductDBHelper(Context context) {super(context, "TimeTable.db", null, DB_VERSION);}
        public void onCreate(SQLiteDatabase db) {}
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onCreate(db_timetable);
        }
    }
}

