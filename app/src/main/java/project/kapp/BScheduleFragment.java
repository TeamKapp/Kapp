package project.kapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Scene;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
/**
 * Created by FullOfOrange on 15. 9. 21..
 */
public class BScheduleFragment extends Fragment implements View.OnClickListener{

    private RecyclerView bsrecycler;
    private LinearLayout bstabContainer;
    private GestureDetectorCompat mDetector;
    private Handler handlerForDelay;
    public Button bstabbtn1, bstabbtn2, bstabbtn3, bstabbtn4, bstabbtn5, bstabbtn6;
    private Animation TabOnAnima, TabOutAnima;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_bschedule, container, false);

        TabOnAnima = AnimationUtils.loadAnimation(getActivity(), R.anim.tab_alpha_in_anim);
        TabOutAnima = AnimationUtils.loadAnimation(getActivity(), R.anim.tab_alpha_out_anim);

        bstabContainer = (LinearLayout) view.findViewById(R.id.bs_tab_container);
        bsrecycler = (RecyclerView) view.findViewById(R.id.bs_recyclerview);
        bstabbtn1 = (Button) view.findViewById(R.id.bs_month_txt1);
        bstabbtn2 = (Button) view.findViewById(R.id.bs_month_txt2);
        bstabbtn3 = (Button) view.findViewById(R.id.bs_month_txt3);
        bstabbtn4 = (Button) view.findViewById(R.id.bs_month_txt4);
        bstabbtn5 = (Button) view.findViewById(R.id.bs_month_txt5);
        bstabbtn6 = (Button) view.findViewById(R.id.bs_month_txt6);
        for(int a = 0; a<=5; a++){
            view.findViewById(R.id.bs_month_txt1+a).setOnClickListener(this);
        }
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        Context getActivity = getActivity();


    }
    @Override
    public void onClick(View view){

    }

    public void ClickViewAnimation(View view){

        view.setAnimation(TabOutAnima);
    }

    public void SwipeTabFadeAnimation(View view1, View view2, View view3, View view4, View view5, View view6, int position){
        int delaytime = 100; int animationtime = 300;
        view1.setAnimation(TabOutAnima);
        view2.setAnimation(TabOutAnima);
        view3.setAnimation(TabOutAnima);
        view4.setAnimation(TabOutAnima);
        view5.setAnimation(TabOutAnima);
        view6.setAnimation(TabOutAnima);
        if(position == 0){  //오른쪽 스와이프
            view1.setAnimation(TabOnAnima);
            view2.animate().setStartDelay(delaytime);
            view2.setAnimation(TabOnAnima);
            view2.animate().setStartDelay(animationtime+delaytime);
            view3.setAnimation(TabOnAnima);
            view3.animate().setStartDelay(2*animationtime+delaytime);
            view4.setAnimation(TabOnAnima);
            view4.animate().setStartDelay(3*animationtime+delaytime);
            view5.setAnimation(TabOnAnima);
            view5.animate().setStartDelay(4*animationtime+delaytime);
            view6.setAnimation(TabOnAnima);
            view6.animate().setStartDelay(5*animationtime+delaytime);
        }
        else if(position == 1){  //왼쪽으로 스와이프
            view6.setAnimation(TabOnAnima);
            view2.animate().setStartDelay(delaytime);
            view5.setAnimation(TabOnAnima);
            view5.animate().setStartDelay(animationtime+delaytime);
            view4.setAnimation(TabOnAnima);
            view4.animate().setStartDelay(2*animationtime+delaytime);
            view3.setAnimation(TabOnAnima);
            view3.animate().setStartDelay(3*animationtime+delaytime);
            view2.setAnimation(TabOnAnima);
            view2.animate().setStartDelay(4*animationtime+delaytime);
            view1.setAnimation(TabOnAnima);
            view1.animate().setStartDelay(5*animationtime+delaytime);
        }
        else{
            Log.d("","입력이 없습니다");
        }
    }

}
