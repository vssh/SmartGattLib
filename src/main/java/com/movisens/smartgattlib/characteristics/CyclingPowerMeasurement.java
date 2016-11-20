package com.movisens.smartgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;
import com.movisens.smartgattlib.GattUtils;

/**
 * Created by varun on 20.11.16.
 */

public class CyclingPowerMeasurement {

    private boolean pedalPowerBalancePresent;
    private boolean pedalPowerBalanceReference;
    private boolean accumulatedTorquePresent;
    private boolean accumulatedTorqueSource;
    private boolean wheelRevolutionDataPresent;
    private boolean crankRevolutionDataPresent;
    private boolean extremeForceMagnitudesPresent;
    private boolean extremeTorqueMagnitudesPresent;
    private boolean extremeAnglesPresent;
    private boolean topDeadSpotAnglesPresent;
    private boolean bottomDeadSpotAnglesPresent;
    private boolean accumulatedEnergyPresent;
    private boolean offsetCompensationIndicator;

    private int distributedInstantaneousPower;
    private int totalInstantaneousPower;        //in Watts
    private float pedalPowerBalance;
    private float accumulatedTorque;
    private long cumulativeWheelRevolutions;
    private int lastWheelEventTime;
    private int cumulativeCrankRevolutions;
    private int lastCrankEventTime;
    private int maxForceMagnitude;
    private int minForceMagnitude;
    private int maxTorqueMagnitude;
    private int minTorqueMagnitude;
    private int maxAngle;
    private int minAngle;
    private int topDeadSpotAngle;
    private int bottomDeadSpotAngle;
    private int accumulatedEnergy;

    public CyclingPowerMeasurement(byte[] value) {
        super();

        GattByteBuffer bb = GattByteBuffer.wrap(value);

        byte flags = bb.getInt8();
        pedalPowerBalancePresent = firstBit(flags);
        pedalPowerBalanceReference = secondBit(flags);
        accumulatedTorquePresent = thirdBit(flags);
        accumulatedTorqueSource = fourthBit(flags);
        wheelRevolutionDataPresent = fifthBit(flags);
        crankRevolutionDataPresent = sixthBit(flags);
        extremeForceMagnitudesPresent = seventhBit(flags);
        extremeTorqueMagnitudesPresent = eighthBit(flags);

        flags = bb.getInt8();
        extremeAnglesPresent = firstBit(flags);
        topDeadSpotAnglesPresent = secondBit(flags);
        bottomDeadSpotAnglesPresent = thirdBit(flags);
        accumulatedEnergyPresent = fourthBit(flags);
        offsetCompensationIndicator = fifthBit(flags);

        distributedInstantaneousPower = bb.getInt16();
        totalInstantaneousPower = distributedInstantaneousPower;

        if(pedalPowerBalancePresent) {
            pedalPowerBalance = bb.getUint8() / 2F;
            totalInstantaneousPower = (int)(distributedInstantaneousPower * 100 / pedalPowerBalance);
        }

        if(accumulatedTorquePresent) {
            accumulatedTorque = bb.getUint16() / 32F;
        }

        if (wheelRevolutionDataPresent) {
            cumulativeWheelRevolutions = bb.getUint32();
            lastWheelEventTime = bb.getUint16();
        }

        if (crankRevolutionDataPresent) {
            cumulativeCrankRevolutions = bb.getUint16();
            lastCrankEventTime = bb.getUint16();
        }

        if(extremeForceMagnitudesPresent) {
            maxForceMagnitude = bb.getInt16();
            minForceMagnitude = bb.getInt16();
        }

        if(extremeTorqueMagnitudesPresent) {
            maxTorqueMagnitude = bb.getInt16();
            minTorqueMagnitude = bb.getInt16();
        }

        if(extremeAnglesPresent) {
            int tmp = bb.getUint16();
            maxAngle = tmp/16;
            minAngle = (tmp%16)*256 + bb.getInt8();
        }

        if(topDeadSpotAnglesPresent) {
            topDeadSpotAngle = bb.getUint16();
        }

        if(bottomDeadSpotAnglesPresent) {
            bottomDeadSpotAngle = bb.getUint16();
        }

        if(accumulatedEnergyPresent) {
            accumulatedEnergy = bb.getUint16();
        }
    }

    public boolean isPedalPowerBalancePresent() {
        return pedalPowerBalancePresent;
    }

    public boolean isPedalPowerBalanceReference() {
        return pedalPowerBalanceReference;
    }

    public boolean isAccumulatedTorquePresent() {
        return accumulatedTorquePresent;
    }

    public boolean isAccumulatedTorqueSource() {
        return accumulatedTorqueSource;
    }

    public boolean isWheelRevolutionDataPresent() {
        return wheelRevolutionDataPresent;
    }

    public boolean isCrankRevolutionDataPresent() {
        return crankRevolutionDataPresent;
    }

    public boolean isExtremeForceMagnitudesPresent() {
        return extremeForceMagnitudesPresent;
    }

    public boolean isExtremeTorqueMagnitudesPresent() {
        return extremeTorqueMagnitudesPresent;
    }

    public boolean isExtremeAnglesPresent() {
        return extremeAnglesPresent;
    }

    public boolean isTopDeadSpotAnglesPresent() {
        return topDeadSpotAnglesPresent;
    }

    public boolean isBottomDeadSpotAnglesPresent() {
        return bottomDeadSpotAnglesPresent;
    }

    public boolean isAccumulatedEnergyPresent() {
        return accumulatedEnergyPresent;
    }

    public boolean isOffsetCompensationIndicator() {
        return offsetCompensationIndicator;
    }

    //in Watt
    public int getDistributedInstantaneousPower() {
        return distributedInstantaneousPower;
    }

    public int getTotalInstantaneousPower() {
        return totalInstantaneousPower;
    }

    public float getPedalPowerBalance() {
        return pedalPowerBalance;
    }

    //in Nm
    public float getAccumulatedTorque() {
        return accumulatedTorque;
    }

    public long getCumulativeWheelRevolutions() {
        return cumulativeWheelRevolutions;
    }

    //unit has resolution of 1/2048s
    public int getLastWheelEventTime() {
        return lastWheelEventTime;
    }

    public int getCumulativeCrankRevolutions() {
        return cumulativeCrankRevolutions;
    }

    //unit has resolution of 1/1024s
    public int getLastCrankEventTime() {
        return lastCrankEventTime;
    }

    //in Newton
    public int getMaxForceMagnitude() {
        return maxForceMagnitude;
    }

    public int getMinForceMagnitude() {
        return minForceMagnitude;
    }

    public int getMaxTorqueMagnitude() {
        return maxTorqueMagnitude;
    }

    public int getMinTorqueMagnitude() {
        return minTorqueMagnitude;
    }

    //in degrees
    public int getMaxAngle() {
        return maxAngle;
    }

    public int getMinAngle() {
        return minAngle;
    }

    public int getTopDeadSpotAngle() {
        return topDeadSpotAngle;
    }

    public int getBottomDeadSpotAngle() {
        return bottomDeadSpotAngle;
    }

    //in kJ
    public int getAccumulatedEnergy() {
        return accumulatedEnergy;
    }

    public int getCadence(CyclingPowerMeasurement cpm) {
        int cadence = -1;
        if(isCrankRevolutionDataPresent() && cpm != null && cpm.isCrankRevolutionDataPresent()) {
            cadence = (int)((getCumulativeCrankRevolutions() - cpm.getCumulativeCrankRevolutions())
                    / ((getLastCrankEventTime() - cpm.getLastCrankEventTime()) / 1024F));
        }
        return cadence;
    }

    private boolean firstBit(byte flags) {
        return (flags & GattUtils.FIRST_BITMASK) != 0;
    }

    private boolean secondBit(byte flags) {
        return (flags & GattUtils.SECOND_BITMASK) != 0;
    }

    private boolean thirdBit(byte flags) {
        return (flags & GattUtils.THIRD_BITMASK) != 0;
    }

    private boolean fourthBit(byte flags) {
        return (flags & GattUtils.FOURTH_BITMASK) != 0;
    }

    private boolean fifthBit(byte flags) {
        return (flags & GattUtils.FIFTH_BITMASK) != 0;
    }

    private boolean sixthBit(byte flags) {
        return (flags & GattUtils.SIXTH_BITMASK) != 0;
    }

    private boolean seventhBit(byte flags) {
        return (flags & GattUtils.SEVENTH_BITMASK) != 0;
    }

    private boolean eighthBit(byte flags) {
        return (flags & GattUtils.EIGTH_BITMASK) != 0;
    }


}
