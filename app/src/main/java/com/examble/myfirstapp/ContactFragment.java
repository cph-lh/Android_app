package com.examble.myfirstapp;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;

import java.util.ArrayList;

public class ContactFragment extends Fragment {

    private String[] names = {"Denmark", "Sweden", "Norway", "Italy", "France", "Germany", "United Kingdom", "Russia"};
    private String[] info = {"Copenhagen", "Stockholm", "Oslo", "Rome", "Paris", "Berlin", "London", "Moscow"};
    private int[] image = {R.drawable.denmark, R.drawable.sweden, R.drawable.norway, R.drawable.italy, R.drawable.france,
            R.drawable.germany, R.drawable.uk, R.drawable.russia};
    private ArrayList<Contact> contactArray;
    private View root;
    private RecyclerView recyclerView;
    private FloatingActionButton fab, fab1, fab2;
    private boolean isOpen;
    private ContactAdapter adapter;
    private Animation fab_open, fab_close;
    final static String SAVED_ARRAY = "contactArray";

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        contactArray = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            if (i < names.length) {
//                Contact c = new Contact();
//                c.setName(names[i]);
//                c.setInfo(info[i]);
//                c.setImageId(image[i]);
//                contactArray.add(c);
//            } else {
//                Contact c = new Contact();
//                c.setName("Contact " + (i + 1));
//                c.setInfo("Info " + (i + 1));
//                c.setImageId(R.drawable.doge);
//                contactArray.add(c);
//            }
//        }
        super.onCreate(savedInstanceState);
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

        //Defines FAB animations
        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);

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
                Contact c = new Contact();
                c.setName("Contact " + (contactArray.size() + 1));
                c.setInfo("Info " + (contactArray.size() + 1));
                c.setImageId(R.drawable.doge);
                contactArray.add(c);
                adapter.notifyDataSetChanged();
                Snackbar.make(v, "Created new contact: " + c.getName(), Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "FAB 2 selected", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });

        //Hides the FAB(s) on scrolling and shows it when not
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        fab.show();
                        break;
                    default:
                        fab.hide();
                        if (isOpen) {
                            hideFAB();
                        }
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



    //Animates the FAB(s)
    public void animateFAB() {
        if (!isOpen) {
            showFAB();
        } else {
            hideFAB();
        }
    }

    //Shows the FAB(s)
    public void showFAB() {
        ObjectAnimator.ofFloat(fab, "rotation", 0f, 45f).setDuration(200).start();
        fab1.startAnimation(fab_open);
        fab2.startAnimation(fab_open);
        fab1.setClickable(true);
        fab2.setClickable(true);
        isOpen = true;
    }

    //Hides the FAB(s)
    public void hideFAB() {
        ObjectAnimator.ofFloat(fab, "rotation", 45f, 0f).setDuration(200).start();
        fab1.startAnimation(fab_close);
        fab2.startAnimation(fab_close);
        fab1.setClickable(false);
        fab2.setClickable(false);
        isOpen = false;
    }

    //Saves the array on configuration changes (screen orientation etc.)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(SAVED_ARRAY, contactArray);
    }
}
