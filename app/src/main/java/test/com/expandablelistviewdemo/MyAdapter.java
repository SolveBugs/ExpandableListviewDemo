package test.com.expandablelistviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhen on 2016/9/6.
 */
public class MyAdapter extends BaseExpandableListAdapter implements CustomExpandListview.HeaderAdapter {

    private ArrayList<String> parent;
    private Map<String, ArrayList<String>> datas = new HashMap<>();
    private Context context;
    private CustomExpandListview listview;

    public MyAdapter(Context context, ArrayList<String> parent, Map<String, ArrayList<String>> datas, CustomExpandListview listview) {
        this.context = context;
        this.parent = parent;
        this.datas = datas;
        this.listview = listview;
    }

    @Override
    public int getGroupCount() {
        int temp = 0;
        if (parent != null) {
            temp = parent.size();
        }
        return temp;
    }

    @Override
    public int getChildrenCount(int i) {
        int size = 0;
        if (parent.size() > 0) {
            String key = parent.get(i);
            size = datas.get(key).size();
        }
        return size;
    }

    @Override
    public Object getGroup(int i) {
        return parent.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        String key = parent.get(i);
        return (datas.get(key).get(i1));
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.parent_layout, null);
        }
        TextView tv = (TextView) view
                .findViewById(R.id.tv_parent);
        tv.setText(parent.get(i));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        PlaceHolder holder;
        if (view == null) {
            holder = new PlaceHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_layout, null);
            holder.tv_title = (TextView) view.findViewById(R.id.tv_item);
            view.setTag(holder);
        } else {
            holder = (PlaceHolder) view.getTag();
        }
        String key = parent.get(i);
        String s = datas.get(key).get(i1);
        holder.tv_title.setText(s);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    /**
     * 根据当前的groupPosition和childPosition判断指示布局是哪种状态（隐藏、可见、正在向上推）
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public int getHeaderState(int groupPosition, int childPosition) {
        final int childCount = getChildrenCount(groupPosition);
        if (childPosition == childCount - 1) {
            return PINNED_HEADER_PUSHED_UP;
        } else if (childPosition == -1
                && !listview.isGroupExpanded(groupPosition)) {
            return PINNED_HEADER_GONE;
        } else {
            return PINNED_HEADER_VISIBLE;
        }
    }

    /**
     * 给指示布局设置内容
     *
     * @param header
     * @param groupPosition
     * @param childPosition
     * @param alpha
     */
    @Override
    public void configureHeader(View header, int groupPosition, int childPosition, int alpha) {
        if (groupPosition > -1) {
            ((TextView) header.findViewById(R.id.tv_indictor))
                    .setText(parent.get(groupPosition));
        }
    }


    /**
     * 子布局
     */
    private class PlaceHolder {
        private TextView tv_title;
    }
}
