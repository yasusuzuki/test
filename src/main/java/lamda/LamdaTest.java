package lamda;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import lamda.reflectiontarget.Search01;

public class LamdaTest {
	ArrayList<Meisai> meisai = new ArrayList<Meisai>();
	ArrayList<Tanpo> tanpo = new ArrayList<Tanpo>();
	ArrayList<Zokusei> zokusei = new ArrayList<Zokusei>();
	
	@Before
	public void init(){
		Collections.addAll(meisai,
		new Meisai("S01","���Q","01"),
		new Meisai("S01","���Q","02"),
		new Meisai("S01","���Q","03"));

		Collections.addAll(tanpo,
		new Tanpo("S01","���Q","01","���S"),
		new Tanpo("S01","���Q","02","���S"),
		new Tanpo("S01","���Q","02","���"),
		new Tanpo("S01","���Q","03","���S"),
		new Tanpo("S01","���Q","03","���"));
		
		Collections.addAll(zokusei,
		new Zokusei("S01","���Q","01","���S","�ی���","100"),
		new Zokusei("S01","���Q","02","���S","�ی���","100"),
		new Zokusei("S01","���Q","02","���","�ی���","100"),
		new Zokusei("S01","���Q","03","���S","�ی���","100"),
		new Zokusei("S01","���Q","03","���","�ی���","100"));	
	}
	
	@Test
	public void test1(){
		System.out.println(" *** Run test1 ***");
		HashMap<String, Tanpo> a = new HashMap<String, Tanpo>();
		tanpo.stream().forEach( i -> a.put(i.getShoban()+i.getShumoku()+i.getMeisai()+i.getTanpo(), i) );
		System.out.println(a);
		
		//map��collect�𗘗p�����`���B�Ȃ��L���X�g���K�v���킩��Ȃ�
		HashMap<String,Tanpo> b =     (HashMap<String, Tanpo>) tanpo.stream()
				//.map( i -> new AbstractMap.SimpleEntry<String,Tanpo>(i.getShoban()+i.getShumoku()+i.getMeisai()+i.getTanpo(),i))
				.collect(Collectors.toMap(i -> i.getShoban()+i.getShumoku()+i.getMeisai()+i.getTanpo(), i -> i));
		System.out.println(b);
		
		//Collectors.groupingBy���g�����`���B�Ȃ��L���X�g���K�v�H
		HashMap<String,List<Tanpo>> c = (HashMap<String, List<Tanpo>>) tanpo.stream()
				.collect( Collectors.groupingBy(i -> i.getShoban()+i.getShumoku()+i.getMeisai()+i.getTanpo()) );
		System.out.println(c);
		
		HashMap<String,?> d = (HashMap<String, ?>) meisai.stream()
				.collect( Collectors.groupingBy(i -> i.getShoban()+i.getShumoku()+i.getMeisai()) );
		System.out.println(d);

		
		ArrayList<String> stream =  (ArrayList<String>) Stream.generate( () -> "aa" ).limit(100).collect(Collectors.toList());
		System.out.println(stream);
	}
	
	
	@Test
	public void test2() {
		System.out.println(" *** Run test2 ***");

		lamdaScope(100).run();
		lamdaScope(200).run();
		
		LamdaFactory.createLamda(Search01.class).run();
	}
	
	//�����_���Q�Ƃ���ϐ��̃X�R�[�v(�ϐ����g����͈�)�́A�����_���`�����N���X�⃁�\�b�h�Ɠ����ɂȂ�B
	//
	public Runnable lamdaScope(int round){ //�����������_�ŎQ�Ɖ\
		int trial = round + 1; // ���[�J���ϐ��������_�ŎQ�Ɖ\
		Runnable runner = () ->{
			//
			tanpo.add(new Tanpo("S01","���Q","01","���S") ); //�e�̃C���X�^���X�ϐ����Q�Ɖ\�B�N���X�^�̓����̕ύX�Ȃ��
			System.out.println(round + " " + trial + " " + tanpo);
		};
		return runner;
	}
}
