import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Edge {
    int sommet1;
    int sommet2;

    public Edge(int sommet1, int sommet2) {
        this.sommet1 = sommet1;
        this.sommet2 = sommet2;
    }
}

class Graphe {
    private int tailleGraphe;
    private int[][] matriceAdjacence;
    private List<Edge> aretesDePreference;
    private List<Edge> aretesDInterference;
    private int[] couleurs; // Tableau pour stocker les couleurs des sommets

    public Graphe(int taille) {
        this.tailleGraphe = taille;
        matriceAdjacence = new int[taille][taille];
        aretesDePreference = new ArrayList<>();
        aretesDInterference = new ArrayList<>();
        couleurs = new int[taille]; // Initialisation avec des valeurs par défaut
    }

    public int getTailleGraphe() {
        return tailleGraphe;
    }

    public void ajouterArete(int sommet1, int sommet2) {
        matriceAdjacence[sommet1][sommet2] = 1;
        matriceAdjacence[sommet2][sommet1] = 1;
    }

    public void ajouterAreteDePreference(int sommet1, int sommet2) {
        aretesDePreference.add(new Edge(sommet1, sommet2));
    }

    public void ajouterAreteDInterference(int sommet1, int sommet2) {
        aretesDInterference.add(new Edge(sommet1, sommet2));
    }

    public boolean estVoisin(int sommet1, int sommet2) {
        return matriceAdjacence[sommet1][sommet2] == 1;
    }

    public List<Edge> getAretesDePreference() {
        return aretesDePreference;
    }

    public List<Edge> getAretesDInterference() {
        return aretesDInterference;
    }

    public int[] getCouleurs() {
        return couleurs;
    }


    public void colorierGraphe(int k) {
        colorierGrapheRécursif(k, 0);
    }

    private void colorierGrapheRécursif(int k, int sommet) {
        if (sommet == tailleGraphe) {
            // Tous les sommets ont été coloriés avec succès
            return;
        }

        if (estTrivialColorable(k, sommet)) {
            // Le sommet est trivialement colorable, on le colorie
            couleurs[sommet] = attribuerCouleurDisponible(k, sommet);
        } else {
            // Le sommet n'est pas trivialement colorable, on le "spille"
            couleurs[sommet] = -1;
        }

        // Appel récursif pour le sommet suivant
        colorierGrapheRécursif(k, sommet + 1);
    }

  private boolean estTrivialColorable(int k, int sommet) {
    int degre = 0;
    for (int autreSommet = 0; autreSommet < tailleGraphe; autreSommet++) {
        if (estVoisin(sommet, autreSommet) && couleurs[autreSommet] != 0) {
            degre++;
            if (aDesAretesDInterference(sommet, autreSommet)) {
                return false; // Le sommet a une arête d'interférence, il n'est pas trivialement colorable
            }
        }
    }
    return degre < k;
}


private boolean aDesAretesDInterference(int sommet1, int sommet2) {
    for (Edge arete : aretesDInterference) {
        if ((arete.sommet1 == sommet1 && arete.sommet2 == sommet2) ||
            (arete.sommet1 == sommet2 && arete.sommet2 == sommet1)) {
            return true; // Il y a une arête d'interférence entre sommet1 et sommet2
        }
    }
    return false; // Aucune arête d'interférence trouvée
}

   private int attribuerCouleurDisponible(int k, int sommet) {
    boolean[] couleursUtilisées = new boolean[k + 1];
    for (int autreSommet = 0; autreSommet < tailleGraphe; autreSommet++) {
        if (estVoisin(sommet, autreSommet) && couleurs[autreSommet] != 0) {
            couleursUtilisées[couleurs[autreSommet]] = true;
        }
    }
    
    // Ajoutez une vérification pour les arêtes de préférence
    for (Edge arete : aretesDePreference) {
        if (arete.sommet1 == sommet || arete.sommet2 == sommet) {
            int autreSommet = (arete.sommet1 == sommet) ? arete.sommet2 : arete.sommet1;
            if (couleurs[autreSommet] != 0) {
                couleursUtilisées[couleurs[autreSommet]] = true;
            }
        }
    }

    for (int couleur = 1; couleur <= k; couleur++) {
        if (!couleursUtilisées[couleur]) {
            return couleur;
        }
    }
    return -1; // Aucune couleur disponible (ne devrait pas se produire si le graphe est bien k-coloriable)
}

}




public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez le nombre de sommets dans le graphe : ");
        int tailleGraphe = scanner.nextInt();

        Graphe graphe = new Graphe(tailleGraphe);

        // Saisie des arêtes du graphe
        System.out.println("Saisissez les arêtes du graphe (ex. 0 1 pour une arête entre les sommets 0 et 1, -1 pour terminer) :");
        int sommet1, sommet2;
        while (true) {
            sommet1 = scanner.nextInt();
            if (sommet1 == -1) {
                break;
            }
            sommet2 = scanner.nextInt();
            graphe.ajouterArete(sommet1, sommet2);
        }

        // Saisie des arêtes de préférence
        System.out.println("Saisissez les arêtes de préférence (ex. 0 1 pour une arête de préférence entre les sommets 0 et 1, -1 pour terminer) :");
        while (true) {
            sommet1 = scanner.nextInt();
            if (sommet1 == -1) {
                break;
            }
            sommet2 = scanner.nextInt();
            graphe.ajouterAreteDePreference(sommet1, sommet2);
        }

        // Saisie des arêtes d'interférence
        System.out.println("Saisissez les arêtes d'interférence (ex. 0 1 pour une arête d'interférence entre les sommets 0 et 1, -1 pour terminer) :");
        while (true) {
            sommet1 = scanner.nextInt();
            if (sommet1 == -1) {
                break;
            }
            sommet2 = scanner.nextInt();
            graphe.ajouterAreteDInterference(sommet1, sommet2);
        }

        System.out.print("Entrez la valeur de k pour le k-coloriage : ");
        int k = scanner.nextInt();

        graphe.colorierGraphe(k);

        int[] couleurs = graphe.getCouleurs();
        for (int i = 0; i < tailleGraphe; i++) {
            System.out.println("Sommet " + i + " est de couleur " + couleurs[i]);
        }
    }

    
}


