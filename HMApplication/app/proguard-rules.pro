-keep public class MyClass

############ Crashlytics ################################
-keepattributes *Annotation*
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception
###########https://firebase.google.com/docs/crashlytics/get-deobfuscated-reports?authuser=0