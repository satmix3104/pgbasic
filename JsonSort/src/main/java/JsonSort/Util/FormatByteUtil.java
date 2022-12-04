package JsonSort.Util;

import java.nio.charset.Charset;

/**
 * コンソールに出力する時に、byte長を基準に列を揃えるクラス
 * 
 * @author Harada
 * @version 1.0
 */
public class FormatByteUtil {

	/**
	 * 文字列のbyte長を考慮して列を揃えるためのメソッド
	 * 
	 * @param target   表示する文字列
	 * @param length   半角何文字分まで埋めるか指定した数値
	 * @param isHyphen "-"をつけるならtrue
	 * @return 文末が調整された文字列
	 */
	public static String format(String target, int length, boolean isHyphen) {
		int byteDiff = (getByteLength(target, Charset.forName("UTF-8")) - target.length()) / 2;
		String hyphen = "";
		if (isHyphen) {
			hyphen = "-";
		}
		return String.format("%" + hyphen + (length - byteDiff) + "s", target);
	}

	/**
	 * byte長を計算するメソッド
	 * 
	 * @param target  表示する文字列
	 * @param charset UTF-8
	 * @return targetのバイト長
	 */
	private static int getByteLength(String target, Charset charset) {
		return target.getBytes(charset).length;
	}
}
