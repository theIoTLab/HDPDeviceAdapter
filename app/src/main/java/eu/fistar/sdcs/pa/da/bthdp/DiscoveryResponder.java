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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import eu.fistar.sdcs.pa.common.Capabilities;
import eu.fistar.sdcs.pa.common.PAAndroidConstants;

/**
 * This class implements the responder to the discovery performed by Protocol Adapter
 *
 * @author Marcello Morena
 * @author Alexandru Serbanati
 */
public class DiscoveryResponder extends BroadcastReceiver {

    // Create the Capabilities object of the Device Adapter
    public final static Capabilities CAPABILITIES = new Capabilities(
            CapabilitiesConstants.CAP_BLACKLIST_SUPPORT,
            CapabilitiesConstants.CAP_WHITELIST_SUPPORT,
            CapabilitiesConstants.CAP_GUICONFIGURATION_ACTIVITY,
            CapabilitiesConstants.CAP_GUICONFIGURATION_ACTIVITY_PACKAGE,
            CapabilitiesConstants.CAP_DEV_CONF_TYPE,
            CapabilitiesConstants.CAP_COMMANDS_SUPPORT,
            CapabilitiesConstants.CAP_DETECT_DEV_SUPPORT,
            CapabilitiesConstants.CAP_PREVIOUS_PAIRING_NEEDED,
            CapabilitiesConstants.CAP_MONITOR_DISCONN_SUPPORT,
            CapabilitiesConstants.CAP_FRIENDLY_NAME,
            CapabilitiesConstants.CAP_ACTION_NAME,
            CapabilitiesConstants.CAP_PACKAGE_NAME,
            CapabilitiesConstants.CAP_CONNECTION_INITIATOR,
            CapabilitiesConstants.CAP_AVAILABLE_DEVICES_SUPPORT
    );

    @Override
    public void onReceive(Context context, Intent intent) {
        // Extract reply action from the received Intent
        String replyAction = intent.getStringExtra(PAAndroidConstants.DA_DISCOVERY.BUNDLE_REPACT);

        // Create a new Intent using the retrieved Reply Action
        Intent replyIntent = new Intent();
        replyIntent.setAction(replyAction);

        // Limit the intent to the package of PA
        replyIntent.setPackage(PAAndroidConstants.PA_PACKAGE);

        // Set the DA ID and DA Capabilities as extras in the Intent
        replyIntent.putExtra(PAAndroidConstants.DA_DISCOVERY.BUNDLE_DAID, CapabilitiesConstants.DA_ID);
        replyIntent.putExtra(PAAndroidConstants.DA_DISCOVERY.BUNDLE_DACAP, CAPABILITIES);

        // Reply to the Discovery Request sent by the Protocol Adapter
        context.sendBroadcast(replyIntent);
    }

    /**
     * Class containing only the Capabilities related constants
     */
    static class CapabilitiesConstants {

        // Generic Constants
        public static final String DA_ID = "eu.fistar.sdcs.pa.da.bthdp";

        // TODO Expand the capabilities as much as possible (if possible) in the final version
        // Capabilities constants, specific for each DA
        public static final boolean CAP_BLACKLIST_SUPPORT = false;
        public static final boolean CAP_WHITELIST_SUPPORT = false;
        public static final String CAP_GUICONFIGURATION_ACTIVITY = "";
        public static final String CAP_GUICONFIGURATION_ACTIVITY_PACKAGE = "";
        public static final int CAP_DEV_CONF_TYPE = Capabilities.CONFIG_NOT_SUPPORTED;
        public static final boolean CAP_COMMANDS_SUPPORT = false;
        public static final boolean CAP_DETECT_DEV_SUPPORT = false;
        public static final boolean CAP_PREVIOUS_PAIRING_NEEDED = true;
        public static final boolean CAP_MONITOR_DISCONN_SUPPORT = true;
        public static final String CAP_FRIENDLY_NAME = "HDP Bluetooth Device Adapter";
        public static final String CAP_ACTION_NAME = DA_ID + ".HDPDeviceAdapter";
        public static final String CAP_PACKAGE_NAME = DA_ID;
        public static final boolean CAP_CONNECTION_INITIATOR = false;
        public static final boolean CAP_AVAILABLE_DEVICES_SUPPORT = false;

    }
}
