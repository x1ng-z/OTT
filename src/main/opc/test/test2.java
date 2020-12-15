package opc.test;

import opc.serve.OPCServe;
import opc.serve.OTT;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/11/30 16:01
 */
public class test2 {
    public static OPCServe opcServe;
    public static void main(String[] args) {
         opcServe=new OPCServe("127.0.0.1","KEPware.KEPServerEx.V4");
        opcServe.connect();
        if(opcServe.isConectstatus()){
            System.out.println("Connect success!");
        }
        System.out.println("is add ="+opcServe.registerItem("Channel_3._System._EnableDiagnostics"));


        ExecutorService executorService =Executors.newFixedThreadPool(2);




//            System.out.println("is write ="+opcServe.writeSpecialItem("Channel_3._System._EnableDiagnostics",1));;
//
//            System.out.println("is read ="+opcServe.readAllItem());
//
//            int re=OTT.INSTANTCE.WRITENUM(opcServe.getOpcclient(),new String[]{"Channel_3._System._EnableDiagnostics"},new float[]{1f},1);
//            System.out.println("in is write ="+re);



        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       Thread thread1=new  Thread(new Runnable() {
           @Override
           public void run() {
//               OPCServe opcServe=new OPCServe("127.0.0.1","KEPware.KEPServerEx.V4");
//               opcServe.connect();
//               System.out.println("is add ="+opcServe.registerItem("Channel_3._System._EnableDiagnostics"));

               opcServe.readAllItem();
                   System.out.println("is read =");
//                    System.out.println("in thread is write ="+re);

           }
       });
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("out");

    }
}
