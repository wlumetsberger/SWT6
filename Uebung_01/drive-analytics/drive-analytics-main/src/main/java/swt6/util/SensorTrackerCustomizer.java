package swt6.util;

import java.util.logging.Logger;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import swt6.osgi.app.Dashboard;
import swt6.osgi.sensor.ISensor;

public class SensorTrackerCustomizer implements ServiceTrackerCustomizer<ISensor, ISensor> {

	private BundleContext context;
	private Dashboard dashboard;
	private static final Logger logger = Logger.getLogger(SensorTrackerCustomizer.class.getName());
	
	 private static enum Action {
		    ADDED, MODIFIED, REMOVED
		  }

	public SensorTrackerCustomizer(BundleContext context, Dashboard dashboard) {
		this.context = context;
		this.dashboard = dashboard;
	}

	@Override
	public ISensor addingService(ServiceReference<ISensor> serviceReference) {
		ISensor sensorFactory = context.getService(serviceReference);
		processEventInUIThread(Action.ADDED, serviceReference, sensorFactory);
		return sensorFactory;
		
	}

	@Override
	public void modifiedService(ServiceReference<ISensor> ref, ISensor sf) {
		processEventInUIThread(Action.MODIFIED, ref, sf);


	}

	@Override
	public void removedService(ServiceReference<ISensor> ref, ISensor sf) {
		processEventInUIThread(Action.REMOVED, ref, sf);

	}
	
	public void processEvent(Action action, ServiceReference<ISensor> ref, ISensor sf){
		
		switch(action){
		case MODIFIED:
			logger.info("modified Sensor");
			dashboard.removeSensor(sf);
			dashboard.addSensor(sf);
			break;
			
		case ADDED:
			logger.info("added sensor");
			dashboard.addSensor(sf);
			break;
		case REMOVED:
			logger.info("removeSensor");
			dashboard.removeSensor(sf);
			break;
			
		}
	}
	
	private void processEventInUIThread(final Action action,
		      final ServiceReference<ISensor> ref, final ISensor sf) {
		    try {
		      JavaFxUtils.runAndWait(() -> processEvent(action, ref, sf));
		    }
		    catch (Exception ex) {
		      ex.printStackTrace();
		    }
		  }

}
