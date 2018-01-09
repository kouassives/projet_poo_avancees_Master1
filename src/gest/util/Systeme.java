package gest.util;

import static java.lang.System.getProperty;
import java.io.FileNotFoundException;

public class Systeme {

	private static final String OS = "os.name";
	private static final String DIR = "user.dir";
	private static final String USER = "user.name";
	private static final String VERSION = "os.version";
	private static final String SEPARATEUR = "file.separator";

	public static String getSeparateur() {
		return getProperty(SEPARATEUR);
	}

	public static String getRepertoireCourant() {
		return getProperty(DIR);
	}

	public static String getNomOS() {
		return getProperty(OS);
	}

	public static String getVersionOS() {
		return getProperty(VERSION);
	}

	public static String getNomUtilisateur() {
		return getProperty(USER);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
	}

}
