# ============================================
# StudyMate Production ProGuard/R8 Rules
# ============================================

# ---- General ----
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions

# ---- Room ----
-keep class * extends androidx.room.RoomDatabase { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao interface * { *; }
-dontwarn androidx.room.**

# ---- Hilt / Dagger ----
-dontwarn dagger.**
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager { *; }
-keepclasseswithmembers class * {
    @dagger.* <methods>;
}
-keepclasseswithmembers class * {
    @javax.inject.* <fields>;
}
-keepclasseswithmembers class * {
    @javax.inject.* <methods>;
}

# ---- Kotlin Coroutines ----
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# ---- Kotlin Serialization (if used) ----
-keepclassmembers class * {
    @kotlinx.serialization.Serializable <fields>;
}

# ---- Compose ----
-dontwarn androidx.compose.**

# ---- Data classes (entities) ----
-keep class com.m3.rajat.piyush.studymatealpha.data.local.entity.** { *; }

# ---- Keep the Application class ----
-keep class com.m3.rajat.piyush.studymatealpha.StudyMateAlpha { *; }

# ---- Remove Log calls in release ----
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
    public static int i(...);
}