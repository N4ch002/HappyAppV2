package cl.ipvg.happyappv2;

import static android.content.ContentValues.TAG;

import cl.ipvg.happyappv2.Classes.Users;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class RegistroActivity extends AppCompatActivity {

    Button btBorrar2, btRegresarRegistro, btRegistrar;
    EditText etUsuario2, etContra2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        btRegistrar = findViewById(R.id.btRegistrar);
        btBorrar2 = findViewById(R.id.btBorrar2);
        btRegresarRegistro = findViewById(R.id.btRegresarRegistro);
        etUsuario2 = findViewById(R.id.etUsuario2);
        etContra2 = findViewById(R.id.etContra2);

        Intent intentregresarregistro = new Intent(this, MainActivity.class);

        btBorrar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUsuario2.setText("");
                etContra2.setText("");
            }
        });

        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        String usuario = etUsuario2.getText().toString();
                        String contraseña = etContra2.getText().toString();

                        if (usuario.isEmpty() || contraseña.isEmpty()) {
                            Toast.makeText(RegistroActivity.this, "Por favor, completa ambos campos", Toast.LENGTH_SHORT).show();
                            return;
                        }

                Users.CheckIfUserExists(usuario, new Users.OnCheckUserListener() {
                    @Override
                    public void onResult(boolean exists, String userId) {
                        if (exists) {
                            Toast.makeText(RegistroActivity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                        } else {
                            // Crea el nuevo usuario
                            Users.crearUsuario(usuario, contraseña);
                            Toast.makeText(RegistroActivity.this, "Usuario creado.", Toast.LENGTH_SHORT).show();
                            startActivity(intentregresarregistro); // Redirige al usuario después del registro
                        }
                    }
                });
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

