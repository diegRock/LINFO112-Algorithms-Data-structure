package sorting;

/**
 * Author Pierre Schaus
 *
 * Complete the Merge Sort (top-down) algorithm below making use
 * of the provided merge method.
 * You are not allowed to use imports or other external classes of Java.
 * Hint: Merger Sort this is a divide and conquer algorithm.
 *       It is easier to implement it recursively.
 *       As an alternative exercise, you can try to implement it
 *       non recursively, using a loop instead.
 */
public class MergeSort {
    /**
     * Pre-conditions: a[lo..mid] and a[mid+1..hi] are sorted
     * Post-conditions: a[lo..hi] is sorted
     */
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
            
        }

        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (aux[j].compareTo(aux[i]) < 0) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    // Mergesort a[lo..hi] using auxiliary array aux[lo..hi]
    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {

        if(hi <= lo) return;
        int mid = lo + (hi - lo)/2;

        sort(a, aux, lo, mid); // leftHalf
        sort(a, aux, mid + 1, hi); // rightHalf

        merge(a, aux, lo, mid, hi);

    }

    /**
     * Rearranges the array in ascending order, using the natural order
     */
    public static void sort(Comparable[] a) {

        Comparable [] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length-1);
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
du plus adéquat et ensuite re convertir l'array en une linkedlist.

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