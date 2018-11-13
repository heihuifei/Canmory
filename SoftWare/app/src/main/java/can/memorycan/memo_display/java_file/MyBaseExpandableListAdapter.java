package jay.com.expandablelistviewdemo;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jay on 2015/9/25 0025.
 */
public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter {

    private ArrayList<Group> gData;
    private ArrayList<ArrayList<Item>> iData;
    private Context mContext;

    public MyBaseExpandableListAdapter(ArrayList<Group> gData,ArrayList<ArrayList<Item>> iData, Context mContext) {
        this.gData = gData;
        this.iData = iData;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return gData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return iData.get(groupPosition).size();
    }

    @Override
    public Group getGroup(int groupPosition) {
        return gData.get(groupPosition);
    }

    @Override
    public Item getChild(int groupPosition, int childPosition) {
        return iData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public void changeData(ArrayList<ArrayList<Item>> item)
    {
        iData = item;
        notifyDataSetChanged();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ViewHolderGroup groupHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_exlist_group, parent, false);
            groupHolder = new ViewHolderGroup();
            groupHolder.tv_group_name = (TextView) convertView.findViewById(R.id.tv_group_name);
            convertView.setTag(groupHolder);
        }else{
            groupHolder = (ViewHolderGroup) convertView.getTag();
        }
        groupHolder.tv_group_name.setText(gData.get(groupPosition).get_Group_name());
        return convertView;
    }

    //取得显示给定分组给定子位置的数据用的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderItem itemHolder;
        if(convertView == null){
            Log.e("groupposition",String.valueOf(convertView));
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_exlist_item, parent, false);
            itemHolder = new ViewHolderItem();
            itemHolder.ckb_name = (CheckBox) convertView.findViewById(R.id.ckb_name);
            itemHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            if(groupPosition == 2 && gData.get(groupPosition).get_memo_done()==0 && iData.get(groupPosition).get(childPosition).get_memo_id() > 100) {
                Log.e("groupposition",String.valueOf(groupPosition));
                itemHolder.ckb_name.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
            convertView.setTag(itemHolder);
//            notifyDataSetChanged();
        }else{
            itemHolder = (ViewHolderItem) convertView.getTag();
        }
        itemHolder.ckb_name.setText(iData.get(groupPosition).get(childPosition).get_memo_title());
        itemHolder.tv_name.setText(iData.get(groupPosition).get(childPosition).get_memo_dtime());
        return convertView;
    }

    //设置子列表是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

//    public void add(Item item)
//    {
//        if(iData == null)
//        {
//            iData=new ArrayList<>();
//        }
//        iData.add(item);
//    }

    private static class ViewHolderGroup{
        private TextView tv_group_name;
    }

    private static class ViewHolderItem{
        private TextView tv_name;
        private CheckBox ckb_name;
    }
}
