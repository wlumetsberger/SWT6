package swt6.osgi.app;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import swt6.osgi.sensor.ISensor;
import swt6.osgi.sensor.ISensorListener;
import swt6.util.JavaFxUtils;
import swt6.util.SensorListenerService;
import swt6.util.SensorTrackerCustomizer;

public class AppActivator implements BundleActivator {

	private ServiceTracker<ISensor, ISensor> serviceTracker;
	private Dashboard dashboard;

	@Override
	public void start(BundleContext context) throws Exception {
		JavaFxUtils.initJavaFx();
		JavaFxUtils.runAndWait(() -> startUI(context));
		context.registerService(ISensorListener.class, new SensorListenerService(), null);
		serviceTracker = new ServiceTracker<>(context, ISensor.class, new SensorTrackerCustomizer(context, dashboard));
		serviceTracker.open();
	}

	private void startUI(BundleContext context) {
		dashboard = new Dashboard();
		dashboard.show();
		dashboard.addCloseHandlers((evt) -> {
			try {
				context.getBundle().stop();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		});

	}

	private void stopUI(BundleContext context) {
		// close Window and Timer
		serviceTracker.close();
		dashboard.close();

	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		JavaFxUtils.runAndWait(() -> stopUI(arg0));
	}

}
