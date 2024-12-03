package cl.ipvg.happyappv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AgendaActivity extends AppCompatActivity {

    Button btRegresarAgenda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agenda);

        btRegresarAgenda = (Button) findViewById(R.id.btRegresarAgenda);

        Intent intentregresaragenda = new Intent(this, MainActivity2.class);

        btRegresarAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentregresaragenda);
            }
        });
    }
}