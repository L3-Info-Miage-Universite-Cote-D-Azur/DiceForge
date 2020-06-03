package diceforge.serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import diceforge.joueur.Identite;
import diceforge.moteur.*;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static diceforge.echange.Protocole.*;

public class Serveur {

    public static StatistiqueGlobales statsGlobal;
    private Partie moteur;
    public SocketIOClient[] clients = new SocketIOClient[4];
    SocketIOServer server;
    private int nbClientsConnectes = 0;
    private int nbPartie;
    private int nbJouee = 0;

    public Serveur(Configuration config, Partie moteur, int nbPartie, StatistiqueGlobales statsP) {
        // le nombre de parties à jouer
        this.nbPartie = nbPartie;

        // le moteur
        this.moteur = moteur;

        // creation du serveur
        server = new SocketIOServer(config);

        // changement de protocole, la connexion est reportee a un message "identification"
        // server.addConnectListener(this);
        this.createListeners();

        this.statsGlobal = statsP;

        server.start(); // démarre un thread… le programme ne s’arrêtera pas tant que le serveur n’est pas terminé

    }

    private void createListeners() {
        server.removeAllListeners(IDENTIFICATION);
        server.addEventListener(IDENTIFICATION, Identite.class, new DataListener<Identite>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identite nom, AckRequest ackRequest) throws Exception {
                Log.println("le client "+nom.getNom()+" s'est connecte");
                receptionNouveauJoueur(socketIOClient, nom);
            }
        });
        server.removeAllListeners(DEMANDER_AU_SERVEUR_DACHETER_UNE_CARTE);
        server.addEventListener(DEMANDER_AU_SERVEUR_DACHETER_UNE_CARTE, Identite.class, new DataListener<Identite>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identite numero, AckRequest ackRequest) throws Exception {
                Log.println("Le joueur veut acheter une carte.");
                int leI, leJ;
                String leMessage = numero.getNom();
                String[] parts = leMessage.split(";");
                leI = Integer.parseInt(parts[0]);
                leJ = Integer.parseInt(parts[1]);
                moteur.getInventaire(moteur.getManche().getCurrentJoueur()).acheterUneCarte(moteur.getPlateau().listeIles[leI].getTableauCarteIle()[leJ]);
            }
        });
        server.removeAllListeners(DEMANDER_AU_SERVEUR_DE_FORGER_UNE_FACE);
        server.addEventListener(DEMANDER_AU_SERVEUR_DE_FORGER_UNE_FACE, Identite.class, new DataListener<Identite>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identite numero, AckRequest ackRequest) throws Exception {
                Log.println("Le joueur veut forger une face.");
                int leI1, leJ1, leI2, leJ2;
                String leMessage = numero.getNom();
                String[] parts = leMessage.split(":");
                leI1 = Integer.parseInt(parts[0].split(";")[0]);
                leJ1 = Integer.parseInt(parts[0].split(";")[1]);
                leI2 = Integer.parseInt(parts[1].split(";")[0]);
                leJ2 = Integer.parseInt(parts[1].split(";")[1]);
                Face FaceARemplacer, FaceRemplacante;
                FaceRemplacante = moteur.getForge().zoneF[leI2].getFaceZone()[leJ2];
                De de;
                if(leI1 == 0) {
                    de = moteur.getInventaire(moteur.getManche().getCurrentJoueur()).getDe1();
                    FaceARemplacer = de.getFaces()[leJ1];
                }else{
                    de = moteur.getInventaire(moteur.getManche().getCurrentJoueur()).getDe2();
                    FaceARemplacer = de.getFaces()[leJ1];

                }
                moteur.getInventaire(moteur.getManche().getCurrentJoueur()).changerFace(de, FaceARemplacer, FaceRemplacante);

            }
        });
        server.removeAllListeners(DIRE_QUE_LE_JOUEUR_A_FINI);
        server.addEventListener(DIRE_QUE_LE_JOUEUR_A_FINI, Identite.class, new DataListener<Identite>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identite nom, AckRequest ackRequest) throws Exception {
                Log.println("le joueur "+nom.getNom()+" a fini son tour");
                moteur.continuer();
            }
        });
    }

    protected void receptionNouveauJoueur(SocketIOClient socketIOClient, Identite id) {
        if(nbClientsConnectes < 4) {
            clients[nbClientsConnectes] = socketIOClient;
            nbClientsConnectes++;
            moteur.creationInventaire();
            // Log.println(monClient.getRemoteAddress());
        }
        if(nbClientsConnectes == 4) {
            moteur.commencer();
        }
    }


    public final static void main(String [] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        // config  com.corundumstudio.socketio.Configuration;
        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(10101);



        // le gestionnaire de tour
        Partie moteur;
        Serveur serveur;
        if(args.length != 0 && Integer.parseInt(args[0]) > 1){
            Log.setPrintLog(false);
            statsGlobal = new StatistiqueGlobales(Integer.parseInt(args[0]));
            moteur = new Partie(1);
            serveur = new Serveur(config, moteur, Integer.parseInt(args[0]), statsGlobal);
        }else{
            Log.setPrintLog(true);
            statsGlobal = new StatistiqueGlobales(1);
            moteur = new Partie(1);
            serveur = new Serveur(config, moteur, 1, statsGlobal);
        }
        moteur.setServeur(serveur);
    }

    /*
    // changement de protocole, la connexion est reportée à un message "identification"
    @Override
    public void onConnect(SocketIOClient socketIOClient) {
        monClient = socketIOClient;
        // Log.println(monClient.getRemoteAddress());
        moteur.ajouterJoueur(new Identité("nomTemp")); //@todo, recevoir le nom
        moteur.jouer();
    }
    */
    public void transfereDemandeDeJouer(int i) {
        clients[i].sendEvent(DEMANDER_AU_JOUEUR_DE_JOUER, moteur.getInventaire(i));
    }

    public void terminer() {
        for(SocketIOClient c : clients) c.disconnect();

        new Thread(new Runnable() {
            @Override
            public void run() {
                server.stop(); // à faire sur un autre thread que sur le thread de SocketIO
                Log.println("fin du serveur - fin");
            }
        }).start();

    }

    public int getNbClientsConnectes() {
        return nbClientsConnectes;
    }

    public void terminerPartie() {
        this.nbJouee++;
        if(this.nbJouee >= this.nbPartie) {
            System.out.println(statsGlobal.toString());
            this.terminer();
        }else{
            Partie moteur = new Partie(this.nbJouee+1);
            this.moteur = moteur;
            this.moteur.setServeur(this);
            for(int i=0; i<4; i++){
                this.moteur.creationInventaire();
            }
            this.createListeners();
            this.moteur.commencer();
        }
    }
}
