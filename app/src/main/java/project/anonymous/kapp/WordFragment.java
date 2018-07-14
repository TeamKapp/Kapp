package project.anonymous.kapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import algorithms.netload;
import algorithms.wordlib;

public class WordFragment extends Fragment {

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
        wordext we = new wordext();
        we.start();
        try {
            we.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

    class wordext extends wordlib {
        public wordext() {
            super();
        }

        public void run() {
            netload nl = new netload();
            Context ct = getActivity();
            Boolean cn = nl.Checknetwork(ct);

            netload nw = new netload();
            if (cn) {
                String html = nw.loadhtml(url);
                Log.d(this.getClass().toString(), "html : \n"+html);

                if (checkwordnull(html)) {
                    word = wordparse(html);
                    mean = meanparse(html);
                }
                else{

                    for(int i = 0; i<5; i++){
                        wordmean[1][i] = "필요합니다";
                        wordmean[0][i] = "인터넷 연결이 ";
                    }

                }
                wordmean = turnout();
            }
            return;
        }

        public boolean checkwordnull(String wm) {
            Pattern patwm = Pattern.compile("txt_col");
            Matcher matcherwm;
            matcherwm = patwm.matcher(wm);
            return matcherwm.find();
        }
    }
}
