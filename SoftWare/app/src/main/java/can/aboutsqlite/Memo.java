package can.aboutsqlite;

public class Memo {


    private int memo_id;
    private String memo_title;
    private String memo_ctime;
    private String memo_dtime;
    private int memo_priority;
    private int memo_periodicity;
    private int memo_advanced;
    private int memo_remind;
    private int memo_paper;
    private int user_id;
    private int memo_done;
    private String memo_content;

    public Memo() {
    }
    /*全部参数的memo构造*/
    public Memo(int memo_id, String memo_title, String memo_ctime, String memo_dtime,int memo_priority,int memo_periodicity,
                int memo_advanced,int memo_remind,int memo_paper,int user_id,int memo_done,String memo_content) {
        this.memo_id = memo_id;
        this.memo_title= memo_title;
        this.memo_ctime = memo_ctime;
        this.memo_dtime = memo_dtime;
        this.memo_priority = memo_priority;
        this.memo_periodicity = memo_periodicity;
        this.memo_advanced = memo_advanced;
        this.memo_remind = memo_remind;
        this.memo_paper = memo_paper;
        this.user_id = user_id;
        this.memo_done = memo_done;
        this.memo_content = memo_content;
    }

    /*memo_id*/
    public int getMemo_id() {
        return memo_id;
    }

    public void setMemo_id(int memo_id) {
        this.memo_id = memo_id;
    }
    /*memo_title*/
    public String getMemo_title() {
        return memo_title;
    }
    public void setMemo_title(String memo_title) {
        this.memo_title = memo_title;
    }
    /*memo_ctime*/
    public String getMemo_ctime() {
        return memo_ctime;
    }
    public void setMemo_ctime(String memo_ctime) {
        this.memo_ctime = memo_ctime;
    }
    /*memo_dtime*/
    public String getMemo_dtime() {
        return memo_dtime;
    }
    public void setMemo_dtime(String memo_dtime) {
        this.memo_dtime = memo_dtime;
    }
    /*memo_priority*/
    public int getMemo_priority() {
        return memo_priority;
    }
    public void setMemo_priority(int memo_priority) {
        this.memo_priority = memo_priority;
    }
    /*memo_periodicity*/
    public int getMemo_periodicity() {
        return memo_periodicity;
    }
    public void setMemo_periodicity(int memo_periodicity) {
        this.memo_periodicity = memo_periodicity;
    }
    /*memo_advanced*/
    public int getMemo_advanced() {
        return memo_advanced;
    }
    public void setMemo_advanced(int memo_advanced) {
        this.memo_advanced = memo_advanced;
    }
    /*memo_remind*/
    public int getMemo_remind() {
        return memo_remind;
    }
    public void setMemo_remind(int memo_remind) {
        this.memo_remind = memo_remind;
    }
    /*memo_paper*/
    public int getMemo_paper() {
        return memo_paper;
    }
    public void setMemo_paper(int memo_paper) {
        this.memo_paper = memo_paper;
    }
    /*user_id*/
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    /*memo_done*/
    public int getMemo_done() {
        return memo_done;
    }
    public void setMemo_done(int memo_done) {
        this.memo_done = memo_done;
    }
    /*memo_content*/
    public String getMemo_content() {
        return memo_content;
    }
    public void setMemo_content(String memo_content) {
        this.memo_content = memo_content;
    }

}
