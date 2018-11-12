package can.memorycan.memo_add;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import can.aboutsqlite.*;
import can.memorycan.memo_add.clock.widget.CustomDatePicker;
import android.widget.AdapterView.OnItemSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import can.memorycan.R;

import java.util.ArrayList;

import can.memorycan.R;
import can.memorycan.memo_add.list_View.Group;
import can.memorycan.memo_add.list_View.Item;
import can.memorycan.memo_add.list_View.MyBaseExpandableListAdapter;
import can.memorycan.memo_add.clock.Clock;
public class memo_add extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout selectDate, selectTime;
    private TextView currentDate, currentTime;
    private CustomDatePicker customDatePicker1, customDatePicker2;
    private ArrayList<Group> gData = null;
    private ArrayList<ArrayList<Item>> iData = null;
    private ArrayList<Item> lData = null;
    private Context mContext;
    private ExpandableListView exlist_lol;
    private MyBaseExpandableListAdapter myAdapter = null;
    private DBManager mgr;

    /*创建Memo所需的变量*/
    int memo_id;
    String memo_title;
    String memo_ctime="now";
    String memo_dtime;
    int memo_priority;
    int memo_periodicity;
    int memo_advanced;
    int memo_remind;
    int memo_paper;
    int user_id;
    int memo_done;
    String memo_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_add);

        /*创建DATABASE*/
        mgr = new DBManager(this);

        /*建立日期添加的选择*/
        selectTime = (RelativeLayout) findViewById(R.id.selectTime);
        selectTime.setOnClickListener( this);
        selectDate = (RelativeLayout) findViewById(R.id.selectDate);
        selectDate.setOnClickListener( this);
        currentDate = (TextView) findViewById(R.id.currentDate);
        currentTime = (TextView) findViewById(R.id.currentTime);
        initDatePicker();

        /*为了可折叠下拉列表准备spinner类型*/
        String[] time = getResources().getStringArray(R.array.time);
        ArrayAdapter<String> _Adapter0=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, time);
        String[] advancedTime = getResources().getStringArray(R.array.advanced_time);
        ArrayAdapter<String> _Adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, advancedTime);
        String[] remindType = getResources().getStringArray(R.array.remind_type);
        ArrayAdapter<String> _Adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, remindType);
        String[] switch1 = getResources().getStringArray(R.array.switch1);
        ArrayAdapter<String> _Adapter3=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, switch1);



        mContext = memo_add.this;
        exlist_lol = (ExpandableListView) findViewById(R.id.exlist_lol);


        //数据准备
        gData = new ArrayList<Group>();
        iData = new ArrayList<ArrayList<Item>>();
        gData.add(new Group("更多设置"));
        lData = new ArrayList<Item>();

        //更多设置组
        lData.add(new Item(R.drawable.icon_generate,"任务周期性",_Adapter0));
        lData.add(new Item(R.drawable.icon_bell,"提前定时提醒",_Adapter1));
        lData.add(new Item(R.drawable.icon_tel,"到期提醒方式",_Adapter2));
        lData.add(new Item(R.drawable.icon_small_picture,"是否显示在锁屏",_Adapter3));
        iData.add(lData);


        //将Adapter加入方法创建myAdapter类
        myAdapter = new MyBaseExpandableListAdapter(gData,iData,mContext);
        exlist_lol.setAdapter(myAdapter);




        //为列表设置点击事件
        exlist_lol.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(mContext, "你点击了：" + iData.get(groupPosition).get(childPosition).getiName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });


    }
    /*为页面添加点击时间的触发*/
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectDate:
                // 日期格式为yyyy-MM-dd
                customDatePicker1.show(currentDate.getText().toString());
                break;

            case R.id.selectTime:
                // 日期格式为yyyy-MM-dd HH:mm
                customDatePicker2.show(currentTime.getText().toString());
                break;
            case R.id.memo_add_title:
                //如果是标题
                EditText editText=findViewById(R.id.memo_add_title);
                memo_title=editText.getText().toString();
                break;
            case R.id.currentTime:
                TextView textView=findViewById(R.id.currentTime);
                memo_dtime=textView.getText().toString();
                break;
            case R.id.memo_add_priority:
                Spinner spinner=findViewById(R.id.memo_add_priority);
                String temp=spinner.getSelectedItem().toString();
                if(temp=="无") {memo_priority=0;}
                else if(temp=="低") {memo_priority=1;}
                else if(temp=="中") {memo_priority=2;}
                else if(temp=="高") {memo_priority=3;}
                break;


        }
    }

    /*进行时间选择的初始化*/
    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        currentDate.setText(now.split(" ")[0]);
        currentTime.setText(now);

        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDate.setText(time.split(" ")[0]);
            }
        }, "2018-01-01 00:00", "2035-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentTime.setText(time);
            }
        }, "2018-01-01 00:00","2035-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }
    /*销毁活动时关闭DB*/
    protected void onDestroy() {
        super.onDestroy();
        //应用的最后一个Activity关闭时应释放DB
        mgr.closeDB();
    }

}
