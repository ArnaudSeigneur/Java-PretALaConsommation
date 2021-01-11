package fr.humanbooster.fx.pret_a_la_consommation.service;

import java.util.Date;
import java.util.List;

import fr.humanbooster.fx.pret_a_la_consommation.business.Client;
import fr.humanbooster.fx.pret_a_la_consommation.business.Pret;
import fr.humanbooster.fx.pret_a_la_consommation.business.Taux;

public interface PretService {

	Pret ajouterPret(Taux taux, Client client, Double montantDemande, Date dateEffet);

	List<Pret> recupererPrets();

	Pret recupererPret(Long id);

	void trierPretsParMontantDecroissant();

	void trierPretsParTauxDecroissant();

	List<Pret> recupererPrets(Date dateDebut, Date dateFin);

}
