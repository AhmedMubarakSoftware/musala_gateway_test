package com.musala.ahmedTest.practicalTask.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GATEWAY")
@Data
@Entity
public class Gateway  {
	 
    //a unique serial number (string)
	@Id 
	@Column(unique=true ,name = "SERIAL_NUM")
    private String serialNum ;
    
    //human-readable name (string)
	@Column(name = "NAME")
    private String name ;

    // IPv4 address (to be validated)
	@Column(name = "IP_ADDRESS")
    private String ipAddress ;

    //multiple associated peripheral devices. 
	  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "gateway", orphanRemoval = true)
	  private List<PeripheralDevice> peripheralDevices ;

	public Gateway addPeripheralDevice(PeripheralDevice peripheralDevice) {
		peripheralDevices.add(peripheralDevice);
		peripheralDevice.setGateway(this);
		return this;
	}

	public Gateway removePeripheralDevice(PeripheralDevice peripheralDevice) {
		peripheralDevices.remove(peripheralDevice);
		peripheralDevice.setGateway(null);
	        return this;

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Gateway other = (Gateway) obj;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (peripheralDevices == null) {
			if (other.peripheralDevices != null)
				return false;
		} else if (!peripheralDevices.equals(other.peripheralDevices))
			return false;
		if (serialNum == null) {
			if (other.serialNum != null)
				return false;
		} else if (!serialNum.equals(other.serialNum))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 37;
		int result = 1;
		result = prime * result + ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((peripheralDevices == null) ? 0 : peripheralDevices.hashCode());
		result = prime * result + ((serialNum == null) ? 0 : serialNum.hashCode());
		return result;
	}
	
//	public String getSerialNum() {
//		return serialNum;
//	}
//
//	public void setSerialNum(String serialNum) {
//		this.serialNum = serialNum;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getIpAddress() {
//		return ipAddress;
//	}
//
//	public void setIpAddress(String ipAddress) {
//		this.ipAddress = ipAddress;
//	}
//
//	public List<PeripheralDevice> getPeripheralDevices() {
//		return peripheralDevices;
//	}
//
//	public void setPeripheralDevices(List<PeripheralDevice> peripheralDevices) {
//		this.peripheralDevices = peripheralDevices;
//	}
	
	
}
