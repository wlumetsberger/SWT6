package swt6.util;

import java.util.EventObject;

public class TimerEvent extends EventObject{

	private static final long serialVersionUID = -6780977600132631822L;

	private final int noTicks;
	private final int tickCount;
	
	public TimerEvent(Object source, int noTicks, int tickCount) {
		super(source);
		this.noTicks=noTicks;
		this.tickCount = tickCount;
	}

	public int getNoTicks() {
		return noTicks;
	}

	public int getTickCount() {
		return tickCount;
	}
}
