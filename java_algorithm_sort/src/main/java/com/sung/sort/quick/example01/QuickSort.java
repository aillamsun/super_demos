package com.sung.sort.quick.example01;

/**
 * Created by sungang on 2016/1/15.9:49
 */
public class QuickSort {

    public static void main(String[] args) {
        Integer[] list = {34, 3, 53, 2, 23, 7, 14, 10};
        QuickSort qs = new QuickSort();
        qs.quick(list);
        for (int i = 0; i < list.length; i++) {
            System.out.print(list[i] + " ");
        }
        System.out.println();
    }

    public void quick(Integer[] str) {
        _quickSort(str, 0, str.length - 1);
    }

    public void _quickSort(Integer[] list, int low, int high) {
        if (low < high) {
            //将list数组进行一分为二
            int middle = getMiddle(list, low, high);
            //对低字表进行递归排序
            _quickSort(list, low,middle - 1);
            //对高字表进行递归排序
            _quickSort(list, middle + 1, high);
        }
    }

    public int getMiddle(Integer[] list, int low, int high) {

        int tmp = list[low];
        while (low < high) {
            while (low < high && list[high] > tmp) {
                high--;
            }

            list[low] = list[high];
            while (low < high && list[low] < tmp) {
                low++;
            }

            list[high] = list[low];
        }

        list[low] = tmp;
        return low;
    }
}
