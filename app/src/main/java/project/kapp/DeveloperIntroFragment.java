package project.kapp;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by FullOfOrange on 2015. 7. 16..
 */
public class DeveloperIntroFragment extends Fragment {

    TextView tx;
    ImageView imageView;
    Animation alphaanim,alphaanim2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.f_developerintro, container, false);

        imageView = (ImageView) view.findViewById(R.id.image);
        tx= (TextView) view.findViewById(R.id.anonymous);
        alphaanim= AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_output_anim);
        alphaanim2= AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_input_anim);
        imageView.startAnimation(alphaanim);
        Handler hd=new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                tx.setVisibility(View.VISIBLE);
                tx.startAnimation(alphaanim2);
            }
        }, 2000);

        return view;
    }
}
