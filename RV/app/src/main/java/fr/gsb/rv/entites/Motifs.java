package fr.gsb.rv.entites;

import android.os.Parcel;
import android.os.Parcelable;

public class Motifs implements Parcelable {

    private int numero;
    private String libelle;

    public Motifs(){}

    protected Motifs(Parcel in) {
        numero = in.readInt();
        libelle = in.readString();
    }

    public static final Creator<Motifs> CREATOR = new Creator<Motifs>() {
        @Override
        public Motifs createFromParcel(Parcel in) {
            return new Motifs(in);
        }

        @Override
        public Motifs[] newArray(int size) {
            return new Motifs[size];
        }
    };

    @Override
    public String toString() {
        return ""+libelle;

    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(numero);
        dest.writeString(libelle);
    }
}
