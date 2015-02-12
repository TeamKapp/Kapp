package project.kapp;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    FragmentTransaction ft;
    FragmentManager fm;

    RelativeLayout rl_drawer;

    Button Nbtn1, Nbtn2, Nbtn3, Nbtn4, Nbtn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        rl_drawer = (RelativeLayout)findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(toggle);

        Nbtn1 = (Button)rl_drawer.findViewById(R.id.btn1);
        Nbtn2 = (Button)rl_drawer.findViewById(R.id.btn2);
        Nbtn3 = (Button)rl_drawer.findViewById(R.id.btn3);
        Nbtn4 = (Button)rl_drawer.findViewById(R.id.btn4);
        Nbtn5 = (Button)rl_drawer.findViewById(R.id.btn5);
        for(int i=0; i<5; i++ ){
            findViewById(R.id.btn1+i).setOnClickListener(this); }

        fm = getSupportFragmentManager();

        if (findViewById(R.id.container) != null) {
            if (savedInstanceState != null) {return;}
            RiceFragment riceFragment = new RiceFragment();
            riceFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.container, riceFragment).commit();
        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(View v) {
        ft = getSupportFragmentManager().beginTransaction();
        switch (v.getId()){
            case R.id.btn1:
                RiceFragment riceFragment = new RiceFragment();
                ft.replace(R.id.container, riceFragment);
                break;
            case R.id.btn2:
                TimetableFragment timetableFragment = new TimetableFragment();
                ft.replace(R.id.container, timetableFragment);
                break;
            case R.id.btn3:
                DdayFragment ddayFragment = new DdayFragment();
                ft.replace(R.id.container, ddayFragment);
                break;
            case R.id.btn4:
                WordFragment wordFragment = new WordFragment();
                ft.replace(R.id.container, wordFragment);
                break;

        }
        ft.addToBackStack(null);
        ft.commit();
        drawerLayout.closeDrawers();
    }
}
