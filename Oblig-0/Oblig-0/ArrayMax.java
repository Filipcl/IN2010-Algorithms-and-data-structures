
class ArrayMax {

    public static void main (String[] args){
    int[] A = {1,2,3,4,5,6,7,8,9,10};           //Oppretter en Array med integers
    System.out.print(ArrayMax(A, A.length));    //Kaller på metoden, sender med listen og lengden av listen som n og printer ut.
}

public static int ArrayMax(int[] A , int n){ //Tar lengden av listen og listen som parametere
    int currentMax = A[0];                  // Setter variablen current max til første index i Arrayen
    for (int i = 1; i < n ; i++ ){          //for-løkke med en teller som sjekker om telleren er mindre en listens lengde.
       if(currentMax < A[i]){               // viss currentmax er mindre enn indexen i listen 
            currentMax = A[i];              //sett indexen til current max
       }
   }
   return currentMax;                       //returner currentmax når i = arrey.length
}
}

