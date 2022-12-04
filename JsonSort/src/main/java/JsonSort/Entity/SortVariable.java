package JsonSort.Entity;

/**
 * 設定したソート項目と昇順降順を返すクラス
 * 
 * @author Harada
 * @version 1.2
 */
public class SortVariable {

	// ソートする項目の物理名
	private String item;
	// ソートする項目の論理名
	private String itemName;
	// ソートする項目の番号
	private int itemNo;

	// ソートする順番の物理名
	private String order;
	// ソートする順番の論理名
	private String orderName;
	// ソートの順番の番号
	private int orderNo;

	// getter

	/**
	 * ソートしたい項目を英小文字列で取得するgetter
	 * 
	 * @return item set済のソートしたい項目の物理名
	 */
	public String getItem() {
		return this.item;
	}

	/**
	 * ソートしたい項目を英大文字列で取得するgetter
	 * 
	 * @return item set済のソートしたい項目の物理名
	 */
	public String getEnumItem() {
		return this.item.toUpperCase();
	}

	/**
	 * ソートしたい項目を日文字列で取得するgetter
	 * 
	 * @return itemName set済のソートしたい項目の論理名
	 */
	public String getItemName() {
		return this.itemName;
	}

	/**
	 * ソートしたい項目を数値で取得するgetter
	 * 
	 * @return itemNo set済のソートしたい項目の数値
	 */
	public int getItemNo() {
		return this.itemNo;
	}

	/**
	 * ソートしたい順序を英小文字列で取得するgetter
	 * 
	 * @return order set済のソートしたい順序の物理名
	 */
	public String getOrder() {
		return this.order;
	}

	/**
	 * ソートしたい順序を英大文字列で取得するgetter
	 * 
	 * @return order set済のソートしたい順序の物理名
	 */
	public String getEnumOrder() {
		return this.order.toUpperCase();
	}

	/**
	 * ソートしたい順序を日文字列で取得するgetter
	 * 
	 * @return orderName set済のソートしたい順序の論理名
	 */
	public String getOrderName() {
		return this.orderName;
	}

	/**
	 * ソートしたい順序を数値で取得するgetter
	 * 
	 * @return orderNo set済のソートしたい順序の数値
	 */
	public int getOrderNo() {
		return this.orderNo;
	}

	// setter

	/**
	 * ソートしたい項目の物理名のsetter
	 * 
	 * @param item ソートしたい項目の英文字列
	 */
	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * ソートしたい項目の論理名のsetter
	 * 
	 * @param itemName ソートしたい項目の日文字列
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * ソートしたい項目の数値のsetter
	 * 
	 * @param itemNo ソートしたい項目の数値(int)
	 */
	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	/**
	 * ソートしたい項目の数値のsetter
	 * 
	 * @param itemNo ソートしたい項目の数値(String)
	 */
	public void setItemNo(String itemNo) {
		this.itemNo = Integer.parseInt(itemNo);
	}

	/**
	 * ソートしたい順序の数値のsetter
	 * 
	 * @param order ソートしたい順序の英文字列
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * ソートしたい順序の数値のsetter
	 * 
	 * @param orderName ソートしたい順序の日文字列
	 */
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	/**
	 * ソートしたい順序の数値のsetter
	 * 
	 * @param orderNo ソートしたい順序の数値(int)
	 */
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * ソートしたい順序の数値のsetter
	 * 
	 * @param orderNo ソートしたい順序の数値(String)
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = Integer.parseInt(orderNo);
	}

}
