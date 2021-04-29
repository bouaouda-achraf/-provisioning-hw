package com.voxloud.provisioning.writer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

public class DeviceDeskPrinter extends MinimalPrettyPrinter {

	private static final long serialVersionUID = -7060255009761776903L;

	public DeviceDeskPrinter() {
	}
	
	@Override
    public void writeStartObject(JsonGenerator g) throws IOException
    {
        g.writeRaw("");
    }
	 
	@Override
    public void writeObjectFieldValueSeparator(JsonGenerator jg)
    throws IOException
    {
        jg.writeRaw("=");
    }
	
	@Override
    public void writeEndObject(JsonGenerator g, int nrOfEntries) throws IOException
    {
        g.writeRaw("");
    }
    @Override
    public void writeObjectEntrySeparator(JsonGenerator jg)
    throws IOException
    {
        jg.writeRaw("\n");
    }

    @Override
    public void writeStartArray(JsonGenerator g) throws IOException
    {
        g.writeRaw("");
    }
    
    @Override
    public void writeEndArray(JsonGenerator g, int nrOfValues) throws IOException
    {
        g.writeRaw("");
    }

}
