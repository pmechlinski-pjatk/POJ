import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;

public class LocalUtils {

    void setNeibers(int size, Cell[][] cells){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                for (int p = 0; p <= 2; p++) {
                    for (int q = 0; q <= 2; q++) {
                        int x = i + p - 1, y = j + q - 1;
                        if (x >= 0 && x < size && y >= 0 && y < size)
                            cells[i][j].setNeiber(cells[x][y], p, q);

                    }
                }
            }
        }
    }

    /**
     * @param size
     * @param cells
     */
    void filArray(int size, Cell[][] cells){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //System.out.print("New cell with x = "+i+" & y = "+j+"\n");  //DEBUG
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public String getSpriteByName(Sprites [] s, String name)
    {
        for (Sprites sprite : s)
        {
            if (sprite.getName() == name) return sprite.getImage();
        }
        return "NULL!";
    }

    public static void addToSprites(int n, Sprites [] s, String name, String image)
    {
        Sprites sprite = new Sprites(name,image);
        s[n] = sprite;
        n++;
    }


    public void redrawAll(Cell cells[][], int size)
    {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                cells[i][j].redraw();
            }
        }
    }

    public void mapGen(int size, Cell[][] cells, GameObject[] terrain, GameObject [] enemyBases, EnemyTank[] enemies, Sprites [] s, Player [] player) {
        int starterX, starterY;
        int baseX, baseY;

        // Generowanie losowej pozycji startowej gracza.
        do {
            starterX = ThreadLocalRandom.current().nextInt(size);
            starterY = ThreadLocalRandom.current().nextInt(size);
        } while ((starterX > 3 && starterX < size - 3) && (starterY > 3 && starterY < size - 3));
        // This requirement should ensure that the starting position of the player is close to the map's border.*
        // *It could crash on maps less than 6x6, but why'd someone use that small map?
        player[0] = new Player("Player", getSpriteByName(s, "Player"), 3, true, cells[starterX][starterY]);
        cells[starterX][starterY].redraw();
        //System.out.println("Player's coords: "+Arrays.toString(player.linkedCell.getCoordinates()));
        //System.out.println("LinkedObject:" + cells[starterX][starterY].getLinkedObject().getImage());
        System.out.println("\t(+)Player created at: "+starterX+","+starterY);
        //System.out.println("Player's neibers are:\n"+(Arrays.deepToString(player.getNeibers())));


        for (int i = 0; i < enemyBases.length; i++) {
            do { // Generowanie losowej pozycji bazy wroga w nie za małej odleglości
                baseX = ThreadLocalRandom.current().nextInt(size);
                baseY = ThreadLocalRandom.current().nextInt(size);
            } while (cells[baseX][baseY].getLinkedObject() != null || Math.abs(starterX - baseX) < 10 || Math.abs(starterY - baseY) < 10);
            enemyBases[i] = new GameObject("EnemyBase", getSpriteByName(s, "EnemyBase"), 3 , true, cells[baseX][baseY]);
            cells[baseX][baseY].redraw();
            System.out.println("\t(+) "+(i+1)+". Enemy base created at: "+baseX+","+baseY);
            baseX = baseY = 0;
        }

        for (int i = 0; i <= Math.round(size*4); i++)
        {
            int x,y;
            do
            {
                x = ThreadLocalRandom.current().nextInt(size);
                y = ThreadLocalRandom.current().nextInt(size);
            } while (cells[x][y].getLinkedObject() != null);
            terrain[i] = new GameObject("Terrain", getSpriteByName(s, "Wall"), 2, true, cells[x][y]);
            cells[x][y].redraw();
            x = y = 0;
        }
        System.out.println("\t(+) A few walls are build on your way.");
        for (int i = 0; i <1; i++) // ONE TANK DEBUG
//        for (int i = 0; i <= Math.round(size/3); i++)
        {
            int x,y;
            do
            {
                x = ThreadLocalRandom.current().nextInt(size);
                y = ThreadLocalRandom.current().nextInt(size);
            } while (cells[x][y].getLinkedObject() != null || Math.abs(starterX - x) < 5 || Math.abs(starterY - y) < 5);
            enemies[i] = new EnemyTank("EnemyTank", getSpriteByName(s, "EnemyTank"), 1, true, cells[x][y]);
            enemies[i].setEastStandby("[x]--");
            enemies[i].setWestStandby("--[x]");
            enemies[i].setNorthStandby("<html>_|_<br>[x]</html>");
            enemies[i].setSouthStandby("<html>[x]<br>'|'</html>");
            //System.out.println("Tank neibers are:\n"+(Arrays.deepToString(enemies[i].getNeibers())));
            cells[x][y].redraw();
            int finalI = i;
            new Thread(() -> {
                try {
                    enemies[finalI].panther(cells);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            x = y = 0;
        }
        System.out.println("\t(+) Some enemy tanks are approaching.");


    }
}
