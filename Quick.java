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
   if (data.length == 0 || data.length == 1) return start;
   Random randgen = new Random(2);
   int[] pivots = {data[start],data[(end-start)/2],data[end]};
   Arrays.sort(pivots);
   int pivot = pivots[1]; //median
   int pivotInd = data.length/2;
   if (data[start] == pivot) pivotInd=start;
   if (data[end] == pivot) pivotInd=end; 
   // if (data[start]==pivot) pivotInd=start;
   // else if (data[(end-start)/2]==pivot) pivotInd=(end-start)/2;
   // else if (data[end]==pivot) pivotInd=end;
   // System.out.println(printArray(data));
   //System.out.println("Pivot: " + pivot);
   data[pivotInd] = data[start]; //move pivot out of the way
   //System.out.println(printArray(data));
   data[start] = pivot;
   //ystem.out.println(printArray(data));
   int s = start;
   int e = end;
   s++;
   //System.out.println(s);
   int cur = data[s];
   while (s != e) {
     cur = data[s];
     // System.out.println(cur);
     if (cur > pivot) {
       int holder = data[e];
       data[e] = cur;
       data[s] = holder;
       e--;
       //System.out.println(printArray(data));
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
         int holder = data[e];
         data[e] = cur;
         data[s] = holder;
         e--;
       }
     }
     // System.out.println(printArray(data));
   }
   //System.out.println(s);
   if (data[s] < pivot) {
     int holder = data[s];
     data[s] = pivot;
     data[start] = holder;
     return s;
   }
   else {
     int holder = data[s-1];
     data[s-1] = pivot;
     data[start] = holder;
     return s-1;
   }
   // System.out.println(printArray(data));
 }

  public static String printArray(int[] ary) {
    String s = "[";
    for (int i = 0; i < ary.length ;i ++) {
      s += ary[i] + ", ";
    }
    s = s.substring(0,s.length()-2);
    s += "]";
    return s;
  }

  /*return the value that is the kth smallest value of the array.
 */
  public static int quickselect(int[] data, int k){
    int s = 0;
    int e = data.length-1;
    int cur = partition(data, s, e);
    // System.out.println(cur);
    while (cur != k) {
      // System.out.println(printArray(data) + ", " + s + ", " + e);
      // System.out.println(cur);
      if (cur < k) {
        s = cur + 1;
      }
      else {
        e = cur - 1;
      }
      cur = partition(data, s, e);
    }
    return data[cur];
  }

  /*Modify the array to be in increasing order.
 */
  public static void quicksort(int[] data) {
    quickSortH(data, 0, data.length-1);
  }

  private static void quickSortH(int[] data, int lo, int hi) {
    if (hi <= lo) return;
    int pivot = partition(data,lo,hi);
    quickSortH(data, lo, pivot-1);
    quickSortH(data, pivot + 1, hi);
  }

  // public static void main(String[] args) {
  //   int[] test = {6,222,8,5,8,3,5,637,3};
  //   System.out.println(quickselect(test, 1));
  //   // quicksort(test);
  //   // System.out.println(printArray(test));
  // }
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
