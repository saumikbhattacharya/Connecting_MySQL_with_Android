package saubhattacharya.stationarymanager.com;

import java.io.Serializable;

public class ListRowItem implements Serializable{

    String Item_Id,Item_Name,Item_Qty;

    public void setItemId(String ItemId)
    {
        Item_Id = ItemId;
    }

    public void setItemName(String ItemName)
    {
        Item_Name = ItemName;
    }

    public void setItemQty(String ItemQuantity)
    {
        Item_Qty = ItemQuantity;
    }

    public String getItemId()
    {
        return Item_Id;
    }

    public String getItemName()
    {
        return Item_Name;
    }

    public String getItemQty()
    {
        return Item_Qty;
    }
}
