package jarvis.remindme.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver { //*** BroadcastReceiver - receives and handles broadcast intents.

    @Override
    public void onReceive(Context context, Intent arg1) { //*** onReceive method is called when the BroadcastReceiver
                                                          //           is receiving an Intent broadcast.
                                                          //*** Context: The Context in which the receiver is running.
                                                          //*** Intent: The Intent being received.

        Uri alarm_tone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);  //*** Using getDefaultUri to get default tone .
                                                                                            //*** RingtoneManager provides access to ringtones
                                                                                            //          and other types of sounds.
        final Ringtone ringtone = RingtoneManager.getRingtone(context, alarm_tone); //*** getRingtone () returns a Ringtone for a given sound URI.
                                                                                    //*** Context: A context used to query.
                                                                                    //*** Uri: The Uri of a sound or ringtone.
        ringtone.play(); //*** Plays the ringtone.

        Toast.makeText(context, "Alarm Received.", Toast.LENGTH_SHORT).show();

        Intent intent1 = new Intent(context, Ringtones.class);
        context.startService(intent1); //*** A service is started when an application component (such as an activity) calls startService().

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE); //*** Class that operates the vibrator on the device.
        vibrator.vibrate(2000); //*** vibrate () method return long milliseconds that is vibrating time.
                                //*** VIBRATOR_SERVICE - Use with getSystemService(Class) to retrieve
                                //              a Vibrator for interacting with the vibration hardware.
                                //*** To obtain an instance of the system vibrator, call getSystemService(Class)
                                //              with VIBRATOR_SERVICE as the argument.
    }
}