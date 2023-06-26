import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Warehouse {
    public static final List<Warehouse> WAREHOUSES = new ArrayList<>();
    private static int counter = 1;
    private static final Comparator<Container> CONTAINER_COMPARATOR = (o1, o2) -> {
        int result = o1.getDate().compareTo(o2.getDate());
        if (result == 0) {
            result = o1.getShipper().getName().compareTo(o2.getShipper().getName());
        }
        return result;
    };

    public final List<Container> warehouseContainers = new ArrayList<>();
    private final int id;
    private final int maximumQuantityOfContainers;
    private boolean isLoad;
    private int currentQuantityOfContainers = 0;

    public Warehouse(int maximumQuantityOfContainers) {
        this.id = counter++;
        this.maximumQuantityOfContainers = maximumQuantityOfContainers;
        WAREHOUSES.add(this);
    }

    public static void startPuttingContainer() {
        System.out.println("On which warehouse do you want to put the container: ");
        int warehouseId = new Scanner(System.in).nextInt();
        final Optional<Warehouse> founded = WAREHOUSES.stream()
                .filter(warehouse -> warehouse.getId() == warehouseId)
                .findAny();
        if (founded.isPresent()) {
            founded.get().putContainer(founded.get().id);
        } else {
            System.out.println("There is not such a warehouse");
        }
    }

    public void putContainer(Container container) {
        if (currentQuantityOfContainers < maximumQuantityOfContainers && container.getShipper().getIrresponsibleSenderWithDangerousGoods().size() < 2) {
            warehouseContainers.add(container);
            currentQuantityOfContainers++;
            container.setDate(Threads.getDate());
            System.out.println("Container put into the warehouse");
            isLoad = true;
        } else {
            System.out.println("Warehouse is full");
            isLoad = false;
        }
    }

    private void putContainer(int warehouseId) {
        System.out.println("Which ship do you wanna unload: ");
        int shipId = new Scanner(System.in).nextInt();
        Optional<Ship> founded = Ship.SHIPS.stream()
                .filter(ship -> ship.getId() == shipId)
                .findAny();
        if (founded.isPresent()) {
            founded.get().unloadShipToWarehouse(warehouseId);
        } else {
            System.out.println("There is not such a ship");
        }
    }

    void takeOutContainer() {
        System.out.println("Give containerId: ");
        Scanner scanner = new Scanner(System.in);
        int containerId = scanner.nextInt();
        Optional<Container> founded = warehouseContainers.stream()
                .filter(container -> container.getId() == containerId)
                .findAny();
        if (founded.isPresent()) {
            System.out.println("Where load the container: ");
            System.out.println("1 - Ship");
            System.out.println("2 - Train");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> unloadToShip(founded.get());
                case 2 -> {
                    Train.TRAIN.loadTrain();
                    if (!Train.isLocked()) {
                        removeContainer(founded.get());
                        Container.CONTAINERS.remove(founded.get());
                    }
                }
                default -> System.out.println("This is not an option");
            }
        } else {
            System.out.println("There is not such a container");
        }
    }

    private void unloadToShip(Container container) {
        System.out.println("Ship id ");
        int shipId = new Scanner(System.in).nextInt();
        Optional<Ship> founded = Ship.SHIPS.stream()
                .filter(ship -> ship.getId() == shipId)
                .findAny();
        if (founded.isPresent()) {
            founded.get().loadShip(container);
        } else {
            System.out.println("There is not such a ship");
        }
    }

    public void removeContainer(Container containerToRemove) {
        warehouseContainers.remove(containerToRemove);
        currentQuantityOfContainers--;
        containerToRemove.setWarehouse(null);
    }

    public int getId() {
        return id;
    }

    public boolean canLoad() {
        return isLoad;
    }

    public String printSaver() {
        warehouseContainers.sort(CONTAINER_COMPARATOR);
        StringBuilder toReturn = new StringBuilder();
        for (Container c : warehouseContainers) {
            toReturn.append(c.toString()).append(", Date: ").append(c.getDate()).append("\n");
        }
        return "id: " + id +
                "\nmaximumQuantityOfContainers: " + maximumQuantityOfContainers +
                "\ncontainerToPutLeft: " + (maximumQuantityOfContainers - currentQuantityOfContainers) +
                "\nwarehouseContainers: {\n" + toReturn + "}";
    }

    @Override
    public String toString() {
        warehouseContainers.sort(CONTAINER_COMPARATOR);
        return "id: " + id +
                "\nmaximumQuantityOfContainers: " + maximumQuantityOfContainers +
                "\ncontainerToPutLeft: " + (maximumQuantityOfContainers - currentQuantityOfContainers) +
                "\nwarehouseContainers: {\n" + Util.printList(warehouseContainers) + "}";
    }
}
