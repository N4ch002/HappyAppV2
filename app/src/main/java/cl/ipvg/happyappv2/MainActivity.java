package cl.ipvg.happyappv2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btIngresar, btBorrar, btRegistar;
    EditText etUsuario, etContra;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btIngresar = (Button) findViewById(R.id.btIngresar);
        btBorrar = (Button) findViewById(R.id.btBorrar);
        btRegistar = (Button) findViewById(R.id.btRegistrar);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etContra = (EditText) findViewById(R.id.etContra);

        Intent intent = new Intent(this, MainActivity2.class);
        Intent intentregistro = new Intent(this, RegistroActivity.class);

        btBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUsuario.setText("");
                etContra.setText("");
            }
        });

        btRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentregistro);
            }
        });

        btIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Usuario = etUsuario.getText().toString();
                String Contra = etContra.getText().toString();

                if (Usuario.equals("Alumno") && Contra.equals("12345")){
                    startActivity(intent);
                }
            }
        });

    }
}