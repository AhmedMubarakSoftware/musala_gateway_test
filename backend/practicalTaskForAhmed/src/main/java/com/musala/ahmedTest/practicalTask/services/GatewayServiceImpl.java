package com.musala.ahmedTest.practicalTask.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musala.ahmedTest.practicalTask.dtos.GatewayDTO;
import com.musala.ahmedTest.practicalTask.dtos.PeripheralDeviceDTO;
import com.musala.ahmedTest.practicalTask.exceptions.NotAllowedDataException;
import com.musala.ahmedTest.practicalTask.exceptions.NotFoundException;
import com.musala.ahmedTest.practicalTask.models.Gateway;
import com.musala.ahmedTest.practicalTask.models.PeripheralDevice;
import com.musala.ahmedTest.practicalTask.repos.GatewayRepo;

@Service
public class GatewayServiceImpl implements IGatewayService {

	@Autowired
	ModelMapper modelMapper;

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
	public Gateway addGateway(GatewayDTO gatewayDTO) throws NotAllowedDataException {

		Gateway gateway = modelMapper.map(gatewayDTO, Gateway.class);

		Optional<Gateway> oldGatewayOptional = gatewayRepo.findById(gateway.getSerialNum());

		// if there is an old gateway with this serial
		if (oldGatewayOptional.isPresent())
			throw new NotAllowedDataException(SERIAL_USED_BEFORE_MESSAGE + gateway.getSerialNum());

//		if (null != gateway.getPeripheralDevices()) {
//			if (gateway.getPeripheralDevices().size() > 10)
//				throw new NotAllowedDataException(MAXIMUM_ALLOWED_DEVICE_NUMBER_MESSAGE);
//
//			for (PeripheralDevice peripheralDevice : gateway.getPeripheralDevices()) {
//				peripheralDevice.setGateway(gateway);
//			}
//			gateway.setPeripheralDevices(gateway.getPeripheralDevices());
//		}
		return gatewayRepo.save(gateway);

	}

	@Override
	public Gateway addDevice(PeripheralDeviceDTO peripheralDeviceDTO, String serial)
			throws NotAllowedDataException, NotFoundException {
		PeripheralDevice newDevice = modelMapper.map(peripheralDeviceDTO, PeripheralDevice.class);

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

	public Gateway update(GatewayDTO gatewayDTO, String serial) throws NotAllowedDataException {
		Gateway gateway = modelMapper.map(gatewayDTO, Gateway.class);

		Gateway oldGateway = findById(serial);
		// update the old gateway with the new gateway and new serial
		oldGateway.setIpAddress(gateway.getIpAddress());
		oldGateway.setName(gateway.getName());
		oldGateway.setSerialNum(serial);

		// if the new gateway has Peripheral Devices
		if (gateway.getPeripheralDevices() != null && !gateway.getPeripheralDevices().isEmpty()) {

			// update the gateway in each Peripheral Device
			gateway.getPeripheralDevices().stream().forEach(peripheralDevice -> peripheralDevice.setGateway(gateway));
			// set the PeripheralDevices in the gateway
			oldGateway.setPeripheralDevices(gateway.getPeripheralDevices());
		}
		return gatewayRepo.save(oldGateway);

	}

}
