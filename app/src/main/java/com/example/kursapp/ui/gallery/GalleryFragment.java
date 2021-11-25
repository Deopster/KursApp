package com.example.kursapp.ui.gallery;

import android.content.Intent;
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
import androidx.fragment.app.Fragment;

import com.example.kursapp.DatabaseAdapter;
import com.example.kursapp.MainActivity;
import com.example.kursapp.R;
import com.example.kursapp.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {
    MainActivity f;
    static DatabaseAdapter databaseAdapter;
    private FragmentGalleryBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        final TextView textView = binding.text;
        textView.setText("Список сотрудников Учёта персонала");
        View root = binding.getRoot();
        databaseAdapter = new DatabaseAdapter(f.getAppContext());
        ListView lvContact = root.findViewById(R.id.lvContact);
        final SimpleCursorAdapter simpleCursorAdapter = databaseAdapter.populateListViewFromDB("Data1");
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