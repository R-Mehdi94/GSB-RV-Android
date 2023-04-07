package fr.gsb.rv.entites;

import android.os.Parcel;
import android.os.Parcelable;

public class Praticien implements Parcelable {
    private int numero;
    private String nom;
    private String prenom;
    private String ville;

    public Praticien(){}

    protected Praticien(Parcel in) {
        numero = in.readInt();
        nom = in.readString();
        prenom = in.readString();
        ville = in.readString();
    }

    public static final Creator<Praticien> CREATOR = new Creator<Praticien>() {
        @Override
        public Praticien createFromParcel(Parcel in) {
            return new Praticien(in);
        }

        @Override
        public Praticien[] newArray(int size) {
            return new Praticien[size];
        }
    };

    @Override
    public String toString() {
        return ""+nom;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(numero);
        dest.writeString(nom);
        dest.writeString(prenom);
        dest.writeString(ville);
    }
}
