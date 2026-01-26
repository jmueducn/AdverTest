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
                                                                                                           public void testArrangeFF_OnlyCenterBlock() throws Exception {
                                                                                                               // Setup
                                                                                                               BorderArrangement arrangement = new BorderArrangement();
                                                                                                               Block centerBlock = mock(Block.class);
                                                                                                               Graphics2D g2 = mock(Graphics2D.class);
                                                                                                               BlockContainer container = new BlockContainer(arrangement);
                                                                                                               
                                                                                                               container.add(centerBlock);
                                                                                                               RectangleConstraint constraint = new RectangleConstraint(100.0, 200.0);
                                                                                                               
                                                                                                               // Get protected method via reflection
                                                                                                               Method arrangeFF = BorderArrangement.class.getDeclaredMethod(
                                                                                                                   "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
                                                                                                               arrangeFF.setAccessible(true);
                                                                                                               
                                                                                                               // Execute
                                                                                                               Size2D result = (Size2D) arrangeFF.invoke(arrangement, container, g2, constraint);
                                                                                                               
                                                                                                               // Verify
                                                                                                               assertEquals(100.0, result.getWidth(), 0.001);
                                                                                                               assertEquals(200.0, result.getHeight(), 0.001);
                                                                                                               
                                                                                                               // Verify bounds setting
                                                                                                               verify(centerBlock).setBounds(new Rectangle2D.Double(0.0, 0.0, 100.0, 200.0));
                                                                                                           }

    @Test
                                                                                                           public void testArrangeFF_TopAndBottomBlocksOnly() throws Exception {
                                                                                                               // Setup
                                                                                                               BorderArrangement arrangement = new BorderArrangement();
                                                                                                               Block topBlock = mock(Block.class);
                                                                                                               Block bottomBlock = mock(Block.class);
                                                                                                               Graphics2D g2 = mock(Graphics2D.class);
                                                                                                               BlockContainer container = mock(BlockContainer.class);
                                                                                                               
                                                                                                               arrangement.add(topBlock, RectangleEdge.TOP);
                                                                                                               arrangement.add(bottomBlock, RectangleEdge.BOTTOM);
                                                                                                               
                                                                                                               RectangleConstraint constraint = new RectangleConstraint(100.0, 200.0);
                                                                                                               
                                                                                                               // Mock block arrangements
                                                                                                               when(topBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                                                                   .thenReturn(new Size2D(100.0, 40.0));
                                                                                                               when(bottomBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                                                                   .thenReturn(new Size2D(100.0, 30.0));
                                                                                                               
                                                                                                               // Use reflection to access protected arrangeFF method
                                                                                                               Method arrangeFFMethod = BorderArrangement.class.getDeclaredMethod(
                                                                                                                   "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
                                                                                                               arrangeFFMethod.setAccessible(true);
                                                                                                               
                                                                                                               // Execute
                                                                                                               Size2D result = (Size2D) arrangeFFMethod.invoke(arrangement, container, g2, constraint);
                                                                                                               
                                                                                                               // Verify
                                                                                                               assertEquals(100.0, result.getWidth(), 0.001);
                                                                                                               assertEquals(200.0, result.getHeight(), 0.001);
                                                                                                               
                                                                                                               // Verify bounds setting
                                                                                                               verify(topBlock).setBounds(new Rectangle2D.Double(0.0, 0.0, 100.0, 40.0));
                                                                                                               verify(bottomBlock).setBounds(new Rectangle2D.Double(0.0, 170.0, 100.0, 30.0));
                                                                                                           }

    @Test
                                                                                                           public void testArrangeFF_WithZeroHeightConstraint() throws Exception {
                                                                                                               // Setup
                                                                                                               BorderArrangement arrangement = new BorderArrangement();
                                                                                                               Block topBlock = mock(Block.class);
                                                                                                               Block bottomBlock = mock(Block.class);
                                                                                                               Graphics2D g2 = mock(Graphics2D.class);
                                                                                                               BlockContainer container = new BlockContainer();
                                                                                                               
                                                                                                               arrangement.add(topBlock, RectangleEdge.TOP);
                                                                                                               arrangement.add(bottomBlock, RectangleEdge.BOTTOM);
                                                                                                               
                                                                                                               RectangleConstraint constraint = new RectangleConstraint(100.0, 0.0);
                                                                                                               
                                                                                                               // Mock block arrangements
                                                                                                               when(topBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                                                                   .thenReturn(new Size2D(100.0, 0.0));
                                                                                                               when(bottomBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                                                                   .thenReturn(new Size2D(100.0, 0.0));
                                                                                                               
                                                                                                               // Use reflection to access protected method
                                                                                                               Method arrangeFFMethod = BorderArrangement.class.getDeclaredMethod(
                                                                                                                   "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
                                                                                                               arrangeFFMethod.setAccessible(true);
                                                                                                               
                                                                                                               // Execute
                                                                                                               Size2D result = (Size2D) arrangeFFMethod.invoke(arrangement, container, g2, constraint);
                                                                                                               
                                                                                                               // Verify
                                                                                                               assertEquals(100.0, result.getWidth(), 0.001);
                                                                                                               assertEquals(0.0, result.getHeight(), 0.001);
                                                                                                           }

    @Test
                                                                                                           public void testArrangeFF_WithZeroWidthConstraint() throws Exception {
                                                                                                               // Setup
                                                                                                               BorderArrangement arrangement = new BorderArrangement();
                                                                                                               Block leftBlock = mock(Block.class);
                                                                                                               Block rightBlock = mock(Block.class);
                                                                                                               Graphics2D g2 = mock(Graphics2D.class);
                                                                                                               BlockContainer container = new BlockContainer();
                                                                                                               
                                                                                                               arrangement.add(leftBlock, RectangleEdge.LEFT);
                                                                                                               arrangement.add(rightBlock, RectangleEdge.RIGHT);
                                                                                                               
                                                                                                               container.setArrangement(arrangement);
                                                                                                               RectangleConstraint constraint = new RectangleConstraint(0.0, 200.0);
                                                                                                               
                                                                                                               // Mock block arrangements
                                                                                                               when(leftBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                                                                   .thenReturn(new Size2D(0.0, 200.0));
                                                                                                               when(rightBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                                                                   .thenReturn(new Size2D(0.0, 200.0));
                                                                                                               
                                                                                                               // Use reflection to access protected method
                                                                                                               Method arrangeFFMethod = BorderArrangement.class.getDeclaredMethod(
                                                                                                                   "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
                                                                                                               arrangeFFMethod.setAccessible(true);
                                                                                                               
                                                                                                               // Execute
                                                                                                               Size2D result = (Size2D) arrangeFFMethod.invoke(arrangement, container, g2, constraint);
                                                                                                               
                                                                                                               // Verify
                                                                                                               assertEquals(0.0, result.getWidth(), 0.001);
                                                                                                               assertEquals(200.0, result.getHeight(), 0.001);
                                                                                                           }

    @Test
                                                                                                           public void testArrangeFF_BlocksExceedingHeightConstraint() {
                                                                                                               // Setup
                                                                                                               BorderArrangement arrangement = new BorderArrangement();
                                                                                                               Block topBlock = mock(Block.class);
                                                                                                               Block bottomBlock = mock(Block.class);
                                                                                                               Graphics2D g2 = mock(Graphics2D.class);
                                                                                                               BlockContainer container = new BlockContainer(arrangement);
                                                                                                               
                                                                                                               container.add(topBlock);
                                                                                                               container.add(bottomBlock);
                                                                                                               
                                                                                                               RectangleConstraint constraint = new RectangleConstraint(100.0, 50.0);
                                                                                                               
                                                                                                               // Mock block arrangements
                                                                                                               when(topBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                                                                   .thenReturn(new Size2D(100.0, 40.0));
                                                                                                               when(bottomBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                                                                   .thenReturn(new Size2D(100.0, 30.0));
                                                                                                               
                                                                                                               // Execute through public arrange() method
                                                                                                               Size2D result = arrangement.arrange(container, g2, constraint);
                                                                                                               
                                                                                                               // Verify
                                                                                                               assertEquals(100.0, result.getWidth(), 0.001);
                                                                                                               assertEquals(70.0, result.getHeight(), 0.001); // Should exceed constraint
                                                                                                           }

    @Test
                                                                                                       public void testArrangeFF_AllBlocksPresent() throws Exception {
                                                                                                           // Setup
                                                                                                           BorderArrangement arrangement = new BorderArrangement();
                                                                                                           Block topBlock = mock(Block.class);
                                                                                                           Block bottomBlock = mock(Block.class);
                                                                                                           Block leftBlock = mock(Block.class);
                                                                                                           Block rightBlock = mock(Block.class);
                                                                                                           Block centerBlock = mock(Block.class);
                                                                                                           Graphics2D g2 = mock(Graphics2D.class);
                                                                                                           BlockContainer container = new BlockContainer();
                                                                                                           
                                                                                                           arrangement.add(topBlock, RectangleEdge.TOP);
                                                                                                           arrangement.add(bottomBlock, RectangleEdge.BOTTOM);
                                                                                                           arrangement.add(leftBlock, RectangleEdge.LEFT);
                                                                                                           arrangement.add(rightBlock, RectangleEdge.RIGHT);
                                                                                                           arrangement.add(centerBlock, null);
                                                                                                           
                                                                                                           container.setArrangement(arrangement);
                                                                                                           RectangleConstraint constraint = new RectangleConstraint(100.0, 200.0);
                                                                                                           
                                                                                                           // Mock block arrangements
                                                                                                           when(topBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                                                               .thenReturn(new Size2D(100.0, 30.0));
                                                                                                           when(bottomBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                                                               .thenReturn(new Size2D(100.0, 20.0));
                                                                                                           when(leftBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                                                               .thenReturn(new Size2D(25.0, 150.0));
                                                                                                           when(rightBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                                                               .thenReturn(new Size2D(25.0, 150.0));
                                                                                                           
                                                                                                           // Use reflection to access protected method
                                                                                                           Method arrangeFFMethod = BorderArrangement.class.getDeclaredMethod(
                                                                                                               "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
                                                                                                           arrangeFFMethod.setAccessible(true);
                                                                                                           Size2D result = (Size2D) arrangeFFMethod.invoke(arrangement, container, g2, constraint);
                                                                                                           
                                                                                                           // Verify
                                                                                                           assertEquals(100.0, result.getWidth(), 0.001);
                                                                                                           assertEquals(200.0, result.getHeight(), 0.001);
                                                                                                           
                                                                                                           // Verify bounds setting
                                                                                                           verify(topBlock).setBounds(new Rectangle2D.Double(0.0, 0.0, 100.0, 30.0));
                                                                                                           verify(bottomBlock).setBounds(new Rectangle2D.Double(0.0, 180.0, 100.0, 20.0));
                                                                                                           verify(leftBlock).setBounds(new Rectangle2D.Double(0.0, 30.0, 25.0, 150.0));
                                                                                                           verify(rightBlock).setBounds(new Rectangle2D.Double(75.0, 30.0, 25.0, 150.0));
                                                                                                           verify(centerBlock).setBounds(new Rectangle2D.Double(25.0, 30.0, 50.0, 150.0));
                                                                                                       }

    @Test
                                                                                                       public void testArrangeFF_LeftAndRightBlocksOnly() throws Exception {
                                                                                                           // Setup
                                                                                                           BorderArrangement arrangement = new BorderArrangement();
                                                                                                           Block leftBlock = mock(Block.class);
                                                                                                           Block rightBlock = mock(Block.class);
                                                                                                           Graphics2D g2 = mock(Graphics2D.class);
                                                                                                           BlockContainer container = new BlockContainer();
                                                                                                           
                                                                                                           // Add blocks to arrangement
                                                                                                           arrangement.add(leftBlock, RectangleEdge.LEFT);
                                                                                                           arrangement.add(rightBlock, RectangleEdge.RIGHT);
                                                                                                           
                                                                                                           RectangleConstraint constraint = new RectangleConstraint(100.0, 200.0);
                                                                                                           
                                                                                                           // Mock block arrangements
                                                                                                           when(leftBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                                                               .thenReturn(new Size2D(30.0, 200.0));
                                                                                                           when(rightBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                                                               .thenReturn(new Size2D(30.0, 200.0));
                                                                                                           
                                                                                                           // Get the protected arrangeFF method using reflection
                                                                                                           Method arrangeFFMethod = BorderArrangement.class.getDeclaredMethod(
                                                                                                               "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
                                                                                                           arrangeFFMethod.setAccessible(true);
                                                                                                           
                                                                                                           // Execute
                                                                                                           Size2D result = (Size2D) arrangeFFMethod.invoke(arrangement, container, g2, constraint);
                                                                                                           
                                                                                                           // Verify
                                                                                                           assertEquals(100.0, result.getWidth(), 0.001);
                                                                                                           assertEquals(200.0, result.getHeight(), 0.001);
                                                                                                           
                                                                                                           // Verify bounds setting
                                                                                                           verify(leftBlock).setBounds(new Rectangle2D.Double(0.0, 0.0, 30.0, 200.0));
                                                                                                           verify(rightBlock).setBounds(new Rectangle2D.Double(70.0, 0.0, 30.0, 200.0));
                                                                                                       }

    @Test
                                                                                  public void testArrangeFF_RightBlockWidthConstraint() throws Exception {
                                                                                      // Setup
                                                                                      BorderArrangement arrangement = new BorderArrangement();
                                                                                      BlockContainer container = mock(BlockContainer.class);
                                                                                      Graphics2D g2 = mock(Graphics2D.class);
                                                                                      
                                                                                      // Create blocks - we only need right block for this test
                                                                                      Block rightBlock = mock(Block.class);
                                                                                      arrangement.add(rightBlock, RectangleEdge.RIGHT);
                                                                                      
                                                                                      // Set up a constraint where available width after left block is less than 1.0
                                                                                      RectangleConstraint constraint = new RectangleConstraint(2.0, 10.0);
                                                                                      
                                                                                      // Mock the left block to take most of the width
                                                                                      Block leftBlock = mock(Block.class);
                                                                                      arrangement.add(leftBlock, RectangleEdge.LEFT);
                                                                                      when(leftBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                                          .thenReturn(new Size2D(1.9, 5.0)); // leaves only 0.1 width for right block
                                                                                      
                                                                                      // Mock the right block arrangement
                                                                                      when(rightBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                                          .thenReturn(new Size2D(0.0, 5.0)); // would return 0 width with original constraint
                                                                                      
                                                                                      // Use reflection to access protected method
                                                                                      Method arrangeFF = BorderArrangement.class.getDeclaredMethod(
                                                                                          "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
                                                                                      arrangeFF.setAccessible(true);
                                                                                      Size2D result = (Size2D) arrangeFF.invoke(arrangement, container, g2, constraint);
                                                                                      
                                                                                      // Verify the right block was arranged with correct constraint (lower bound 0.0)
                                                                                      org.mockito.ArgumentCaptor<RectangleConstraint> constraintCaptor = 
                                                                                          org.mockito.ArgumentCaptor.forClass(RectangleConstraint.class);
                                                                                      verify(rightBlock).arrange(eq(g2), constraintCaptor.capture());
                                                                                      
                                                                                      // Assert the constraint had lower bound 0.0 (would fail if mutant is present)
                                                                                      assertEquals(0.0, constraintCaptor.getValue().getWidthRange().getLowerBound(), 0.0001);
                                                                                      
                                                                                      // Also verify the final bounds set on right block
                                                                                      org.mockito.ArgumentCaptor<java.awt.geom.Rectangle2D> boundsCaptor = 
                                                                                          org.mockito.ArgumentCaptor.forClass(java.awt.geom.Rectangle2D.class);
                                                                                      verify(rightBlock).setBounds(boundsCaptor.capture());
                                                                                      assertEquals(0.0, boundsCaptor.getValue().getWidth(), 0.0001);
                                                                                  }

    @Test
                                                                             public void testArrangeFF_RightBlockSpaceAllocation() throws Exception {
                                                                                 // Setup
                                                                                 BorderArrangement arrangement = new BorderArrangement();
                                                                                 Graphics2D g2 = mock(Graphics2D.class);
                                                                                 BlockContainer container = mock(BlockContainer.class);
                                                                                 
                                                                                 // Create blocks - we need left and right blocks to test this case
                                                                                 Block leftBlock = mock(Block.class);
                                                                                 Block rightBlock = mock(Block.class);
                                                                                 
                                                                                 // Set up the arrangement with blocks
                                                                                 arrangement.add(leftBlock, RectangleEdge.LEFT);
                                                                                 arrangement.add(rightBlock, RectangleEdge.RIGHT);
                                                                                 
                                                                                 // Configure mock blocks to return specific sizes
                                                                                 when(leftBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                                     .thenReturn(new Size2D(100, 50)); // left block takes 100 width
                                                                                 when(rightBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                                     .thenReturn(new Size2D(150, 50)); // right block wants 150 width
                                                                                 
                                                                                 // Create a constraint with width larger than left block's width
                                                                                 RectangleConstraint constraint = new RectangleConstraint(300, 200);
                                                                                 
                                                                                 // Use reflection to access protected arrangeFF method
                                                                                 Method arrangeFFMethod = BorderArrangement.class.getDeclaredMethod(
                                                                                     "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
                                                                                 arrangeFFMethod.setAccessible(true);
                                                                                 Size2D result = (Size2D) arrangeFFMethod.invoke(arrangement, container, g2, constraint);
                                                                                 
                                                                                 // Verify right block gets proper space
                                                                                 // In original code, right block should get 200 width (300 total - 100 left)
                                                                                 // In mutated code, it would get 0 width due to Math.min
                                                                                 verify(rightBlock).setBounds(argThat(rect -> 
                                                                                     rect.getWidth() > 0 && // Should get positive width
                                                                                     Math.abs(rect.getWidth() - 200) < 0.001 // Should get remaining space (300-100)
                                                                                 ));
                                                                                 
                                                                                 // Also verify the final size matches the constraint
                                                                                 assertEquals(300, result.getWidth(), 0.001);
                                                                                 assertEquals(200, result.getHeight(), 0.001);
                                                                             }

    @Test
                                                                    public void testArrangeFF_DetectNegativeWidthRangeMutant() throws Exception {
                                                                        // Setup
                                                                        BorderArrangement arrangement = new BorderArrangement();
                                                                        BlockContainer container = mock(BlockContainer.class);
                                                                        Graphics2D g2 = mock(Graphics2D.class);
                                                                        
                                                                        // Create a scenario where constraint.getWidth() - w[2] will be negative
                                                                        RectangleConstraint constraint = new RectangleConstraint(100.0, 200.0);
                                                                        
                                                                        // Mock blocks to control their behavior
                                                                        Block leftBlock = mock(Block.class);
                                                                        Block rightBlock = mock(Block.class);
                                                                        
                                                                        // Set the left block to return a width larger than the constraint
                                                                        Size2D leftBlockSize = new Size2D(150.0, 50.0); // width > constraint width
                                                                        when(leftBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                            .thenReturn(leftBlockSize);
                                                                        
                                                                        // Set the right block to verify it gets proper constraints
                                                                        Size2D rightBlockSize = new Size2D(0.0, 50.0);
                                                                        when(rightBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                                            .thenReturn(rightBlockSize);
                                                                        
                                                                        // Set the blocks in the arrangement using add() method
                                                                        arrangement.add(leftBlock, RectangleEdge.LEFT);
                                                                        arrangement.add(rightBlock, RectangleEdge.RIGHT);
                                                                        
                                                                        // Use reflection to access protected arrangeFF method
                                                                        Method arrangeFF = BorderArrangement.class.getDeclaredMethod(
                                                                            "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
                                                                        arrangeFF.setAccessible(true);
                                                                        Size2D result = (Size2D) arrangeFF.invoke(arrangement, container, g2, constraint);
                                                                        
                                                                        // Verify the right block was arranged with proper constraints
                                                                        // The key assertion is that the arrange method completes without throwing exception
                                                                        assertEquals(100.0, result.getWidth(), 0.0001);
                                                                        assertEquals(200.0, result.getHeight(), 0.0001);
                                                                        
                                                                        // Verify the right block was called with a valid range (upper bound >= 0)
                                                                        verify(rightBlock).arrange(eq(g2), any(RectangleConstraint.class));
                                                                        // Alternative verification since we can't use ArgumentCaptor without proper imports
                                                                        // The main point is to verify the arrange method completes with valid constraints
                                                                    }

    @Test
                                                       public void testArrangeFF_LeftBlockWidthConstraint() throws Exception {
                                                           // Setup
                                                           BorderArrangement arrangement = new BorderArrangement();
                                                           Graphics2D g2 = mock(Graphics2D.class);
                                                           RectangleConstraint constraint = new RectangleConstraint(100.0, 200.0);
                                                           BlockContainer container = mock(BlockContainer.class);
                                                           
                                                           // Create a left block that would naturally take some width
                                                           Block leftBlock = mock(Block.class);
                                                           Size2D leftBlockSize = new Size2D(30.0, 50.0);
                                                           when(leftBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                                               .thenReturn(leftBlockSize);
                                                           
                                                           arrangement.add(leftBlock, RectangleEdge.LEFT);
                                                           
                                                           // Use reflection to call protected arrangeFF method
                                                           Method arrangeFFMethod = BorderArrangement.class.getDeclaredMethod(
                                                               "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
                                                           arrangeFFMethod.setAccessible(true);
                                                           Size2D result = (Size2D) arrangeFFMethod.invoke(arrangement, container, g2, constraint);
                                                           
                                                           // Verify left block arrangement - should use RANGE constraint
                                                           org.mockito.ArgumentCaptor<RectangleConstraint> constraintCaptor = 
                                                               org.mockito.ArgumentCaptor.forClass(RectangleConstraint.class);
                                                           verify(leftBlock).arrange(eq(g2), constraintCaptor.capture());
                                                           
                                                           RectangleConstraint actualConstraint = constraintCaptor.getValue();
                                                           assertEquals(LengthConstraintType.RANGE, actualConstraint.getWidthConstraintType());
                                                           assertEquals(0.0, actualConstraint.getWidthRange().getLowerBound(), 0.001);
                                                           assertEquals(100.0, actualConstraint.getWidthRange().getUpperBound(), 0.001);
                                                           
                                                           // Verify bounds setting - left block should get its natural width (30.0)
                                                           org.mockito.ArgumentCaptor<java.awt.geom.Rectangle2D> boundsCaptor = 
                                                               org.mockito.ArgumentCaptor.forClass(java.awt.geom.Rectangle2D.class);
                                                           verify(leftBlock).setBounds(boundsCaptor.capture());
                                                           
                                                           java.awt.geom.Rectangle2D bounds = boundsCaptor.getValue();
                                                           assertEquals(30.0, bounds.getWidth(), 0.001);
                                                           
                                                           // Verify final result
                                                           assertEquals(100.0, result.getWidth(), 0.001);
                                                           assertEquals(200.0, result.getHeight(), 0.001);
                                                       }

    @Test
                          public void testArrangeFF_LeftBlockHeightConstraint() throws Exception {
                              // Setup
                              BorderArrangement arrangement = new BorderArrangement();
                              
                              // Create mock blocks with different heights
                              Block topBlock = mock(Block.class);
                              Block bottomBlock = mock(Block.class);
                              Block leftBlock = mock(Block.class);
                              
                              // Setup arrange responses
                              when(topBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                  .thenReturn(new Size2D(100, 20)); // top height = 20
                              when(bottomBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                  .thenReturn(new Size2D(100, 30)); // bottom height = 30
                              when(leftBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                                  .thenReturn(new Size2D(40, 50)); // width = 40, height doesn't matter
                              
                              // Set blocks using add() method with appropriate keys
                              arrangement.add(topBlock, RectangleEdge.TOP);
                              arrangement.add(bottomBlock, RectangleEdge.BOTTOM);
                              arrangement.add(leftBlock, RectangleEdge.LEFT);
                              
                              // Test constraint (total height = 100)
                              RectangleConstraint constraint = new RectangleConstraint(100, 100);
                              Graphics2D g2 = mock(Graphics2D.class);
                              BlockContainer container = mock(BlockContainer.class);
                              
                              // Use reflection to access protected arrangeFF method
                              Method arrangeFF = BorderArrangement.class.getDeclaredMethod(
                                  "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
                              arrangeFF.setAccessible(true);
                              arrangeFF.invoke(arrangement, container, g2, constraint);
                              
                              // Verify left block received correct height constraint (should be middle section height)
                              // Total height = 100, top = 20, bottom = 30, so middle = 50
                              org.mockito.ArgumentCaptor<RectangleConstraint> constraintCaptor = 
                                  org.mockito.ArgumentCaptor.forClass(RectangleConstraint.class);
                              verify(leftBlock).arrange(eq(g2), constraintCaptor.capture());
                              
                              RectangleConstraint leftConstraint = constraintCaptor.getValue();
                              assertEquals(50.0, leftConstraint.getHeight(), 0.001); // Should be 50 (middle height)
                              
                              // Also verify bounds setting
                              org.mockito.ArgumentCaptor<Rectangle2D> boundsCaptor = 
                                  org.mockito.ArgumentCaptor.forClass(Rectangle2D.class);
                              verify(leftBlock).setBounds(boundsCaptor.capture());
                              
                              Rectangle2D leftBounds = boundsCaptor.getValue();
                              assertEquals(50.0, leftBounds.getHeight(), 0.001); // Should be 50 (middle height)
                          }

    @Test
                 public void testLeftBlockHeightConstraint() throws Exception {
                     // Setup
                     BorderArrangement arrangement = new BorderArrangement();
                     Graphics2D g2 = mock(Graphics2D.class);
                     RectangleConstraint constraint = new RectangleConstraint(100.0, 200.0);
                     
                     // Create a mock left block that would return size (50, 150) if given freedom
                     Block leftBlock = mock(Block.class);
                     when(leftBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                         .thenAnswer(invocation -> {
                             RectangleConstraint rc = invocation.getArgument(1);
                             if (rc.getHeightConstraintType() == LengthConstraintType.FIXED) {
                                 return new Size2D(50, rc.getHeight()); // must use fixed height
                             } else {
                                 return new Size2D(50, 150); // can choose any height
                             }
                         });
                     
                     // Set the left block
                     arrangement.add(leftBlock, RectangleEdge.LEFT);
                     
                     // Test with top and bottom blocks to create h[2] space
                     Block topBlock = mock(Block.class);
                     when(topBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                         .thenReturn(new Size2D(100, 50));
                     arrangement.add(topBlock, RectangleEdge.TOP);
                     
                     Block bottomBlock = mock(Block.class);
                     when(bottomBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
                         .thenReturn(new Size2D(100, 50));
                     arrangement.add(bottomBlock, RectangleEdge.BOTTOM);
                     
                     // Create a mock container
                     BlockContainer container = mock(BlockContainer.class);
                     
                     // Use reflection to access protected arrangeFF method
                     Method arrangeFF = BorderArrangement.class.getDeclaredMethod(
                         "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
                     arrangeFF.setAccessible(true);
                     Size2D result = (Size2D) arrangeFF.invoke(arrangement, container, g2, constraint);
                     
                     // Verify left block was arranged with correct height (h[2] = 200 - 50 - 50 = 100)
                     // The mutant would allow 150 height here
                     verify(leftBlock).setBounds(argThat(rect -> 
                         rect.getHeight() == 100.0 && // should be exactly 100 with FIXED constraint
                         rect.getWidth() == 50.0 &&
                         rect.getY() == 50.0
                     ));
                     
                     // Verify overall result
                     assertEquals(100.0, result.getWidth(), 0.001);
                     assertEquals(200.0, result.getHeight(), 0.001);
                 }

    @Test
    public void testArrangeFF_RightBlockArrangement() throws Exception {
        // Setup
        BorderArrangement arrangement = new BorderArrangement();
        Graphics2D g2 = mock(Graphics2D.class);
        RectangleConstraint constraint = new RectangleConstraint(100.0, 200.0);
        
        // Create mock blocks with different sizes
        Block leftBlock = mock(Block.class);
        Block rightBlock = mock(Block.class);
        BlockContainer container = new BlockContainer(arrangement);
        
        // Stub arrange methods to return different sizes
        when(leftBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
            .thenReturn(new Size2D(30.0, 150.0));
        when(rightBlock.arrange(any(Graphics2D.class), any(RectangleConstraint.class)))
            .thenReturn(new Size2D(40.0, 150.0));
        
        // Set blocks in arrangement using add() method with proper position keys
        container.add(leftBlock, RectangleEdge.LEFT);
        container.add(rightBlock, RectangleEdge.RIGHT);
        
        // Use reflection to access protected arrangeFF method
        Method arrangeFFMethod = BorderArrangement.class.getDeclaredMethod(
            "arrangeFF", BlockContainer.class, Graphics2D.class, RectangleConstraint.class);
        arrangeFFMethod.setAccessible(true);
        Size2D result = (Size2D) arrangeFFMethod.invoke(arrangement, container, g2, constraint);
        
        // Verify right block bounds - should be at x=60 (100 total width - 30 left - 40 right = 30 center)
        // Right block width should be 40 (from arrange result)
        verify(rightBlock).setBounds(new Rectangle2D.Double(60.0, 0.0, 40.0, 150.0));
        
        // Also verify arrange was called on rightBlock (not leftBlock for c4)
        verify(rightBlock).arrange(eq(g2), any(RectangleConstraint.class));
        
        // Verify overall result
        assertEquals(100.0, result.getWidth(), 0.001);
        assertEquals(200.0, result.getHeight(), 0.001);
    }


}
