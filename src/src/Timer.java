package src;
public class Timer extends Thread {
    
	private int tick = 60;
	protected boolean stopped = false;
        Controller controller= Controller.getInstance();

    	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(getTick() * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			controller.tick(); //1   

                        
		}
	}
	public void stops() {
		System.out.println("Stop Timer...");
		stopped = true;
                this.stop();
	}

	public int getTick() {
		return tick;
	}

	public void setTick(int tick) {
		if (tick > 0)
			this.tick = tick;
		else
			stops();
	}
}
