package JsonSort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * ソートする項目をリストに取り出し、昇順か降順でソートして返すクラス
 * 
 * @author Harada
 * @version 1.0
 */
public class SortItemUtil {

	// Comparatorに渡すためにクラス変数にする
	private String itemST;
	private int orderST;

	/**
	 * 項目ソートした順番を配列で取得する
	 * 
	 * @param json     test.jsonの入ったJsonNode
	 * @param sortList ソートしたい項目を並べ替えるための配列
	 * @param item     ソートしたい項目
	 * @param order    ソートする順序
	 * @return sortList ソートが完了した配列
	 */
	public ArrayList<String> preSort(JsonNode json, ArrayList<String> sortList, final String item, final int order) {

		// クラス変数に代入
		itemST = item;
		orderST = order;

		// ソートする項目を抜き出す
		for (JsonNode s : json.get("datas")) {
			sortList.add(s.get(item).asText());
		}

		// ソートメソッドに渡す
		Collections.sort(sortList, Comparator);

		return sortList;
	}

	/**
	 * ソートしたい順序にしたがって配列を入れ替える
	 */
	private Comparator<String> Comparator = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			if (("no".equals(itemST)) || ("kurasu".equals(itemST))) {// 番号順とクラス順を、string型で比較してソートしたリストを作る
				if (orderST == 0) {
					return o1.compareTo(o2);
				} else if (orderST == 1) {
					return o2.compareTo(o1);
				}
			} else if (("age".equals(itemST)) || ("val".equals(itemST))) {// 年齢順と点数順を、int型で比較してソートしたリストを作る
				if (orderST == 0) {
					return Integer.valueOf(o1).compareTo(Integer.valueOf(o2));
				} else if (orderST == 1) {
					return Integer.valueOf(o2).compareTo(Integer.valueOf(o1));
				}
			}
			return -1;// コンパイルエラーを消すためのダミー
		}
	};

}
