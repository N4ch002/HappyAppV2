package cl.ipvg.happyappv2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistroActivity extends AppCompatActivity {

    Button btBorrar2, btRegresarRegistro;
    EditText etUsuario2, etContra2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        
        btBorrar2 = (Button) findViewById(R.id.btBorrar2);
        btRegresarRegistro = (Button) findViewById(R.id.btRegresarRegistro);
        etUsuario2 = (EditText) findViewById(R.id.etUsuario2);
        etContra2 = (EditText) findViewById(R.id.etContra2);

        Intent intentregresarregistro = new Intent(this, MainActivity.class);

        btBorrar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUsuario2.setText("");
                etContra2.setText("");
            }
        });

        btRegresarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentregresarregistro);
            }
        });
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}