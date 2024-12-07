package cl.ipvg.happyappv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class AgendaActivity extends AppCompatActivity {

    Button btRegresarAgenda, btModificar, btBorrarHoy, btGuardar;
    CalendarView calendarView;
    TextView tvEventos;
    EditText etModificarAgenda;
    String fechaSeleccionada = "";
    String userId;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agenda);

        // Inicializar componentes
        btRegresarAgenda = findViewById(R.id.btRegresarAgenda);
        btModificar = findViewById(R.id.btModificar);
        btBorrarHoy = findViewById(R.id.btBorrarHoy);
        calendarView = findViewById(R.id.calendarView);
        tvEventos = findViewById(R.id.textViewEventos);
        etModificarAgenda = findViewById(R.id.editTextModificarAgenda);
        btGuardar = findViewById(R.id.btGuardar);

        db = FirebaseFirestore.getInstance();
        userId = getIntent().getStringExtra("USER_ID");


        //cargar evento de hoy
        fechaSeleccionada = getFechaActual();
        cargarEventoDelDia();



        // Volver a la actividad principal
        btRegresarAgenda.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });

        // Seleccionar fecha
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            fechaSeleccionada = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
            cargarEventoDelDia();
        });

        // Cambiar a modo de modificación
        btModificar.setOnClickListener(view -> {
            tvEventos.setVisibility(View.INVISIBLE);
            etModificarAgenda.setVisibility(View.VISIBLE);
            etModificarAgenda.setText(tvEventos.getText());
            btModificar.setVisibility(View.INVISIBLE);
            btGuardar.setVisibility(View.VISIBLE);
            btBorrarHoy.setVisibility(View.INVISIBLE);


            // Verificar si el texto actual es "Nada agendado para este día"
            if (tvEventos.getText().toString().equals("Nada agendado para este día.")) {
                etModificarAgenda.setText(""); // Dejar vacío
            } else {
                etModificarAgenda.setText(tvEventos.getText().toString()); // Mostrar el texto actual del evento
            }
        });




        btGuardar.setOnClickListener(view -> {
            String evento = etModificarAgenda.getText().toString();
            if (!evento.isEmpty()) {

                guardarEvento(evento);


                volverAModoNormal();
            } else {
                Toast.makeText(this, "El evento no puede estar vacío.", Toast.LENGTH_SHORT).show();
            }
        });


        btBorrarHoy.setOnClickListener(
                view -> eliminarEvento()
        );
    }


    private void cargarEventoDelDia() {
        if (userId == null || fechaSeleccionada == null || fechaSeleccionada.isEmpty()) {
            Toast.makeText(this, "Error: Usuario o fecha no definidos.", Toast.LENGTH_SHORT).show();
            return;
        }


        DocumentReference agendaRef = db.collection("agenda").document(userId);
        agendaRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                String evento = task.getResult().getString(fechaSeleccionada);
                if (evento != null) {
                    tvEventos.setText(evento);
                } else {
                    tvEventos.setText("Nada agendado para este día.");
                }
            } else {
                tvEventos.setText("Error al cargar evento.");
            }
        });
    }


    private void guardarEvento(String evento) {
        DocumentReference agendaRef = db.collection("agenda").document(userId);
        Map<String, Object> data = new HashMap<>();
        data.put(fechaSeleccionada, evento);

        agendaRef.set(data, com.google.firebase.firestore.SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Evento guardado con éxito.", Toast.LENGTH_SHORT).show();
                    volverAModoNormal();
                    cargarEventoDelDia();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al guardar evento.", Toast.LENGTH_SHORT).show());
    }


    private void eliminarEvento() {
        DocumentReference agendaRef = db.collection("agenda").document(userId);
        Map<String, Object> updates = new HashMap<>();
        updates.put(fechaSeleccionada, null);

        agendaRef.update(updates)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Evento eliminado con éxito.", Toast.LENGTH_SHORT).show();
                    cargarEventoDelDia();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al eliminar evento.", Toast.LENGTH_SHORT).show());
    }


    private void volverAModoNormal() {
        tvEventos.setVisibility(View.VISIBLE);
        etModificarAgenda.setVisibility(View.INVISIBLE);
        etModificarAgenda.setText("");
        btModificar.setVisibility(View.VISIBLE);
        btGuardar.setVisibility(View.INVISIBLE);
        btBorrarHoy.setVisibility(View.VISIBLE);
    }

    public String getFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(calendarView.getDate());

    }
}

