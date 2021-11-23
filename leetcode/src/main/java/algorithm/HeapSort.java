package algorithm;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class HeapSort {



    public void sink(int[] arr, int parent,int len) {
        int parentVal = arr[parent];
        int child;
        while ((child = (parent << 1) + 1) < len) {
            // 左右孩子较大值
            if (child + 1 < len && arr[child] < arr[child + 1]) {
                child++;
            }
            if (arr[child] > parentVal) {
                arr[parent] = arr[child];
                parent = child;
            } else {
                break;
            }
        }
        arr[parent] = parentVal;
    }

    public void swing(int[] arr, int child) {
        int childVal = arr[child];
        while (child > 0) {
            int parent = (child - 1) >>> 1;
            if (arr[parent] < childVal) {
                arr[child] = arr[parent];
                child = parent;
            } else {
                break;
            }
        }
        arr[child] = childVal;
    }

    private int[] arr = new int[10];
    private int size = 0;

    public void push(int e){
        if (size >= arr.length){
            int[] newArr = new int[size<<1];
            System.arraycopy(arr,0,newArr,0,size);
            arr = newArr;
        }
        arr[size++] = e;
        swing(arr,size-1);
    }

    public int pop(){
        int pop = arr[0];
        arr[0] = arr[--size];
        arr[size] = 0;
        sink(arr,0,size);
        return pop;
    }


    @Test
    public void maxHeap(){
        push(1);
        push(8);
        push(9);
        push(6);
        pop();
    }



    public void buildHeap(int[] arr, int parent, int len) {
        int parentVal = arr[parent];
        int child = (parent << 1) + 1;
        while (child < len) {
            if (child + 1 < len && arr[child] < arr[child + 1]) {
                child++;
            }
            // 此时在 child溜了一个坑下次肯定要填充child
            if (arr[child] > parentVal) {
                arr[parent] = arr[child];
                parent = child;
                child = (parent << 1) + 1;
            } else {
                break;
            }
        }
        arr[parent] = parentVal;
    }


    public int[] heapSort(int[] arr) {

        int len = arr.length;
        for (int parent = arr.length >>> 1; parent >= 0; parent--) {
            buildHeap(arr, parent, len);
        }
        for (int i = arr.length - 1; i > 0; i--) {
            int min = arr[i];
            arr[i] = arr[0];
            arr[0] = min;
            buildHeap(arr, 0, i);
        }
        return arr;
    }


    @Test
    public void testHeapSort() {
        log.info("heapSort = {}", heapSort(new int[]{
                5, 2, 3, 4, 7,
        }));

    }
}
