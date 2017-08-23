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
    protected ImageView imageView;
    protected float screenHeight;
    protected Button button;
    protected boolean down;
    protected float newPosition;
    protected float startPosition;
    static final String SAVED_TEXT = "buttonText";
    static final String SAVED_VISIBILITY = "imageVisibility";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        super.onCreateView(inflater, container, savedState);

        getScreenHeight();
        root = inflater.inflate(R.layout.favorite_fragment, container, false);
        imageView = root.findViewById(R.id.favorite);
        button = root.findViewById(R.id.favorite_button);

        startPosition = imageView.getTranslationY();
        if (savedState != null) {
            button.setText(savedState.getString(SAVED_TEXT));
            if (savedState.getBoolean(SAVED_VISIBILITY)) {
                imageView.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.INVISIBLE);
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().toString().equals("FAVORITE")) {
                    if (down) {
                        imageView.setVisibility(View.INVISIBLE);
                        imageView.setTranslationY(0);
                        down = false;
                    }
                    circularReveal();
                } else {
                    reverseCircularReveal();
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (down) {
                    favoriteUpAnimation();
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
        if (imageView != null) {
            savedState.putBoolean(SAVED_VISIBILITY, imageView.isShown());
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
        int x = imageView.getWidth() / 2;
        int y = 0;
        float endRadius = (float) Math.hypot(imageView.getWidth(), imageView.getHeight());

        Animator animator = ViewAnimationUtils.createCircularReveal(imageView, x, y, 0, endRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        imageView.setVisibility(View.VISIBLE);
        animator.start();
        button.setText("UNFAVORITE");
    }

    //Hide animation
    public void reverseCircularReveal() {
        int x = imageView.getWidth() / 2;
        int y = 0;
        float startRadius = (float) imageView.getWidth();

        Animator animation = ViewAnimationUtils.createCircularReveal(imageView, x, y, startRadius, 0);
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageView.setVisibility(View.INVISIBLE);
                button.setText("FAVORITE");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animation.start();
    }

    //Imageview down-animation
    public void favoriteDownAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(startPosition, screenHeight / 2);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                imageView.setTranslationY(value);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                down = true;
                newPosition = imageView.getTranslationY();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.setDuration(1500L);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    //Imageview up-animation
    public void favoriteUpAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(newPosition, startPosition);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                imageView.setTranslationY(value);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                down = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.setDuration(1500L);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }
}
