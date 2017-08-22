package com.examble.myfirstapp;

import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private static final String e = "log";
    protected ArrayList<Contact> mContacts;

    public ContactAdapter(ArrayList<Contact> Contacts) {
        mContacts = Contacts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Contact mContact;
        private ImageView mImageView;
        private TextView mNameTextView;
        private TextView mInfoTextView;

        public ViewHolder(View v) {
            super(v);
            mImageView = (ImageView) itemView.findViewById(R.id.contact_image);
            mNameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            mInfoTextView = (TextView) itemView.findViewById(R.id.contact_info);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  Toast.makeText(v.getContext(), "Selected "+mContact.getName()+" -\nshuffled contacts!", Toast.LENGTH_SHORT).show();
                    Collections.shuffle(mContacts);
                    notifyDataSetChanged();
                }
            });
            //itemView.getTag();
            //Log.v(e,"sdasdasdasdasdasdasd --- "+itemView.getTag());
        }

        public void bindData(Contact c) {
            mContact = c;
            mImageView.setImageResource(c.getImageId());
            mNameTextView.setText(c.getName());
            mInfoTextView.setText(c.getInfo());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.contact_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact c = mContacts.get(position);
        holder.bindData(c);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

}
