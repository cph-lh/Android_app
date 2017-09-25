package com.example.myfirstapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.Random;

public class BounceDogeAnimationFragment extends Fragment {

    private View doge;
    private float startPositionY, startPositionX;
    private long animationDuration;
    private boolean running;
    private int color;
    private View root, background;
    private ObjectAnimator animY, animX;
    private Animator animator;
    private AnimatorSet animatorSet;
    static final String SAVED_RUN_STATUS = "runStatus";
    static final String SAVED_ANIMATION_DURATION = "animationDuration";
    static final String SAVED_BACKGROUND_COLOR = "color";
    private static final String TAG = "print";

    public static BounceDogeAnimationFragment newInstance() {
        return new BounceDogeAnimationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        super.onCreateView(inflater, container, savedState);

        //Inflates the layout for this fragment
        root = inflater.inflate(R.layout.bounce_doge_fragment, container, false);
        doge = root.findViewById(R.id.doge);
        background = root.findViewById(R.id.reveal_background);

        startPositionY = doge.getY();
        startPositionX = doge.getX();
        animationDuration = 1000;

        if (savedState != null) {
            color = savedState.getInt(SAVED_BACKGROUND_COLOR);
            root.setBackgroundColor(color);
            running = savedState.getBoolean(SAVED_RUN_STATUS);
            if (running) {
                animationDuration = savedState.getLong(SAVED_ANIMATION_DURATION);
                startAnimation(animationDuration);
            }
        }

        //Starts and cancels the animation
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                doge.getLocationInWindow(location);
                Log.w("Doge location", location[0] + " " + location[1]);
                if (running) {
                    animatorSet.cancel();
                } else {
                    startAnimation(animationDuration);
                    Random r = new Random();
                    color = Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256));
                    background.setBackgroundColor(color);
                    playBackgroundAnimation();
                }
            }
        });
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
//        if (doge != null) {
            //savedState Doge position
//        }
        savedState.putLong(SAVED_ANIMATION_DURATION, animationDuration);
        savedState.putBoolean(SAVED_RUN_STATUS, running);
        savedState.putInt(SAVED_BACKGROUND_COLOR, color);
    }

    //Starts the doge animation
    public void startAnimation(long duration) {
        Log.i(TAG, "STARTED");
        int rootHeight = root.getHeight();
        int rootWidth = root.getWidth();
        int minus = new Random().nextBoolean() ? 1 : -1;

        //Y
        float randomY = new Random().nextInt(rootHeight) - doge.getHeight();
        float valueY = minus * randomY;
        float destinationY = Math.max(valueY, Math.min(0, rootHeight));
        //X
        float randomX = new Random().nextInt(rootWidth) - doge.getWidth();
        float valueX = minus * randomX;
        float destinationX =  Math.max(valueX, Math.min(0, rootWidth));

        animY = ObjectAnimator.ofFloat(doge, "y", doge.getY(), destinationY);
        Log.i(TAG,"DestY: "+destinationY);
        animX = ObjectAnimator.ofFloat(doge, "x", doge.getX(), destinationX);
        Log.i(TAG,"DestX: "+destinationX);

        animatorSet = new AnimatorSet();
        animatorSet.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                running = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (BounceDogeAnimationFragment.this.isAdded()) {
                    Log.i(TAG, "ENDED");
                    Log.i(TAG,"CurrY: "+doge.getY());
                    Log.i(TAG,"CurrX: "+doge.getX());
                    if (!running) {
                        //DO NOTHING
                    } else {
                        startAnimation(animationDuration);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                running = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        animatorSet.setDuration(duration);
        animatorSet.play(animY).with(animX);
        animatorSet.start();

    }

    //Starts the background color animation
    public void playBackgroundAnimation() {
        int x = background.getWidth() / 2;
        int y = 0;
        float startRadius = 0;
        float endRadius = (float) Math.hypot(background.getWidth(), background.getHeight());

        animator = ViewAnimationUtils.createCircularReveal(background, x, y, startRadius, endRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(1000);
        background.setVisibility(View.VISIBLE);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                background.setVisibility(View.INVISIBLE);
                root.setBackgroundColor(color);
            }
        });
    }
}
