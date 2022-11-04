package jp.co.wiss1.util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.fasterxml.jackson.databind.JsonNode;

import jp.co.wiss1.jsonsort.Sortjson;


public class Itemsort{

	// Comparatorにitemとorderを渡すためのgetter/setter
	private static Sortjson sv = new Sortjson();
	public static ArrayList<String> preSort(JsonNode json, ArrayList<String> sortList, final String item, final int order) {

		// 引数をセット
		sv.setItem(item);
		sv.setOrder(order);

		// ソートする項目を抜き出す
		for (JsonNode s : json.get("datas")) {
			sortList.add(s.get(item).asText());
		}

		// ソートメソッドに渡す
		Collections.sort(sortList, Comparator);

		return sortList;
	}

	private static Comparator<String> Comparator = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			// 番号順とクラス順を、string型で比較してソートしたリストを作る
			if (("no".equals(sv.getItem())) || ("kurasu".equals(sv.getItem()))) {
				if (sv.getOrder() == 0) {
					return o1.compareTo(o2); // 昇順
				} else if (sv.getOrder() == 1) {
					return o2.compareTo(o1); // 降順
				}
				// 年齢順と点数順を、int型で比較してソートしたリストを作る
			} else if (("age".equals(sv.getItem())) || ("val".equals(sv.getItem()))) {
				if (sv.getOrder() == 0) {
					return Integer.valueOf(o1).compareTo(Integer.valueOf(o2)); // 昇順
				} else if (sv.getOrder() == 1) {
					return Integer.valueOf(o2).compareTo(Integer.valueOf(o1)); // 降順
				}
			}
			// コンパイルエラーを消すための到達不能ダミー
			System.out.println("Comparatorメソッドで想定外のエラーが発生しました\n");
			return -1;
		}
	};

}
