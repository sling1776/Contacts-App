package com.sling.contacts.veiwmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.sling.contacts.AppDatabase.AppDatabase;
import com.sling.contacts.models.Contact;

import java.util.ArrayList;

public class ContactViewModel extends AndroidViewModel {
    private AppDatabase database;
    private MutableLiveData<Boolean> saving = new MutableLiveData<>();
    private ObservableArrayList<Contact> contactList = new ObservableArrayList<>();
    private MutableLiveData<Contact> currentContact = new MutableLiveData<>();



    public ContactViewModel(@NonNull Application application) {
        super(application);
        database = Room.databaseBuilder(application, AppDatabase.class, "contactdb").build();
        saving.setValue(false);
        new Thread(()->{
            ArrayList<Contact> contacts = (ArrayList<Contact>)database.getContactsDao().getAll();
            contactList.addAll(contacts);
        }).start();

    }

    public MutableLiveData<Contact> getCurrentContact() {
        return currentContact;
    }

    public void setCurrentContact(Contact currentContact) {
        this.currentContact.setValue(currentContact);
    }

    public MutableLiveData<Boolean> getSaving(){
        return saving;
    }

    public ObservableArrayList<Contact> getContactList() {
        return contactList;
    }

    public void saveContact(String name, String phoneNumber, String email, String address){
        saving.setValue(true);
        new Thread(()->{
            if(currentContact.getValue()!=null){
                Contact current = currentContact.getValue();
                current.name = name;
                current.email = email;
                current.address = address;
                current.phoneNumber = phoneNumber;
                database.getContactsDao().update(current);
                currentContact.postValue(current);
                int index = contactList.indexOf(current);
                contactList.set(index, current);

            }else {
                Contact newContact = new Contact();
                if (name == null) newContact.name = " ";
                else newContact.name = name;
                if (phoneNumber == null) newContact.phoneNumber = " ";
                else newContact.phoneNumber = phoneNumber;
                if (address == null) newContact.address = " ";
                else newContact.address = address;
                if (email == null) newContact.email = " ";
                else newContact.email = email;

                //Insert into Database:
                newContact.id = database.getContactsDao().insert(newContact);

                //put into an Observable list:
                contactList.add(newContact);
            }
            saving.postValue(false);

        }).start();

    }

    public void deleteCurrentContact(){
        new Thread(()->{
            database.getContactsDao().delete(currentContact.getValue());
            contactList.remove(currentContact.getValue());
            currentContact.postValue(null);
        }).start();
    }
}
