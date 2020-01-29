package njsoly;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * NOTE: TODO: this doesn't work.  It keeps running forever, even though the
 * timer has successfully been discontinued.
 */
public class TimerTaskLimited extends TimerTask {

	public int times = 0;
	final int max;
	Runnable task;

	public TimerTaskLimited(Runnable task, int maxTimes){
		this.max = maxTimes;
		this.task = task;
	}

	@Override
	public void run() {
		task.run();
		times++;
		if(times >= max) {
			this.cancel();
			task = null;
		}
	}

	static void killIt(){
		t = null;
	}
	static Timer t = new Timer();
	static AtomicInteger i = new AtomicInteger(1);
	public static void main(String[] args){

		t.scheduleAtFixedRate(
			new TimerTaskLimited(() -> {
				System.out.println("I am running (" + i.getAndIncrement() + ")");
				if (i.get() >= 10){
					t.purge();
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							killIt();
						}
					}, 2000);
				}
			}, 10
			), 0, 1000
		);

	}
}
