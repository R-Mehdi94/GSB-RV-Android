package fr.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;

public class RechercheRvActivity extends AppCompatActivity {

    static final String TAG = "GSB_Recherche_Rv_Activity";

    private static final String [] lesMois = {"Janvier", "Février", "Mois", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Décembre"};
    private static final Integer [] lesAnnees = {2019,2020,2021,2022,2023};



    Spinner spMois;
    Spinner spAnnees;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_rv);

        Log.v(TAG, "onCreate :" + "Création de l'activité RechercheRV");


        spMois = (Spinner) findViewById(R.id.spMois);

        ArrayAdapter<String> aaMois = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                lesMois
        );

        aaMois.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line
        );

        spMois.setAdapter(aaMois);


        spAnnees = (Spinner) findViewById(R.id.spAnnee);

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


        String valeurMois = spMois.getSelectedItem().toString();
        int numeroMois = Arrays.asList(lesMois).indexOf(valeurMois) + 1; // Ajouter 1 pour obtenir le numéro du mois
        String valeurAnnee = spAnnees.getSelectedItem().toString();

        Bundle paquet = new Bundle();
        paquet.putInt("moisInt", numeroMois);
        paquet.putString("mois", valeurMois);
        paquet.putString("annee", valeurAnnee);

        Log.v(TAG, "onCreate :" + "Création du Bundle RechercheRV");


        Intent intentionEnvoyer = new Intent(getApplicationContext(), ListeRvActivity.class);

        intentionEnvoyer.putExtras(paquet);

        startActivity(intentionEnvoyer);

        Log.v(TAG, "intention :" + "Intention vers ListeRvActivity");

    }

}