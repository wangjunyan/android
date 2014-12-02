#define LOG_TAG "example.wjy.AddService"

#include<utils/Log.h>
#include<binder/IServiceManager.h>
#include<binder/IPCThreadState.h>

#include"../common/IAddService.h"

class AddService : public BnAddService
{
	public:
	AddService()
	{
	}

	virtual ~AddService()
	{
	}


	public:
	static void instantiate()
	{
        LOGI("add %s to service manager.\n", ADD_SERVICE);
		defaultServiceManager()->addService(String16(ADD_SERVICE), new AddService());
	}

	int32_t add(int32_t v1, int32_t v2)
	{
        LOGI("get add request: v1=%d, v2=%d\n", v1, v2);
		return v1 + v2;
	}

	int32_t mul(int32_t v1, int32_t v2)
	{
        LOGI("get mul request: v1=%d, v2=%d\n", v1, v2);
		return v1 * v2;
	}
};

int main(int argc, char** argv)
{
    //sp<ProcessState> proc(ProcessState::self());
	AddService::instantiate();
	ProcessState::self()->startThreadPool();
	IPCThreadState::self()->joinThreadPool();
	return 0;
}
