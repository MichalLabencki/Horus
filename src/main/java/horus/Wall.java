package horus;


import java.util.*;
import java.util.stream.Collectors;

public class Wall implements Structure {
    private List<Block> blocks = new ArrayList<>();

    public Wall(BlockImpl... blocks) {
        this.blocks = Arrays.stream(blocks).collect(Collectors.toList());
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        Objects.requireNonNull(color);
        return blocks.stream().flatMap(Block::toStream).filter(it -> color.equals(it.getColor())).findFirst();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return Objects.requireNonNull(blocks.stream()
                .filter(it -> material.equals(it.getMaterial()))
                .collect(Collectors.toList()));
    }

    @Override
    public int count() {
        return (int) blocks.stream().flatMap(Block::toStream).count();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wall wall = (Wall) o;
        return Objects.equals(blocks, wall.blocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blocks);
    }
}