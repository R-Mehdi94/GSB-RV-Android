package fr.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.gsb.rv.entites.RapportVisite;
import fr.gsb.rv.entites.Visiteur;
import fr.gsb.rv.technique.Ip;
import fr.gsb.rv.technique.Session;

public class ListeRvActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    static final String TAG = "GSB_Liste_Rv_Activity";


    TextView mois;
    TextView annee;
    ListView lvRapportVisite;

    private List<RapportVisite> rapportVisiteList;

    public static List<String> rapportDate;

    private RapportVisite rapportVisite = new RapportVisite();

    private int numRapport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Bundle paquet = this.getIntent().getExtras();


        rapportDate = paquet.getStringArrayList("rapportDate");
        rapportVisiteList = paquet.getParcelableArrayList("rapportVisite");
        // rapportNum = paquet.getIntegerArrayList("rapportNum");



        String moisP = paquet.getString("mois");
        String anneeP = paquet.getString("annee");




        Log.v(TAG, "onCreate :" + "Création de l'activité ListeRvActivity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_rv);

        lvRapportVisite = findViewById(R.id.lvRapports);


        ArrayAdapter<String> adaptateur = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                rapportDate
        );


        lvRapportVisite.setAdapter(adaptateur);

        lvRapportVisite.setOnItemClickListener(this);


        mois = findViewById(R.id.mois);
        annee = findViewById(R.id.annee);

        mois.setText(moisP);
        annee.setText(anneeP);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String rapportSelectionne = rapportDate.get(position);

        for (RapportVisite rapport: rapportVisiteList) {
            if(Objects.equals(rapport.getDateVisite(), rapportSelectionne)){
                numRapport = rapport.getNumero();
            }
        }


        Response.Listener<JSONObject> ecouteurReponse = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{

                    rapportVisite.setNumero(response.getInt(("rap_num")));
                    rapportVisite.setDateVisite(response.getString("rap_date_visite"));
                    rapportVisite.setBilan(response.getString("rap_bilan"));
                    rapportVisite.setNomPraticien(response.getString("pra_nom"));
                    rapportVisite.setPrenomPraticien(response.getString("pra_prenom"));
                    rapportVisite.setCpPraticien(response.getString("pra_cp"));
                    rapportVisite.setVillePraticen(response.getString("pra_ville"));

                    Log.v(TAG, "onCreate :" + "Création du Bundle RechercheRV");


                    Bundle paquet = new Bundle();
                    paquet.putParcelable("rapportVisite", rapportVisite);


                    Intent intentionEnvoyer = new Intent(getApplicationContext(), VIsuRvActivity.class);

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

        JsonObjectRequest requete = new JsonObjectRequest(
                Request.Method.GET,
                Ip.ip+"/rapportsDate/"+ Session.getSession().getLeVisiteur().getMatricule()+"/"+rapportSelectionne+"/"+numRapport,
                null,
                ecouteurReponse,
                ecouteurError
        );

        RequestQueue fileRequetes = Volley.newRequestQueue(this);
        fileRequetes.add(requete);

    }




}