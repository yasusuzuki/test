package lamda;

import java.util.function.Consumer;

import lamda.reflectiontarget.SearchAbstract;

public class LamdaFactory {
	//ラムダからクラス変数にアクセスできる。
	private static String class_instance_var = "AA";
	
	//アプリ初期化時にすべてラムダを作成・登録をして、実行は後で。
	public static <T extends SearchAbstract> Runnable createLamda(Class<T> searchClass) {
		int out_lamda = 100; // ローカル変数もラムダで参照可能
		//Runnableはスレッドを作るときと同じだが、new Thread(Runnable runner)をしないので
		//新しいスレッドは作成されない
		Runnable runner = () ->{
			int in_lamda = out_lamda;
			T instance = null;
			try {
				instance = searchClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
				System.exit(1);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				System.exit(1);
			}
			//前処理
			
			//実行
			instance.execute();
			
			//後処理
			
			//ラムダ外で定義された変数は参照はできるが、変更はできない。finalと同じ扱い
			//ただし、ラムダ内で定義された変数にコピーすれば当然変更ができる。
			System.out.println("Instance = " + instance);
			System.out.println("In Lamda = " + ++in_lamda);
			System.out.println("Out Lamda =  " + out_lamda);
			
		};
		return runner;
	}
}
