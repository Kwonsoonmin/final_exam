package com.example.sm.problem2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MyBaseAdapter adapter;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Employee> emp_list = null;

        adapter = new MyBaseAdapter(this, emp_list);
        listview = (ListView) findViewById(R.id.listView1) ;
        listview.setAdapter(adapter);
        listview.setOnItemClickListener((AdapterView.OnItemClickListener)adapter);
    }
    @Override
    public void onClick(View v){
        EditText edit_name = (EditText) findViewById(R.id.edit_name);
        EditText edit_age = (EditText) findViewById(R.id.edit_age);
        EditText edit_salary = (EditText) findViewById(R.id.edit_salary);

        String name = edit_name.getText().toString();
        String age = edit_age.getText().toString();
        String salary = edit_salary.getText().toString();

        int a = Integer.parseInt(age);
        int s = Integer.parseInt(salary);

        Employee employee;

        switch (v.getId()){
            case R.id.btn_inc:
                employee = new Employee(name,a,s);
                employee.increase();
                break;

            case R.id.btn_dec:
                employee = new Employee(name,a,s);
                employee.decrease();
                break;

            case R.id.btn_store:
                employee = new Employee(name, a,s);
                adapter.add(employee);
                break;

            case R.id.btn_modify:
                employee = new Employee(name, a,s);
                adapter.add(employee);
                break;

            case R.id.btn_delete:
                employee = new Employee(name, a, s);
                int index = listview.getId();
                adapter.delete(index);
                break;
        }
    }
}

interface Payment {
    void increase();
    void decrease();
}
