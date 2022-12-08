package HW_OOP_Java_6.Units.whiteSide;

import HW_OOP_Java_6.System.Vector2D;
import HW_OOP_Java_6.Units.Unit;

import java.util.*;

public class Wizard extends Unit {
    static int atmInt = -1;
    private boolean magic;

    public Wizard(List<Unit> ownGang, List<Unit> enemyGang, int x, int y) {
        super(17, 12, new int[]{-5, -5}, 30, 9, "Волшебник");
        magic = true;
        super.ownGang = ownGang;
        super.position = new Vector2D(x, y);
        super.enemyGang = enemyGang;
        this.quantity = new Random().nextInt(2, 3);
    }

    @Override
    public String getInfo() {
        return name + "-> " + super.getInfo();
    }

    @Override
    public void step() {
        double minHealth = Integer.MAX_VALUE;
        Map<Integer, Double> healths = new HashMap<>();
        for (int i = 0; i < ownGang.size(); i++) {
            healths.put(i, ownGang.get(i).getHealth() / ownGang.get(i).getMAX_HEALTH());
        }
        List<Double> a = new ArrayList<>(healths.values().stream().toList());
        Collections.sort(a);
        healths.forEach((index, value) -> {
            if (value.equals(a.get(0))) {
                atmInt = index;
            }
        });
        if (a.get(0) > 0.5) {
            double dist = 1;
            int index = -1;
            for (int i = 0; i < enemyGang.size(); i++) {
                double tmp = enemyGang.get(i).getHealth() / enemyGang.get(i).getMAX_HEALTH();
                if (dist > tmp && enemyGang.get(i).getStatus().equals("Мертв")) {
                    dist = tmp;
                    index = i;
                }
            }
            if (index < 0) {
                index = 0;
            }
            enemyGang.get(index).getHit(damage[0] * -1);
            setStatus("Выстрелил в " + index);
            return;
        }
        if (a.get(0).equals(0.0)) {
            healths.forEach((index, value) -> {
                if (value.equals(0.0)) {
                    if (ownGang.get(index).getName().equals("Снайпер") || ownGang.get(index).getName().equals("Волшебник")) {
                        atmInt = index;
                    }
                }
            });
            if (atmInt >= 0) {
                ownGang.get(atmInt).setHealth(1);
                ownGang.get(atmInt).setStatus("Стоит");
                setStatus("Возродил " + atmInt);
            }
            return;
        }
        if (a.get(0) <= 0.5) {
            ownGang.get(atmInt).setHealth(ownGang.get(atmInt).getHealth() - this.damage[0]);
            if (ownGang.get(atmInt).getHealth() > ownGang.get(atmInt).getMAX_HEALTH()) {
                ownGang.get(atmInt).setHealth(ownGang.get(atmInt).getMAX_HEALTH());
            }
            setStatus("Вылечил " + atmInt);
        }
    }
}
