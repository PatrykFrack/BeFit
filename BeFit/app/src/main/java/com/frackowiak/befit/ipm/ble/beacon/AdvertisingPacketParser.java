package com.frackowiak.befit.ipm.ble.beacon;

import org.apache.commons.codec.binary.Hex;

import java.text.ParseException;

/**
 * Parser of Bluetooth Low Energy Advertisement Packets from iBeacon
 * Based on:
 * http://blog.conjure.co.uk/2014/08/ibeacons-and-android-parsing-the-uuid-major-and-minor-values/
 */
public class AdvertisingPacketParser {

    public static final int UUID_LENGTH = 16;
    public static final int UUID_OFFSET = 4;
    public static final int MAJOR_OFFSET = 20;
    public static final int MINOR_OFFSET = 22;
    public static final int TX_POWER_OFFSET = 24;
    private byte[] packet;
    private int startByte = 2;

    public AdvertisingPacketParser(byte[] packet) throws ParseException {
        this.packet = packet;
        if (!alignStartByte())
            throw new ParseException("Can't parse given packet. Doesn't seem compatible with the iBeacon standard", startByte);
    }

    public String getUuid() {
        byte[] uuidBytes = new byte[UUID_LENGTH];
        System.arraycopy(packet, startByte + UUID_OFFSET, uuidBytes, 0, UUID_LENGTH);
        String hexString = new String(Hex.encodeHex(uuidBytes));
        hexString = hexString.toUpperCase();

        return hexString.substring(0, 8) + "-" +
                hexString.substring(8, 12) + "-" +
                hexString.substring(12, 16) + "-" +
                hexString.substring(16, 20) + "-" +
                hexString.substring(20, 32);
    }

    public int getMajor() {
        return (packet[startByte + MAJOR_OFFSET] & 0xff) * 0x100 +
                (packet[startByte + MAJOR_OFFSET + 1] & 0xff);
    }

    public int getMinor() {
        return (packet[startByte + MINOR_OFFSET] & 0xff) * 0x100 +
                (packet[startByte + MINOR_OFFSET + 1] & 0xff);
    }

    public int getTxPower() {
        return (int) packet[startByte + TX_POWER_OFFSET];
    }

    private boolean alignStartByte() {
        while (startByte <= 5) {
            if (((int) packet[startByte + 2] & 0xff) == 0x02 &&     //Identifies an iBeacon
                    ((int) packet[startByte + 3] & 0xff) == 0x15) { //Identifies correct data length
                return true;
            }
            startByte++;
        }
        return false;
    }

}
