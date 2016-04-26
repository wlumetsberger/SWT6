package swt6.osgi.sensor.oil;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import swt6.osgi.sensor.ISensor;

public class OilSensorActivator implements BundleActivator{

	@Override
	public void start(BundleContext context) throws Exception {
		context.registerService(ISensor.class, new OilSensor("OilSensor"), null);	
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		
		
	}

}
