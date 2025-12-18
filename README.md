# FlymeLive 模块

如果您不知道如何使用此包，请查看 [GitHub Packages 页面](https://github.com/wsu2059q/FlymeLive/packages/2781103?version=1.0.0) 获取帮助。

FlymeLive 是一个用于简化 Flyme 系统实况通知功能使用的库。它提供了易于使用的 API 来创建和管理 Flyme 实况通知。

> ⚠️ 为了获得最佳视觉效果，强烈建议开发者自定义胶囊的所有视觉元素（包括背景色、文字颜色、图标等），避免使用系统默认样式，因为默认样式可能无法达到理想的视觉效果。

> Flyme 实况通知的视觉呈现完全依赖于开发者自定义的胶囊视图和展开视图。系统不会提供默认的美化样式，因此开发者必须仔细设计这两个组件的视觉效果，以确保在各种设备和主题下都能呈现出良好的用户体验。

## 功能特性

- 简化 Flyme 实况通知的创建过程
- 提供链式调用和 DSL 风格的 API
- 封装复杂的 Bundle 和 RemoteViews 配置
- 支持自定义胶囊视图和内容视图
- 易于集成到现有项目中

## Flyme 实况通知说明

Flyme 实况通知具有独特的交互方式：

1. **胶囊视图** - 通知在状态栏中显示的小胶囊形式
2. **展开视图** - 下拉通知栏或点击胶囊后显示的详细内容视图

### Flyme系统特殊性

需要注意的是，在Flyme系统中：
- 传统的通知标题、内容等字段通常不会显示
- 系统只显示胶囊视图和展开视图
- 通知栏中展示的也是展开视图的内容，而非传统通知布局
- 因此在设计通知时应重点关注胶囊视图和展开视图的设计

## 示例

```kotlin
val flymeLiveManager = FlymeLiveManager(context)

// 创建胶囊视图
val capsuleView = RemoteViews(context.packageName, R.layout.live_notification_capsule).apply {
    setTextViewText(R.id.capsule_content, "Hello World")
}

// 创建展开视图
val contentView = RemoteViews(context.packageName, R.layout.live_notification_content).apply {
    setTextViewText(R.id.title, "实况通知标题")
    setTextViewText(R.id.message, "这是实况通知的详细内容")
}

flymeLiveManager.showLiveNotification(
    channelId = "live_channel",
    channelName = "Live Channel",
    notificationId = 1001
) {
    setSmallIcon(R.drawable.ic_notification)
    setContentTitle("传统通知字段（Flyme中不显示）")
    setContentText("传统通知字段（Flyme中不显示）")
    
    // 胶囊配置
    setCapsuleContent("实况")
    setCapsuleContentView(capsuleView)
    setCapsuleIconFromResource(context, R.drawable.ic_notification)
    setCapsuleBgColor(Color.BLUE)
    setCapsuleContentColor(Color.WHITE)
    
    // 展开视图配置
    setContentView(contentView)
}
```

## API 说明

### FlymeLiveManager

主要的管理类，用于创建和管理实况通知。

#### 方法

- `showLiveNotification(builder: LiveNotificationBuilder): Int` - 显示实况通知
- `cancelNotification(notificationId: Int)` - 取消指定ID的通知
- `cancelAllNotifications()` - 取消所有通知
- `createLiveNotification(channelId: String, channelName: String, notificationId: Int): LiveNotificationBuilder` - 创建通知构建器

### 扩展函数

- `showLiveNotification(...)` - DSL风格的通知创建和显示方法
- `setCapsuleView(...)` - 快速设置胶囊视图
- `setContentView(...)` - 快速设置内容视图
- `setCapsuleIconFromResource(...)` - 从资源创建胶囊图标
- `setContentIntent(...)` - 设置通知内容点击事件
- `setCapsuleClickIntent(...)` - 为胶囊视图设置点击事件
- `setCapsuleContentColorRes(...)` - 通过资源ID设置胶囊内容颜色
- `setCapsuleBackgroundColorRes(...)` - 通过资源ID设置胶囊背景颜色

## 参数说明

Flyme 实况通知涉及多个参数，经过测试发现：

- `capsuleStatus`: 胶囊状态，只有值为1时胶囊可见，其他值会使胶囊隐藏
- `capsuleType`: 胶囊类型，测试中不同值没有明显视觉差异
- `operation`: 操作类型，测试中不同值没有明显视觉差异
- `type`: 通知类型，测试中不同值没有明显视觉差异

> 除非有特殊需求，否则建议保持各参数的默认值，因为目前测试发现改变这些值除了影响胶囊是否可见外，没有其他明显效果。

## 权限要求

在您的应用中添加以下权限到 `AndroidManifest.xml`：

```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<uses-permission android:name="flyme.permission.READ_NOTIFICATION_LIVE_STATE" />
```

## 注意事项

1. 仅适用于 Flyme 系统
2. 需要相应的权限才能正常工作
3. 不要依赖setContentTitle和setContentText等传统通知字段，因为Flyme系统通常不显示这些内容
4. 在 Android 13 (API 33) 及更高版本上，您需要在运行时请求 `POST_NOTIFICATIONS` 权限

## 示例项目
您可以查看 [FlymeLiveDemo](https://github.com/wsu2059q/FlymeLiveDemo) 项目，了解如何在实际应用中使用此库的完整示例。

## 致谢
基于 [Ruyue-Kinsenka/Flyme-Live-Notification-Demo](https://github.com/Ruyue-Kinsenka/Flyme-Live-Notification-Demo) 的代码进行开发与参考，特此致谢！