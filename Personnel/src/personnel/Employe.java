package personnel;
import java.time.*;
import java.io.Serializable;
/**
 * Employé d'une ligue hébergée par la M2L. Certains peuvent
 * être administrateurs des employés de leur ligue.
 * Un seul employé, rattaché à aucune ligue, est le root.
 * Il est impossible d'instancier directement un employé,
 * il faut passer la méthode {@link Ligue#addEmploye addEmploye}.
 */

public class Employe implements Serializable, Comparable<Employe>
{
	private static final long serialVersionUID = 4795721718037994734L;
	private String nom, prenom, password, mail;
	private int id = -1;
	private LocalDate dateArrive, dateDepart;
	private Ligue ligue;
	private GestionPersonnel gestionPersonnel;
	

	/**
	 * Insère un employé dans la base de donnée et l'ajoute en local
	 * @param gestionPersonnel
	 * @param ligue de l'employé
	 * @param nom de l'employé
	 * @param prenom de l'employé
	 * @param mail de l'employé
	 * @param password de l'employé
	 * @param dateArrive de l'employé
	 * @param dateDepart de l'employé
	 * @throws SauvegardeImpossible
	 */
	Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String password, LocalDate dateArrive, LocalDate dateDepart) throws SauvegardeImpossible
	{
		this(gestionPersonnel, -1, ligue,  nom, prenom, mail, password, dateArrive, dateDepart);
		this.id = gestionPersonnel.insert(this);
	}
	
	/**
	 * Ajoute un employé localement
	 * @param gestionPersonnel
	 * @param id de l'employé
	 * @param ligue de l'employé
	 * @param nom de l'employé
	 * @param prenom de l'employé
	 * @param mail de l'employé
	 * @param password de l'employé
	 * @param dateArrive de l'employé
	 * @param dateDepart de l'employé
	 */
	Employe(GestionPersonnel gestionPersonnel, int id, Ligue ligue, String nom, String prenom, String mail, String password, LocalDate dateArrive, LocalDate dateDepart) {
	    this.gestionPersonnel = gestionPersonnel;
		this.id = id;
		setLigue(ligue);
		setNom(nom);
		setPrenom(prenom);
		setMail(mail);
		setPassword(password);
		setDateArrive(dateArrive);
		setDateDepart(dateDepart);
	}
    
	
	/**
	 * Retourne vrai ssi l'employé est administrateur de la ligue
	 * passée en paramètre.
	 * @return vrai ssi l'employé est administrateur de la ligue
	 * passée en paramètre.
	 * @param ligue la ligue pour laquelle on souhaite vérifier si this
	 * est l'admininstrateur.
	 */
	
	public boolean estAdmin(Ligue ligue)
	{
		return ligue.getAdministrateur() == this;
	}
	
	/**
	 * Retourne vrai ssi l'employé est le root.
	 * @return vrai ssi l'employé est le root.
	 */
	
	public boolean estRoot()
	{
		return gestionPersonnel.getRoot() == this;
	}
	
	/**
	 * Retourne le nom de l'employé.
	 * @return le nom de l'employé.
	 */
	
	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom de l'employé.
	 * @param nom le nouveau nom.
	 */
	
	public void setNom(String nom)
	{
		this.nom = nom;
	}

	/**
	 * Retourne le prénom de l'employé.
	 * @return le prénom de l'employé.
	 */
	
	public String getPrenom()
	{
		return prenom;
	}
	
	/**
	 * Change le prénom de l'employé.
	 * @param prenom le nouveau prénom de l'employé.
	 */

	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
	}

	/**
	 * Retourne le mail de l'employé.
	 * @return le mail de l'employé.
	 */
	
	public String getMail()
	{
		return mail;
	}
	
	/**
	 * Change le mail de l'employé.
	 * @param mail le nouveau mail de l'employé.
	 */

	public void setMail(String mail)
	{
		this.mail = mail;
	}

	/**
	 * Retourne vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @return vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @param password le password auquel comparer celui de l'employé.
	 */
	
	public boolean checkPassword(String password)
	{
		return this.password.equals(password);
	}

	/**
	 * Change le password de l'employé.
	 * @param password le nouveau password de l'employé.
	 */
	
	public void setPassword(String password)
	{
		this.password= password;
	}
	
	public String getPassword() {
		return password;
	}
	
	/**
	 * Retourne la ligue à laquelle l'employé est affecté.
	 * @return la ligue à laquelle l'employé est affecté.
	 */
	
	public Ligue getLigue()
	{
		return ligue;
	}

	/**
	 * Change la ligue à laquelle l'utilisateur est affecté
	 * @param ligue
	 */

	public void setLigue(Ligue ligue)
	{
		this.ligue = ligue;
	}
	/**
	 * Retourne la date à laquelle l'employé est arrivé.
	 * @return la date à laquelle l'employé est arrivé.
	 */
	
	public LocalDate getDateArrive() {
		return dateArrive;
	}

	/**
	 * Change la date d'arrivé de l'employé
	 * @param dateArrive
	 * @throws IllegalArgumentException
	 */

	public void setDateArrive(LocalDate dateArrive) {
		this.dateArrive = dateArrive;
	}

	/**
	 * Retourne la date de départ de l'employé
	 * @return la date de départ de l'employé
	 */

	public LocalDate getDateDepart() {
		return dateDepart;
	}

	/**
	 * Change la date de départ de l'employé
	 * @param dateDepart
	 * @throws IllegalArgumentException
	 */
	public void setDateDepart(LocalDate dateDepart) {
		if (dateDepart == null || (dateArrive != null && dateArrive.isBefore(dateDepart))) {
			this.dateDepart = dateDepart;
		} else {
			throw new IllegalArgumentException("La date de départ est invalide.");
		}
	}

	/**
	 * Supprime l'employé. Si celui-ci est un administrateur, le root
	 * récupère les droits d'administration sur sa ligue.
	 * @throws SauvegardeImpossible
	 * @throws ImpossibleDeSupprimerRoot
	 */
	
	public void remove() throws SauvegardeImpossible
	{
		Employe root = gestionPersonnel.getRoot();
		if (this != root)
		{
			if (estAdmin(getLigue()))
				getLigue().setAdministrateur(root);
			getLigue().remove(this, this.getLigue());
		}
		else
			throw new ImpossibleDeSupprimerRoot();
	}

	@Override
	public int compareTo(Employe autre)
	{
		int cmp = getNom().compareTo(autre.getNom());
		if (cmp != 0)
			return cmp;
		return getPrenom().compareTo(autre.getPrenom());
	}
	
	@Override
	public String toString()
	{
		String res = nom + " " + prenom + " " + mail + " (";
		if (estRoot())
			res += "super-utilisateur";
		else
			res += ligue.toString();
		return res + ")";
	}


	
}