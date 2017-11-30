/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noble.lukuvinkki.io;

import java.sql.SQLException;
import java.util.List;
import noble.lukuvinkki.TietokantaSetup;
import noble.lukuvinkki.dao.Tietokanta;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;
import noble.lukuvinkki.tietokohteet.VideoVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kari
 */
public class KayttoliittymaInterfaceTest {

    private KayttoliittymaInterface kayttisIO;
    private Tietokanta tietokanta;

    @Before
    public void setUp() {
        tietokanta = TietokantaSetup.alustaTestiTietokanta();
        kayttisIO = new KayttoliittymaInterface(tietokanta);
    }

    @After
    public void tearDown() {
        try {
            tietokanta.suljeYhteys();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @Test
    public void testaaKayttisIO() {
        assertTrue(kayttisIO != null);
    }
    
    @Test
    public void testaaKirjaVinkinLisaysHakuJaPoistoTietokannasta() throws SQLException {
        KirjaVinkki kirjaVinkki = new KirjaVinkki();
        kirjaVinkki.setTekija("Arto Paasilinna");
        kirjaVinkki.setNimi("Jäniksen vuosi");
        int id = kayttisIO.lisaaKirja(kirjaVinkki);
        assertTrue(id != -1);
        Vinkki kirjaVinkki2 = kayttisIO.haeYksiKirja(id);
        assertEquals(kirjaVinkki.getNimi(), kirjaVinkki2.getNimi());
        assertEquals(kirjaVinkki.getTekija(), kirjaVinkki2.getTekija());
        boolean poisto = kayttisIO.poistaKirja(id);
        assertTrue(poisto);
        assertTrue(kayttisIO.haeYksiKirja(id) == null);
    }
    
    @Test
    public void testaaHaeKaikkiKirjat() throws SQLException {
        List<Vinkki> kirjat = kayttisIO.haeKaikkiKirjat();
        assertEquals(0, kirjat.size());
        KirjaVinkki kirjaVinkki = new KirjaVinkki();
        kirjaVinkki.setTekija("Arto Paasilinna");
        kirjaVinkki.setNimi("Jäniksen vuosi");
        int id = kayttisIO.lisaaKirja(kirjaVinkki);
        kirjat = kayttisIO.haeKaikkiKirjat();
        assertEquals(1, kirjat.size());
        kayttisIO.poistaKirja(id);
        kirjat = kayttisIO.haeKaikkiKirjat();
        assertEquals(0, kirjat.size());
    }
    
    @Test
    public void testaaMuokkaaKirjaa() throws SQLException {
        KirjaVinkki kirjaVinkki = new KirjaVinkki();
        kirjaVinkki.setTekija("Arto Paasilinna");
        kirjaVinkki.setNimi("Jäniksen vuosi");
        int id = kayttisIO.lisaaKirja(kirjaVinkki);
        KirjaVinkki kirjaVinkki2 = new KirjaVinkki(id, "Seitsemän veljestä", "Aleksis Kivi");
        boolean muokkaus = kayttisIO.muokkaaKirja(kirjaVinkki2);
        assertTrue(muokkaus);
        KirjaVinkki kirjavinkki3 = kayttisIO.haeYksiKirja(id);
        assertEquals("Seitsemän veljestä", kirjavinkki3.getNimi());
        assertEquals("Aleksis Kivi", kirjavinkki3.getTekija());
    }
    
    @Test
    public void testaaVideonLisäysJaPoisto() throws SQLException {
        VideoVinkki videoVinkki = new VideoVinkki();
        videoVinkki.setNimi("Video");
        videoVinkki.setUrl("www.youtube.com");
        int id = kayttisIO.lisaaVideo(videoVinkki);
        assertTrue(id != -1);
        VideoVinkki vinkki2 = kayttisIO.haeYksiVideo(id);
        assertEquals(videoVinkki.getNimi(), vinkki2.getNimi());
        assertEquals(videoVinkki.getUrl(), vinkki2.getUrl());
        boolean poisto = kayttisIO.poistaVideo(id);
        assertTrue(poisto);
        assertTrue(kayttisIO.haeYksiVideo(id) == null);
    }
}