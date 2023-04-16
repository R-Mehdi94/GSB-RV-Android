package fr.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import fr.gsb.rv.entites.Motifs;
import fr.gsb.rv.entites.Praticien;
import fr.gsb.rv.entites.RapportVisite;
import fr.gsb.rv.entites.Visiteur;
import fr.gsb.rv.technique.Ip;
import fr.gsb.rv.technique.Session;

public class SaisieRvActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener{


    static final String TAG = "GSB_Saisie_Rv_Activity";

    public GregorianCalendar laDateCommande;
    public static List<Praticien> praticienList = new ArrayList<>();
    public static List<Motifs> motifsList = new ArrayList<>();
    public List<Integer> coefConfiance = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));

    public String selectedElementPraticien;
    public String selectedElementMotif;
    public String selectedElementCoef;
    private int numPraticien;
    private int numMotifs;



    TextView tvDateSelectionnee;
    Button btnDateCommande;
    Spinner spPraticien;
    Spinner spMotif;
    EditText etBilan;
    Spinner spCoefConf;







    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Bundle paquet = this.getIntent().getExtras();


        praticienList = paquet.getParcelableArrayList("praticienList");
        motifsList = paquet.getParcelableArrayList("motifsList");

        //System.out.println(praticienList + " " + motifsList);




        //Création---------------------------------------------------------------------

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie_rv);

        etBilan = findViewById(R.id.etBilan);




        //TextViewDate----------------------------------------------------------------------------------------------

        tvDateSelectionnee = findViewById(R.id.tvSaisie);
        tvDateSelectionnee.setText("Date de la visite :");



        //SpinnerPraticien------------------------------------------------------------------------------------------------------

        spPraticien = findViewById(R.id.spPraticiens);
        ArrayAdapter<Praticien> adapterPraticien = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, praticienList);
        adapterPraticien.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spPraticien.setAdapter(adapterPraticien);




        //SpinnerMotifs------------------------------------------------------------------------------------------------------

        spMotif = findViewById(R.id.spMotifs);
        ArrayAdapter<Motifs> adapterMotifs = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, motifsList);
        adapterMotifs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spMotif.setAdapter(adapterMotifs);

        //SpinnerCoefConfiance------------------------------------------------------------------------------------------------------

        spCoefConf = findViewById(R.id.spCoefConf);
        ArrayAdapter<Integer> adapterConfiance = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, coefConfiance);
        adapterConfiance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCoefConf.setAdapter(adapterConfiance);


        spPraticien.setOnItemSelectedListener(this);
        spMotif.setOnItemSelectedListener(this);
        spCoefConf.setOnItemSelectedListener(this);


        //ButtonDate-----------------------------------------------------------

        btnDateCommande = findViewById(R.id.btnDateCommande);

        Button btnDateCommande = findViewById(R.id.btnDateCommande);

        btnDateCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionnerDateCommande(v);
            }
        });





    }

    public void selectionnerDateCommande(View vue){
        GregorianCalendar ajd = new GregorianCalendar();

        int jour = ajd.get(Calendar.DAY_OF_MONTH);
        int mois = ajd.get(Calendar.MONTH);
        int annee = ajd.get(Calendar.YEAR);

        new DatePickerDialog(this,this,annee,mois,jour).show();

    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        @SuppressLint("DefaultLocale") String dateCommande = String.format("%02d/%02d/%04d",
                                            dayOfMonth,
                                            month+1,
                                            year);

        tvDateSelectionnee.setText("Date de la visite : " + dateCommande);
        laDateCommande = new GregorianCalendar(year, month, dayOfMonth);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Vérifier quel spinner a déclenché l'événement
        if (parent == spPraticien) {
            // Gérer la sélection d'un élément dans le spinner spPraticien
            selectedElementPraticien = spPraticien.getItemAtPosition(position).toString();
        } else if (parent == spMotif) {
            // Gérer la sélection d'un élément dans le spinner spMotif
            selectedElementMotif = spMotif.getItemAtPosition(position).toString();
        } else if (parent == spCoefConf) {
            // Gérer la sélection d'un élément dans le spinner spCoefConf
            selectedElementCoef = spCoefConf.getItemAtPosition(position).toString();
        }
    }



    public void valider(View vue) throws JSONException {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(laDateCommande.getTime());
        String bilan = String.valueOf(etBilan.getText());

        for (Praticien praticien: praticienList) {
            if(Objects.equals(praticien.getNom(), selectedElementPraticien)){
                numPraticien = praticien.getNumero();
            }
        }

        for (Motifs motifs: motifsList) {
            if(Objects.equals(motifs.getLibelle(), selectedElementMotif)){
                numMotifs = motifs.getNumero();
            }
        }


        Log.v(TAG, selectedElementCoef);

        JSONObject params = new JSONObject();

        params.put("matricule", Session.getSession().getLeVisiteur().getMatricule());
        params.put("praticien", numPraticien);
        params.put("visite", date);
        params.put("bilan", bilan);
        params.put("coefConfiance", selectedElementCoef);
        params.put("motif", numMotifs);




        JsonObjectRequest requete = new JsonObjectRequest(
                Request.Method.POST,
                Ip.ip+"/rapports",
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, "200 Ok Posts");
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Erreur HTTP :" + " " + error.getMessage());
                        System.out.println("erreur");
                    }
                });


        // Ajoutez la requête à la file d'attente de Volley
        RequestQueue fileRequetes = Volley.newRequestQueue(this);
        fileRequetes.add(requete);





    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}