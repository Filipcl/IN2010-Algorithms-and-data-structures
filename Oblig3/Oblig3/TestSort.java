import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

import javax.lang.model.type.ArrayType;

class TestSort {
    int numElem = 100 ;
    Random random = new Random();
    Sort sort = new Sort();
    int[] sortedArray;

    // Opprettet et array med random input
    public TestSort(int numElem) {
        this.numElem = numElem;
        this.sortedArray = new int[numElem];
        for (int i = 0; i < this.numElem; i++) {
            this.sortedArray[i] = random.nextInt(10);

        }
    }


    // Sorterer arrayen med random input til increasing og decreasing input:
    void arrayType(String type) {
        if (type == "increasing") {
            Arrays.sort(sortedArray);

        } else if (type == "decreasing") {
            for (int i = 0; i < sortedArray.length / 2; ++i) {
                int temp = sortedArray[i];
                sortedArray[i] = sortedArray[sortedArray.length - i - 1];
                sortedArray[sortedArray.length - i - 1] = temp;
            }
        }
        System.out.println(type);
   
    }

    // sorterer input med riktig metode & returnerer sortert array
    int[] sortTest(String method) {
        int[] sorted = new int[this.numElem];

        if (method == "selectionSort") {
            sorted = sort.selectionSort(sortedArray);
        } else if (method == "quickSort") {
            sort.quickSort(sortedArray, 0, sortedArray.length - 1);
            sorted = sortedArray;
        } else if (method == "heapSort") {
            sort.heapSort(sortedArray);
            sorted = sortedArray;
        }else if( method == "arraySort"){
            Arrays.sort(sortedArray);
            sorted = sortedArray;
        }

        System.out.println("Sorted by " + method + ": ");

        return sorted;
    }

    // Print array metode
    static void printArray(int[] list) {
        int n = list.length;
        System.out.print("{");
        for (int i : list) {
            System.out.print(i + ",");
        }
        System.out.print(list[n-1]+ "}");
        System.out.println();
    }

    // main
    public static void main(String[] args) {
    String type = "";
    int[] sorted = new int[Integer.parseInt(args[1])];

    // Tester algoritme på alle arrays ut ifra bruker input 
    TestSort test = new TestSort(Integer.parseInt(args[1]));
    if (args[0].equals("selectionSort")){
      type = "selectionSort";
    } else if (args[0].equals("quickSort")){
      type = "quickSort";
    } else if (args[0].equals("heapSort")){
      type = "heapSort";
    } else if (args[0].equals("arraySort")){
        type = "arraySort";
    }

    // tilfeldig rekkefølge
    test.arrayType("-- Random input -- ");
    long t1 = System.nanoTime();
    sorted = test.sortTest(type);
    double tid1 = (System.nanoTime()-t1) /1000000.0; 
    //test.printArray(sorted);
    System.out.println("runtime : "+ tid1 +" miliseconds");


    System.out.println();

    // stigende rekkefølge
    test.arrayType("-- Increasing input --");
    long t2 = System.nanoTime(); 

    sorted = test.sortTest(type);
    double tid2 = (System.nanoTime()-t2)/1000000.0; 
    //test.printArray(sorted);
    System.out.println("runtime : "+ tid2 +" miliseconds");

    System.out.println();
    System.out.println();

    // fallende rekkefølge
    test.arrayType("-- Descending input --");
    long t3 = System.nanoTime(); 

    sorted = test.sortTest(type);
    double tid3 = (System.nanoTime()-t3)/1000000.0; 
    test.printArray(sorted);
    System.out.println("runtime : "+ tid3 +" miliseconds");
    System.out.println();
    System.out.println();


    }

}