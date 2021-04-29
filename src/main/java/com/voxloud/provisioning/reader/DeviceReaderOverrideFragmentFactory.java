package com.voxloud.provisioning.reader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.util.DeviceConstants;

public class DeviceReaderOverrideFragmentFactory {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private DeviceReaderOverrideFragmentFactory() {
	}

	public static DeviceReaderOverrideFragmentFactory getInstance() {
		return new DeviceReaderOverrideFragmentFactory();
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getMap(Device device){
		Map<String, Object> reconstructedUtilMap = null;
		
		
		switch (device.getModel()) {
		case CONFERENCE:
			try {
				reconstructedUtilMap =  new ObjectMapper().readValue(device.getOverrideFragment(), HashMap.class);
			} catch (JsonMappingException e) {
				logger.error("prob when processing  getOverrideFragment", e);
			} catch (JsonProcessingException e) {
				logger.error("prob when processing  getOverrideFragment", e);
			}
			break;
		case DESK:
			reconstructedUtilMap = Arrays.stream(device.getOverrideFragment().split(DeviceConstants.NEW_LINE))
            .map(s -> s.split(DeviceConstants.EQUAL))
            .collect(Collectors.toMap(s -> s[0], s -> s[1]));
			break;
		default:
		}
		
		return reconstructedUtilMap;
	}

	public String getSpliter(Device device) {
		String splitBy = null;
		
		return splitBy;
	}

}
