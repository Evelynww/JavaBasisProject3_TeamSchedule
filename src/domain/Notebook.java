package domain;

public class Notebook implements Equipment{
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private String model; //机器型号
    private double price;//机器价格

    public Notebook(){

    }
    public Notebook(String model, double price) {
        this.model = model;
        this.price = price;
    }

    @Override
    public String getDescription() {
        return model+price;
    }
}
