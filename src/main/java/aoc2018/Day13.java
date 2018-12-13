package aoc2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day13 {
    private static final String INPUT = "src/main/resources/aoc2018/day13.txt";

    private static String part1() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(INPUT));
        int maxRow = lines.size();
        int maxCol = lines.stream().mapToInt(a -> a.length()).max().getAsInt();
        char[][] grid = new char[maxRow][maxCol];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = lines.get(i).toCharArray();
        }
        List<Cart> carts = new ArrayList<>();
        int id = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == '^') {
                    carts.add(new Cart(id++, row, col, Direction.UP));
                    grid[row][col] = '|';
                } else if (grid[row][col] == '>') {
                    carts.add(new Cart(id++, row, col, Direction.RIGHT));
                    grid[row][col] = '-';
                } else if (grid[row][col] == 'v') {
                    carts.add(new Cart(id++, row, col, Direction.DOWN));
                    grid[row][col] = '|';
                } else if (grid[row][col] == '<') {
                    carts.add(new Cart(id++, row, col, Direction.LEFT));
                    grid[row][col] = '-';
                }
            }
        }
        String answer = "";
        outer:
        while (true) {
            Collections.sort(carts, (a, b) -> {
                int cmp = a.row - b.row;
                if (cmp != 0) {
                    return cmp;
                }
                return a.col - b.col;
            });
            for (Cart cart : carts) {
                int row = cart.row;
                int col = cart.col;
                if (cart.direction == Direction.UP) {
                    row--;
                } else if (cart.direction == Direction.RIGHT) {
                    col++;
                } else if (cart.direction == Direction.DOWN) {
                    row++;
                } else if (cart.direction == Direction.LEFT) {
                    col--;
                }
                if (grid[row][col] == '/') {
                    if (cart.direction == Direction.UP) {
                        cart.direction = Direction.RIGHT;
                    } else if (cart.direction == Direction.RIGHT) {
                        cart.direction = Direction.UP;
                    } else if (cart.direction == Direction.DOWN) {
                        cart.direction = Direction.LEFT;
                    } else if (cart.direction == Direction.LEFT) {
                        cart.direction = Direction.DOWN;
                    }
                } else if (grid[row][col] == '\\') {
                    if (cart.direction == Direction.UP) {
                        cart.direction = Direction.LEFT;
                    } else if (cart.direction == Direction.RIGHT) {
                        cart.direction = Direction.DOWN;
                    } else if (cart.direction == Direction.DOWN) {
                        cart.direction = Direction.RIGHT;
                    } else if (cart.direction == Direction.LEFT) {
                        cart.direction = Direction.UP;
                    }
                } else if (grid[row][col] == '+') {
                    cart.direction = cart.currentIntersection(cart.direction);
                    cart.nextIntersection();
                }
                cart.row = row;
                cart.col = col;
                // Check for collision
                for (Cart c : carts) {
                    if (cart.id == c.id) {
                        continue;
                    }
                    if (cart.row == c.row && cart.col == c.col) {
                        answer = cart.col + "," + cart.row;
                        break outer;
                    }
                }
            }
        }
        return answer;
    }

    private static String part2() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(INPUT));
        int maxRow = lines.size();
        int maxCol = lines.stream().mapToInt(a -> a.length()).max().getAsInt();
        char[][] grid = new char[maxRow][maxCol];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = lines.get(i).toCharArray();
        }
        List<Cart> carts = new ArrayList<>();
        int id = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == '^') {
                    carts.add(new Cart(id++, row, col, Direction.UP));
                    grid[row][col] = '|';
                } else if (grid[row][col] == '>') {
                    carts.add(new Cart(id++, row, col, Direction.RIGHT));
                    grid[row][col] = '-';
                } else if (grid[row][col] == 'v') {
                    carts.add(new Cart(id++, row, col, Direction.DOWN));
                    grid[row][col] = '|';
                } else if (grid[row][col] == '<') {
                    carts.add(new Cart(id++, row, col, Direction.LEFT));
                    grid[row][col] = '-';
                }
            }
        }
        outer:
        while (true) {
            Collections.sort(carts, (a, b) -> {
                int cmp = a.row - b.row;
                if (cmp != 0) {
                    return cmp;
                }
                return a.col - b.col;
            });
            for (Cart cart : carts) {
                if (cart.collided) {
                    continue;
                }
                int row = cart.row;
                int col = cart.col;
                if (cart.direction == Direction.UP) {
                    row--;
                } else if (cart.direction == Direction.RIGHT) {
                    col++;
                } else if (cart.direction == Direction.DOWN) {
                    row++;
                } else if (cart.direction == Direction.LEFT) {
                    col--;
                }
                if (grid[row][col] == '/') {
                    if (cart.direction == Direction.UP) {
                        cart.direction = Direction.RIGHT;
                    } else if (cart.direction == Direction.RIGHT) {
                        cart.direction = Direction.UP;
                    } else if (cart.direction == Direction.DOWN) {
                        cart.direction = Direction.LEFT;
                    } else if (cart.direction == Direction.LEFT) {
                        cart.direction = Direction.DOWN;
                    }
                } else if (grid[row][col] == '\\') {
                    if (cart.direction == Direction.UP) {
                        cart.direction = Direction.LEFT;
                    } else if (cart.direction == Direction.RIGHT) {
                        cart.direction = Direction.DOWN;
                    } else if (cart.direction == Direction.DOWN) {
                        cart.direction = Direction.RIGHT;
                    } else if (cart.direction == Direction.LEFT) {
                        cart.direction = Direction.UP;
                    }
                } else if (grid[row][col] == '+') {
                    cart.direction = cart.currentIntersection(cart.direction);
                    cart.nextIntersection();
                }
                cart.row = row;
                cart.col = col;
                // Check for collision
                for (Cart c : carts) {
                    if (cart.id == c.id || c.collided) {
                        continue;
                    }
                    if (cart.row == c.row && cart.col == c.col) {
                        cart.collided = true;
                        c.collided = true;
                        break;
                    }
                }
            }
            if (carts.stream().filter(c -> !c.collided).count() == 1) {
                break outer;
            }
        }
        Cart cart = carts.stream().filter(c -> !c.collided).findFirst().get();
        return cart.col + "," + cart.row;
    }

    private enum Direction {
        LEFT, RIGHT, UP, DOWN, STRAIGHT
    }

    private static class Cart {
        private final int id;
        private int row;
        private int col;
        private Direction direction;
        private int intIdx; // intersection index
        private final List<Direction> intersection = Arrays.asList(
            Direction.LEFT, Direction.STRAIGHT, Direction.RIGHT);
        private boolean collided;

        public Cart(int id, int row, int col, Direction direction) {
            this.id = id;
            this.row = row;
            this.col = col;
            this.direction = direction;
        }

        private Direction currentIntersection(Direction direction) {
            Direction newDirection = intersection.get(intIdx);
            if (newDirection == Direction.STRAIGHT) {
                return direction;
            }
            if (direction == Direction.UP) {
                return newDirection;
            }
            if (direction == Direction.RIGHT) {
                if (newDirection == Direction.LEFT) {
                    return Direction.UP;
                } else if (newDirection == Direction.RIGHT) {
                    return Direction.DOWN;
                }
            }
            if (direction == Direction.DOWN) {
                if (newDirection == Direction.LEFT) {
                    return Direction.RIGHT;
                } else if (newDirection == Direction.RIGHT) {
                    return Direction.LEFT;
                }
            }
            if (direction == Direction.LEFT) {
                if (newDirection == Direction.LEFT) {
                    return Direction.DOWN;
                } else if (newDirection == Direction.RIGHT) {
                    return Direction.UP;
                }
            }
            throw new IllegalStateException("Boom!");
        }

        private void nextIntersection() {
            intIdx++;
            intIdx = intIdx % intersection.size();
        }

        @Override
        public String toString() {
            return id + ": (" + row + ", " + col + ", " + direction + ") --> " + collided;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
        System.out.println(part2());
    }
}
