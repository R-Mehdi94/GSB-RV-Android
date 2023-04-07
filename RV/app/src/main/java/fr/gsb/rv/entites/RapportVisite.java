package fr.gsb.rv.entites;

import android.os.Parcel;
import android.os.Parcelable;

public class RapportVisite implements Parcelable {
    private int numero;

    private String dateVisite;

    private String bilan;

    private String nomPraticien;

    private String prenomPraticien;

    private String cpPraticien;

    private String villePraticen;

    public RapportVisite(){}

    public RapportVisite(int numero, String dateVisite, String bilan, String nomPraticien, String prenomPraticien, String cpPraticien, String villePraticen) {
        this.numero = numero;
        this.dateVisite = dateVisite;
        this.bilan = bilan;
        this.nomPraticien = nomPraticien;
        this.prenomPraticien = prenomPraticien;
        this.cpPraticien = cpPraticien;
        this.villePraticen = villePraticen;
    }

    protected RapportVisite(Parcel in) {
        numero = in.readInt();
        dateVisite = in.readString();
        bilan = in.readString();
        nomPraticien = in.readString();
        prenomPraticien = in.readString();
        cpPraticien = in.readString();
        villePraticen = in.readString();
    }

    public static final Creator<RapportVisite> CREATOR = new Creator<RapportVisite>() {
        @Override
        public RapportVisite createFromParcel(Parcel in) {
            return new RapportVisite(in);
        }

        @Override
        public RapportVisite[] newArray(int size) {
            return new RapportVisite[size];
        }
    };

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(String dateVisite) {
        this.dateVisite = dateVisite;
    }

    public String getBilan() {
        return bilan;
    }

    public void setBilan(String bilan) {
        this.bilan = bilan;
    }

    public String getNomPraticien() {
        return nomPraticien;
    }

    public void setNomPraticien(String nomPraticien) {
        this.nomPraticien = nomPraticien;
    }

    public String getPrenomPraticien() {
        return prenomPraticien;
    }

    public void setPrenomPraticien(String prenomPraticien) {
        this.prenomPraticien = prenomPraticien;
    }

    public String getCpPraticien() {
        return cpPraticien;
    }

    public void setCpPraticien(String cpPraticien) {
        this.cpPraticien = cpPraticien;
    }

    public String getVillePraticen() {
        return villePraticen;
    }

    public void setVillePraticen(String villePraticen) {
        this.villePraticen = villePraticen;
    }


    @Override
    public String toString() {
        return "RapportVisite{" +
                "numero=" + numero +
                ", dateVisite=" + dateVisite +
                ", bilan='" + bilan + '\'' +
                ", nomPraticien='" + nomPraticien + '\'' +
                ", prenomPraticien='" + prenomPraticien + '\'' +
                ", cpPraticien='" + cpPraticien + '\'' +
                ", villePraticen='" + villePraticen + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(numero);
        dest.writeString(dateVisite);
        dest.writeString(bilan);
        dest.writeString(nomPraticien);
        dest.writeString(prenomPraticien);
        dest.writeString(cpPraticien);
        dest.writeString(villePraticen);
    }
}
