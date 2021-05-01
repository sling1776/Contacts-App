package com.sling.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sling.contacts.fragments.ContactsListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, ContactsListFragment.class, null)
                    .commit();
        }
    }
}