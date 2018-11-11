package can.aboutsqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dale on 2018/11/04.
 */
public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;
    private static final String TABLE1_NAME = "user";
    private static final String TABLE2_NAME = "memo";
    private static final String TABLE3_NAME = "picture";
    private static final String TABLE4_NAME = "audio";
    private static final String TABLE5_NAME = "wallpaper";


    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }
    /*创建账户*/
    public void insert_User(int user_id,String user_password,String user_tel){
        db.beginTransaction();  //开始事务
        try {
            ContentValues cv = new ContentValues();
            cv.put("user_id",user_id);
            cv.put("user_password",user_password);
            cv.put("user_tel",user_tel);
            db.insert(TABLE1_NAME,null,cv);
            db.setTransactionSuccessful();  //设置事务成功完成
        }finally {
            db.endTransaction();    //结束事务
        }
    }
    /*修改账户密码*/
    public void changeUser_password(int user_id,String user_password){
        db.execSQL("update user set user_id= ? where  user_id=?", new Object[]{ user_id,user_password });
    }
    /*用户获取密码*/
    public String getUser_password(int user_id){
        String sql="select user_password from user where user_id ="+user_id;
        Cursor c = null;
        c = db.rawQuery(sql,null);
        if(c.moveToFirst()){
            c.close();
            return c.getString(0);
        }else {
            c.close();
            return "none";//返回用户不存在
        }
    }
    /*创建备忘录*/
    public  void insert_Memo(Memo memo) {
        db.beginTransaction();  //开始事务
        try {
            ContentValues cv = new ContentValues();
            cv.put("memo_title",memo.getMemo_title());
            cv.put("memo_ctime",memo.getMemo_ctime());
            cv.put("memo_dtime",memo.getMemo_dtime());
            cv.put("memo_priority",memo.getMemo_priority());
            cv.put("memo_periodicity",memo.getMemo_periodicity());
            cv.put("memo_advanced",memo.getMemo_advanced());
            cv.put("memo_remind",memo.getMemo_remind());
            cv.put("meo_paper",memo.getMemo_paper());
            cv.put("user_id",memo.getUser_id());
            cv.put("memo_done",memo.getMemo_done());
            cv.put("memo_content",memo.getMemo_content());
            db.insert(TABLE2_NAME,null,cv);
            db.setTransactionSuccessful();  //设置事务成功完成
        }finally {
            db.endTransaction();    //结束事务
        }
    }

    /*修改trip_on为关*/
    public void changeTrip_on(int user_id){
        db.execSQL("update user set trip_on= 0 where  user_id=?", new Object[]{ user_id });
    }
    /*修改trip_priority为关*/
    public void changeTrip_priority(int user_id){
        db.execSQL("update user set trip_priority=0  where  user_id=?", new Object[]{ user_id });
    }
    /*修改trip_paper为关*/
    public void changeTrip_paper(int user_id){
        db.execSQL("update user set trip_paper= 0 where  user_id=?", new Object[]{ user_id });
    }
    /*修改weather_on为关*/
    public void changeWeather_on(int user_id){
        db.execSQL("update user set weather_on= 0 where  user_id=?", new Object[]{ user_id });
    }
    /*修改weather_priority为关*/
    public void changeWall_priority(int user_id){
        db.execSQL("update user set weather_priority=0  where  user_id=?", new Object[]{ user_id });
    }
    /*修改weather_paper为关*/
    public void changeWallpaper_paper(int user_id){
        db.execSQL("update user set weather_paper= 0 where  user_id=?", new Object[]{ user_id });
    }
    /*修改parcel_on为关*/
    public void changeParcel_on(int user_id){
        db.execSQL("update user set parcel_on= 0 where  user_id=?", new Object[]{ user_id });
    }
    /*修改parcel_priority为关*/
    public void changeParcel_priority(int user_id){
        db.execSQL("update user set parcel_priority=0  where  user_id=?", new Object[]{ user_id });
    }
    /*修改parcel_paper为关*/
    public void changeParcel_paper(int user_id){
        db.execSQL("update user set parcel_paper= 0 where  user_id=?", new Object[]{ user_id });
    }
    /*修改analysis_on为关*/
    public void changeAnalysis_on(int user_id){
        db.execSQL("update user set analysis_on= 0 where  user_id=?", new Object[]{ user_id });
    }
    /*修改analysis_priority为关*/
    public void changeAnalysis_priority(int user_id){
        db.execSQL("update user set trip_priority=0  where  user_id=?", new Object[]{ user_id });
    }
    /*修改analysis_paper为关*/
    public void changeAnalysis_paper(int user_id){
        db.execSQL("update user set analysis_paper= 0 where  user_id=?", new Object[]{ user_id });
    }

    //改变备忘录的完成状态
    public void changestate(int memo_id){
        db.execSQL("update memo set memo_done = 1 where memo_id =?", new Object[]{ memo_id });
    }

    //删除已完成备忘录列表
    public void deletedone(int user_id){
        db.execSQL("delete from memo where memo_done==1 and user_id=?", new Object[]{ user_id });
    }

    //删除多条备忘录
    public void deletedone(List<Integer> memoidlist ){
        Iterator iter = memoidlist.iterator();
        while(iter.hasNext()){
            db.execSQL("delete from memo where memo_id=?", new Object[]{ iter.next() });
        }
    }
    //返回主界面的已完成列表(memo中任务id任务标题到期时间有效)
    public List<Memo> returnmemo1(int user_id){
        String sql= "select * from "+TABLE2_NAME+" where user_id = "+user_id;
        List<Memo> memolist = new ArrayList<Memo>();
        Cursor c = null;
        try{
            c = db.rawQuery(sql,null);
            while (c.moveToNext()) {
                if(c.getInt((c.getColumnIndex("memo_done")))==0) continue;
                Memo m = new Memo();
                m.setMemo_title(c.getString((c.getColumnIndex("memo_title"))));
                m.setMemo_dtime(c.getString((c.getColumnIndex("memo_dtime"))));
                m.setMemo_id(c.getInt((c.getColumnIndex("memo_id"))));
                memolist.add(m);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (c!= null) {
                c.close();
            }
        }
        return memolist;
    }

    //返回主界面的未完成列表(memo中任务id任务标题到期时间有效)
    public List<Memo> returnmemo2(int user_id){
        String sql= "select * from "+TABLE2_NAME+" where user_id = "+user_id;
        List<Memo> memolist = new ArrayList<Memo>();
        Cursor c = null;
        try{
            c = db.rawQuery(sql,null);
            while (c.moveToNext()) {
                if(c.getInt((c.getColumnIndex("memo_done")))==1) continue;
                Memo m = new Memo();
                m.setMemo_title(c.getString((c.getColumnIndex("memo_title"))));
                m.setMemo_dtime(c.getString((c.getColumnIndex("memo_dtime"))));
                m.setMemo_id(c.getInt((c.getColumnIndex("memo_id"))));
                memolist.add(m);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (c!= null) {
                c.close();
            }
        }
        db.close();
        return memolist;
    }

    /*获得memo的主界面列表按照优先级和memo_dtime升序查询*/
    public Memo[] returMemo_list(int user_id) {
        int a =0;
        String sql="select memo_title,memo_dtime from memo where user_id ="+user_id+" and order by mem0_priority DESC,memo_dtime ASC";
        Cursor c = null;
        c = db.rawQuery(sql,null);
        if(c.getCount()>10)
            a=10;
        else
            a=c.getCount();
        Memo[] memos = new Memo[a];
        if(c.moveToFirst()) {
            for (int i = 0; i < a; i++) {
                c.move(i);
                memos[i].setMemo_title(c.getString(0));
                memos[i].setMemo_dtime(c.getString(1));

            }
        }
        c.close();
        return memos;

    }
    //返回一个备忘的详细内容（除图片音频）
    public Memo returnamemo(int memo_id) {
        String sql= "select * from "+TABLE2_NAME+" where memo_id = "+memo_id;
        Cursor c=null;
        Memo m=new Memo();
        try{
            c = db.rawQuery(sql,null);
            while (c.moveToNext()) {
                m.setMemo_title(c.getString((c.getColumnIndex("memo_title"))));
                m.setMemo_advanced(c.getInt((c.getColumnIndex("memo_asvanced"))));
                m.setMemo_content(c.getString((c.getColumnIndex("memo_content"))));
                m.setMemo_ctime(c.getString((c.getColumnIndex("memo_ctime"))));
                m.setMemo_dtime(c.getString((c.getColumnIndex("memo_dtime"))));
                m.setMemo_done(c.getInt((c.getColumnIndex("memo_done"))));
                m.setMemo_id(c.getInt((c.getColumnIndex("memo_id"))));
                m.setMemo_paper(c.getInt((c.getColumnIndex("memo_paper"))));
                m.setMemo_periodicity(c.getInt((c.getColumnIndex("memo_periodicity"))));
                m.setMemo_remind(c.getInt((c.getColumnIndex("memo_remind"))));
                m.setMemo_priority(c.getInt((c.getColumnIndex("memo_priority"))));
                m.setUser_id(c.getInt((c.getColumnIndex("user_id"))));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (c!= null) {
                c.close();
            }
        }
        db.close();
        return m;
    }


    //返回一张或多张图片路径
    public List<String> returnpicture(int memo_id){
        String sql= "select pic_filename from "+TABLE1_NAME+" where memo_id = "+memo_id;
        List<String> piclist = new ArrayList<String>();
        String s=null;
        Cursor c=null;
        try{
            c = db.rawQuery(sql,null);
            while (c.moveToNext()) {
                s=c.getString((c.getColumnIndex("pic_filename")));
                piclist.add(s);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (c!= null) {
                c.close();
            }
        }
        db.close();
        return piclist;
    }

    //返回一张或多张音频路径
    public List<String> returnaudio(int memo_id){
        String sql= "select audio_filename from "+TABLE2_NAME+" where memo_id = "+memo_id;
        List<String> audiolist = new ArrayList<String>();
        String s=null;
        Cursor c=null;
        try{
            c = db.rawQuery(sql,null);
            while (c.moveToNext()) {
                s=c.getString((c.getColumnIndex("audio_filename")));
                audiolist.add(s);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (c!= null) {
                c.close();
            }
        }
        db.close();
        return audiolist;
    }
    /*关闭数据库*/
    public void closeDB(){
        db.close();
    }
}
