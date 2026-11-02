# TP5 DAR — Développement d'Applications Réparties (Java RMI)

## 📌 Aperçu du projet
Ce dépôt contient la réalisation des deux activités du TP5 sur les Invocations de Méthodes Distantes (RMI) en Java :
1. **Activité 5-1** : Service de conversion de devise (Euro vers Dinar) via `Naming` (RMI classique).
2. **Activité 5-2** : Service de banque (Création et consultation de comptes) avec intégration de **JNDI** (Java Naming and Directory Interface) et interaction client via **Scanner**.

## 🛠️ Prérequis
- **Java JDK** (version 8 ou supérieure)
- **IntelliJ IDEA** (ou Eclipse)
- **Maven** (inclus avec IntelliJ)
- **Git**

## 📂 Arborescence globale de l'espace de travail
Le projet est divisé en 4 sous-projets Maven :
```text
TP5_DevAppReparties/
├── RMIConversion_Server/  (Activité 5-1 - Serveur)
├── RMIConversion_Client/  (Activité 5-1 - Client)
├── RMIBanque_Server/      (Activité 5-2 - Serveur)
└── RMIBanque_Client/      (Activité 5-2 - Client avec Scanner)
```

---

# 🟢 Activité 5-1 : Conversion de Devise

## Architecture
- **Interface (`IConversion`)** : Définit la méthode `convertirMontant(double mt)` et hérite de `Remote`.
- **Implémentation (`ConversionImpl`)** : Hérite de `UnicastRemoteObject` et implémente la logique de conversion (ex: `mt * 3.3`).
- **Serveur (`ConversionServer`)** : Crée le registre RMI sur le port 1099 et publie l'objet via `Naming.rebind("rmi://localhost:1099/BK", od)`.
- **Client (`ConversionClient`)** : Recherche l'objet distant via `Naming.lookup` et invoque la méthode.

## Exécution
1. Lancez `ConversionServer.java`.
2. Lancez `ConversionClient.java`.
3. Le client affiche le résultat de la conversion dans la console.

### 📸 Captures d'écran (Activité 5-1)
*(Remplacez ces textes par vos captures d'écran IntelliJ)*
- **Serveur en cours d'exécution :** `![Capture Serveur Conversion](lien_image)` 
- **Client en cours d'exécution :** `![Capture Client Conversion](lien_image)`

---

# 🔵 Activité 5-2 : Service de Banque (RMI + JNDI + Scanner)

## Architecture
- **Modèle (`Compte`)** : Classe `Serializable` contenant le code (int), le solde (double) et la date de création (Date).
- **Interface (`IBanque`)** : Définit `creerCompte(Compte c)` et `getInfoCompte(int code)`.
- **Implémentation (`BanqueImpl`)** : Utilise une `HashMap` pour stocker les comptes en mémoire et gérer les doublons.
- **Serveur (`BanqueServer`)** : Utilise l'API **JNDI** (`InitialContext`) au lieu de `Naming` pour enregistrer l'objet.
- **Client (`BanqueClient`)** : Utilise **JNDI** pour la recherche et intègre un **Scanner** pour permettre à l'utilisateur de choisir entre créer un compte ou le consulter via le terminal.

## Configuration JNDI
Pour que JNDI fonctionne, un fichier `jndi.properties` a été placé dans le dossier `src/main/resources` **des deux projets** (Serveur et Client) :
```properties
java.naming.factory.initial=com.sun.jndi.rmi.registry.RegistryContextFactory
java.naming.provider.url=rmi://localhost:1099
```

## Partage du code (Comment lier le Client au Serveur dans IntelliJ)
Pour que le client puisse utiliser l'interface sans avoir accès à la logique métier (`BanqueImpl`), le projet Serveur génère un `.jar` API. Voici comment faire le lien directement depuis l'interface d'IntelliJ :

1. **Ouvrez le projet Serveur (`RMIBanque_Server`)** dans IntelliJ.
2. Sur le côté droit de l'écran, cliquez sur l'onglet **Maven** pour ouvrir la fenêtre d'outils.
3. Dépliez le nom de votre projet, puis le dossier **Lifecycle**.
4. Double-cliquez sur **`clean`** (pour nettoyer les anciennes compilations).
5. Double-cliquez sur **`install`** (cela compile le serveur et sauvegarde le fichier JAR dans le dossier caché Maven de votre ordinateur).
6. Attendez que la console IntelliJ affiche le message vert **`BUILD SUCCESS`**.
7. Ensuite, ouvrez votre projet Client (`RMIBanque_Client`).
8. Dans l'onglet **Maven** à droite, cliquez sur le bouton **Reload All Maven Projects** (l'icône avec deux flèches circulaires). 
9. La dépendance sera automatiquement reconnue et les erreurs rouges disparaîtront.

## Exécution & Test interactif (Scanner)
1. Lancez `BanqueServer.java`.
2. Lancez `BanqueClient.java`. 
3. Le terminal vous affichera un menu interactif :

### Exemple d'interaction :
```text
Connexion au serveur banque réussie !

--- MENU BANQUE ---
1. Créer un compte
2. Consulter un compte
3. Quitter
Votre choix : 1
Entrez le code du nouveau compte : 101
Entrez le solde initial : 1500.0
Réponse du Serveur : Succès : Compte 101 créé avec succès.

--- MENU BANQUE ---
1. Créer un compte
2. Consulter un compte
3. Quitter
Votre choix : 2
Entrez le code du compte à consulter : 101
Réponse du Serveur : Code: 101 | Solde: 1500.0 | Date: Mon Nov 02 22:43:26 WAT 2026

--- MENU BANQUE ---
1. Créer un compte
2. Consulter un compte
3. Quitter
Votre choix : 3
Fermeture du client. Au revoir !
```

### 📸 Captures d'écran (Activité 5-2)
*(Remplacez ces textes par vos captures d'écran IntelliJ)*
- **Serveur Banque (JNDI) :** `![Capture Serveur Banque](lien_image)`
- **Client Banque interactif (Scanner) :** `![Capture Client Banque](lien_image)`

---

## 💡 Synthèse
Ce TP a permis de mettre en œuvre le cycle de vie complet d'un service distant :
1. **Définition** d'un contrat via une interface héritant de `Remote`.
2. **Implémentation** de la logique métier côté serveur (`UnicastRemoteObject`).
3. **Enregistrement** dans un annuaire (via `Naming` pour l'Act 1, et via `InitialContext` (JNDI) pour l'Act 2).
4. **Invocation** et communication réseau par le client.

L'intégration de JNDI et la séparation du code via Maven illustrent parfaitement les principes d'architecture distribuée et de découplage des composants.