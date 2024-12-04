package cl.ipvg.happyappv2;

import static android.app.ProgressDialog.show;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import java.util.List;
import java.util.Map;

public class NotasActivity extends AppCompatActivity {

    Button btRegresarNotas;
    EditText etTitle, etContent;
    Button btnAddNote;
    ListView listViewNotes;
    ArrayAdapter<String> notesAdapter;
    ArrayList<String> notesList;
    FirebaseFirestore db;
    String userId; // Asegúrate de pasar el userID al iniciar esta Activity

    @SuppressLint("MissingInflatedId")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);



        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        btnAddNote = findViewById(R.id.btAñadir);
        listViewNotes = findViewById(R.id.listView);

        db = FirebaseFirestore.getInstance();
        userId = getIntent().getStringExtra("USER_ID");

        if (userId == null || userId.isEmpty()) {
            Toast.makeText(this, "Error: ID de usuario no recibido", Toast.LENGTH_SHORT).show();
            Log.e("NotasActivity", "userId es null o vacío");
            finish();  // Cierra la actividad si el ID no es válido
            return;
        }

        notesList = new ArrayList<>();
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        listViewNotes.setAdapter(notesAdapter);

        loadNotes();

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });
        btRegresarNotas = (Button) findViewById(R.id.btRegresarNotas);

        Intent intentregresarnotas = new Intent(this, MainActivity2.class);

        btRegresarNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentregresarnotas.putExtra("USER_ID", userId);  // Pasa el ID correcto)
                startActivity(intentregresarnotas);
            }
        });

    }

    private void loadNotes() {
        db.collection("users").document(userId).collection("notes")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    notesList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String title = document.getString("title");
                        String content = document.getString("content");
                        notesList.add(title + "\n" + content);
                    }
                    notesAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(NotasActivity.this, "Error al cargar notas", Toast.LENGTH_SHORT).show();
                });
    }

    private void addNote() {
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> note = new HashMap<>();
        note.put("title", title);
        note.put("content", content);
        note.put("timestamp", FieldValue.serverTimestamp());

        db.collection("users").document(userId).collection("notes")
                .add(note)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Nota agregada", Toast.LENGTH_SHORT).show();
                    loadNotes();
                    etTitle.setText("");
                    etContent.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al agregar nota", Toast.LENGTH_SHORT).show();
                });
    }
}

