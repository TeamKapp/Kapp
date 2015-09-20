package project.kapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by FullOfOrange on 15. 9. 20..
 */
public class GotoMoreActivity extends ActionBarActivity{

    private Toolbar toolbar;
    private String getFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        getFragment = intent.getStringExtra("FramgentName");
        if(getFragment == "학사일정"){}
        else if(getFragment == "단어"){WordFragment }
        else if(getFragment == "공지사항"){}
        else if(getFragment == "학교소개"){}
        else if(getFragment == "개발자정보"){}
    }
}
