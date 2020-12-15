package opc.serve;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import opc.item.ItemManger;
import opc.item.ItemUnit;



/**
 * @author zzx
 * @version 1.0
 * @date 2020/11/24 16:36
 */
public class OPCServe implements Serve {

    private String ip;
    private String opcservename;
    private Pointer opcclient;
    private opc.item.ItemManger itemManger;
    private boolean conectstatus=false;

    public OPCServe(String ip, String opcservename) {
        this.ip = ip;
        this.opcservename = opcservename;
        opcclient=null;
        init();
    }

    @Override
    public void init() {
        itemManger=new ItemManger();
    }

    @Override
    public void connect() {
        itemManger.clear();
        opcclient= OTT.INSTANTCE.CONNECT(ip,opcservename);
        if(opcclient==null){
            conectstatus=false;
        }else {
            conectstatus=true;
        }
    }

    @Override
    public void disconnect() {
        if(opcclient!=null){
            itemManger.clear();
            OTT.INSTANTCE.DISCONNECT(opcclient);
            opcclient=null;
            conectstatus=false;
        }
    }

    @Override
    public synchronized boolean registerItem(String itemname) {
        if (!itemManger.iscontainstag(itemname)) {
            if (OTT.INSTANTCE.ADDITEM(opcclient, itemname) == 1) {
                ItemUnit newitem = new ItemUnit();
                newitem.setItem(itemname);
                newitem.addrefrencecount();
                return itemManger.addItemUnit(itemname, newitem);
            } else {
                return false;
            }
        }else {
            itemManger.getItemUnit(itemname).addrefrencecount();
        }
        return false;
    }

    @Override
    public synchronized boolean removeItem(String itemname) {
        if (!itemManger.iscontainstag(itemname)) {

            ItemUnit itemUnit=itemManger.getItemUnit(itemname);
            itemUnit.minsrefrencecount();

            if(itemUnit.isnorefrence()){
                itemManger.removeItemUnit(itemname);
                String[] waitremovetag = new String[]{itemname};
                if (OTT.INSTANTCE.REMOVEITEMS(opcclient, waitremovetag, 1) == 1) {
                    //itemManger.removeItemUnit(itemname);
                    return true;
                } else {
                    return false;
                }

            }

        }
        return false;

    }


    public synchronized boolean readAllItem(){
        Memory valueBuf = new Memory(itemManger.getTagOrderList().size() * 4);
        if(OTT.INSTANTCE.READALLREGISTERPOINTNUMS(opcclient,valueBuf)==1){
            float[] values = valueBuf.getFloatArray(0, itemManger.getTagOrderList().size());
            int index=0;
            for(ItemUnit unit:itemManger.getTagOrderList()){
                unit.setValue(values[index]);
                ++index;
            }
            long peer = Pointer.nativeValue(valueBuf);
            Native.free(peer);//手动释放内存
            Pointer.nativeValue(valueBuf, 0);////避免Memory对象被GC时重复执行Nativ.free()方法
            return true;
        }else {
            conectstatus=false;
            return false;
        }
    }

    public synchronized boolean writeSpecialItems(String[] itemName,float[] values){

        if(itemName.length!=values.length){
            return false;
        }

        for(String subitem:itemName){
            if(!itemManger.iscontainstag(subitem)){
                return false;
            }
        }

        if(OTT.INSTANTCE.WRITENUM(opcclient,itemName,values,values.length)==1){
            return true;
        }else {
            conectstatus=false;
            return false;
        }


    }


    public synchronized boolean writeSpecialItem(String itemName,float value){
        String[] writeitem=new String[]{itemName};
        float[] writevalue=new float[]{value};

        if(!itemManger.iscontainstag(itemName)){
            return false;
        }

        if(OTT.INSTANTCE.WRITENUM(opcclient,writeitem,writevalue,1)==1){
            return true;
        }else {
            conectstatus=false;
            return false;
        }
    }



    public ItemManger getItemManger() {
        return itemManger;
    }

    public boolean isConectstatus() {
        return conectstatus;
    }

    public Pointer getOpcclient() {
        return opcclient;
    }

    public void setOpcclient(Pointer opcclient) {
        this.opcclient = opcclient;
    }
}
