package rmiClient;

import metier.Compte;
import rmiService.IBanque;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Date;
import java.util.Scanner;

public class BanqueClient {
    public static void main(String[] args) {
        try {
            // Recherche JNDI de l'objet distant
            Context ctx = new InitialContext();
            IBanque stub = (IBanque) ctx.lookup("banque");

            Scanner sc = new Scanner(System.in);
            int choix = 0;

            System.out.println("Connexion au serveur banque réussie !");

            while (choix != 3) {
                System.out.println("\n--- MENU BANQUE ---");
                System.out.println("1. Créer un compte");
                System.out.println("2. Consulter un compte");
                System.out.println("3. Quitter");
                System.out.print("Votre choix : ");
                choix = sc.nextInt();

                switch (choix) {
                    case 1:
                        System.out.print("Entrez le code du nouveau compte : ");
                        int code = sc.nextInt();
                        System.out.print("Entrez le solde initial : ");
                        double solde = sc.nextDouble();

                        Compte nouveauCompte = new Compte(code, solde, new Date());
                        String resCreation = stub.creerCompte(nouveauCompte);
                        System.out.println("Réponse du Serveur : " + resCreation);
                        break;

                    case 2:
                        System.out.print("Entrez le code du compte à consulter : ");
                        int codeConsult = sc.nextInt();

                        String resConsultation = stub.getInfoCompte(codeConsult);
                        System.out.println("Réponse du Serveur : " + resConsultation);
                        break;

                    case 3:
                        System.out.println("Fermeture du client. Au revoir !");
                        break;

                    default:
                        System.out.println("Choix invalide. Veuillez choisir 1, 2 ou 3.");
                }
            }

            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}