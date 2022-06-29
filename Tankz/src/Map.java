import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;


public class Map {

    public Map(int size, int noEnemyBases, int noEnemyTanks) {
        // In the future iterations, there could be a possibility to choose from many predefined mapgen functions or just parse premade map.

        Cell[][] map = new Cell[size][size];
        filArray(size, map);
		setNeibers(size, map);

        setEntities(10,size,map);

        redrawAll(map, size );

        //while(true)
        //{
        //	if (KeyListener.isWPressed() == true)
        //}+

        System.out.print(Arrays.toString(map));
    }




    /**
     * @param size
     * @param cells
     */
        private void setNeibers ( int size, Cell[][] cells){
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
        private void filArray ( int size, Cell[][] cells){
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    cells[i][j] = new Cell();
                }
            }
        }

    private void redrawAll (Cell[][] map, int size){

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j].redraw();
            }
        }
    }

    public void setEntities(int entitiesNumber, int size, Cell[][] cells) {
        int entityType = 0; // Default type gives empty field.
        int starterX, starterY;
        int baseX, baseY;

        // Generowanie losowej pozycji startowej gracza.
        starterX = ThreadLocalRandom.current().nextInt(size);
        starterY = ThreadLocalRandom.current().nextInt(size);
        cells[starterX][starterY].entityType = "Player"; //
        //Player.

        do { // Generowanie losowej pozycji bazy wroga w nie za małej odleglości
            baseX = ThreadLocalRandom.current().nextInt(size);
            baseY = ThreadLocalRandom.current().nextInt(size);
        } while (cells[baseX][baseY].isEntity() && Math.abs(starterX-baseX) < 20 && Math.abs(starterY-baseY) < 20);
        cells[baseX][baseY].entityType = "EnemyBase"; //

        for (int i = 0; i < entitiesNumber; i++) {
            // 1#  4[x]
            int x, y, p;
            do { // Generowanie losowej pozycji bazy wroga w nie za małej odleglości
                x = ThreadLocalRandom.current().nextInt(size);
                y = ThreadLocalRandom.current().nextInt(size);
            } while (cells[x][y].isEntity());
            p = ThreadLocalRandom.current().nextInt(16);
            if (p > 14)	cells[x][y].entityType = "Enemy";
            else cells[x][y].entityType = "Wall";
        }
    }
}
