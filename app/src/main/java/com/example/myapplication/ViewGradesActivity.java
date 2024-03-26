package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewGradesActivity extends AppCompatActivity {

    private ListView listViewGrades;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> gradesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_grades);

        // Inform the user they can delete items from the list
        Toast.makeText(this, "Toca un elemento para eliminarlo", Toast.LENGTH_LONG).show();

        // Initialize ListView and adapter
        listViewGrades = findViewById(R.id.listViewGrades);
        gradesList = getIntent().getStringArrayListExtra("gradesList");

        // Set up adapter for ListView
        if (gradesList != null) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gradesList);
            listViewGrades.setAdapter(adapter);
        }

        // Set up item click listener for ListView to allow item removal
        listViewGrades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gradesList.remove(position);
                adapter.notifyDataSetChanged();

                // Actualiza el resultado que se enviará de regreso a MainActivity
                Intent returnIntent = new Intent();
                returnIntent.putStringArrayListExtra("gradesList", gradesList);
                setResult(RESULT_OK, returnIntent);
            }
        });

        // OnClickListener for Back button
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Asegúrate de establecer el resultado si el usuario presiona el botón de regreso
                Intent returnIntent = new Intent();
                returnIntent.putStringArrayListExtra("gradesList", gradesList);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
