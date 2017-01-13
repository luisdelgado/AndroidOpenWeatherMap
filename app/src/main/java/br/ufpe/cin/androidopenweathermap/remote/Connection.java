package br.ufpe.cin.androidopenweathermap.remote;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.System.currentTimeMillis;

/**
 * Created by luis on 12/01/17.
 */

public class Connection extends AsyncTask<String, Integer, String> {

    private String saida;

    @Override
    protected String doInBackground(String... params) {

        // Conexão com Open Weather Map deve ser feita aqui pelas boas práticas do Android
        this.saida = "Aqui";
        String preUrl = params[0];
        URL url;
        String resposta = "";
        HttpURLConnection urlConnection = null;
        try{
            url = new URL(preUrl);
            urlConnection = null;
            urlConnection = (HttpURLConnection) url
                    .openConnection();
            InputStream in = urlConnection.getInputStream();

            // Por causa da demora do Open Wather Map para funcionar
            Thread.currentThread().sleep(10000);
            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();
//            while (data != -1) {
//                char current = (char) data;
//                data = isw.read();
//                System.out.print(current);
//            }
            resposta = "ok";
        } catch (Exception e) {
            resposta = "erro";
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        this.saida = resposta;
        return resposta;
    }

    @Override
    protected void onPostExecute(String feed) {

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
        String preUrl = "http://api.openweathermap.org/data/2.5/find?lat={"+LAT+"}&lon={"+LON+"}&cnt=15&APPID=99c49b547f8027f110bd4bb55639e8ec";
        execute(preUrl);
        return this.saida;
    }
}
