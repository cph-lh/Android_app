package com.examble.myfirstapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class ContactListFragment extends Fragment {

    private String[] names = {"Denmark", "Sweden", "Norway", "Italy", "France", "Germany", "United Kingdom", "Russia"};
    private String[] info = {"Copenhagen", "Stockholm", "Oslo", "Rome", "Paris", "Berlin", "London", "Moscow"};
    private int[] image = {R.drawable.denmark, R.drawable.sweden, R.drawable.norway, R.drawable.italy, R.drawable.france,
            R.drawable.germany, R.drawable.uk, R.drawable.russia};
    protected ArrayList<Contact> contactArray;
    protected View root;
    protected RecyclerView mRecyclerView;
    protected ImageView mImageView;
    protected ContactAdapter mAdapter;
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

        root = inflater.inflate(R.layout.contact_list_fragment, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.contact_r_view);
        mImageView = root.findViewById(R.id.add_item);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact c = new Contact();
                c.setName("Contact " + (contactArray.size() + 1));
                c.setInfo("Info " + (contactArray.size() + 1));
                c.setImageId(R.drawable.doge);
                contactArray.add(c);
                mAdapter.notifyDataSetChanged();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ContactAdapter(contactArray);
        mRecyclerView.setAdapter(mAdapter);
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(SAVED_ARRAY, contactArray);
    }
}
