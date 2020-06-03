package diceforge.joueur;

import diceforge.echange.ToJSON;
import org.json.JSONException;
import org.json.JSONObject;

public class Identite implements ToJSON {
    private String nom;

    public Identite() {
        this("sans nom");
    }

    public Identite(String nom) {
        setNom(nom);
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj =  new JSONObject();
        try {
            obj.put("nom", getNom());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
