package fr.humanbooster.fx.pret_a_la_consommation.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import fr.humanbooster.fx.pret_a_la_consommation.business.Client;
import fr.humanbooster.fx.pret_a_la_consommation.business.Mensualite;
import fr.humanbooster.fx.pret_a_la_consommation.business.Pret;
import fr.humanbooster.fx.pret_a_la_consommation.business.Taux;
import fr.humanbooster.fx.pret_a_la_consommation.service.MensualiteService;
import fr.humanbooster.fx.pret_a_la_consommation.service.PretService;
import fr.humanbooster.fx.pret_a_la_consommation.util.ComparateurDePretsSurTaux;

public class PretServiceImpl implements PretService {

	private List<Pret> prets = new ArrayList<>();
	private MensualiteService mensualiteService = new MensualiteServiceImpl();
	
	@Override
	public Pret ajouterPret(Taux taux, Client client, Double montantDemande, Date dateEffet) {
		// On détermine le montant de l'échéance (ce que l'on va rembourser chaques
		// mois)
		// montantEcheance = (double) (montantDemande * tauxMensuel / (1 -
		// Math.pow(1+tauxMensuel, -dureeEnMois)));
		double tauxMensuel = taux.getValeur() / 12;
		int nbMois = taux.getDuree().getDureeEnMois();
		double montantMensualite = (double) (montantDemande * tauxMensuel / (1 - Math.pow(1 + tauxMensuel, -nbMois)));

		// On cree un objet pret ici
		Pret pret = new Pret(taux, client, montantDemande, dateEffet, montantMensualite);

		// On ajoute les mensualite de ce nouveau prêt

		// Robert -> for int i = 0; i<nbMois; i++;
		// utiliser boucle chloe

		// Clémentine -> on declare une liste de mensualite en locale
		// on boucle sur les nbMois et à chaques iterations on set le montantMensualite
		// dans l objet mensualite

		// Jen -> boucle en fonction du nombre de mensualité

		// Chloe-> faire appel à mensualite service

		// Arnaud-> tu prend montantDemande et montantMensuel = ce que tu fais en
		// fait tout ca dans une boucle

		// Ludivine -> on set le montantMensualite calculé et ce montant là on
		// l ajoute en boucle dans list mensualite du montant pret
		// et la boucle elle tourne pendant la duree du pret en nbMois

		// Valou si il y a bien quelques choses qui m angoisse plus que Java c'est les
		// prêts
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(pret.getDateEffet());
		
		double partDesInterets = montantDemande * tauxMensuel;
		double capitalRembourse = montantMensualite - partDesInterets;

		double montantPaye = montantMensualite - partDesInterets;
		double montantRestant = montantDemande - montantPaye;

		Mensualite premiereMensualite = mensualiteService.ajouterMensualite(pret, capitalRembourse, partDesInterets);
		premiereMensualite.setDatePrelevement(calendar.getTime());
		pret.getMensualites().add(premiereMensualite);
		
		for (int i = 1; i < nbMois; i++) {
			
		    partDesInterets = montantRestant * tauxMensuel;
			montantPaye = montantMensualite - partDesInterets;
			montantRestant = montantRestant - montantPaye;
			
			capitalRembourse = pret.getMensualites().get(i-1).getPartCapitalRembourse() + montantMensualite - partDesInterets;

			// System.out.println(capitalRembourse + "montantPaye =" + montantPaye);
			calendar.add(Calendar.MONTH, 1);
			Mensualite nouvelleMensualite = mensualiteService.ajouterMensualite(pret, capitalRembourse, partDesInterets);
			nouvelleMensualite.setDatePrelevement(calendar.getTime());
			pret.getMensualites().add(nouvelleMensualite);
		}
		
		// On ajoute le nouveau pret à la liste locale
		prets.add(pret);
		return pret;
	}

	@Override
	public List<Pret> recupererPrets() {
		return prets;
	}

	@Override
	public Pret recupererPret(Long id) {
		for (Pret pret : prets) {
			if (pret.getId()==id) {
				return pret;
			}
		}
		return null;
	}

	@Override
	public void trierPretsParMontantDecroissant() {
		Collections.sort(prets);
	}

	@Override
	public void trierPretsParTauxDecroissant() {
		Collections.sort(prets, new ComparateurDePretsSurTaux());
	}

	@Override
	public List<Pret> recupererPrets(Date dateDebut, Date dateFin) {
		List<Pret> pretsCorrespondants = new ArrayList<>();
		for (Pret pret : prets) {
			if ((pret.getDateEffet().equals(dateDebut) || pret.getDateEffet().after(dateDebut))
					&& (pret.getDateEffet().equals(dateFin) || pret.getDateEffet().before(dateFin))) {
				pretsCorrespondants.add(pret);
			}
		}
		return pretsCorrespondants;
	}

}