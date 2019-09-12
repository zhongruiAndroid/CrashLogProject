
### 收集app使用过程的崩溃日志
#### Application 中初始化
```java
CrashLog.get()
        //初始化
        .init(this)

        //设置日志文件的后缀名,不设置默认为.txt
        .setFileNameSuffix(".txt")

        //设置日志文件存储路径,不设置默认为(外部缓存目录)Android/data/包名/cache/crash
        //如果设置了SD卡存储路径,但是没有文件写入权限,则默认储存在Android/data/包名/cache/crash/目录下
        .setSavePath(savePath)                

        //将额外的信息储存在日志文件中
        .setExtraLogInfo("额外的信息");
```
