package br.ufpe.cin.androidopenweathermap.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.ufpe.cin.androidopenweathermap.R;
import br.ufpe.cin.androidopenweathermap.model.Cidade;

public class ListActivity extends AppCompatActivity {

    List<String> opcoes;
    ArrayAdapter<String> adaptador;
    ListView cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        cities = (ListView) findViewById(R.id.cities);

        opcoes = new ArrayList<>();

        Object jsonDeResposta = getIntent().getExtras().get("jsonDeResposta");

        Cidade cidade = new Cidade();
        try {
            Cidade[] cidades = cidade.getCidades(jsonDeResposta.toString());
            for (int i = 0; i < 15; i++) {
                opcoes.add(cidades[i].getName());
            }

        } catch (Exception e) {
            finish();
        }

        adaptador = new ArrayAdapter<>(ListActivity.this, android.R.layout.simple_list_item_1, opcoes);
        cities.setAdapter(adaptador);
        cities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: finish();
                        break;
                }
            }
        });
    }
}

