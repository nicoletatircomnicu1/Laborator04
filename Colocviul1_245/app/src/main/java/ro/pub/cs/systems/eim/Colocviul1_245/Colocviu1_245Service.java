package ro.pub.cs.systems.eim.Colocviul1_245;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Colocviu1_245Service extends Service {
    private ProcessingThread processingThread = null;
    public Colocviu1_245Service() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int sum = Integer.valueOf(intent.getIntExtra(Constants.ServiceSUM, -1));
        processingThread = new ProcessingThread(this, sum);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}
