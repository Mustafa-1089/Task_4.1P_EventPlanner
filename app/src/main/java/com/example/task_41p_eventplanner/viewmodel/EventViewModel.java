package com.example.task_41p_eventplanner.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.*;
import com.example.task_41p_eventplanner.data.Event;
import com.example.task_41p_eventplanner.data.EventRepository;
import java.util.List;

public class EventViewModel extends AndroidViewModel {

    private final EventRepository repository;
    public final LiveData<List<Event>> allEvents;

    public EventViewModel(@NonNull Application application) {
        super(application);
        repository = new EventRepository(application);
        allEvents = repository.getAllEvents();
    }

    public void insert(Event e) { repository.insert(e); }
    public void update(Event e) { repository.update(e); }
    public void delete(Event e) { repository.delete(e); }
}