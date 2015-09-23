package project.kapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    Toolbar toolbar;
    private RiceFragment riceFragment = new RiceFragment();
    private TimetableFragment timetableFragment = new TimetableFragment();
    private DdayFragment ddayFragment = new DdayFragment();
    private WordFragment wordFragment = new WordFragment();
    private IntroduceFragment introduceFragment = new IntroduceFragment();
    private DeveloperIntroFragment developintroFragment = new DeveloperIntroFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MainSlideAdapter(getSupportFragmentManager()));



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

    }

}
