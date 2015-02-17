package project.kapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class NoticeFragment extends Fragment {
    public static WordFragment newInstance(int position) {

        WordFragment f = new WordFragment();
        Bundle b = new Bundle();
        b.putInt("position", position);

        f.setArguments(b);

        return f;
    }

    public String[][] wordmean = new String[2][5];// word:0/mean:1
    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.f_word, container, false);

        tv = (TextView) view.findViewById(R.id.txt_word1);
        tv.setText(wordmean[0][0]);
        tv = (TextView) view.findViewById(R.id.txt_word2);
        tv.setText(wordmean[0][1]);
        tv = (TextView) view.findViewById(R.id.txt_word3);
        tv.setText(wordmean[0][2]);
        tv = (TextView) view.findViewById(R.id.txt_word4);
        tv.setText(wordmean[0][3]);
        tv = (TextView) view.findViewById(R.id.txt_word5);
        tv.setText(wordmean[0][4]);

        tv = (TextView) view.findViewById(R.id.txt_mean1);
        tv.setText(wordmean[1][0]);
        tv = (TextView) view.findViewById(R.id.txt_mean2);
        tv.setText(wordmean[1][1]);
        tv = (TextView) view.findViewById(R.id.txt_mean3);
        tv.setText(wordmean[1][2]);
        tv = (TextView) view.findViewById(R.id.txt_mean4);
        tv.setText(wordmean[1][3]);
        tv = (TextView) view.findViewById(R.id.txt_mean5);
        tv.setText(wordmean[1][4]);

        return view;
    }
}
