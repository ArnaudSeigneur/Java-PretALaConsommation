package fr.humanbooster.fx.pret_a_la_consommation.service;

import java.util.List;

import fr.humanbooster.fx.pret_a_la_consommation.business.Motif;

public interface MotifService {

	Motif ajouterMotif(String nom);
	
	List<Motif> recupererMotifs();
	
	Motif recupererMotif(Long id);
	
}
