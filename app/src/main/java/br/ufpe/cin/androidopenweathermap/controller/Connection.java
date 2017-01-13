package br.ufpe.cin.androidopenweathermap.controller;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import br.ufpe.cin.androidopenweathermap.model.Cidade;
import br.ufpe.cin.androidopenweathermap.view.MapsActivity;

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
        try{
            url = new URL(preUrl);
            urlConnection = null;
            urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestProperty("Content-type", "application/json");
            InputStream in = urlConnection.getInputStream();

            // Lendo a resposta completa do Open Weather Map
            Scanner s = new Scanner(in).useDelimiter("\\A");
            this.jsonDeResposta = s.hasNext() ? s.next() : "";
        } catch (Exception e) {
            e.printStackTrace();
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
        Toast.makeText(progress[0],
                "Segure e arraste o pino para a posição desejada", Toast.LENGTH_LONG).show();
    }


    public String enviar(LatLng latLng, Context context) {

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
