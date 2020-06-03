package diceforge.moteur;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ZoneForgeTest {

    ZoneForge zoneForge;

    @BeforeEach
    void setup(){
        Face[] faces = {Face.OR3, Face.OR3, Face.LUNE1, Face.LUNE1};
        zoneForge = new ZoneForge(2, faces);
    }

    @Test
    void enleverFace() {
        int length =  zoneForge.getFaceZone().length;
        zoneForge.enleverFace(Face.OR3);
        assertEquals(length-1, zoneForge.getFaceZone().length);
        zoneForge.enleverFace(Face.OR3);
        assertEquals(length-2, zoneForge.getFaceZone().length);
        zoneForge.enleverFace(Face.OR3);
        assertEquals(length-2, zoneForge.getFaceZone().length);
        zoneForge.enleverFace(Face.LUNE1);
        assertEquals(length-3, zoneForge.getFaceZone().length);
    }
}