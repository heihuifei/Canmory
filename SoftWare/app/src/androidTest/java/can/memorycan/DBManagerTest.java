package can.aboutsqlite;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import android.util.Log;
import static org.junit.Assert.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class DBManagerTest {
    private final String TAG = "DBManagerTest";

    @Before
    public void setUp() throws Exception {
        DBManager mgr;
        mgr=new DBManager(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void insert_User() {
        DBManager mgr;
        mgr=new DBManager(InstrumentationRegistry.getTargetContext());
        mgr.insert_User(3,"qwerasdz112","13215983398");
        String msString = mgr.getUser_password(1);
        System.out.println(msString);
        Log.i(TAG, "----------------------------------------------->" + msString);
    }

    @Test
    public void changeUser_password() {
        DBManager mgr;
        mgr=new DBManager(InstrumentationRegistry.getTargetContext());
        mgr.changeUser_password(1,"12345678");
    }
    @Test
    //创建一条备忘录
    public void testinsert_Memo() {
        DBManager mgr;
        mgr=new DBManager(InstrumentationRegistry.getTargetContext());
        Memo memo=new Memo("明天有博客",
                "2018-11-14 23:00:00",2,0,0,
                0, 1,1,0,"：）");
        mgr.insert_Memo(memo);
        //我发现好像不用返回memoid，因为本身这个创建的函数就是没有返回值的
        //int memoid=0;
        //memoid=mgr.getmemoid();
        //memo.setMemo_id(memoid);
        Log.i(TAG, "----------------------------------------------->");
    }

    @Test
    //返回一条备忘录详情
    public void get_aMemo(){
        DBManager mgr;
        mgr=new DBManager(InstrumentationRegistry.getTargetContext());
        Memo memo=mgr.returnamemo(20);
        //System.out.println(memo.getMemo_title());
        Log.i(TAG, "--------------------------->" + memo.getMemo_title());
        //memo.setMemo_title("33333");
        //String dt=memo.getMemo_title();
        //String title=memo.getMemo_title();
        //System.out.println(title);
        //System.out.println(memo.getMemo_dtime()+" "+memo.getMemo_title());
    }

    @Test
    //测试删除已完成备忘录
    public void testdeletedone(){
        DBManager mgr;
        mgr=new DBManager(InstrumentationRegistry.getTargetContext());
        mgr.deletedone(1);
    }

    @Test
    //测试删除多条备忘录
    public void testdelete(){
        DBManager mgr;
        mgr=new DBManager(InstrumentationRegistry.getTargetContext());
        List<Integer> list = new ArrayList();
        list.add(1);
        list.add(3);
        mgr.deletedone(list);
    }

    @Test
    //测试返回已完成备忘录
    public void returndone1(){
        DBManager mgr;
        mgr=new DBManager(InstrumentationRegistry.getTargetContext());
        List<Memo> memos=mgr.returnmemo1(1);
        Log.i(TAG, "--------------------------->" );
    }

    @Test
    //测试返回未完成列表(未到期)
    public void returnundone1(){
        DBManager mgr;
        mgr=new DBManager(InstrumentationRegistry.getTargetContext());
        List<Memo> memos=mgr.returnmemo2(1);
        Log.i(TAG, "--------------------------->" );
    }

    @Test
    //测试返回未完成列表(过期)
    public void returnundone2(){
        DBManager mgr;
        mgr=new DBManager(InstrumentationRegistry.getTargetContext());
        List<Memo> memos=mgr.returnmemo3(1);
        Log.i(TAG, "--------------------------->" );
    }


    @Test
    //测试备忘录修改
    public void updatememo(){
        DBManager mgr;
        mgr=new DBManager(InstrumentationRegistry.getTargetContext());
        Memo memo=new Memo("把666改成999",
                "2018-11-19 23:00:00",0,0,0,
                0, 0,1,0,"qmq");
        memo.setMemo_id(20);
        mgr.update_Memo(memo);
    }

    @Test
    //测试修改trip_on为开
    public void testtripon(){
        DBManager mgr;
        mgr=new DBManager(InstrumentationRegistry.getTargetContext());
        mgr.changeTrip_on(1,1);
    }

    @Test
    //测试修改trip_priority为2
    public void testtrippriority(){
        DBManager mgr;
        mgr=new DBManager(InstrumentationRegistry.getTargetContext());
        mgr.changeTrip_priority(1,2);
    }

    @Test
    //测试修改trip_paper为开
    public void testtrippaper(){
        DBManager mgr;
        mgr=new DBManager(InstrumentationRegistry.getTargetContext());
        mgr.changeTrip_paper(1,1);
    }

    @Test
    //测试修改备忘录的状态，使之完成
    public void testchangestate(){
        DBManager mgr;
        mgr=new DBManager(InstrumentationRegistry.getTargetContext());
        mgr.changestate(5);
    }



}