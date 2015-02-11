package project.kapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        ActionBar actionBar = getActionBar();

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
                    return WordFragment.newInstance(position);
                default:
                    return null;
            }
        }
    }
}
