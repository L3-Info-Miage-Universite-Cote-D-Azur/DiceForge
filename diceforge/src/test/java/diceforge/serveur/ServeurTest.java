package diceforge.serveur;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import diceforge.joueur.Identite;
import diceforge.moteur.Partie;
import diceforge.moteur.StatistiqueGlobales;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ServeurTest {

    private  Serveur serveur;

    @Mock
    Partie moteur;

    @Mock
    SocketIOClient socket;

    @BeforeEach
    void setup(){
        // config  com.corundumstudio.socketio.Configuration;
        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(10101);
        StatistiqueGlobales statistiqueGlobales = new StatistiqueGlobales(9);
        serveur = new Serveur(config, moteur, 1, statistiqueGlobales);
        moteur.setServeur(serveur);
    }

    @Test
    void testReceptionNouveauJoueurs() {
        serveur.receptionNouveauJoueur(socket, new Identite("Joueur 1"));
        assertEquals(1, serveur.getNbClientsConnectes());
        serveur.receptionNouveauJoueur(socket, new Identite("Joueur 2"));
        assertEquals(2, serveur.getNbClientsConnectes());
        serveur.receptionNouveauJoueur(socket, new Identite("Joueur 3"));
        assertEquals(3, serveur.getNbClientsConnectes());
        serveur.receptionNouveauJoueur(socket, new Identite("Joueur 3"));
        assertEquals(4, serveur.getNbClientsConnectes());
        serveur.receptionNouveauJoueur(socket, new Identite("Joueur 3"));
        assertEquals(4, serveur.getNbClientsConnectes());
    }

}