package com.example.sm.problem3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<CustomerThread> list = new ArrayList<CustomerThread>();
        Manager manager = new Manager();

        int sum = 0;

        for(int i = 0 ; i < 10 ; i++){
            Customer customer = new Customer("Customer" + i);
            CustomerThread ct = new CustomerThread(customer);
            list.add(ct);
            manager.add_customer(customer);
            ct.start();
        }


        for(CustomerThread ct : list){
            try {
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        manager.sort();

        MyBaseAdapter adapter = new MyBaseAdapter(this, manager.list);
        ListView listview = (ListView) findViewById(R.id.listView1) ;
        listview.setAdapter(adapter);


    }
}

class CustomerThread extends Thread{

    Customer customer;

    CustomerThread(Customer customer){
        this.customer = customer;
    }

    public void run() {
        while(true) {
            try{
                customer.work();
            }catch (SecurityException se) {
                se.printStackTrace();
            }
        }
    }
}

abstract class Person{

    static int money = 100000;
    int spent_money = 0;
    abstract void work();

}


class Customer extends Person{

    String name;
    Customer(String name){
        this.name = name;
    }

    void work() {
        int money = (int)(Math.random() * 1000);
        spent_money += money;
    }
}


class Manager extends Person{
    ArrayList <Customer> list = new ArrayList<Customer>();

    void add_customer(Customer customer) {
        list.add(customer);
    }

    void sort(){ // 직접 소팅 알고리즘을 이용하여 코딩해야함. 자바 기본 정렬 메소드 이용시 감
        int max = list.get(0).spent_money;
        int index = 0;

        for(int i = 0; i< list.size(); i++) {
            int temp = list.get(i).spent_money;
            if(temp > max) {
                max = temp;
                Customer temp1 = list.get(index);
                Customer temp2 = list.get(i);
                list.add(0, temp1);
                list.add(1,temp2);
                index = i;
            }
        }
    }

    @Override
    void work() {
        sort();
    }
}

// need something here

