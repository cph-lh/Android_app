package com.example.myfirstapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.graphics.Path;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.TextView;

public class PopUpFragment extends Fragment {

    private ViewGroup root;
    private FloatingActionButton fab;
    private boolean menuIsOpen, isAnimating;
    private View backgroundOverlay, toolbarOverlay;
    private ObjectAnimator objectAnimator;
    private Animator revealAnimation, hideAnimation;
    private float startX, startY;
    private Toolbar toolbar;
    private AnimatorSet showSet, hideSet;
    private final float scaleUp = 1.5F;
    private final float scaleDown = 1F;
    private final long duration = 1000;
    private final FastOutSlowInInterpolator interpolator = new FastOutSlowInInterpolator();
    private DisplayMetrics mDisplayMetrics;

    public static PopUpFragment newInstance() {
        return new PopUpFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        setDisplayMetrics();

        root = (ViewGroup) inflater.inflate(R.layout.pop_up_fragment, container, false);
        fab = (FloatingActionButton) root.findViewById(R.id.fab);
        backgroundOverlay = root.findViewById(R.id.background_overlay);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbarOverlay = getActivity().findViewById(R.id.toolbar_overlay_dark);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateMenu();
            }
        });
        backgroundOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAnimating) {
                    hideMenu();
                }
            }
        });
        toolbarOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAnimating) {
                    hideMenu();
                }
            }
        });
        return root;
    }

    public void setDisplayMetrics() {
        mDisplayMetrics = getResources().getDisplayMetrics();
    }

    public void animateMenu() {
        if (!isAnimating) {
            if (!menuIsOpen) {
                showMenu();
            } else {
                hideMenu();
            }
        }
        //Do nothing
    }

    public void showMenu() {
        /*isAnimating = true;

        int [] location = new int [2];
        //Arc animation
        //Starts by defining a path for the FAB to move along
        final Path path = new Path();

        //Defines start points
        startX = fab.getX();
        startY = fab.getY();
        path.moveTo(startX, startY);

        //Defines control points and end points for the curve
        float controlX = root.getWidth() / 2 + fab.getWidth();
        float controlY = startY;
        float endX = (root.getWidth() / 2) + (fab.getWidth() / 2);
        float endY = (root.getHeight() / 2) + (fab.getHeight() * 2);
        path.quadTo(controlX, controlY, endX, endY);

        //Moves the FAB along the defined path
        objectAnimator = ObjectAnimator.ofFloat(fab, View.X, View.Y, path);
        objectAnimator.setDuration(duration);
        objectAnimator.setInterpolator(interpolator);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fab.setVisibility(View.GONE);
                menu.setVisibility(View.VISIBLE);
            }
        });

        //Reveal animation
        float endRadius = (float) Math.hypot(menu.getWidth(), menu.getHeight());
        int centerX = menu.getWidth() / 2 + (fab.getWidth() / 2);
        int centerY = menu.getHeight() / 2 + (fab.getHeight() / 2);
        revealAnimation = ViewAnimationUtils.createCircularReveal(menu, centerX, centerY, 168f, endRadius);
        revealAnimation.setInterpolator(interpolator);
        revealAnimation.setDuration(duration);

        //Sets play order and starts the animations
        showSet = new AnimatorSet();
        showSet.play(objectAnimator).before(revealAnimation);
        showSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                //Scales up FAB
                scaleFAB(scaleUp, interpolator, duration);

                //Shows overlay
                backgroundOverlay.setVisibility(View.VISIBLE);
                toolbar.setElevation(0);
                toolbarOverlay.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                menuIsOpen = true;
            }
        });
        showSet.start();*/
    }

    public void hideMenu() {
        /*isAnimating = true;

        //Hides FAB menu
        int centerX = menu.getWidth() / 2 + (fab.getWidth() / 2);
        int centerY = menu.getHeight() / 2 + (fab.getHeight() / 2);
        float startRadius = (float) Math.hypot(menu.getWidth(), menu.getHeight());
        hideAnimation = ViewAnimationUtils.createCircularReveal(menu, centerX, centerY, startRadius, 168f);
        hideAnimation.setInterpolator(interpolator);
        hideAnimation.setDuration(duration);
        hideAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
//                menu.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                //Scales down the FAB
                scaleFAB(scaleDown, interpolator, duration);
            }
        });

        //Reverse arc animation
        //Starts by defining a path for the FAB to move along
        final Path path = new Path();

        //Defines start points
        float newStartX = fab.getX();
        float newStartY = fab.getY();
        path.moveTo(newStartX, newStartY);

        //Defines control points and end points for the curve
        float controlX = root.getWidth() / 2 + fab.getWidth();
        float controlY = startY;
        float endX = startX;
        float endY = startY;
        path.quadTo(controlX, controlY, endX, endY);

        //Moves the FAB along the defined path
        objectAnimator = ObjectAnimator.ofFloat(fab, View.X, View.Y, path);
        objectAnimator.setDuration(duration);
        objectAnimator.setInterpolator(interpolator);
        objectAnimator.setStartDelay(750);

        //Sets play order and starts the animations
        hideSet = new AnimatorSet();
        hideSet.playTogether(hideAnimation, objectAnimator);
        hideSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //Hides overlay
                toolbarOverlay.setVisibility(View.GONE);
                toolbar.setElevation(12);
                backgroundOverlay.setVisibility(View.GONE);
                menuIsOpen = false;
                isAnimating = false;
            }
        });
        hideSet.start();*/
    }

    public void scaleFAB(float constant, Interpolator interpolator, long duration) {
        fab.animate().scaleX(constant).scaleY(constant).setDuration(duration)
                .setInterpolator(interpolator).start();
    }
}
