LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := ../common/IAddService.cpp AddServer.cpp
LOCAL_SHARED_LIBRARIES := libcutils libutils libbinder
LOCAL_MODULE := AddServer
include $(BUILD_EXECUTABLE)
