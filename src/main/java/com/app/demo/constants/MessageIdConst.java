package com.app.demo.constants;

public class MessageIdConst {

    /**
     * インスタンス生成不可
     */
    private MessageIdConst() {
    }

    /**
     * エラー：日付不整合.
     */
    public static final String E_INVALID_TERM = "error.invalidterm";

    /**
     * エラー：該当データ無し.
     */
    public static final String E_DATA_NOT_FOUND = "error.dataNotFound";

    /**
     * エラー：SQL発行.
     */
    public static final String E_SQL_ISSUE = "error.sql.issue";

    /**
     * INFO：登録成功.
     */
    public static final String I_INSERT_SUCCESS = "info.insert.success";

    /**
     * INFO：取得成功.
     */
    public static final String I_GETTING_SUCCESS = "info.getting.success";

    /**
     * INFO：更新成功.
     */
    public static final String I_UPDATE_SUCCESS = "info.update.success";

    /**
     * INFO：削除成功.
     */
    public static final String I_DELETE_SUCCESS = "info.delete.success";

    /**
     * INFO：保存成功.
     */
    public static final String I_SAVE_SUCCESS = "info.save.success";

    /**
     * INFO：ダウンロード成功.
     */
    public static final String I_DOWNLOAD_SUCCESS = "info.download.success";
}