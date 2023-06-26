public abstract class ToxinsContainer extends HeavyContainer {

    public ToxinsContainer(Shipper shipper, double massNettoInTons, double massBruttoInTons, boolean over100Tons) {
        super(shipper, massNettoInTons, massBruttoInTons, over100Tons);
    }

    @Override
    public String toString() {
        return "ToxinsContainer - " +
                "shipper: [" + getShipper() +
                "], massNettoInTons: " + getWeightNetto() +
                ", massBruttoInTons: " + getWeightBrutto() +
                ", over100tons: " + isOver100tons();
    }
}
