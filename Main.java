/* 
Adam DAIA
Mohammed DAFAOUI    
---------------------------------------------------
Voici quelques exemple de graphe à utiliser :

Graphe Cour
        (préféreces)
0 1
0 4
0 5  
1 4 
1 3 
2 5 
3 5
         (interférences)
4 3      
__________________________________
Losange 
        (préférences)
0 1
1 2
2 3
3 0
        (interférences)
1 3 

*/

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

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
    private int[] couleurs; 

    public Graphe(int taille) {
        this.tailleGraphe = taille;
        matriceAdjacence = new int[taille][taille];
        aretesDePreference = new ArrayList<>();
        aretesDInterference = new ArrayList<>();
        couleurs = new int[taille];
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
        boolean coloriable = true;
        for (int sommet = 0; sommet < tailleGraphe; sommet++) {
            couleurs[sommet] = attribuerCouleurDisponible(k, sommet);
            if (couleurs[sommet] == -1) {
                coloriable = false;
                break;
            }
        }
        if (!coloriable) {
            System.out.println("\u001B[31m" + "Graphe pas coloriable avec " + k + " couleurs, mais spille les arretes qui ne peuvent pas etre colorés" + "\u001B[0m");
            //System.exit(0); 
        } else {
            System.out.println("\u001B[32m" + "Graphe coloriable avec " + k + " couleurs" + "\u001B[0m");
        }
    }

    private boolean estTrivialColorable(int k, int sommet) {
        int degre = 0;
        for (int autreSommet = 0; autreSommet < tailleGraphe; autreSommet++) {
            if (estVoisin(sommet, autreSommet) && couleurs[autreSommet] != 0) {
                degre++;
                if (aDesAretesDInterference(sommet, autreSommet)) {
                    return false; 
                }
            }
        }
        return degre < k;
    }

    private boolean aDesAretesDInterference(int sommet1, int sommet2) {
        for (Edge arete : aretesDInterference) {
            if ((arete.sommet1 == sommet1 && arete.sommet2 == sommet2) ||
                (arete.sommet1 == sommet2 && arete.sommet2 == sommet1)) {
                return true; 
            }
        }
        return false; 
    }

    private int attribuerCouleurDisponible(int k, int sommet) {
        boolean[] couleursUtilisées = new boolean[k + 1];
        for (int autreSommet = 0; autreSommet < tailleGraphe; autreSommet++) {
            if (estVoisin(sommet, autreSommet) && couleurs[autreSommet] != 0 && !aDesAretesDInterference(sommet, autreSommet)) {
                couleursUtilisées[couleurs[autreSommet]] = true;
            }
        }

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

class GraphPanel extends JPanel {
    private Graphe graphe;

    public GraphPanel(Graphe graphe) {
        this.graphe = graphe;
    }

//Fonction Graphique
 @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int radius = 30;
    int[] colors = graphe.getCouleurs();

    // Calculer la position des sommets en cercle
    int centerX = getWidth() / 2;
    int centerY = getHeight() / 2;
    int circleRadius = Math.min(getWidth(), getHeight()) / 3;
    double angleStep = 2 * Math.PI / graphe.getTailleGraphe();

    for (int i = 0; i < graphe.getTailleGraphe(); i++) {
        int x = (int) (centerX + circleRadius * Math.cos(i * angleStep));
        int y = (int) (centerY + circleRadius * Math.sin(i * angleStep));

        g.setColor(Color.BLACK);
        g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);

        if (colors[i] > 0) {
            g.setColor(new Color(Math.min(colors[i] * 50, 255), Math.min(colors[i] * 100, 255), Math.min(colors[i] * 150, 255)));
            g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        }

        g.setColor(Color.WHITE);
        g.drawString("Sommet " + i, x - 10, y + 5);
    }

    for (int i = 0; i < graphe.getTailleGraphe(); i++) {
        int x1 = (int) (centerX + circleRadius * Math.cos(i * angleStep));
        int y1 = (int) (centerY + circleRadius * Math.sin(i * angleStep));

        for (int j = 0; j < graphe.getTailleGraphe(); j++) {
            if (graphe.estVoisin(i, j)) {
                int x2 = (int) (centerX + circleRadius * Math.cos(j * angleStep));
                int y2 = (int) (centerY + circleRadius * Math.sin(j * angleStep));

                g.setColor(Color.BLACK);
                g.drawLine(x1, y1, x2, y2);
            }
        }
    }
}

}

public class Main extends JFrame {

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

        // Créer et afficher la fenêtre avec le graphe
        Main frame = new Main(graphe);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
       
    }

    public Main(Graphe graphe) {
        add(new GraphPanel(graphe));
    }
}
