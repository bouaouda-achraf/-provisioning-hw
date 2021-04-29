package com.voxloud.provisioning.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.voxloud.provisioning.config.ConfigDeviceProperties;
import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.reader.DeviceReaderOverrideFragmentFactory;
import com.voxloud.provisioning.repository.DeviceRepository;
import com.voxloud.provisioning.service.IProvisioningService;
import com.voxloud.provisioning.util.DeviceConstants;
import com.voxloud.provisioning.writer.DeviceObjectWriterFactory;

@Service
public class ProvisioningServiceImpl implements IProvisioningService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	DeviceRepository deviceRepository;

	@Autowired
	ConfigDeviceProperties configDeviceProperties;

	public String getProvisioningFile(String macAddress) {
		Optional<Device> device = deviceRepository.findById(macAddress);
		if (device.isPresent()) {

			Map<String, Object> map = constructMapFromAllSources(device.get());
			try {
				return DeviceObjectWriterFactory.getInstance().getObjectWriterForDevice(device.get())
						.writeValueAsString(map);
			} catch (JsonProcessingException e) {
				logger.error("Prob when JsonProcessing ", e);
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> constructMapFromAllSources(Device device) {
		Map<String, Object> mapDevice = constructMapFromDevice(device);
		mapDevice.putAll(constructMapFromPropertiesFile());
		if(device.getOverrideFragment() != null) {
			mapDevice.putAll(constructMapFromDeviceOverrideFragment(device));
		}
		return mapDevice;
	}

	/**
	 * get properties data from DB
	 * 
	 * @param device
	 * @return
	 */
	private Map<String, Object> constructMapFromDevice(Device device) {
		Map<String, Object> map = new HashMap<>();
		map.put(DeviceConstants.USERNAME, device.getUsername());
		map.put(DeviceConstants.PASSWORD, device.getPassword());
		return map;
	}

	/**
	 * get properties data from properties file
	 *  
	 * @return
	 */
	private Map<String, Object> constructMapFromPropertiesFile() {
		Map<String, Object> map = new HashMap<>();
		map.put(DeviceConstants.DOMAIN, configDeviceProperties.getDomain());
		map.put(DeviceConstants.PORT, configDeviceProperties.getPort());
		map.put(DeviceConstants.CODECS, configDeviceProperties.getCodecs());
		return map;
	}
	
	private Map<String, Object> constructMapFromDeviceOverrideFragment(Device device){
		return DeviceReaderOverrideFragmentFactory.getInstance().getMap(device);
	}
}
