import java.util.*;
import java.io.*;

public class Quick {
  /*Modify the array such that:
 *1. Only the indices from start to end inclusive are considered in range
 *2. A random index from start to end inclusive is chosen, the corresponding
 *   element is designated the pivot element.
 *3. all elements in range that are smaller than the pivot element are placed before the pivot element.
 *4. all elements in range that are larger than the pivot element are placed after the pivot element.
 *@return the index of the final position of the pivot element.
 */
 public static int partition(int[] data, int start, int end) {
   if (start==end) return start;
   Random randgen = new Random(2);
   int[] pivots = {data[start],data[(end-start)/2],data[end]};
   Arrays.sort(pivots);
   int pivot = pivots[1]; //median
   // System.out.println(Arrays.toString(data));
   // System.out.println("Start: " + start);
   // System.out.println("End: " + end);
   // System.out.println("Pivot: " + pivot);
   // System.out.println();
   int pivotInd = (end-start)/2;
   if (data[start] == pivot) pivotInd=start;
   if (data[end] == pivot) pivotInd=end;
   data[pivotInd] = data[start]; //move pivot out of the way
   //System.out.println(printArray(data));
   data[start] = pivot;
   int s = start;
   int e = end;
   s++;
   int cur = data[s];
   while (s != e) {
     cur = data[s];
     if (cur > pivot) {
       int holder = data[s];
       data[s] = data[e];
       data[e] = holder;
       e--;
     }
     else if (cur < pivot) {
       s++;
     }
     else if (cur == pivot) {
       int chances = randgen.nextInt() % 2;
       if (chances == 0) {
         s++;
       }
       else {
         int holder = data[s];
         data[s] = data[e];
         data[e] = holder;
         e--;
       }
     }
   }
   if (data[s] <= pivot) {
     data[start] = data[s];
     data[s] = pivot;

     return s;
   }
   else {
     data[start] = data[s-1];
     data[s-1] = pivot;
     return s-1;
   }
 }

 public static int[] partitionDutch(int[] data, int lo, int hi) {
   int lt = lo;
   int gt = hi;
   int i = lo + 1;
   int pivot = lo;
   while (gt >= i) {
     if (data[i] == data[pivot]) {
       i++;
     }
     else if (data[i]< data[pivot]) {
       int temp = data[i];
       data[i] = data[lt];
       data[lt] = temp; //swap lt and i
       lt++;
       i++;
       pivot++;
     }
     else if (data[i]>data[pivot]) {
       int temp = data[i];
       data[i] = data[gt];
       data[gt] = temp; //swap gt and i
       gt--;
     }
   }
   return new int[] {lt,gt};
 }

  /*return the value that is the kth smallest value of the array.
 */
  public static int oldQuickselect(int[] data, int k){
    int s = 0;
    int e = data.length-1;
    int pivotInd = partition(data, s, e);
    // System.out.println(cur);
    while (pivotInd != k) {
      // System.out.println(printArray(data) + ", " + s + ", " + e);
      // System.out.println(cur);
      if (pivotInd < k) {
        s = pivotInd + 1;
      }
      else {
        e = pivotInd - 1;
      }
      pivotInd = partition(data, s, e);
      // System.out.println(Arrays.toString(data));
      // System.out.println(cur);
    }
    return data[pivotInd];
  }

  public static int quickselect(int[] data, int k) {
    int s = 0;
    int e = data.length-1;
    int[] pivotInd = partitionDutch(data, s, e);
    while (k != pivotInd[0] && k != pivotInd[1]) {
      if (k >= pivotInd[0]) {
        s = pivotInd[1]+1;
      }
      else {
        e = pivotInd[0]-1;
      }
      pivotInd = partitionDutch(data,s,e);
    }
    return data[pivotInd[0]];
  }

  /*Modify the array to be in increasing order.
 */
  public static void quicksort(int[] data) {
    quickSortH(data, 0, data.length-1);
  }

  private static void quickSortH(int[] data, int lo, int hi) {
    if (hi <= lo) return;
    if (data.length < 43) {
      insertionsort(data,lo,hi);
      return;
    }
    int[] pivotInds = partitionDutch(data,lo,hi);
    quickSortH(data, lo, pivotInds[0]-1);
    quickSortH(data, pivotInds[1] + 1, hi);
  }

  public static void insertionsort(int[] data, int lo, int hi) {
    for (int i = lo+1; i <= hi; i ++) {
      int cur = data[i];
      for (int x = i - 1; x >= 0; x--) {
        if (cur < data[x]){
          data[x+1]=data[x];
          if (x==0) {
            data[lo]=cur;
          }
        }
        else if (cur > data[x]) {
          data[x+1]=cur;
          x--;
        }
      }
      // System.out.println(Arrays.toString(data));
    }
  }

  public static void main(String[]args){
  System.out.println("Size\t\tMax Value\tquick/builtin ratio ");
  int[]MAX_LIST = {1000000000,500,10};
  for(int MAX : MAX_LIST){
    for(int size = 31250; size < 2000001; size*=2){
      long qtime=0;
      long btime=0;
      //average of 5 sorts.
      for(int trial = 0 ; trial <=5; trial++){
        int []data1 = new int[size];
        int []data2 = new int[size];
        for(int i = 0; i < data1.length; i++){
          data1[i] = (int)(Math.random()*MAX);
          data2[i] = data1[i];
        }
        long t1,t2;
        t1 = System.currentTimeMillis();
        Quick.quicksort(data2);
        t2 = System.currentTimeMillis();
        qtime += t2 - t1;
        t1 = System.currentTimeMillis();
        Arrays.sort(data1);
        t2 = System.currentTimeMillis();
        btime+= t2 - t1;
        if(!Arrays.equals(data1,data2)){
          System.out.println("FAIL TO SORT!");
          System.exit(0);
        }
      }
      System.out.println(size +"\t\t"+MAX+"\t"+1.0*qtime/btime);
    }
    System.out.println();
  }
}


}
