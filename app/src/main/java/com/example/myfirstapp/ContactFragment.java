package com.example.myfirstapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactFragment extends Fragment {

    private String[] names = {"Denmark", "Sweden", "Norway", "Italy", "France", "Germany", "United Kingdom", "Russia"};
    private String[] info = {"Copenhagen", "Stockholm", "Oslo", "Rome", "Paris", "Berlin", "London", "Moscow"};
    private int[] image = {R.drawable.denmark, R.drawable.sweden, R.drawable.norway, R.drawable.italy, R.drawable.france,
            R.drawable.germany, R.drawable.uk, R.drawable.russia};
    private ArrayList<Contact> contactArray;
    private View root, backgroundOverlay, toolbarOverlay;
    private RecyclerView recyclerView;
    private FloatingActionButton fab, fab1, fab2, fab3;
    private TextView text, text1, text2, text3;
    private ContactAdapter adapter;
    private Toolbar toolbar;
    private boolean isOpen;
    private int x, y;
    private long defaultDuration = 200L, shortDuration = 150L;
    final static String SAVED_ARRAY = "contactArray";
    private static final String TAG = "print";
    private boolean isAnimating;

    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //Inflates the layout for this fragment
        root = inflater.inflate(R.layout.contact_list_fragment, container, false);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) root.findViewById(R.id.contact_r_view);
        fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) root.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) root.findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) root.findViewById(R.id.fab3);
        text = (TextView) root.findViewById(R.id.fab_text);
        text1 = (TextView) root.findViewById(R.id.fab1_text);
        text2 = (TextView) root.findViewById(R.id.fab2_text);
        text3 = (TextView) root.findViewById(R.id.fab3_text);
        backgroundOverlay = root.findViewById(R.id.background_overlay);
        toolbarOverlay = getActivity().findViewById(R.id.toolbar_overlay_light);


        //Sets the array to a saved array (if any) or else create a new array
        if (savedInstanceState != null) {
            contactArray = savedInstanceState.getParcelableArrayList(SAVED_ARRAY);
        } else {
            contactArray = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Contact c = new Contact();
                if (i < names.length) {
                    c.setName(names[i]);
                    c.setInfo(info[i]);
                    c.setImageId(image[i]);
                } else {
                    c.setName("Contact " + (i + 1));
                    c.setInfo("Info " + (i + 1));
                    c.setImageId(R.drawable.doge);
                }
                contactArray.add(c);
            }
        }

        //Adds a new list item when the FAB is clicked and displays a Snackbar with the data added
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    hideFABs(defaultDuration);
                }
                Contact c = new Contact();
                c.setName("Contact " + (contactArray.size() + 1));
                c.setInfo("Info " + (contactArray.size() + 1));
                c.setImageId(R.drawable.doge);
                contactArray.add(c);
                adapter.notifyDataSetChanged();
                Snackbar.make(v, "Created new contact: " + c.getName(), Snackbar.LENGTH_SHORT).show();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAnimating && isOpen) {
                    hideFABs(defaultDuration);
                }
                Snackbar.make(v, "FAB 2 selected", Snackbar.LENGTH_SHORT).show();
            }
        });

        //Sets the image of fab3

        fab3.setImageBitmap(textToBitmap("T", 60, Color.WHITE));

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAnimating && isOpen) {
                    hideFABs(defaultDuration);
                }
                Snackbar.make(v, "FAB 3 selected", Snackbar.LENGTH_SHORT).show();
            }
        });

        backgroundOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFABs(defaultDuration);
            }
        });

        toolbarOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFABs(defaultDuration);
            }
        });

        //Hides the FAB(s) on scrolling
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        fab.show();
                        break;
                    default:
                        if (!isAnimating && isOpen) {
                            hideFABs(shortDuration);
                        }
                        fab.hide();
                        break;
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        //Sets the RecyclerView's layout and adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ContactAdapter(contactArray);
        recyclerView.setAdapter(adapter);
        return root;
    }

    //Saves the array on configuration changes (screen orientation etc.)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(SAVED_ARRAY, contactArray);
    }

    //Animates the FABs
    public void animateFAB() {
        if (!isAnimating) {
            if (!isOpen) {
                showFABs();
            } else {
                hideFABs(defaultDuration);
            }
        } //Do nothing
    }

    //Shows the FABs
    public void showFABs() {
        isAnimating  = true;

        fab1.animate().translationY(-getResources().getDimension(R.dimen.fab_distance));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.fab_distance));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.fab_distance));

        //Rotates the FAB
        fab.animate().rotationBy(45).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
               isAnimating = false;
            }
        });

        fab1.setVisibility(View.VISIBLE);
        fab2.setVisibility(View.VISIBLE);
        fab3.setVisibility(View.VISIBLE);

        //Shows FAB-menu background overlay
        toolbar.setElevation(0);
        toolbarOverlay.setVisibility(View.VISIBLE);
        backgroundOverlay.setVisibility(View.VISIBLE);

        text.setVisibility(View.VISIBLE);
        text1.setVisibility(View.VISIBLE);
        text2.setVisibility(View.VISIBLE);
        text3.setVisibility(View.VISIBLE);

        isOpen = true;
    }

    //Hides the FABs with speed depending on the given duration
    public void hideFABs(long duration) {
        isAnimating = true;

        text.setVisibility(View.GONE);
        text1.setVisibility(View.GONE);
        text2.setVisibility(View.GONE);
        text3.setVisibility(View.GONE);

        //Hides the additional FABs
        x = fab1.getWidth() / 2;
        y = fab1.getHeight() / 2;
        float startRadius = (float) fab1.getWidth();
        Animator fab1Hide = ViewAnimationUtils.createCircularReveal(fab1, x, y, startRadius, 0).setDuration(duration);
        Animator fab2Hide = ViewAnimationUtils.createCircularReveal(fab2, x, y, startRadius, 0).setDuration(duration);
        Animator fab3Hide = ViewAnimationUtils.createCircularReveal(fab3, x, y, startRadius, 0).setDuration(duration);

        fab1Hide.start();
        fab2Hide.start();
        fab3Hide.start();
        fab1Hide.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fab1.setVisibility(View.GONE);
            }
        });
        fab2Hide.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fab2.setVisibility(View.GONE);
            }
        });
        fab3Hide.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fab3.setVisibility(View.GONE);
            }
        });

        //Re-rotates the FAB
        fab.animate().rotationBy(-45);

        //Hides FAB-menu background overlay
        toolbarOverlay.setVisibility(View.GONE);
        toolbar.setElevation(12);
        backgroundOverlay.setVisibility(View.GONE);

        //Reset the FABs position
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
            }
        });

        isOpen = false;
    }

    public static Bitmap textToBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setElegantTextHeight(true);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent() + 15f; // ascent() is negative
        int width = (int) (paint.measureText(text) + 10f); // round
        int height = (int) (baseline + paint.descent() + 10f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 5, baseline, paint);
        return image;
    }

//    /*
//        //Show the fabs
//        public void showFAB1(final long duration) {
//
//        //Reveals additional fabs
//        x = fab1.getWidth() / 2;
//        y = fab1.getHeight();
//        revealRadius = (float) Math.hypot(fab1.getWidth(), fab1.getHeight());
//        fab1Reveal = ViewAnimationUtils.createCircularReveal(fab1, x, y, 0, revealRadius).setDuration(duration);
//        fab1Reveal.setInterpolator(new AccelerateDecelerateInterpolator());
//        fab1Reveal.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                showFAB2(duration);
//            }
//        });
//        fab1.setVisibility(View.VISIBLE);
//        fab1Reveal.start();
//        text1.setVisibility(View.VISIBLE);
//    }*/

//    public void showFAB2(final long duration) {
//
//        //Reveals additional fabs
//        x = fab2.getWidth() / 2;
//        y = fab2.getHeight();
//        revealRadius = (float) Math.hypot(fab2.getWidth(), fab2.getHeight());
//        fab2Reveal = ViewAnimationUtils.createCircularReveal(fab2, x, y, 0, revealRadius).setDuration(duration);
//        fab2Reveal.setInterpolator(new AccelerateDecelerateInterpolator());
//        fab2Reveal.setStartDelay(100);
//        fab2Reveal.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                showFAB3(duration);
//            }
//        });
//
//        fab2.setVisibility(View.VISIBLE);
//        fab2Reveal.start();
//        text2.setVisibility(View.VISIBLE);
//    }


//    public void showFAB3(final long duration) {
//
//        //Reveals additional fabs
//        x = fab3.getWidth() / 2;
//        y = fab3.getHeight();
//        revealRadius = (float) Math.hypot(fab3.getWidth(), fab3.getHeight());
//        fab3Reveal = ViewAnimationUtils.createCircularReveal(fab3, x, y, 0, revealRadius).setDuration(duration);
//        fab3Reveal.setInterpolator(new AccelerateDecelerateInterpolator());
//        fab3Reveal.setStartDelay(100);
//
//        fab3.setVisibility(View.VISIBLE);
//        fab3Reveal.start();
//        text3.setVisibility(View.VISIBLE);
//    }
}
