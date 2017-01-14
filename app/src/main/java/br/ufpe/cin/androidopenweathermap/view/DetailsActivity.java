package br.ufpe.cin.androidopenweathermap.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.androidopenweathermap.R;
import br.ufpe.cin.androidopenweathermap.model.Cidade;

public class DetailsActivity extends AppCompatActivity {

    List<String> opcoes;
    ArrayAdapter<String> adaptador;
    ListView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Criando lista
        details = (ListView) findViewById(R.id.cities);
        opcoes = new ArrayList<>();

        // Configurando valores dos campos
        Object objetoJsonDeResposta = getIntent().getExtras().get("jsonDeResposta");
        Object objetoPosicao = getIntent().getExtras().get("posicao");
        Cidade cidade = new Cidade();
        try {
            String jsonDeResposta = objetoJsonDeResposta.toString();
            int posicao = Integer.parseInt(objetoPosicao.toString());
            Cidade detalhes = cidade.getDetalhes(posicao, jsonDeResposta);
            getActionBar().setTitle(detalhes.getName());
            opcoes.add("Temperatura máxima: " + detalhes.getTemp_max());
            opcoes.add("Temperatura mínimo: " + detalhes.getTemp_min());
            opcoes.add("Descrição do tempo: " + detalhes.getWeather());

        } catch (Exception e) {
            Toast.makeText(DetailsActivity.this,
                    "Ocorreu algum erro", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
