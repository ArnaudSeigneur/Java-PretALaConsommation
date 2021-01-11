package fr.humanbooster.fx.pret_a_la_consommation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import fr.humanbooster.fx.pret_a_la_consommation.business.Client;
import fr.humanbooster.fx.pret_a_la_consommation.business.Mensualite;
import fr.humanbooster.fx.pret_a_la_consommation.business.Pret;
import fr.humanbooster.fx.pret_a_la_consommation.business.Taux;
import fr.humanbooster.fx.pret_a_la_consommation.service.ClientService;
import fr.humanbooster.fx.pret_a_la_consommation.service.DureeService;
import fr.humanbooster.fx.pret_a_la_consommation.service.MotifService;
import fr.humanbooster.fx.pret_a_la_consommation.service.PretService;
import fr.humanbooster.fx.pret_a_la_consommation.service.TauxService;
import fr.humanbooster.fx.pret_a_la_consommation.service.impl.ClientServiceImpl;
import fr.humanbooster.fx.pret_a_la_consommation.service.impl.DureeServiceImpl;
import fr.humanbooster.fx.pret_a_la_consommation.service.impl.MotifServiceImpl;
import fr.humanbooster.fx.pret_a_la_consommation.service.impl.PretServiceImpl;
import fr.humanbooster.fx.pret_a_la_consommation.service.impl.TauxServiceImpl;

public class App {

	private static final int AFFICHER_PRETS_TRIES_PAR_MONTANT_DECROISSANT = 1;
	private static final int AFFICHER_PRETS_TRIES_PAR_TAUX_DECROISSANT = 2;
	private static final int AFFICHER_PRETS_DEBUTANT_ENTRE_DEUX_DATES = 3;
	private static final int AJOUTER_PRET = 4;
	private static final int QUITTER = 5;
	private static final String DATE_HEURE_FORMAT = "dd/MM/yyyy";
	
	// DureeService est l'interface
	// dureeService: objet de type DureeService
	// DureeServiceImpl() est le constucteur de la classe DureeServiceImpl qui implémente DureeService
	private static DureeService dureeService = new DureeServiceImpl();
	private static MotifService motifService = new MotifServiceImpl();
	private static TauxService tauxService = new TauxServiceImpl();
	private static PretService pretService = new PretServiceImpl();
	private static ClientService clientService = new ClientServiceImpl();
	private static Scanner scanner = new Scanner(System.in);
	private static SimpleDateFormat simpleDateFormatMensualite = Mensualite.simpleDateFormat;
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_HEURE_FORMAT);
	
	public static void main(String[] args) {
		ajouterDonnees();

		System.out.println("Bienvenue sur prêt à la consommation");
		
		while (true) {
			afficherMenuPrincipal();
			int choix = demanderChoix("Faîtes votre choix : ", 1, QUITTER);
			switch (choix) {
			case AFFICHER_PRETS_TRIES_PAR_MONTANT_DECROISSANT:
				pretService.trierPretsParMontantDecroissant();
				afficherPrets();				
				break;
			case AFFICHER_PRETS_TRIES_PAR_TAUX_DECROISSANT:
				pretService.trierPretsParTauxDecroissant();
				afficherPrets();				
				break;
			case AFFICHER_PRETS_DEBUTANT_ENTRE_DEUX_DATES:
				Date dateDebut = demanderDate("Entrez la date de début au format " + DATE_HEURE_FORMAT + " : ");
				Date dateFin = demanderDate("Entrez la date de fin au format " + DATE_HEURE_FORMAT + " : ");
				afficherPrets(dateDebut, dateFin);
				break;
			case AJOUTER_PRET:
				ajouterPret();				
				break;
			case QUITTER:
				System.out.println("Au revoir");
				scanner.close();
				System.exit(0);
				break;
			default:
				break;
			}
		}
		
	}
	
	private static void afficherPrets(Date dateDebut, Date dateFin) {
		for (Pret pret : pretService.recupererPrets(dateDebut, dateFin)) {
			System.out.println(pret);
		}
	}

	private static void afficherPrets() {
		for (Pret pret : pretService.recupererPrets()) {
			System.out.println(pret);
		}
	}

	private static void afficherMenuPrincipal() {
		System.out.println(AFFICHER_PRETS_TRIES_PAR_MONTANT_DECROISSANT + ". Voir tous les prêts triées par montant (du plus élevé au plus petit)");
		System.out.println(AFFICHER_PRETS_TRIES_PAR_TAUX_DECROISSANT + ". Voir tous les prêts triées par taux (du plus élevé au plus petit)");
		System.out.println(AFFICHER_PRETS_DEBUTANT_ENTRE_DEUX_DATES + ". Voir la liste des prêts qui débutent entre deux dates données");
		System.out.println(AJOUTER_PRET + ". Ajouter un prêt");
		System.out.println(QUITTER + ". Quitter");
	}

	private static void ajouterPret() {
		Date dateEffet = null;
		Client clientChoisi = null;
		Taux tauxChoisi = null;

		afficherClients();
		clientChoisi = clientService.recupererClient((long) demanderChoix("Veuillez saisir l'id du client concerné : ", 1, clientService.recupererClients().size()));

		System.out.print("Veuillez saisir le montant demandé : ");
		double montantDemande = Double.parseDouble(scanner.nextLine());
		
		afficherTaux();
		tauxChoisi = tauxService.recupererTaux((long) demanderChoix("Veuillez saisir l'id du taux annuel : ", 1, tauxService.recupererTaux().size()));
		
		System.out.print("Veuillez saisir la date d'effet au format MM/yyyy : ");
		try {
			dateEffet = simpleDateFormatMensualite.parse(scanner.nextLine());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pret pret = pretService.ajouterPret(tauxChoisi, clientChoisi, montantDemande, dateEffet);
		System.out.println(pret);
	}

	private static void afficherClients() {
		for (Client client : clientService.recupererClients()) {
			System.out.println(client.getId() + ". " + client.getNom().toUpperCase() + " " + client.getPrenom());
		}
	
	}

	private static void afficherTaux() {
		for (Taux taux : tauxService.recupererTaux()) {
			System.out.println(taux.getId() + ". " + taux.getValeur()*100 + " % sur " + taux.getDuree().getDureeEnMois() + " mois pour " + taux.getMotif().getNom());
		}
	}

	public static void ajouterDonnees() {
		// On délègue au service la création des objets de type Duree
		dureeService.ajouterDuree(12);
		dureeService.ajouterDuree(24);
		motifService.ajouterMotif("Auto");
		motifService.ajouterMotif("Moto");
		
		tauxService.ajouterTaux(0.01, dureeService.recupererDuree(1L), motifService.recupererMotif(1L));
		tauxService.ajouterTaux(0.015, dureeService.recupererDuree(2L), motifService.recupererMotif(1L));
		tauxService.ajouterTaux(0.02, dureeService.recupererDuree(1L), motifService.recupererMotif(2L));
		tauxService.ajouterTaux(0.025, dureeService.recupererDuree(2L), motifService.recupererMotif(2L));
		
		// On ajoute 5 clients à l'aide du boucle
		for (int i = 0; i < 5; i++) {
			clientService.ajouterClient("Nom" + i, "Prenom" + i);
		}
	}

	private static int demanderChoix(String message, int borneMin, int borneMax) {
		int valeur = borneMin-1;
		// Utilisation d'une boucle do while
		// Le code dans le do sera exécuté au moins une fois
		do {
			System.out.print(message);
			try {
				String saisie = scanner.nextLine();
				valeur = Integer.parseInt(saisie);
				if (valeur < borneMin || valeur > borneMax) {
					System.out.println("Merci de saisir un nombre compris entre " + borneMin + " et " + borneMax);
				}
			} catch (Exception e) {
				System.out.println("Merci de saisir un nombre");
			}

		} while (!(valeur >= borneMin && valeur <= borneMax));
		return valeur;
	}

	private static Date demanderDate(String message) {
		Date date = null;
		Calendar calendar = Calendar.getInstance();
		System.out.print(message);
		do {
			try {
				date = simpleDateFormat.parse(scanner.nextLine());
			} catch (ParseException e) {
				System.out.println(message);
			}				
		} while (date == null);
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

}