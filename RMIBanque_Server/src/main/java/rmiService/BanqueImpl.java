package rmiService;

import metier.Compte;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class BanqueImpl extends UnicastRemoteObject implements IBanque {
    private Map<Integer, Compte> comptes;

    public BanqueImpl() throws RemoteException {
        super();
        this.comptes = new HashMap<>();
    }

    @Override
    public String creerCompte(Compte c) throws RemoteException {
        if (comptes.containsKey(c.getCode())) {
            return "Erreur : Le compte avec le code " + c.getCode() + " existe déjà.";
        }
        comptes.put(c.getCode(), c);
        return "Succès : Compte " + c.getCode() + " créé avec succès.";
    }

    @Override
    public String getInfoCompte(int code) throws RemoteException {
        Compte c = comptes.get(code);
        if (c == null) {
            return "Erreur : Compte introuvable.";
        }
        return c.toString();
    }
}