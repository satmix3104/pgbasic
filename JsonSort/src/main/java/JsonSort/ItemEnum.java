package JsonSort;

/**
 * 並べ替えたい項目として入力された数字をtest.jsonの項目名に変換するenum
 * 
 * @author Harada
 * @version 1.0
 */
public enum ItemEnum {

	// ソートしたい項目のenum
	NO("no", "0"), KURASU("kurasu", "1"), AGE("age", "2"), VAL("val", "3");

	// enumの中身
	private String itemE;
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
	 * scanに入力された数値に対応する項目をEnumで返すメソッド
	 * 
	 * @param scanE scanTに入力された文字列
	 * @return te scanTと一致する文字列を持つenamのインスタンス
	 */
	public static ItemEnum getByScanE(String scanE) {

		for (ItemEnum ie : ItemEnum.values()) { // 拡張for文による走査
			if (ie.getScanE().equals(scanE)) {
				return ie; // 条件に一致するインスタンスを返す
			}
		}
		// 見つからなかったら例外処理にかける
		return null;
	}
}
