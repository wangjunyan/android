#define LOG_TAG "example.wjy.AddService"

#include<utils/Log.h>
#include"IAddService.h"

using namespace android;

enum
{
	OP_ADD = IBinder::FIRST_CALL_TRANSACTION,
	OP_MUL
};

class BpAddService : public BpInterface<IAddService>
{
	public:
	BpAddService(const sp<IBinder>& impl) : BpInterface<IAddService>(impl)
	{
	}

	int32_t add(int32_t v1, int32_t v2)
	{
		Parcel data;
		data.writeInterfaceToken(IAddService::getInterfaceDescriptor());
		data.writeInt32(v1);
		data.writeInt32(v2);
		Parcel reply;
		remote()->transact(OP_ADD, data, &reply);
        int32_t v = reply.readInt32();
        return v;
	}

	int32_t mul(int32_t v1, int32_t v2)
	{
		Parcel data;
		data.writeInterfaceToken(IAddService::getInterfaceDescriptor());
		data.writeInt32(v1);
		data.writeInt32(v2);
		Parcel reply;
		remote()->transact(OP_MUL, data, &reply);
        int32_t v = reply.readInt32();
        return v;
	}
};

IMPLEMENT_META_INTERFACE(AddService, "example.wjy.IAddService");

status_t BnAddService::onTransact(uint32_t code, const Parcel& data, Parcel* reply, uint32_t flags)
{
	switch(code)
	{
		case OP_ADD:
            {
                CHECK_INTERFACE(IAddService, data, reply);
			    int32_t va1 = data.readInt32();
			    int32_t va2 = data.readInt32();
			    int32_t va = add(va1, va2);
			    reply->writeInt32(va);
			    return NO_ERROR;
            }
		case OP_MUL:
            {
                CHECK_INTERFACE(IAddService, data, reply);
			    int32_t vm1 = data.readInt32();
			    int32_t vm2 = data.readInt32();
			    int32_t vm = mul(vm1, vm2);
			    reply->writeInt32(vm);
			    return NO_ERROR;
            }
		default:
            {
			    return BBinder::onTransact(code, data, reply, flags);
            }
	}
}
