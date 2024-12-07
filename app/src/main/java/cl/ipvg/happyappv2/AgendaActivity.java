package cl.ipvg.happyappv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AgendaActivity extends AppCompatActivity {

    Button btRegresarAgenda, btModificar, btBorrarHoy, btGuardar;
    CalendarView calendarView;
    TextView tvEventos;
    EditText etModificarAgenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agenda);

        btRegresarAgenda = findViewById(R.id.btRegresarAgenda);
        btModificar = findViewById(R.id.btModificar);
        btBorrarHoy = findViewById(R.id.btBorrarHoy);
        calendarView = findViewById(R.id.calendarView);
        tvEventos = findViewById(R.id.textViewEventos);
        etModificarAgenda = findViewById(R.id.editTextModificarAgenda);
        btGuardar = findViewById(R.id.btGuardar);

        String UserId = getIntent().getStringExtra("USER_ID");

        btRegresarAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //regresar userid
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                intent.putExtra("USER_ID", UserId);
                startActivity(intent);

            }
        });

        btModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Cambiar a vista de modificar
                tvEventos.setVisibility(View.INVISIBLE);
                etModificarAgenda.setVisibility(View.VISIBLE);
                btModificar.setVisibility(View.INVISIBLE);
                btGuardar.setVisibility(View.VISIBLE);
                btBorrarHoy.setVisibility(View.INVISIBLE);

            }
        });


        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //volver a modo normal
                tvEventos.setVisibility(View.VISIBLE);
                etModificarAgenda.setVisibility(View.INVISIBLE);
                etModificarAgenda.setText("");
                btModificar.setVisibility(View.VISIBLE);
                btGuardar.setVisibility(View.INVISIBLE);
                btBorrarHoy.setVisibility(View.VISIBLE);

                //guardar cambios



            }
            });


            }
    }
