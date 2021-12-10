package main;

import java.sql.Connection;

public class Month {
    double personnelSalary;
    double importTotal;
    double tax;
    double other;
    double turnover;
    double profit;

    public double getPersonnelSalary() {
        return personnelSalary;
    }

    public void setPersonnelSalary(double personnelSalary) {
        this.personnelSalary = personnelSalary;
    }

    public double getImportTotal() {
        return importTotal;
    }

    public void setImportTotal(double importTotal) {
        this.importTotal = importTotal;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getOther() {
        return other;
    }

    public void setOther(double other) {
        this.other = other;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }


    public Month(double personnelSalary, double importTotal, double tax, double other, double turnover, double profit) {
        this.personnelSalary = personnelSalary;
        this.importTotal = importTotal;
        this.tax = tax;
        this.other = other;
        this.turnover = turnover;
        this.profit = profit;
    }

    public Month(double turnover, double profit) {
        this.turnover = turnover;
        this.profit = profit;
    }
}
