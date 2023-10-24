# Programme de Coloration de Graphe

## À propos du Programme

Ce programme Java permet de colorier un graphe selon certaines contraintes, telles que les arêtes de préférence et d'interférence, en utilisant l'algorithme de Chaitin. La coloration de graphe consiste à attribuer des couleurs aux sommets d'un graphe de manière à ce que deux sommets voisins ne portent pas la même couleur. L'objectif est de minimiser le nombre de couleurs utilisées tout en respectant ces contraintes.

## Fonctionnalités

- Saisie des arêtes du graphe : Vous pouvez entrer les arêtes du graphe en spécifiant les sommets qui les relient.
- Saisie des arêtes de préférence : Le programme prend en compte les arêtes de préférence, ce qui signifie que certains sommets ont une préférence pour être colorés avec une certaine couleur.
- Saisie des arêtes d'interférence : Les arêtes d'interférence définissent les contraintes pour les sommets qui ne doivent pas avoir la même couleur. Si deux sommets sont reliés par une arête d'interférence, ils ne peuvent pas avoir la même couleur.

## Utilisation

1. Exécutez le programme et spécifiez le nombre de sommets dans le graphe.
2. Saisissez les arêtes du graphe en spécifiant les sommets qui les relient. Utilisez -1 pour terminer la saisie.
3. Saisissez les arêtes de préférence de la même manière.
4. Saisissez les arêtes d'interférence de la même manière.
5. Entrez la valeur de k pour le k-coloriage. Le programme essaiera de colorier le graphe avec k couleurs tout en respectant les contraintes.

## Résultats

Le programme affiche si le graphe est coloriable avec k couleurs en utilisant l'algorithme de Chaitin. Si le graphe est coloriable, il attribuera des chiffres représentant les couleurs aux sommets et les affichera. S'il n'est pas coloriable avec k couleurs, il tentera de "spiller" les arêtes qui ne peuvent pas être colorées, c'est-à-dire les marquer comme -1. Vous obtiendrez un message indiquant si le graphe a pu être colorié avec succès ou s'il n'était pas coloriable avec k couleurs.

Les sommets auront des couleurs attribuées ou une valeur de -1 s'ils ont été spillés.


## Auteurs
Ce projet a été réalisé par : 
  - Adam DAIA
  - Mohammed DAFAOUI