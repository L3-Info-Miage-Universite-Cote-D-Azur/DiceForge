package diceforge.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import diceforge.echange.ToJSON;
import diceforge.joueur.Joueur;
import diceforge.moteur.Inventaire;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.io.IOException;

import static diceforge.echange.Protocole.DEMANDER_AU_JOUEUR_DE_JOUER;


public class Client implements Emitter.Listener {

    private final Socket mSocket;
    private final Joueur joueur;

    public Client(Socket mSocket, Joueur joueur) {
        this.mSocket = mSocket;
        this.joueur = joueur;
        mSocket.on(DEMANDER_AU_JOUEUR_DE_JOUER, this);

        mSocket.on("disconnect", (objects) -> {
            terminer();
        });


    }

    public void connnexion() {
        mSocket.connect();
    }

    /*public final static void main(String [] args) throws URISyntaxException {


        Socket mSocket = IO.socket("http://127.0.0.1:10101");
        //"http://192.168.1.23:10101";

        Log.println("Le joueur 1 a ete cree");
        Joueur j = new Joueur(1);

        Log.println("le client a ete cree");
        Client c = new Client(mSocket, j);

        j.setClient(c);

        // changement de protocole, la connexion est reportée à un message "identification"
        // c.connnexion();
    }*/

    @Override
    public void call(Object... objects) {
        Inventaire inventaire = null;

        try {
            inventaire = new ObjectMapper().readValue(objects[0].toString(), Inventaire.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        joueur.joue(inventaire);
    }

    /**
     * méthode générique d'envoi de message (sans json) au serveur
     * @param msg le message (à connaitre par la classe utilisatrice) à envoyer
     */
    public void transfereMessage(String msg) {
        mSocket.emit(msg);
    }

    public void transfereMessage(String msg, ToJSON obj) {
        mSocket.emit(msg, obj.toJSON());
    }

    /*
    alternative à transfereMessage : dans ce cas, la classe utilisatrice ne connait pas le protocole d'échange
    entre le client et le serveur
    public void demanderÀLancerLesDé() {
        mSocket.emit("jouer");
    }
     */

    public void terminer() {


        // pour ne pas être sur le thread de SocketIO
        new Thread(new Runnable() {

            @Override
            public void run() {
                mSocket.off(DEMANDER_AU_JOUEUR_DE_JOUER);
                mSocket.off("disconnect");
                mSocket.close();
                // hack pour arrêter plus vite (sinon attente de plusieurs secondes
                // à ne pas faire si on veut lancer 500 parties
                System.exit(0);
            }
        }).start() ;



    }

}
