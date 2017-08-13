package multithread;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HandlingExceptionCallable implements Callable<Long> {
	/**
	 * Callableを使用すると、子スレッドから戻り値や例外を取得することができる。
	 * Threadが開始されたと同時に、親スレッドに返されるFutureインスタンスの
	 * get()メソッドを利用すれば戻り値や例外を取得できる。
	 */
	public static void main(String[] args) {
		HandlingExceptionCallable t = new HandlingExceptionCallable();
		ExecutorService pool = Executors.newSingleThreadExecutor();
		System.out.println("メインスレッド開始－Callableの例");
		try {
			Future<?> future = pool.submit(t);
			try {
				future.get();

				long sum = t.getSum();
				System.out.println(sum);
			} catch (InterruptedException | ExecutionException e) {
				System.out.println("!!!!!例外処理【外側】 " + Thread.currentThread().getName() + " " + e.getCause());
				//e.printStackTrace();
			}
		} finally {
			pool.shutdownNow();
		}
		System.out.println("メインスレッド終了");
	}

	private long getSum() {
		return 0;
	}

	@Override
	public Long call() throws Exception {
		try {
			while (true) {
				System.out.println("ワーカスレッド処理開始 [" + Thread.currentThread().getName()+"]");
				Thread.sleep(2000);
				throw new Exception("○○による例外");
			}
		} catch (InterruptedException e) {

		} catch (Exception e) {
			System.out.println("例外処理【スレッド内】");
			throw e;
		}
		return null;
	}
}
