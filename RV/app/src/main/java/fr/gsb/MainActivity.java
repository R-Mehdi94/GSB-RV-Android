package fr.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import fr.gsb.rv.entites.Visiteur;
import fr.gsb.rv.modeles.ModeleGsb;
import fr.gsb.rv.technique.Ip;
import fr.gsb.rv.technique.Session;

public class MainActivity extends AppCompatActivity {

     static final String TAG = "GSB_MAIN_ACTIVITY";
     EditText etMatricule;
     EditText etMdp;
     Button bSeConnecter;
     Button bAnnuler;
     //ModeleGsb modele = new ModeleGsb();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMatricule = findViewById(R.id.etMatricule);
        etMdp = findViewById(R.id.etMdo);
        bSeConnecter = findViewById(R.id.bValider);
        bAnnuler = findViewById(R.id.bAnnuler);
        Log.v(TAG, "onCreate :" + "Création de l'activité principal");

    }

    public void seConnecter(View vue){



        Response.Listener<JSONObject> ecouteurReponse = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{

                    Visiteur visiteur = new Visiteur();
                    visiteur.setMatricule(response.getString("vis_matricule"));
                    visiteur.setNom(response.getString("vis_nom"));
                    visiteur.setPrenom(response.getString("vis_prenom"));
                    Session.ouvrir(visiteur);

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
                System.out.println("erreur");
            }
        };


        JsonObjectRequest requete = new JsonObjectRequest(
                Request.Method.GET,
                Ip.ip +"/visiteurs/"+etMatricule.getText().toString()+"/"+etMdp.getText().toString(),
                null,
                ecouteurReponse,
                ecouteurError
        );

        RequestQueue fileRequetes = Volley.newRequestQueue(this);
        fileRequetes.add(requete);



        /*


             Visiteur visiteur = modele.seConnecter(etMatricule.getText().toString(), etMdp.getText().toString());

            if(visiteur != null){



            Session.ouvrir(visiteur);
            Log.v(TAG, "seConnecter :" + "Visiteur Connecter");
            System.out.println(visiteur.getNom() + " " + visiteur.getMatricule());
        }else if(etMatricule.getText().toString().equals("") || etMdp.getText().toString().equals("")){

            Log.v(TAG, "seConnecter :" + "Tentative de co échouer");

            Context context = getApplicationContext();
            CharSequence text = "Identifiant ou mot de passe manquant";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            }else {
                Log.v(TAG, "seConnecter :" + "Tentative de co échouer");

                Context context = getApplicationContext();
                CharSequence text = "L'identifiant ou le mot de passe que vous avez saisie est incorrecte";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }*/
    }

    public void annuler(View vue){
        etMatricule.setText("");
        etMdp.setText("");
    }



}