package reflection;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
/*
  
TODO: �������̎g�p�ʂɂ��Ă̌v�����Ȃ�
TODO: DB�⃋�[���ւ̃A�N�Z�X�ɑ΂����r��A�����񑀍�ȊO�̉��Z�ɑ΂����r���~����
TODO: Java7���瓱�����ꂽMethodHandle��Lamda���g���ƍ����ɓ����炵�������������

 ============= �����_�̌��_ ===============
 �|3������x�̃A�N�Z�X�Ȃ烊�t���N�V�����ƒ��ڌďo���̍���2ms���x�Ȃ̂ŁA�I�����C���A�v���Ȃ���Ȃ��B�o�b�`�͂���������������K�v�����邩������Ȃ��B
 �@�@�|�@���ڌďo����3����̃Q�b�^�[�Z�b�^�[�Ŗ�1ms���x(�������A�Ӑ}���Ȃ��œK����h�����߂�switch���̕��ׂ��������Ă��܂��Ă���B
�@�@�@�@�@switch�������œ����Q�b�^�[�Z�b�^�[��3����A�N�Z�X����󋵂ł�1ms���x�������̂ŁAswitch���ɂ�镉�ׂ͖������Ă悢�ƍl����)
�@�@�|�@���t���N�V�����Ăяo���ɂ��������@������Ȃ��ōł����\���ǂ����@�ł��A3ms���x
�@�@�|�@�܂�A���t���N�V�����͒��ڌďo���ɔ�ׂ�3�{�x���B�������A3����Ăяo����2ms�̍��ł���B

 �|�@���t���N�V�����ɂ��Q�Ƃ��g�������\�b�h�̌Ăяo����t�B�[���h�ւ̃A�N�Z�X��������A���̎Q�Ƃ��擾���镉�S��1.5~9�{���x������
 �@�@�܂�A���\�b�h��t�B�[���h�ւ̎Q�Ƃ����炩���ߎ擾���ăL���b�V�����Ă����ƁA���t���N�V�����̐��\�𒘂������コ���邱�Ƃ��ł���
 �|�@���t���N�V�����ɂ����āA���\�b�h��t�B�[���h�ւ̎Q�Ƃ��擾����̂̓t�B�[���h�̕����������A�Q�Ƃ��g���Ď��ۂɃ��\�b�h���Ăяo������
 �@�@�t�B�[���h�ɃA�N�Z�X����̂� ���\�b�h�̂ق��������B�ΏƓI�ɁA���ڌĂяo���ł́A�Q�b�^�[�Z�b�^�[���g�킸���ڃt�B�[���h�ɃA�N�Z�X�����ق��������B�A
 �@�@�������A���̍���3����A�N�Z�X��2ms���x�Ȃ̂ŁA�債�����ł͂Ȃ��B
 */
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class ReflectionBenchMark {
	private  final int RUNS = 30000;
	private A[] as = new A[RUNS];
	private B[] bs = new B[RUNS];
	
	private Field[] sourceFields = new Field[RUNS];
	private Field[] destinationFields = new Field[RUNS];
	
	private Method[] getters = new Method[RUNS];
	private Method[] setters = new Method[RUNS];
	
	private MethodHandle[] mh_getters = new MethodHandle[RUNS];
	private MethodHandle[] mh_setters = new MethodHandle[RUNS];
	

    {
    	try{
		for (int i = 0; i < RUNS; i++) {
			//�C���X�^���X�͂��炩���ߍ쐬���Ă���
			as[i] = A.class.newInstance();
			bs[i] = B.class.newInstance();
			//Method�ւ̎Q�Ƃ����炩���ߍ쐬���Ă���;
			getters[i] = A.class.getMethod("getF" + String.format("%02d", i%30+1),new Class<?>[]{});
			setters[i] = B.class.getMethod("setF" + String.format("%02d", i%30+1),String.class);
			//mh_getters[i] = MethodHandles.publicLookup().findVirtual(A.class,"getF"+ String.format("%02d", i%30+1),MethodType.methodType(String.class));
			//mh_setters[i] = MethodHandles.publicLookup().findVirtual(B.class,"setF"+ String.format("%02d", i%30+1),MethodType.methodType(void.class, String.class));

			sourceFields[i] = A.class.getDeclaredField("f" + String.format("%02d", i%30+1));
			sourceFields[i].setAccessible(true);
			destinationFields[i] = B.class.getDeclaredField("f" + String.format("%02d", i%30+1));
			destinationFields[i].setAccessible(true);

			mh_getters[i] = MethodHandles.lookup().unreflectGetter(sourceFields[i]);
			mh_setters[i] = MethodHandles.lookup().unreflectSetter(destinationFields[i]);
			}
    	}catch(Exception e){
    		e.printStackTrace();
    		System.exit(1);
    	}

    	
    }	
	public static void main(String[] args) throws Exception {
		ReflectionBenchMark ap = new ReflectionBenchMark();
		
		for (int i=1; i <= 50; i++) {
			System.out.printf("*** ROUND %2d ***\n",i);
			//���t���N�V��������
			ap.doRegular( i );
			//�Q�b�^�[�Z�b�^�[�ւ�Method�̃��t���N�V�����g�p�B������Method�̃C���X�^���X�͓s�x�쐬
			ap.doReflectionMethodWithoutReferenceOptimized( i );
			//Field�̃��t���N�V�����g�p�B������Field�̃C���X�^���X�͓s�x�쐬			
			ap.doReflectionFieldWithouReferenceOptimized( i );
			//�Q�b�^�[�Z�b�^�[�ւ�Method�̃��t���N�V�����g�p�BMethod�̃C���X�^���X�͂��炩���ߍ쐬
			ap.doReflectionMethodWithReferenceOptimized( i );
			//Field�̃��t���N�V�����g�p�BField�̃C���X�^���X�͂��炩���ߍ쐬
			ap.doReflectionFieldWithReferenceOptimized( i );
			//Field�̃��t���N�V�����g�p�BField�̃C���X�^���X�͂��炩���ߍ쐬�B
			//����ɁA������A���̕��ׂƃ��t���N�V�����̕s���ׂ邽�߂ɁA�C�ӂ������𖈉�A�����Ă���Z�b�^�[���Ă�
			ap.doStringConcatAsBenchMark( i );
			//(�z��͂��Ă��Ȃ���)���t���N�V�����ɂ��C���X�^���X���̕��ׂ��v������
			ap.doNewInstanceWithReflectionAsBenchMark(i);
			//System.gc();
			ap.doMethodHandleWithReferenceOptimized(i);
		}


	}

	public void doRegular(int round) throws Exception {
		
		long start = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			//bs[i].setF01( as[i].getF01() ); //Swtich���̕��ו����擾���邽�߂̖��ߕ�

			switch (i%30+1) {
			case 1:
				bs[i].setF01( as[i].getF01() );				
				break;
			case 2:
				bs[i].setF02( as[i].getF02() );				
				break;
			case 3:
				bs[i].setF03( as[i].getF03() );				
				break;
			case 4:
				bs[i].setF04( as[i].getF04() );				
				break;
			case 5:
				bs[i].setF05( as[i].getF05() );				
				break;
			case 6:
				bs[i].setF06( as[i].getF06() );				
				break;
			case 7:
				bs[i].setF07( as[i].getF07() );				
				break;
			case 8:
				bs[i].setF08( as[i].getF08() );				
				break;
			case 9:
				bs[i].setF09( as[i].getF09() );				
				break;
			case 10:
				bs[i].setF10( as[i].getF10() );				
				break;
			case 11:
				bs[i].setF11( as[i].getF11() );				
				break;
			case 12:
				bs[i].setF12( as[i].getF12() );				
				break;
			case 13:
				bs[i].setF13( as[i].getF13() );				
				break;
			case 14:
				bs[i].setF14( as[i].getF14() );				
				break;
			case 15:
				bs[i].setF15( as[i].getF15() );				
				break;
			case 16:
				bs[i].setF16( as[i].getF16() );				
				break;
			case 17:
				bs[i].setF17( as[i].getF17() );				
				break;
			case 18:
				bs[i].setF18( as[i].getF18() );				
				break;
			case 19:
				bs[i].setF19( as[i].getF19() );				
				break;
			case 20:
				bs[i].setF20( as[i].getF20() );				
				break;
			case 21:
				bs[i].setF21( as[i].getF21() );				
				break;
			case 22:
				bs[i].setF22( as[i].getF22() );				
				break;
			case 23:
				bs[i].setF23( as[i].getF23() );				
				break;
			case 24:
				bs[i].setF24( as[i].getF24() );				
				break;
			case 25:
				bs[i].setF25( as[i].getF25() );				
				break;
			case 26:
				bs[i].setF26( as[i].getF26() );				
				break;
			case 27:
				bs[i].setF27( as[i].getF27() );				
				break;
			case 28:
				bs[i].setF28( as[i].getF28() );				
				break;
			case 29:
				bs[i].setF29( as[i].getF29() );				
				break;
			case 30:
				bs[i].setF30( as[i].getF30() );				
				break;
			default:
				System.exit(1);
				break;
			}
		}

		System.out.printf("%2d %-30s\t%,20d ns%n",round,"���ڌďo",  (System.nanoTime() - start) );
	}

	public  void doReflectionMethodWithoutReferenceOptimized(int round) throws Exception {
		String[] getterNames = new String[RUNS];
		String[] setterNames = new String[RUNS];
		
		for (int i = 0; i < RUNS; i++) {
			//Method���͍쐬���Ă������AMethod�ւ̎Q�Ƃ����炩���ߍ쐬���Ă����Ȃ�
			getterNames[i] = "getF" + String.format("%02d", i%30+1);
			setterNames[i] = "setF" + String.format("%02d", i%30+1);
		}
		long start = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			Method getter = A.class.getMethod(getterNames[i], new Class<?>[]{});
			Method setter = B.class.getMethod(setterNames[i], String.class);
			String val = (String) getter.invoke(as[i], new Object[]{});
			setter.invoke(bs[i], val );
		}
		System.out.printf("%2d %-30s\t%,20d ns%n",round,"���t���N�V�������\�b�h���O�Q�Ǝ擾�E��",(System.nanoTime() - start) );
	}

	public  void doReflectionMethodWithReferenceOptimized(int round) throws Exception {

		long start = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			String val = (String) getters[i].invoke(as[i], new Object[]{});
			setters[i].invoke(bs[i], val );
		}
 		System.out.printf("%2d %-30s\t%,20d ns%n",round,"���t���N�V�������\�b�h���O�Q�Ǝ擾�E�L",(System.nanoTime() - start) );
	}
	
	public  void doReflectionFieldWithouReferenceOptimized(int round) throws Exception {
		Field[] sourceFields = new Field[RUNS];
		Field[] destinationFields = new Field[RUNS];
		
		long start = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			sourceFields[i] = A.class.getDeclaredField("f" + String.format("%02d", i%30+1));
			sourceFields[i].setAccessible(true);
			destinationFields[i] = B.class.getDeclaredField("f" + String.format("%02d", i%30+1));
			destinationFields[i].setAccessible(true);
			
			String val = (String) sourceFields[i].get(as[i]);
			destinationFields[i].set(bs[i], val);
		}
 		System.out.printf("%2d %-30s\t%,20d ns%n",round,"���t���N�V�����t�B�[���h���O�Q�Ǝ擾�E��",(System.nanoTime() - start) );
	}
	
	
	public  void doReflectionFieldWithReferenceOptimized(int round) throws Exception {
		long start = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			String val = (String) sourceFields[i].get(as[i]);
			destinationFields[i].set(bs[i], val);
		}
		
		System.out.printf("%2d %-30s\t%,20d ns%n",round, "���t���N�V�����t�B�[���h���O�Q�Ǝ擾�E�L", (System.nanoTime() - start) );
	}
	
	
	public  void doStringConcatAsBenchMark( int round ) throws Exception {
		String[] dummyString = new String[RUNS];
		
		for (int i = 0; i < RUNS; i++) {
			dummyString[i] = String.format("%02d", i%30+1);
		}
		long start = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			//������̑���ɑ΂���A���t���N�V�����̃I�[�o�[�w�b�h�𑪂邽�߂ɁA�C�ӂ̕�����A������
			String val = (String) sourceFields[i].get(as[i]) + dummyString[i];
			destinationFields[i].set(bs[i], val);
		}
		
		System.out.printf("%2d %-30s\t%,20d ns%n",round, "���t���N�V�����t�B�[���h���O�Q�Ǝ擾�E�L�{�����A��", (System.nanoTime() - start) );
	}
	
	public  void doNewInstanceWithReflectionAsBenchMark(int round) throws Exception {
		String[] dummyString = new String[RUNS];
		
		long start = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			as[i] = A.class.newInstance();
			bs[i] = B.class.newInstance();
			String val = (String) sourceFields[i].get(as[i]) + dummyString[i];
			destinationFields[i].set(bs[i], val);
		}
		
		System.out.printf("%2d %-30s\t%,20d ns%n",round, "���t���N�V�����t�B�[���h���O�Q�Ǝ擾�E�L�{�����A���{�C���X�^���X����", (System.nanoTime() - start) );
	}
	

	

	public  void doMethodHandleWithReferenceOptimized(int round) throws Exception {
       
		long start = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			try {
				String val = (String) mh_getters[i].invoke(as[i]);
				mh_setters[i].invoke(bs[i],val);
				//String val = (String) mh_getter.invoke(as[i]);
				//mh_setter.invoke(bs[i],val);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
 		System.out.printf("%2d %-30s\t%,20d ns%n",round,"���\�b�h�n���h�����O�Q�Ǝ擾�E�L",(System.nanoTime() - start) );
	}
	
	
	//MethodHandle���ł��œK���E������������@ final�B���I�ɌĂяo����ύX���邱�Ƃ��ł��Ȃ��̂ŁA����͋p��
	private static final MethodHandle mh_getter = createMethodHandle(A.class,"getF01",MethodType.methodType(String.class));
	private static final MethodHandle mh_setter = createMethodHandle(B.class,"setF01",MethodType.methodType(void.class, String.class));
	private static MethodHandle createMethodHandle(Class cls, String methodName, MethodType methodType ){
        try {
            return MethodHandles.lookup().findVirtual( cls, methodName , methodType );
        } catch ( Throwable e ) {
            e.printStackTrace();

            return null;
        }
	}
}
