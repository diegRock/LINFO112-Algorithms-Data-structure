package graphs;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Sophie and Marc want to reduce the bubbles of contacts in the belgian population
 * to contain an evil virus (weird idea but nevertheless inspired by a true belgian story in 2020, don't ask ...).
 *
 * Help them!
 *
 * The Belgian government has imposed on the population to limit the number of contacts, via "bubbles".
 *
 * The principle is quite simple. If you have a (close) contact with someone,
 * You are then in his bubble, and he is in yours.
 *
 * Let's say the following contacts have taken place: [
 * [Alice, Bob], [Alice, Carol], [Carol, Alice], [Eve, Alice], [Alice, Frank],
 * [Bob, Carole], [Bob, Eve], [Bob, Frank], [Bob, Carole], [Eve, Frank]
 * ].
 *
 * Note that the contacts are two-by-two and can occur several times. The order within
 * of a contact does not matter.
 *
 * The resulting bubbles are :
 *
 * - Alice's bubble = [Bob, Carol, Eve, Frank]
 * - Bob's bubble = [Bob, Carol, Eve, Frank]
 * - Bob's bubble = [Alice, Carol, Eve, Frank]
 * - Carole's bubble = [Alice, Bob]
 * - Frank's Bubble = [Alice, Bob, Eve]
 *
 * Note that the relationship is symmetric
 * (if Alice is in Bob's bubble, then Bob is in Alice's bubble)
 * but not transitive (if Bob is in Alice's bubble, then Bob is in Alice's bubble)
 * and Carol is in Bob's bubble, Carol is not necessarily in Alice's.
 *
 * Since at most n people can be in someone's bubble without him being outlaw
 * given a list of contacts, select pairs of people that you will forbid to meet, so that eventually
 * each person has a bubble of NO MORE than n people (not counting themselves).
 * You need to ban AS FEW AS POSSIBLE (pairs of) people to meet.
 *
 * For example, if n=3, in the example above, you could forbid Alice and Carol to see each other, but also
 * Bob and Carol. This removes 2 links (even though Alice and Carol appear twice in the contacts!).
 * But there is a better solution: prevent Alice and Bob from seeing each other, which only removes one link.
 * Finding an algorithm that solves this problem is complex, that's why we give you a rather vague idea of an algorithm:
 *
 * - As long as there are links between two bubbles each "too big", remove one of these links;
 * - Then, as long as there are bubbles that are too big, remove any link connected to one of these bubbles
 * (removing is equivalent to "adding the link to the list of forbidden relationships")
 *
 * Implementing this algorithm as it is a bad idea. Think of an optimal way to implement it.
 *
 * You CANNOT modify the `contacts` list directly (nor the lists inside)
 * If you try, you will receive an UnsupportedOperationException.
 *
 * @returns a list of people you are going to forbid to see each other. There MUST NOT be any duplicates.
 * the order doesn't matter, both within the ForbiddenRelation and in the list.
 */
public class Bubbles {


    public static List<ForbiddenRelation> cleanBubbles(List<Contact> contacts, int n) {
        /////////////////////////////////////////////
        // First of all --> Create lists of Bubbles//
        /////////////////////////////////////////////

        // Ici on va créer un hashmap de hashSet car l'avantage du hashset est que
        // il n'y aura pas de doublons à gérer. Si on essaie d'ajouter un nom mais qu'il
        // Est déja dans le set, il ne sera pas rajouter.

        HashMap<String, HashSet<String>> bubbles = new HashMap<>();

        for(Contact contact : contacts){

            if(!bubbles.containsKey(contact.a)){
                // On initialise la liste de contact de la première personne
                // La clé étant le nom de la paersonne et la valeur, le set associé.
                bubbles.put(contact.a, new HashSet<>());
            }
            if(!bubbles.containsKey(contact.b)){
                bubbles.put(contact.b, new HashSet<>());
            }

            // On récupère le set associé au contact a
            HashSet<String> add_b_to_a_set = bubbles.get(contact.a);

            HashSet<String> add_a_to_b_set = bubbles.get(contact.b);

            // On ajoute au set associé au contact a, le contact b
            add_b_to_a_set.add(contact.b);
            add_a_to_b_set.add(contact.a);
        }

        List<ForbiddenRelation> forbiddenRelations = new LinkedList<>();

        for(Contact contact : contacts){
            // Puisque les listes sont symétriques, on à juste besoin de check dans une des liste
            // Ici on regarde si la liste de a contient le nom de b. Si elle ne le contient
            // pas on skip le code en dessous du continue pour passer à l'itération suivante;
            if(!bubbles.get(contact.a).contains(contact.b)) continue;

            // Ici, lorsque les deux listes sont supérieurs à n, on les dégage
            // Pour laisser de la place.
            if(bubbles.get(contact.a).size() > n && bubbles.get(contact.b).size() > n){
                forbiddenRelations.add(new ForbiddenRelation(contact.a, contact.b));
                bubbles.get(contact.a).remove(contact.b);
                bubbles.get(contact.b).remove(contact.a);
            }
        }

        // Toutes les listes les plus critiques ont été modifiées.
        // Cependant, il reste les listes ou l'un des deux set des
        // contact est full et ou il faut donc encore enleve un contact.

        for(Contact contact : contacts){
            // Puisque les listes sont symétriques, on à juste besoin de check dans une des liste
            // Ici on regarde si la liste de a contient le nom de b. Si elle ne le contient
            // pas on skip le code en dessous du continue pour passer à l'itération suivante;
            if(!bubbles.get(contact.a).contains(contact.b)) continue;

            // Cependant,
            if(bubbles.get(contact.a).size() > n || bubbles.get(contact.b).size() > n){
                forbiddenRelations.add(new ForbiddenRelation(contact.a, contact.b));
                bubbles.get(contact.a).remove(contact.b);
                bubbles.get(contact.b).remove(contact.a);
            }
        }

        return forbiddenRelations;
    }

}



class Contact {
    public final String a, b;

    public Contact(String a, String b) {
        // We always force a < b for simplicity.
        if(a.compareTo(b) > 0) {
            this.b = a;
            this.a = b;
        }
        else {
            this.a = a;
            this.b = b;
        }
    }
}

class ForbiddenRelation implements Comparable<ForbiddenRelation> {
    public final String a, b;

    public ForbiddenRelation(String a, String b) {
        // We always force a < b for simplicity.
        if(a.compareTo(b) > 0) {
            this.b = a;
            this.a = b;
        }
        else {
            this.a = a;
            this.b = b;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ForbiddenRelation)
            return a.equals(((ForbiddenRelation) obj).a) && b.equals(((ForbiddenRelation) obj).b);
        return false;
    }

    @Override
    public int compareTo(ForbiddenRelation o) {
        if(a.equals(o.a))
            return b.compareTo(o.b);
        return a.compareTo(o.a);
    }
}
