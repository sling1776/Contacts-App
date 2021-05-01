package com.sling.contacts.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sling.contacts.R;
import com.sling.contacts.models.Contact;
import com.sling.contacts.veiwmodels.ContactAdapter;
import com.sling.contacts.veiwmodels.ContactViewModel;

public class ContactsListFragment extends Fragment {
    public ContactsListFragment() {
        super(R.layout.fragment_contact_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ContactViewModel viewmodel = new ViewModelProvider(getActivity()).get(ContactViewModel.class);
        ObservableArrayList<Contact> contacts = viewmodel.getContactList();

        ContactAdapter adapter = new ContactAdapter(contacts,
        contact-> {
            viewmodel.setCurrentContact(contact);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ContactsFragment.class, null).setReorderingAllowed(true)
                    .addToBackStack(null).commit();
        }
        );
        contacts.addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {
                getActivity().runOnUiThread(()->{
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(()->{
                    adapter.notifyItemRangeChanged(positionStart, itemCount);
                });
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(()->{
                    adapter.notifyItemRangeChanged(positionStart, itemCount);

                });
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                getActivity().runOnUiThread(()->{
                    adapter.notifyItemMoved(fromPosition, toPosition);
                });

            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(()->{
                    adapter.notifyItemRangeRemoved(positionStart, itemCount);
                });

            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.contact_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        View fab = view.findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   viewmodel.setCurrentContact(null);
                   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, AddOrUpdateContactFragment.class, null)
                           .setReorderingAllowed(true)
                           .addToBackStack(null)
                           .commit();
               }
           }
        );


    }
}
