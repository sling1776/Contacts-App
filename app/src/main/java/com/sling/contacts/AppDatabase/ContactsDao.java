package com.sling.contacts.AppDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sling.contacts.models.Contact;

import java.util.List;

@Dao
public interface ContactsDao {
    @Insert
    public long insert(Contact contact);

    @Query("SELECT * FROM contact")
    public List<Contact> getAll();

    @Query("SELECT * FROM contact WHERE id = :id LIMIT 1")
    public Contact findByID(long id);

    @Update
    public void update(Contact contact);

    @Delete
    public void delete(Contact contact);
}
