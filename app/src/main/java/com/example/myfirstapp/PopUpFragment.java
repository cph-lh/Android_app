package com.example.myfirstapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ScrollView;

import static android.R.attr.endX;

public class PopUpFragment extends Fragment {

    private ViewGroup root;
    private View menu;
    private FloatingActionButton fab;
    private boolean menuIsOpen, isAnimating;
    private ScrollView sv;
    private static boolean animationStarted;
    private View backgroundOverlay, toolbarOverlay;
    private float startX, startY, endX, controlX;
    private Toolbar toolbar;
    private final float scaleUp = 1.5F;
    private final float scaleDown = 1F;
    private final long duration = 600;
    private final FastOutSlowInInterpolator interpolator = new FastOutSlowInInterpolator();
    static final String MENU_STATUS = "menuStatus";
    static final String MENU_VISIBILITY = "menuVisibility";
    static final String FAB_VISIBILITY = "fabVisibility";
    static final String B_OVERLAY_VISIBILITY = "backgroundOverlayVisibility";
    static final String T_OVERLAY_VISIBILITY = "toolbarOverlayVisibility";
    static final String TOOLBAR_ELEVATION = "toolbarElevation";

    public static PopUpFragment newInstance() {
        return new PopUpFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        root = (ViewGroup) inflater.inflate(R.layout.pop_up_fragment, container, false);
        fab = (FloatingActionButton) root.findViewById(R.id.fab);
        menu = root.findViewById(R.id.fab_menu);
        sv = (ScrollView) root.findViewById(R.id.scrollView);
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

//        if (savedInstanceState != null) {
//            menuIsOpen = savedInstanceState.getBoolean(MENU_STATUS);
//            toolbar.setElevation(savedInstanceState.getFloat(TOOLBAR_ELEVATION));
//
//            if (savedInstanceState.getInt(FAB_VISIBILITY) == 0) {
//                fab.setVisibility(View.VISIBLE);
//            } else fab.setVisibility(View.GONE);
//
//            //Menu visibility
//            if (savedInstanceState.getInt(MENU_VISIBILITY) == 0) {
//                menu.setVisibility(View.VISIBLE);
//            } else menu.setVisibility(View.GONE);
//
//            if (savedInstanceState.getInt(B_OVERLAY_VISIBILITY) == 0) {
//                backgroundOverlay.setVisibility(View.VISIBLE);
//            } else backgroundOverlay.setVisibility(View.GONE);
//
//            if (savedInstanceState.getInt(T_OVERLAY_VISIBILITY) == 0) {
//                toolbarOverlay.setVisibility(View.VISIBLE);
//            } else toolbarOverlay.setVisibility(View.GONE);
//        }

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
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        savedState.putBoolean(MENU_STATUS, menuIsOpen);
        savedState.putInt(FAB_VISIBILITY, fab.getVisibility());
        savedState.putInt(MENU_VISIBILITY, menu.getVisibility());
        savedState.putInt(B_OVERLAY_VISIBILITY, backgroundOverlay.getVisibility());
        savedState.putInt(T_OVERLAY_VISIBILITY, toolbarOverlay.getVisibility());
        savedState.putFloat(TOOLBAR_ELEVATION, toolbar.getElevation());
    }

    //Animates the FAB to show a menu
    public void showMenu() {
        isAnimating = true;
        sv.setVisibility(View.VISIBLE);

        //Gets margins of the FAB
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) fab.getLayoutParams();
        float bottomMargin = layoutParams.bottomMargin;
        float rightMargin = layoutParams.rightMargin;
        float leftMargin = layoutParams.leftMargin;

        //Gets layout direction and screen orientation
        Configuration config = getResources().getConfiguration();

        //Start values change depending on layout direction (in rare cases RTL is used)
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            startX = fab.getWidth() / 2 - leftMargin;
            startY = root.getHeight() - fab.getHeight() - bottomMargin;
        } else {
            startX = root.getWidth() - fab.getWidth() - rightMargin;
            startY = root.getHeight() - fab.getHeight() - bottomMargin;
        }

        //Defines start points for the Path
        final Path path = new Path();
        path.moveTo(startX, startY);

        //Defines control points and end points for the curve
        float controlY = startY;
        float endY = root.getHeight() * 0.5f;

        //X values change depending on screen orientation (1 = portrait)
        if (config.orientation == 1) {
            controlX = root.getWidth() * 0.5f;
            endX = root.getWidth() * 0.5f;
        } else {
            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                controlX = root.getWidth() * 0.25f;
                endX = root.getWidth() * 0.25f;
            } else {
                controlX = root.getWidth() * 0.75f;
                endX = root.getWidth() * 0.75f;
            }
        }
        path.quadTo(controlX, controlY, endX, endY);

        //Moves the FAB along the defined path
        ObjectAnimator pathAnimator = ObjectAnimator.ofFloat(fab, View.X, View.Y, path);
        pathAnimator.setDuration(duration);
        pathAnimator.setInterpolator(interpolator);
        pathAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fab.setBackgroundTintList(ColorStateList.valueOf(ColorUtils.blendARGB(
                        ContextCompat.getColor(getActivity(), R.color.colorAccent), Color.WHITE,
                        animation.getAnimatedFraction())));
                if (animation.getAnimatedFraction() > 0.7f && !animationStarted) {
                    animationStarted = true;
                    //Reveal animation
                    float endRadius = (float) Math.hypot(menu.getWidth(), menu.getHeight());
                    int centerX = (int) (Math.abs(fab.getX() - menu.getX()));
                    int centerY = (int) (Math.abs(fab.getY() - menu.getY()));
                    Animator revealAnimator = ViewAnimationUtils.createCircularReveal(menu, centerX, centerY, 120f, endRadius);
                    revealAnimator.setInterpolator(interpolator);
                    revealAnimator.setDuration(duration);
                    revealAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            fab.setVisibility(View.GONE);
                            menu.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            animationStarted = false;
                        }
                    });
                    revealAnimator.start();
                }
            }
        });

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(fab, View.ALPHA, 1f, 0.1f);
        alphaAnimator.setDuration(duration);

        //Sets play order and starts the animations
        AnimatorSet showSet = new AnimatorSet();
        showSet.playTogether(pathAnimator, alphaAnimator);
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

    //Animates the menu back into a FAB
    public void hideMenu() {
        isAnimating = true;

        //Detects layout direction and screen orientation
        Configuration config = getResources().getConfiguration();

        final Path path = new Path();

        //Defines start points for the Path
        float newStartX = fab.getX();
        float newStartY = fab.getY();
        path.moveTo(newStartX, newStartY);

        //Defines control points and end points for the curve
        float controlY = startY;
        float endX = startX;
        float endY = startY;

        //ControlX value depends on screen orientation
        if (config.orientation == 1) {
            controlX = root.getWidth() * 0.5f;
        } else if (config.orientation == 2) {
            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                controlX = root.getWidth() * 0.25f;
            } else {
                controlX = root.getWidth() * 0.75f;
            }
        }
        path.quadTo(controlX, controlY, endX, endY);

        final ObjectAnimator pathAnimator = ObjectAnimator.ofFloat(fab, View.X, View.Y, path);
        pathAnimator.setDuration(1000);
        pathAnimator.setInterpolator(interpolator);
        pathAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fab.setBackgroundTintList(ColorStateList.valueOf(ColorUtils.blendARGB(
                        Color.WHITE, ContextCompat.getColor(getActivity(), R.color.colorAccent),
                        animation.getAnimatedFraction())));
                if (animation.getAnimatedFraction() > 0.6f && !animationStarted) {
                    animationStarted = true;
                    //Hides FAB menu
                    int centerX = (int) (Math.abs(fab.getX() - menu.getX()));
                    int centerY = (int) (Math.abs(fab.getY() - menu.getY()));
                    float startRadius = (float) Math.hypot(menu.getWidth(), menu.getHeight());
                    Animator hideAnimator = ViewAnimationUtils.createCircularReveal(menu, centerX, centerY, startRadius, 120f);
                    hideAnimator.setInterpolator(interpolator);
                    hideAnimator.setDuration(500);
                    hideAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            menu.setVisibility(View.GONE);
                            pathAnimator.resume();
                            fab.setVisibility(View.VISIBLE);
                            //Scales down the FAB
                            scaleFAB(scaleDown, interpolator, duration);
                        }
                    });
                    hideAnimator.start();
                    sv.setVisibility(View.INVISIBLE);
                    pathAnimator.pause();
                }
            }
        });

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(fab, View.ALPHA, 0.1f, 1f);
        alphaAnimator.setDuration(duration);
        alphaAnimator.setStartDelay(300);

        //Sets play order and starts the animations
        AnimatorSet hideSet = new AnimatorSet();
        hideSet.playTogether(pathAnimator, alphaAnimator);
        hideSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //Hides overlay
                toolbarOverlay.setVisibility(View.GONE);
                toolbar.setElevation(12);
                backgroundOverlay.setVisibility(View.GONE);
                animationStarted = false;
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