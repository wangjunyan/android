#include<stdio.h>
#include<utils/Log.h>
#include<utils/StrongPointer.h>
#include<utils/RefBase.h>

using namespace android;

class LightClass : public LightRefBase<LightClass>
{
public:
	LightClass()
	{
		printf("Construct LightClass Object.\n");
	}

	virtual ~LightClass()
	{
		printf("Destory LightClass Object.\n");
	}
};


int main()
{
	LightClass* pLightClass = new LightClass();

    printf("Light Ref Count: %d.\n", pLightClass->getStrongCount());

	sp<LightClass> lpOut = pLightClass;

	printf("Light Ref Count: %d.\n", pLightClass->getStrongCount());

	{
		sp<LightClass> lpInner = lpOut;
		printf("Light Ref Count: %d.\n", pLightClass->getStrongCount());
	}

	printf("Light Ref Count: %d.\n", pLightClass->getStrongCount());
	return 0;
}
