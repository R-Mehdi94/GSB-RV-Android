package fr.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.gsb.rv.entites.RapportVisite;
import fr.gsb.rv.technique.Ip;
import fr.gsb.rv.technique.Session;

public class RechercheRvActivity extends AppCompatActivity {

    static final String TAG = "GSB_Recherche_Rv_Activity";

    private static final String [] lesMois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
    private static final Integer [] lesAnnees = {2019,2020,2021,2022,2023};

    TextView mois;
    TextView annee;


    private List<String> rapportDate= new ArrayList<>();
    private List<RapportVisite> rapportVisite = new ArrayList<>();



    Spinner spMois;
    Spinner spAnnees;

    protected void onResume() {
        super.onResume();

        // Code à exécuter à chaque fois que l'activité redevient visible
        if(rapportDate != null && !rapportDate.isEmpty()){
            rapportDate.clear();
        }

    }


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_rv);

        Log.v(TAG, "onCreate :" + "Création de l'activité RechercheRV");


        spMois = findViewById(R.id.spMois);

        ArrayAdapter<String> aaMois = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                lesMois
        );

        aaMois.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line
        );

        spMois.setAdapter(aaMois);


        spAnnees = findViewById(R.id.spAnnee);

        ArrayAdapter<Integer> aaAnnees = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                lesAnnees
        );

        aaAnnees.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line
        );

        spAnnees.setAdapter(aaAnnees);

    }

    @SuppressLint("LongLogTag")
    public void confirmer(View vue){

        Log.v(TAG, "Confirmation :" + "Confirmation du mois et de l'année");

        Log.v(TAG, "list :" + ListeRvActivity.rapportDate);


        /*if(ListeRvActivity.rapportDate != null && !ListeRvActivity.rapportDate.isEmpty()){
            ListeRvActivity.rapportDate.clear();
        }*/

        String valeurMois = spMois.getSelectedItem().toString();
        int numeroMois = Arrays.asList(lesMois).indexOf(valeurMois) + 1; // Ajouter 1 pour obtenir le numéro du mois
        String valeurAnnee = spAnnees.getSelectedItem().toString();



        Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try{

                    for(int i = 0; i < response.length(); i++ ){
                        RapportVisite unRapport = new RapportVisite();

                        unRapport.setNumero(Integer.parseInt(response.getJSONObject(i).getString("rap_num")));
                        unRapport.setDateVisite(response.getJSONObject(i).getString("rap_date_visite"));

                        rapportVisite.add(unRapport);



                        //rapportNum.add(unRapport.getNumero());
                        rapportDate.add(unRapport.getDateVisite());

                    }


                    Log.v(TAG, "onCreate :" + "Création du Bundle RechercheRV");

                    Bundle paquet = new Bundle();

                    paquet.putStringArrayList("rapportDate", (ArrayList<String>) rapportDate);
                    paquet.putParcelableArrayList("rapportVisite", (ArrayList<RapportVisite>) rapportVisite);

                    paquet.putString("mois", valeurMois);
                    paquet.putString("annee", valeurAnnee);


                    Intent intentionEnvoyer = new Intent(getApplicationContext(), ListeRvActivity.class);

                    intentionEnvoyer.putExtras(paquet);

                    startActivity(intentionEnvoyer);

                    Log.v(TAG, "intention :" + "Intention vers ListeRvActivity");


                    Log.v(TAG, "200 Ok");


                }catch(JSONException e){
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
                Ip.ip+"/rapports/"+ Session.getSession().getLeVisiteur().getMatricule()+"/"+numeroMois+"/"+valeurAnnee,
                null,
                ecouteurReponse,
                ecouteurError
        );

        RequestQueue fileRequetes = Volley.newRequestQueue(this);
        fileRequetes.add(requete);



    }

}