import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Ship {
    public static final List<Ship> SHIPS = new ArrayList<>();
    private static int counter = 1;

    private final int id;
    private final String name;
    private final String homePort;
    private final String sourceLocation;
    private final String transportDestination;
    private final int maximumQuantityOfToxicAndExplosiveContainers;
    private final int maximumQuantityOfHeavyContainers;
    private final int maximumQuantityOfRefrigeratedContainers;
    private final int maximumQuantityOfContainers;
    private final double maximumDisplacement;
    private int quantityOfToxicAndExplosiveContainers = 0;
    private int quantityOfHeavyContainers = 0;
    private int quantityOfRefrigeratedContainers = 0;
    private int quantityOfContainers = 0;
    private double displacement = 0;
    final List<Container> shipContainers = new ArrayList<>();

    Ship(String name, String port, String sourceLocation, String transportDestination, int maximumQuantityOfToxicAndExplosiveContainers, int maximumQuantityOfHeavyContainers, int maximumQuantityOfRefrigeratedContainers, int maximumQuantityOfContainers, double maximumDisplacement) {
        this.id = counter++;
        this.name = name;
        this.homePort = port;
        this.sourceLocation = sourceLocation;
        this.transportDestination = transportDestination;
        this.maximumQuantityOfToxicAndExplosiveContainers = maximumQuantityOfToxicAndExplosiveContainers;
        this.maximumQuantityOfHeavyContainers = maximumQuantityOfHeavyContainers;
        this.maximumQuantityOfRefrigeratedContainers = maximumQuantityOfRefrigeratedContainers;
        this.maximumQuantityOfContainers = maximumQuantityOfContainers;
        this.maximumDisplacement = maximumDisplacement;

        SHIPS.add(this);
    }

    static void findContainer(Ship ship) {
        System.out.println("Container id: ");
        int containerId = new Scanner(System.in).nextInt();
        final Optional<Container> founded = Container.CONTAINERS.stream()
                .filter(container -> container.getId() == containerId)
                .findAny();
        if (founded.isPresent()) {
            ship.loadShip(founded.get());
        } else {
            System.out.println("There is no container with this id!");
        }
    }

    public void loadShip(Container container) {
        if (
                quantityOfContainers == maximumQuantityOfContainers ||
                        displacement + container.getWeightBrutto() > maximumDisplacement ||
                        container.isLoaded()
        ) {
            switch (container.getClass().getSimpleName()) {
                case "ExplosivesContainer", "FluidToxinsContainer", "SolidToxinsContainer" -> {
                    if (quantityOfToxicAndExplosiveContainers < maximumQuantityOfToxicAndExplosiveContainers) {
                        quantityOfToxicAndExplosiveContainers++;
                        reduceTheSpace(container);
                    } else {
                        System.out.println("There is already max toxic/explosive containers");
                    }
                }
                case "HeavyContainer" -> {
                    if (quantityOfHeavyContainers < maximumQuantityOfHeavyContainers) {
                        quantityOfHeavyContainers++;
                        reduceTheSpace(container);
                    } else {
                        System.out.println("There is already max heavy containers");
                    }
                }
                case "RefrigeratedContainers" -> {
                    if (quantityOfRefrigeratedContainers < maximumQuantityOfRefrigeratedContainers) {
                        quantityOfRefrigeratedContainers++;
                        reduceTheSpace(container);
                    } else {
                        System.out.println("There is already max refrigerated containers");
                    }
                }
                default -> reduceTheSpace(container);
            }
        } else {
            System.out.println("There is no space for this container");
        }
    }

    private void reduceTheSpace(Container container) {
        shipContainers.add(container);
        quantityOfContainers++;
        displacement += container.getWeightBrutto();
        container.setLoaded(true);
        if (container.getWarehouse() != null) {
            container.getWarehouse().removeContainer(container);
        }
        System.out.println("Container loaded");
    }

    void unloadShip() {
        System.out.println("Give containerId: ");
        Scanner scanner = new Scanner(System.in);
        int containerId = scanner.nextInt();
        Optional<Container> fouded = shipContainers.stream()
                .filter(container1 -> container1.getId() == containerId)
                .findAny();
        if (fouded.isPresent()) {
            if (fouded.get().getShipper().getIrresponsibleSenderWithDangerousGoods().size() > 1) {
                System.out.println("I cant unloaded this container, because there is more than one shipper with dangerous goods");
            } else {
                containerPutter(fouded.get());
            }
        } else {
            System.out.println("There is no container with this id!");
        }
    }

    private void containerPutter(Container container1) {
        System.out.println("Where do you want to unload it?");
        System.out.println("1 - Warehouse");
        System.out.println("2 - Train");
        int choice = new Scanner(System.in).nextInt();
        switch (choice) {
            case 1 -> unloadToWarehouse(container1);
            case 2 -> {
                Train.TRAIN.loadTrain();
                if (!Train.isLocked()) {
                    increaseSpace(container1);
                    Container.CONTAINERS.remove(container1);
                }
            }
            default -> System.out.println("Wrong choice");
        }
    }

    private void unloadToWarehouse(Container container) {
        System.out.println("Which warehouse: ");
        int warehouseId = new Scanner(System.in).nextInt();
        final Optional<Warehouse> founded = Warehouse.WAREHOUSES.stream()
                .filter(w -> w.getId() == warehouseId)
                .findAny();
        if (founded.isPresent()) {
            founded.get().putContainer(container);
            if (founded.get().canLoad()) {
                increaseSpace(container);
                container.setWarehouse(founded.get());
                container.setLoaded(false);
            }
        } else {
            System.out.println("There is no warehouse with this id!");
        }
    }

    private void increaseSpace(Container container1) {
        switch (container1.getClass().getSimpleName()) {
            case "ExplosivesContainer", "FluidToxinsContainer", "SolidToxinsContainer" -> quantityOfToxicAndExplosiveContainers--;
            case "HeavyContainer" -> quantityOfHeavyContainers--;
            case "RefrigeratedContainers" -> quantityOfRefrigeratedContainers--;
        }
        shipContainers.remove(container1);
        quantityOfContainers--;
        displacement -= container1.getWeightBrutto();
    }

    public void unloadShipToWarehouse(int warehouseId) {
        System.out.println("Which container do you wanna unload: ");
        int containerId = new Scanner(System.in).nextInt();
        Optional<Container> founded = shipContainers.stream()
                .filter(container -> container.getId() == containerId)
                .findAny();
        if (founded.isPresent()) {
            if (founded.get().getShipper().getIrresponsibleSenderWithDangerousGoods().size() > 1) {
                System.out.println("I cant unload this container");
            } else {
                Optional<Warehouse> founded2 = Warehouse.WAREHOUSES.stream()
                        .filter(warehouse -> warehouse.getId() == warehouseId)
                        .findAny();
                if (founded2.isPresent()) {
                    founded2.get().putContainer(founded.get());
                    if (founded2.get().canLoad()) {
                        increaseSpace(founded.get());
                    }
                } else {
                    System.out.println("There is no warehouse with this id!");
                }
            }
        } else {
            System.out.println("There is no container with this id!");
        }
    }

    void setSail() {
        shipContainers.forEach(Container.CONTAINERS::remove);
        SHIPS.remove(this);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        List<Container> sorted = shipContainers.stream()
                .sorted(Comparator.comparingDouble(Container::getWeightBrutto))
                .collect(Collectors.toList());
        return "id: " + id +
                "\nname: " + name +
                "\nhomePort: " + homePort +
                "\nsourceLocation: " + sourceLocation +
                "\ntransportDestination: " + transportDestination +
                "\nmaximumQuantityOfToxicAndExplosiveContainers: " + maximumQuantityOfToxicAndExplosiveContainers +
                "\nmaximumQuantityOfHeavyContainers: " + maximumQuantityOfHeavyContainers +
                "\nmaximumQuantityOfRefrigeratedContainers: " + maximumQuantityOfRefrigeratedContainers +
                "\nmaximumQuantityOfContainers: " + maximumQuantityOfContainers +
                "\nmaximumDisplacement: " + maximumDisplacement +
                "\nshipContainers: {\n" + Util.printList(sorted) + "}";
    }
}