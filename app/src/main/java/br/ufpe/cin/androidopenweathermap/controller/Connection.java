package br.ufpe.cin.androidopenweathermap.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import br.ufpe.cin.androidopenweathermap.model.Cidade;
import br.ufpe.cin.androidopenweathermap.view.MapsActivity;

import static android.R.attr.button;
import static android.R.attr.inAnimation;

/**
 * Created by luis on 12/01/17.
 */

public class Connection extends AsyncTask<String, Context, String> {

    private String jsonDeResposta = "";

    @Override
    protected String doInBackground(String... params) {

        // Conexão com Open Weather Map deve ser feita aqui pelas boas práticas do Android
        String preUrl = params[0];
        URL url;
        HttpURLConnection urlConnection = null;
        int resposta = 0;
        try{
            url = new URL(preUrl);
            urlConnection = null;
            urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestProperty("Content-type", "application/json");
            InputStream in = urlConnection.getInputStream();
            resposta = urlConnection.getResponseCode();
            // Lendo a resposta completa do Open Weather Map
            Scanner s = new Scanner(in).useDelimiter("\\A");
            this.jsonDeResposta = s.hasNext() ? s.next() : "";
        } catch (Exception e) {
            if (resposta != 200) {
                this.jsonDeResposta = "erro";
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String feed) {

    }

    @Override
    protected void onProgressUpdate(Context... progress) {

    }


    public String enviar(LatLng latLng, Context context) {

        onProgressUpdate(context);

        // Criando URL, fazendo conexão em outra thread e pegando resposta
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        String LAT = String.valueOf(latitude);
        String LON = String.valueOf(longitude);
        String preUrl = "http://api.openweathermap.org/data/2.5/find?lat="+LAT+"&lon="+LON+"&units=metric&cnt=15&APPID=99c49b547f8027f110bd4bb55639e8ec";
        execute(preUrl);

        // Esperando resposta chegar do servidor
        while (jsonDeResposta.equals("")) {
            try {
                Thread.currentThread().sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return jsonDeResposta;
    }
}
