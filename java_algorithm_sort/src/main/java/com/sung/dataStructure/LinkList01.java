package com.sung.dataStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/* LINKED LIST
 * insert and delete is fast in linked list
 * searching in linked list is slow
 *
 * MOVE NEXT
 * ----------------------
 * H
 * O->|1|->|2|->null
 * ---------------------
 * h = h.next;
 * -------------------
 * |1|->|2|->null
 *       ^
 *    O--|
 *    H
 * -------------------
 *
 *
 * DELETE(3) in MIDDLE
 * --------------------------------------------------------
 * head/cur
 * O---------->|1|---->|2|---->|3|------>|4|------->null
 * --------------------------------------------------------
 *
 * move before 3
 * ------------------------------------------------------------
 * head
 * O------->|1|----->|2|----->|3|----->|4|------->null
 *                 ^
 *                 |
 *             Cur O
 * -----------------------------------------------------------
 *
 * del = curNode.next;
 * ---------------------------------------------------------
 * head
 *   O------->|1|----->|2|----->|3|----->|4|------->null
 *                      ^        ^
 *                      |        |
 *                  Cur O    del O
 * -----------------------------------------------------------
 *
 * curNode.next = CurNode.next.next;
 * --------------------------------------------------------
 *                       -----------------|
 * head                 |                 V
 *   O------->|1|----->|2|      |3|----->|4|------->null
 *                      ^        ^
 *                      |        |
 *                  Cur O    del O
 * ----------------------------------------------------------
 * */
/**
 * Created by sungang on 2016/2/17.13:26
 */
public class LinkList01 {

    /**
     * CREATE NODE **
     */
    private class node {
        private String name;
        private int age;
        private node next;

        /**
         * Constructor **
         */
        public node() {
            name = "";
            age = 0;
            next = null;
        }

        public node(String n, int a) {
            name = n;
            age = a;
            next = null;
        }/*** End of constructor ***/
    }/*** End of Node class ***/


    /**
     * Linked List Constructor and Fields **
     */
    private node head_node;
    private node current_node;
    private node new_node;
    private node del_node;

    public LinkList01() {
        // TODO Auto-generated constructor stub
    }


    /*********************/
    /*** Insert Method ***/
    /**
     * *****************
     */
    public void insert(String n, int a) {
        current_node = head_node;  //set current node to head node
        boolean inList = false;
        boolean skip = false;

        if (head_node == null)  //if its empty
        {
            head_node = new node(n, a);    //create head node
            head_node.next = null;
        } else                       //if not empty
        {
            /*** skip double ***/
            while (current_node != null) {
                if (current_node.name.equals(n)) {
                    skip = true;
                }
                current_node = current_node.next;
            }

            if (!skip) {
                /*** insert in begining ***/
                current_node = head_node;
                if (head_node.age > a) {
                    new_node = new node(n, a); //create new node
                    new_node.next = head_node;
                    head_node = new_node;
                    inList = true;
                }

                /*** insert in middle ***/ //error
                current_node = head_node;
                while (current_node != null && current_node.next != null) {
                    if (current_node.age < a && current_node.next.age >= a) {
                        new_node = new node(n, a);
                        new_node.next = current_node.next;
                        current_node.next = new_node;
                        inList = true;
                    }
                    current_node = current_node.next;
                }
                /*** insert in end ***/
                if (!inList) {
                    current_node = head_node;
                    while (current_node.next != null)  //go in end
                    {
                        current_node = current_node.next;
                    }
                    new_node = new node(n, a);  //create new node
                    current_node.next = new_node;    //set cur next to new node
                    new_node.next = null;            //set newnode next to null
                }
            }//End of skip if statment
        }//End of else statment
    }/*** End of Insert Method ***/


    /*********************/
    /*** Delete Method ***/
    /**
     * *****************
     */
    public void delete(String n) {
        current_node = head_node; //set current node to new node
        boolean found = false;

        if (head_node == null)   //List is empty
        {
            System.out.println("LinkList is already empty!");
        } else                      //List is not empty
        {
            /*** delete from begining ***/
            if (head_node.name.equals(n)) {
                del_node = head_node.next;
                head_node = head_node.next;
                del_node = null;
                found = true;
            }

            /*** delete from middle and end ***/
            while (current_node != null && current_node.next != null) {
                if (current_node.next.name.equals(n)) {
                    del_node = current_node.next;
                    current_node.next = current_node.next.next;
                    del_node = null;
                    found = true;
                }
                current_node = current_node.next;
            }//End of while loop

            /*** if name is not in Linked List***/
            if (!found) {
                System.out.println(n + " is not in LinkedList!");
                System.out.println();
            }
        }//End of else statment
    }/*** End of delete Method ***/


    /**********************/
    /*** Display Method ***/
    /**
     * ******************
     */
    public void display() {
        current_node = head_node;
        System.out.println("name\tage");
        if (current_node == null) {
            System.out.println("List is Empty!");
        } else {
            while (current_node != null) {
                System.out.println(current_node.name + "\t" + current_node.age);
                current_node = current_node.next;
            }
        }
        System.out.println();
    }/*** End of Display Method/


     /*******************/
    /*** Main Method ***/
    /**
     * ***************
     */
    public static void main(String[] args) throws IOException {
        LinkList01 m = new LinkList01();

        String cmd = "";
        String name = "";
        int age = 0;


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a Command: ");
        cmd = br.readLine();
        while (!(cmd.equals("exit"))) {
            if (cmd.equals("insert")) {
                System.out.print("Enter name to insert");
                name = br.readLine();
                System.out.print("Enter age to insert");
                age = sc.nextInt();
                System.out.println();
                m.insert(name, age);
            } else if (cmd.equals("delete")) {
                System.out.print("Enter name to delete");
                name = br.readLine();
                m.delete(name);
            } else if (cmd.equals("display")) {
                m.display();
            }
            System.out.print("Enter a Command: ");
            cmd = br.readLine();
        }
    }
}
