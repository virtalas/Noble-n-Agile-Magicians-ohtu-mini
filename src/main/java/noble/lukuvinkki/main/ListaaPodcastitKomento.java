package noble.lukuvinkki.main;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.tietokohteet.Vinkki;

public class ListaaPodcastitKomento extends Komento {

    public ListaaPodcastitKomento(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO) {
        super(nimi, komento, teksti, io, kayttisIO);
    }


    @Override
    public void komento() {
        try {
            List<Vinkki> kaikkiPodcastit = kayttisIO.haeKaikkiPodcastit();
            if (tarkistaOnkoListaTyhjaTaiNull(kaikkiPodcastit)) {
                return;
            }
            for (Vinkki vinkki : kaikkiPodcastit) {
                io.print(vinkki.toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
