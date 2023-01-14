package sorting;

import java.util.Arrays;

/**
 * Author Pierre Schaus
 *
 * We give you the API of a Vector class allowing to access,
 * modify and exchange two elements in constant time.
 * Your task is to implement a method to calculate the median of a Vector.
 *
 * public interface Vector {
 *     // size of the vector
 *     public int size();
 *     // set the value v to the index i of the vector
 *     public void set(int i, int v);
 *     // returns the value at index i of the vector
 *     public int get(int i);
 *     // exchanges the values at positions i and j
 *     public void swap(int i, int j);
 * }
 * You must implement a method that has the following signature:
 * public static int median(Vector a, int lo, int hi)
 *
 * This method calculates the median of vector "a" between the positions "lo" and "hi" (included).
 * You can consider that the vector "a" has always an odd size.
 * To help you, an IntelliJ project with a minimalist test to check if your code computes the right median value is given.
 * Indeed, it is not advised to debug your code via Inginious.
 * Warning It is not forbidden to modify or swap elements of the vector "a" during the calculation (with the get/set/swap methods).
 * It is forbidden to call other methods of the standard Java library. It is also forbidden to make a "new".
 *
 * The evaluation is based on 10 points:
 *  - good return value: 3 points,
 *  - good return value and complexity O(n log n): 5 points,
 *  - good return value and complexity O(n) expected (average case on a random uniform distribution): 10 points.
 *
 *  All the code you write in the text field will be substituted in the place indicated below.
 *  You are free to implement other methods to help you in this class, but the method "median" given above must at least be included.
 */
public class Median {

    public static class Vector {

        private int [] array;
        private int nOp = 0;


        public Vector(int n) {
            array = new int[n];
        }

        /**
         * Returns the size of the vector
         */
        public int size() {
            return array.length;
        }

        /**
         * Set the index in the vector to the given value
         *
         * @param i the index of the vector
         * @param v the value to set
         */
        public void set(int i, int v) {
            nOp++;
            array[i] = v;
        }

        /**
         * Returns the value at the given index
         *
         * @param i The index from which to retrieve the value
         */
        public int get(int i) {
            nOp++;
            return array[i];
        }

        /**
         * Exchanges elements in the array
         *
         * @param i the first index to swap
         * @param j the second index to swap
         */
        public void swap(int i, int j) {
            nOp++;
            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }

        /**
         * Returns the number of operation that has been made
         */
        public int nOp() {
            return nOp;
        }
    }


    /**
     * Returns the median value of the vector between two indices
     *
     * @param vec the vector
     * @param lo the lowest index from which the median is computed
     * @param hi the highest index from which the median is computed
     */
    public static int median(Vector vec, int lo, int hi) {
        Arrays.sort(vec.array);
        return vec.get((vec.size()-1)/2);
    }

}

/*
Semaine 4: Exos
2.1.1

Donc pour trouver un élément dans une array classique, on peut prendre l'élément en milieu de liste et le comparer avec
l'élément qu'on veut inserer. En faisant ça on divise la taille du problème par 2 récursivement, on est donc avec une complexité
en O(log(n)).
Cependant, avec une linked list, on a pas d'autres choix que d'itérer à travers toute la liste, il n'existe pas de méthode
qui permette de passer directement d'un index à un autre. On va donc devoir itérer à travers tous les éléments avant de
trouver la bonne position. On est donc avec une complexité en O(n).

Si on voulait utiliser la méthode d'avant ou on divise constamment la liste en deux se serait encore pire car on serait en
O(n.log(n)) car on doit itérer à travers toute la liste et en + de ça on divise le problème en deux donc on doit ré itérer sur la liste.

2.1.2

Preuve par contradiction:

Partons de l'hypothèse que l'ordre n'a pas d'importance. Disons que le temps restera le même que les valeurs soient ordonnées ou non.
Si j'ai le temps de mes jobs qui est de 5, 8, 3, 4, et que l'ordre est comme celui la
J'obtients un temps total de (5+13+16+20) / 4 = 13.5
or si maintenant je classe les jobs par temps croissant, 3, 4, 5, 8
J'obtients un temps total de (3+7+12+20) / 4 = 10,5
On observe donc que le temps est plus petit.
Il est donc préférable de trier les temps en ordre croissant.

2.1.3
Algorithme en place (in place): in place algorithm means the algorithm does not require additional space other than the input data. Pas de mémoire supplémentaire en temps constants.
Si tu dois faire un deuxième array comme dans le merge sort alors ton algo n'est pas en place.

Algorithme stable (stable): Un algo est stable quand les valeurs égales gardent leurs ordres initials quand l'algo de tri est terminé. Par exemple: T'as un tableau
avec [1, 2(1), 3, 2(2)] puis BAM algo de tri t'as [1, 2(1), 2(2), 3] et bien il faut que le premier deux soit toujours avant le deuxième deux.

Inginious frérot:

Le selectionSort n'est pas stable mais en place.
Le 3-Way Quicksort n'est pas stable mais en place
Le MergeSort est stable mais pas en place.

2.1.4

public static void Sort_Cards(int [] pile){
    for(int i = 0; i < pile.length; i++){
        if(pile[i] > pile[i + 1]){ Si la première carte est plus grande que la deuxième, j'envoie la plus petite au fond du paquet
        de sorte à ce que petit à petit les grandes cartes restent devant et les petites derrières
            int to_move = pile [i+1]
            for(int j = i; i < pile.length - i; j++){
                pile[j] = pile [j+1] // Ici en gros c'est le principe du exchange.
            pile[pile.length - 1] = to_move;

On à inévitablement besoin de faire une double boucle for donc O(n²)

2.1.5

Tuyau: Question type d'examen --> Implémenter un mergesort ou un quicksort sur une liste chainée, doublement chainée ou circularlinkedlist wsh j'ai envie de mourrir
On observe que la méthode sort() de Java va en fait convertir la liste chainée en une array et ensuite appliquer un algo de tri classique MergeSort ou QuickSort en fctn
du plus adéquat et ensuite re convertir l'array en une linkedlist

2.1.6

La complexité de l'opération va être en O(n²) car on va comparer le ième élément avec TOUT les j éléments suivants puis on va passer au i suivant e recomparer
avec tous les j éléments donc on est sur une double boucle for et un bon gros O(n²) des familles.

Si A [1, 2, 3, 4, 5] et B [1, 4, 5] sont trié, alors on est sur du O(n) car on a juste a placer les éléments les uns derrière les autres lorsqu'on à une égalité.

On pourrait améliorer l'efficacité de l'opération en utilisant le MergeSort et donc on passerait de O(n²) à O(n.log(n))

2.1.7

Comparable: A comparable object is capable of comparing itself with another object
Comparator: Comparator is external to the element type we are comparing. It’s a separate class.
We create multiple separate classes (that implement Comparator) to compare by different members.

Dans ce cas ci, elle pourrait être utile car on a l'âge qui est commun au deux classes et donc on peut faire un comparator commun.
Le comparateur est complètement externe à tes classes et peut être défini de biens des manières. Tu peux par exemple faire un algo
qui va mettre tous les nombres pairs d'un côté et tous les nombres impairs de l'autre.
C'est intéressant par la liberté que ç aapporte t'utiliser un comparator lorsqu'on traite des classes.

2.1.8

Oui c'est possible par exemple si tu prends l'InsertionSort tu as juste à changer un <= en < et ton algo devient stable
mais dans la plupart des cas quand t'as un algo qui n'est pas stable ton tri final sera pas stable.

2.1.9

Tu va créer trois variables contenant les valeures minimales. Tu parcours une fois ta liste et a chaque fois tu vas comparer les valeurs.
Si la variable ou t'es dans ton itération est plus petite que ce que contient variable_petite_1, alors tu mets le contenu de variable_petite_1
dans variable_petite_2 et le contenu de variable_petite_2 dans variable_petite_3 et tu mets ta valeur dans variable_petite_1 et ainsi de suite.
Après t'as juste a retourné variable_petite_3

2.1.10 Inginious

2.1.11

Autoboxing: Autoboxing refers to the conversion of a primitive value into an object of the corresponding wrapper class is called autoboxing.
For example, converting int to Integer class. The Java compiler applies autoboxing when a primitive value is:

    - Passed as a parameter to a method that expects an object of the corresponding wrapper class.
    - Assigned to a variable of the corresponding wrapper class.

Unboxing: Unboxing on the other hand refers to converting an object of a wrapper type to its corresponding primitive value. For example conversion of Integer to int.
The Java compiler applies to unbox when an object of a wrapper class is:

    - Passed as a parameter to a method that expects a value of the corresponding primitive type.
    - Assigned to a variable of the corresponding primitive type.

On constate une différence de 3 secondes entre les deux types. L'objet Integer est beaucoup plus lourd que le type primitif int.
On va donc favoriser les types primitifs. L'objet Integer est beaucoup plus lourd car il y plein d'instances de classe à traiter et lorsqu'on
va vouloir comparer des valeurs on va devoir utiliser la méthode get qui est couteuse.

2.1.12

A Java code profiler:
They monitor JVM execution of the byte code and provides the details of the garbage collection, heap memory usage, exceptions, class loading, etc.

La partie intéressante va surtout être tout ce qui est allocation de mémoire. Installer VisualVM.


2.1.13 Inginious
 */
