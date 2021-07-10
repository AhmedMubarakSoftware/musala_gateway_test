package com.musala.ahmedTest.practicalTask.services;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musala.ahmedTest.practicalTask.exceptions.NotAllowedDataException;
import com.musala.ahmedTest.practicalTask.exceptions.NotFoundException;
import com.musala.ahmedTest.practicalTask.models.Gateway;
import com.musala.ahmedTest.practicalTask.models.PeripheralDevice;
import com.musala.ahmedTest.practicalTask.repos.GatewayRepo;

@Service
public class GatewayServiceImpl implements IGatewayService {

	private static final String GATEWAY_NOT_FOUND_MESSAGE = " Couldn't find a gateway with serial :";
	private static final String MAXIMUM_ALLOWED_DEVICE_NUMBER_MESSAGE = " The Maximum Number of Peripheral Devices for each Gateway is 10 ";
	private static final String DEVICE_NOT_FOUND_MESSAGE = " Couldn't find a Peripheral device with uid :";
	private static final String INVALID_IP_MESSAGE = " Invalid IP V4 Address: ";
	
	@Autowired
	private GatewayRepo gatewayRepo;

	public Collection<Gateway> findAll() {
		return gatewayRepo.findAll();
	}

	public Gateway findById(String gatewaySerial) {
		return gatewayRepo.findById(gatewaySerial)
				.orElseThrow(() -> new NotFoundException(GATEWAY_NOT_FOUND_MESSAGE + gatewaySerial));

	}

	@Override
	public Gateway addNewGateway(Gateway gateway) throws NotAllowedDataException {
		if (notValidIPV4Address(gateway.getIpAddress()))
			throw new NotAllowedDataException(INVALID_IP_MESSAGE + gateway.getIpAddress());
		if (null != gateway.getPeripheralDevices()) {
			if (gateway.getPeripheralDevices().size() > 10)
				throw new NotAllowedDataException(MAXIMUM_ALLOWED_DEVICE_NUMBER_MESSAGE);

			for (PeripheralDevice peripheralDevice : gateway.getPeripheralDevices()) {
				peripheralDevice.setGateway(gateway);
			}
			gateway.setPeripheralDevices(gateway.getPeripheralDevices());
		}
		return gatewayRepo.save(gateway);

	}

	@Override
	public Gateway addNewDevice(PeripheralDevice newDevice, String serial)
			throws NotAllowedDataException, NotFoundException {
		var gateway = findById(serial);
		if (gateway.getPeripheralDevices().size() == 10)
			throw new NotAllowedDataException(MAXIMUM_ALLOWED_DEVICE_NUMBER_MESSAGE);

		gateway.addPeripheralDevice(new PeripheralDevice(newDevice.getVendor(), newDevice.getStatus()));
		return gatewayRepo.save(gateway);

	}

	@Override
	public void deleteById(String serial) throws NotFoundException {
		findById(serial);
		gatewayRepo.deleteById(serial);

	}

	@Override
	public void deleteDeviceFromGatewayById(String serial, Integer uid) throws NotFoundException {
		var gateway = findById(serial);

		var notRemoved = true;
		for (PeripheralDevice peripheralDevice : gateway.getPeripheralDevices()) {
			if (peripheralDevice.getUid().equals(uid)) {
				gateway.removePeripheralDevice(peripheralDevice);
				notRemoved = false;
				break;
			}
		}

		if (notRemoved)
			throw new NotFoundException(DEVICE_NOT_FOUND_MESSAGE + uid);
		gatewayRepo.save(gateway);
	}

	private boolean notValidIPV4Address(String ip) {
		if (ip == null)
			return true;

		String[] groups = ip.split("\\.");

		if (groups.length != 4) {
			return true;
		}

		try {
			return Arrays.stream(groups).filter(s -> s.length() >= 1).map(Integer::parseInt)
					.filter(i -> (i >= 0 && i <= 255)).count() != 4;
		} catch (NumberFormatException e) {
			return true;
		}

	}

	@Override
	public Gateway replaceGateway(Gateway gateway, String serial) throws NotAllowedDataException {

		boolean isNewGatewayHasPeripheralDevices = false;

		// check if the new gateway has a valid ip address
		if (notValidIPV4Address(gateway.getIpAddress()))
			throw new NotAllowedDataException(INVALID_IP_MESSAGE + gateway.getIpAddress());

		// check if the new gateway has a Peripheral Devices
		if (null != gateway.getPeripheralDevices()) {

			if (gateway.getPeripheralDevices().size() > 10)
				throw new NotAllowedDataException(MAXIMUM_ALLOWED_DEVICE_NUMBER_MESSAGE);

			// set the flag newGatewayHasPeripheralDevices to true
			isNewGatewayHasPeripheralDevices = true;
		}
		// check if there an old gateway with serial number {serial}
		Gateway oldGateway = findById(serial);

		// if there is no old gateway with this serial
		if (null == oldGateway) {
			// add the new gateway with the new serial
			gateway.setSerialNum(serial);

			// if the new gateway has Peripheral Devices
			if (isNewGatewayHasPeripheralDevices) {

				// update the gateway in each Peripheral Device
				for (PeripheralDevice peripheralDevice : gateway.getPeripheralDevices()) {
					peripheralDevice.setGateway(gateway);
				}

				// set the PeripheralDevices in the gateway
				gateway.setPeripheralDevices(gateway.getPeripheralDevices());
			}

			return gatewayRepo.save(gateway);
		}

		// if there are an old gateway (mean old gateway not null )
		else {

			// update the old gateway with the new gateway and new serial
			oldGateway.setIpAddress(gateway.getIpAddress());
			oldGateway.setName(gateway.getName());
			oldGateway.setSerialNum(serial);

			// if the new gateway has Peripheral Devices
			if (isNewGatewayHasPeripheralDevices) {

				// update the gateway in each Peripheral Device
				for (PeripheralDevice peripheralDevice : gateway.getPeripheralDevices()) {
					peripheralDevice.setGateway(oldGateway);
				}

				// set the PeripheralDevices in the gateway
				oldGateway.setPeripheralDevices(gateway.getPeripheralDevices());
			}
			return gatewayRepo.save(oldGateway);

		}

	}

}
