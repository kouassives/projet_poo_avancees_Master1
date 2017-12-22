package connection;

import java.util.Scanner;

public class TestRequetes {
	public static boolean test(String user, String MDP) {
		boolean succes = false;
		Scanner sc = new Scanner(System.in);
		if (ControleConnexion.controle(user, MDP))
			if (ControleConnexion.getControleConnexion())
			{
				try {
					// Si la connexion reussi on peut exécuter des requetes sql
					//Exemples:
					//ControleConnexion.transfertDonnees();
					succes = false;
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		sc.close();
		return succes;
	}
}
