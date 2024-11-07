package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public String requestProductSelect() {
        System.out.println(Statement.PRODUCT_SELECT_STATEMENT.message);
        return Console.readLine();
    }
}
