import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorWrapper {
	ExecutorService executor;
	int numThreads = 4;

	private static ExecutorWrapper instance = new ExecutorWrapper();

	private ExecutorWrapper() {
		executor = Executors.newFixedThreadPool(numThreads);
	}

	public int getNumThreads() {
		return numThreads;
	}

	public static ExecutorWrapper getInstance() {
		return instance;
	}

	public void execute(Runnable command) {
		executor.execute(command);
	}

}