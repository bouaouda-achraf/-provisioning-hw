package com.voxloud.provisioning.service;

import java.util.Map;

import com.voxloud.provisioning.entity.Device;

public interface IProvisioningService {
	/**
	 * get file structure from macAddress
	 *  when macAddress not found return null
	 *  
	 * @param macAddress
	 * @return
	 */
    String getProvisioningFile(String macAddress);
    
    /**
     * get data properties as Map from macAddress
     * 
     * @param device
     * @return
     */
	Map<String, Object> constructMapFromAllSources(Device device);
}
