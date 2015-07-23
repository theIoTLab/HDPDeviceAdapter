#HDP Bluetooth Device Adapter

FI-STAR Phase: Beta  
Document version: 1.0 (draft)
Date: 05/03/2015

##What is the HDP Bluetooth Device Adapter
The HDP Bluetooth Device Adapter is a Device Adapter, a component of the Protocol Adapter (more about the Protocol Adapter [here](https://github.com/theIoTLab/ProtocolAdapterManager/blob/master/Protocol%20Adapter%20Guide.md)). It handles devices supporting the Bluetooth Health Device Profile. These devices also implement the ISO 11073 family standards promoted by Continua Alliance.

##Initial operations
Before you can start using the Device Adapter, there are some initial operations that you are required to perform.

###Installation of the Protocol Adapter
The first step for using the Device Adapter is installing the Protocol Adapter on the Android device where you are going to use it. To do this, just manually install the Protocol Adapter APK in the system.
The Protocol Adapter by itself is pretty useless, so you should install at least a Device Adapter, specifically the one (or ones) that handles the devices you want to work with.

###Installation of the HDP Bluetooth Device Adapter
To install the Device Adapter in your system, just manually install in the system the appropriate APK.

###First start
On startup, the Protocol Adapter performs a probe scan to discover all the Device Adapters on the system. Due to Android security policies, Device Adapters will not respond to Protocol Adapter’s probing (and therefore are not usable) unless they are manually started once after installation. To do this, just go to your Drawer (the list of installed applications), find the icon of the Device Adapter you just installed and tap on it. After this first time, there is no need to manually start the Device Adapters again.

###Devices
The HDP Bluetooth Device Adapter works only with Bluetooth devices. However, due to Android restrictions, pairing is not provided by the Device Adapter. So, for you to be able to use a particular device, you have to manually pair the device and the smartphone before trying to use them together.

##Supported Features
The HDP Bluetooth Device Adapter has, just like other Device Adapters, a list of Capabilities. Here you have them summarized:

* **Support for Blacklist**: NO - You **CANNOT** put some devices in blacklist to avoid unwanted connections
* **Support for Whitelist**: NO - You **CANNOT** put some devices in whitelist to connect only to trusted devices
* **Support for configuration via a GUI**: NO - You **CANNOT** configure this Device Adapter via the Protocol Adapter GUI configuration facility
* **Support for devices configuration**: NO - You **CANNOT** set the configuration parameters for the devices
* **Support for sending commands to devices**: NO - You **CANNOT** send commands to devices
* **Support for devices detection and discovery**: NO - You **CANNOT** perform a discovery for nearby devices
* **Previous pairing needed**: YES - You **HAVE TO** perform the pairing between the smartphone and the device you are going to use, before you can use it
* **Can monitor device disconnection**: NO - You **CANNOT** be notified of device's disconnection
* **Is connection initiator**: NO - The Device Adapter **RECEIVE** the connection request from devices, so you **MUST NOT** manually perform a connection with the device
* **Support for available devices**: NO - You **CANNOT** retrieve the list of devices paired and managed by the Device Adapter

##Supported Commands
Command sending is **NOT** supported by this Device Adapter.

##Configuration Parameters
Device configuration is **NOT** supported by this Device Adapter.

## Authors, Contact and Contributions
As the licence reads, this is free software released by Consorzio Roma Ricerche. The authors (Marcello Morena and Alexandru Serbanati) will add over time support for even more devices, but external contributions are welcome. Please have a look at the TODO file about to what we are working on and feel free to contact us directly or at protocoladapter[at]gmail[dot]com if you plan on contributing.

## Acknowledgement
This work was carried out with the support of the FI-STAR project (“Future Internet Social and Technological Alignment Research”), an integrated project funded by the European Commission through the 7th ICT - Framework Programme (318389).