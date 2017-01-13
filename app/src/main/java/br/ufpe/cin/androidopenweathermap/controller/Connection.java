package br.ufpe.cin.androidopenweathermap.controller;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import br.ufpe.cin.androidopenweathermap.model.Cidade;

/**
 * Created by luis on 12/01/17.
 */

public class Connection extends AsyncTask<String, Integer, Cidade[]> {

    private String saida;

    @Override
    protected Cidade[] doInBackground(String... params) {

        // Conexão com Open Weather Map deve ser feita aqui pelas boas práticas do Android
        String preUrl = params[0];
        URL url;
        Cidade[] cidades = new Cidade[15];
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
            String jsonDeResposta = s.hasNext() ? s.next() : "";

            // Tratadno resposta
            Cidade cidade = new Cidade();
            cidades = cidade.getCidades(jsonDeResposta);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return cidades;
    }

    @Override
    protected void onPostExecute(Cidade[] feed) {

    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }


    public String enviar(LatLng latLng) {

        // Criando URL, fazendo conexão em outra thread e pegando resposta
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        String LAT = String.valueOf(latitude);
        String LON = String.valueOf(longitude);
        String preUrl = "http://api.openweathermap.org/data/2.5/find?lat="+LAT+"&lon="+LON+"&units=metric&cnt=15&APPID=99c49b547f8027f110bd4bb55639e8ec";
        execute(preUrl);
        return this.saida;
    }
}
