package br.ufpe.cin.androidopenweathermap.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import br.ufpe.cin.androidopenweathermap.view.MapsActivity;

/**
 * Created by luis on 13/01/17.
 */

public class Cidade {

    private String name;
    private String temp_max;
    private String temp_min;
    private String weather;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemp_max() {
        return temp_max;
    }

    private void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }

    public String getTemp_min() {
        return temp_min;
    }

    private void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }

    public String getWeather() {
        return weather;
    }

    private void setWeather(String weather) {
        this.weather = weather;
    }

    public Cidade[] getCidades(String jsonString) {

        Cidade[] cidades = new Cidade[15];

        try {
            JSONObject cidadeObjeto = new JSONObject(jsonString);
            for (int i = 0; i < 15; i++) {

                // Pegando atributos da resposta do Open Weather Map
                JSONObject cidadeName = cidadeObjeto.getJSONArray("list").getJSONObject(i);
                String name = cidadeName.getString("name");
                JSONObject cidadeDetalhes = cidadeName.getJSONObject("main");
                String temp_max = cidadeDetalhes.getString("temp_max");
                String temp_min = cidadeDetalhes.getString("temp_min");
                JSONObject cidadeTempo = cidadeName.getJSONArray("weather").getJSONObject(0);
                String description = cidadeTempo.getString("description");

                // Colocando atributos no array de objetos de Cidade
                Cidade cidade = new Cidade();
                cidade.setName(name);
                cidade.setTemp_max(temp_max);
                cidade.setTemp_min(temp_min);
                cidade.setWeather(description);
                cidades[i] = cidade;
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }

        return cidades;
    }

}
