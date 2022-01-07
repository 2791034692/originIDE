package cn.original.ide.launch.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import oms.ability.视窗能力;
import oms.content.意图;

public class LauncherUILayoutHelper extends 视窗能力 {
    private Thread thread;

    @Override
    protected void 当视窗载入时(意图 intent) {
        super.当视窗载入时(intent);
        thread = new Thread();
    }

    @Override
    protected void 当视窗不可见时() {
        super.当视窗不可见时();
    }

    @Override
    protected void 当视窗销毁时() {
        super.当视窗销毁时();
        thread.stop();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    class Thread extends oms.thread.Thread {
        private boolean state = false;
        private boolean isRun = true;
        private Runnable runnable;

        @Override
        public void run() {
            while (isRun) {
                if (state) {
                    state = false;
                    synchronized (runnable) {
                        runnable.run();
                        runnable = null;
                    }
                    System.gc();
                } else {
                    continue;
                }
                sleep(1000);
            }
        }

        synchronized void setRunnable(Runnable runnable) {
            this.runnable = runnable;
            this.state = true;
        }

        @Override
        public boolean stop() {
            isRun = false;
            return super.stop();
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }


}
