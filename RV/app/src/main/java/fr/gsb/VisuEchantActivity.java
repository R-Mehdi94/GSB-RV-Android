package fr.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import fr.gsb.rv.entites.Echantillons;

public class VisuEchantActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView lvEchant;


    public List<Echantillons> echantillonsList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            Bundle paquet = this.getIntent().getExtras();

            echantillonsList = paquet.getParcelableArrayList("echantillonsList");

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_visu_echant);

            lvEchant = findViewById(R.id.lvEchant);



            ArrayAdapter<Echantillons> adaptateur = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    echantillonsList
            );

            lvEchant.setAdapter(adaptateur);

            lvEchant.setOnItemClickListener(this);

            lvEchant.setSelector(new ColorDrawable(Color.TRANSPARENT));


        }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {}
}