package com.sung.sort.binSearch;

/**
 * Created by sungang on 2016/2/22.14:10
 */
public class BinarySearch {

    public static void main(String[] args) {
        int arr[] = {3,5,11,17,21,23,28,30,32,50,64,78,81,95,101};
        System.out.println(binSearch(arr, 0, arr.length - 1, 81));
    }

    // 二分查找递归实现
    public static int binSearch(int[] arr,int start,int end,int key){

        int mid = (end - start) / 2 + start;
        if (arr[mid] == key){
            return mid;
        }

        if (start >= end){
            return -1;
        }else if (key > arr[mid]){
            return binSearch(arr,mid + 1,end,key);
        }else if (key < arr[mid]){
            return binSearch(arr,start, mid - 1,key);
        }
        return -1;
    }


    // 二分查找普通循环实现
    public static int binSearch(int srcArray[], int key) {
        int mid = srcArray.length / 2;
        if (key == srcArray[mid]) {
            return mid;
        }

        int start = 0;
        int end = srcArray.length - 1;
        while (start <= end) {
            mid = (end - start) / 2 + start;
            if (key < srcArray[mid]) {
                end = mid - 1;
            } else if (key > srcArray[mid]) {
                start = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

}
