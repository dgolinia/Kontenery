public class HeavyContainer extends BaseContainer {

    protected boolean over100tons;

    public HeavyContainer(Shipper shipper, double massNettoInTons, double massBruttoInTons, boolean over100tons) {
        super(shipper, massNettoInTons, massBruttoInTons);
        this.over100tons = over100tons;
    }

    boolean isOver100tons() {
        return over100tons;
    }

    @Override
    public String toString() {
        return "HeavyContainer - " +
                "shipper: [" + getShipper() +
                "], massNettoInTons: " + getWeightNetto() +
                ", massBruttoInTons: " + getWeightBrutto() +
                ", over100tons: " + isOver100tons();
    }
}
