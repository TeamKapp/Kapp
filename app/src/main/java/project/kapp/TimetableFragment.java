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
    public Cursor cursor_timetable;
    public SimpleCursorAdapter TableAdapter_timetable=null;
    Spinner timetable_spinner;
    String[] item_timetable = {"학년반을 선택하세요",
            "1학년1반","1학년2반","1학년3반","1학년4반","1학년5반","1학년6반","1학년7반","1학년8반","1학년9반",
            "2학년1반","2학년2반","2학년3반","2학년4반","2학년5반","2학년6반","2학년7반","2학년8반","2학년9반",
            "3학년1반","3학년2반","3학년3반","3학년4반","3학년5반","3학년6반","3학년7반","3학년8반","3학년9반"
                              };
    String[] name_timetable = new String[] {"set","class1","class2","class3","class4","class5","class6","class7"};

    int[] num_timetable = new int[]{R.id.text1,R.id.text2,R.id.text3,R.id.text4,R.id.text5,R.id.text6,R.id.text7,R.id.text8};

    @SuppressLint("SdCardPath")
    public static final String ROOT_DIR = "/data/data/project.kapp/databases/";

    ProductDBHelper mHelper;

    SharedPreferences ttset;
    SharedPreferences.Editor tteditor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ttset = getActivity().getSharedPreferences("ttset", 0);
        tteditor = ttset.edit();
        
        setDB();
        view = inflater.inflate(R.layout.f_timetable, container, false);
        rl_gv = (RelativeLayout)view.findViewById(R.id.tt_rl1);
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
            public void onNothingSelected(AdapterView<?> parent) {
            }});

        return view;
    }
    public void setTimetable(){
        int position = ttset.getInt("key", 0);
        timetable_spinner.setSelection(position);
        if(position==0)  {}
        else if(position == 1)  {data11(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==2)  {data12(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==3)  {data13(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==4)  {data14(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==5)  {data15(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==6)  {data16(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==7)  {data17(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==8)  {data18(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==9)  {data19(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==10)  {data21(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==11)  {data22(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==12)  {data23(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==13)  {data24(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==14)  {data25(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==15)  {data26(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==16)  {data27(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==17)  {data28(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==18)  {data29(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==19)  {data31(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==20)  {data32(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==21)  {data33(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==22)  {data34(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==23)  {data35(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==24)  {data36(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==25)  {data37(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==26)  {data38(); rl_gv.setVisibility(view.VISIBLE);}
        else if(position==27)  {data39(); rl_gv.setVisibility(view.VISIBLE);}


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
    private void data11(){
        cursor_timetable=db_timetable.rawQuery("Select * from onea where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    private void data12(){
        cursor_timetable=db_timetable.rawQuery("Select * from oneb where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    private void data13(){
        cursor_timetable=db_timetable.rawQuery("Select * from onec where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    private void data14(){
        cursor_timetable=db_timetable.rawQuery("Select * from oned where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    private void data15(){
        cursor_timetable=db_timetable.rawQuery("Select * from onee where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    private void data16(){
        cursor_timetable=db_timetable.rawQuery("Select * from onef where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    private void data17(){
        cursor_timetable=db_timetable.rawQuery("Select * from oneg where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    private void data18(){
        cursor_timetable=db_timetable.rawQuery("Select * from oneh where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    private void data19(){
        cursor_timetable=db_timetable.rawQuery("Select * from onei where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }

    @SuppressWarnings("deprecation")
    private void data21(){
        cursor_timetable=db_timetable.rawQuery("Select * from seconda where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    public void data22(){
        cursor_timetable=db_timetable.rawQuery("Select * from secondb where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    public void data23(){
        cursor_timetable=db_timetable.rawQuery("Select * from secondc where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    public void data24(){
        cursor_timetable=db_timetable.rawQuery("Select * from secondd where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    public void data25(){
        cursor_timetable=db_timetable.rawQuery("Select * from seconde where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    public void data26(){
        cursor_timetable=db_timetable.rawQuery("Select * from secondf where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    public void data27(){
        cursor_timetable=db_timetable.rawQuery("Select * from secondg where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    public void data28(){
        cursor_timetable=db_timetable.rawQuery("Select * from secondh where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    public void data29(){
        cursor_timetable=db_timetable.rawQuery("Select * from secondi where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }

    @SuppressWarnings("deprecation")
    private void data31(){
        cursor_timetable=db_timetable.rawQuery("Select * from thirda where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    private void data32(){
        cursor_timetable=db_timetable.rawQuery("Select * from thirdb where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    private void data33(){
        cursor_timetable=db_timetable.rawQuery("Select * from thirdc where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    private void data34(){
        cursor_timetable=db_timetable.rawQuery("Select * from thirdd where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    private void data35(){
        cursor_timetable=db_timetable.rawQuery("Select * from thirde where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    private void data36(){
        cursor_timetable=db_timetable.rawQuery("Select * from thirdf where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    private void data37(){
        cursor_timetable=db_timetable.rawQuery("Select * from thirdg where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    private void data38(){
        cursor_timetable=db_timetable.rawQuery("Select * from thirdh where day='시간표'",null);
        getActivity().startManagingCursor(cursor_timetable);
        final GridView timetable_gridview=(GridView)view.findViewById(R.id.tt_gridview);
        TableAdapter_timetable=new SimpleCursorAdapter(timetable_gridview.getContext(), R.layout.v_tt_list, cursor_timetable, name_timetable,num_timetable);
        timetable_gridview.setAdapter(TableAdapter_timetable);
    }
    @SuppressWarnings("deprecation")
    private void data39(){
        cursor_timetable=db_timetable.rawQuery("Select * from thirdi where day='시간표'",null);
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

