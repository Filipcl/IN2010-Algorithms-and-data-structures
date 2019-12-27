import java.util.LinkedList;
import java.util.ArrayList;

class Sort {

    //Metode for å bytte elementers index-plass i en array
    public void swap(int array[], int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    // --- Selection-Sort algoritmen
    int[] selectionSort(int list[]) {
        int num = list.length;
        for (int i = 0; i < num - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < num; j++) {
                if (list[j] < list[minIndex]) {
                    minIndex = j;
                }
            }
            swap(list, i, minIndex);
        }
        return list;
    }

    // --- Quick-Sort algoritme
    void quickSort(int[] list, int start, int end){
        int left;
        while(start < end){
          left = quickSortPartition(list,start,end);
    
          if(left-start < end-left){
            quickSort(list,start,left-1);
            start = left+1;
          } else {
            quickSort(list,left+1,end);
            end = left-1;
          }
        }
      }
    
      int quickSortPartition(int[] list, int start, int end){
        int pivot = list[end];
        int left = start;
        int right = end-1;

        while(left <= right){
          while(left <= right && list[left]<= pivot){
            left++;
          }
          while(right >= left && list[right]>=pivot){
            right--;
          }
          if(left<right){
            swap(list,left,right);
          }
        }
        swap(list,left,end);
        return left;
      }

      // --- Heap-Sort algoritme
      public void heapSort(int list[]) {   
        int n = list.length;  
        for (int i = n / 2 - 1; i >= 0; i--) //Bygger en heap 
            buildHeap(list, n, i); 
        for (int i=n-1; i>=0; i--) { // Tar ut et og et element og bytter det største elementet med root 
            swap(list, 0 , i);             
            buildHeap(list, i, 0); 
        } 
    } 
  
    void buildHeap(int list[], int n, int i) { 
        int largest = i; // Største element tilsvarer root 
        int left = 2*i + 1;  
        int right = 2*i + 2;  
  
        // If left child er større enn root 
        if (left < n && list[left] > list[largest]) 
            largest = left; 
  
        // If right child er større enn current største element 
        if (right < n && list[right] > list[largest]) 
            largest = right; 
  
        // If størst is not root 
        if (largest != i) { 
            swap(list , i , largest);
            buildHeap(list, n, largest); 
        } 
    } 


}