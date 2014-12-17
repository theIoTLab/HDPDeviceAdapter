#HDP Bluetooth Device Adapter

FI-STAR Phase: Beta  
Document version: 0.9 (draft)  
Date: 15/12/2014  

##The Protocol Adapter architecture
The Protocol Adapter is an M2M data collection software that runs on Android (mainly mobile) devices acting as a gateway for sensor devices. The Protocol Adapter was developed as an open source component of the FI-STAR Frontend Platform, in the frame of the FI-STAR project.
The Protocol Adapter software architecture has three high-level components:

* the Protocol Adapter Manager,
* the Device Adapter and
* the Protocol Adapter Library

###The Protocol Adapter Manager
It includes a Protocol Adapter Manager (PAManagerService) service and several Device Adapters (DA) on the same Android device. All of them are implemented in separate Android applications and communicate using the AIDL interfaces and common objects included in a separate library. The Protocol Adapter automatically discovers DAs present on the system at startup time and adds them to the pool of available DAs. This makes the architecture modular and expandable.
The PAManagerService has three main roles:

* to provide a single entry point for data-collection applications
* to provide device management interfaces for the application
* to manage the lifecycle of the DAs.

###The Device Adapter
A Device Adapter is a software component that manages low-level connections to sensor devices of a given kind and collects data from them. The collected data resulting from the measurements carried out by the sensor devices are provided to the Protocol Adapter with a well-known data structure (i.e. Java object) called Observation.
Generally, DAs provide communication and interoperability at channel and syntactic level. Some operational aspects are also managed by the DA with sensor devices.

###The Protocol Adapter Library
The Protocol Adapter library is a library that contains all the objects and facilities (parcelable objects, AIDL interfaces, etc.) needed to develop applications that make use of the Protocol Adapter. Once included in your project, you won’t need to worry about low level details, but instead you can focus on implementing your logic, taking for granted the underlying infrastructure and functionalities provided by the Protocol Adapter. Please, note that the library is not only for using in applications, but it is also used by us in the Protocol Adapter and in every Device Adapter. We released this library in the form of an AAR package.

##Description of the HDP Bluetooth Device Adapter
The HDP Bluetooth Device Adapter is a Device Adapter that handles devices supporting the Bluetooth Health Device Profile. These devices also implement the ISO 11073 family standards promoted by Continua Alliance.

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