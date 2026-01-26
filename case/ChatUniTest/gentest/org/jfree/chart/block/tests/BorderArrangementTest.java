package gentest.org.jfree.chart.block.tests;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.jfree.chart.block.*;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import org.jfree.chart.util.ObjectUtilities;
import org.jfree.chart.util.RectangleEdge;
import org.jfree.chart.util.Size2D;
import org.jfree.data.Range;
 
public class BorderArrangementTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Test
            public void testArrangeFFWithAllBlocks() throws Exception {
                // Setup mocks
                Block topBlock = mock(Block.class);
                Block bottomBlock = mock(Block.class);
                Block leftBlock = mock(Block.class);
                Block rightBlock = mock(Block.class);
                Block centerBlock = mock(Block.class);
                Graphics2D g2 = mock(Graphics2D.class);
                BlockContainer container = mock(BlockContainer.class);
                
                // Create arrangement and set blocks
                BorderArrangement borderArrangement = new BorderArrangement();
                
                // Setup constraints
                RectangleConstraint constraint = new RectangleConstraint(100.0, 200.0);
                
                // Setup mock blocks
                when(topBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                    .thenReturn(new Size2D(100.0, 20.0));
                when(bottomBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                    .thenReturn(new Size2D(100.0, 30.0));
                when(leftBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                    .thenReturn(new Size2D(25.0, 150.0));
                when(rightBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                    .thenReturn(new Size2D(25.0, 150.0));
                
                // Add blocks to arrangement
                borderArrangement.add(topBlock, RectangleEdge.TOP);
                borderArrangement.add(bottomBlock, RectangleEdge.BOTTOM);
                borderArrangement.add(leftBlock, RectangleEdge.LEFT);
                borderArrangement.add(rightBlock, RectangleEdge.RIGHT);
                borderArrangement.add(centerBlock, null);
                
                // Use reflection to access protected method
                Method arrangeFFMethod = BorderArrangement.class.getDeclaredMethod(
                    "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
                arrangeFFMethod.setAccessible(true);
                Size2D result = (Size2D) arrangeFFMethod.invoke(
                    borderArrangement, container, g2, constraint);
                
                // Verify
                assertEquals(100.0, result.getWidth(), 0.001);
                assertEquals(200.0, result.getHeight(), 0.001);
                
                // Verify top block
                verify(topBlock).setBounds(new Rectangle2D.Double(0.0, 0.0, 100.0, 20.0));
                
                // Verify bottom block
                verify(bottomBlock).setBounds(new Rectangle2D.Double(0.0, 170.0, 100.0, 30.0));
                
                // Verify left block
                verify(leftBlock).setBounds(new Rectangle2D.Double(0.0, 20.0, 25.0, 150.0));
                
                // Verify right block
                verify(rightBlock).setBounds(new Rectangle2D.Double(75.0, 20.0, 25.0, 150.0));
                
                // Verify center block
                verify(centerBlock).setBounds(new Rectangle2D.Double(25.0, 20.0, 50.0, 150.0));
            }

    @Test
            public void testArrangeFFWithOnlyTopBlock() throws Exception {
                // Create mocks
                Block topBlock = mock(Block.class);
                BlockContainer container = mock(BlockContainer.class);
                Graphics2D g2 = mock(Graphics2D.class);
                BorderArrangement borderArrangement = new BorderArrangement();
                
                RectangleConstraint constraint = new RectangleConstraint(100.0, 200.0);
                
                when(topBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                    .thenReturn(new Size2D(100.0, 50.0));
                
                borderArrangement.add(topBlock, RectangleEdge.TOP);
                
                // Use reflection to access protected method
                Method arrangeFFMethod = BorderArrangement.class.getDeclaredMethod(
                    "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
                arrangeFFMethod.setAccessible(true);
                Size2D result = (Size2D) arrangeFFMethod.invoke(borderArrangement, container, g2, constraint);
                
                assertEquals(100.0, result.getWidth(), 0.001);
                assertEquals(200.0, result.getHeight(), 0.001);
                verify(topBlock).setBounds(new Rectangle2D.Double(0.0, 0.0, 100.0, 50.0));
            }

    @Test
            public void testArrangeFFWithOnlyLeftAndRightBlocks() throws Exception {
                // Create mocks and test objects
                BorderArrangement borderArrangement = new BorderArrangement();
                BlockContainer container = mock(BlockContainer.class);
                Graphics2D g2 = mock(Graphics2D.class);
                Block leftBlock = mock(Block.class);
                Block rightBlock = mock(Block.class);
                
                RectangleConstraint constraint = new RectangleConstraint(100.0, 200.0);
                
                when(leftBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                    .thenReturn(new Size2D(30.0, 200.0));
                when(rightBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                    .thenReturn(new Size2D(30.0, 200.0));
                
                borderArrangement.add(leftBlock, RectangleEdge.LEFT);
                borderArrangement.add(rightBlock, RectangleEdge.RIGHT);
                
                // Use reflection to access protected method
                Method arrangeFFMethod = BorderArrangement.class.getDeclaredMethod(
                    "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
                arrangeFFMethod.setAccessible(true);
                Size2D result = (Size2D) arrangeFFMethod.invoke(borderArrangement, container, g2, constraint);
                
                assertEquals(100.0, result.getWidth(), 0.001);
                assertEquals(200.0, result.getHeight(), 0.001);
                
                verify(leftBlock).setBounds(new Rectangle2D.Double(0.0, 0.0, 30.0, 200.0));
                verify(rightBlock).setBounds(new Rectangle2D.Double(70.0, 0.0, 30.0, 200.0));
            }

    @Test
            public void testArrangeFFWithOnlyCenterBlock() throws Exception {
                // Setup test objects
                BorderArrangement borderArrangement = new BorderArrangement();
                Block centerBlock = mock(Block.class);
                BlockContainer container = mock(BlockContainer.class);
                Graphics2D g2 = mock(Graphics2D.class);
                RectangleConstraint constraint = new RectangleConstraint(100.0, 200.0);
                
                borderArrangement.add(centerBlock, null);
                
                // Use reflection to access protected method
                Method arrangeFFMethod = BorderArrangement.class.getDeclaredMethod(
                    "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
                arrangeFFMethod.setAccessible(true);
                Size2D result = (Size2D) arrangeFFMethod.invoke(
                    borderArrangement, container, g2, constraint);
                
                assertEquals(100.0, result.getWidth(), 0.001);
                assertEquals(200.0, result.getHeight(), 0.001);
                verify(centerBlock).setBounds(new Rectangle2D.Double(0.0, 0.0, 100.0, 200.0));
            }

    @Test
            public void testArrangeFFWithNoBlocks() throws Exception {
                BorderArrangement borderArrangement = new BorderArrangement();
                BlockContainer container = mock(BlockContainer.class);
                Graphics2D g2 = mock(Graphics2D.class);
                RectangleConstraint constraint = new RectangleConstraint(100.0, 200.0);
                
                Method method = BorderArrangement.class.getDeclaredMethod(
                    "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
                method.setAccessible(true);
                Size2D result = (Size2D) method.invoke(borderArrangement, container, g2, constraint);
                
                assertEquals(100.0, result.getWidth(), 0.001);
                assertEquals(200.0, result.getHeight(), 0.001);
            }

    @Test
        public void testArrangeFFWithOnlyBottomBlock() throws Exception {
            // Create mocks and test objects
            Block bottomBlock = mock(Block.class);
            BlockContainer container = mock(BlockContainer.class);
            Graphics2D g2 = mock(Graphics2D.class);
            BorderArrangement borderArrangement = new BorderArrangement();
            
            RectangleConstraint constraint = new RectangleConstraint(100.0, 200.0);
            
            when(bottomBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                .thenReturn(new Size2D(100.0, 40.0));
            
            borderArrangement.add(bottomBlock, RectangleEdge.BOTTOM);
            
            // Use reflection to access protected method
            Method arrangeFFMethod = BorderArrangement.class.getDeclaredMethod(
                "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
            arrangeFFMethod.setAccessible(true);
            Size2D result = (Size2D) arrangeFFMethod.invoke(borderArrangement, container, g2, constraint);
            
            assertEquals(100.0, result.getWidth(), 0.001);
            assertEquals(200.0, result.getHeight(), 0.001);
            verify(bottomBlock).setBounds(new Rectangle2D.Double(0.0, 160.0, 100.0, 40.0));
        }

    @Test
        public void testArrangeFFWithZeroWidth() throws Exception {
            // Create mocks and test objects
            BlockContainer container = mock(BlockContainer.class);
            Graphics2D g2 = mock(Graphics2D.class);
            Block topBlock = mock(Block.class);
            Block leftBlock = mock(Block.class);
            BorderArrangement borderArrangement = new BorderArrangement();
            
            RectangleConstraint constraint = new RectangleConstraint(0.0, 200.0);
            
            when(topBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                .thenReturn(new Size2D(0.0, 20.0));
            when(leftBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                .thenReturn(new Size2D(0.0, 150.0));
            
            borderArrangement.add(topBlock, RectangleEdge.TOP);
            borderArrangement.add(leftBlock, RectangleEdge.LEFT);
            
            // Use reflection to access protected method
            Method arrangeFFMethod = BorderArrangement.class.getDeclaredMethod(
                "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
            arrangeFFMethod.setAccessible(true);
            Size2D result = (Size2D) arrangeFFMethod.invoke(
                borderArrangement, container, g2, constraint);
            
            assertEquals(0.0, result.getWidth(), 0.001);
            assertEquals(200.0, result.getHeight(), 0.001);
            
            verify(topBlock).setBounds(new Rectangle2D.Double(0.0, 0.0, 0.0, 20.0));
            verify(leftBlock).setBounds(new Rectangle2D.Double(0.0, 20.0, 0.0, 150.0));
        }


}
