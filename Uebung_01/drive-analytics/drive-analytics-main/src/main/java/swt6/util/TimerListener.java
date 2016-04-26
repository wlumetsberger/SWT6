package swt6.util;

import java.util.EventListener;

public interface TimerListener extends EventListener {

	void expired(TimerEvent event);
}
