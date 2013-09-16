package com.tado.androiddialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

/**
 * Created with IntelliJ IDEA.
 * User: neto
 * Date: 9/12/13
 * Time: 5:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class NumberDialog extends Dialog {
    private Context context;
    private int number;

    private NumberPicker n1;
    private NumberPicker n2;
    private NumberPicker n3;
    private NumberPicker n4;

    private onSetEvent setListener;

    private String currency;

    public interface onSetEvent {
        public void onSetListener(int endValue);
    }

    public NumberDialog(Context context, int number, String currency, onSetEvent eventListener) {
        super(context);
        this.context = context;
        this.number = number;
        this.currency = currency;

        this.setListener = eventListener;
    }

    private void setUpMaxMin(NumberPicker np) {
        np.setMinValue(0);
        np.setMaxValue(9);
    }

    private void setValue(int value) {
        String nStr = String.valueOf(value);
        while (nStr.length() < 4) {
            nStr = "0" + nStr;
        }
        char[] splitNumber = nStr.toCharArray();

        NumberPicker[] npList = new NumberPicker[] { n1, n2, n3, n4 };
        for (int i = 0; i < 4; i++) {
            npList[i].setValue(Integer.parseInt(String.valueOf(splitNumber[i])));
        }
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Dialog dialogSearchByDate = this;
        dialogSearchByDate.setContentView(R.layout.dialog_number);

        //Configure Hours Column
        NumberPicker n1 = (NumberPicker) dialogSearchByDate.findViewById(R.id.n1);
        NumberPicker n2 = (NumberPicker) dialogSearchByDate.findViewById(R.id.n2);
        NumberPicker n3 = (NumberPicker) dialogSearchByDate.findViewById(R.id.n3);
        NumberPicker n4 = (NumberPicker) dialogSearchByDate.findViewById(R.id.n4);

        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
        this.n4 = n4;

        NumberPicker[] npList = new NumberPicker[] { n1, n2, n3, n4 };
        for (int i = 0; i < 4; i++) {
            setUpMaxMin(npList[i]);

            npList[i].setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    updateTitle();
                }
            });
        }

        setValue(this.number);
        updateTitle();

        Button button = (Button)dialogSearchByDate.findViewById(R.id.set);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NumberDialog.this.setListener != null) {
                    NumberDialog.this.setListener.onSetListener(getValue());
                }
            }
        });
    }

    private int getValue() {
        String number = "";
        int[] values = new int[] { n1.getValue(), n2.getValue(), n3.getValue(), n4.getValue() };
        for (int v : values) {
            number += String.valueOf(v);
        }

        return Integer.valueOf(number);
    }

    private void updateTitle() {
        this.setTitle(currency + " " + String.valueOf(getValue()));
    }
}
