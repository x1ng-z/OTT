package opc.serve;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface OTT extends Library {
    //加载libhello.so链接库
     OTT INSTANTCE = (OTT) Native.load("PACKDLL2", OTT.class);
    Pointer CONNECT(String host, String servename);
    int IsConnected(Pointer client);
    void ADDITEMS(Pointer client, String itemname[], int itemlength,Pointer buf);
    int ADDITEM(Pointer client, String itemname);
    int REMOVEITEMS(Pointer client, String itemname[], int itemlength) ;
    int READNUM(Pointer client, String itemname[], int itemlength,Pointer buf) ;
    int READNUMS(Pointer client, String itemname[], int itemlength,Pointer buf) ;
    void READTEST() ;
    int READALLREGISTERPOINTNUMS(Pointer client,Pointer buf);
    int WRITENUM(Pointer client, String itemname[], float itemvalue[], int itemlength);
    int DISCONNECT(Pointer client) ;
}
