package com.examble.myfirstapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.Random;

public class LaunchRocketAnimationFragment extends Fragment {

    protected View mRocket;
    protected View mDoge;
    protected float screenHeight;
    protected float destination;
    protected float startPosition = 0;
    protected long animationDuration = 3000;
    protected boolean running;
    protected int color;
    protected View root;
    protected View background;
    protected ValueAnimator vAnimator;
    protected Animator animator;
    static final String SAVED_START_POSITION = "startPosition";
    static final String SAVED_RUN_STATUS = "runStatus";
    static final String SAVED_ANIMATION_DURATION = "animationDuration";
    static final String SAVED_BACKGROUND_COLOR = "color";
    private static final String TAG = "print";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        super.onCreateView(inflater, container, savedState);

        getScreenHeight();
        root = inflater.inflate(R.layout.launch_rocket_fragment, container, false);
        mRocket = root.findViewById(R.id.rocket);
        mDoge = root.findViewById(R.id.doge);
        background = root.findViewById(R.id.reveal_background);

        if (savedState != null) {
            color = savedState.getInt(SAVED_BACKGROUND_COLOR);
            root.setBackgroundColor(color);
            startPosition = savedState.getFloat(SAVED_START_POSITION) * screenHeight;
            mRocket.setTranslationY(startPosition);
            mDoge.setTranslationY(startPosition);
            running = savedState.getBoolean(SAVED_RUN_STATUS);
            if (running) {
                animationDuration = savedState.getLong(SAVED_ANIMATION_DURATION);
                playRocketAnimation(animationDuration);
            }
        }
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    vAnimator.pause();
                    running = false;
                    animationDuration = animationDuration - vAnimator.getCurrentPlayTime();
                    startPosition = mRocket.getTranslationY();
                } else {
                    playRocketAnimation(animationDuration);
                    if (mRocket.getTranslationY() == 0) {
                        Random r = new Random();
                        color = Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256));
                        background.setBackgroundColor(color);
                        playBackgroundAnimation();
                    }
                }
            }
        });
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        if (mRocket != null) {
            savedState.putFloat(SAVED_START_POSITION, mRocket.getTranslationY() / screenHeight);
        }
        savedState.putLong(SAVED_ANIMATION_DURATION, animationDuration);
        savedState.putBoolean(SAVED_RUN_STATUS, running);
        savedState.putInt(SAVED_BACKGROUND_COLOR, color);
    }

    public void getScreenHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
    }

    public void playRocketAnimation(long duration) {
        destination = -screenHeight + (screenHeight / 7);
        vAnimator = ValueAnimator.ofFloat(startPosition, destination);
//        Log.i(TAG, "fullscreen " + -screenHeight);
//        Log.i(TAG, "destination " + destination);
        vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                mRocket.setTranslationY(value);
//                Log.i(TAG, "rocket position" + mRocket.getTranslationY());
                mDoge.setTranslationY(value);
            }
        });
        vAnimator.setDuration(duration);
        vAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                running = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (LaunchRocketAnimationFragment.this.isAdded()) {
                    running = false;
                    startPosition = 0;
                    animationDuration = 3000;
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
        vAnimator.start();
    }

    public void playBackgroundAnimation() {
        int x = background.getWidth() / 2;
        int y = 0;
        float startRadius = 0;
        float endRadius = (float) Math.hypot(background.getWidth(), background.getHeight());

        animator = ViewAnimationUtils.createCircularReveal(background, x, y, startRadius, endRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(3000);
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
