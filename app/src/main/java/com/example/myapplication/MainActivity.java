package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Views and adapter declaration
    private EditText editTextSubject, editTextGrade;
    private Button buttonAdd, viewGradesButton;
    private ListView listViewGrades;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> gradesList;

    private static final int VIEW_GRADES_REQUEST = 1;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Comprueba si el resultado proviene de la actividad ViewGradesActivity
        if (requestCode == VIEW_GRADES_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                // Actualiza la lista con los elementos eliminados
                ArrayList<String> updatedGradesList = data.getStringArrayListExtra("gradesList");
                if (updatedGradesList != null) {
                    gradesList.clear();
                    gradesList.addAll(updatedGradesList);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextGrade = findViewById(R.id.editTextGrade);
        buttonAdd = findViewById(R.id.buttonAdd);
        viewGradesButton = findViewById(R.id.viewGradesButton);
        listViewGrades = findViewById(R.id.listViewGrades);
        gradesList = new ArrayList<>();

        // Setting up adapter for ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gradesList);
        listViewGrades.setAdapter(adapter);

        // Checks if the button to view grades should be enabled
        updateViewGradesButtonState();

        // OnClickListener for Add button
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Collects user input
                String subject = editTextSubject.getText().toString();
                String gradeString = editTextGrade.getText().toString();
                // Validates user input and adds to list
                if (!subject.isEmpty() && !gradeString.isEmpty()) {
                    try {
                        double grade = Double.parseDouble(gradeString);
                        if (grade >= 0 && grade <= 10) {
                            gradesList.add(subject + ": " + grade);
                            adapter.notifyDataSetChanged();
                            editTextSubject.setText("");
                            editTextGrade.setText("");
                            updateViewGradesButtonState();
                        } else {
                            Toast.makeText(MainActivity.this, "La calificación debe ser entre 0 y 10.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Por favor, ingrese una calificación válida.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Por favor, ingrese todos los datos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // OnClickListener for the View Grades button

        viewGradesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewGradesActivity.class);
                intent.putStringArrayListExtra("gradesList", gradesList);
                startActivityForResult(intent, VIEW_GRADES_REQUEST);
            }
        });
    }

    // Enables or disables the view grades button based on the list's content
    private void updateViewGradesButtonState() {
        viewGradesButton.setEnabled(!gradesList.isEmpty());
    }
}

