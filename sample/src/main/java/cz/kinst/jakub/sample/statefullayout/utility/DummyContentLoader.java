package cz.kinst.jakub.sample.statefullayout.utility;

/**
 * Created by jakubkinst on 12/11/15.
 */
public class DummyContentLoader {
	public interface OnDummyContentLoaded {
		void onDummyContentLoaded(String data);
	}


	public static void loadDummyContent(final OnDummyContentLoaded listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
					String content = "This is the loaded dummy data";
					listener.onDummyContentLoaded(content);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
