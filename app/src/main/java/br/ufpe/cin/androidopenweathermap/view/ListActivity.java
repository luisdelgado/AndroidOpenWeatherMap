package br.ufpe.cin.androidopenweathermap.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

        // Criando lista
        cities = (ListView) findViewById(R.id.cities);
        opcoes = new ArrayList<>();

        // Configurando valores dos campos
        Object objetoJsonDeResposta = getIntent().getExtras().get("jsonDeResposta");
        Cidade cidade = new Cidade();
        try {
            final String jsonDeResposta = objetoJsonDeResposta.toString();
            Cidade[] cidades = cidade.getCidades(jsonDeResposta);
            for (int i = 0; i < 15; i++) {
                opcoes.add(cidades[i].getName());
            }

            // Configurando o clique na cidade
            adaptador = new ArrayAdapter<>(ListActivity.this, android.R.layout.simple_list_item_1, opcoes);
            cities.setAdapter(adaptador);
            cities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ListActivity.this, DetailsActivity.class);
                    intent.putExtra("jsonDeResposta", jsonDeResposta);
                    intent.putExtra("posicao", position);
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            Toast.makeText(ListActivity.this,
                    "Ocorreu algum erro", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}

