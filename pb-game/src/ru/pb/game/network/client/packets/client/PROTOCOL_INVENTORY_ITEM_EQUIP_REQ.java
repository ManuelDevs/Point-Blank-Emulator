package ru.pb.game.network.client.packets.client;

import ru.pb.game.network.client.packets.ClientPacket;
import ru.pb.game.network.client.packets.server.PROTOCOL_INVENTORY_ITEM_EQUIP_ACK;
import ru.pb.global.enums.ItemState;
import ru.pb.global.enums.item.ItemConsumeType;
import ru.pb.global.enums.item.ItemType;
import ru.pb.global.models.PlayerItem;
import ru.pb.global.utils.DateTimeUtil;

public class PROTOCOL_INVENTORY_ITEM_EQUIP_REQ extends ClientPacket
{

	private long id;
	
    public PROTOCOL_INVENTORY_ITEM_EQUIP_REQ(int opcode)
    {
        super(opcode);
    }

    protected void readImpl()
    {
    	id = readQ();
    }

    protected void runImpl()
    {
    	int error = 1;
    	PlayerItem item = getConnection().getPlayer().getEqipment().getItemById(id);
    	
    	if(item != null)
    	{
    		if((item.getItem().getItemType() == ItemType.WEAPON || item.getItem().getItemType() == ItemType.CHARACTER) 
    				&& item.getItem().getConsumeType() == ItemConsumeType.TEMPORARY 
    				&& item.getConsumeLost() == 1)
    		{
    			item.setCount(DateTimeUtil.getDateTime(item.getCount() / 86400));
    			item.setConsumeLost(2);
    			item.setFlag(ItemState.UPDATE);
    		}
    		else error = 0x80000000;
    	}
    	else error = 0x80000000;
    	
    	sendPacket(new PROTOCOL_INVENTORY_ITEM_EQUIP_ACK(error, item));
    }
}