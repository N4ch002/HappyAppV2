package cl.ipvg.happyappv2;

import static android.content.ContentValues.TAG;

import cl.ipvg.happyappv2.Classes.Users;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btIngresar, btBorrar, btRegistar;
    EditText etUsuario, etContra;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btIngresar = findViewById(R.id.btIngresar);
        btBorrar = findViewById(R.id.btBorrar);
        btRegistar = findViewById(R.id.btRegistro);
        etUsuario = findViewById(R.id.etUsuario);
        etContra = findViewById(R.id.etContra);

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
                String usuario = etUsuario.getText().toString();
                String contraseña = etContra.getText().toString();
                //crearUsuario(usuario, contraseña);


                if (usuario.isEmpty() || contraseña.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, completa ambos campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                Users.CheckIfUserIsInDatabase(usuario, contraseña, new Users.OnCheckUserListener() {
                    @Override
                    public void onResult(boolean exists, String userId) {
                        if (exists && userId != null) {
                            Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Usuario autenticado con éxito. ID: " + userId);

                            intent.putExtra("USER_ID", userId);  // Ahora pasas el ID correcto
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Usuario o contraseña incorrectos.");
                        }
                    }
                });
            }
        });







    }














}