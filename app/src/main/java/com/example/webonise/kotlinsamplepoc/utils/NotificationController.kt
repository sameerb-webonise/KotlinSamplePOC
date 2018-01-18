package com.example.webonise.kotlinsamplepoc.utils

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import com.example.webonise.kotlinsamplepoc.KotlinSampleApplication
import com.example.webonise.kotlinsamplepoc.R
import com.example.webonise.kotlinsamplepoc.ui.BaseActivity

/**
 * Created by webonise on 1/18/18.
 */
class NotificationController {
    private val GROUP_KEY_BUNDLED = "Kotlin POC"
    val NOTIFICATION = 100
    private var mNotificationData: NotificationData? = null

    internal inner class NotificationData {
        var content: String? = null
        var extras: Intent? = null
    }

    companion object {
        private var mNotificationController: NotificationController? = null

        fun getInstance(): NotificationController? {
            if(mNotificationController == null) {
                mNotificationController = NotificationController()
            }
            return mNotificationController
        }
    }

    constructor() {

        /*if (mNotificationThread == null) {
            mNotificationThread = new NotificationThread();
            mNotificationThread.start();
        }*/
    }

    @Synchronized
    fun showNotification(tag: String, content: String) {
        if (mNotificationData == null) {
            mNotificationData = NotificationData()
        }

        mNotificationData!!.content = content
        optimizeNotification(tag, false)
    }

    private fun optimizeNotification(tag: String, mIsVibrateOn: Boolean) {
        try {
            val mContext = KotlinSampleApplication.getAppContext()
            val builder = NotificationCompat.Builder(mContext)

            val title = mContext.getResources()?.getString(R.string
                    .app_name)
            builder.setContentTitle(title)
            builder.setContentText(mNotificationData?.content)
            builder.setTicker(mNotificationData?.content)
            builder.setSmallIcon(R.mipmap.ic_launcher)
            builder.setGroupSummary(true)
            builder.setGroup(GROUP_KEY_BUNDLED)
            builder.color = mContext.getResources()?.getColor(R.color.colorPrimary)!!
            builder.setDefaults(Notification.DEFAULT_LIGHTS)


            //If vibrate is allowed, use default vibrate mode.
            if (mIsVibrateOn) {
                builder.setDefaults(Notification.DEFAULT_VIBRATE)
            }

            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            builder.setSound(uri)
            builder.setAutoCancel(true)


            val resultIntent = Intent(mContext, BaseActivity::class.java)
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)

            builder.setContentText(mNotificationData?.content)
            val inboxStyle = NotificationCompat.InboxStyle()
            builder.setStyle(inboxStyle.setSummaryText(mNotificationData?.content))
            //resultIntent.putExtras(mNotificationData.extras);
            //below line is not significant but if not used then intent extras are dropped from
            // 1st notification and no new extras are added from second
            //resultIntent.setAction( Long.toString( System.currentTimeMillis( ) ) );

            //int type = mNotificationData.extras.getIntExtra(Constants.NOTIFICATION_TYPE_BUNDLE, FIND_ME_NOTIFICATION);
            val stackBuilder = TaskStackBuilder.create(mContext)
            // Adds the back stack
            //            stackBuilder.addParentStack( HomeActivity.class );
            // Adds the Intent to the top of the stack
            stackBuilder.addNextIntent(resultIntent)
            // Gets a PendingIntent containing the entire back stack
            val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent
                    .FLAG_UPDATE_CURRENT)
            builder.setContentIntent(resultPendingIntent)


            /* Notify */
            val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(tag, NOTIFICATION, builder.build())
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}