package JsonSort.Util;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import JsonSort.Entity.ItemEnum;
import JsonSort.Entity.OrderEnum;
import JsonSort.Entity.SortVariable;

/**
 * ソートした条件と日時をSQLで参照・登録するクラス
 * 
 * @author Harada
 * @version 1.0
 */
public class JsonDaoUtil {

	// SQL操作
	final static String url = "jdbc:mysql://localhost/JsonDB?useSSL=false&serverTimezone=Japan&useUnicode=true&characterEncoding=utf8";
	final static String user = "root";
	final static byte[] code = { 109, 121, 115, 113, 108 }; // パスワードを文字列で直書きするのもなと思ったので…
	final static String insert = "insert into JsonDB.SORT_HISTORY (sort, sortType, addDate) values (?,?,now());";
	final static String exists = "select id from JsonDB.SORT_HISTORY limit 1;";
	final static String select = "select * from JsonDB.SORT_HISTORY order by addDate DESC limit 10;";

	/**
	 * 直近10件分のソート条件を表示するクラス
	 * 
	 * @throws SQLException SQL操作に失敗した場合の例外
	 */
	public static void showRecord() {

		// データベース接続
		try (Connection conn = DriverManager.getConnection(url, user, password())) {
			Statement stat = conn.createStatement();
			// データが存在するかどうかを確認
			ResultSet rs = stat.executeQuery(exists);
			if (rs.next()) {
				// 最新の10件を出力
				rs = stat.executeQuery(select);
				System.out.println("直近10件の履歴を表示します。\n並替日時                 並替項目  並替種別");
				while (rs.next()) {
					System.out.printf("%tY年%<tm月%<td日 %<tT  ", rs.getTimestamp(4));
					ItemEnum ie = ItemEnum.getByItemNo(rs.getString(2)); // 区分値を項目名に変換
					System.out.print(FormatByteUtil.format(ie.getItemName(), 10, true));
					OrderEnum oe = OrderEnum.getByOrderNo(rs.getString(3)); // 区分値を項目名に変換
					System.out.println(oe.getOrderName());
				}
				System.out.println();
			} else {
				// データが無ければ表示
				System.out.println("直近の履歴表情報はありません。");
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("パスワードが間違っています。");
		} catch (SQLException e) {
			System.out.println("直近の履歴表情報を取得できませんでした。");
		}
	}

	/**
	 * ソートした条件をDBに登録する
	 * 
	 * @param sv ソートした項目と順序
	 * @throws SQLException SQL操作に失敗した場合の例外
	 */
	public static void insertRecord(SortVariable sv) {

		// データベース接続
		try (Connection conn = DriverManager.getConnection(url, user, password())) {
			// レコードを登録
			try (PreparedStatement ps = conn.prepareStatement(insert)) {
				conn.setAutoCommit(false);
				ps.setInt(1, sv.getItemNo());
				ps.setInt(2, sv.getOrderNo());
				ps.executeUpdate();
				conn.commit();
				System.out.println("DBにログを登録しました。");
			} catch (SQLException e) {
				conn.rollback();
				System.out.println(e + "\nDBへのログ登録に失敗しました。");
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("パスワードが間違っています。");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	/**
	 * パスワードを復号する
	 * 
	 * @return 復号した文字列
	 * @throws UnsupportedEncodingException byteの復号に失敗した場合の例外
	 */
	private static String password() throws UnsupportedEncodingException {
		return new String(code, "UTF-8");
	}

}