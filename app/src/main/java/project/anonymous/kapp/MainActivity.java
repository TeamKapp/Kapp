package project.anonymous.kapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

/**
 * 06.05 문제 발생(ActionBarActivity 관련)
 * -> ActionBarActivity는 deprecated이므로,
 * AppCompatActivity로 변경하여 사용할 것.
 * 출처 -> http://apphappy.tistory.com/10
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    Button  ttablebtn, ddaybtn, morebtn;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MainSlideAdapter(getSupportFragmentManager()));

        ttablebtn = (Button)findViewById(R.id.main_ttable_btn);
        ttablebtn.setOnClickListener(this);
        ddaybtn = (Button)findViewById(R.id.main_dday_btn);
        ddaybtn.setOnClickListener(this);
        morebtn = (Button)findViewById(R.id.main_more_btn);
        morebtn.setOnClickListener(this);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                TabbtnColordefault(position-1);
                TabbtnColordefault(position+1);
                TabbtnColorselected(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        }

    public static class MainSlideAdapter extends FragmentPagerAdapter {

        public MainSlideAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return TimetableFragment.newInstance(position);
                case 1:
                    return DdayFragment.newInstance(position);
                case 2:
                    return MoreFragment.newInstance(position);
                default:
                    return null;
            }
        }
    }
    public void TabbtnColordefault(int a){
         if (a==0){
            ttablebtn.setTextColor(getResources().getColor(R.color.ddgray));}
        else if (a==1){
             ddaybtn.setTextColor(getResources().getColor(R.color.ddgray));}
        else if (a==2){
            morebtn.setTextColor(getResources().getColor(R.color.ddgray));}
        else{
        }
    }
    public void TabbtnColorselected(int a){
        if (a==0){
            ttablebtn.setTextColor(getResources().getColor(R.color.blue));}
        else if (a==1){
            ddaybtn.setTextColor(getResources().getColor(R.color.blue));}
        else if (a==2){
            morebtn.setTextColor(getResources().getColor(R.color.blue));}
    }
    @Override
    public void onClick(View view){
        TabbtnColordefault(pager.getCurrentItem());
        switch(view.getId()) {
            case R.id.main_ttable_btn:
                pager.setCurrentItem(0);
                break;
            case R.id.main_dday_btn:
                pager.setCurrentItem(1);
                break;
            case R.id.main_more_btn:
                pager.setCurrentItem(2);
                break;
        }
        TabbtnColorselected(pager.getCurrentItem());
    }

}
