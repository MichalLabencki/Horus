package horus;

import java.util.Objects;
import java.util.stream.Stream;

public class BlockImpl implements Block {
    private final String colour;
    private final String material;

    public BlockImpl(String colour, String material) {
        this.colour = colour;
        this.material = material;
    }

    @Override
    public String getColor() {
        return colour;
    }

    @Override
    public String getMaterial() {
        return material;
    }

    @Override
    public Stream<Block> toStream() {
        return Stream.of(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockImpl block = (BlockImpl) o;
        return Objects.equals(colour, block.colour) && Objects.equals(material, block.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colour, material);
    }
}
