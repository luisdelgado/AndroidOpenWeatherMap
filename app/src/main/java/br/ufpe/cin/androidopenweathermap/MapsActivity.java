package br.ufpe.cin.androidopenweathermap;

import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);
                if (pinoBuscar.isVisible()) {
                    LatLng latLng = pinoBuscar.getPosition();
                    double a = latLng.latitude;
                    enviar(latLng);
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
        final LatLng PERTH = new LatLng(-31.900000000000001, 115.860000000000001);
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

    public String enviar(LatLng latLng) {
        URL url;
        HttpURLConnection urlConnection = null;
        String resposta = "";
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        String LAT = String.valueOf(latitude);
        String LON = String.valueOf(longitude);
        String preUrl = "http://api.openweathermap.org/data/2.5/find?lat={"+LAT+"}&lon={"+LON+"}&cnt=15&APPID=99c49b547f8027f110bd4bb55639e8ec";
        try {
            url = new URL(preUrl);
            urlConnection = (HttpURLConnection) url
                    .openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();
//            while (data != -1) {
//                char current = (char) data;
//                data = isw.read();
//                System.out.print(current);
//            }
            Toast.makeText(MapsActivity.this,
                    "Resposta OK", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(MapsActivity.this,
                    "Erro", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
                Toast.makeText(MapsActivity.this,
                        "Terminado", Toast.LENGTH_LONG).show();
            }
        }

        return resposta;
    }
}
