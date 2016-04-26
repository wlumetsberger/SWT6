package swt6.osgi.sensor.distance;

import java.nio.ByteBuffer;
import java.util.Random;

import swt6.osgi.sensor.ISensor;

public class DistanceSensor implements ISensor {

	private final String sensorId;
	private Random random;

	public DistanceSensor(String sensorId) {
		this.sensorId = sensorId;
		random = new Random();
	}

	@Override
	public String getSensorId() {
		return this.sensorId;
	}

	@Override
	public byte[] getData() {
		long value = random.nextLong();
		if(value <= 0){
			value = 1L;
		}
		byte[] bytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(value).array();
		return bytes;
	}

	@Override
	public SensorDataFormat getDataFormat() {
		return SensorDataFormat.ABSOLUTE_VALUE_LONG;
	}

}
