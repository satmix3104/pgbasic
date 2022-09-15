package JsonSort;

/**
 * 並べ替えたい項目として入力された数字をtest.jsonの項目名に変換するenum
 * 
 * @author Harada
 * @version 1.1
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
	private String itemE;
	// 項目を指定する数字
	private String scanE;

	/**
	 * コンストラクタ
	 */
	private ItemEnum(String itemE, String scanE) {
		this.itemE = itemE;
		this.scanE = scanE;
	}

	/**
	 * 並べ替えたい項目をenumから文字列で取得するgetter
	 */
	public String getItemE() {
		return itemE;
	}

	/**
	 * 並べ替えたい項目に対応する数値をenumから取得するgetter
	 */
	public String getScanE() {
		return scanE;
	}

	/**
	 * scanに入力された数値に対応する項目をenumで返すメソッド
	 * 
	 * @param scanE scanTに入力された文字列
	 * @return te scanTと一致する数字を持つenamのインスタンス
	 */
	public static ItemEnum getByScanE(String scanE) {

		// 拡張for文による捜索
		for (ItemEnum ie : ItemEnum.values()) {
			if (ie.getScanE().equals(scanE)) {
				// 条件に一致するインスタンスを返す
				return ie;
			}
		}
		// 見つからなかったら例外処理にかける
		return null;
	}
}
