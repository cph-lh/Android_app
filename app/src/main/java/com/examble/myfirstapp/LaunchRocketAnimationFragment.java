package com.examble.myfirstapp;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

public class LaunchRocketAnimationFragment extends Fragment {

    protected View mRocket;
    protected View mDoge;
    protected float mScreenHeight;
    protected float mStartPosition;
    protected boolean running;
    protected View root;
    protected ObjectAnimator oAnimator;
    protected ValueAnimator vAnimator;
    protected AnimatorSet animatorSet;
    static final String SAVED_START_POSITION = "startPosition";
    static final String SAVED_RUN_STATUS = "runStatus";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        super.onCreateView(inflater, container, savedState);

        getScreenHeight();
        root = inflater.inflate(R.layout.launch_rocket_fragment, container, false);
        mRocket = root.findViewById(R.id.rocket);
        mDoge = root.findViewById(R.id.doge);

        if (savedState != null) {
            running = savedState.getBoolean(SAVED_RUN_STATUS);
            if (running) {
                mStartPosition = savedState.getFloat(SAVED_START_POSITION) * mScreenHeight;
                mRocket.setTranslationY(mStartPosition);
                mDoge.setTranslationY(mStartPosition);
                onStartAnimation();
            }
        }
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartPosition = 0;
                onStartAnimation();
                running = true;
            }
        });

        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        if(mRocket != null) {
            savedState.putFloat(SAVED_START_POSITION, mRocket.getTranslationY() / mScreenHeight);
        }
        savedState.putBoolean(SAVED_RUN_STATUS, running);
    }

    public void getScreenHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mScreenHeight = displaymetrics.heightPixels;
    }

    public void onStartAnimation() {
        oAnimator = ObjectAnimator.ofObject(root, "backgroundColor",
                new ArgbEvaluator(),
                ContextCompat.getColor(getActivity(), R.color.background),
                ContextCompat.getColor(getActivity(), R.color.black));
        oAnimator.setDuration(4000L);

        vAnimator = ValueAnimator.ofFloat(mStartPosition, -mScreenHeight);
        vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                mRocket.setTranslationY(value);
                mDoge.setTranslationY(value);
            }
        });
        //vAnimator.setInterpolator(new AccelerateInterpolator(2f));
        vAnimator.setDuration(3000L);
        vAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                running = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (LaunchRocketAnimationFragment.this.isAdded()) {
                    running = false;
                    mStartPosition = 0;
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
        animatorSet = new AnimatorSet();
        animatorSet.play(vAnimator).with(oAnimator);
        animatorSet.start();
    }
}
