package opc.item;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/7/16 17:29
 */

public class ItemManger {
    private Map<String, opc.item.ItemUnit> opcitemunitPool = new ConcurrentHashMap<String, opc.item.ItemUnit>();//key=标签，value=opc.item.ItemUnit

    private List<ItemUnit> tagOrderList=new CopyOnWriteArrayList<ItemUnit>();

    public ItemManger() {
    }


    public void clear(){
       opcitemunitPool.clear();
       tagOrderList.clear();
   }

    public boolean iscontainstag(String tagname){
        return opcitemunitPool.containsKey(tagname);
    }

    public boolean addItemUnit(String tagname,ItemUnit itemUnit){
        if((itemUnit.getItem()!=null)&&(opcitemunitPool.get(tagname)==null)){
            tagOrderList.add(itemUnit);
            opcitemunitPool.put(tagname,itemUnit);
            return true;
        }else {
            return false;
        }
    }

    public ItemUnit removeItemUnit(String tagname){
        ItemUnit itemUnit=opcitemunitPool.remove(tagname);
        tagOrderList.remove(itemUnit);
        return itemUnit;
    }

    public ItemUnit getItemUnit(String tagname){
        return opcitemunitPool.get(tagname);
    }

    public List<ItemUnit> getTagOrderList() {
        return tagOrderList;
    }

    public Map<String, ItemUnit> getOpcitemunitPool() {
        return opcitemunitPool;
    }
}
