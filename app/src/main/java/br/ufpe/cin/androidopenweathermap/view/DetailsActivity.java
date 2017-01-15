package br.ufpe.cin.androidopenweathermap.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import br.ufpe.cin.androidopenweathermap.R;
import br.ufpe.cin.androidopenweathermap.model.Cidade;

public class DetailsActivity extends AppCompatActivity {

    String jsonDeResposta;
    InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Configurando valores da cidade para aparecer na tela
        Object objetoJsonDeResposta = getIntent().getExtras().get("jsonDeResposta");
        Object objetoPosicao = getIntent().getExtras().get("posicao");
        Cidade cidade = new Cidade();
        try {
            jsonDeResposta = objetoJsonDeResposta.toString();
            int posicao = Integer.parseInt(objetoPosicao.toString());

            Cidade detalhes = cidade.getDetalhes(posicao, jsonDeResposta);

            // Criando TextViews
            TextView name = (TextView)findViewById(R.id.name);
            TextView temp_max = (TextView)findViewById(R.id.temp_max);
            TextView temp_min = (TextView)findViewById(R.id.temp_min);
            TextView description = (TextView)findViewById(R.id.description);

            // Coloando valores nas TextViews
            name.setText(detalhes.getName());
            String temp_maxPalavra = "Temperatura máxima: " + detalhes.getTemp_max() + "°C";
            String temp_minPalavra = "Temperatura mínimo: " + detalhes.getTemp_min() + "°C";
            String descricao = "Descrição do tempo: " + detalhes.getWeather();
            temp_max.setText(temp_maxPalavra);
            temp_min.setText(temp_minPalavra);
            description.setText(descricao);

        } catch (Exception e) {
            Toast.makeText(DetailsActivity.this,
                    "Ocorreu algum erro", Toast.LENGTH_LONG).show();
            finish();
        }

        // In Loco Interstitial
        interstitialAd = new InterstitialAd(DetailsActivity.this);
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.setAdUnitId("bf6e6388b0278b3adc57c2e6a0fbb3c556cf4f93979d18f1ed1de195526a32d2");
        interstitialAd.loadAd(adRequest);

        // Forma correta de pegar Interstitial
//        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
//        mPublisherInterstitialAd.setAdUnitId("bf6e6388b0278b3adc57c2e6a0fbb3c556cf4f93979d18f1ed1de195526a32d2");
//        PublisherAdRequest adRequest = new PublisherAdRequest.Builder()
//                .addTestDevice("bf6e6388b0278b3adc57c2e6a0fbb3c556cf4f93979d18f1ed1de195526a32d2")
//                .build();
//        mPublisherInterstitialAd.loadAd(adRequest);

}

    @Override
    public void onBackPressed() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }

        // Forma correta de pegar Interstitial
//        if (mPublisherInterstitialAd.isLoaded()) {
//            mPublisherInterstitialAd.show();
//        }

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
