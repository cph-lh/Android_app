package com.examble.myfirstapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ListFragment extends Fragment {

    static final String SAVED_ARRAY = "itemArray";
    protected View root;
    protected ImageView imageView;
    protected ListView listView;
    protected View listHeader;
    protected ArrayAdapter<String> adapter;
    protected ArrayList<String> itemArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        root = inflater.inflate(R.layout.list_fragment, container, false);
        listView = (ListView) root.findViewById(R.id.list_view);
        listHeader = (TextView) root.findViewById(R.id.header);
        imageView = (ImageView) root.findViewById(R.id.add_item);
        listHeader = inflater.inflate(R.layout.list_header, null, false);
        listView.addHeaderView(listHeader, null, false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int position, long id) {
                Toast.makeText(getActivity(), "Selected " + av.getItemAtPosition(position) + " -\nshuffled items!", Toast.LENGTH_SHORT).show();
                Collections.shuffle(itemArray);
                adapter.notifyDataSetChanged();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newItem = "Item " + (itemArray.size() + 1);
                itemArray.add(newItem);
                adapter.notifyDataSetChanged();
            }
        });

        if (savedInstanceState != null) {
            itemArray = savedInstanceState.getStringArrayList(SAVED_ARRAY);
        } else {
            itemArray = new ArrayList<String>();
            for (int i = 0; i < 100; i++) {
                itemArray.add("Item " + (i + 1));
            }
        }

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, itemArray);
        listView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList(SAVED_ARRAY, itemArray);
    }
}