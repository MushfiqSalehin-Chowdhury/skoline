#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_id_co_skoline_model_utils_ShareInfo_getBaseUrl(JNIEnv *env, jobject instance) {
    return env->NewStringUTF("http://192.168.50.39:3000/");
}