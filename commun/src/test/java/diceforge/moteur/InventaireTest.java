package diceforge.moteur;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class InventaireTest {

    Inventaire inventaire;
    StatistiquePartie stats;

    @Mock
    Random rand;

    @Mock
    Forge forge;

    @BeforeEach
    void setUp() {
        Plateau plateau = new Plateau();
        stats = new StatistiquePartie(1);
        inventaire = new Inventaire(1, rand, plateau, forge, stats);
    }

    @Test
    /**
     * On test que le lancement du de incremente bien les bonnes valeurs d'or pour les faces 1 à 5.
     */
    void testIncrementOr() {
        when(rand.nextInt(anyInt())).thenReturn(1, 2, 3, 4, 2, 0, 3, 1, 3, 4);
        for(int i=0; i<10; i++){
            inventaire.lancementDe(inventaire.getDe1());
            assertEquals(i+1, inventaire.getOr(), "l'or du joueur s'incremente de 1 pour les faces 1 a 5.");
        }
    }

    @Test
    /**
     * On test que le lancement du de increment bien les bonnes valeurs de fragments de lunes pour la face 6.
     */
    void testIncrementLune() {
        when(rand.nextInt(anyInt())).thenReturn(5);
        for(int i=0; i<6; i++){
            inventaire.lancementDe(inventaire.getDe1());
            assertEquals(i+1, inventaire.getFragmentsLunaires(), "les fragments de lune du joueur s'incremente de 1 pour la face 6.");
        }
    }

    @Test
    /**
     * On test que le nombre d'or ne dépasse pas 12.
     */
    void testMaxOr() {

        inventaire.incrementOr(12);
        assertEquals(12, inventaire.getOr());

        inventaire.incrementOr(3);
        assertEquals(12, inventaire.getOr());
    }

    @Test
    /**
     * On test que le nombre de fragments de lune ne dépasse pas 6.
     */
    void testMaxFragmentsLune() {
        inventaire.incrementFragmentsLunaires(3);
        assertEquals(3, inventaire.getFragmentsLunaires());

        inventaire.incrementFragmentsLunaires(3);
        assertEquals(6, inventaire.getFragmentsLunaires());

        inventaire.incrementFragmentsLunaires(3);
        assertEquals(6, inventaire.getFragmentsLunaires());
    }
    @Test
    /**
     * On test que le nombre de fragments de Soleil ne dépasse pas 6.
     */
    void testMaxFragmentsSoleil() {
        inventaire.incrementFragmentsSolaires(3);
        assertEquals(3, inventaire.getFragmentsSolaires());

        inventaire.incrementFragmentsSolaires(3);
        assertEquals(6, inventaire.getFragmentsSolaires());

        inventaire.incrementFragmentsSolaires(3);
        assertEquals(6, inventaire.getFragmentsSolaires());
    }

    @Test
    /**
     * On test l'achat d'une carte en supposant qu'on ne puisse pas acheter une carte par manque de fragments
     */
    void testAcheterCarteSansFragments() {
        assertEquals(0, inventaire.getFragmentsLunaires());
        assertEquals(0, inventaire.getNbDeCartes());
        inventaire.acheterUneCarte(Carte.CASQUE);
        assertEquals(0, inventaire.getNbDeCartes());
    }

    @Test
    /**
     * On test l'achat d'une carte en supposant qu'on puisse acheter la carte
     */
    void testAcheterCarteAvecFragments() {
        assertEquals(0, inventaire.getFragmentsLunaires());
        inventaire.incrementFragmentsLunaires(6);
        assertEquals(0, inventaire.getNbDeCartes());
        inventaire.acheterUneCarte(Carte.CASQUE);
        assertEquals(1, inventaire.getNbDeCartes());
        assertEquals(Carte.CASQUE, inventaire.getCartesDuJoueur()[0]);
    }

}
