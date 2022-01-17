package com.myopicmobile.textwarrior.android;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.myopicmobile.textwarrior.common.DocumentProvider;

public class ClipboardPanel {
    private final boolean DEBUG = false;
    protected FreeScrollingTextField _textField;
    private Context _context;
    private ActionMode _clipboardActionMode;

    public ClipboardPanel(FreeScrollingTextField textField) {
        _textField = textField;
        _context = textField.getContext();

    }

    public Context getContext() {
        return _context;
    }

    public void show() {
        startClipboardAction();
    }

    public void hide() {
        stopClipboardAction();
    }

    @SuppressWarnings("ResourceType")
    public void startClipboardAction() {
        if (_clipboardActionMode == null)
            _textField.startActionMode(new ActionMode.Callback() {

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    _clipboardActionMode = mode;
                    mode.setTitle("选择文本");
                    TypedArray array = _context.getTheme().obtainStyledAttributes(new int[]{
                            android.R.attr.actionModeSelectAllDrawable,
                            android.R.attr.actionModeCutDrawable,
                            android.R.attr.actionModeCopyDrawable,
                            android.R.attr.actionModePasteDrawable,
                    });
                    menu.add(0, 0, 0, _context.getString(android.R.string.selectAll))
                            .setShowAsActionFlags(2)
                            .setAlphabeticShortcut('a');

                    menu.add(0, 1, 0, _context.getString(android.R.string.cut))
                            .setShowAsActionFlags(2)
                            .setAlphabeticShortcut('x');

                    menu.add(0, 2, 0, _context.getString(android.R.string.copy))
                            .setShowAsActionFlags(2)
                            .setAlphabeticShortcut('c');

                    menu.add(0, 3, 0, _context.getString(android.R.string.paste))
                            .setShowAsActionFlags(2)
                            .setAlphabeticShortcut('v');
                    array.recycle();
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case 0:
                            _textField.selectAll();
                            break;
                        case 1:
                            _textField.cut();
                            mode.finish();
                            break;
                        case 2:
                            _textField.copy();
                            mode.finish();
                            break;
                        case 3:
                            _textField.paste();
                            mode.finish();
                            break;
                    }
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode p1) {
                    _textField.selectText(false);
                    _clipboardActionMode = null;
                }
            });
    }

    //注释行
    private void dealComment() {
        DocumentProvider documentProvider = _textField.createDocumentProvider();
        int startRowNum = documentProvider.findRowNumber(_textField.getSelectionStart());
        int endRowNum = documentProvider.findRowNumber(_textField.getSelectionEnd());

        for (int i = startRowNum; i <= endRowNum; i++) {
            if (isLineComment(i)) {
                unCommentRow(i);
            } else {
                commentRow(i);
            }
        }
    }

    private void log(String log) {
        if (DEBUG)
            System.out.println("-------------->" + log);
    }

    /*
     *判断是否为行注释
     */
    public boolean isLineComment(int row) {
        DocumentProvider documentProvider = _textField.createDocumentProvider();
        int rowStart = documentProvider.getRowOffset(row);
        documentProvider.seekChar(rowStart);//调到该行的开始
        int offset = 0;
        while (documentProvider.hasNext()) {
            char ch = documentProvider.next();
            log("ch1 :" + ch + "," + (int) ch);
            if (ch != '/' && ch != ' ')
                return false;
            char nextCh = documentProvider.charAt(rowStart + offset + 1);
            log("nextCh1 :" + nextCh + "," + (int) ch);
            if (ch == '/' && nextCh == '/') {
                return true;
            }
            ++offset;
        }
        return false;
    }

    /**
     * 取消注释
     */
    public void unCommentRow(int row) {
        DocumentProvider documentProvider = _textField.createDocumentProvider();
        int rowStart = documentProvider.getRowOffset(row);
        documentProvider.seekChar(rowStart);//调到该行的开始
        log("rowStart:" + rowStart);
        int offset = 0;
        while (documentProvider.hasNext()) {

            char ch = documentProvider.next();
            log("ch2:" + ch);
            char nextCh = documentProvider.charAt(rowStart + offset + 1);
            log("nextCh2:" + nextCh);
            if (ch == '/' && nextCh == '/') {
                documentProvider.deleteAt(rowStart + offset, System.nanoTime());
                documentProvider.deleteAt(rowStart + offset, System.nanoTime());//删除一个‘/’后，第二个'/'的位置变成了原来第一个的位置
                _textField.respan();
                return;
            }
            ++offset;
        }


    }

    /**
     * 注释
     *
     * @param row
     */
    public void commentRow(int row) {
        DocumentProvider documentProvider = _textField.createDocumentProvider();
        documentProvider.insert(documentProvider.getRowOffset(row), "/");
        documentProvider.insert(documentProvider.getRowOffset(row), "/");
        _textField.respan();
    }

    public void stopClipboardAction() {
        if (_clipboardActionMode != null) {
            _clipboardActionMode.finish();
            _clipboardActionMode = null;
        }
    }

}
