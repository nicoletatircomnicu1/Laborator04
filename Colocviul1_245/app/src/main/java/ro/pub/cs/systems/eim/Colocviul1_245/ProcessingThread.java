package ro.pub.cs.systems.eim.Colocviul1_245;

import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;


public class ProcessingThread extends Thread {

    private Context context = null;
    private int sum;
    private boolean isRunning = true;

    public ProcessingThread(Context context, int sum) {
        this.context = context;
        sum = sum;
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionType);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                new Date(System.currentTimeMillis()) + " " + sum);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
