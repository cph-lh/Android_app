package com.examble.myfirstapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ListFragment extends Fragment {

    private View root;
    private FloatingActionButton fab;
    private ListView listView;
    private ListAdapter adapter;
    private ArrayList<ListItem> itemArray;
    static final String SAVED_ARRAY = "itemArray";
    private static final String TAG = "print";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //Inflates the layout for this fragment
        root = inflater.inflate(R.layout.list_fragment, container, false);
        listView = (ListView) root.findViewById(R.id.list_view);
        fab = (FloatingActionButton) root.findViewById(R.id.fab);

        //Shows a toast when an list item is clicked and shuffles the array
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int position, long id) {
                ListItem item = itemArray.get(position);
                Toast.makeText(getActivity(), "Selected " + item.getName() + " - shuffled items!", Toast.LENGTH_SHORT).show();
                Collections.shuffle(itemArray);
                adapter.notifyDataSetChanged();
            }
        });
        //*** Add onScrollListener ***

        //Adds a new list item when the FAB is clicked and displays a Snackbar with the data added
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListItem newItem = new ListItem();
                newItem.setName("Item " + (itemArray.size() + 1));
                newItem.setInfo("Info " + (itemArray.size() + 1));
                itemArray.add(newItem);
                adapter.notifyDataSetChanged();
                Snackbar.make(view, "Added " + newItem.getName(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        //Sets the array to a saved array (if any) or else create a new array
        if (savedInstanceState != null) {
            itemArray = savedInstanceState.getParcelableArrayList(SAVED_ARRAY);
        } else {
            itemArray = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                ListItem item = new ListItem();
                item.setName("Item " + (i + 1));
                item.setInfo("Info " + (i + 1));
                itemArray.add(item);
            }
        }

        //Sets the adapter
        adapter = new ListAdapter(getActivity(), itemArray);
        listView.setAdapter(adapter);
        return root;
    }

    //Saves the array on configuration changes (screen orientation etc.)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(SAVED_ARRAY, itemArray);
    }
}