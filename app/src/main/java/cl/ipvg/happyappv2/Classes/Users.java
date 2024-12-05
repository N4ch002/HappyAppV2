package cl.ipvg.happyappv2.Classes;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

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

import cl.ipvg.happyappv2.MainActivity;

 public class Users {
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


     public static void cambiarContraseña(String usuario, String nuevaContraseña, OnOperationCompleteListener listener) {
         FirebaseFirestore db = FirebaseFirestore.getInstance();

         db.collection("users")
                 .whereEqualTo("user", usuario)
                 .get()
                 .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                         if (task.isSuccessful() && !task.getResult().isEmpty()) {
                             for (QueryDocumentSnapshot document : task.getResult()) {
                                 db.collection("users").document(document.getId())
                                         .update("pass", nuevaContraseña)
                                         .addOnSuccessListener(aVoid -> listener.onComplete(true))
                                         .addOnFailureListener(e -> listener.onComplete(false));
                             }
                         } else {
                             listener.onComplete(false);  // Usuario no encontrado
                         }
                     }
                 });
     }



     public static void borrarUsuario(String usuario, OnOperationCompleteListener listener) {
         FirebaseFirestore db = FirebaseFirestore.getInstance();

         db.collection("users")
                 .whereEqualTo("user", usuario)
                 .get()
                 .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                         if (task.isSuccessful() && !task.getResult().isEmpty()) {
                             for (QueryDocumentSnapshot document : task.getResult()) {
                                 db.collection("users").document(document.getId())
                                         .delete()
                                         .addOnSuccessListener(aVoid -> listener.onComplete(true))
                                         .addOnFailureListener(e -> listener.onComplete(false));
                             }
                         } else {
                             listener.onComplete(false);  // Usuario no encontrado
                         }
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


 }
