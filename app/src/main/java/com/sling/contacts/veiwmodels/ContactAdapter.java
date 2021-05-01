package com.sling.contacts.veiwmodels;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.sling.contacts.R;
import com.sling.contacts.models.Contact;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    ObservableArrayList<Contact> contacts;

    public interface OnContactListItemClicked{
        public void onClick(Contact contact);
    }

    OnContactListItemClicked listener;

    public ContactAdapter(ObservableArrayList<Contact> contacts, OnContactListItemClicked listener){
        this.contacts= contacts;
        this.listener = listener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView contactName = holder.itemView.findViewById(R.id.contact_name);
        TextView phoneNumber = holder.itemView.findViewById(R.id.card_phoneNumber);

        phoneNumber.setText(contacts.get(position).phoneNumber);
        contactName.setText(contacts.get(position).name);
        contactName.setTextColor(Color.BLACK);

        holder.itemView.setOnClickListener(view->{
            listener.onClick(contacts.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
