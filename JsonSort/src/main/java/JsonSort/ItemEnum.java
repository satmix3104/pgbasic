package JsonSort;

/**
 * 並べ替えたい項目として入力された数字をtest.jsonの項目名に変換するenum
 * 
 * @author Harada
 * @version 1.2
 */
public enum ItemEnum {

	// ソートしたい項目のenum
	// 0:番号
	NO("no", "0"),
	// 1:クラス
	KURASU("kurasu", "1"),
	// 2:年齢
	AGE("age", "2"),
	// 3:点数
	VAL("val", "3");

	// 項目の名前
	private String item;
	// 項目を指定する数字
	private String itemNo;

	/**
	 * コンストラクタ
	 */
	private ItemEnum(String item, String itemNo) {
		this.item = item;
		this.itemNo = itemNo;
	}

	/**
	 * 並べ替えたい項目をenumから文字列で取得するgetter
	 * 
	 * @return item ソートしたい項目の文字列
	 */
	public String getItem() {
		return item;
	}

	/**
	 * 並べ替えたい項目に対応する数値をenumから取得するgetter
	 * 
	 * @return itemNo ソートしたい項目に対応する数値の文字列
	 */
	public String getItemNo() {
		return itemNo;
	}

	/**
	 * 渡された文字列の数値に対応する項目をenumで返すメソッド
	 * 
	 * @param itemNo 並べ替えたい項目に対応する数値の文字列
	 * @return ie itemNoと一致する数値を持つenamのインスタンス
	 */
	public static ItemEnum getByItemNo(String itemNo) {

		// 拡張for文による捜索
		for (ItemEnum ie : ItemEnum.values()) {
			if (ie.getItemNo().equals(itemNo)) {
				// 条件に一致するインスタンスを返す
				return ie;
			}
		}
		// 見つからなかったら例外処理にかける
		System.out.println("入力情報が不正です。\n");
		throw new IllegalArgumentException();
	}
}
