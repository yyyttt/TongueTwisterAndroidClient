package team.abc.tonguetwister.tools;

import java.util.List;

import team.abc.tonguetwister.bean.TongueTwister;
import team.abc.tonguetwister.constant.Level;

public class GroupTitleUtil {
	
	public static void insertGroupTitle(List<TongueTwister> list) {

		/*int insertPos = 0;
		for (int i = 0; i < Level.GROUP_NUM; i++) {
			insertPos = i * Level.GROUP_TT_NUM + i;
			list.add(insertPos, new TongueTwister(Level.ARRAY[i], ""));
		}*/
		
		int level = 0;
		for(int i = 0 ; i < list.size() ; i++ ){
			if(list.get(i).getId() % Level.GROUP_TT_NUM == 0){								
				list.add(i, new TongueTwister(Level.ARRAY[level++], ""));
				i++;
			}
		}
		

	}
	
	public static int getTruePosition(int pos){
		return pos - pos/(Level.GROUP_TT_NUM+1) - 1;
	}

	public static void insertCollectionGroupTitle(List<TongueTwister> list) {
		
		int level = 0;
		int preLevel = 0;
		int id = -1;
		for(int i = 0 ; i < list.size() ; i++ ){
			id = list.get(i).getId();
			level = id / Level.GROUP_TT_NUM;
			
			if(i == 0){
				list.add(i, new TongueTwister(Level.ARRAY[level], ""));
				i++;
			}else{
				if(level != preLevel){					
					list.add(i, new TongueTwister(Level.ARRAY[level], ""));
					i++;
				}
			}
			
			preLevel = level;
			
		}
		
	}
}
