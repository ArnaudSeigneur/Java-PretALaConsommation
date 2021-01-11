package fr.humanbooster.fx.pret_a_la_consommation.service;

import java.util.List;

import fr.humanbooster.fx.pret_a_la_consommation.business.Client;

public interface ClientService {

	Client ajouterClient(String nom, String prenom);
	
	List<Client> recupererClients();
	
	Client recupererClient(Long id);
	
	boolean supprimerClient(Long id);
}
