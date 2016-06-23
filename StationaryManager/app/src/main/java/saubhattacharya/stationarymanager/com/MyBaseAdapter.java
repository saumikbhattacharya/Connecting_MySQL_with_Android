package saubhattacharya.stationarymanager.com;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyBaseAdapter extends BaseAdapter {

    public Context ba_context;
    public ArrayList<ListRowItem> listitem = new ArrayList<>();
    public LayoutInflater inflater;
    ListRowItem currentlistitem;
    String status;

    public MyBaseAdapter(Context ma_context, ArrayList<ListRowItem> ma_listitem) {
        super();
        this.ba_context = ma_context;
        this.listitem = ma_listitem;

        inflater = (LayoutInflater) ba_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.listitem.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listitem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if (convertView == null)
            vi = inflater.inflate(R.layout.single_listitem, parent, false);

        TextView item_name = (TextView) vi.findViewById(R.id.item_name);
        TextView item_status = (TextView) vi.findViewById(R.id.item_status);
        TextView item_qty = (TextView) vi.findViewById(R.id.item_qty);
        TextView item_id = (TextView) vi.findViewById(R.id.item_id);

        currentlistitem = listitem.get(position);

        String str_item_name = currentlistitem.getItemName();
        String str_item_id = currentlistitem.getItemId();
        String str_item_qty = currentlistitem.getItemQty();

        int item_quantity = Integer.parseInt(str_item_qty);
        if(item_quantity > 0)
        {
            status = "In Stock";
        }
        else
        {
            status = "Out of Stock";
        }

        item_name.setText(str_item_name);
        item_qty.setText(str_item_qty);
        item_id.setText(str_item_id);
        item_status.setText(status);
        if((item_quantity > 0)&&(item_quantity <=8))
        {
            item_status.setTextColor(Color.YELLOW);
            item_qty.setTextColor(Color.YELLOW);
        }
        else if(item_quantity > 8)
        {
            item_status.setTextColor(Color.GREEN);
            item_qty.setTextColor(Color.GREEN);
        }
        else
        {
            item_status.setTextColor(Color.RED);
            item_qty.setTextColor(Color.RED);
        }

        return vi;
    }
}


