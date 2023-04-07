package fr.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.List;

import fr.gsb.rv.entites.Motifs;
import fr.gsb.rv.entites.Praticien;
import fr.gsb.rv.entites.RapportVisite;
import fr.gsb.rv.technique.Ip;
import fr.gsb.rv.technique.Session;

public class MenuRvActivity extends AppCompatActivity {

    static final String TAG = "GSB_Menu_Rv_Activity";

    public List<Praticien> praticienList = new ArrayList<>();
    public List<Motifs> motifsList = new ArrayList<>();
    public Bundle paquet = new Bundle();


    Button bSeDeconnecter;
    TextView tvNomPrenom;
    Button consulter;
    Button saisir;

    protected void onResume() {
        super.onResume();

        // Code à exécuter à chaque fois que l'activité redevient visible
        if(motifsList != null && !motifsList.isEmpty()){
            motifsList.clear();
        }

        if(praticienList != null && !praticienList.isEmpty()){
            praticienList.clear();
        }

    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_rv);

        SaisieRvActivity.praticienList.clear();
        SaisieRvActivity.motifsList.clear();


        tvNomPrenom = findViewById(R.id.nomPrenom);
        tvNomPrenom.setText(Session.getSession().getLeVisiteur().getPrenom() + " " + Session.getSession().getLeVisiteur().getNom());
        consulter = findViewById(R.id.consulter);
        saisir = findViewById(R.id.saisir);
        bSeDeconnecter = findViewById(R.id.bDeco);

        Log.v(TAG, "onCreate :" + "Création de l'activité MenuRV");

    }

    public void consulter(View vue){

        Log.v(TAG, "intention :" + "Intention vers RechercheRvActivity");
        Intent intentionEnvoyer = new Intent(getApplicationContext(), RechercheRvActivity.class);
        startActivity(intentionEnvoyer);

    }

    public void enregistrer(View vue){

        //Praticien--------------------------------------------------------------------------------


        Response.Listener<JSONArray> ecouteurReponsePraticien = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try{

                    for(int i = 0; i < response.length(); i++ ){
                        Praticien unPraticien= new Praticien();

                        unPraticien.setNumero(Integer.parseInt(response.getJSONObject(i).getString("pra_num")));
                        unPraticien.setNom(response.getJSONObject(i).getString("pra_nom"));
                        unPraticien.setPrenom(response.getJSONObject(i).getString("pra_prenom"));
                        unPraticien.setVille(response.getJSONObject(i).getString("pra_ville"));

                        praticienList.add(unPraticien);

                    }


                    paquet.putParcelableArrayList("praticienList", (ArrayList<Praticien>) praticienList);


                    Log.v(TAG, "200 Ok");

                    if(!praticienList.isEmpty() && !motifsList.isEmpty()){
                        Log.v(TAG, "intention :" + "Intention vers SaisieRvActivity");

                        Intent intentionEnvoyer = new Intent(getApplicationContext(), SaisieRvActivity.class);

                        intentionEnvoyer.putExtras(paquet);

                        startActivity(intentionEnvoyer);
                    }



                }catch(JSONException e){
                    Log.e(TAG, "JSON : " + e.getMessage());
                    System.out.println("catch");

                }

            }
        };

        Response.ErrorListener ecouteurErrorPraticien = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Erreur HTTP :" + " " + error.getMessage());
            }
        };

        JsonArrayRequest requetePraticien = new JsonArrayRequest(
                Request.Method.GET,
                Ip.ip+"/praticiens",
                null,
                ecouteurReponsePraticien,
                ecouteurErrorPraticien
        );


        //Motifs--------------------------------------------------------------------------------


        Response.Listener<JSONArray> ecouteurReponseMotifs = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try{

                    for(int i = 0; i < response.length(); i++ ){
                        Motifs unMotif = new Motifs();

                        unMotif.setNumero(Integer.parseInt(response.getJSONObject(i).getString("mot_num")));
                        unMotif.setLibelle(response.getJSONObject(i).getString("mot_libelle"));


                        motifsList.add(unMotif);

                    }



                    paquet.putParcelableArrayList("motifsList", (ArrayList<Motifs>) motifsList);

                    Log.v(TAG, "200 Ok");

                    if(!praticienList.isEmpty() && !motifsList.isEmpty()){
                        Log.v(TAG, "intention :" + "Intention vers SaisieRvActivity");

                        Intent intentionEnvoyer = new Intent(getApplicationContext(), SaisieRvActivity.class);

                        intentionEnvoyer.putExtras(paquet);

                        startActivity(intentionEnvoyer);
                    }




                }catch(JSONException e){
                    Log.e(TAG, "JSON : " + e.getMessage());
                    System.out.println("catch");

                }

            }
        };

        Response.ErrorListener ecouteurErrorMotifs = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Erreur HTTP :" + " " + error.getMessage());
            }
        };

        JsonArrayRequest requeteMotifs = new JsonArrayRequest(
                Request.Method.GET,
                Ip.ip+"/motifs",
                null,
                ecouteurReponseMotifs,
                ecouteurErrorMotifs
        );

        RequestQueue fileRequetesMotifs = Volley.newRequestQueue(this);

        fileRequetesMotifs.add(requetePraticien);
        fileRequetesMotifs.add(requeteMotifs);

    }

    public void deconnecter(View vue){
        Session.fermer();

        Intent intentionEnvoyer = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(intentionEnvoyer);
    }

}