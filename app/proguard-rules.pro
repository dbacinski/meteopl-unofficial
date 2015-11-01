-dontobfuscate
-optimizationpasses 5
# Guava:
-dontwarn sun.misc.Unsafe
-dontwarn com.google.common.collect.MinMaxPriorityQueue
-dontwarn javax.annotation.**
# Picasso:
-dontwarn com.squareup.okhttp.**
# AboutLibraries:
-keep class .R
-keep class **.R$* {
    <fields>;
}