package cl.ipvg.happyappv2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

public abstract class MapaActivity2 extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mapa2);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().
                findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

    }

    public void onMapReady(GoogleMap googleMap){
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