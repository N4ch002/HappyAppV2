package cl.ipvg.happyappv2.Classes;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


 public class Users {

     public static void CheckIfPasswordIsCorrect(String userId, String contraseña, OnCheckUserListener listener) {
         FirebaseFirestore db = FirebaseFirestore.getInstance();

         db.collection("users")
                 .document(userId) // Busca directamente por ID
                 .get()
                 .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                         if (task.isSuccessful() && task.getResult().exists()) {
                             String storedPassword = task.getResult().getString("pass");
                             if (storedPassword != null && storedPassword.equals(contraseña)) {
                                 listener.onResult(true, userId);
                             } else {
                                 listener.onResult(false, null);
                             }
                         } else {
                             listener.onResult(false, null);
                         }
                     }
                 });
     }


     public static void CheckIfUserIsInDatabase(String usuario, String contraseña, OnCheckUserListener listener) {
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


     public static void CheckIfUserExists(String usuario, OnCheckUserListener listener) {
         FirebaseFirestore db = FirebaseFirestore.getInstance();

         db.collection("users")
                 .whereEqualTo("user", usuario)
                 .get()
                 .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                         if (task.isSuccessful()) {
                             if (!task.getResult().isEmpty()) {
                                 // El usuario existe
                                 String userId = task.getResult().getDocuments().get(0).getId();
                                 listener.onResult(true, userId);
                             } else {
                                 // El usuario no existe
                                 listener.onResult(false, null);
                             }
                         } else {
                             Log.w(TAG, "Error checking user.", task.getException());
                             listener.onResult(false, null);
                         }
                     }
                 });
     }
     public interface OnOperationCompleteListener {
         void onComplete(boolean success);
     }


     public static void cambiarContraseña(String userId, String nuevaContraseña, OnOperationCompleteListener listener) {
         FirebaseFirestore db = FirebaseFirestore.getInstance();

         db.collection("users")
                 .document(userId) // Accede directamente al documento por su ID
                 .update("pass", nuevaContraseña) // Actualiza solo el campo "pass"
                 .addOnSuccessListener(aVoid -> listener.onComplete(true)) // Éxito
                 .addOnFailureListener(e -> listener.onComplete(false)); // Error
     }




     public static void borrarUsuario(String userId, OnOperationCompleteListener listener) {
         FirebaseFirestore db = FirebaseFirestore.getInstance();

         db.collection("users").document(userId)
                 .delete()
                 .addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         listener.onComplete(true);
                     }
                 })
                 .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         listener.onComplete(false);
                     }
                 });
     }



     public static void crearUsuario(String usuario, String contraseña) {

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
         public void  GetUsuarios(){
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
     public static void obtenerGuiasDeFirestore(Callback<List<Guia>> callback) {
         // Referencia a la base de datos Firestore
         FirebaseFirestore db = FirebaseFirestore.getInstance();

         // Lista para almacenar las guías obtenidas
         List<Guia> listaGuias = new ArrayList<>();

         // Consulta a la colección "guias"
         db.collection("guias")
                 .get()
                 .addOnCompleteListener(task -> {
                     if (task.isSuccessful()) {
                         // Itera sobre los documentos obtenidos
                         for (QueryDocumentSnapshot document : task.getResult()) {
                             // Extrae los datos del documento
                             String titulo = document.getString("titulo");
                             String descripcion = document.getString("descripcion");

                             // Crea un objeto Guia y lo agrega a la lista
                             listaGuias.add(new Guia(titulo, descripcion));
                         }
                         // Devuelve la lista a través del callback
                         callback.onSuccess(listaGuias);
                     } else {
                         // Manejo de errores
                         callback.onFailure(task.getException());
                     }
                 });

     }
     public interface Callback<T> {
         void onSuccess(T result);
         void onFailure(Exception e);
     }


 }
