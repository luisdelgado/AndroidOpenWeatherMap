package br.ufpe.cin.androidopenweathermap.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.androidopenweathermap.R;
import br.ufpe.cin.androidopenweathermap.controller.Connection;
import br.ufpe.cin.androidopenweathermap.model.Cidade;

import static android.app.PendingIntent.getActivity;
import static android.provider.AlarmClock.EXTRA_MESSAGE;
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

                // Verificando se o usuario ja passou da primeria tela
                if (pinoBuscar.isVisible()) {
                    Toast.makeText(MapsActivity.this,
                            "Carregando...", Toast.LENGTH_SHORT).show();

                    LatLng latLng = pinoBuscar.getPosition();

                    // Iniciando conexão com Open Weather Map
                    Connection connection = new Connection();
                    String jsonDeResposta;
                    jsonDeResposta = connection.enviar(latLng, MapsActivity.this);

                    // Esperando resposta chegar do servidor
                    double inicio = System.currentTimeMillis();
                    double fim = System.currentTimeMillis();
                    while (fim - inicio < 5) {
                        fim = System.currentTimeMillis();
                    }
                    if (jsonDeResposta.equals("") || jsonDeResposta.equals("erro")) {
                        Toast.makeText(MapsActivity.this,
                                "O servidor está com problemas", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(MapsActivity.this, ListActivity.class);
                        intent.putExtra("jsonDeResposta", jsonDeResposta);
                        startActivity(intent);
                    }
                    fim = System.currentTimeMillis();
                    if (fim - inicio > 150) {
                        Toast.makeText(MapsActivity.this,
                                "Verifique sua conexão com a Internet", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(MapsActivity.this,
                            "Segure e arraste o pino para o local", Toast.LENGTH_LONG).show();
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

        // Só inicia com mapa e buscar de acordo coma a especificação
        final LatLng PERTH = new LatLng(-8.063122,-34.8716389);
        final Marker perth = mMap.addMarker(new MarkerOptions()
                .position(PERTH)
                .draggable(true)
                .visible(false));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PERTH, 14));

        // O pino só deve aparecer depois
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (!perth.isVisible()) {
                    Toast.makeText(MapsActivity.this,
                            "SSegure e arraste o pino para o local", Toast.LENGTH_LONG).show();
                    perth.setVisible(true);
                }
            }
        });

        // Movendo o pino
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
                if (!perth.isVisible()) {
                    Toast.makeText(MapsActivity.this,
                            "Segure e arraste o pino para o local", Toast.LENGTH_LONG).show();
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
