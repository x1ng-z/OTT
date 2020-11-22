import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface OTT extends Library {


    //加载libhello.so链接库
    OTT INSTANTCE = (OTT) Native.loadLibrary("PACKDLL2", OTT.class);


    Pointer CONNECT(String host, String servename);


    void ADDITEMS(Pointer client, String itemname[], int itemlength,Pointer buf);

    boolean ADDITEM(Pointer client, String itemname);


    boolean REMOVEITEMS(Pointer client, String itemname[], int itemlength) ;


    void READNUM(Pointer client, String itemname[], int itemlength,Pointer buf) ;


    void READALLREGISTERPOINTNUMS(Pointer client,Pointer buf);


    boolean WRITENUM(Pointer client, String itemname[], float itemvalue[], int itemlength);


    void DISCONNECT(Pointer client) ;


}
