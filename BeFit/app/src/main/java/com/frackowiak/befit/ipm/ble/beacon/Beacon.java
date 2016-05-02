package com.frackowiak.befit.ipm.ble.beacon;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;

import java.text.ParseException;


public class Beacon implements Comparable<Beacon> {

    private BluetoothDevice bluetoothDevice;
    private int rssi;
    private AdvertisingPacketParser packetParser;

    public Beacon(@NonNull BluetoothDevice bluetoothDevice, int rssi, byte[] advertisementPacket) throws ParseException {
        this.bluetoothDevice = bluetoothDevice;
        updateState(rssi, advertisementPacket);
    }

    public void updateState(int rssi, byte[] advertisementPacket) throws ParseException {
        this.rssi = rssi;
        this.packetParser = new AdvertisingPacketParser(advertisementPacket);
    }

    public String getName() {
        return bluetoothDevice.getName();
    }

    public String getAddress() {
        return bluetoothDevice.getAddress();
    }

    public int getRssi() {
        return rssi;
    }

    public int getTxPower() {
        return packetParser.getTxPower();
    }

    public String getUuid() {
        return packetParser.getUuid();
    }

    @Override
    public int compareTo(@NonNull Beacon another) {
        return getAddress().compareTo(another.getAddress());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Beacon beacon = (Beacon) o;

        return getAddress().equals(beacon.getAddress());
    }

    @Override
    public int hashCode() {
        return getAddress().hashCode();
    }

    @Override
    public String toString() {
        return "Beacon device: " + getName() +
                "\nMAC address: " + getAddress() +
                "\nRSSI: " + getRssi() +
                "\nTX Power: " + getTxPower();
    }

    public String toDetailedString() {
        return "Beacon device: " + getName() +
                "\nMAC address: " + getAddress() +
                "\nRSSI: " + getRssi() +
                "\nTX Power: " + getTxPower() +
                "\nUUID: " + getUuid() +
                "\nMajor: " + packetParser.getMajor() +
                "\nMinor: " + packetParser.getMinor();
    }

}
