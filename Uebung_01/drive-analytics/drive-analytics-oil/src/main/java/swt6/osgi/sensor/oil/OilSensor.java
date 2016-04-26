package swt6.osgi.sensor.oil;

import java.nio.ByteBuffer;
import java.util.Random;

import swt6.osgi.sensor.ISensor;

public class OilSensor implements ISensor {

	private final String sensorId;
	private Random random;

	public OilSensor(String sensorId) {
		this.sensorId = sensorId;
		random = new Random();
	}

	@Override
	public String getSensorId() {
		return this.sensorId;
	}

	@Override
	public byte[] getData() {
		double value = random.nextDouble();
		byte[] bytes = ByteBuffer.allocate(Double.SIZE / Byte.SIZE).putDouble(value).array();
		return bytes;
	}

	@Override
	public SensorDataFormat getDataFormat() {
		return SensorDataFormat.PERCENT;
	}

}
