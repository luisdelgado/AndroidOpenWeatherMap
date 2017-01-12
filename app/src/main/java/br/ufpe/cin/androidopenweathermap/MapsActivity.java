package br.ufpe.cin.androidopenweathermap;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static br.ufpe.cin.androidopenweathermap.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button buscar;
    private Marker pinoBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        buscar = (Button) findViewById(R.id.search);
        buscar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (pinoBuscar.isVisible()) {
                    pinoBuscar.getPosition();
                } else {
                    Toast.makeText(MapsActivity.this,
                            "Segure e arraste o pino para a posição desejada", Toast.LENGTH_LONG).show();
                    pinoBuscar.setVisible(true);
                }
            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Só inicia com mapa e buscar
        final LatLng PERTH = new LatLng(-31.90, 115.86);
        final Marker perth = mMap.addMarker(new MarkerOptions()
                .position(PERTH)
                .draggable(true)
                .visible(false));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(PERTH));

        // Depois que o pino aparece
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (!perth.isVisible()) {
                    Toast.makeText(MapsActivity.this,
                            "Segure e arraste o pino para a posição desejada", Toast.LENGTH_LONG).show();
                    perth.setVisible(true);
                }
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
                if (!perth.isVisible()) {
                    Toast.makeText(MapsActivity.this,
                            "Segure e arraste o pino para a posição desejada", Toast.LENGTH_LONG).show();
                    perth.setVisible(true);
                }

            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                perth.setPosition(arg0.getPosition());
                pinoBuscar = perth;
            }

            @Override
            public void onMarkerDrag(Marker arg0) {

            }
        });

        pinoBuscar = perth;

    }


}
