package com.example.task_41p_eventplanner.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.task_41p_eventplanner.R;
import com.example.task_41p_eventplanner.data.Event;
import com.example.task_41p_eventplanner.viewmodel.EventViewModel;
import com.google.android.material.snackbar.Snackbar;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddEditEventFragment extends Fragment {

    private EventViewModel viewModel;
    private EditText etTitle, etLocation;
    private Spinner  spCategory;
    private TextView tvDateTime;
    private long     selectedMillis = -1;
    private String   selectedDateTimeStr = "";
    private Event    eventToEdit = null;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_edit_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel  = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        etTitle    = view.findViewById(R.id.etTitle);
        spCategory = view.findViewById(R.id.spCategory);
        etLocation = view.findViewById(R.id.etLocation);
        tvDateTime = view.findViewById(R.id.tvDateTime);

        String[] categories = {"Work", "Social", "Travel", "Appointments", "Other"};
        spCategory.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, categories));

        Calendar calendar = Calendar.getInstance();
        view.findViewById(R.id.btnPickDateTime).setOnClickListener(v -> {
            new DatePickerDialog(requireContext(), (dpView, year, month, day) -> {
                new TimePickerDialog(requireContext(), (tpView, hour, minute) -> {

                    Calendar picked = Calendar.getInstance();
                    picked.set(year, month, day, hour, minute, 0);
                    picked.set(Calendar.SECOND, 0);

                    if (eventToEdit == null && picked.getTimeInMillis() <= System.currentTimeMillis()) {
                        Snackbar.make(view, "Date must be in the future for new events",
                                Snackbar.LENGTH_LONG).show();
                        return;
                    }

                    selectedMillis = picked.getTimeInMillis();
                    selectedDateTimeStr = String.format(Locale.getDefault(),
                            "%02d/%02d/%04d %02d:%02d", day, month + 1, year, hour, minute);
                    tvDateTime.setText(selectedDateTimeStr);

                }, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        int eventId = getArguments() != null ? getArguments().getInt("eventId", -1) : -1;

        if (eventId != -1) {
            viewModel.allEvents.observe(getViewLifecycleOwner(), events -> {
                if (eventToEdit != null) return; // already loaded
                for (Event e : events) {
                    if (e.id == eventId) {
                        eventToEdit = e;
                        prefillFields(e, categories);
                        break;
                    }
                }
            });
        }

        view.findViewById(R.id.btnSave).setOnClickListener(v -> saveEvent(view));
    }

    private void prefillFields(Event e, String[] categories) {
        etTitle.setText(e.title);
        etLocation.setText(e.location);
        tvDateTime.setText(e.dateTime);
        selectedDateTimeStr = e.dateTime;
        selectedMillis      = e.dateTimeMillis;
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].equals(e.category)) {
                spCategory.setSelection(i);
                break;
            }
        }
    }

    private void saveEvent(View view) {
        String title    = etTitle.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String category = spCategory.getSelectedItem().toString();

        if (title.isEmpty()) {
            Snackbar.make(view, "Title is required", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (selectedDateTimeStr.isEmpty()) {
            Snackbar.make(view, "Please pick a date and time", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (eventToEdit == null) {
            Event newEvent = new Event();
            newEvent.title         = title;
            newEvent.category      = category;
            newEvent.location      = location;
            newEvent.dateTime      = selectedDateTimeStr;
            newEvent.dateTimeMillis = selectedMillis;
            viewModel.insert(newEvent);
            Snackbar.make(view, "Event \"" + title + "\" added!", Snackbar.LENGTH_SHORT).show();
        } else {
            eventToEdit.title          = title;
            eventToEdit.category       = category;
            eventToEdit.location       = location;
            eventToEdit.dateTime       = selectedDateTimeStr;
            eventToEdit.dateTimeMillis = selectedMillis;
            viewModel.update(eventToEdit);
            Snackbar.make(view, "Event updated!", Snackbar.LENGTH_SHORT).show();
        }

        Navigation.findNavController(view).navigate(R.id.eventListFragment);
    }
}