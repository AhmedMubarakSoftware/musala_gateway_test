import { DeviceStatus } from "./DeviceStatus";
import { PeripheralDevice } from "./PeripheralDevice";

export interface Gateway
{
     serialNum:string ;
     name:string;
     ipAddress:DeviceStatus ;
     peripheralDevices:Array<PeripheralDevice>;

}