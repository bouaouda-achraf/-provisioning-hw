package com.voxloud.provisioning.writer;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.voxloud.provisioning.entity.Device;

public class DeviceObjectWriterFactory {

	private DeviceObjectWriterFactory() {
	}

	public static DeviceObjectWriterFactory getInstance() {
		return new DeviceObjectWriterFactory();
	}

	public ObjectWriter getObjectWriterForDevice(Device device) {
		ObjectMapper mapper = new ObjectMapper();
		// default behavior
		MinimalPrettyPrinter printer = new MinimalPrettyPrinter();
		switch (device.getModel()) {
		case CONFERENCE:
			printer = new DeviceConferencePrinter();
			break;
		case DESK:
			SimpleModule module = new SimpleModule("UnQuote");
			module.addSerializer(new UnQuotesSerializer());
			mapper.configure(com.fasterxml.jackson.core.json.JsonWriteFeature.QUOTE_FIELD_NAMES.mappedFeature(), false);
			mapper.configure(
					com.fasterxml.jackson.core.json.JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(),
					true);
			mapper.registerModule(module);
			printer = new DeviceDeskPrinter();
			break;
		default:
		}
		return mapper.writer(printer);
	}

}
