package com.example.task_41p_eventplanner.data;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import java.util.List;

@Dao
public interface EventDao {
    @Insert
    void insert(Event event);
    @Update
    void update(Event event);
    @Delete
    void delete(Event event);
    @Query("SELECT * FROM events ORDER BY dateTimeMillis ASC")
    LiveData<List<Event>> getAllEvents();
}