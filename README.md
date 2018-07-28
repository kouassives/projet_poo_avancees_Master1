### KOUASSI YVES ANSELME MAGLOIRE

@version 01/01/2018
L'application est une application classique de gestion de commande.

# Projet POO AVANCEES Master1 BDGL

**Requière une version jkd au moins superieur à 8.
** Une autre machine ou la meme machine pour le service Web.
      
      Le programme recupère la TVA sur un serveur lors des achats d'articles
      Clonez le service web avec ce lien: https://github.com/kouassives/ServiceWeb_Projet_MASTER1.git et deployé le Serveur en vous aidant du _lisezMoi.txt 
      
- Les fichiers resources
    La base de données : resources/database/gestcmandsapp.sql
                         resources/database/DBAutorite.sql ( pour le service web )
    La classe principale : src/gest/MainApp.java
- La librairie des bibliothèque : lib

1-Demarrer l'application en lancant src/gest/MainApp.java
  ***NB:Au demarrage il est possible qu'il apparaissent une information sur l'état connexion de la base de données, Ne vous inquiétez pas 
  vous pourrez parametrer cela pour configurater les informations de la Base de données comme indiqué dans la suite.
  
  ***A l'ouverture de la page de connexion:
    Cliquez sur le button DB pour ouvrir une fenêtre qui vous aidera à configurer les informations de la base de données.
    Nom d'utilisateur: root ( Entrez le nom d'utilisateur SQL que vous utilisez)
    Mot de passe:**** Entrer (Entrez le mot de passe SQL que vous utilisez)
    adresse serveur DB: localhost ( Dans le cas d'un accès à une base de données distance entrez l'adress IP de la machine distance)
    
    Lorsque la configuration sera parfaite vous obtiendrez un message de succès. Alors revenez à  la fenetre de connexion et entrez les informations de l'un des utilisateur de l'application présent dans la table gestcmandsapp.utilisateurs

2-La fenêtre du menu presente les differents modules de la gestion.
  La gestion des articles, La gestions des commandes, la gestion des clients, les statistiques, les paramtres. 
    
