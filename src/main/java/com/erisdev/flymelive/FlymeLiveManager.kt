package com.erisdev.flymelive

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.RemoteViews
import androidx.annotation.DrawableRes

/**
 * Flyme Live Notification Manager
 * 提供对Flyme系统的实况通知功能的封装
 * 
 * Flyme实况通知允许在状态栏显示可交互的胶囊形式通知，点击后展开为完整内容。
 * 
 * 注意：以下参数基于 https://github.com/Ruyue-Kinsenka/Flyme-Live-Notification-Demo 代码分析得出，魅族官方未提供完整文档说明：
 * - capsuleStatus: 控制胶囊状态的标志（使用值为1）
 * - capsuleType: 控制胶囊类型的标志（使用值为5）
 * - operation: 控制实况通知操作类型的标志（使用值为0）
 * - type: 控制实况通知类型的标志（使用值为2）
 */
@Suppress("unused")
class FlymeLiveManager(private val context: Context) {
    
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    
    /**
     * 显示实况通知构建器
     */
    fun showLiveNotification(builder: LiveNotificationBuilder): Int {
        val notification = builder.build(context, notificationManager)
        notificationManager.notify(builder.notificationId, notification)
        return builder.notificationId
    }
    
    /**
     * 取消指定ID的通知
     */
    fun cancelNotification(notificationId: Int) {
        notificationManager.cancel(notificationId)
    }
    
    /**
     * 取消所有通知
     */
    fun cancelAllNotifications() {
        notificationManager.cancelAll()
    }
    
    /**
     * 实况通知构建器
     */
    class LiveNotificationBuilder(
        private val channelId: String,
        private val channelName: String,
        val notificationId: Int
    ) {
        private var channelDescription: String = ""
        private var importance: Int = NotificationManager.IMPORTANCE_DEFAULT
        
        // 基本通知属性
        private var smallIcon: Int = 0
        private var largeIcon: Int = 0
        private var contentTitle: String = ""
        private var contentText: String = ""
        private var contentIntent: PendingIntent? = null
        
        // 实况通知特有属性
        private var capsuleStatus: Int = 1  // 控制胶囊状态（启用/禁用）
        private var capsuleType: Int = 5    // 控制胶囊类型（显示样式）
        private var capsuleContent: String = ""  // 胶囊内显示的简单文本内容
        private var capsuleIcon: Icon? = null    // 胶囊图标
        private var capsuleBgColor: Int = 0      // 胶囊背景色，0表示使用默认值
        private var capsuleContentColor: Int = 0 // 胶囊文本颜色，0表示使用默认值
        private var capsuleContentView: RemoteViews? = null  // 胶囊自定义视图（如需复杂布局）
        
        // 实况通知操作和类型
        private var operation: Int = 0  // 控制实况通知操作类型，0为显示操作
        private var type: Int = 2        // 控制实况通知类型，2为标准实况通知
        
        // 主要内容视图（展开视图）
        private var contentView: RemoteViews? = null
        
        // 其他额外数据
        private val extrasBundle = Bundle()
        
        fun setChannelDescription(description: String) = apply {
            this.channelDescription = description
        }
        
        fun setImportance(importance: Int) = apply {
            this.importance = importance
        }
        
        fun setSmallIcon(@DrawableRes icon: Int) = apply {
            this.smallIcon = icon
        }
        
        fun setLargeIcon(@DrawableRes icon: Int) = apply {
            this.largeIcon = icon
        }
        
        fun setContentTitle(title: String) = apply {
            this.contentTitle = title
        }
        
        fun setContentText(text: String) = apply {
            this.contentText = text
        }
        
        fun setContentIntent(intent: PendingIntent) = apply {
            this.contentIntent = intent
        }
        
        fun setCapsuleStatus(status: Int) = apply {
            /**
             * 设置胶囊状态
             * @param status 胶囊状态值，1表示启用
             * 
             * 注意：经测试，除1以外的值会使胶囊不可见，其他数值对显示效果无明显差异。
             * 建议保持默认值1。
             */
            this.capsuleStatus = status
        }
        
        fun setCapsuleType(type: Int) = apply {
            /**
             * 设置胶囊类型
             * @param type 胶囊类型值，5表示标准类型
             * 
             * 注意：经测试，不同数值对显示效果无明显差异。
             * 建议保持默认值5。
             */
            this.capsuleType = type
        }
        
        fun setCapsuleContent(content: String) = apply {
            this.capsuleContent = content
        }
        
        fun setCapsuleIcon(icon: Icon) = apply {
            this.capsuleIcon = icon
        }
        
        fun setCapsuleBgColor(color: Int) = apply {
            this.capsuleBgColor = color
        }
        
        fun setCapsuleContentColor(color: Int) = apply {
            this.capsuleContentColor = color
        }
        
        fun setCapsuleContentView(view: RemoteViews) = apply {
            this.capsuleContentView = view
        }
        
        fun setOperation(operation: Int) = apply {
            /**
             * 设置实况通知操作类型
             * @param operation 操作类型值，0表示显示操作
             * 
             * 注意：经测试，不同数值对显示效果无明显差异。
             * 建议保持默认值0。
             */
            this.operation = operation
        }
        
        fun setType(type: Int) = apply {
            /**
             * 设置实况通知类型
             * @param type 通知类型值，2表示标准实况通知
             * 
             * 注意：经测试，不同数值对显示效果无明显差异。
             * 建议保持默认值2。
             */
            this.type = type
        }
        
        fun setContentView(view: RemoteViews) = apply {
            this.contentView = view
        }
        
        fun addExtra(key: String, value: String) = apply {
            extrasBundle.putString(key, value)
        }
        
        fun addExtra(key: String, value: Int) = apply {
            extrasBundle.putInt(key, value)
        }
        
        fun addExtra(key: String, value: Boolean) = apply {
            extrasBundle.putBoolean(key, value)
        }
        
        fun addExtras(bundle: Bundle) = apply {
            extrasBundle.putAll(bundle)
        }
        
        internal fun build(context: Context, notificationManager: NotificationManager): Notification {
            // 创建通知渠道
            createNotificationChannel(notificationManager)
            
            // 构建胶囊配置
            val capsuleBundle = Bundle().apply {
                putInt("notification.live.capsuleStatus", capsuleStatus)
                putInt("notification.live.capsuleType", capsuleType)
                putString("notification.live.capsuleContent", capsuleContent)
                capsuleIcon?.let { putParcelable("notification.live.capsuleIcon", it) }
                if (capsuleBgColor != 0) putInt("notification.live.capsuleBgColor", capsuleBgColor)
                if (capsuleContentColor != 0) putInt("notification.live.capsuleContentColor", capsuleContentColor)
                capsuleContentView?.let { putParcelable("notification.live.capsule.content.remote.view", it) }
            }
            
            // 构建实况通知Bundle
            val liveBundle = Bundle().apply {
                putBoolean("is_live", true)
                putInt("notification.live.operation", operation)
                putInt("notification.live.type", type)
                putBundle("notification.live.capsule", capsuleBundle)
                putAll(extrasBundle) // 添加用户自定义extras
            }
            
            // 构建通知
            val builder = Notification.Builder(context, channelId)
                .setSmallIcon(smallIcon)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setContentIntent(contentIntent)
                .setShowWhen(true)
                .addExtras(liveBundle)
                .setAutoCancel(false)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                
            if (largeIcon != 0) {
                builder.setLargeIcon(BitmapFactory.decodeResource(context.resources, largeIcon))
            }
            
            // 设置自定义布局（展开视图）
            contentView?.let { builder.setCustomContentView(it) }
            
            return builder.build()
        }
        
        private fun createNotificationChannel(notificationManager: NotificationManager) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                importance
            ).apply {
                description = channelDescription
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    /**
     * 创建一个新的LiveNotificationBuilder实例
     * 
     * @param channelId 通知渠道ID
     * @param channelName 通知渠道名称
     * @param notificationId 通知ID
     */
    fun createLiveNotification(
        channelId: String,
        channelName: String,
        notificationId: Int
    ): LiveNotificationBuilder {
        return LiveNotificationBuilder(channelId, channelName, notificationId)
    }
}