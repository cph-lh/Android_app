package com.example.myfirstapp;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    protected ArrayList<Contact> mContacts;

    public ContactAdapter(ArrayList<Contact> Contacts) {
        mContacts = Contacts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected Contact c;
        protected ImageView cImageView;
        protected TextView nameTextView, infoTextView;

        public ViewHolder(View view) {
            super(view);
            cImageView = (ImageView) itemView.findViewById(R.id.contact_image);
            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            infoTextView = (TextView) itemView.findViewById(R.id.contact_info);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Selected " + c.getName(), Snackbar.LENGTH_SHORT).show();
                }
            });
        }

        private void bindData(Contact c) {
            this.c = c;
            cImageView.setImageResource(c.getImageId());
            nameTextView.setText(c.getName());
            infoTextView.setText(c.getInfo());
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
