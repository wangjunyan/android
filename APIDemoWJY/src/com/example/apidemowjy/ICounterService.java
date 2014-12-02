package com.example.apidemowjy;

public interface ICounterService {
	public void startCounter(int initVal, ICounterCallback callback);
	public void stopCounter();
}
