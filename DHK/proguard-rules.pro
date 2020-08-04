#------------------  下方是共性的排除项目         ----------------
# 方法名中含有“JNI”字符、或带有 native 关键词的，认定是Java Native Interface方法，自动排除
# 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除

-keepclasseswithmembers class * { native <methods>; }
-keepclasseswithmembers class * {
    ... *JNI*(...);
}
-keepclasseswithmembernames class * {
	... *JRI*(...);
}
-keep class **JNI* {*;}

# keep curllib
-keep class com.moxie.mxcurllib.** { *; }

# keep annotated by NotProguard
-keep @com.proguard.annotation.NotProguard class * {*;}
-keep class * {
    @com.proguard.annotation <fields>;
    @android.webkit.JavascriptInterface <fields>;
}
-keepclassmembers class * {
    @com.proguard.annotation <fields>;
    @android.webkit.JavascriptInterface <fields>;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
#-ignorewarnings
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontwarn dalvik.**

#------------------  下方是android平台自带的排除项，这里不要动         ----------------
# 保留使用的四大组件，自定义的Application等等这些类不被混淆
# 因为这些子类都有可能被外部调用
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class com.android.vending.licensing.ILicensingService
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference

# 保护注解
-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation {*;}
# 避免混淆泛型
-keepattributes Signature
# 避免混淆反射
-keepattributes EnclosingMethod
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
# 不混淆内部类
-keepattributes InnerClasses

# 不混淆枚举
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 不混淆Bean对象
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-dontwarn com.igexin.**
-keep class com.igexin.** { *; }
-keep class org.json.** { *; }