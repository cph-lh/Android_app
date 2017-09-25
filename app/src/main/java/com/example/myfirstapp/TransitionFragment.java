package com.example.myfirstapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.graphics.Path;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TransitionFragment extends Fragment {

    private ViewGroup root, layout;
    private View menu;
    private Button button;
    private TextView text;
    private FloatingActionButton fab;
    private boolean isHidden, visible;
    private View backgroundOverlay, toolbarOverlay;
    private ObjectAnimator objectAnimator;
    private Animator delayAnimation, revealAnimation, hideAnimation;
    private float x1, y1, x2, y2, x3, y3, x4, y4;
    private Toolbar toolbar;
    private AnimatorSet showSet, hideSet;

    public static TransitionFragment newInstance() {
        return new TransitionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        root = (ViewGroup) inflater.inflate(R.layout.transition_fragment, container, false);
        layout = (ViewGroup) root.findViewById(R.id.menu_layout);
        text = (TextView) root.findViewById(R.id.text);
        fab = (FloatingActionButton) root.findViewById(R.id.fab);
        menu = root.findViewById(R.id.fab_menu);
        backgroundOverlay = root.findViewById(R.id.background_overlay);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbarOverlay = getActivity().findViewById(R.id.toolbar_overlay);
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
                hideMenu();
            }
        });
        toolbarOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideMenu();
            }
        });

        return root;
    }

    public void animateMenu() {
        if (!isHidden) {
            showMenu();
        } else {
            hideMenu();
        }
    }

    public void showMenu() {
        //Arc animation
        final Path path = new Path();
        x1 = fab.getX();
        y1 = fab.getY();
        path.moveTo(x1, y1);
        float controlX = root.getWidth() / 2 + fab.getWidth();
        float controlY = y1;
        x2 = (root.getWidth() / 2) + (fab.getWidth() / 2);
        y2 = (root.getHeight() / 2) + fab.getHeight() * 2;
        path.quadTo(controlX, controlY, x2, y2);
        objectAnimator = ObjectAnimator.ofFloat(fab, View.X, View.Y, path);
        objectAnimator.setDuration(200);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fab.setVisibility(View.GONE);
            }
        });

        //Reveal animation
        float endRadius = (float) Math.hypot(menu.getWidth(), menu.getHeight());
        int centerX = (fab.getLeft() + fab.getRight()) / 2;
        int centerY = (fab.getTop() + fab.getBottom()) / 2;
        delayAnimation = ViewAnimationUtils.createCircularReveal(menu, (int) fab.getX(), (int) fab.getY(), 0, 0);
        delayAnimation.setDuration(200);
        revealAnimation = ViewAnimationUtils.createCircularReveal(menu, menu.getWidth() / 2, menu.getHeight() / 2, 150f, endRadius);


        //Sets play order and starts the animations
        showSet = new AnimatorSet();
        showSet.playSequentially(delayAnimation, revealAnimation);
        showSet.play(objectAnimator).with(delayAnimation);
        menu.setVisibility(View.VISIBLE);
        showSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                //Shows overlay
                backgroundOverlay.setVisibility(View.VISIBLE);
                toolbar.setElevation(0);
                toolbarOverlay.setVisibility(View.VISIBLE);
            }
        });
        showSet.start();

        isHidden = true;
    }

    public void hideMenu() {
        //Hide animation
        float startRadius = (float) Math.hypot(menu.getWidth(), menu.getHeight());
        hideAnimation = ViewAnimationUtils.createCircularReveal(menu, menu.getWidth() / 2, menu.getHeight() / 2, startRadius, 0);

        //Reverses arc animation
        final Path path = new Path();
        x3 = fab.getX();
        y3 = fab.getY();
        path.moveTo(x3, y3);
        float cX = root.getWidth() / 2 + fab.getWidth();
        float cY = y1;
        x4 = x1;
        y4 = y1;
        path.quadTo(cX, cY, x4, y4);
        objectAnimator = ObjectAnimator.ofFloat(fab, View.X, View.Y, path);
        objectAnimator.setDuration(200);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                menu.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
            }
        });

        //Sets play order and starts the animations
        hideSet = new AnimatorSet();
        hideSet.playSequentially(hideAnimation, objectAnimator);
        hideSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //Hides overlay
                toolbarOverlay.setVisibility(View.GONE);
                toolbar.setElevation(12);
                backgroundOverlay.setVisibility(View.GONE);
            }
        });
        hideSet.start();

        isHidden = false;
    }
}
