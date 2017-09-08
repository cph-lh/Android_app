package com.examble.myfirstapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.Random;

public class LaunchRocketAnimationFragment extends Fragment {

    protected View rocket, doge;
    protected float screenHeight, destination, startPositionY;
    protected long animationDuration;
    protected boolean running;
    protected int color;
    protected View root, background;
    protected ValueAnimator rocketAnimator;
    protected Animator animator;
    static final String SAVED_START_POSITION = "startPositionY";
    static final String SAVED_RUN_STATUS = "runStatus";
    static final String SAVED_ANIMATION_DURATION = "animationDuration";
    static final String SAVED_BACKGROUND_COLOR = "color";
    private static final String TAG = "print";

//    public static LaunchRocketAnimationFragment newInstance() {
//        LaunchRocketAnimationFragment launchRocketAnimationFragment = new LaunchRocketAnimationFragment();
//        return launchRocketAnimationFragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        super.onCreateView(inflater, container, savedState);

        //Inflates the layout for this fragment
        root = inflater.inflate(R.layout.launch_rocket_fragment, container, false);
        rocket = root.findViewById(R.id.rocket);
        doge = root.findViewById(R.id.doge);
        background = root.findViewById(R.id.reveal_background);

        //Gets the device screen height
        getScreenHeight();

        startPositionY = 0;
        animationDuration = 2500;

        if (savedState != null) {
            color = savedState.getInt(SAVED_BACKGROUND_COLOR);
            root.setBackgroundColor(color);
            startPositionY = savedState.getFloat(SAVED_START_POSITION) * screenHeight;
            rocket.setTranslationY(startPositionY);
            doge.setTranslationY(startPositionY);
            running = savedState.getBoolean(SAVED_RUN_STATUS);
            if (running) {
                animationDuration = savedState.getLong(SAVED_ANIMATION_DURATION);
                startRocketAnimation(animationDuration);
            }
        }

        //Starts the rocket animation and the bagground animation when the screen is clicked
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    rocketAnimator.pause();
                    running = false;
                    animationDuration = animationDuration - rocketAnimator.getCurrentPlayTime();
                    startPositionY = rocket.getTranslationY();
                } else {
                    startRocketAnimation(animationDuration);
                    if (rocket.getTranslationY() == 0) {
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

    //Saves important variables on configuration changes (screen orientation etc.)
    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        if (rocket != null) {
            savedState.putFloat(SAVED_START_POSITION, rocket.getTranslationY() / screenHeight);
        }
        savedState.putLong(SAVED_ANIMATION_DURATION, animationDuration);
        savedState.putBoolean(SAVED_RUN_STATUS, running);
        savedState.putInt(SAVED_BACKGROUND_COLOR, color);
    }

    //Gets the device screen height
    public void getScreenHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
    }

    //Starts the rocket animation
    public void startRocketAnimation(long duration) {
        int rootHeight = root.getHeight();
        destination = -rootHeight;

        //Animates the rocket between two values
        rocketAnimator = ValueAnimator.ofFloat(startPositionY, destination);
        rocketAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                rocket.setTranslationY(value);
                doge.setTranslationY(value);
            }
        });
        rocketAnimator.setDuration(duration);
        rocketAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                running = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (LaunchRocketAnimationFragment.this.isAdded()) {
                    running = false;
                    startPositionY = 0;
                    animationDuration = 2500;
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
        rocketAnimator.start();
    }

    //Starts the background color animation
    public void playBackgroundAnimation() {
        int x = background.getWidth() / 2;
        int y = 0;
        float startRadius = 0;
        float endRadius = (float) Math.hypot(background.getWidth(), background.getHeight());

        animator = ViewAnimationUtils.createCircularReveal(background, x, y, startRadius, endRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(animationDuration);
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
