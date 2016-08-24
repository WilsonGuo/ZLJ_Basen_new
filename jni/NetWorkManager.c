#include <jni.h>
#include "TypeDef.h"
#include "com_example_smarteair_net_NetWorkManager.h"

extern unsigned int g_printf_debug_on;
static  JavaVM *g_jvm = NULL; 
static  jobject g_obj = NULL; 
uint32 SendMsgToUi(int msgType, int DevId, uint8* buff, int len);

JNIEXPORT int JNICALL Java_com_example_smarteair_net_NetWorkManager_JniNetWorkInit
  (JNIEnv *env, jobject jo,  jint wifi_id, jint group_id,   jbyteArray check)
{
    jbyte *check_byte;
    int rc = 0;
    
    if(g_printf_debug_on != 0)
    {
        JNI_LOGI("JNI_INTERFACE: JniNetWorkInit!!!!!!!!");
    }
    //保存全局JVM以便在子线程中使用 
    (*env)->GetJavaVM(env,&g_jvm); 
    //不能直接赋值(g_obj = obj) 
    g_obj = (*env)->NewGlobalRef(env,jo); 
    
    check_byte = (*env)-> GetByteArrayElements(env,check,0);

    rc= UserInit(wifi_id, group_id, check_byte);


    JNI_LOGI("JNI_INTERFACE: JniNetWorkInit rc=%d", rc);

    return   rc;
}


JNIEXPORT int JNICALL Java_com_example_smarteair_net_NetWorkManager_JniNetWorkExit
  (JNIEnv *env, jobject jo)
{
	if(g_printf_debug_on != 0)
	{
        JNI_LOGI("JNI_INTERFACE: JniNetWorkExit!!!!!!!!");
	}
    (*env)->DeleteGlobalRef(env, g_obj);
    return UserNetWorkExit();
}


JNIEXPORT int JNICALL Java_com_example_smarteair_net_NetWorkManager_JniNetWorkPause
  (JNIEnv *env, jobject jo)
{
    int rc = 0;

    if(g_printf_debug_on != 0)
    {
        JNI_LOGI("JNI_INTERFACE: JniNetWorkPause!!!!!!!!");
    }
    
    rc = UserNetWorkPause();
    return rc;
}

JNIEXPORT int JNICALL Java_com_example_smarteair_net_NetWorkManager_JniNetWorkResume
  (JNIEnv *env, jobject jo)
{
    int rc = 0;
    
    if(g_printf_debug_on != 0)
    {
        JNI_LOGI("JNI_INTERFACE: JniNetWorkResume!!!!!!!!");
    }
    
    rc = UserNetWorkResume();
    return rc;
}

JNIEXPORT int JNICALL Java_com_example_smarteair_net_NetWorkManager_JniEntryScanMode
  (JNIEnv *env, jobject jo)
{
    int rc = 0;
    
    if(g_printf_debug_on != 0)
    {
        JNI_LOGI("JNI_INTERFACE: JniEntryScanMode!!!!!!!!");
    }

   rc = UserEntryScanMode();

    return rc;
}

JNIEXPORT int JNICALL Java_com_example_smarteair_net_NetWorkManager_JniExitScanMode
  (JNIEnv *env, jobject jo)
{
    int rc = 0;

    if(g_printf_debug_on != 0)
    {
        JNI_LOGI("JNI_INTERFACE: JniExitScanMode!!!!!!!!");
    }

    rc = UserExitScanMode();

    return rc;
}


JNIEXPORT int JNICALL Java_com_example_smarteair_net_NetWorkManager_JniAddDevie
  (JNIEnv *env, jobject jo, jint wifi_id)
{
    int rc = 0;
    
    if(g_printf_debug_on != 0)
    {
        JNI_LOGI("JNI_INTERFACE: JniAddDevie SN = %X!!!!!!!!", wifi_id);
    }
    
    rc = UserAddDevie(wifi_id);

    return rc;
}

JNIEXPORT int JNICALL Java_com_example_smarteair_net_NetWorkManager_JniDelDevie
  (JNIEnv *env, jobject jo, jint wifi_id)
{
    int rc = 0;

    if(g_printf_debug_on != 0)
    {
        JNI_LOGI("JNI_INTERFACE: JniDelDevie SN = %X!!!!!!!!", wifi_id);
    }

    rc = UserDelDevie(wifi_id);

   return rc;
}

JNIEXPORT int JNICALL Java_com_example_smarteair_net_NetWorkManager_JniActiveDevice
  (JNIEnv *env, jobject jo, jint wifi_id)
{
    int rc = 0;
    
    if(g_printf_debug_on != 0)
	{
        JNI_LOGI("JNI_INTERFACE: JniActiveDevice SN = %X!!!!!!!!", wifi_id);
	}

    rc = UserActiveDevice(wifi_id);

    return rc;
}
    
    
JNIEXPORT int JNICALL Java_com_example_smarteair_net_NetWorkManager_JniDisActiveDevice
  (JNIEnv *env, jobject jo, jint wifi_id)
{
    int rc = 0;
    
    if(g_printf_debug_on != 0)
	{
        JNI_LOGI("JNI_INTERFACE: JniDisActiveDevice SN = %X!!!!!!!!", wifi_id);
	}
   rc = UserDisActiveDevice(wifi_id);

    return rc;
}
    
    
    
JNIEXPORT int JNICALL Java_com_example_smarteair_net_NetWorkManager_JniDisActiveAll
  (JNIEnv *env, jobject jo)
{
    int rc = 0;
    
    if(g_printf_debug_on != 0)
	{
        JNI_LOGI("JNI_INTERFACE: JniDisActiveAll!!!!!!!!");
	}

    rc = UserDisActiveAll();
    return rc;
}

JNIEXPORT int JNICALL Java_com_example_smarteair_net_NetWorkManager_JniGetDeviceLinkStatus
  (JNIEnv *env, jobject jo, jint wifi_id)
{
    int rc = 0;
    
    rc = UserGetDeviceLinkStatus(wifi_id);

    if(g_printf_debug_on != 0)
	{
        JNI_LOGI("JNI_INTERFACE: JniGetDeviceLinkStatus SN = %X, rc=%d !!", wifi_id, rc);
	}
    return rc;
}


JNIEXPORT int JNICALL Java_com_example_smarteair_net_NetWorkManager_JniGetServiceLinkStatus
  (JNIEnv *env, jobject jo)
{
    int rc = 0;
    
    if(g_printf_debug_on != 0)
	{
        JNI_LOGI("JNI_INTERFACE: JniGetServiceLinkStatus!!!!!!!!");
	}

    rc = UserGetServiceLinkStatus();

    return rc;
}

//将会阻塞
JNIEXPORT int JNICALL Java_com_example_smarteair_net_NetWorkManager_JniApConfig
    (JNIEnv *env, jobject jo, jbyteArray ssid, jbyteArray password,  jint add_wifi)
{
    int rc = 0;    
    jbyte *jb_ssid;
    jbyte *jb_password;
    int ssid_len;
    int ps_len;

    jb_ssid = (*env)-> GetByteArrayElements(env,ssid,0);
    jb_password = (*env)-> GetByteArrayElements(env,password,0);
        
    ssid_len =  (*env)->GetArrayLength(env, ssid);
    ps_len =  (*env)->GetArrayLength(env, password);

    if(g_printf_debug_on != 0)
	{
        JNI_LOGI("JNI_INTERFACE: JniApConfig, ssid_len=%d, ps_len=%d", ssid_len, ps_len);
	}

    rc = UserApConfig(jb_ssid, ssid_len,  jb_password, ps_len, add_wifi);

    return rc;
}

JNIEXPORT int JNICALL Java_com_example_smarteair_net_NetWorkManager_JniCancelAPConfig
(JNIEnv *env, jobject jo)
{
    int rc = 0;

    if(g_printf_debug_on != 0)
	{
        JNI_LOGI("JNI_INTERFACE: JniCancelAPConfig!!!!!!!!");
	}
    
    UserCancelAPConfig();

    return rc;
}

JNIEXPORT int JNICALL Java_com_example_smarteair_net_NetWorkManager_JniSendData
    (JNIEnv *env, jobject jo, jint wifi_id, jint datalen, jbyteArray data)
{
    int rc = 0;
    jbyte *p;

    p = (*env)-> GetByteArrayElements(env,data,0);  
    
    rc = UserSendData(wifi_id, datalen, p);

    if(g_printf_debug_on != 0)
  	{
          JNI_LOGI("JNI_INTERFACE: send data: sn=%x, len=%d!", wifi_id, datalen);
  	}
    return rc;
}

char MainCmdString[32][64] = {
      "MAIN_MSG_TYPE_GET_APP_SN",
      "MAIN_MSG_TYPE_DATA",
      "MAIN_MSG_TYPE_NETWORK_UP",
      "MAIN_MSG_TYPE_NETWORK_DOWN",
      "MAIN_MSG_TYPE_FIND_NEW_WIFI_DEV",
      "MAIN_MSG_TYPE_MISS_NEW_WIFI_DEV",
      "MAIN_MSG_TYPE_ADD_NEW_WIFI_DEV_SUCCESS",
      "MAIN_MSG_TYPE_DEL_WIFI_DEV_SUCCESS",
      "MAIN_MSG_TYPE_SET_WIFI_PASSWORD_SUCCESS",
      "MAIN_MSG_NULL",
      "MAIN_MSG_TYPE_ONE_SECOND",
      "MAIN_MSG_NULL",
      "MAIN_MSG_NULL",
      "MAIN_MSG_NULL",
      "MAIN_MSG_NULL",
      "MAIN_MSG_NULL"};
                                                 
 uint32 SendMsgToUi(int msgType, int wifi_id, uint8* buff, int len)
 {
    #if 1
     int i;
     JNIEnv *JniEnv; 
     jclass JNIJavacClass; 
     jfieldID   JvBuffId;
     jmethodID CtoJavaId;


      //Attach主线程 
     if((*g_jvm)->AttachCurrentThread(g_jvm, &JniEnv, NULL) != JNI_OK) 
     { 
         LOGI("%s: AttachCurrentThread() failed", __FUNCTION__); 
         return false;
     } 

      //找到对应的类 
     JNIJavacClass = (*JniEnv)->GetObjectClass(JniEnv,g_obj); 
     if(JNIJavacClass == NULL) 
     { 
         LOGI("FindClass() Error....."); 
         goto error; 
     } 

     CtoJavaId = (*JniEnv)->GetStaticMethodID(JniEnv, JNIJavacClass,"SendMsgToUi","(III[B)V");//查找java中的show方法的ID，最后的签名符号为void类型  
     JvBuffId  = (*JniEnv)->GetFieldID(JniEnv,  JNIJavacClass, "CtoJvBuff", "[B");
     
     jbyteArray  array =   (*JniEnv)->GetObjectField(JniEnv, g_obj, JvBuffId);

      jsize length = (*JniEnv)->GetArrayLength(JniEnv, array);

      if(length < len)
      {
          goto error;
      }

      (*JniEnv)->SetByteArrayRegion(JniEnv, array, 0, len, (jbyte*)buff);

    // jbyte *pointer = (*JniEnv)->GetByteArrayElements(JniEnv, array, NULL); //获取数组指针
     
    //for (i=0; i<len; i++)
    //{
    //    pointer[i] = buff[i]; //相加每个数组元素
    //}
    
    //(*JniEnv)->ReleaseByteArrayElements(JniEnv, array, pointer, 0); //释放内存，这个不能忘了
    
    (*JniEnv)->CallStaticVoidMethod(JniEnv, JNIJavacClass,CtoJavaId,msgType,wifi_id, len, array);

   JNI_LOGI("SendMsgToUi: msgType=%s, DevId=%X, len=%d", MainCmdString[msgType], wifi_id, len);

error:   
    
     if((*g_jvm)->DetachCurrentThread(g_jvm) != JNI_OK)
     { 
         LOGI("%s: DetachCurrentThread() failed", __FUNCTION__); 
     } 

     #endif
     return true;
 }
     


