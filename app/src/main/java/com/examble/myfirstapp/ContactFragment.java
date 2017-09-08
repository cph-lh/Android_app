package com.examble.myfirstapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactFragment extends Fragment {

    private String[] names = {"Denmark", "Sweden", "Norway", "Italy", "France", "Germany", "United Kingdom", "Russia"};
    private String[] info = {"Copenhagen", "Stockholm", "Oslo", "Rome", "Paris", "Berlin", "London", "Moscow"};
    private int[] image = {R.drawable.denmark, R.drawable.sweden, R.drawable.norway, R.drawable.italy, R.drawable.france,
            R.drawable.germany, R.drawable.uk, R.drawable.russia};
    private ArrayList<Contact> contactArray;
    private View root, fabBackground;
    private RecyclerView recyclerView;
    private FloatingActionButton fab, fab1, fab2, fab3;
    private TextView text1, text2, text3;
    private ContactAdapter adapter;
    private boolean isOpen;
    private int x, y;
    private long defaultDuration = 200L, shortDuration = 150L;
    final static String SAVED_ARRAY = "contactArray";

    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //Inflates the layout for this fragment
        root = inflater.inflate(R.layout.contact_list_fragment, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.contact_r_view);
        fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) root.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) root.findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) root.findViewById(R.id.fab3);
        text1 = (TextView) root.findViewById(R.id.fab1_text);
        text2 = (TextView) root.findViewById(R.id.fab2_text);
        text3 = (TextView) root.findViewById(R.id.fab3_text);
        fabBackground = root.findViewById(R.id.fab_background);

        //Sets the array to a saved array (if any) or else create a new array
        if (savedInstanceState != null) {
            contactArray = savedInstanceState.getParcelableArrayList(SAVED_ARRAY);
        } else {
            contactArray = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                if (i < names.length) {
                    Contact c = new Contact();
                    c.setName(names[i]);
                    c.setInfo(info[i]);
                    c.setImageId(image[i]);
                    contactArray.add(c);
                } else {
                    Contact c = new Contact();
                    c.setName("Contact " + (i + 1));
                    c.setInfo("Info " + (i + 1));
                    c.setImageId(R.drawable.doge);
                    contactArray.add(c);
                }
            }
        }

//        fab.setImageBitmap();

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
                if (isOpen) {
                    hideFABs(defaultDuration);
                }
                Snackbar.make(v, "FAB 2 selected", Snackbar.LENGTH_SHORT).show();
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    hideFABs(defaultDuration);
                }
                Snackbar.make(v, "FAB 3 selected", Snackbar.LENGTH_SHORT).show();
            }
        });

        fabBackground.setOnClickListener(new View.OnClickListener() {
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
                        if (isOpen) {
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
        if (!isOpen) {
            showFABS(shortDuration);
        } else {
            hideFABs(defaultDuration);
        }
    }

    //Shows the FABs with speed depending on the given duration
    public void showFABS(long duration) {
        fab1.animate().translationY(-getResources().getDimension(R.dimen.fab_distance));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.fab_distance));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.fab_distance));

        //Rotates the FAB
        fab.animate().rotationBy(45);

        fab1.setVisibility(View.VISIBLE);
        fab2.setVisibility(View.VISIBLE);
        fab3.setVisibility(View.VISIBLE);
        fabBackground.setVisibility(View.VISIBLE);
        text1.setVisibility(View.VISIBLE);
        text2.setVisibility(View.VISIBLE);
        text3.setVisibility(View.VISIBLE);
        isOpen = true;
    }

    //Hides the FABs with speed depending on the given duration
    public void hideFABs(long duration) {

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
                super.onAnimationEnd(animation);
                fab1.setVisibility(View.GONE);
            }
        });
        fab2Hide.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fab2.setVisibility(View.GONE);
            }
        });
        fab3Hide.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fab3.setVisibility(View.GONE);
            }
        });

        //Re-rotates the FAB
        fab.animate().rotationBy(-45);
        fabBackground.setVisibility(View.GONE);

        //Reset the FABs position
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
        isOpen = false;
    }

//    public static Bitmap textToBitmap(String text, float textSize, R.color.white)

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
