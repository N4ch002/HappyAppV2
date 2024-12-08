package cl.ipvg.happyappv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cl.ipvg.happyappv2.Classes.Guia;
import cl.ipvg.happyappv2.Classes.GuiasAdapter;
import cl.ipvg.happyappv2.Classes.Users;

public class GuiasActivity extends AppCompatActivity {


    RecyclerView recyclerViewGuias;
    Button btRegresarGuias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guias);

        btRegresarGuias = findViewById(R.id.btRegresarGuias);
        String userId = getIntent().getStringExtra("USER_ID");



                RecyclerView recyclerView = findViewById(R.id.recyclerViewGuias);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                List<Guia> guias = new ArrayList<>();
                //get guias de database

        Users.obtenerGuiasDeFirestore(new Users.Callback<List<Guia>>() {
            @Override
            public void onSuccess(List<Guia> guiasObtenidas) {
                // Reemplaza la lista original con la nueva lista obtenida
                guias.clear(); // Limpia la lista local antes de agregar nuevas guías
                guias.addAll(guiasObtenidas);

                GuiasAdapter adapter = new GuiasAdapter(guias);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                // Muestra un mensaje de error al usuario
                Toast.makeText(GuiasActivity.this, "Error al obtener guías", Toast.LENGTH_SHORT).show();
            }
        });
//                guias.add(new Guia("Relajación", "Ejercicios para reducir el estrés. https://www.helpguide.org/es/estres/guia-para-aliviar-el-estres"));
//                guias.add(new Guia("Meditación", "Guía para principiantes. https://aprende.com/blog/bienestar/meditacion/meditacion-para-principiantes/" ) );
//                guias.add(new Guia("Respiración", "Técnicas para calmar la ansiedad. https://medlineplus.gov/spanish/ency/patientinstructions/000874.htm"));
//                guias.add(new Guia("Yoga", "Posturas para mejorar la flexibilidad y reducir el estrés. https://www.sport.es/labolsadelcorredor/practicar-yoga-guia-para-principiantes/"));








        btRegresarGuias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);

            }
        });
    }
}