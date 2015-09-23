package project.kapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by FullOfOrange on 15. 9. 21..
 */
public class BScheduleFragment extends Fragment{

    private RecyclerView bsrecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_bschedule, container, false);

        bsrecycler = (RecyclerView) view.findViewById(R.id.bs_recyclerview);
        

        return view;
    }
}
