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

import java.util.Arrays;
import java.util.List;

import eu.fistar.sdcs.pa.common.Observation;

/**
 * This class represents an Observation, that is a a collection of the values observed for the
 * property, taken in a certain amount of time
 *
 * @author Marcello Morena
 * @author Alexandru Serbanati
 */
public class HDPObservation extends Observation {

    private String propertyName;    // Property name (same as propertyName in ISensorDescription)
    private String measurementUnit; // Unit of measure of the property
    private List<String> values;    // Values observed for the property
    private long phenomenonTime;    // Timestamp associated with the measurement
    private long duration;          // Duration of the measurement [WARNING: for now it's always 0]

    public HDPObservation(HDPSensor mSensor, String[] mValues) {
        propertyName = mSensor.getPropertyName();
        measurementUnit = mSensor.getMeasurementUnit();
        phenomenonTime = System.currentTimeMillis();
        duration = 0;
        values = Arrays.asList(mValues);
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public List<String> getValues() {
        return values;
    }

    @Override
    public long getPhenomenonTime() {
        return phenomenonTime;
    }

    @Override
    public long getDuration() {
        return duration;
    }

    @Override
    public String getMeasurementUnit() {
        return measurementUnit;
    }

    @Override
    public void setProperty(String mPropertyName) {
        propertyName = mPropertyName;
    }

    @Override
    public void setDuration(long mDuration) {
        duration = mDuration;
    }

    @Override
    public void setMeasurementUnit(String mMeasurementUnit) {
        measurementUnit = mMeasurementUnit;
    }

    @Override
    public void setValues(String[] mValues) {
        values = Arrays.asList(mValues);
    }

    @Override
    public void setPhenomenonTime(long mPhenomenonTime) {
        phenomenonTime = mPhenomenonTime;
    }

    /**
     * Returns a read-friendly String representing the object
     *
     * @return
     *      The String representing the object
     */
    public String toString() {
        return "Property Name: "+propertyName+"\nMeasurement Unit: "+measurementUnit+"\nTime: "+
                phenomenonTime+"\nDuration: "+duration+"\nValue: "+values.get(0)+"\n";
    }
}
