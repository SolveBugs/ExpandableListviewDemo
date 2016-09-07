package test.com.expandablelistviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private CustomExpandListview listview;
    private MyAdapter myAdapter;

    private String[] parentSource = {"分类1", "分类2", "分类3", "分类4", "分类5"};
    private ArrayList<String> parent = new ArrayList<>();
    private Map<String, ArrayList<String>> datas = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (CustomExpandListview) findViewById(R.id.listView);

        //模拟数据
        for (int i = 0; i < parentSource.length; i++) {
            parent.add(parentSource[i]);
        }

        for (int i = 0; i < parent.size(); i++) {
            String str = parent.get(i);
            ArrayList<String> temp = new ArrayList<>();
            for (int j = 0; j < 20; j++) {
                temp.add("" + j);
            }
            datas.put(str, temp);
        }


        myAdapter = new MyAdapter(this, parent, datas, listview);
        listview.setAdapter(myAdapter);
        listview.setHeaderView(getLayoutInflater().inflate(
                R.layout.indictor_layout, listview, false));

        listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Toast.makeText(MainActivity.this, "点击了第" + (i + 1) + " 类的第" + i1 + "项", Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        // 默认全部展开
        for (int i = 0; i < parent.size(); i++) {
            listview.expandGroup(i);
        }
    }
}
