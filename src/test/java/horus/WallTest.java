package horus;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class WallTest {
    private static final BlockImpl BLOCK1 = new BlockImpl("colour1", "material1");
    private static final BlockImpl BLOCK2 = new BlockImpl("colour2", "material2");
    private static final CompositeBlockImpl COMPOSITE_BLOCK3 = new CompositeBlockImpl("colour3", "material3");
    private static final BlockImpl BLOCK4 = new BlockImpl("colour4", "material4");
    private static final CompositeBlockImpl COMPOSITE_BLOCK5 = new CompositeBlockImpl("colour5", "material5");
    private static final BlockImpl BLOCK6 = new BlockImpl("colour6", "material6");
    private Wall emptyStructure;
    private Wall filledStructure;

    @BeforeAll
    static void setUpClass() {
        COMPOSITE_BLOCK3.addBlock(BLOCK4);
        COMPOSITE_BLOCK3.addBlock(COMPOSITE_BLOCK5);

        COMPOSITE_BLOCK5.addBlock(BLOCK6);
    }

    @BeforeEach
    void setUp() {
        emptyStructure = new Wall();

        filledStructure = new Wall(BLOCK1, BLOCK2, COMPOSITE_BLOCK3);

    }

    @Test
    public void shouldBeAbleToInstantiateClass() {
        assertThat(emptyStructure, notNullValue());
    }

    @Test
    public void shouldReturnNullWhenNotFoundByColourAndStructureIsEmpty() {
        assertThat(emptyStructure.findBlockByColor("xxx"), is(Optional.empty()));
    }

    @Test
    public void shouldReturnNullWhenNotFoundByMaterialAndStructureIsEmpty() {
        assertThat(emptyStructure.findBlocksByMaterial("xxx"), is(List.of()));
    }

    @Test
    public void shouldReturn0WhenStructureIsEmpty() {
        assertThat(emptyStructure.count(), is(equalTo(0)));
    }

    @Test
    public void shouldReturnNullWhenNotFoundByColour() {
        assertThat(filledStructure.findBlockByColor("missingColour"), is(Optional.empty()));
    }

    @Test
    public void shouldReturnNullWhenNotFoundByMaterial() {
        assertThat(filledStructure.findBlocksByMaterial("missingMaterial"), is(List.of()));
    }

    @Test
    public void shouldReturnBlockFoundByColour() {
        assertThat(filledStructure.findBlockByColor("colour2").get(), is(BLOCK2));
    }

    @Test
    public void shouldReturnBlockFoundByMaterial() {
        assertThat(filledStructure.findBlocksByMaterial("material2"), is(listOfBlocksToTest(BLOCK2)));
    }

    @Test
    public void shouldFindCompositeBlockByColour() {
        assertThat(filledStructure.findBlockByColor("colour3").get(), is(COMPOSITE_BLOCK3));
    }

    @Test
    public void shouldFindCompositeBlockByReferer() {
        assertThat(filledStructure.findBlocksByMaterial("material3"), is(listOfBlocksToTest(COMPOSITE_BLOCK3)));
    }

    @Test
    public void shouldBeAbleToFindNestedBlockByColour() {
        assertThat(filledStructure.findBlockByColor("colour4").get(), is(BLOCK4));
    }

    @Test
    public void testAnother() {
        MatcherAssert.assertThat(filledStructure.findBlockByColor("colour6").get(), is(BLOCK6));
        MatcherAssert.assertThat(filledStructure.findBlockByColor("colour5").get(), is(COMPOSITE_BLOCK5));
        MatcherAssert.assertThat(filledStructure.findBlockByColor("colour2").get(), is(BLOCK2));
        MatcherAssert.assertThat(filledStructure.findBlockByColor("colour1").get(), is(BLOCK1));
        MatcherAssert.assertThat(filledStructure.findBlockByColor("colour3").get(), is(COMPOSITE_BLOCK3));
        MatcherAssert.assertThat(filledStructure.findBlockByColor("colour6").get(), is(BLOCK6));
    }

    @Test
    public void shouldHandleCountProperlyWithNestedStructure() {
        MatcherAssert.assertThat(filledStructure.count(), is(6));
    }

    private List<Block> listOfBlocksToTest(Block block) {
        return List.of(block);
    }
}
