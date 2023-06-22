package horus;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class CompositeBlockImpl extends BlockImpl implements CompositeIBlock {
    private List<Block> blocks = new LinkedList<>();

    public CompositeBlockImpl(String colour, String material) {
        super(colour, material);
    }

    @Override
    public List<Block> getBlocks() {
        return Collections.unmodifiableList(blocks);
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    public Stream<Block> toStream() {
        Stream<Block> flattenedBlocks = blocks.stream()
                .flatMap(Block::toStream);

        return Stream.concat(Stream.of(this), flattenedBlocks);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CompositeBlockImpl that = (CompositeBlockImpl) o;
        return Objects.equals(blocks, that.blocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), blocks);
    }
}
