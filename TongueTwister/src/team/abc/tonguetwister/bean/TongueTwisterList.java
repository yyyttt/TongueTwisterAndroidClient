package team.abc.tonguetwister.bean;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author zsc
 *	单例设计模式，可保证只读文件一次
 */
public class TongueTwisterList {

	private static List<TongueTwister> ttl = new ArrayList<TongueTwister>();

	private TongueTwisterList() {

	}

	public static List<TongueTwister> getTTList() {
		return ttl;
	}
}
