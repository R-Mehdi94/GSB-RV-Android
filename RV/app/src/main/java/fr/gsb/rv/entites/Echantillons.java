package fr.gsb.rv.entites;

import android.os.Parcel;
import android.os.Parcelable;

public class Echantillons implements Parcelable {

    private String medNomCommercial;
    private int quantite;


    public Echantillons(String medNomCommercial, int quantite) {
        this.medNomCommercial = medNomCommercial;
        this.quantite = quantite;
    }

    public Echantillons() {

    }


    protected Echantillons(Parcel in) {
        medNomCommercial = in.readString();
        quantite = in.readInt();
    }

    public static final Creator<Echantillons> CREATOR = new Creator<Echantillons>() {
        @Override
        public Echantillons createFromParcel(Parcel in) {
            return new Echantillons(in);
        }

        @Override
        public Echantillons[] newArray(int size) {
            return new Echantillons[size];
        }
    };

    public String getMedNomCommercial() {
        return medNomCommercial;
    }

    public void setMedNomCommercial(String medNomCommercial) {
        this.medNomCommercial = medNomCommercial;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }


    @Override
    public String toString() {
        return "Echantillons{" +
                "medNomCommercial='" + medNomCommercial + '\'' +
                ", quantite=" + quantite +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(medNomCommercial);
        dest.writeInt(quantite);
    }
}
