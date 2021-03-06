package opc.test;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import opc.serve.OTT;


import java.util.concurrent.TimeUnit;

public class Test {


    public static void main(String[] args) {
        Pointer opcclient = OTT.INSTANTCE.CONNECT("128.128.1.130", "SUPCON.JXServer.1");

        if(opcclient==null){
            System.out.println("connect failed");
        }

//        Pointer p = new Memory(1024 * 1024);
//        long peer = Pointer.nativeValue(p);
//        Native..free(peer);//手动释放内存
//        Pointer.nativeValue(p, 0);//避免Memory对象被GC时重复执行Nativ.free()方法

        if(OTT.INSTANTCE. IsConnected(opcclient)==1){
            System.out.println("Connect Success");
        }
        String[] a = new String[]{"A_SLM2_PV5"};

//
//        "dcs.User.ff2",
//                "dcs.User.ff3",
//                "dcs.User.ff4",
//                "dcs.User.pv1",
//                "dcs.User.pv2",
//                "dcs.User.pv3",
//                "dcs.User.mv1",
//                "dcs.User.mvfb1",
//                "dcs.Ramp.Ramp_Float",
//                "dcs.Sine.Sine1",
//                "dcs.Sine.Sine2",
//                "dcs.Sine.Sine3",
//                "dcs.Sine.Sine4",
//                "dcs.Sine.Sine5",
//                "dcs.User.ffdown1",
//                "dcs.User.ffdown2",
//                "dcs.User.ffdown3",
//                "dcs.User.ffdown4",
//                "dcs.User.ffenable1",
//                "dcs.User.ffenable2",
//                "dcs.User.fffilter1",
//                "dcs.User.fffilter2",
//                "dcs.User.fffilter3"


        Memory addresultbuf=new Memory(a.length);
        addresultbuf.clear();
        OTT.INSTANTCE.ADDITEMS(opcclient, a, a.length,addresultbuf);

        byte[] tmpresut=addresultbuf.getByteArray(0,a.length);

        for(int i=0;i<tmpresut.length;i++){
            System.out.println(String.format("opc.item=%s,result=%d",a[i],tmpresut[i]));
        }



        //JSONObject json_additemresult = JSONObject.parseObject(addresult);
        //System.out.println("\"dcs.User.ff1\"=" + json_additemresult.getInteger("dcs.User.ff1"));
        ;

        float[] avalue = new float[a.length];
        boolean test = true;

       // opc.serve.OTT.INSTANTCE.READTEST();

//        Memory readovobuf=new Memory(a.length*4);
//        readovobuf.clear();
//        opc.serve.OTT.INSTANTCE.READALLREGISTERPOINTNUMS(opcclient,  readovobuf);
//        float[] readovoresult = readovobuf.getFloatArray(0, a.length);
//
//        for (int i = 0; i < tmpresut.length; i++) {
//            System.out.println(String.format("opc.item=%s,result=%f", a[i], readovoresult[i]));
//        }

//        return;

        int count=150;
        while ((count--)>0) {

            System.out.println("##############"+count+"##########");

            try {
                long start = System.currentTimeMillis();

                for (int i = 0; i < avalue.length; ++i) {
                    avalue[i] = (avalue[i] + 0.02f) / 1.02f;
                }

                long writestart = System.currentTimeMillis();
                System.out.println(OTT.INSTANTCE.WRITENUM(opcclient,a, avalue, a.length));
                if (OTT.INSTANTCE.WRITENUM(opcclient,a, avalue, a.length)==0) {
                    System.out.println("write failed");
//                    break;
                }
                System.out.println("write cost time =" + (System.currentTimeMillis() - writestart));


                long readallstart = System.currentTimeMillis();
                Memory readallbuf=new Memory(a.length*4);

                if(OTT.INSTANTCE.READNUMS(opcclient,a,a.length,readallbuf)==0){
                    System.out.println("read error out");
                    System.out.println("READALLREGISTERPOINTNUMS OUT******************");
                    break;
                }
                float[] readnum=readallbuf.getFloatArray(0,a.length);
                for(int i=0;i<a.length;i++){
                    System.out.println(readnum[i]);
                }
                TimeUnit.MILLISECONDS.sleep(2000);
                System.out.println("##############"+count+"##########");

//                System.out.println("is Connect "+opc.serve.OTT.INSTANTCE.IsConnected(opcclient));

            } catch (InterruptedException e) {
                e.printStackTrace();
                OTT.INSTANTCE.DISCONNECT(opcclient);
            }
        }

        OTT.INSTANTCE.DISCONNECT(opcclient);
    }
}
