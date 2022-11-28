package jp.co.wiss1.jsonsort;

public class Sortjson{

	// ソートする項目
	private String item;
	// ソートの昇順降順
	private int order;

	
	public String getItem() {
		return this.item;
	}

	public int getOrder() {
		return this.order;
	}

	
	public void setItem(String item) {
		this.item = item;
	}

	
	public void setOrder(int order) {
		this.order = order;
	}

	
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
		System.out.println("入力情報が不正です。\n");
		throw new IllegalArgumentException();
	}

}