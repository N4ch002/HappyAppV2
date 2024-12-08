package cl.ipvg.happyappv2;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    Button btContact, btNotas, btGuias, btAgenda, btOpciones, btMapa;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        btContact = findViewById(R.id.btContact);
        btOpciones = findViewById(R.id.btOpcionesCuenta);
        btNotas = findViewById(R.id.btNotas);
        btGuias = findViewById(R.id.btGuias);
        btAgenda = findViewById(R.id.btAgenda);
        btMapa = findViewById(R.id.btMapa);

        String userId = getIntent().getStringExtra("USER_ID");



        btOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OpcionesActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });


        btContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });


        btNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NotasActivity.class);

                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });


        btGuias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GuiasActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });


        btAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AgendaActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });


        btMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapaActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });
        }
    }
