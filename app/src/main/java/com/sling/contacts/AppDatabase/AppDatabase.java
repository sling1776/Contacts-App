package com.sling.contacts.AppDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sling.contacts.models.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactsDao getContactsDao();
}
