package diceforge.joueur;

import diceforge.joueur.Joueur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JoueurTest {

    private Joueur joueur;

    @Mock
    Random rand;

    @BeforeEach
    void setup() {
        joueur = new Joueur(1, new IARandom());
    }


}