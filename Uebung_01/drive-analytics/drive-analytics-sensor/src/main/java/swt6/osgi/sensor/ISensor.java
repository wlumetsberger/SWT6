package swt6.osgi.sensor;

public interface ISensor{
	
	public enum SensorDataFormat{
		PERCENT, ABSOLUTE_VALUE_LONG
	}
	
	String getSensorId();
	byte[] getData();
	SensorDataFormat getDataFormat();
}