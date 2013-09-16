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
public class TemperatureSpinner extends Dialog {
    private Context context;
    private double temp;
    private NumberPicker np1;
    private NumberPicker np2;

    private int numberRangeMin;
    private int numberRangeMax;
    private int decimalRangeMin;
    private int decimalRangeMax;

    private onSetEvent setListener;

    public interface onSetEvent {
        public void onSetListener(double endValue);
    }

    public TemperatureSpinner(Context context, double temperature, int[] numberRange, int[] decimalRange, onSetEvent eventListener) {
        super(context);
        this.context = context;
        this.temp = temperature;

        this.numberRangeMin = numberRange[0];
        this.numberRangeMax = numberRange[1];
        this.decimalRangeMin = decimalRange[0];
        this.decimalRangeMax = decimalRange[1];

        this.setListener = eventListener;
    }

    private void setValue(double temp) {
        final int number = (int)temp;
        final int decimal = (int)((temp - number) * 10);

        this.np1.setValue(number);
        this.np2.setValue(decimal);
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Dialog dialogSearchByDate = this;
        dialogSearchByDate.setContentView(R.layout.dialog_temp_spinner);

        NumberPicker np1 = (NumberPicker)dialogSearchByDate.findViewById(R.id.numberP1);
        NumberPicker np2 = (NumberPicker)dialogSearchByDate.findViewById(R.id.numberP2);
        this.np1 = np1;
        this.np2 = np2;

        np1.setMinValue(this.numberRangeMin); np1.setMaxValue(numberRangeMax);
        np2.setMinValue(this.decimalRangeMin); np2.setMaxValue(this.decimalRangeMax);

        np1.setOnValueChangedListener(new ChangeListener());
        np2.setOnValueChangedListener(new ChangeListener());

        Button button = (Button)findViewById(R.id.setButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TemperatureSpinner.this.setListener != null) {
                    TemperatureSpinner.this.setListener.onSetListener(getValue());
                }
            }
        });


        setValue(this.temp);

        updateTitle();
    }

    class ChangeListener implements NumberPicker.OnValueChangeListener {
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            updateTitle();
        }
    }


    public double getValue() {
        return this.np1.getValue() + (((double)this.np2.getValue())/10);
    }

    private void updateTitle() {
        final int number1 = this.np1.getValue();
        final int number2 = this.np2.getValue();

        this.setTitle(String.valueOf(number1) + "." + String.valueOf(number2));
    }
}
