package com.sling.contacts.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.sling.contacts.R;
import com.sling.contacts.veiwmodels.ContactViewModel;

public class AddOrUpdateContactFragment extends Fragment {
    public AddOrUpdateContactFragment(){
        super(R.layout.fragment_add_contact);
    }
    private boolean previouslySaving = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ContactViewModel viewmodel = new ViewModelProvider(getActivity()).get(ContactViewModel.class);
        viewmodel.getCurrentContact().observe(getViewLifecycleOwner(),(contact -> {
            if(contact != null){
                AppCompatEditText contactNameEditText = view.findViewById(R.id.editTextPersonName);
                AppCompatEditText phoneNumberEditText = view.findViewById(R.id.editTextPhone);
                AppCompatEditText emailEditText = view.findViewById(R.id.editTextEmailAddress);
                AppCompatEditText addressEditText = view.findViewById(R.id.editTextTextPostalAddress);

                contactNameEditText.setText(contact.name);
                phoneNumberEditText.setText(contact.phoneNumber);
                emailEditText.setText(contact.email);
                addressEditText.setText(contact.address);

            }
        }));


        viewmodel.getSaving().observe(getViewLifecycleOwner(), (saving)->{
            if(saving && !previouslySaving){
                Button button = view.findViewById(R.id.savebutton);
                button.setEnabled(false);
                button.setText("Saving...");
                previouslySaving = saving;
            }else if(previouslySaving && !saving){
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        view.findViewById(R.id.savebutton).setOnClickListener((savebutton)->{
            AppCompatEditText contactNameEditText = view.findViewById(R.id.editTextPersonName);
            AppCompatEditText phoneNumberEditText = view.findViewById(R.id.editTextPhone);
            AppCompatEditText emailEditText = view.findViewById(R.id.editTextEmailAddress);
            AppCompatEditText addressEditText = view.findViewById(R.id.editTextTextPostalAddress);

            viewmodel.saveContact(
                    contactNameEditText.getText().toString(),
                    phoneNumberEditText.getText().toString(),
                    emailEditText.getText().toString(),
                    addressEditText.getText().toString()
            );

        });
    }
}
