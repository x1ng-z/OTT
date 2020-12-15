package opc.serve;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/11/24 16:37
 */
public interface Serve {
    void init();
    void connect();
    void disconnect();
    boolean registerItem(String itemname);
    boolean removeItem(String itemname);
}
