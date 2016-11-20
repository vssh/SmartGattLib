package com.movisens.smartgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;
import com.movisens.smartgattlib.GattUtils;

/**
 * Created by varun on 20.11.16.
 */

public class RunningSpeedCadenceMeasurement {

    public static final int MAX_CUMULATIVE_CRANK_REVS = 65535;
    public static final long MAX_CUMULATIVE_WHEEL_REVS = 4294967295L;

    private boolean instantaneousStrideLengthPresent;
    private boolean totalDistancePresent;
    private boolean isRunning;

    private float instantaneousSpeed;            //in m/s²
    private int instantaneousCadence;            //per minute
    private int instantaneousStrideLength;      //in cm
    private long totalDistance;                 //in m

    public RunningSpeedCadenceMeasurement(byte[] value) {

        GattByteBuffer bb = GattByteBuffer.wrap(value);

        byte flags = bb.getInt8();
        instantaneousStrideLengthPresent = instantaneousStrideLengthPresent(flags);
        totalDistancePresent = totalDistancePresent(flags);
        isRunning = getRunning(flags);

        instantaneousSpeed = bb.getUint16() / 256F;
        instantaneousCadence = bb.getUint8();

        if (instantaneousStrideLengthPresent) {
            instantaneousStrideLength = bb.getUint16();
        }

        if (totalDistancePresent) {
            totalDistance = bb.getUint32() / 10;
        }
    }

    public boolean isStrideLengthPresent() {
        return instantaneousStrideLengthPresent;
    }

    public boolean isTotalDistancePresent() {
        return totalDistancePresent;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public float getInstantaneousSpeed() {
        return instantaneousSpeed;
    }

    public int getInstantaneousCadence() {
        return instantaneousCadence;
    }

    public int getInstantaneousStrideLength() {
        return instantaneousStrideLength;
    }

    public long getTotalDistance() {
        return totalDistance;
    }

    private boolean instantaneousStrideLengthPresent(byte flags) {
        return (flags & GattUtils.FIRST_BITMASK) != 0;
    }

    private boolean totalDistancePresent(byte flags) {
        return (flags & GattUtils.SECOND_BITMASK) != 0;
    }

    private boolean getRunning(byte flags) {
        return (flags & GattUtils.THIRD_BITMASK) != 0;
    }

    @Override
    public String toString() {
        return "RunningSpeedCadenceMeasurement{" +
                "instantaneousSpeed=" + instantaneousSpeed +
                ", instantaneousCadence=" + instantaneousCadence +
                ", instantaneousStrideLength=" + instantaneousStrideLength +
                ", totalDistance=" + totalDistance +
                ", isRunning=" + isRunning +
                "}";
    }
}
