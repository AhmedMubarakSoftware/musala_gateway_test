package com.musala.ahmedTest.practicalTask.dtos;

import com.musala.ahmedTest.practicalTask.enums.DeviceStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data

public class PeripheralDeviceDTO {


	private String vendor;

	private DeviceStatus status;

}
