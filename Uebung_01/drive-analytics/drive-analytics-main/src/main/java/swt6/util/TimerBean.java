package swt6.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class TimerBean {

		private int noTicks = 50;
		
		private final AtomicInteger tickInterval = new AtomicInteger(1000);
		private final AtomicInteger tickCount = new AtomicInteger(0);
		private final AtomicBoolean stopTimer = new AtomicBoolean(false);
		private final AtomicReference<Thread> tickerThread = new AtomicReference<Thread>(null);
		
		private final Vector<TimerListener> listener = new Vector<>();
		
		private final PropertyChangeSupport propertyChangeSupport;
		
		public TimerBean(){
			propertyChangeSupport = new PropertyChangeSupport(this);
			
		}
		
		public boolean isRunning(){
			return tickerThread.get() != null;
		}
		
		public void stop(){
			stopTimer.set(true);
		}
		
		public void reset(){
			if(isRunning()){
				throw new IllegalStateException("Cannot reset while timer is running");
			}
			tickCount.set(0);
		}
		
		public int getTickCount(){
			return tickCount.get();
		}
		
		public int getTickInterval(){
			return tickInterval.get();
		}
		
		public void setTickInterval(int interval){
			int oldValue = tickInterval.get();
			if(interval != oldValue){
				this.tickInterval.set(interval);
				firePropertyChange("tickInterval", oldValue, interval);
			}
		}

		public int getNoTicks() {
			return noTicks;
		}

		public void setNoTicks(int noTicks) {
			int oldValue = this.noTicks;
			this.noTicks = noTicks;
			firePropertyChange("noTicks", oldValue, noTicks);
		}
		
		
		public void addTimerListener(TimerListener listener){
			this.listener.addElement(listener);
		}
		
		public void removeTimerListener(TimerListener listener){
			this.listener.remove(listener);
		}
		
		protected void fireEvent(TimerEvent e){
			((Vector<TimerListener>)this.listener.clone()).stream().forEach(l -> l.expired(e));
		}
		
		public void statrt(){
			if(isRunning()){
				
				throw new IllegalStateException("Cannot start: is allready running");
			}
			
			final int ticks = noTicks;
			
			tickerThread.set(new Thread(()->{
				tickCount.set(0);
				while(!stopTimer.get() && tickCount.get() < ticks){
					try {
						Thread.sleep(tickInterval.get());
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(!stopTimer.get()){
						fireEvent(new TimerEvent(this, tickCount.incrementAndGet(), ticks));
					}
					
					stopTimer.set(false);
					tickerThread.set(null);
				}
			}));
			tickerThread.get().start();
		}
		
		public void addPropertyChangeListener(PropertyChangeListener l){
			propertyChangeSupport.addPropertyChangeListener(l);
		}
		
		public void removePropertyChangeListener(PropertyChangeListener l){
			propertyChangeSupport.removePropertyChangeListener(l);
		}
		
		private void firePropertyChange(String propertyName, Object oldValue, Object newValue){
			propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
		}
		
		



}
