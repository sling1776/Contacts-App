package com.sling.contacts.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sling.contacts.R;
import com.sling.contacts.veiwmodels.ContactViewModel;

public class ContactsFragment extends Fragment {
    public ContactsFragment(){
        super(R.layout.fragment_contact);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ContactViewModel viewmodel = new ViewModelProvider(getActivity()).get(ContactViewModel.class);
        viewmodel.getCurrentContact().observe(getViewLifecycleOwner(),(contact)->{
            if(contact == null){
                getActivity().getSupportFragmentManager().popBackStack();
            }else {

                TextView contactName = view.findViewById(R.id.contact_page_contact_name);
                TextView contactPhoneNumber = view.findViewById(R.id.contact_page_phone_number);
                TextView contactEmail = view.findViewById(R.id.contact_page_email);
                TextView contactAddress = view.findViewById(R.id.contact_page_address);

                contactName.setText(contact.name);
                contactPhoneNumber.setText(contact.phoneNumber);
                contactEmail.setText(contact.email);
                contactAddress.setText(contact.address);
            }
        });

        view.findViewById(R.id.edit).setOnClickListener((editButton)->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,AddOrUpdateContactFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });
        view.findViewById(R.id.delete).setOnClickListener((fab)->{
            viewmodel.deleteCurrentContact();
        });
    }
}
