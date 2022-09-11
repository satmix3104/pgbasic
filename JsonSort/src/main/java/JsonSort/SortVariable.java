package JsonSort;

/**
 * SortTaskで設定するソート項目と昇順降順を返すクラス
 * 
 * @author Harada
 * @version 1.0
 */
public class SortVariable {

	// ソートする項目
	private String itemV;
	// ソートの昇順降順
	private int orderV;

	/**
	 * ソートしたい項目を文字列で取得するgetter
	 */
	public String getItem() {
		return this.itemV;
	}

	/**
	 * ソートの順序を数値で取得するgetter
	 */
	public int getOrder() {
		return this.orderV;
	}

	/**
	 * ソートしたい項目のsetter
	 * 
	 * @param itemV enumから取得されたソートしたい項目の文字列
	 */
	public void setItem(String itemV) {
		this.itemV = itemV;
	}

	/**
	 * ソートする順番のsetter
	 * 
	 * @param orderV scanUに入力されたソート順序の数値の文字列
	 */
	public void setOrder(String orderV) {
		// 半角数字一桁ならセットする
		if ((orderV.length() == 1) && (orderV.matches("^[0-9]*$"))) {
			this.orderV = Integer.parseInt(orderV);
			// 0か1なら呼び出し元に戻る
			if ((this.orderV == 0) || (this.orderV == 1)) {
				return;
			}
		}
		// 半角の0か1以外の不正な値なら例外処理にかける
		this.orderV = Integer.parseInt("error");
	}

}
