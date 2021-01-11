package fr.humanbooster.fx.pret_a_la_consommation.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pret implements Comparable<Pret>{

	private Long id;
	
	private double montantDemande;
	private double montantMensualite;
	private Date dateSouscription;
	
	private Date dateEffet;
	private String observations;
	
	private List<Mensualite> mensualites = new ArrayList<>();
	
	private Client client;
	
	private Taux taux;
	
	private static Long compteur = 0L;

	public Pret(Taux taux, Client client, Double montantDemande, Date dateEffet, double montantMensualite) {
		id = ++compteur;
		this.client = client;
		this.taux = taux;
		this.montantDemande = montantDemande;
		this.dateEffet = dateEffet;
		this.montantMensualite = montantMensualite;
		this.dateSouscription = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getMontantDemande() {
		return montantDemande;
	}

	public void setMontantDemande(double montantDemande) {
		this.montantDemande = montantDemande;
	}

	public double getMontantMensualite() {
		return montantMensualite;
	}

	public void setMontantMensualite(double montantMensualite) {
		this.montantMensualite = montantMensualite;
	}

	public Date getDateSouscription() {
		return dateSouscription;
	}

	public void setDateSouscription(Date dateSouscription) {
		this.dateSouscription = dateSouscription;
	}

	public Date getDateEffet() {
		return dateEffet;
	}

	public void setDateEffet(Date dateEffet) {
		this.dateEffet = dateEffet;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public List<Mensualite> getMensualites() {
		return mensualites;
	}

	public void setMensualites(List<Mensualite> mensualites) {
		this.mensualites = mensualites;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Taux getTaux() {
		return taux;
	}

	public void setTaux(Taux taux) {
		this.taux = taux;
	}

	// On implémente la méthode compareTo de l'interface Comparable
	@Override
	public int compareTo(Pret autrePret) {
		return -((Double) getMontantDemande()).compareTo(autrePret.getMontantDemande());
	}

	// On redéfinit la méthode toString() présente dans la classe mère java.lang.Object
	@Override
	public String toString() {
		String resultat = "Pret [id=" + id + ", montantDemande=" + montantDemande + ", montantMensualite=" + montantMensualite
				+ ", dateSouscription=" + dateSouscription + ", dateEffet=" + dateEffet + ", observations="
				+ observations + ", client=" + client + ", taux=" + taux + "\n";
		resultat += "mensualites:\n";
		for (Mensualite mensualite : mensualites) {
			resultat += mensualite + "\n";
		}
		resultat += "]";
		return resultat;
	}

}