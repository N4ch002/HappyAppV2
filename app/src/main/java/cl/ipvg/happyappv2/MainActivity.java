package cl.ipvg.happyappv2;

import static android.content.ContentValues.TAG;

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

    Button btIngresar, btBorrar;
    EditText etUsuario, etContra;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btIngresar = (Button) findViewById(R.id.btIngresar);
        btBorrar = (Button) findViewById(R.id.btBorrar);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etContra = (EditText) findViewById(R.id.etContra);

        Intent intent = new Intent(this, MainActivity2.class);

        btBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUsuario.setText("");
                etContra.setText("");
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

                CheckIfUserIsInDatabase(usuario, contraseña, new OnCheckUserListener() {
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



    private void CheckIfUserIsInDatabase(String usuario, String contraseña, OnCheckUserListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .whereEqualTo("user", usuario)
                .whereEqualTo("pass", contraseña)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                // Obtiene el ID del primer documento coincidente
                                String userId = task.getResult().getDocuments().get(0).getId();
                                listener.onResult(true, userId);
                            } else {
                                listener.onResult(false, null);
                            }
                        } else {
                            Log.w(TAG, "Error checking user.", task.getException());
                            listener.onResult(false, null);
                        }
                    }
                });
    }

    public interface OnCheckUserListener {
        void onResult(boolean exists, String userId);
    }





    private void crearUsuario(String usuario, String contraseña) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("user", usuario);
        user.put("pass", contraseña);

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }




    private  void  GetUsuarios(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }


}