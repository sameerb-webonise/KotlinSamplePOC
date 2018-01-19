package com.example.webonise.kotlinsamplepoc

import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.example.webonise.kotlinsamplepoc.ui.BaseActivity
import java.util.*

class NotificationUtil {
    private val GROUP_KEY_BUNDLED = "group_key_bundled"

    private val NOTIFICATION_BUNDLED_BASE_ID = 1000

    //simple way to keep track of the number of bundled notifications
    //Simple way to track text for notifications that have already been issued
    private val issuedMessages = LinkedList<CharSequence>()

    companion object {
        private var mNotificationUtil: NotificationUtil? = null

        fun getInstance(): NotificationUtil? {
            if(mNotificationUtil == null) {
                mNotificationUtil = NotificationUtil()
            }
            return mNotificationUtil
        }
    }

    fun bundledNotification(title: String, message: String) {
        val context = KotlinSampleApplication.getAppContext()
        val notificationManager = NotificationManagerCompat.from(context)

        /*String message = "This is message # " + ++numberOfBundled
                + ". This text is generally too long to fit on a single line in a notification";*/
        issuedMessages.add(title)

        //Build and issue the group summary. Use inbox style so that all messages are displayed
        val summaryBuilder = NotificationCompat.Builder(context)
                .setContentTitle(context.getResources().getString(R.string
                        .app_name))
                .setContentText(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setGroupSummary(true)
                .setGroup(GROUP_KEY_BUNDLED)

        val inboxStyle = NotificationCompat.InboxStyle()
        inboxStyle.setBigContentTitle(context.getResources().getString(R.string
                .app_name))
        for (cs in issuedMessages) {
            inboxStyle.addLine(cs)
        }
        summaryBuilder.setStyle(inboxStyle)

        notificationManager.notify(NOTIFICATION_BUNDLED_BASE_ID, summaryBuilder.build())


        //issue the Bundled notification. Since there is a summary notification, this will only display
        //on systems with Nougat or later
        val builder = NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(title))
                .setGroup(GROUP_KEY_BUNDLED)

        //Add an action that simply starts the main activity. This is not very useful it is mainly for demonstration
        val intent = Intent(context, BaseActivity::class.java)
        //Each notification needs a unique request code, so that each pending intent is unique. It does not matter
        //in this simple case, but is important if we need to take action on a specific notification, such as
        //deleting a message
        val requestCode = NOTIFICATION_BUNDLED_BASE_ID
        val pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val action = NotificationCompat.Action.Builder(android.R.drawable.ic_input_get,
                "OK", pendingIntent)
                .build()
        builder.addAction(action)

        notificationManager.notify(NOTIFICATION_BUNDLED_BASE_ID, builder.build())
    }
}