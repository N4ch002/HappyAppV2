package cl.ipvg.happyappv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import cl.ipvg.happyappv2.Classes.Users;

public class OpcionesActivity extends AppCompatActivity {

    EditText editTextContraseñaNueva, editTextContraseñaAntigua;
    Button btBorrarCuenta1, btBorrarCuenta2, btVolver, btCambiarContraseña;
    TextView textViewBorrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_opciones);

        String userId = getIntent().getStringExtra("USER_ID");


        textViewBorrar = findViewById(R.id.textViewBorrar);
        editTextContraseñaNueva = findViewById(R.id.editTextContraseñaNueva);
        editTextContraseñaAntigua = findViewById(R.id.editTextContraseñaAntigua);
        btBorrarCuenta1 = findViewById(R.id.buttonBorrarCuenta1);
        btBorrarCuenta2 = findViewById(R.id.buttonBorrarCuenta2);
        btVolver = findViewById(R.id.btVolver);
        btCambiarContraseña = findViewById(R.id.buttonCambiarContraseña);


        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });


        btBorrarCuenta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewBorrar.setVisibility(View.VISIBLE);
                btBorrarCuenta2.setVisibility(View.VISIBLE);
            }
        });

        btBorrarCuenta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //borrar cuenta y volver a login

                Users.borrarUsuario(userId, new Users.OnOperationCompleteListener() {
                    public void onComplete(boolean success) {
                        if (success) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                            Toast.makeText(getApplicationContext(), "Usuario borrado con éxito", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error al borrar el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }


                });


            }
        });

        btCambiarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nuevaContraseña = editTextContraseñaNueva.getText().toString();
                String contraseñaAntigua = editTextContraseñaAntigua.getText().toString();





                Users.CheckIfPasswordIsCorrect(userId, contraseñaAntigua, new Users.OnCheckUserListener() {
                    @Override
                    public void onResult(boolean correctPassword, String userId) {
                        if (correctPassword) {
                            Users.cambiarContraseña(userId, nuevaContraseña, new Users.OnOperationCompleteListener() {
                                @Override
                                public void onComplete(boolean success) {
                                    if (success) {
                                        Toast.makeText(getApplicationContext(), "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Contraseña antigua incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
                                               });






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
}