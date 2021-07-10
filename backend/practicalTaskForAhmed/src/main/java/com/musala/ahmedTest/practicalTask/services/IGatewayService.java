package com.musala.ahmedTest.practicalTask.services;

import java.util.Collection;

import com.musala.ahmedTest.practicalTask.exceptions.NotAllowedDataException;
import com.musala.ahmedTest.practicalTask.exceptions.NotFoundException;
import com.musala.ahmedTest.practicalTask.models.Gateway;
import com.musala.ahmedTest.practicalTask.models.PeripheralDevice;

public interface IGatewayService {


	Collection<Gateway> findAll();

	Gateway addNewGateway(Gateway gatway) throws NotAllowedDataException;
	Gateway findById(String gatewaySerial)throws  NotFoundException;
	Gateway addNewDevice(PeripheralDevice newDevice , String serial) throws NotAllowedDataException, NotFoundException;
	Gateway replaceGateway(Gateway gateway, String id) throws NotAllowedDataException;
	
	void deleteById(String serial)throws  NotFoundException;
	void deleteDeviceFromGatewayById(String serial,Integer uid)throws  NotFoundException;

}
