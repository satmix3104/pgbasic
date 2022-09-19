package JsonSort;

/**
 * SortTaskで設定するソート項目と昇順降順を返すクラス
 * 
 * @author Harada
 * @version 1.1
 */
public class SortVariable {

	// ソートする項目
	private String item;
	// ソートの昇順降順
	private int order;

	/**
	 * ソートしたい項目を文字列で取得するgetter
	 * 
	 * @return item set済のソートしたい項目
	 */
	public String getItem() {
		return this.item;
	}

	/**
	 * ソートの順序を数値で取得するgetter
	 * 
	 * @return order set済のソートしたい順序
	 */
	public int getOrder() {
		return this.order;
	}

	/**
	 * ソートしたい項目のsetter
	 * 
	 * @param item ソートしたい項目の文字列
	 */
	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * ソートしたい順序のsetter
	 * 
	 * @param order ソートしたい順序の数値
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * 渡された文字列をソートする順序の数値に変換するsetter
	 * 
	 * @param order メソッドに渡されたソート順序の数値の文字列
	 */
	public void setToOrder(String order) {
		// 半角数字一桁ならセットする
		if ((order.length() == 1) && (order.matches("^[0-9]*$"))) {
			try {
				this.order = Integer.parseInt(order);
				// 0か1なら呼び出し元に戻る
				if ((this.order == 0) || (this.order == 1)) {
					return;
				}
			} catch (NumberFormatException e) {
				System.out.println("想定外のエラーです\n" + e);
			}
		}
		// 半角の0か1以外の不正な値なら例外処理にかける
		System.out.println("入力情報が不正です。\n");
		throw new IllegalArgumentException();
	}

}
