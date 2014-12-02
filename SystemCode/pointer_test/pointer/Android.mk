LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := lightpointer.cpp
LOCAL_SHARED_LIBRARIES := libcutils libutils libbinder
LOCAL_MODULE := lightpointer
include $(BUILD_EXECUTABLE)
