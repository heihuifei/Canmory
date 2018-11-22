package can.main_delete;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.baiduspeechdialog.dialog.SpeechBottomSheetDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.util.ArrayList;
import can.aboutsqlite.DBManager;
import can.aboutsqlite.Memo;
import can.main_delete.Group_new;
import can.main_delete.MyBaseExpandableListAdapter_new;
import can.memorycan.R;
import can.memorycan.memo_add.memo_add;
import can.memorycan.speech;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = speech.class.getSimpleName();
    private Button mOpenSpeechDialogBtn;
    private TextView mResultTv;
    private ArrayList<Group_new> gData = null;
    private ArrayList<ArrayList<Memo>> iData = null;
    private ArrayList<Memo> lData = null;
    private Context mContext;
    private ExpandableListView list_memo;
    private ImageButton imagebotton_slide;
    private ImageButton imagebotton_delete;
    private ImageButton imagebotton_speak;
    private ImageButton imagebotton_add;
    private MyBaseExpandableListAdapter_new myAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DBManager mgr = new DBManager(this);
        mContext = MainActivity.this;
        list_memo = (ExpandableListView) findViewById(R.id.list_memo);
        imagebotton_slide = (ImageButton) findViewById(R.id.imageButton_slide);
        imagebotton_delete = (ImageButton) findViewById(R.id.imageButton_delete);
        imagebotton_add = (ImageButton) findViewById(R.id.imageButton_add);
        gData = new ArrayList<Group_new>();
        iData = new ArrayList<ArrayList<Memo>>();
        gData.add(new Group_new("近期待完成",1));
        gData.add(new Group_new("超时未完成",-1));
        gData.add(new Group_new("已完成任务",0));

        lData = new ArrayList<Memo>();
        lData = mgr.returnmemo2(1);
        iData.add(lData);

        lData = new ArrayList<Memo>();
        lData = mgr.returnmemo3(1);
        iData.add(lData);

        lData = new ArrayList<Memo>();
        lData = mgr.returnmemo1(1);
        iData.add(lData);

        myAdapter = new MyBaseExpandableListAdapter_new(gData,iData,mContext);
        list_memo.setAdapter(myAdapter);
        if(list_memo!=null)
        {
            list_memo.expandGroup(0);
            list_memo.expandGroup(1);
        }
        final Intent detail = new Intent(MainActivity.this,memo_add.class);
        list_memo.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                detail.putExtra("memo_id",iData.get(groupPosition).get(childPosition).getMemo_id());
                startActivity(detail);
                return true;
            }
        });
        onePermission();
        initViews();
//        initEvents();
        mOpenSpeechDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开百度语音对话框
                SpeechBottomSheetDialog speechBottomSheetDialog = SpeechBottomSheetDialog.getInstance(MainActivity.this);
                speechBottomSheetDialog.seOnResultListItemClickListener(new SpeechBottomSheetDialog.OnResultListItemClickListener() {
                    @Override
                    public void onItemClick(String title) {
                        //填充到输入框中
//                        mResultTv.setText(title);
                        Memo memo = new Memo(title,
                                "2018-11-14 23:00:00",2,0,0,
                                0, 1,1,0,"：）");
                        mgr.insert_Memo(memo);
                    }

                });
                speechBottomSheetDialog.show(getSupportFragmentManager(), TAG);
            }
        });
    }

    public void initViews() {
        mOpenSpeechDialogBtn = findViewById(R.id.btn_openSpeechDialog);
        //mOpenSpeechLongDialogBtn = findViewById(R.id.btn_openSpeechLongDialog);
        mResultTv = findViewById(R.id.tv_result);
    }

    public void initEvents() {
        mOpenSpeechDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开百度语音对话框
                SpeechBottomSheetDialog speechBottomSheetDialog = SpeechBottomSheetDialog.getInstance(MainActivity.this);
                speechBottomSheetDialog.seOnResultListItemClickListener(new SpeechBottomSheetDialog.OnResultListItemClickListener() {
                    @Override
                    public void onItemClick(String title) {
                        //填充到输入框中
                        mResultTv.setText(title);
                        Memo memo = new Memo(title,
                "2018-11-14 23:00:00",2,0,0,
                0, 1,1,0,"：）");
                    }
                });
                speechBottomSheetDialog.show(getSupportFragmentManager(), TAG);
            }
        });
    }

    /**只有一个运行时权限申请的情况*/
    private void onePermission(){
        RxPermissions rxPermissions = new RxPermissions(MainActivity.this); // where this is an Activity instance
        rxPermissions.request(Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) //权限名称，多个权限之间逗号分隔开
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        Log.e(TAG, "{accept}granted=" + granted);//执行顺序——1【多个权限的情况，只有所有的权限均允许的情况下granted==true】
                        if (granted) { // 在android 6.0之前会默认返回true
                            // 已经获取权限
                        } else {
                            // 未获取权限
                            Toast.makeText(MainActivity.this, "您没有授权该权限，请在设置中打开授权", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG,"{accept}");//可能是授权异常的情况下的处理
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG,"{run}");//执行顺序——2
                    }
                });
    }
}
