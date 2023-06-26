public class RefrigeratedContainer extends HeavyContainer {

    private double temperature;

    public RefrigeratedContainer(Shipper shipper, double massNettoInTons, double massBruttoInTons, boolean over100tons, double temperature) {
        super(shipper, massNettoInTons, massBruttoInTons, over100tons);
        this.temperature = temperature;
    }

    double getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return "RefrigeratedContainer - " +
                "shipper: [" + getShipper() +
                "], massNettoInTons: " + getWeightNetto() +
                ", massBruttoInTons: " + getWeightBrutto() +
                ", over100tons: " + isOver100tons() +
                ", temperature: " + getTemperature();
    }
}
