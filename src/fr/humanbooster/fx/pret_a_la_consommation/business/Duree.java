package fr.humanbooster.fx.pret_a_la_consommation.business;

import java.util.List;

public class Duree {

	private Long id;
	private int dureeEnMois;
	
	private List<Taux> taux;

	private static Long compteur = 0L;

	public Duree(int dureeEnMois) {
		id = ++compteur;
		this.dureeEnMois = dureeEnMois;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getDureeEnMois() {
		return dureeEnMois;
	}

	public void setDureeEnMois(int dureeEnMois) {
		this.dureeEnMois = dureeEnMois;
	}

	public List<Taux> getTaux() {
		return taux;
	}

	public void setTaux(List<Taux> taux) {
		this.taux = taux;
	}

	@Override
	public String toString() {
		return "Duree [id=" + id + ", dureeEnMois=" + dureeEnMois + "]";
	}
	
	
}
