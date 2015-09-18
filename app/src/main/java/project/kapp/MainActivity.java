package project.kapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    Toolbar toolbar;
    Button ricebtn, ttablebtn, ddaybtn, morebtn;
    ViewPager pager;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MainSlideAdapter(getSupportFragmentManager()));

        ricebtn = (Button)findViewById(R.id.main_rice_btn);
        ricebtn.setOnClickListener(this);
        ricebtn.setSelected(true);
        ttablebtn = (Button)findViewById(R.id.main_ttable_btn);
        ttablebtn.setOnClickListener(this);
        ddaybtn = (Button)findViewById(R.id.main_dday_btn);
        ddaybtn.setOnClickListener(this);
        morebtn = (Button)findViewById(R.id.main_more_btn);
        morebtn.setOnClickListener(this);
        }

    public static class MainSlideAdapter extends FragmentPagerAdapter {

        public MainSlideAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 4;
        }

        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return RiceFragment.newInstance(position);
                case 1:
                    return TimetableFragment.newInstance(position);
                case 2:
                    return DdayFragment.newInstance(position);
                case 3:
                    return MoreFragment.newInstance(position);
                default:
                    return null;
            }
        }
    }
    @Override
    public void onClick(View view){
        switch(view.getId()) {
            case R.id.main_rice_btn:
                pager.setCurrentItem(0);
                ricebtn.setSelected(true);
                ttablebtn.setSelected(false);
                ddaybtn.setSelected(false);
                morebtn.setSelected(false);
                break;
            case R.id.main_ttable_btn:
                pager.setCurrentItem(1);
                ricebtn.setSelected(false);
                ttablebtn.setSelected(true);
                ddaybtn.setSelected(false);
                morebtn.setSelected(false);
                break;
            case R.id.main_dday_btn:
                pager.setCurrentItem(2);
                ricebtn.setSelected(false);
                ttablebtn.setSelected(false);
                ddaybtn.setSelected(true);
                morebtn.setSelected(false);
                break;
            case R.id.main_more_btn:
                pager.setCurrentItem(3);
                ricebtn.setSelected(false);
                ttablebtn.setSelected(false);
                ddaybtn.setSelected(false);
                morebtn.setSelected(true);
                break;
        }
    }
}
