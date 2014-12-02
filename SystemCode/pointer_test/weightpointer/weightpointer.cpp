#include<stdio.h>
#include<utils/Log.h>
#include<utils/StrongPointer.h>
#include<utils/RefBase.h>

#define INITIAL_STRONG_VALUE (1<<28)

using namespace android;

class WeightClass : public RefBase
{
public:
	void printRefCount()
	{
		int32_t strong = getStrongCount();
		weakref_type* ref = getWeakRefs();
		printf("----------------\n");
		printf("Strong Ref Count: %d.\n", (strong==INITIAL_STRONG_VALUE ? 0 : strong));
		printf("Weak Ref Count: %d.\n", ref->getWeakCount());
		printf("----------------\n");
	}
};

class StrongClass : public WeightClass
{
public:
	StrongClass()
	{
		printf("Construct StrongClass Object.\n");
	}

	virtual ~StrongClass()
	{
		printf("Destroy StrongClass Object.\n");
	}
};

class WeakClass : public WeightClass
{
public:
	WeakClass()
	{
		extendObjectLifetime(OBJECT_LIFETIME_WEAK);
		printf("Construct WeakClass Object.\n");
	}

	virtual ~WeakClass()
	{
		printf("Destroy WeakClass Object.\n");
	}
};

void TestStrongClass(StrongClass* pStrongClass)
{
	wp<StrongClass> wpOut = pStrongClass;
	pStrongClass->printRefCount();
	{
		sp<StrongClass> spInner = pStrongClass;
		pStrongClass->printRefCount();
	}

	pStrongClass->printRefCount();

	sp<StrongClass> spOut = wpOut.promote();
	printf("spOut: %p.\n", spOut.get());
}

void TestWeakClass(WeakClass* pWeakClass)
{
	wp<WeakClass> wpOut = pWeakClass;
	pWeakClass->printRefCount();
	{
		sp<WeakClass> spInner = pWeakClass;
		pWeakClass->printRefCount();
	}

	pWeakClass->printRefCount();

	sp<WeakClass> spOut = wpOut.promote();
	printf("spOut: %p.\n", spOut.get());
}

int main()
{
	printf("Test Strong Class:\n");
	StrongClass* pStrongClass = new StrongClass();
	TestStrongClass(pStrongClass);

	printf("Test Weak Class:\n");
	WeakClass* pWeakClass = new WeakClass();
	TestWeakClass(pWeakClass);
	return 0;
}
