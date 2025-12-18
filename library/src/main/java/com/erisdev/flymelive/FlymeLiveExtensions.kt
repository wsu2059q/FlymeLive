package com.erisdev.flymelive

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.RemoteViews
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes

/**
 * FlymeLiveManager的扩展函数，提供更简洁的API
 * 
 * 这些扩展函数旨在简化Flyme实况通知的创建过程，提供DSL风格和便利方法
 *
 */

/**
 * 使用DSL方式创建并显示实况通知
 * 
 * 示例用法：
 * ```
 * flymeLiveManager.showLiveNotification("channel_id", "Channel Name", 1001) {
 *     setSmallIcon(R.drawable.ic_notification)
 *     setContentTitle("标题")
 *     setContentText("内容")
 * }
 * ```
 */
@Suppress("unused")
inline fun FlymeLiveManager.showLiveNotification(
    channelId: String,
    channelName: String,
    notificationId: Int,
    block: FlymeLiveManager.LiveNotificationBuilder.() -> Unit
): Int {
    val builder = createLiveNotification(channelId, channelName, notificationId)
    block(builder)
    return showLiveNotification(builder)
}

/**
 * 快速设置胶囊视图的扩展函数
 * 
 * 允许使用XML布局文件创建自定义胶囊视图
 * 
 * 示例用法：
 * ```
 * setCapsuleView(context, R.layout.capsule_layout) { remoteViews ->
 *     remoteViews.setTextViewText(R.id.text, "胶囊内容")
 *     remoteViews.setTextColor(R.id.text, Color.WHITE)
 * }
 * ```
 */
@Suppress("unused")
fun FlymeLiveManager.LiveNotificationBuilder.setCapsuleView(
    context: Context,
    @LayoutRes layoutId: Int,
    config: RemoteViews.() -> Unit
): FlymeLiveManager.LiveNotificationBuilder {
    val remoteViews = RemoteViews(context.packageName, layoutId)
    config(remoteViews)
    return setCapsuleContentView(remoteViews)
}

/**
 * 快速设置内容视图的扩展函数
 * 
 * 允许使用XML布局文件创建自定义内容视图（点击胶囊后展开的视图）
 * 
 * 示例用法：
 * ```
 * setContentView(context, R.layout.content_layout) { remoteViews ->
 *     remoteViews.setTextViewText(R.id.title, "展开标题")
 *     remoteViews.setTextViewText(R.id.message, "展开内容")
 * }
 * ```
 */
@Suppress("unused")
fun FlymeLiveManager.LiveNotificationBuilder.setContentView(
    context: Context,
    @LayoutRes layoutId: Int,
    config: RemoteViews.() -> Unit
): FlymeLiveManager.LiveNotificationBuilder {
    val remoteViews = RemoteViews(context.packageName, layoutId)
    config(remoteViews)
    return setContentView(remoteViews)
}

/**
 * 快速创建基于资源的图标 (仅支持API 23及以上版本)
 */
@Suppress("unused")
fun FlymeLiveManager.LiveNotificationBuilder.setCapsuleIconFromResource(
    context: Context,
    @DrawableRes iconRes: Int
): FlymeLiveManager.LiveNotificationBuilder {
    return setCapsuleIcon(Icon.createWithResource(context, iconRes))
}

/**
 * 设置通知内容点击事件
 */
@Suppress("unused")
fun FlymeLiveManager.LiveNotificationBuilder.setContentIntent(
    context: Context,
    targetClass: Class<*>,
    requestCode: Int = 0,
    flags: Int = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
): FlymeLiveManager.LiveNotificationBuilder {
    val intent = Intent(context, targetClass)
    val pendingIntent = PendingIntent.getActivity(context, requestCode, intent, flags)
    return setContentIntent(pendingIntent)
}

/**
 * 为胶囊视图设置点击事件
 * 
 * 当用户点击状态栏中的胶囊时，会触发此意图
 * 
 * 示例用法：
 * ```
 * setCapsuleClickIntent(context, DetailActivity::class.java)
 * ```
 */
@Suppress("unused")
fun FlymeLiveManager.LiveNotificationBuilder.setCapsuleClickIntent(
    context: Context,
    targetClass: Class<*>,
    requestCode: Int = 0,
    flags: Int = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
): FlymeLiveManager.LiveNotificationBuilder {
    val intent = Intent(context, targetClass)
    val pendingIntent = PendingIntent.getActivity(context, requestCode, intent, flags)
    val bundle = Bundle()
    bundle.putParcelable("notification.live.capsule.content.intent", pendingIntent)
    return addExtras(bundle)
}

/**
 * 为胶囊视图设置文本颜色
 */
@Suppress("unused")
fun FlymeLiveManager.LiveNotificationBuilder.setCapsuleContentColorRes(
    context: Context,
    colorRes: Int
): FlymeLiveManager.LiveNotificationBuilder {
    val color = context.resources.getColor(colorRes, null)
    return setCapsuleContentColor(color)
}

/**
 * 为胶囊视图设置背景颜色
 */
@Suppress("unused")
fun FlymeLiveManager.LiveNotificationBuilder.setCapsuleBackgroundColorRes(
    context: Context,
    colorRes: Int
): FlymeLiveManager.LiveNotificationBuilder {
    val color = context.resources.getColor(colorRes, null)
    return setCapsuleBgColor(color)
}