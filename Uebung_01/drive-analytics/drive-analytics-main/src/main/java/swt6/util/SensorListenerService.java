package swt6.util;

import java.util.logging.Logger;

import swt6.osgi.sensor.ISensor;
import swt6.osgi.sensor.ISensorListener;

public class SensorListenerService implements ISensorListener{

	private static final Logger logger = Logger.getLogger(SensorListenerService.class.getName());
	@Override
	public void valueChanged(ISensor sensor) {
		byte[] data = null;
		switch(sensor.getDataFormat()){
		case ABSOLUTE_VALUE_LONG: data= sensor.getData(); break;
		case PERCENT: data = sensor.getData(); break;
		}
		logger.info("Sensor: " + sensor.getSensorId() + "hat den Wert auf: "+ data + " geändert");
		
	}

	
}
