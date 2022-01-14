package cn.original.view.console;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;

public class ConsoleEditor extends AppCompatEditText {
    ConsoleEditor(@NonNull ConsoleView consoleView) {
        super(consoleView.getContext());
    }
}
