package sample;

import java.time.LocalDate;

public class Payment {
    private int payment_id = -1;
    private boolean payment_status;
    private double payment_amount;
    private LocalDate payment_date;
    private String payment_card_holder_name;
    private int payment_card_number;
    private int[] payment_card_exp_date;
    private int payment_card_CVC;

    public Payment(int payment_id, boolean payment_status, double payment_amount, LocalDate payment_date, String payment_card_holder_name, int payment_card_number, int[] payment_card_exp_date, int payment_card_CVC) {
        this.payment_id = payment_id;
        this.payment_status = payment_status;
        this.payment_amount = payment_amount;
        this.payment_date = payment_date;
        this.payment_card_holder_name = payment_card_holder_name;
        this.payment_card_number = payment_card_number;
        this.payment_card_exp_date = payment_card_exp_date;
        this.payment_card_CVC = payment_card_CVC;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public boolean isPayment_status() {
        return payment_status;
    }

    public void setPayment_status(boolean payment_status) {
        this.payment_status = payment_status;
    }

    public double getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(double payment_amount) {
        this.payment_amount = payment_amount;
    }

    public LocalDate getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(LocalDate payment_date) {
        this.payment_date = payment_date;
    }

    public String getPayment_card_holder_name() {
        return payment_card_holder_name;
    }

    public void setPayment_card_holder_name(String payment_card_holder_name) {
        this.payment_card_holder_name = payment_card_holder_name;
    }

    public int getPayment_card_number() {
        return payment_card_number;
    }

    public void setPayment_card_number(int payment_card_number) {
        this.payment_card_number = payment_card_number;
    }

    public int[] getPayment_card_exp_date() {
        return payment_card_exp_date;
    }

    public void setPayment_card_exp_date(int[] payment_card_exp_date) {
        this.payment_card_exp_date = payment_card_exp_date;
    }

    public int getPayment_card_CVC() {
        return payment_card_CVC;
    }

    public void setPayment_card_CVC(int payment_card_CVC) {
        this.payment_card_CVC = payment_card_CVC;
    }
}
