package model;

public class CuatroEnLinea {
    private static final int FILAS = 6;
    private static final int COLUMNAS = 7;
    private char[][] tablero = new char[FILAS][COLUMNAS];

    public CuatroEnLinea() {
        inicializarTablero();
    }

    private void inicializarTablero() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                tablero[i][j] = '\u0000';
            }
        } 
    }

    public String getTablero() {
        StringBuilder sb = new StringBuilder();
        sb.append(" 1 2 3 4 5 6 7\n");
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                sb.append("|").append(tablero[i][j]);
            }
            sb.append("|\n");
        }
        sb.append("---------------");
        return sb.toString();
    }

    public void colocarFicha(int columna, char jugador) {
        for (int i = FILAS - 1; i >= 0; i--) {
            if (tablero[i][columna] == '\u0000') {
                tablero[i][columna] = jugador;
                break;
            }
        }
    }

    public boolean haGanado(char jugador) {
        // Verificar horizontalmente
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna <= COLUMNAS - 4; columna++) {
                if (tablero[fila][columna] == jugador && tablero[fila][columna + 1] == jugador &&
                    tablero[fila][columna + 2] == jugador && tablero[fila][columna + 3] == jugador) {
                    return true;
                }
            }
        }

        // Verificar verticalmente
        for (int columna = 0; columna < COLUMNAS; columna++) {
            for (int fila = 0; fila <= FILAS - 4; fila++) {
                if (tablero[fila][columna] == jugador && tablero[fila + 1][columna] == jugador &&
                    tablero[fila + 2][columna] == jugador && tablero[fila + 3][columna] == jugador) {
                    return true;
                }
            }
        }

        // Verificar en diagonal (\)
        for (int fila = 0; fila <= FILAS - 4; fila++) {
            for (int columna = 0; columna <= COLUMNAS - 4; columna++) {
                if (tablero[fila][columna] == jugador && tablero[fila + 1][columna + 1] == jugador &&
                    tablero[fila + 2][columna + 2] == jugador && tablero[fila + 3][columna + 3] == jugador) {
                    return true;
                }
            }
        }

        // Verificar en diagonal (/)
        for (int fila = 0; fila <= FILAS - 4; fila++) {
            for (int columna = COLUMNAS - 1; columna >= 3; columna--) {
                if (tablero[fila][columna] == jugador && tablero[fila + 1][columna - 1] == jugador &&
                    tablero[fila + 2][columna - 2] == jugador && tablero[fila + 3][columna - 3] == jugador) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean tableroLleno() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (tablero[i][j] == '\u0000') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean haTerminado() {
        return haGanado('X') || haGanado('O') || tableroLleno();
    }
}