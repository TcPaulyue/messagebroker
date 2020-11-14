package com.servicematrix.matrixmq.spaceMappingEngine.staticLocation;



import com.servicematrix.matrixmq.spaceMappingEngine.model.Device;
import com.servicematrix.matrixmq.spaceMappingEngine.model.DeviceToAccessNode;
import com.servicematrix.matrixmq.spaceMappingEngine.model.Floor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceToAccessMapBuilder {

    public Map<String, DeviceToAccessNode> build(Floor floor){
        Map<String, DeviceToAccessNode> pointToDoorNodeMap = new HashMap<>();
        List<Device> deviceList = floor.getDevicesOnFloor();
        deviceList.forEach(mc-> pointToDoorNodeMap.put(mc.getServiceName(),new DeviceToAccessNode(mc.getServiceName())));

        floor.getCellList().forEach(cell -> deviceList.forEach(mc->{
            if(cell.getDevices().contains(mc)){
                cell.getAccesses().forEach(access -> pointToDoorNodeMap.get(mc).getAccessMap().put(access
                        ,access.getLocation().calcDistance(mc.getLocation())));
            }
        }));
        return pointToDoorNodeMap;
    }
}
