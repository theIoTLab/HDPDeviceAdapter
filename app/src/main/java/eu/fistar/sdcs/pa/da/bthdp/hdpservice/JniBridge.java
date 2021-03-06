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

package eu.fistar.sdcs.pa.da.bthdp.hdpservice;

/**
 * This class is the JNI Bridge between the healthd daemon (written in C, compiled with the NDK)
 * implementing the ISO 11073 Manager, and the rest of the Android code that takes care of
 * providing Bluetooth HDP communication channel and interaction with the rest of the system.
 *
 * @author Marcello Morena
 * @author Alexandru Serbanati
 */
public class JniBridge {
	HDPHealthManagerService cb;
	boolean finalized;

	JniBridge(HDPHealthManagerService pcb) {
		cb = pcb;
		finalized = false;
		healthd_init();
	}

	protected void finalize() {
		if (! finalized) {
			healthd_finalize();
			finalized = true;
		}
	}

	// communication part: called from C
	public void disconnect_channel(int context)
	{
		cb.disconnect_channel(context);
	}

	public void send_data(int context, byte [] data)
	{
		cb.send_data(context, data);
	}

	// communication part: called from service
	public synchronized void channel_connected(int context) {
		Cchannelconnected(context);
	}

	public synchronized void channel_disconnected(int context) {
		Cchanneldisconnected(context);
	}

	public synchronized void data_received(int context, byte [] data)
	{
		Cdatareceived(context, data);
	}

	public native void Cchannelconnected(int context);
	public native void Cchanneldisconnected(int context);
	public native void Cdatareceived(int context, byte [] data);

	// manager part: called from C
	public void cancel_timer(int handle)
	{
		cb.cancel_timer(handle);
	}

	public int create_timer(int milisseconds, int handle)
	{
		return cb.create_timer(milisseconds, handle);
	}

	public void associated(int context, String xml)
	{
		cb.associated(context, xml);
	}

	public void disassociated(int context)
	{
		cb.disassociated(context);
	}

	public void deviceattributes(int context, String xml)
	{
		cb.deviceattributes(context, xml);
	}

	public void measurementdata(int context, String xml)
	{
		cb.measurementdata(context, xml);
	}

	// manager part: called from service
	public synchronized void timer_alarm(int handle)
	{
		Ctimeralarm(handle);
	}

	public synchronized void healthd_init()
	{
		Chealthdinit(cb.getApplicationContext().getFilesDir().toString());
	}

	public synchronized void healthd_finalize()
	{
		Chealthdfinalize();
	}

	public synchronized void releaseassoc(int context)
	{
		Creleaseassoc(context);
	}

	public synchronized void abortassoc(int context)
	{
		Cabortassoc(context);
	}

	public synchronized String getconfig(int context)
	{
		return Cgetconfig(context);
	}

	public synchronized void reqmdsattr(int context)
	{
		Creqmdsattr(context);
	}

	public synchronized void reqactivationscanner(int context, int handle)
	{
		Creqactivationscanner(context, handle);
	}

	public synchronized void reqdeactivationscanner(int context, int handle)
	{
		Creqdeactivationscanner(context, handle);
	}

	public synchronized void reqmeasurement(int context)
	{
		Creqmeasurement(context);
	}

	public native void Ctimeralarm(int handle);
	public native void Chealthdinit(String tmp_path);
	public native void Chealthdfinalize();
	public native void Creleaseassoc(int context);
	public native void Cabortassoc(int context);
	public native String Cgetconfig(int context);
	public native void Creqmdsattr(int context);
	public native void Creqactivationscanner(int context, int handle);
	public native void Creqdeactivationscanner(int context, int handle);
	public native void Creqmeasurement(int context);

	static {
		System.loadLibrary("healthd");
	}
}
