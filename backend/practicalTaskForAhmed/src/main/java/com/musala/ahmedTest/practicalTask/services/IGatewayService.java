package com.musala.ahmedTest.practicalTask.services;

import java.util.Collection;

import com.musala.ahmedTest.practicalTask.dtos.GatewayDTO;
import com.musala.ahmedTest.practicalTask.dtos.PeripheralDeviceDTO;
import com.musala.ahmedTest.practicalTask.exceptions.NotAllowedDataException;
import com.musala.ahmedTest.practicalTask.exceptions.NotFoundException;
import com.musala.ahmedTest.practicalTask.models.Gateway;

public interface IGatewayService {
	
	String GATEWAY_NOT_FOUND_MESSAGE = " Couldn't find a gateway with serial :";
	String MAXIMUM_ALLOWED_DEVICE_NUMBER_MESSAGE = " The Maximum Number of Peripheral Devices for each Gateway is 10 ";
	String DEVICE_NOT_FOUND_MESSAGE = " Couldn't find a Peripheral device with uid :";
	String INVALID_IP_MESSAGE = " Invalid IP V4 Address: ";
	String INVALID_GATEWAY_NAME_MESSAGE = " Invalid gateway name ";
	String SERIAL_USED_BEFORE_MESSAGE = " This value  is used before for serial number : ";

	Collection<Gateway> findAll();

	Gateway addGateway(GatewayDTO gatewayDTO) throws NotAllowedDataException;

	Gateway findById(String gatewaySerial) throws NotFoundException;

	Gateway addDevice(PeripheralDeviceDTO perDeviceDTO, String serial)
			throws NotAllowedDataException, NotFoundException;
//	Gateway replaceGateway(GatewayDTO gatewayDTO, String id) throws NotAllowedDataException;

	void deleteById(String serial) throws NotFoundException;

	void deleteDeviceFromGatewayById(String serial, Integer uid) throws NotFoundException;

	Gateway update(GatewayDTO gatewayDTO, String serial) throws NotAllowedDataException;

}
