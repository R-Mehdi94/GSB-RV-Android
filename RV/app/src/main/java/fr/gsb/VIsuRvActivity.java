package fr.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.gsb.rv.entites.Echantillons;
import fr.gsb.rv.entites.RapportVisite;
import fr.gsb.rv.entites.Visiteur;
import fr.gsb.rv.technique.Ip;
import fr.gsb.rv.technique.Session;

public class VIsuRvActivity extends AppCompatActivity {

    private RapportVisite rapportVisite;

    static final String TAG = "GSB_Visu_Rv_Activity";

    public List<Echantillons> echantillonsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visu_rv);

        Bundle paquet = this.getIntent().getExtras();

        rapportVisite = paquet.getParcelable("rapportVisite");

        TableLayout tableLayout = findViewById(R.id.tbVisu);
        tableLayout.setStretchAllColumns(true);

        // Créer la première ligne avec les titres des colonnes
        TableRow rowHeader = new TableRow(this);
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        ));
        rowHeader.addView(createTextView("Numéro"));
        rowHeader.addView(createTextView("Date de visite"));
        rowHeader.addView(createTextView("Bilan"));
        rowHeader.addView(createTextView("Nom du Praticien"));
        rowHeader.addView(createTextView("Prenom du Praticien"));
        rowHeader.addView(createTextView("Code postale Praticien"));
        rowHeader.addView(createTextView("Ville Praticien"));

    // Ajouter la première ligne à la table
        tableLayout.addView(rowHeader);


        // Créer une ligne de séparation
        View separator = new View(this);
        separator.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                1
        ));
        separator.setBackgroundColor(Color.parseColor("#c0c0c0"));

        // Ajouter la ligne de séparation à la table
        tableLayout.addView(separator);



    // Créer la deuxième ligne avec les données
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        ));
        row.addView(createTextView(String.valueOf(rapportVisite.getNumero())));
        row.addView(createTextView(rapportVisite.getDateVisite()));
        row.addView(createTextView(rapportVisite.getBilan()));
        row.addView(createTextView(rapportVisite.getNomPraticien()));
        row.addView(createTextView(rapportVisite.getPrenomPraticien()));
        row.addView(createTextView(rapportVisite.getCpPraticien()));
        row.addView(createTextView(rapportVisite.getVillePraticen()));

    // Ajouter la deuxième ligne à la table
        tableLayout.addView(row);




    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(20, 20, 20, 20);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(16);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        return textView;
    }

    public void echantillons(View vue) {


        Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for (int i = 0; i < response.length(); i++) {

                        Echantillons unEchantillon = new Echantillons();

                        unEchantillon.setMedNomCommercial(response.getJSONObject(i).getString("med_nomcommercial"));
                        unEchantillon.setQuantite(Integer.parseInt(response.getJSONObject(i).getString("off_quantite")));

                        Log.e(TAG, String.valueOf(unEchantillon));

                        echantillonsList.add(unEchantillon);


                    }


                    Log.v(TAG, "onCreate :" + "Création du Bundle RechercheRV");

                    Bundle paquet = new Bundle();

                    paquet.putParcelableArrayList("echantillonsList", (ArrayList<Echantillons>) echantillonsList);

                    Intent intentionEnvoyer = new Intent(getApplicationContext(), VisuEchantActivity.class);

                    intentionEnvoyer.putExtras(paquet);

                    startActivity(intentionEnvoyer);

                    Log.v(TAG, "intention :" + "Intention vers ListeRvActivity");


                    Log.v(TAG, "200 Ok");


                } catch (JSONException e) {
                    Log.e(TAG, "JSON : " + e.getMessage());
                    System.out.println("catch");

                }

            }
        };

        Response.ErrorListener ecouteurError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Erreur HTTP :" + " " + error.getMessage());
            }
        };

        JsonArrayRequest requete = new JsonArrayRequest(
                Request.Method.GET,
                Ip.ip + "/rapports/echantillons/" +Session.getSession().getLeVisiteur().getMatricule()+"/"+rapportVisite.getNumero(),
                null,
                ecouteurReponse,
                ecouteurError
        );

        RequestQueue fileRequetes = Volley.newRequestQueue(this);
        fileRequetes.add(requete);

    }
}