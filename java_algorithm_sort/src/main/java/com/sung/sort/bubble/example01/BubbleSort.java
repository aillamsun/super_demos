package com.sung.sort.bubble.example01;

/**
 * Created by sungang on 2016/1/15.10:18
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] values = {17,3,24,8,10,0,16,15,2};
        int[] a = new int[0];
        sort(values);
        for (int i = 0; i < values.length ; i++ ){
            System.out.print(values[i] + " ");
        }

        System.out.println();
    }

    public  static  void sort(int[] values){

        int tmp;
        for (int i = 0; i < values.length; i++){ //
            for (int j = 0; j < values.length - i - 1; j++){
                if (values[j] > values[j + 1]){
                    tmp = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = tmp;
                }
            }
        }
    }
}
