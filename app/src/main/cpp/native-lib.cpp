#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_id_co_skoline_model_utils_ShareInfo_getBaseUrl(JNIEnv *env, jobject instance) {
    return env->NewStringUTF("http://103.89.5.232/api/v1/");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_id_co_skoline_model_utils_ShareInfo_getRootBaseUrl(JNIEnv *env, jobject instance) {
    return env->NewStringUTF("http://103.89.5.232");
}