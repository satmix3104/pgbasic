package JsonSort.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.fasterxml.jackson.databind.JsonNode;

import JsonSort.Entity.ItemEnum;
import JsonSort.Entity.OrderEnum;
import JsonSort.Entity.SortVariable;

/**
 * ソートする項目をリストに取り出し、昇順か降順でソートして返すクラス
 * 
 * @author Harada
 * @version 1.2
 */
public class SortItemUtil {

	// Comparatorにitemとorderを渡すための変数
	private static String item;
	private static String order;

	/**
	 * 項目ソートした順番を配列で取得する
	 * 
	 * @param json test.jsonの入ったJsonNode
	 * @param sv   ソートしたい項目と順序
	 * @return sortList ソートが完了した配列
	 */
	public static ArrayList<String> preSort(JsonNode json, SortVariable sv) {

		// 引数を代入
		item = sv.getItem();
		// ソートする項目を抜き出す
		ArrayList<String> sortList = new ArrayList<>();
		for (JsonNode s : json.get("datas")) {
			sortList.add(s.get(item).asText());
		}

		// 引数を再代入
		item = sv.getEnumItem();
		order = sv.getEnumOrder();
		// ソートメソッドに渡す
		Collections.sort(sortList, Comparator);

		return sortList;
	}

	/**
	 * ソートしたい順序にしたがって配列を入れ替える
	 */
	private static Comparator<String> Comparator = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			ItemEnum ie = ItemEnum.valueOf(item);
			OrderEnum oe = OrderEnum.valueOf(order);
			// 番号順とクラス順を、string型で比較してソートしたリストを作る
			if (ie == ItemEnum.NO || ie == ItemEnum.KURASU) {
				if (oe == OrderEnum.ASC) {
					return o1.compareTo(o2); // 昇順
				} else if (oe == OrderEnum.DESC) {
					return o2.compareTo(o1); // 降順
				}
				// 年齢順と点数順を、int型で比較してソートしたリストを作る
			} else if (ie == ItemEnum.AGE || ie == ItemEnum.VAL) {
				if (oe == OrderEnum.ASC) {
					return Integer.valueOf(o1).compareTo(Integer.valueOf(o2)); // 昇順
				} else if (oe == OrderEnum.DESC) {
					return Integer.valueOf(o2).compareTo(Integer.valueOf(o1)); // 降順
				}
			}
			System.out.println("想定外のエラーです");
			return 0;
		}
	};

}
