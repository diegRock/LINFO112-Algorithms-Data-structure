package fundamentals;

import java.util.ArrayDeque;
import java.util.EmptyStackException;
import java.util.Queue;

/**
 * Author: Pierre Schaus and Auguste Burlats
 * Implement the abstract data type stack using two queues
 * You are not allowed to modify or add the instance variables,
 * only the body of the methods
 */
public class StackWithTwoQueues<E> {

    Queue<E> queue1;
    Queue<E> queue2;

    public StackWithTwoQueues() {
        queue1 = new ArrayDeque();
        queue2 = new ArrayDeque();
    }

    /**
     * Looks at the object at the top of this stack
     * without removing it from the stack
     */
    public boolean empty() {
        return queue1.isEmpty();
    }

    /**
     * Returns the first element of the stack, without removing it from the stack
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E peek() throws EmptyStackException {
         if (this.empty()) throw new EmptyStackException();
         return queue1.peek();
    }

    /**
     * Remove the first element of the stack and returns it
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E pop() throws EmptyStackException {
         if (this.empty()) throw new EmptyStackException();
         return queue1.remove();
    }

    /**
     * Adds an element to the stack
     *
     * @param item the item to add
     */
    public void push(E item) {
        if (this.empty()) {
            queue1.add(item);
        } else {
            while (!this.empty()) {
                queue2.add(queue1.remove());
            }
            queue1.add(item);
            while (!queue2.isEmpty()) {
                queue1.add(queue2.remove());
            }
        }
    }

}

// Q1.1.5
// La méthode get nécessite de parcourir toute la liste à chaque fois, la complexité temporelle est donc pus grande O(n²) qu'avec un itérator O(n)

// Q1.1.6
// L'utilisation de ~ est préférable car elle prend en compte les constantes et est donc plus précise

// Q1.1.7
// On calcule le ratio entre chaque temps donné. Tu fais ensuite la moyenne de ces ratios et t'arrives à un résultat de 4.
// Ca nous indique clairement que la complexité est en O(n²).
// Et le temps sera d'environ 80 secondes pour 128 000

/*
 Q1.1.8

 Heap: mémoire disponible pour stocker les calculs du processeur
 Stack: mémoire disponible pour stocker des informations de façon statique (moins éphémère que la Heap)

 -Xmx et -Xms désignent respectivement la quantité de mémoire maximale et minimale provenant du heap qui peut être alloué pour l'exécution d'un byte code.

 Oui car dans le cas ou trop peu d'espace mémoire est disponible pour stocker les calculs du processeur, des nouvelles zones mémoires seront allouées
 ittérativement ce qui fait perdre un temps considérable. Dans le cas ou trop de mémoire est allouée, le garbage collector devra faire plus de calcul
 pour nettoyer tout le bloc mémoire en question. Il faut donc trouver un compromis entre trop et pas assez de mémoire allouée pour que le temps d'exécution
 soit le plus faible possible.
 */

/*
Q1.1.9

Si la somme des deux est au dela de 2³² alors le résultat sera incrorect car l'ordi ne sait pas concevoir un nombre aussi grand.
a + b-a / 2 est la formule à utiliser pour éviter l'overflow.

Il faut que le type soit float car la division par deux ne rendra pas forcément un integer
 */
