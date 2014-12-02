#define LOG_TAG "example.wjy.AddService"

#include<utils/Log.h>
#include<binder/IServiceManager.h>

#include"../common/IAddService.h"

int main()
{
	sp<IBinder> binder = defaultServiceManager()->getService(String16(ADD_SERVICE));
	if(binder == NULL)
	{
		LOGE("Failed to get add service: %s.\n", ADD_SERVICE);
		return -1;
	}
	sp<IAddService> service = IAddService::asInterface(binder);
	if(service == NULL)
	{
		LOGE("Failed to get add service interface.\n");
		return -2;
	}

	int32_t v1 = 2;
	int32_t v2 = 4;
	int32_t va = service->add(v1, v2);
	int32_t vm = service->mul(v1, v2);
	LOGI("%d + %d = %d\n", v1, v2, va);
	LOGI("%d x %d = %d\n", v1, v2, vm);
	return 0;
}
