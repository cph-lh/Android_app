package com.examble.myfirstapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ContactListFragment extends Fragment {

    private String[] names = {"Denmark", "Sweden", "Norway", "Italy", "France", "Germany", "United Kingdom", "Russia"};
    private String[] info = {"Copenhagen", "Stockholm", "Oslo", "Rome", "Paris", "Berlin", "London", "Moscow"};
    private int[] image = {R.drawable.denmark, R.drawable.sweden, R.drawable.norway, R.drawable.italy, R.drawable.france,
            R.drawable.germany, R.drawable.uk, R.drawable.russia};
    protected ArrayList<Contact> contactArray;
    protected View root;
    protected RecyclerView recyclerView;
    protected FloatingActionButton fab;
    protected ContactAdapter adapter;
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
        fab = (FloatingActionButton) root.findViewById(R.id.add_item);

        //Sets the array to a saved array (if any) or else create a new array
        if (savedInstanceState != null) {
            contactArray = savedInstanceState.getParcelableArrayList(SAVED_ARRAY);
        } else {
            contactArray = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
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
                Contact c = new Contact();
                c.setName("Contact " + (contactArray.size() + 1));
                c.setInfo("Info " + (contactArray.size() + 1));
                c.setImageId(R.drawable.doge);
                contactArray.add(c);
                adapter.notifyDataSetChanged();
                Snackbar.make(view, "Added " + c.getName(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        //Hides the FAB on scrolling and shows it when not
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        fab.show();
                        break;
                    default:
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
}
