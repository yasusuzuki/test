package lamda;

import java.util.function.Consumer;

import lamda.reflectiontarget.SearchAbstract;

public class LamdaFactory {
	//�����_����N���X�ϐ��ɃA�N�Z�X�ł���B
	private static String class_instance_var = "AA";
	
	//�A�v�����������ɂ��ׂă����_���쐬�E�o�^�����āA���s�͌�ŁB
	public static <T extends SearchAbstract> Runnable createLamda(Class<T> searchClass) {
		int out_lamda = 100; // ���[�J���ϐ��������_�ŎQ�Ɖ\
		//Runnable�̓X���b�h�����Ƃ��Ɠ��������Anew Thread(Runnable runner)�����Ȃ��̂�
		//�V�����X���b�h�͍쐬����Ȃ�
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
			//�O����
			
			//���s
			instance.execute();
			
			//�㏈��
			
			//�����_�O�Œ�`���ꂽ�ϐ��͎Q�Ƃ͂ł��邪�A�ύX�͂ł��Ȃ��Bfinal�Ɠ�������
			//�������A�����_���Œ�`���ꂽ�ϐ��ɃR�s�[����Γ��R�ύX���ł���B
			System.out.println("Instance = " + instance);
			System.out.println("In Lamda = " + ++in_lamda);
			System.out.println("Out Lamda =  " + out_lamda);
			
		};
		return runner;
	}
}
