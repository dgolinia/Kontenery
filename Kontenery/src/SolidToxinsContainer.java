public class SolidToxinsContainer extends ToxinsContainer {

    public SolidToxinsContainer(Shipper shipper, double massNettoInTons, double massBruttoInTons, boolean over100Tons) {
        super(shipper, massNettoInTons, massBruttoInTons, over100Tons);
    }

    @Override
    public String toString() {
        return "SolidToxinsContainer - " +
                "shipper: [" + getShipper() +
                "], massNettoInTons: " + getWeightNetto() +
                ", massBruttoInTons: " + getWeightBrutto() +
                ", over100tons: " + isOver100tons();
    }
}