package diceforge.moteur;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeTest {
    De de;

    @Mock
    Random rand;

    @BeforeEach
    void setup() {
        de = new De(rand);
    }

    @Test
    /**
     * On teste le resultat du de lorsqu'on tombe sur les 5 premieres faces
     */
    void testface1a5(){
        when(rand.nextInt(anyInt())).thenReturn(1, 2, 3, 4, 2, 0, 3, 1, 3, 4);
        for(int i=0; i<10; i++){
            assertEquals(Face.OR1, de.lancer(), "le de donne 1 or pour les faces 1 a 5.");
        }
    }

    @Test
    /**
     * On teste le resultat du de lorsqu'on tombe sur la face 6
     */
    void testface6(){
        when(rand.nextInt(anyInt())).thenReturn(5);
        for(int i=0; i<10; i++){
            assertEquals(Face.LUNE1, de.lancer(), "le de donne 1 fragment de lune pour la face 6.");
        }
    }

    @Test
    /**
     * On teste le changement d'une face
     */
    void testChangerFace(){
        assertEquals(Face.LUNE1, de.faces[5]);
        de.remplacerFace(Face.LUNE1, Face.SOLEIL1);
        assertEquals(Face.SOLEIL1, de.faces[5]);
        when(rand.nextInt(anyInt())).thenReturn(5);
        for(int i=0; i<10; i++){
            assertEquals(Face.SOLEIL1, de.lancer(), "le de donne 1 fragment de lune pour la face 6.");
        }
    }
}