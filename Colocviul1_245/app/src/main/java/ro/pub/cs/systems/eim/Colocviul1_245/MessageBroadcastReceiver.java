package ro.pub.cs.systems.eim.Colocviul1_245;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MessageBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Constants.actionType.equals(action))
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.ServiceSUM));
    }
}
