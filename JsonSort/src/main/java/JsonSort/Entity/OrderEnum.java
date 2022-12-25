package JsonSort.Entity;

/**
 * 並べ替えたい順番として入力された数字をtest.jsonの昇順降順に変換するenum
 * 
 * @author Harada
 * @version 1.0
 */
public enum OrderEnum {

	// ソートしたい順番のenum
	/** 昇順:0 */
	ASC("asc", "昇順", "0"),
	/** 降順:1 */
	DESC("desc", "降順", "1");
	
	// 項目の物理名
	private String order;
	// 項目の論理名
	private String orderName;
	// 項目を指定するID
	private String orderNo;

	/**
	 * コンストラクタ
	 * @param order 項目の物理名
	 * @param orderName 項目の論理名
	 * @param orderNo 項目を指定するID
	 */
	private OrderEnum(String order, String orderName, String orderNo) {
		this.order = order;
		this.orderName = orderName;
		this.orderNo = orderNo;
	}

	/**
	 * 並べ替えたい順番をenumから文字列で取得するgetter
	 * 
	 * @return order ソートしたい順番の英文字列
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 並べ替えたい順番をenumから文字列で取得するgetter
	 * 
	 * @return orderName ソートしたい順番の日文字列
	 */
	public String getOrderName() {
		return orderName;
	}

	/**
	 * 並べ替えたい順番に対応する数値をenumから取得するgetter
	 * 
	 * @return orderNo ソートしたい順番に対応する数値の文字列
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * 渡された文字列の数値に対応する項目をenumで返すメソッド
	 * 
	 * @param orderNo 並べ替えたい順番に対応する数値の文字列
	 * @return oe orderNoと一致する数値を持つenamのインスタンス
	 * @throws IllegalArgumentException 入力されたorderNoを持つEnumが存在しなかった場合の例外
	 */
	public static OrderEnum getByOrderNo(String orderNo) {

		// 拡張for文による捜索
		for (OrderEnum oe : OrderEnum.values()) {
			if (oe.getOrderNo().equals(orderNo)) {
				// 条件に一致するインスタンスを返す
				return oe;
			}
		}
		// 見つからなかったら例外処理にかける
		System.out.println("入力情報が不正です。\n");
		throw new IllegalArgumentException();
	}
}
