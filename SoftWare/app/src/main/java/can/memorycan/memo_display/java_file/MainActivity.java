package jay.com.expandablelistviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Group> gData = null;
    private ArrayList<ArrayList<Item>> iData = null;
    private ArrayList<Item> lData = null;
    private Context mContext;
    private ExpandableListView list_memo;
    private MyBaseExpandableListAdapter myAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;

        list_memo = (ExpandableListView) findViewById(R.id.list_memo);

        //数据准备
        gData = new ArrayList<Group>();
        iData = new ArrayList<ArrayList<Item>>();
        gData.add(new Group("近期待完成",1));
        gData.add(new Group("超时未完成",-1));
        gData.add(new Group("已完成任务",0));

        //待完成组
        lData = new ArrayList<Item>();
        lData.add(new Item(1,"今天要答辩","10月18号"));
        lData.add(new Item(2,"周三要答辩","10月19号"));
        lData.add(new Item(3,"周一要答辩","10月17号"));
        lData.add(new Item(4,"明天要答辩","10月15号"));
        lData.add(new Item(5,"后天要答辩","10月19号"));
        iData.add(lData);

        //未完成组
        lData = new ArrayList<Item>();
        lData.add(new Item(6,"今天要吃饭","10月18号"));
        lData.add(new Item(7,"周三要喝酒","10月19号"));
        lData.add(new Item(8,"周一要开心","10月17号"));
        lData.add(new Item(9,"明天要快乐","10月15号"));
        lData.add(new Item(10,"后天要打码","10月19号"));
        iData.add(lData);

        //已完成组
        lData = new ArrayList<Item>();
        lData.add(new Item(101,"今天要打球","10月18号"));
        lData.add(new Item(102,"周三要找喵","10月19号"));
        lData.add(new Item(103,"周一要找喵","10月17号"));
        lData.add(new Item(104,"明天要打球","10月15号"));
        lData.add(new Item(105,"后天要找喵","10月19号"));
        iData.add(lData);

        myAdapter = new MyBaseExpandableListAdapter(gData,iData,mContext);
        list_memo.setAdapter(myAdapter);

        if(list_memo!=null)
        {
            list_memo.expandGroup(0);
//            list_memo.expandGroup(1);
            list_memo.expandGroup(2);
        }

        //为列表设置点击事件
        list_memo.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(mContext, "你点击了：" + iData.get(groupPosition).get(childPosition).get_memo_id() + groupPosition, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
