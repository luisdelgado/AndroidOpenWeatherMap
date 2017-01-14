package br.ufpe.cin.androidopenweathermap.view;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.androidopenweathermap.R;
import br.ufpe.cin.androidopenweathermap.model.Cidade;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.WHITE;

public class DetailsActivity extends AppCompatActivity {

    List<String> opcoes;
    ArrayAdapter<String> adaptador;
    ListView details;
    String jsonDeResposta;
    Button voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

//        // Criando lista
//        details = (ListView) findViewById(R.id.details);
//        opcoes = new ArrayList<>();
//
        // Configurando valores dos campos
        Object objetoJsonDeResposta = getIntent().getExtras().get("jsonDeResposta");
        Object objetoPosicao = getIntent().getExtras().get("posicao");
        Cidade cidade = new Cidade();
        try {
            jsonDeResposta = objetoJsonDeResposta.toString();
            int posicao = Integer.parseInt(objetoPosicao.toString());
            Cidade detalhes = cidade.getDetalhes(posicao, jsonDeResposta);
            TextView name = (TextView)findViewById(R.id.name);
            TextView temp_max = (TextView)findViewById(R.id.temp_max);
            TextView temp_min = (TextView)findViewById(R.id.temp_min);
            TextView description = (TextView)findViewById(R.id.description);
            name.setText(detalhes.getName());
            String temp_maxPalavra = "Temperatura máxima: " + detalhes.getTemp_max() + "°C";
            String temp_minPalavra = "Temperatura mínimo: " + detalhes.getTemp_min() + "°C";
            String descricao = "Descrição do tempo: " + detalhes.getWeather();
            temp_max.setText(temp_maxPalavra);
            temp_min.setText(temp_minPalavra);
            description.setText(descricao);
//            opcoes.add(detalhes.getName());
//            opcoes.add("Temperatura máxima: " + detalhes.getTemp_max() + "°C");
//            opcoes.add("Temperatura mínimo: " + detalhes.getTemp_min() + "°C");
//            opcoes.add("Descrição do tempo: " + detalhes.getWeather());
//            adaptador = new ArrayAdapter<>(DetailsActivity.this, android.R.layout.simple_list_item_1, opcoes);
//            details.setAdapter(adaptador);
//            details.colo
//
        } catch (Exception e) {
            Toast.makeText(DetailsActivity.this,
                    "Ocorreu algum erro", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailsActivity.this, ListActivity.class);
        intent.putExtra("jsonDeResposta", jsonDeResposta);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
