package com.sung.sort.bubble;

/**
 * Created by sungang on 2016/1/15.10:09
 */
public class BubbleSort {

    public static void main(String[] args) {

        int[] list = {3,1,6,2,9,0,7,4,5};

        sort(list);

        for(int i=0;i<list.length;i++){//排序后打印数组中的元素
            System.out.print(list[i] + " ");
        }
        System.out.println();
    }


    public static void sort(int[] list){
        int tmp ;
        for (int i = 0; i < list.length; i++){ //趟数
            for (int j = 0; j < list.length - i - 1; j++){//比较次数
                if (list[j] > list[j + 1]){
                    tmp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = tmp;
                }
            }

        }
    }
}
