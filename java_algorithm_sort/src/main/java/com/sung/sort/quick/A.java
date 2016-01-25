package com.sung.sort.quick;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by sungang on 2016/1/15.9:48
 */
public class A {


    static Set<String> ss = new HashSet<>();

    public static void main(String[] args) {
        int[] v = {1, 4, 6, 9, 11, 13, 15};
        int[] v2 = {15, 13, 11, 9, 6, 4, 1};
        //int [] v =  {1,2,3,4,5,6,7};


//        print(v);
//        for (String s : ss){
//            System.out.println(ss);
//        }

        //bb(v2);
        cc(v2);
    }

    public static void aa(int[] v) {
        int temp = 15;
        for (int c : v) {
            if (c < temp / 2) {
                if (c != 1) {
                    ss.add(c + "," + (temp - c));
                }

            } else {

            }
        }
        System.out.println(ss);
    }

    public static void bb(int[] v) {
        int hash[] = new int[1000];
        int K = 15;
        int flag = 0;
        for (int i : v) {
            hash[i] = 1;
        }
        for (int i : v) {
            if (hash[K - i] == 1) {
                if (flag == 1) {
                    System.out.print(" ");
                }
                System.out.print("(" + i + "," + (K - i) + ")");
                flag = 1;
                hash[K - i] = 2;
                hash[i] = 2;
            }
        }
    }

    public static void cc(int[] v) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int K = 15;
        int flag = 0;
        for (int i : v) {
            map.put(i, 1);
        }

        for (int i : v) {
            if (map.get(K - i) != null && map.get(K - i) == 1) {

                if (flag == 1) {
                    System.out.print(",");
                }

                System.out.print(i + "," + (K - i));
                flag = 1;
                map.put(K - i, 2);
                map.put(i, 2);

            }
        }
    }


    public static void print(int[] v) {
        int temp = 15;
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v.length - i - 1; j++) {
                if (v[i] + v[j + 1] == temp) {
                    ss.add(v[i] + "," + v[j + 1]);
                }
            }
        }
    }
}
