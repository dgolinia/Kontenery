public class FluidToxinsContainer extends ToxinsContainer {

    private final String fluid;
    private final String volume;

    public FluidToxinsContainer(Shipper shipper, double massNettoInTons, double massBruttoInTons, String fluidName, String volume, Boolean over100Tons) {
        super(shipper, massNettoInTons, massBruttoInTons, over100Tons);
        this.fluid = fluidName;
        this.volume = volume;
    }

    String getFluid() {
        return fluid;
    }

    String getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return "FluidToxinsContainer - " +
                "shipper: [" + getShipper() +
                "], massNettoInTons: " + getWeightNetto() +
                ", massBruttoInTons: " + getWeightBrutto() +
                "], fluid: " + getFluid() +
                "], volume: " + getVolume();
    }
}
