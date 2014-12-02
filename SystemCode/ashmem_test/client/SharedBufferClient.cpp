#define LOG_TAG "example.wjy.SharedBuffer"

#include<utils/Log.h>
#include<binder/IServiceManager.h>
#include<binder/MemoryBase.h>


#include"../common/ISharedBuffer.h"

int main()
{
	sp<IBinder> binder = defaultServiceManager()->getService(String16(SHARED_BUFFER_SERVICE));
	if(binder == NULL)
	{
		LOGE("Failed to get shared buffer service: %s.\n", SHARED_BUFFER_SERVICE);
		return -1;
	}
	sp<ISharedBuffer> service = ISharedBuffer::asInterface(binder);
	if(service == NULL)
	{
		LOGE("Failed to get shared buffer service interface.\n");
		return -2;
	}

	sp<IMemory> buffer = service->getBuffer();
	if(buffer == NULL)
	{
		return -3;
	}

	int32_t* data = (int32_t*)buffer->pointer();
	if(data == NULL)
	{
		return -3;
	}

	LOGI("The value of the shared buffer is %d.\n", *data);
	*data = *data + 1;
	LOGI("Add value 1 to the shared buffer.\n");
	return 0;
}
