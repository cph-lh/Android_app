package com.examble.myfirstapp;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LaunchRocketAnimationFragment extends Fragment {

    protected View mRocket;
    protected View mDoge;
    protected float screenHeight;
    protected float startPosition;
    protected long animationDuration;
    protected boolean running;
    protected View root;
    protected ObjectAnimator oAnimator;
    protected ValueAnimator vAnimator;
    protected AnimatorSet animatorSet;
    static final String SAVED_START_POSITION = "startPosition";
    static final String SAVED_RUN_STATUS = "runStatus";
    private static final String TAG = "print";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        super.onCreateView(inflater, container, savedState);

        getScreenHeight();
        root = inflater.inflate(R.layout.launch_rocket_fragment, container, false);
        mRocket = root.findViewById(R.id.rocket);
        mDoge = root.findViewById(R.id.doge);
        startPosition = 0;
        animationDuration = 3000;

        if (savedState != null) {
            running = savedState.getBoolean(SAVED_RUN_STATUS);
            if (running) {
                startPosition = savedState.getFloat(SAVED_START_POSITION) * screenHeight;
                mRocket.setTranslationY(startPosition);
                mDoge.setTranslationY(startPosition);
                startAnimation(animationDuration);
            }
        }
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    animatorSet.pause();
                    running = false;
                    animationDuration = animationDuration - vAnimator.getCurrentPlayTime();
                    startPosition = mRocket.getTranslationY();
                } else {
                    startAnimation(animationDuration);
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
        savedState.putBoolean(SAVED_RUN_STATUS, running);
    }

    public void getScreenHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
    }

    public void startAnimation(long duration) {
        oAnimator = ObjectAnimator.ofObject(root, "backgroundColor",
                new ArgbEvaluator(),
                ContextCompat.getColor(getActivity(), R.color.background),
                ContextCompat.getColor(getActivity(), R.color.black));
        oAnimator.setDuration(duration);

        vAnimator = ValueAnimator.ofFloat(startPosition, -screenHeight + (screenHeight / 7));
        vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                mRocket.setTranslationY(value);
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
        //vAnimator.start();
        animatorSet = new AnimatorSet();
        animatorSet.play(vAnimator).with(oAnimator);
        animatorSet.start();
    }
}
