package com.example.task_41p_eventplanner.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.task_41p_eventplanner.R;
import com.example.task_41p_eventplanner.viewmodel.EventViewModel;

public class EventListFragment extends Fragment {

    private EventViewModel viewModel;
    private EventAdapter adapter;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new EventAdapter(
                event -> {
                    Bundle args = new Bundle();
                    args.putInt("eventId", event.id);
                    Navigation.findNavController(view)
                            .navigate(R.id.addEditEventFragment, args);
                },
                event -> {
                    viewModel.delete(event);
                    com.google.android.material.snackbar.Snackbar.make(
                            view, "\"" + event.title + "\" deleted",
                            com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
                }
        );

        recyclerView.setAdapter(adapter);

        viewModel.allEvents.observe(getViewLifecycleOwner(), events -> {
            adapter.submitList(events);
        });
    }
}