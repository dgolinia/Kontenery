import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Threads {

    public static LocalDate date = LocalDate.now();

    private static final Thread DAYS = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                date = date.plusDays(1);
            }
        }
    });

    private static final Thread WAREHOUSE = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<Container> toRemove = new ArrayList<>();
            Container.CONTAINERS.stream()
                    .filter(container -> {
                        String name = container.getClass().getSimpleName();
                        if (name.equals("ExplosivesContainer") || name.equals("FluidToxinsContainer") || name.equals("SolidToxinsContainer")) {
                            return true;
                        } else {
                            return false;
                        }
                    })
                    .filter(container -> container.getDate() != null)
                    .forEach(container -> {
                        long between = ChronoUnit.DAYS.between(container.getDate(), date);
                        switch (container.getClass().getSimpleName()) {
                            case "ExplosivesContainer" -> {
                                if (between > 5) {
                                    executeWarning(toRemove, container);
                                }
                            }
                            case "FluidToxinsContainer" -> {
                                if (between > 10) {
                                    executeWarning(toRemove, container);
                                }
                            }
                            case "SolidToxinsContainer" -> {
                                if (between > 14) {
                                    executeWarning(toRemove, container);
                                }
                            }
                        }
                    });
            Container.CONTAINERS.removeAll(toRemove);
        }
    });

    public void start() {
        DAYS.start();
        WAREHOUSE.start();
    }

    private static void executeWarning(List<Container> toRemove, Container container) {
        String message = "\nContainer disposed, WARNING added for sender! " + "Arriving day: " + container.getDate() + " ,Disposal day: " + date + " ,id: " + container.getId();
        System.out.println(message);
        container.getShipper().addWarning(message);
        Warehouse.WAREHOUSES
                .forEach(warehouse -> warehouse.warehouseContainers.remove(container));
        toRemove.add(container);
    }
    public static LocalDate getDate() {
        return date;
    }
}
