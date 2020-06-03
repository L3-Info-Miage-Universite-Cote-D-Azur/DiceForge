package diceforge;

import diceforge.client.Client;
import diceforge.joueur.*;
import diceforge.serveur.Serveur;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class Lanceur implements Runnable {


    private int j, ia;

    public Lanceur(int j, int ia) {
        this.j = j;
        this.ia = ia;
    }

    public final static void main(String [] args) {

        //  la façon de lancer... forcer à une nouvelle socket, sinon c'était la même socket pour les deux clients
        //  la façon de lancer... forcer à une nouvelle socket, sinon c'était la même socket pour les deux clients


        Thread joueur1 = new Thread(new Lanceur(0, 0));
        Thread joueur2 = new Thread(new Lanceur(1, 1));
        Thread joueur3 = new Thread(new Lanceur(2, 2));
        Thread joueur4 = new Thread(new Lanceur(3, 3));





        Thread moteur = new Thread(new Runnable() {
            @Override
            public void run() {
                String [] argsServeur = args;
                Serveur.main(argsServeur);
            }
        });

        moteur.start();
        joueur1.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        joueur2.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        joueur3.start();
        try {
            TimeUnit.SECONDS.sleep(2  );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        joueur4.start();

    }

    @Override
    public void run() {
        Socket mSocket = null;
        try {
            IO.Options options = new IO.Options();
            options.forceNew = true;
            mSocket = IO.socket("http://127.0.0.1:10101", options);
            //Log.println(mSocket);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        //"http://192.168.1.23:10101";
        Joueur j;
        switch(this.ia){
            case 0:
                j = new Joueur(this.j, new IARandom());
                break;
            case 1:
                j = new Joueur(this.j, new IAFirst());
                break;
            case 2:
                j = new Joueur(this.j, new IALast());
                break;
            case 3:
                j = new Joueur(this.j, new IAThink());
                break;
            default:
                j = new Joueur(this.j, new IARandom());
        }

        Client c = new Client(mSocket, j);

        j.setClient(c);
    }
}