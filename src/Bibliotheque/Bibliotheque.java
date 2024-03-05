package Bibliotheque;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.io.IOException;
import java.nio.file.*;

 
 
public class Bibliotheque {
	public List<Livre> filtrerLivres(Predicate<Livre> predicate) {
        List<Livre> livresFiltres = new ArrayList<>();
        for (Livre livre : livres) {
            if (predicate.test(livre)) {
                livresFiltres.add(livre);
            }
        }
        return livresFiltres;
    }
	 public List<Livre> getLivresEmpruntes() {
	        return filtrerLivres(Livre::isEmprunte);
	    }
 
	    // Utilisation d'une expression lambda pour filtrer les livres non empruntés
	    public List<Livre> getLivresNonEmpruntes() {
	        return filtrerLivres(livre -> !livre.isEmprunte());
	    }
	    // Debut de la partie 7
	    public List<Livre> getLivresEmpruntesStream() {
	        return livres.stream()
	                .filter(Livre::isEmprunte)
	                .collect(Collectors.toList());
	    }
	 // Utiliser un stream pour filtrer les livres non empruntés partie 7
	    public List<Livre> getLivresNonEmpruntesStream() {
	        return livres.stream()
	                .filter(livre -> !livre.isEmprunte())
	                .collect(Collectors.toList());
	    }
	    public void ecrireLivresDansFichier(String cheminFichier) throws IOException {
	        Path path = Paths.get(cheminFichier);
	        List<String> lignes = livres.stream()
	                .map(Livre::toString) // Vous devrez peut-être ajuster cela en fonction de votre représentation des livres
	                .collect(Collectors.toList());
 
	        Files.write(path, lignes);
	    }
 
	    // Lire les informations des livres depuis un fichier
	    public void lireLivresDepuisFichier(String cheminFichier) throws IOException {
	        Path path = Paths.get(cheminFichier);
	        List<String> lignes = Files.readAllLines(path);
 
	        // Vous devrez peut-être ajuster cela en fonction de votre représentation des livres
	        lignes.stream()
	                .map(this::creerLivreDepuisChaine) // Méthode à implémenter pour créer un livre à partir d'une chaîne
	                .forEach(this::ajouterLivre);
	    }
 
	    // Méthode à implémenter pour créer un livre à partir d'une chaîne
	    private Livre creerLivreDepuisChaine(String ligne) {
	        // Implémentez cette méthode en fonction du format de vos données dans le fichier
	        // Vous pourriez utiliser StringTokenizer ou d'autres méthodes pour extraire les informations nécessaires.
	        // Retournez un objet Livre avec les informations extraites.
	        return null;
	    }
 
//fin de la partie 7
	 private StringBuilder historique;
    private List<Livre> livres = new ArrayList<>();
 
    public Bibliotheque(String nom, String adresse, String ville, String codePostal, String email) {
        this.nom = nom;
        this.adresse = adresse;
        this.ville = ville;
        this.codePostal = codePostal;
        this.email = email;
        historique = new StringBuilder();
    }
 
    public static void main(String[] args) {
        Bibliotheque maBibliotheque = new Bibliotheque("Ma Bibliothèque", "123 Rue Principale", "Villeville", "12345", "contact@ma-bibliotheque.com");
        Scanner scanner = new Scanner(System.in);
 
        while (true) {
            System.out.println("Menu de la Bibliothèque");
            System.out.println("1. Afficher les informations de la bibliothèque");
            System.out.println("2. Ajouter un livre");
            System.out.println("3. Afficher la liste des livres");
            System.out.println("4. Emprunter un livre");
            System.out.println("5. Retourner un livre");
            System.out.println("6. Supprimer un livre");
            System.out.println("7. Quitter");
            System.out.print("Choisissez une option (1, 2, 3 ,4 , 5, 6 ou 7) : ");
 
            try {
                int choix = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character
 
                switch (choix) {
                    case 1:
                        afficherInformationsBibliotheque(maBibliotheque);
                        break;
                    case 2:
                        ajouterLivre(maBibliotheque, scanner);
                        break;
                    case 3:
                        afficherListeLivres(maBibliotheque);
                        break;
                    case 4:
                        emprunterLivre(maBibliotheque, scanner);
                        break;
                    case 5:
                        retournerLivre(maBibliotheque, scanner);
                        break;
                    case 6:
                        supprimerLivres(maBibliotheque, scanner);
                        break;
                    case 7:
                        System.out.println("Au revoir !");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Option invalide. Veuillez choisir à nouveau.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Erreur : Veuillez entrer un nombre.");
                scanner.nextLine();  // Clear the input buffer
            }
        }
    }
 
    private static void supprimerLivres(Bibliotheque maBibliotheque, Scanner scanner) {
        try {
            System.out.print("Indice du livre à supprimer : ");
            int indiceSuppression = scanner.nextInt();
            maBibliotheque.supprimerLivre(indiceSuppression);
        } catch (InputMismatchException e) {
            System.out.println("Erreur : Veuillez entrer un nombre.");
            scanner.nextLine();  // Clear the input buffer
        }
    }
 
    private void supprimerLivre(int indiceSuppression) {
        if (indiceSuppression >= 0 && indiceSuppression < livres.size()) {
            Livre livreSupprime = livres.remove(indiceSuppression);
            System.out.println("Livre supprimé avec succès : " + livreSupprime);
        } else {
            System.out.println("Indice invalide. Aucun livre supprimé.");
        }
    }
 
    private static void emprunterLivre(Bibliotheque maBibliotheque, Scanner scanner) {
        try {
            System.out.print("Indice du livre à emprunter : ");
            int indiceEmprunt = scanner.nextInt();
            maBibliotheque.emprunterLivre(indiceEmprunt);
        } catch (InputMismatchException e) {
            System.out.println("Erreur : Veuillez entrer un nombre.");
            scanner.nextLine();  // Clear the input buffer
        }
    }
 
    private static void retournerLivre(Bibliotheque maBibliotheque, Scanner scanner) {
        try {
            System.out.print("Indice du livre à retourner : ");
            int indiceRetour = scanner.nextInt();
            maBibliotheque.retournerLivre(indiceRetour);
        } catch (InputMismatchException e) {
            System.out.println("Erreur : Veuillez entrer un nombre.");
            scanner.nextLine();  // Clear the input buffer
        }
    }
 
    private static void afficherInformationsBibliotheque(Bibliotheque maBibliotheque) {
        System.out.println("Informations de la bibliothèque :");
        System.out.println("Nom : " + maBibliotheque.getNom());
        System.out.println("Adresse : " + maBibliotheque.getAdresse());
        System.out.println("Ville : " + maBibliotheque.getVille());
        System.out.println("Code Postal : " + maBibliotheque.getCodePostal());
        System.out.println("Email : " + maBibliotheque.getEmail());
    }
 
    private static void ajouterLivre(Bibliotheque maBibliotheque, Scanner scanner) {
        try {
            System.out.print("Entrez le titre du livre : ");
            String titre = scanner.nextLine();
            System.out.print("Entrez l'auteur du livre : ");
            String auteur = scanner.nextLine();
            System.out.print("Entrez l'ISBN du livre : ");
            String isbn = scanner.nextLine();
            System.out.print("Entrez le prix du livre : ");
            double prix = scanner.nextDouble();
 
            Livre nouveauLivre = new Livre(titre, auteur, isbn, prix);
            maBibliotheque.ajouterLivre(nouveauLivre);
            System.out.println("Livre ajouté avec succès !");
        } catch (InputMismatchException e) {
            System.out.println("Erreur : Veuillez entrer un nombre pour le prix.");
            scanner.nextLine();  // Clear the input buffer
        }
    }
 
    private static void afficherListeLivres(Bibliotheque maBibliotheque) {
        List<Livre> livres = maBibliotheque.getLivres();

        if (livres != null && !livres.isEmpty()) {
            System.out.println("Liste des livres :");
            for (int i = 0; i < livres.size(); i++) {
                Livre livre = livres.get(i);
                System.out.println(i + ": " + livre);
                if (livre.isEmprunte()) {
                    System.out.println("   Date d'emprunt : " + livre.getDateEmprunt());
                    System.out.println("   Date de retour : " + livre.getDateRetour());
                }
            }
        } else {
            System.out.println("Aucun livre dans la bibliothèque.");
        }
    }
 
    public void emprunterLivre(int index) {
        if (index >= 0 && index < livres.size()) {
            Livre livre = livres.get(index);
            if (!livre.isEmprunte()) {
                livre.setEmprunte(true);
                livre.setDateEmprunt(LocalDateTime.now().plusSeconds(1));  // Enregistrez la date d'emprunt
                System.out.println("Livre emprunté avec succès : " + livre);
                System.out.println("Date d'emprunt : " + livre.getDateEmprunt());
            } else {
                System.out.println("Ce livre est déjà emprunté.");
            }
        } else {
            System.out.println("Indice invalide.");
        }
    }
    public void emprunterLivre(String titre, String utilisateur) {
        LocalDateTime dateEmprunt = LocalDateTime.now().plusSeconds(1);
        String empruntInfo = String.format("Livre \"%s\" emprunté par %s le %s%n", titre, utilisateur, formatDate(dateEmprunt));
        historique.append(empruntInfo);
 
        // Recherche du livre par titre
        Livre livreEmprunte = null;
        for (Livre livre : livres) {
            if (livre.getTitre().equals(titre) && !livre.isEmprunte()) {
                livreEmprunte = livre;
                break;
            }
        }
 
        // Si le livre est trouvé, marquez-le comme emprunté
        if (livreEmprunte != null) {
            livreEmprunte.setEmprunte(true);
            System.out.println("Livre emprunté avec succès : " + livreEmprunte);
        } else {
            System.out.println("Le livre \"" + titre + "\" n'est pas disponible pour l'emprunt.");
        }
    }
    
    public void retournerLivre(String titre) {
        LocalDateTime dateRetour = LocalDateTime.now().plusSeconds(1);
        String retourInfo = String.format("Livre \"%s\" retourné le %s%n", titre, formatDate(dateRetour));
        historique.append(retourInfo);
        
    }
    
 
    public String getHistorique() {
        return historique.toString();
    }
 
    private static String formatDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return date.format(formatter);
    }
 
    public void retournerLivre(int index) {
        if (index >= 0 && index < livres.size()) {
            Livre livre = livres.get(index);
            if (livre.isEmprunte()) {
                livre.setEmprunte(false);
                livre.setDateRetour(LocalDateTime.now().plusSeconds(1));  // Enregistrez la date de retour
                System.out.println("Livre retourné avec succès : " + livre);
                System.out.println("Date de retour : " + livre.getDateRetour());
            } else {
                System.out.println("Ce livre n'est pas emprunté.");
            }
        } else {
            System.out.println("Indice invalide.");
        }
    }

 
    public List<Livre> getLivres() {
        return livres;
    }
 
    public void ajouterLivre(Livre livre) {
        livres.add(livre);
    }
 
    public String getNom() {
        return nom;
    }
 
    public String getAdresse() {
        return adresse;
    }
 
    public String getVille() {
        return ville;
    }
 
    public String getCodePostal() {
        return codePostal;
    }
 
    public String getEmail() {
        return email;
    }
 
    private String nom;
    private String adresse;
    private String ville;
    private String codePostal;
    private String email;
 
    static class Livre {
        private String titre;
        private String auteur;
        private String isbn;
        private double prix;
        private boolean emprunte;
        private LocalDateTime dateEmprunt;
        private LocalDateTime dateRetour;
        
		public Livre(String titre, String auteur, String isbn, double prix) {
            this.titre = titre;
            this.auteur = auteur;
            this.isbn = isbn;
            this.prix = prix;
            this.emprunte = false;
            this.dateEmprunt = null;
            this.dateRetour = null;
        }
 
		 public String getDateEmprunt() {
		        return formatDate(dateEmprunt);
		    }

		 public String getDateRetour() {
			    return (dateRetour != null) ? formatDate(dateRetour) : "N/A";
			}


		    public void setDateEmprunt(LocalDateTime dateEmprunt) {
		        this.dateEmprunt = dateEmprunt;
		    }

		    public void setDateRetour(LocalDateTime dateRetour) {
		        this.dateRetour = dateRetour;
		    }


		public Object getTitre() {
			// TODO Auto-generated method stub
			return null;
		}
 
		@Override
        public String toString() {
            return "Livre{" +
                    "titre='" + titre + '\'' +
                    ", auteur='" + auteur + '\'' +
                    ", ISBN='" + isbn + '\'' +
                    ", prix=" + prix +
                    ", emprunté=" + emprunte +
                    '}';
        }
 
        public boolean isEmprunte() {
            return emprunte;
        }
 
        public void setEmprunte(boolean emprunte) {
            this.emprunte = emprunte;
        }
    }
}