package multithread;
public class HandlingExceptionRunnable implements Runnable {

	/**
	 * Runnableを使用すると、子スレッドから戻り値や例外を取得することはできない。
	 * スレッド内で検査例外をスローすると、Thread.run()の定義上、コンパイルエラーになる
	 * スレッド内でRuntimeExceptionをスローすると、Thread.start()の外側でcatchしようとしてもそこまでたどりつかずスレッドが停止する。
	 */
	public static void main(String[] args) {
		HandlingExceptionRunnable t = new HandlingExceptionRunnable();
		System.out.println("メインスレッド開始－Runnableの例");

		try {
			new Thread(t).start();
		} catch (RuntimeException e) {
			System.out.println("!!!!!例外処理【外側】 " + Thread.currentThread().getName());
		}
		System.out.println("メインスレッド終了");
	}

	@Override
	public void run() {
		try {
			while (true) {
				System.out.println("ワーカスレッド処理開始 [" + Thread.currentThread().getName()+"]");
				Thread.sleep(2000);
				throw new RuntimeException("○○による例外");
			}
		} catch (InterruptedException e) {

		} catch (RuntimeException e) {
			System.out.println("例外処理【スレッド内】");
			throw e;
		}
	}
}
