package jp.co.wiss1.jsonsort;

public enum Item{


		NO("no", "0"),
		CRASS("kurasu", "1"),
		AGE("age", "2"),
		VAL("val", "3");

		private String item;
		private String itemNo;

		
		private Item(String item, String itemNo) {
			this.item = item;
			this.itemNo = itemNo;
		}

		
		public String getItem() {
			return item;
		}

		
		public String getItemNo() {
			return itemNo;
		}

		
		public static Item getByItemNo(String itemNo) {

			// 拡張for文による捜索
			for (Item ie : Item.values()) {
				if (ie.getItemNo().equals(itemNo)) {
					// 条件に一致するインスタンスを返す
					return ie;
				}
			}
			// 見つからなかったら例外処理にかける
			System.out.println("入力情報が不正です。\n");
			System.exit(0);
			return null;
		}
	}