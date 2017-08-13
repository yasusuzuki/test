package multithread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class HandlingExceptionLambda {
	public static void main(String[] args) {
		new HandlingExceptionLambda().runLambda();
	}
	
	// リストを繰り返して、リストの中の文字列をすべて対応するクラス型に置き換えるユーティリティメソッド。
	public List<Class> getClasses(final List<String> names) throws ClassNotFoundException {
		final List<Class> classes = new ArrayList<>();
		for (final String name : names)
			classes.add(Class.forName(name));
		return classes;
	}

	// ラムダを使って、リストの中の文字列をすべて対応するクラス型に置き換えるユーティリティメソッド。
	public Stream<Class<?>> getClasses(final Stream<String> names) throws ClassNotFoundException {
		// これはできない、なぜならClass::forNameはClassCastExceptionをスローするから
		// Stream<Class<?>> str = names.map( Class::forName );
		// この方法ならできる。なぜならラムダの中で例外をキャッチしているから。でも非常に読みにくい。
		Stream<Class<?>> stream = names.map((String s) -> {
			try {
				return Class.forName(s);
			} catch (Exception e) {
			}
			return null;
		});
		return stream;
	}


	//ラムダ内で例外を外側に伝搬させる方法
	public void runLambda() {
		try{
			List<String> strArray = Arrays.asList("abc", "def", "xxx", "ghi", "jkl", "xxx", "pqr", "stu");
			strArray.parallelStream().forEach(s -> {
				System.out.println("ラムダ開始: id=" + Thread.currentThread().getId());
				try {
					Thread.sleep(100L);
					if (s.equals("xxx"))
						throw new RuntimeException("ラムダ内で例外: id=" + Thread.currentThread().getId() + ", s=" + s);
				} catch (RuntimeException ex) {
					System.out.println("ラムダ内で例外発生: id=" + Thread.currentThread().getId() + ", s=" + s);
					// スタックトレースを出力させてみる(ラムダの中でcatchした例外)
					ex.printStackTrace(System.out);
					throw ex;
				} catch (InterruptedException e) {
					e.printStackTrace(System.out);
				}
				System.out.println("ラムダ終了: id=" + Thread.currentThread().getId() + ", s=" + s);
			});
		} catch (Exception ex) {
			System.out.println("外側で例外をcatch");
			// スタックトレースを出力させてみる(ラムダの外でcatchした例外)
			ex.printStackTrace(System.out);
			System.out.println("ラムダの外でcatchした例外：ここまで");
		}
	}

}
