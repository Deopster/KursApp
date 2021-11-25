package com.example.kursapp.ui.home;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.kursapp.DatabaseAdapter;
import com.example.kursapp.MainActivity;
import com.example.kursapp.R;
import com.example.kursapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    MainActivity f;
    static DatabaseAdapter databaseAdapter;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.text;
       textView.setText("Список сотрудников Подбора персонала");
        databaseAdapter = new DatabaseAdapter(f.getAppContext());
        ListView lvContact = root.findViewById(R.id.lvContact);
        final SimpleCursorAdapter simpleCursorAdapter = databaseAdapter.populateListViewFromDB("Data");
        lvContact.setAdapter(simpleCursorAdapter);
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) simpleCursorAdapter.getItem(position);
                String name = cursor.getString(1);
                String email = cursor.getString(2);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}