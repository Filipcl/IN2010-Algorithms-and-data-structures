import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.lang.Math;

class MaxSubSum {
    public static void main (String[] args){
        Integer [] array = {-2,-4,3,-1,5,6,-7,-2,4,-3,2};  //Oppretter listen som array 
        ArrayList<Integer> arrayl = new ArrayList<Integer>(Arrays.asList(array)); //Adder arrayet inn i en Arraylist
        LinkedList<Integer> list = new LinkedList<Integer>(); //Oppretter en linkedlist
        list.addAll(arrayl); //legger arraylisten inn i lenkelisten, gjør egt dette bare for å slippe og ha masse .add på lenkelisten
        System.out.println("Max sub in list: " + MaxSubSum(list));  //Printer ut listens max subliste sum
                                                                        
    }

    public static int MaxSubSum(LinkedList <Integer> list){
        Integer [] prefix = new Integer[list.size()];                   //Oppretter en Array som skal være så stor som lengden av listen
        prefix[0] = list.getFirst();                                    //setter den første indeksen i prefix til første index i listen
        int sum = prefix[0];
        int i = 0;
        for (int t = 1; t < list.size(); t++){                          //Lager en teller som starter på 1. 
            prefix[t] = Math.max(0, (prefix[t-1]) + list.get(t));       //Legger sammen current list index og prefix sin forrige index, Math.max velger det største tallet som blir lagt inn i prefix sin current index.
            sum = Math.max(sum, prefix[t]);                             //Sjekker prefix mot sum, hvis prefix er større enn sum blir det sum.
            if(Math.max(sum , prefix[t]) == prefix[t] ){
               i = t;  
            }
        }
        return sum;                                                     //returnerer sum 
    }
}




