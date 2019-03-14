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
    Random randgen = new Random(2);
    int pivot = data[(end-start)/2];
    // System.out.println(printArray(data));
    //System.out.println("Pivot: " + pivot);
    data[(end-start)/2] = data[start]; //move pivot out of the way
    //System.out.println(printArray(data));
    data[start] = pivot;
    //ystem.out.println(printArray(data));
    int s = start;
    int e = end;
    s++;
    //System.out.println(s);
    int cur = data[s];
    while (s != e) {
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
        int chances = randgen.nextInt();
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
      cur = data[s];
      // System.out.println(printArray(data));
    }
    //ystem.out.println(s);
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
    String s = "";
    for (int i = 0; i < ary.length ;i ++) {
      s += ary[i] + " ";
    }
    return s;
  }

  /*return the value that is the kth smallest value of the array.
 */
  public static int quickselect(int[] data, int k){
    int s = 0;
    int e = data.length-1;
    int cur = partition(data, s, e);
    while (cur != k) {
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
}
