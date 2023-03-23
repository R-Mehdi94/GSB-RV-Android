package fr.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.gsb.rv.technique.Session;

public class MenuRvActivity extends AppCompatActivity {

    static final String TAG = "GSB_Menu_Rv_Activity";
    TextView tvNomPrenom;
    Button consulter;
    Button saisir;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_rv);

        tvNomPrenom = findViewById(R.id.nomPrenom);
        tvNomPrenom.setText(Session.getSession().getLeVisiteur().getPrenom() + " " + Session.getSession().getLeVisiteur().getNom());
        consulter = findViewById(R.id.consulter);
        saisir = findViewById(R.id.saisir);

        Log.v(TAG, "onCreate :" + "Création de l'activité MenuRV");

    }

    public void consulter(View vue){

        Log.v(TAG, "intention :" + "Intention vers RechercheRvActivity");
        Intent intentionEnvoyer = new Intent(getApplicationContext(), RechercheRvActivity.class);
        startActivity(intentionEnvoyer);

    }

}