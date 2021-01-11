package fr.humanbooster.fx.pret_a_la_consommation.service.impl;

import java.util.ArrayList;
import java.util.List;

import fr.humanbooster.fx.pret_a_la_consommation.business.Motif;
import fr.humanbooster.fx.pret_a_la_consommation.service.MotifService;

public class MotifServiceImpl implements MotifService {

	private List<Motif> motifs = new ArrayList<>();

	@Override
	public Motif ajouterMotif(String nom) {
		Motif motif = new Motif(nom);
		motifs.add(motif);
		return motif;
	}

	@Override
	public List<Motif> recupererMotifs() {
		return motifs;
	}

	@Override
	public Motif recupererMotif(Long id) {
		for (Motif motif : motifs) {
			if (motif.getId()==id) {
				return motif;
			}
		}
		return null;
	}
	
}