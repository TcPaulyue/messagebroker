package com.servicematrix.matrixmq.spaceMappingEngine.deviceAccess;



import com.servicematrix.matrixmq.spaceMappingEngine.model.Device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.servicematrix.matrixmq.spaceMappingEngine.staticLocation.IndoorDistance.minDistanceMap;


public class DeviceManager {

    private List<Device> deviceList = new ArrayList<>();
    private Map<Device,Map<Device,Double>> deviceMap = new HashMap<>();

    public DeviceManager() {
    }

    public void addDevice(Device device){
        deviceList.add(device);
        deviceList.forEach(device1 -> {
            if(deviceMap.containsKey(device1))
                deviceMap.get(device1).put(device,0.0);
            else{
                Map<Device,Double> map =new HashMap<>();
                deviceList.forEach(device2 -> {
                    map.put(device2,0.0);
                });
                deviceMap.put(device,map);
            }
        });
        //todo: add device
        minDistanceMap.get(0);
    }

    //todo: update device
    public void updateDevice(List<Device> devices){

    }

    //todo: delete device
    public void deleteDevice(List<Device> devices){

    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public Map<Device, Map<Device, Double>> getDeviceMap() {
        return deviceMap;
    }

    public void setDeviceMap(Map<Device, Map<Device, Double>> deviceMap) {
        this.deviceMap = deviceMap;
    }
}
