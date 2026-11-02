package rmiServer;

import rmiService.BanqueImpl;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.registry.LocateRegistry;

public class BanqueServer {
    public static void main(String[] args) {
        try {
            // i. Activer l'annuaire RMIRegistry
            LocateRegistry.createRegistry(1099);

            // ii. Créer l'instance
            BanqueImpl od = new BanqueImpl();
            System.out.println("Objet distant créé : " + od);

            // iii. Enregistrement via JNDI
            Context ctx = new InitialContext();
            ctx.rebind("banque", od);

            System.out.println("Serveur Banque RMI démarré sur le port 1099 avec JNDI.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}