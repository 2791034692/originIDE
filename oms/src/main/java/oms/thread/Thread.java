package oms.thread;

import android.util.SparseArray;
import android.view.View;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import oms.ability.ModAbility;

public abstract class Thread extends ModAbility implements Runnable {
     private static ExecutorService executorService = Executors.newFixedThreadPool(12);
     private static SparseArray<Thread> threadSparseArray = new SparseArray<>();
     private int _ThreadId = 0;
     private Future future;
     public boolean stop(){
        return future.cancel(true);
     }
     public boolean isDone(){
          return  future.isDone();
     }
     public boolean isStop(){
         return future.isCancelled();
     }
     protected void sleep(int t){
         try {
             java.lang.Thread.sleep(t);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }
     public Thread(){
        if(executorService == null){
            executorService = Executors.newFixedThreadPool(12);
            threadSparseArray = new SparseArray<>();
        }
        _ThreadId = View.generateViewId();
        threadSparseArray.put(_ThreadId,this);
        this.future = executorService.submit(this::run);
     }
     @Override
     public abstract void run();

    @Override
     protected void finalize() throws Throwable {
        super.finalize();
        this.stop();
        threadSparseArray.delete(_ThreadId);
        if(threadSparseArray.size() == 0){
            threadSparseArray = null;
            executorService = null;
        }
        System.gc();
     }

}
