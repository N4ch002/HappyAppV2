package cl.ipvg.happyappv2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback{

    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView tvLocacion;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        tvLocacion = findViewById(R.id.tvLocacion);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        getCurrentLocation();
    }

    private void getCurrentLocation(){
        if(ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager
                .PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
            if(location != null){
                tvLocacion.setText(
                        "Latitud: " + location.getLatitude() + "\n" +
                                "Longitud: " + location.getLongitude()
                );
            } else {
                tvLocacion.setText("No se ha podido obtener la ubicación del dispositivo.");
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                tvLocacion.setText("Sin permiso de ubicación");
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng VirginioGomez = new LatLng(-36.589738, -72.082471);
        LatLng CentroBienestar = new LatLng(-36.616619092256855, -72.09904593951883);
        LatLng COSAM = new LatLng(-36.60806119016521, -72.09561281683861);
        LatLng Depto = new LatLng(-36.60435116537748, -72.10312930895003);
        LatLng MEDSALUD = new LatLng(-36.58902331990335, -72.09391150269776);
        LatLng OCONTO = new LatLng(-36.58692904027955, -72.06705955950041);

        mMap.addMarker(new MarkerOptions().position(VirginioGomez).title("Instituto Profesional Virginio Gomez"));
        mMap.addMarker(new MarkerOptions().position(CentroBienestar).title("Centro Bienestar"));
        mMap.addMarker(new MarkerOptions().position(COSAM).title("COSAM Chillan"));
        mMap.addMarker(new MarkerOptions().position(Depto).title("Depto Salud Mental"));
        mMap.addMarker(new MarkerOptions().position(MEDSALUD).title("MEDSALUD Centro Médico Quilamapu"));
        mMap.addMarker(new MarkerOptions().position(OCONTO).title("OCONTO Centro Psicologico y de Salud"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(VirginioGomez));
        mMap.setMinZoomPreference(4.0F);
        mMap.setMaxZoomPreference(18.0f);
    }
}