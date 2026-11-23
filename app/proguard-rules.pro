# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Keep line numbers so crash stack traces can be deobfuscated with the mapping
# file (AGP bundles it into the AAB, so Play picks it up automatically).
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Navigation resolves nav-graph argTypes by name at runtime (Class.forName),
# so their class names must survive obfuscation. See main_nav_graph.xml.
-keepnames class com.vanthien113.tiannote.presentation.model.NoteModel