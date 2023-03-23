package fr.gsb.rv.technique;

public enum Mois {
    
    Janvier, Février, Mars, Avril, Mai, Juin, Juillet, Août, Septembre, Octobre, Novembre, Décembre;

    public int getNumero() {
        return this.ordinal() + 1; // ajouter 1 pour obtenir le numéro de mois
    }
}
