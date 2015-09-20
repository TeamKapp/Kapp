package project.kapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by FullOfOrange on 15. 9. 20..
 */
public class MoreFragment extends Fragment{

    private ListView listview;
    private ArrayAdapter<String> arrayAdapter;
    private String[] datalist = {"학사일정","단어","공지사항","학교소개","개발자정보"};

    public static MoreFragment newInstance(int num) {
        MoreFragment f = new MoreFragment();
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.f_more, container, false);

        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,datalist);
        listview = (ListView)view.findViewById(R.id.morelist);
        listview.setAdapter(arrayAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GotoMoreActivity.class);
                Bundle extras = new Bundle();
                extras.putString("FragmentName",((TextView)view).getText().toString());
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        return view;
    }
}
