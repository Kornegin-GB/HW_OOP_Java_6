package HW_OOP_Java_6.Units.whiteSide;

import HW_OOP_Java_6.System.Vector2D;
import HW_OOP_Java_6.Units.Unit;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Robber extends Unit {
    public Robber(List<Unit> ownGang, List<Unit> enemyGang, int x, int y) {
        super(8, 3, new int[]{2, 4}, 10, 6, "Разбойник");
        super.ownGang = ownGang;
        super.position = new Vector2D(x, y);
        super.enemyGang = enemyGang;
        this.quantity = new Random().nextInt(15, 20);
    }

    @Override
    public void step() {
        double dist = Double.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < enemyGang.size(); i++) {
            double tmp = enemyGang.get(i).getPosition().getDist(this.position);
            if (dist > tmp && !enemyGang.get(i).getStatus().equals("Мертв")) {
                dist = tmp;
                index = i;
            }
        }
        if (index >= 0) {
            if (dist < 2) {
                enemyGang.get(index).getHit(getDamage(enemyGang.get(index)));
            } else {
                Vector2D enemyPosition = enemyGang.get(index).getPosition();
                Vector2D newPosition = new Vector2D(0, 0);
                if (enemyPosition.getY() == this.position.getY()) {
                    newPosition.setY(this.position.getY());
                    if (this.position.getX() - enemyPosition.getX() < 0) {
                        newPosition.setX(this.position.getX() + 1);
                    } else {
                        newPosition.setX(this.position.getX() - 1);
                    }
                } else {
                    newPosition.setX(this.position.getX());
                    if (enemyPosition.getY() - this.position.getY() > 0) {
                        newPosition.setY(this.position.getY() + 1);
                    } else {
                        newPosition.setY(this.position.getY() - 1);
                    }
                }
                AtomicBoolean empty = new AtomicBoolean(true);
                ownGang.forEach(unit -> {
                    if (unit.getPosition().isEquals(newPosition)) {
                        empty.set(false);
                    }
                });
                if (empty.get()) {
                    this.position = newPosition;
                }
            }
        }
    }

    @Override
    public String getInfo() {
        return name + "-> " + super.getInfo();
    }
}
