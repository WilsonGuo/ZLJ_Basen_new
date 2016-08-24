LOCAL_PATH := $(call my-dir)


include $(CLEAR_VARS)   
LOCAL_MODULE    := static  
LOCAL_SRC_FILES := libNetLink.a   
include $(PREBUILT_STATIC_LIBRARY)  
  
include $(CLEAR_VARS)
LOCAL_LDLIBS    := -lm -llog 
LOCAL_MODULE    := NetWorkManager
LOCAL_SRC_FILES :=  NetWorkManager.c   
LOCAL_STATIC_LIBRARIES  := static
include $(BUILD_SHARED_LIBRARY)

