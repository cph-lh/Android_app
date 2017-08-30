package com.examble.myfirstapp;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class FavoriteFragment extends Fragment {

    protected View root;
    protected ImageView favoriteIcon;
    protected float screenHeight;
    protected Button button;
    protected boolean down;
    protected boolean running;
    protected float newPosition;
    protected float startPosition;
    protected Animator animator;
    protected ValueAnimator vAnimator;
    static final String SAVED_TEXT = "buttonText";
    static final String SAVED_VISIBILITY = "imageVisibility";
    private static final String TAG = "print";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        super.onCreateView(inflater, container, savedState);

        getScreenHeight();
        root = inflater.inflate(R.layout.favorite_fragment, container, false);
        favoriteIcon = (ImageView) root.findViewById(R.id.favorite);
        button = (Button) root.findViewById(R.id.favorite_button);

        startPosition = favoriteIcon.getTranslationY();
        if (savedState != null) {
            button.setText(savedState.getString(SAVED_TEXT));
            if (savedState.getBoolean(SAVED_VISIBILITY)) {
                favoriteIcon.setVisibility(View.VISIBLE);
            } else {
                favoriteIcon.setVisibility(View.INVISIBLE);
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().toString().equals("FAVORITE")) {
                    if (down) {
                        favoriteIcon.setVisibility(View.INVISIBLE);
                        favoriteIcon.setTranslationY(0);
                        down = false;
                    }
                    circularReveal();
                } else {
                    reverseCircularReveal();
                }
            }
        });
        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (down) {
                    favoriteUpAnimation();
                } else if (running && !down) {
                    //Do nothing
                } else {
                    favoriteDownAnimation();
                }
            }
        });
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        if (button != null) {
            savedState.putString(SAVED_TEXT, button.getText().toString());
        }
        if (favoriteIcon != null) {
            savedState.putBoolean(SAVED_VISIBILITY, favoriteIcon.isShown());
        }
    }

    //Gets device screenheight
    public void getScreenHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
    }

    //Reveal animation
    public void circularReveal() {
        int x = favoriteIcon.getWidth() / 2;
        int y = 0;
        float endRadius = (float) Math.hypot(favoriteIcon.getWidth(), favoriteIcon.getHeight());

        animator = ViewAnimationUtils.createCircularReveal(favoriteIcon, x, y, 0, endRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        favoriteIcon.setVisibility(View.VISIBLE);
        animator.start();
        button.setText("UNFAVORITE");
    }

    //Hide animation
    public void reverseCircularReveal() {
        int x = favoriteIcon.getWidth() / 2;
        int y = 0;
        float startRadius = (float) favoriteIcon.getWidth();

        animator = ViewAnimationUtils.createCircularReveal(favoriteIcon, x, y, startRadius, 0);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                favoriteIcon.setVisibility(View.INVISIBLE);
                button.setText("FAVORITE");
                if (running) {
                    vAnimator.cancel();
                    favoriteIcon.setTranslationY(0);

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
    }

    //Imageview down-animation
    public void favoriteDownAnimation() {
        running = true;
        vAnimator = ValueAnimator.ofFloat(startPosition, screenHeight / 2);

        vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                favoriteIcon.setTranslationY(value);
            }
        });
        vAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                running = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                down = true;
                running = false;
                newPosition = favoriteIcon.getTranslationY();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                running = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        vAnimator.setDuration(1500L);
        vAnimator.setInterpolator(new LinearInterpolator());
        vAnimator.start();
    }

    //Imageview up-animation
    public void favoriteUpAnimation() {
        vAnimator = ValueAnimator.ofFloat(newPosition, startPosition);

        vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                favoriteIcon.setTranslationY(value);
            }
        });
        vAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                down = false;
                running = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                down = false;
                running = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                running = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        vAnimator.setDuration(1500L);
        vAnimator.setInterpolator(new LinearInterpolator());
        vAnimator.start();
    }
}
