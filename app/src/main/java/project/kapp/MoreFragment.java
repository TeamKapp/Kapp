package project.kapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MoreFragment extends Fragment implements View.OnClickListener{

    public static MoreFragment newInstance(int position) {

        MoreFragment f = new MoreFragment();
        Bundle b = new Bundle();
        b.putInt("position", position);

        f.setArguments(b);

        return f;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.f_more, container, false);

        Button btnNotice = (Button) view.findViewById(R.id.more_notice_btn);
        btnNotice.setOnClickListener(this);

        return view;
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.more_notice_btn):
                Intent intent = new Intent(getActivity(), NoticeActivity.class);
                startActivity(intent);
                break;
        }
    }

}