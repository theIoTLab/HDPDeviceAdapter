/**
 * Copyright (C) 2014 Consorzio Roma Ricerche
 * All rights reserved
 *
 * This file is part of the Protocol Adapter software, available at
 * https://github.com/theIoTLab/ProtocolAdapter .
 *
 * The Protocol Adapter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://opensource.org/licenses/LGPL-3.0
 *
 * Contact Consorzio Roma Ricerche (protocoladapter@gmail.com)
 */

package eu.fistar.sdcs.pa.da.bthdp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;

import eu.fistar.sdcs.pa.common.Capabilities;
import eu.fistar.sdcs.pa.common.DeviceDescription;
import eu.fistar.sdcs.pa.common.IDeviceAdapterListener;
import eu.fistar.sdcs.pa.common.Observation;
import eu.fistar.sdcs.pa.common.PAAndroidConstants;
import eu.fistar.sdcs.pa.common.da.IDeviceAdapter;
import eu.fistar.sdcs.pa.da.bthdp.hdpservice.HDPHealthManagerService;
import eu.fistar.sdcs.pa.da.bthdp.hdpservice.HealthAgentAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the HDP Device Adapter. It is a bound service which can be bound from the
 * Protocol Adapter, implements the Device Adapter interface and takes care of communicating with
 * and managing all the HDP connectedDevices. It makes use of a ISO 11073 Manager (which is a modified
 * version of Antidote, developed by Signove) to communicate with the HDP Devices.
 *
 * @author Marcello Morena
 * @author Alexandru Serbanati
 */
public class HDPDeviceAdapter extends Service implements HealthAgentAPI {

    private final static String SERVICE_HDP_MANAGER_NAME = "eu.fistar.sdcs.pa.da.bthdp.hdpservice.HDPHealthManagerService";
    private final static String LOGTAG_HDP_SERVICE = "HDP >>>";
    private final static String METHOD_NOT_SUPPORTED = "Method not supported by Device Adapter!";

    private HDPHealthManagerService hdpApi;
    private IDeviceAdapterListener paApi;

    private Handler handler = new Handler();
    private final Map<String, HDPDevice> connectedDevices = new HashMap<String, HDPDevice>();

    /**
     * The HDPBinder to pass to the HDPHealthManagerService
     */
    private final HDPBinder hdpEndpoint = new HDPBinder();

    /**
     * Implementation of the Device Adapter API (IDeviceAdapter) to pass to the Protocol Adapter
     */
    private final IDeviceAdapter.Stub paEndpoint = new IDeviceAdapter.Stub() {

        /**
         * Returns the list of all the connectedDevices that are actually connected to the Device Adapter
         *
         * @return The list of all the device active now inside the Device Adapter
         */
        @Override
        public List<DeviceDescription> getConnectedDevices() {
            return new ArrayList<DeviceDescription>(connectedDevices.values());
        }


        /**
         * Set the Protocol Adapter API endpoint
         *
         * @param pa The Protocol Adapter Binder
         */
        @Override
        public void registerDAListener(IBinder pa) {
            paApi = IDeviceAdapterListener.Stub.asInterface(pa);
        }

        @Override
        public List<String> getPairedDevicesAddress() throws RemoteException {
            throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
        }

        @Override
        public List<String> detectDevices() throws RemoteException {
            throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
        }

        @Override
        public void setDeviceConfig(Map map, String s) throws RemoteException {
            throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
        }

        @Override
        public Capabilities getDACapabilities() throws RemoteException {
            return DiscoveryResponder.CAPABILITIES;
        }

        /**
         * Start the Device Adapter operations.
         */
        @Override
        public void start() throws RemoteException {
            // Create the Intent to start the service, start the service and bind to it
            Intent intent = new Intent(getApplicationContext(), HDPHealthManagerService.class);

            Log.d(LOGTAG_HDP_SERVICE, "Connecting to HDP Manager Service");
            bindService(intent, serviceHDPManagerConnection, Context.BIND_AUTO_CREATE);
        }

        /**
         * Stop the Device Adapter operations. This will not close or disconnect the service.
         */
        @Override
        public void stop() throws RemoteException {
            try {
                // Deregister the application
                hdpApi.Unconfigure(hdpEndpoint);
            } catch (Throwable t) {
            }
            // Unbind from the service
            unbindService(serviceHDPManagerConnection);
        }

        @Override
        public void connectDev(String s) throws RemoteException {
            throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
        }

        @Override
        public void forceConnectDev(String s) throws RemoteException {
            throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
        }

        @Override
        public void disconnectDev(String s) throws RemoteException {
            throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
        }

        @Override
        public void addDeviceToWhitelist(String s) throws RemoteException {
            throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
        }

        @Override
        public void removeDeviceFromWhitelist(String s) throws RemoteException {
            throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
        }

        @Override
        public List<String> getWhitelist() throws RemoteException {
            throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
        }

        @Override
        public void setWhitelist(List<String> strings) throws RemoteException {
            throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
        }

        @Override
        public void addDeviceToBlackList(String s) throws RemoteException {
            throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
        }

        @Override
        public void removeDeviceFromBlacklist(String s) throws RemoteException {
            throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
        }

        @Override
        public List<String> getBlacklist() throws RemoteException {
            throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
        }

        @Override
        public void setBlackList(List<String> strings) throws RemoteException {
            throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
        }

        @Override
        public List<String> getCommandList() throws RemoteException {
            throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
        }

        @Override
        public void execCommand(String s, String s2, String s3) throws RemoteException {
            throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
        }
    };

    /**
     * Implementation of the ServiceConnection object used to
     * handle the connection to the service
     */
    private ServiceConnection serviceHDPManagerConnection = new ServiceConnection() {

        /**
         * Called when connected to {@link eu.fistar.sdcs.pa.da.bthdp.hdpservice.HDPHealthManagerService}
         *
         * @param name
         *      Component Name of the service
         * @param service
         *      The binder returned by the service
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Log.w(LOGTAG_HDP_SERVICE, "Service connected");

            // Get the object to use the API of the service
            hdpApi = ((HDPHealthManagerService.HDPManagerBinder) service).getHealthManager();

            // Call the method to initiate the communication with the HDP device
            hdpApi.ConfigurePassive(hdpEndpoint, null);
        }

        /**
         * Gracefully handle the service disconnection
         *
         * @param name
         *      Component Name of the service
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.w(LOGTAG_HDP_SERVICE, "Service connection closed");
        }
    };


    /*******************************************************************/
    /********************* HEALTH AGENT API BEGIN **********************/
    /*******************************************************************/
    /* Implementation of the HealthAgentAPI, which is needed to allow
     * the service to contact back this activity and notify it of some
     * interesting situations, such as Connection, Association,
     * Disassociation, etc, of a device. Health Agent is the user of
     * the service and should not be confused with ISO 11073 Agent.
     */

    /**
     * Called back by the ISO 11073 Manager when a device is connected. Create a new HDPDevice
     * and put it in the HashMap
     *
     * @param dev  The String that identifies the device inside the ISO 11073 Manager
     * @param addr The MAC address of the bluetooth device that has just connected
     */
    public void Connected(String dev, String addr) {
        Log.d(LOGTAG_HDP_SERVICE, "Device " + dev + " connected");
        connectedDevices.put(dev, new HDPDevice(addr));
    }

    /**
     * Called back by the ISO 11073 Manager when a device is associated
     *
     * @param dev          The String that identifies the device inside the ISO 11073 Manager
     * @param assocXmlData The raw XML data generated by the ISO 11073 Manager containing data from the device
     */
    public void Associated(String dev, String assocXmlData) {

        Log.d(LOGTAG_HDP_SERVICE, "Device " + dev + " associated");

        final String idev = dev;

        // Prepare the request for the device attributes
        Runnable req = new Runnable() {
            public void run() {
                RequestDeviceAttributes(idev);
            }
        };

        // Enqueue the requests in the Handler
        handler.postDelayed(req, 500);
    }

    /**
     * Called back by the ISO 11073 Manager when the the measurement data is returned
     *
     * @param dev                The String that identifies the device inside the ISO 11073 Manager
     * @param measurementXmlData The raw XML data generated by the ISO 11073 Manager containing data from the device
     */
    public void MeasurementData(String dev, String measurementXmlData) {

        Log.d(LOGTAG_HDP_SERVICE, "Received measurement from device " + dev);

        // Retrieve the device from the list
        HDPDevice hdpDev = connectedDevices.get(dev);

        if (hdpDev != null && hdpDev.isRegistered()) {
            // Parse XML measurement data
            List<HDPObservation> hdpObservations = HDPXMLUtils.parseMeasurementData(hdpDev, measurementXmlData);
            List<Observation> observations = new ArrayList<Observation>();

            // Create a log string with all the measurements and create a new array of Observation
            String obsStr = "";
            for (HDPObservation tmpObs : hdpObservations) {
                // It's required to create a new objects of type Observation for AIDL to work correctly
                Observation newObs = new Observation(tmpObs);

                // Concatenate the String representing the Observation
                obsStr += newObs.toString();

                // Add the newly created object to the Observation List
                observations.add(newObs);
            }
            Log.d(PAAndroidConstants.DA_LOGTAG, "Device: " + hdpDev.toString() + "\nObservations:\n" + obsStr + "\n");

            // Push measurements to Protocol Adapter
            try {
                // It's required to create a new object of type DeviceDescription for AIDL to work correctly
                paApi.pushData(observations, new DeviceDescription(hdpDev));

            } catch (RemoteException e) {
                Log.d(PAAndroidConstants.DA_LOGTAG, "Remote Exception!");
            }
        }

    }

    /**
     * Called back by the ISO 11073 Manager when device attributes are received
     *
     * @param dev         The String that identifies the device inside the ISO 11073 Manager
     * @param attrXmlData The raw XML data generated by the ISO 11073 Manager containing data from the device
     */
    public void DeviceAttributes(String dev, String attrXmlData) {

        Log.v(LOGTAG_HDP_SERVICE, "Received attributes from device " + dev);

        String confXmlData;
        HDPDevice hdpDev;

        // Call the API method to retrieve the configuration
        confXmlData = hdpApi.GetConfiguration(dev);
        Log.v(LOGTAG_HDP_SERVICE, "Got configuration from device " + dev);

        hdpDev = connectedDevices.get(dev);

        // Parse attributes and configuration data and populate the device object
        HDPXMLUtils.parseAttrData(hdpDev, attrXmlData);
        HDPXMLUtils.parseConfData(hdpDev, confXmlData);

        // Register the device to the Protocol Adapter
        Log.v(PAAndroidConstants.DA_LOGTAG, hdpDev.toString());
        try {
            // It's required to create a new object of type DeviceDescription for AIDL to work correctly
            paApi.registerDevice(new DeviceDescription(hdpDev), DiscoveryResponder.CapabilitiesConstants.DA_ID);
        } catch (RemoteException e) {
            Log.d(PAAndroidConstants.DA_LOGTAG, "Remote Exception!");
        }

        // Mark the device as registered
        hdpDev.setRegistered(true);

    }

    /**
     * Called back by the ISO 11073 Manager when a device is disassociated
     *
     * @param dev The String that identifies the device inside the ISO 11073 Manager
     */
    public void Disassociated(String dev) {
        Log.d(LOGTAG_HDP_SERVICE, "Device " + dev + " disassociated");
    }

    /**
     * Called back by the ISO 11073 Manager when a device is disconnected
     *
     * @param dev The String that identifies the device inside the ISO 11073 Manage
     */
    public void Disconnected(String dev) {

        HDPDevice hdpDev;

        // Remove device from HashMap
        hdpDev = connectedDevices.remove(dev);

        if (hdpDev != null) {
            // Deregister device from Protocol Adapter
            try {
                // It's required to create a new object of type DeviceDescription for AIDL to work correctly
                paApi.deviceDisconnected(new DeviceDescription(hdpDev));
            } catch (RemoteException e) {
                Log.d(PAAndroidConstants.DA_LOGTAG, "Remote Exception!");
            }

            // Mark the device as unregistered
            hdpDev.setRegistered(false);

            Log.d(LOGTAG_HDP_SERVICE, "Device " + dev + " disconnected");
        }
    }

    /*******************************************************************/
    /********************** HEALTH AGENT API END ***********************/
    /*******************************************************************/


    /**
     * Request the device attributes from the device attributes
     *
     * @param dev The String that identifies the device inside the ISO 11073 Manage
     */
    private void RequestDeviceAttributes(String dev) {
        // Call the API method to request the device attributes
        hdpApi.RequestDeviceAttributes(dev);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return paEndpoint;
    }

    /**
     * This class is used to facilitate communication between the HDPHealthManagerService and the
     * HDPDeviceAdapter, allowing the HDPHealthManagerService to retrieve the Device Adapter's
     * instance
     */
    public class HDPBinder extends Binder {

        public HDPDeviceAdapter getDeviceAdapter() {
            // Return this instance of the HDPHealthManagerService
            return HDPDeviceAdapter.this;
        }
    }

}
