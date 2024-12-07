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


        Intent intentOpciones = new Intent(this, OpcionesActivity.class);

        btOpciones.setOnClickListener(new View.OnClickListener() {
                @Override
                                          public void onClick(View view) {
                                              intentOpciones.putExtra("USER_ID", userId);
                                              startActivity(intentOpciones);
                                          }
                                      });



        Intent intentcontact = new Intent(this, ContactActivity.class);

        btContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intentcontact);
            }
        });

        Intent intentnotas = new Intent(this, NotasActivity.class);

        btNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            intentnotas.putExtra("USER_ID", userId);
                startActivity(intentnotas);
            }
        });

        Intent intentguias = new Intent(this, GuiasActivity.class);

        btGuias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentguias);
            }
        });

        Intent intentagenda = new Intent(this, AgendaActivity.class);

        btAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentagenda);
            }
        });

        Intent intentmapa = new Intent(this, MapaActivity.class);

        btMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentmapa);
            }
        });
    }
}