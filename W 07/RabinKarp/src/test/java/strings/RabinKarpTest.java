package strings;

import org.junit.Test;
import com.github.guillaumederval.javagrading.Grade;



import java.util.Random;

import static java.lang.Math.min;
import static org.junit.Assert.assertEquals;

public class RabinKarpTest {


    @Test
    @Grade(value= 1)
    public void basicTest(){
        String[] pat = {"comp","like"};
        String txt = "I like computer science";
        RabinKarp rc = new RabinKarp(pat);
        assertEquals(2,rc.search(txt));
    }


    @Test
    @Grade(value= 1)
    public void wordNotPresentTest(){
        String[] pat = {"Yavin","C-3PO","R2-D2" };
        String txt = "Mais, vous savez, moi je ne crois pas qu'il y ait de bonne ou de mauvaise situation. Moi," +
                " si je devais résumer ma vie aujourd'hui avec vous, je dirais que c'est d'abord des rencontres," +
                " des gens qui m'ont tendu la main, peut-être à un moment où je ne pouvais pas, où j'étais seul chez moi." +
                " Et c'est assez curieux de se dire que les hasards, les rencontres forgent une destinée... " +
                "Parce que quand on a le goût de la chose, quand on a le goût de la chose bien faite, le beau geste," +
                " parfois on ne trouve pas l'interlocuteur en face, je dirais, le miroir qui vous aide à avancer." +
                " Alors ce n'est pas mon cas, comme je le disais là, puisque moi au contraire, j'ai pu ;" +
                " et je dis merci à la vie, je lui dis merci, je chante la vie, je danse la vie... Je ne suis qu'amour !" +
                " Et finalement, quand beaucoup de gens aujourd'hui me disent Mais comment fais-tu pour avoir cette" +
                " humanité ?, eh ben je leur réponds très simplement, je leur dis que c'est ce goût de l'amour, ce goût donc" +
                " qui m'a poussé aujourd'hui à entreprendre une construction mécanique, mais demain, qui sait," +
                " peut-être seulement à me mettre au service de la communauté, à faire le don, le don de soi...";
        RabinKarp rc = new RabinKarp(pat);
        assertEquals(txt.length(),rc.search(txt));
    }


    @Test
    @Grade(value= 1)
    public void randomWordTest(){
        //int[] seeds = new int[]{42,56,3,9,65,99,23};
        Random rand = new Random(new Random(5).nextInt(7));
        String[] pat = new String[10];
        int length = 8;


        String txt = "Mais, vous savez, moi je ne crois pas qu'il y ait de bonne ou de mauvaise situation. Moi," +
                " si je devais résumer ma vie aujourd'hui avec vous, je dirais que c'est d'abord des rencontres," +
                " des gens qui m'ont tendu la main, peut-être à un moment où je ne pouvais pas, où j'étais seul chez moi." +
                " Et c'est assez curieux de se dire que les hasards, les rencontres forgent une destinée... " +
                "Parce que quand on a le goût de la chose, quand on a le goût de la chose bien faite, le beau geste," +
                " parfois on ne trouve pas l'interlocuteur en face, je dirais, le miroir qui vous aide à avancer." +
                " Alors ce n'est pas mon cas, comme je le disais là, puisque moi au contraire, j'ai pu ;" +
                " et je dis merci à la vie, je lui dis merci, je chante la vie, je danse la vie... Je ne suis qu'amour !" +
                " Et finalement, quand beaucoup de gens aujourd'hui me disent Mais comment fais-tu pour avoir cette" +
                " humanité ?, eh ben je leur réponds très simplement, je leur dis que c'est ce goût de l'amour, ce goût donc" +
                " qui m'a poussé aujourd'hui à entreprendre une construction mécanique, mais demain, qui sait," +
                " peut-être seulement à me mettre au service de la communauté, à faire le don, le don de soi...";


        int minIndex = txt.length();
        for(int i=0;i<10;i++){
            int startIndex = rand.nextInt(txt.length()-length);
            pat[i] = txt.substring(startIndex,startIndex+length);
            minIndex = min(minIndex,startIndex);
        }
        RabinKarp rc = new RabinKarp(pat);
        assertEquals(minIndex,rc.search(txt));
    }

    private int nChar = 26;
    private int patSize = 3;
    private String[] patterns = new String[(int)Math.pow(nChar,patSize)];
    private int nPats = 0;
    private void genAllWords(String prefix, int k) {
        if (k == 0) {
            this.patterns[nPats] = prefix;
            this.nPats++;
            return;
        }

        for (int i = 0; i < nChar; ++i) {
            String newPrefix = prefix + (char)('a' + i);
            genAllWords(newPrefix, k - 1);
        }
    }

    @Test(timeout=50)
    @Grade(value= 1)
    public void complexityTest(){
        long t0 = System.currentTimeMillis();
        genAllWords("",patSize);
        RabinKarp rc = new RabinKarp(this.patterns);

        String txt = ""+
                "Ra th er  t ha n  pu rs ui ng  m or e  so ph is ti ca te d  sk ip pi ng ,  th e  Ra bi n– Ka rp  a l"+
                "g or it hm  s ee ks  t o  sp ee d  up  t he  t es ti ng  o f  eq ua li ty  o f  th e  pa tt er n  to"+
                "  t he  s ub st ri ng s  in  t he  t ex t  by  u si ng  a  h as h  fu nc ti on .  A  ha sh  f un ct "+
                "io n  is  a  f un ct io n  wh ic h  co nv er ts  e ve ry  s tr in g  in to  a  n um er ic  v al ue ,"+
                "  ca ll ed  i ts  h as h  va lu e;  f or  e xa mp le ,  we  m ig ht  h av e  ha sh (h el lo )= 5.  T";

        assertEquals(txt.length(),rc.search(txt));

        long t1 = System.currentTimeMillis();
        System.out.println("Spent time = "+(t1-t0));


    }


}

/*
Q4.1.4 Générer ine clé a partir d'un hash

Private int hash(Key x){return (x.hashCode() & 0x7ffffff)%M);
Tous les bits mis à 1 sauf le premier, c'est ça que veut dire le fffffff
0x implique la notation hexadécimale

Q4.1.5 Collisions

Separate chaining: Parcours de liste chaînée, si elle est trop grande, on met un red black tree.


Linear probing: se renseigner

Q4.1.6 Load factor

load factor = nombre d'éléments dans la hashmap

Q4.1.9 Linear probing avec marqueur.

 */
/*
Comment est ce qu'on caclule un hash ?

What is the hash of int 42 ? On peut hasher l'entier directement

Représentation binaire de 42 = 101010

Computing the hash of a string of size M
Theta(M) longueur de la string

pattern de taille k --> calcul du nouveau hash ne dépend pas de la taille du pattern, formule en temps constant.


 */