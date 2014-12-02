#ifndef IADDSERVICE_H_
#define IADDSERVICE_H_

#include<utils/RefBase.h>
#include<binder/IInterface.h>
#include<binder/Parcel.h>

#define ADD_SERVICE "example.wjy.AddService"

using namespace android;

class IAddService : public IInterface
{
	public:
	DECLARE_META_INTERFACE(AddService);
	virtual int32_t add(int32_t v1, int32_t v2) = 0;
	virtual int32_t mul(int32_t v1, int32_t v2) = 0;
};

class BnAddService : public BnInterface<IAddService>
{
	public:
	virtual status_t onTransact(uint32_t code, const Parcel& data, Parcel* reply, uint32_t flags=0);
};

#endif
