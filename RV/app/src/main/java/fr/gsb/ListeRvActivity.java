package fr.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import fr.gsb.rv.entites.RapportVisite;
import fr.gsb.rv.entites.Visiteur;
import fr.gsb.rv.technique.Ip;
import fr.gsb.rv.technique.Session;

public class ListeRvActivity extends AppCompatActivity {

    static final String TAG = "GSB_Liste_Rv_Activity";


    TextView mois;
    TextView annee;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate :" + "Création de l'activité ListeRvActivity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_rv);

        Bundle paquet = this.getIntent().getExtras();


        String moisP = paquet.getString("mois");

        String anneeP = paquet.getString("annee");

        int moisInt = paquet.getInt("moisInt");



        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxx " +moisP + anneeP);

        mois = findViewById(R.id.mois);
        annee = findViewById(R.id.annee);

        mois.setText(moisP);
        annee.setText(anneeP);



        Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try{

                    for(int i = 0; i <response.length(); i++ ){
                        RapportVisite unRapport = new RapportVisite();

                        unRapport.setNumero(Integer.parseInt(response.getJSONObject(i).getString("rap_num")));
                        unRapport.setDateVisite(response.getJSONObject(i).getString("rap_date_visite"));
                        unRapport.setBilan(response.getJSONObject(i).getString("rap_bilan"));
                        unRapport.setNomPraticien(response.getJSONObject(i).getString("pra_nom"));
                        unRapport.setPrenomPraticien(response.getJSONObject(i).getString("pra_prenom"));
                        unRapport.setCpPraticien(response.getJSONObject(i).getString("pra_cp"));
                        unRapport.setVillePraticen(response.getJSONObject(i).getString("pra_ville"));

                        Log.i(TAG, unRapport.toString());

                    }

                    Log.v(TAG, "200 Ok");

                    Intent intentionEnvoyer = new Intent(getApplicationContext(), MenuRvActivity.class);
                    startActivity(intentionEnvoyer);


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
                System.out.println("erreur"+ mois.toString() + annee.toString());
            }
        };

        JsonArrayRequest requete = new JsonArrayRequest(
                Request.Method.GET,
                "http://"+Ip.ip+"/rapports/"+Session.getSession().getLeVisiteur().getMatricule()+"/"+moisInt+"/"+anneeP,
                null,
                ecouteurReponse,
                ecouteurError
        );

        RequestQueue fileRequetes = Volley.newRequestQueue(this);
        fileRequetes.add(requete);

    }
}