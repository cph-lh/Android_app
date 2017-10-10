package com.example.myfirstapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Bundle;
import android.provider.Settings;
import android.renderscript.Sampler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TransitionFragment extends Fragment {

    private ViewGroup root;
    private View menu;
    private Button button;
    private TextView text;
    private TextView floaty;
    private FloatingActionButton fab;
    private boolean menuIsOpen, visible, isAnimating;
    private View backgroundOverlay, toolbarOverlay;
    private float startX, startY;
    private Toolbar toolbar;
    private final float scaleUp = 1.5F;
    private final float scaleDown = 1F;
    private final long duration = 1000;
    private final FastOutSlowInInterpolator interpolator = new FastOutSlowInInterpolator();
    private float x;
    private float y;

    public static TransitionFragment newInstance() {
        return new TransitionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        root = (ViewGroup) inflater.inflate(R.layout.transition_fragment, container, false);
        text = (TextView) root.findViewById(R.id.text);
        floaty = (TextView) root.findViewById(R.id.floaty);
        fab = (FloatingActionButton) root.findViewById(R.id.fab);
        menu = root.findViewById(R.id.fab_menu);
        backgroundOverlay = root.findViewById(R.id.background_overlay);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbarOverlay = getActivity().findViewById(R.id.toolbar_overlay_dark);
        button = (Button) root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(root);
                visible = !visible;
                text.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        });

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

    @Override
    public void onStart() {
        super.onStart();
        floaty.setText("x: " + fab.getX() + System.lineSeparator() + " y: " + fab.getY() + "x: " +
                root.getWidth() / 2 + System.lineSeparator() + " y: " + root.getHeight() / 2);

    }

    public void showMenu() {
        isAnimating = true;

        startX = fab.getX();
        startY = fab.getY();
        float endX = button.getX();//root.getWidth() / 2;
        float endY = button.getY();//root.getHeight() / 2;

//        x = startX * (float) Math.cos(Math.toRadians(degrees));
//        y = startY * (float) Math.sin(Math.toRadians(degrees));
//        mMidPoint.x = mStartPoint.x + mStartEndSegment / 2 * (mEndPoint.x - mStartPoint.x) / mStartEndSegment;
//        mMidPoint.y = mStartPoint.y + mStartEndSegment / 2 * (mEndPoint.y - mStartPoint.y) / mStartEndSegment;
        final double initRradius = radius(fab.getX(), button.getX(), fab.getY(), button.getY());
        final float middleX = (float) (fab.getX() +initRradius * (button.getX()-fab.getX())/(initRradius*2));
        final float middleY = (float) (fab.getY() +initRradius * (button.getY()-fab.getY())/(initRradius*2));
        ValueAnimator arcAnimator = ValueAnimator.ofFloat(1, 100);
        arcAnimator.setDuration(2000);
        arcAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                double degree = degree(fab.getX(), button.getX(), fab.getY(), button.getY());
                double radius = radius(fab.getX(), button.getX(), fab.getY(), button.getY())*animation.getAnimatedFraction();
                fab.setX((float) (middleX + radius * Math.cos((Math.toRadians(degree)))));
                fab.setY((float) (middleY- radius * Math.sin((Math.toRadians(degree)))));
                floaty.setText("x: " + fab.getX() + System.lineSeparator() + " y: " + fab.getY());
                fab.setBackgroundTintList(ColorStateList.valueOf(ColorUtils.blendARGB(
                        ContextCompat.getColor(getActivity(), R.color.colorAccent), Color.WHITE,
                        animation.getAnimatedFraction())));
                Log.w("üpdate", Math.cos((Math.toRadians(degree)))+" gjhdfghfj "+Math.sin((Math.toRadians(degree))) );
            }
        });
        arcAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fab.setVisibility(View.GONE);
                menu.setVisibility(View.VISIBLE);
            }
        });


        //Reveal animation
        float endRadius = (float) Math.hypot(menu.getWidth(), menu.getHeight());
        int centerX = menu.getWidth() / 2;
        int centerY = menu.getHeight() / 2;
        Animator revealAnimation = ViewAnimationUtils.createCircularReveal(menu, centerX, centerY, 125f, endRadius);
        revealAnimation.setInterpolator(interpolator);
        revealAnimation.setDuration(duration);
        revealAnimation.setStartDelay(700);
        revealAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                fab.setVisibility(View.GONE);
                menu.setVisibility(View.VISIBLE);
            }
        });

        //Sets play order and starts the animations
        AnimatorSet showSet = new AnimatorSet();
        showSet.playTogether(arcAnimator, revealAnimation);
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
        showSet.start();
    }

    private double degree(float xStart, float xFinish, float yStart, float yFinish) {
        double degrees = Math.toDegrees(Math.atan2(yStart - yFinish, xStart - xFinish));
        if (degrees < 0) {
            degrees += 360;
        }
        return degrees;
    }

    private double radius(float xStart, float xFinish, float yStart, float yFinish) {
        float firstPart = Math.max(xStart - xFinish, xFinish - xStart);
        float secondtPart = Math.max(yStart - yFinish, yFinish - yStart);
        return Math.sqrt(Math.pow(firstPart, 2) + Math.pow(secondtPart, 2)) / 2;
    }

    public void hideMenu() {
        isAnimating = true;

        //Hides FAB menu
        int centerX = menu.getWidth() / 2;
        int centerY = menu.getHeight() / 2;
        float startRadius = (float) Math.hypot(menu.getWidth(), menu.getHeight());
        Animator hideAnimation = ViewAnimationUtils.createCircularReveal(menu, centerX, centerY, startRadius, 120f);
        hideAnimation.setInterpolator(interpolator);
        hideAnimation.setDuration(duration);
        hideAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                menu.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                //Scales down the FAB
                scaleFAB(scaleDown, interpolator, duration);
            }
        });

        final Path path = new Path();

        //Defines start points
        float newStartX = fab.getX();
        float newStartY = fab.getY();
        path.moveTo(newStartX, newStartY);

        //Defines control points and end points for the curve
        float controlX = root.getWidth() / 2;
        float controlY = startY;
        float endX = startX;
        float endY = startY;
        path.quadTo(controlX, controlY, endX, endY);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fab, View.X, View.Y, path);
        objectAnimator.setDuration(duration);
        objectAnimator.setInterpolator(interpolator);
        objectAnimator.setStartDelay(750);
        objectAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fab.setBackgroundTintList(ColorStateList.valueOf(ColorUtils.blendARGB(
                        Color.WHITE, ContextCompat.getColor(getActivity(), R.color.colorAccent),
                        animation.getAnimatedFraction())));
            }
        });

        //Sets play order and starts the animations
        AnimatorSet hideSet = new AnimatorSet();
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
        hideSet.start();
    }

    //Scales the FAB up/down during the animations
    public void scaleFAB(float constant, Interpolator interpolator, long duration) {
        fab.animate().scaleX(constant).scaleY(constant).setDuration(duration)
                .setInterpolator(interpolator).start();
    }
}
