package can.memorycan;

import android.database.MergeCursor;
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
import com.a404note.aboutsqlite.DBManager;
import com.a404note.aboutsqlite.Memo;

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
        Log.i(TAG, "---->" + msString);
    }

    @Test
    public void changeUser_password() {
        DBManager mgr;
        mgr=new DBManager(InstrumentationRegistry.getTargetContext());
        mgr.changeUser_password(1,"12345678");
    }
    @Test
    public void testinsert_Memo() {
        DBManager mgr;
        mgr=new DBManager(InstrumentationRegistry.getTargetContext());
        Memo memo=new Memo("接口测试","2018-11-12 17:16:00",1,1,
        1,1,1,1,0,":)");
        mgr.insert_Memo(memo);
        int a;
        a=mgr.getmemoid();
        memo.setMemo_id(a);
        //Memo mm =new Memo();
        Memo mm=mgr.returnamemo(a);
        System.out.println(mm.getMemo_title());
    }
}

