-dontobfuscate
-optimizationpasses 5
# Guava:
-dontwarn sun.misc.Unsafe
-dontwarn com.google.common.collect.MinMaxPriorityQueue
-dontwarn javax.annotation.**
# Picasso:
-dontwarn com.squareup.okhttp.**
-dontwarn okio.**
# Tests:
-dontwarn com.squareup.leakcanary.DisplayLeakService
-dontwarn android.support.v4.**
-dontwarn org.hamcrest.**
-dontwarn org.junit.**
-dontwarn junit.**
-dontwarn org.codehaus.groovy.**
-dontwarn groovy**
-dontwarn com.squareup.javawriter.JavaWriter
-dontwarn sun.misc.Unsafe
-dontwarn android.support.test.**
# AboutLibraries:
-keep class .R
-keep class **.R$* {
    <fields>;
}