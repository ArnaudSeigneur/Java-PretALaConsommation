package fr.humanbooster.fx.pret_a_la_consommation.service.impl;

import java.util.ArrayList;
import java.util.List;

import fr.humanbooster.fx.pret_a_la_consommation.business.Client;
import fr.humanbooster.fx.pret_a_la_consommation.service.ClientService;

public class ClientServiceImpl implements ClientService {

	private List<Client> clients = new ArrayList<>();
	
	@Override
	public Client ajouterClient(String nom, String prenom) {
		Client client = new Client(nom, prenom);
		clients.add(client);
		return client;
	}

	@Override
	public List<Client> recupererClients() {
		return clients;
	}

	@Override
	public Client recupererClient(Long id) {
		for (Client client : clients) {
			if (client.getId()==id) {
				return client;
			}
		}
		return null;
	}

	@Override
	public boolean supprimerClient(Long id) {
		Client clientASupprimer = recupererClient(id);
		if (clientASupprimer==null) {
			return false;
		} else {
			clients.remove(clientASupprimer);
			return true;
		}
	}

}
