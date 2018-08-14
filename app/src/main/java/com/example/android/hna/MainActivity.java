/*
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match the package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.hna;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String name = ((EditText) findViewById(R.id.name_text)).getText().toString();
        boolean whippedCreamChecked = checkboxState(R.id.whipped_cream_checkbox);
        boolean chocolateChecked = checkboxState(R.id.chocolate_checkbox);
        int price = calculatePrice(quantity, whippedCreamChecked, chocolateChecked);
        //Log.i("MainActivity", ""+whippedCreamCheckedState);
        //display(quantity);
        //displayPrice(quantity*5);

        String orderSummary = createOrderSummary(name, whippedCreamChecked, chocolateChecked,
                quantity, price);
        displayMessage(orderSummary, name);
    }

    /**
     * Creates order summary string given user input parameters
     *
     * @param name      name of user
     * @param addWhippedCream   whipped cream topping
     * @param addChocolate chocolate topping
     * @param quantity  quantity of coffees orders
     * @param price     price of order
     * @return message string
     */
    private String createOrderSummary(String name,
                                      boolean addWhippedCream,
                                      boolean addChocolate,
                                      int quantity,
                                      int price) {
        String message;
        if (quantity == 0) {
            message = "You need to order something";
        } else {
            message = getResources().getString(R.string.order_summary_name, name);//"Name: " + lName;
            message += "\n" + getResources().getString(R.string.order_summary_whipped_cream, addWhippedCream);//lWhipped;
            message += "\n" + getResources().getString(R.string.order_summary_chocolate, addChocolate);//lChocolate;
            message += "\n" + getResources().getString(R.string.order_summary_quantity, quantity);//lQuantity;
            message += "\n" + getResources().getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));// + NumberFormat.getCurrencyInstance().format(lPrice);
            message += "\n" + getResources().getString(R.string.thank_you);
        }
        return message;
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity     is the number of cups of coffee ordered
     * @param whippedCream whipped cream checked
     * @param chocolate    chocolate checked
     * @return price returned
     */
    private int calculatePrice(int quantity, boolean whippedCream, boolean chocolate) {
        int pricePerCup = 5;
        if (whippedCream)
            pricePerCup += 1;
        if (chocolate)
            pricePerCup += 2;
        return pricePerCup * quantity;
        //return price;
    }

    /**
     * Finds checkbox with given ID and returns its checked state
     *
     * @param ResId the resource ID of the checkbox to find
     * @return the state of the checkbox
     */
    private boolean checkboxState(int ResId) {
        CheckBox checkBox = findViewById(ResId); //(CheckBox)
        return checkBox.isChecked();
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        //int quantity = 2;
        if (quantity > 99) {
            Toast.makeText(this, getResources().getString(R.string.more_than_100_cups),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
        //displayPrice(quantity*5);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        //int quantity = 2;
        if (quantity < 2) {
            Toast.makeText(this, getResources().getString(R.string.less_than_1_cup),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
        //displayPrice(quantity*5);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view); //(TextView)
        quantityTextView.setText(String.format(Locale.ENGLISH, "%d", number));
    }

    /*
     * This method displays the given price on the screen.
     *
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    */

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message, String name) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view); //(TextView)

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:m.o.khan@warwick.ac.uk")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            orderSummaryTextView.setText(getResources().getString(R.string.order_sent));
        } else {
            orderSummaryTextView.setText(message);
        }

    }
}

/*package com.example.android.justjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
*/