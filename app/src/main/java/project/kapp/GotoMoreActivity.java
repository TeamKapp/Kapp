package project.kapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

/**
 * Created by FullOfOrange on 15. 9. 20..
 */
public class GotoMoreActivity extends ActionBarActivity{

    private Toolbar toolbar;
    String getFragment;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_gotomore);

        toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        getFragment = getIntent().getExtras().getString("FragmentName");
        int getFragmentNum = getIntent().getExtras().getInt("FragmentNum");
        if(getFragmentNum==0){
            BScheduleFragment bScheduleFragment = new BScheduleFragment();
            fragmentTransaction.add(R.id.moreframe, bScheduleFragment);
        }
        else if(getFragmentNum==1){
            WordFragment wordFragment = new WordFragment();
            fragmentTransaction.add(R.id.moreframe, wordFragment).commit();
        }
        else if(getFragmentNum==2){
            IntroduceFragment introduceFragment = new IntroduceFragment();
            fragmentTransaction.add(R.id.moreframe, introduceFragment).commit();
        }
        else if(getFragmentNum==3){
            WordFragment wordFragment = new WordFragment();
            fragmentTransaction.add(R.id.moreframe, wordFragment).commit();
        }
        else if(getFragmentNum==4){
            DeveloperIntroFragment developerintroFragment = new DeveloperIntroFragment();
            fragmentTransaction.add(R.id.moreframe, developerintroFragment).commit();
        }
        getSupportActionBar().setTitle(getFragment);
    }
}
