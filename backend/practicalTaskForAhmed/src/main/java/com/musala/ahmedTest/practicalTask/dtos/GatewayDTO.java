package com.musala.ahmedTest.practicalTask.dtos;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.musala.ahmedTest.practicalTask.services.IGatewayService;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GatewayDTO {

	private String serialNum;
	@NotEmpty(message = IGatewayService.INVALID_GATEWAY_NAME_MESSAGE)
	private String name;
	@Pattern(regexp = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])"+
	"\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"
			, message = IGatewayService.INVALID_IP_MESSAGE)
	private String ipAddress;
	@Max(value = 10 ,message = IGatewayService.MAXIMUM_ALLOWED_DEVICE_NUMBER_MESSAGE)
	private List<PeripheralDeviceDTO> peripheralDevices;

}
