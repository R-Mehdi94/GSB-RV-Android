package fr.gsb.rv.entites;

import java.time.LocalDate;

public class RapportVisite {
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
}
