package org.jfree.chart.block;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegressionTest1 {

    public static boolean debug = false;

    public void assertBooleanArrayEquals(boolean[] expectedArray, boolean[] actualArray) {
        if (expectedArray.length != actualArray.length) {
            throw new AssertionError("Array lengths differ: " + expectedArray.length + " != " + actualArray.length);
        }
        for (int i = 0; i < expectedArray.length; i++) {
            if (expectedArray[i] != actualArray[i]) {
                throw new AssertionError("Arrays differ at index " + i + ": " + expectedArray[i] + " != " + actualArray[i]);
            }
        }
    }

    @Test
    public void test501() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test501");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.BlockBorder blockBorder2 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder2);
        java.util.List list4 = blockContainer1.getBlocks();
        blockContainer1.setPadding((double) 100.0f, 33.0d, (double) (-1L), (double) 1L);
        org.jfree.chart.block.BorderArrangement borderArrangement10 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer11 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement10);
        org.jfree.chart.block.BlockBorder blockBorder12 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer11.setFrame((org.jfree.chart.block.BlockFrame) blockBorder12);
        java.util.List list14 = blockContainer11.getBlocks();
        org.jfree.chart.block.EmptyBlock emptyBlock17 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.lang.Object obj18 = emptyBlock17.clone();
        emptyBlock17.setMargin((double) (-1.0f), (double) (byte) 10, (double) 'a', (double) 0L);
        org.jfree.chart.util.RectangleInsets rectangleInsets24 = emptyBlock17.getPadding();
        boolean boolean25 = blockContainer11.equals((java.lang.Object) emptyBlock17);
        org.jfree.chart.block.BlockFrame blockFrame26 = emptyBlock17.getFrame();
        java.awt.Graphics2D graphics2D27 = null;
        org.jfree.chart.block.RectangleConstraint rectangleConstraint30 = new org.jfree.chart.block.RectangleConstraint((double) (short) -1, (double) 100);
        double double31 = rectangleConstraint30.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint33 = rectangleConstraint30.toFixedHeight((double) 0);
        org.jfree.chart.util.Size2D size2D34 = emptyBlock17.arrange(graphics2D27, rectangleConstraint30);
        blockContainer1.add((org.jfree.chart.block.Block) emptyBlock17);
        java.lang.Object obj36 = blockContainer1.clone();
        org.junit.Assert.assertNotNull(blockBorder2);
        org.junit.Assert.assertNotNull(list4);
        org.junit.Assert.assertNotNull(blockBorder12);
        org.junit.Assert.assertNotNull(list14);
        org.junit.Assert.assertNotNull(obj18);
        org.junit.Assert.assertNotNull(rectangleInsets24);
        org.junit.Assert.assertTrue("'" + boolean25 + "' != '" + false + "'", boolean25 == false);
        org.junit.Assert.assertNotNull(blockFrame26);
        org.junit.Assert.assertTrue("'" + double31 + "' != '" + (-1.0d) + "'", double31 == (-1.0d));
        org.junit.Assert.assertNotNull(rectangleConstraint33);
        org.junit.Assert.assertNotNull(size2D34);
        org.junit.Assert.assertNotNull(obj36);
    }

    @Test
    public void test502() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test502");
        org.jfree.chart.block.EmptyBlock emptyBlock2 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets3 = emptyBlock2.getPadding();
        org.jfree.chart.block.BlockFrame blockFrame4 = emptyBlock2.getFrame();
        java.lang.String str5 = emptyBlock2.getID();
        java.awt.Graphics2D graphics2D6 = null;
        org.jfree.chart.util.Size2D size2D7 = emptyBlock2.arrange(graphics2D6);
        org.junit.Assert.assertNotNull(rectangleInsets3);
        org.junit.Assert.assertNotNull(blockFrame4);
        org.junit.Assert.assertNull(str5);
        org.junit.Assert.assertNotNull(size2D7);
    }

    @Test
    public void test503() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test503");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = null;
        java.awt.Graphics2D graphics2D2 = null;
        org.jfree.chart.block.RectangleConstraint rectangleConstraint5 = new org.jfree.chart.block.RectangleConstraint((double) (short) -1, (double) 100);
        org.jfree.chart.util.Size2D size2D6 = borderArrangement0.arrangeFF(blockContainer1, graphics2D2, rectangleConstraint5);
        borderArrangement0.clear();
        org.jfree.chart.block.EmptyBlock emptyBlock10 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D11 = emptyBlock10.getBounds();
        java.lang.Object obj12 = emptyBlock10.clone();
        emptyBlock10.setHeight(0.0d);
        org.jfree.data.Range range16 = null;
        org.jfree.data.Range range18 = org.jfree.data.Range.expandToInclude(range16, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint19 = new org.jfree.chart.block.RectangleConstraint(33.0d, range18);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint21 = rectangleConstraint19.toFixedWidth((double) 10);
        java.lang.String str22 = rectangleConstraint21.toString();
        org.jfree.chart.block.LengthConstraintType lengthConstraintType23 = rectangleConstraint21.getHeightConstraintType();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint25 = rectangleConstraint21.toFixedHeight((double) 0L);
        org.jfree.data.Range range26 = rectangleConstraint25.getHeightRange();
        // The following exception was thrown during execution in test generation
        try {
            borderArrangement0.add((org.jfree.chart.block.Block) emptyBlock10, (java.lang.Object) range26);
            org.junit.Assert.fail("Expected exception of type java.lang.ClassCastException; message: org.jfree.data.Range cannot be cast to org.jfree.chart.util.RectangleEdge");
        } catch (java.lang.ClassCastException e) {
            // Expected exception.
        }
        org.junit.Assert.assertNotNull(size2D6);
        org.junit.Assert.assertNotNull(rectangle2D11);
        org.junit.Assert.assertNotNull(obj12);
        org.junit.Assert.assertNotNull(range18);
        org.junit.Assert.assertNotNull(rectangleConstraint21);
        org.junit.Assert.assertEquals("'" + str22 + "' != '" + "RectangleConstraint[LengthConstraintType.FIXED: width=10.0, height=0.0]" + "'", str22, "RectangleConstraint[LengthConstraintType.FIXED: width=10.0, height=0.0]");
        org.junit.Assert.assertNotNull(lengthConstraintType23);
        org.junit.Assert.assertNotNull(rectangleConstraint25);
        org.junit.Assert.assertNotNull(range26);
    }

    @Test
    public void test504() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test504");
        org.jfree.data.Range range1 = null;
        org.jfree.data.Range range3 = org.jfree.data.Range.expandToInclude(range1, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint4 = new org.jfree.chart.block.RectangleConstraint(33.0d, range3);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint6 = rectangleConstraint4.toFixedWidth((double) 10);
        java.lang.String str7 = rectangleConstraint6.toString();
        org.jfree.chart.block.LengthConstraintType lengthConstraintType8 = rectangleConstraint6.getHeightConstraintType();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint10 = rectangleConstraint6.toFixedHeight((double) 0L);
        org.jfree.chart.block.EmptyBlock emptyBlock13 = new org.jfree.chart.block.EmptyBlock((-1.0d), 0.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets14 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double16 = rectangleInsets14.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock19 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D20 = emptyBlock19.getBounds();
        java.awt.geom.Rectangle2D rectangle2D23 = rectangleInsets14.createOutsetRectangle(rectangle2D20, true, false);
        java.awt.geom.Rectangle2D rectangle2D24 = emptyBlock13.trimBorder(rectangle2D20);
        java.awt.Graphics2D graphics2D25 = null;
        org.jfree.chart.util.Size2D size2D26 = emptyBlock13.arrange(graphics2D25);
        org.jfree.chart.block.BorderArrangement borderArrangement27 = new org.jfree.chart.block.BorderArrangement();
        boolean boolean28 = size2D26.equals((java.lang.Object) borderArrangement27);
        size2D26.setHeight(100.0d);
        size2D26.height = 0L;
        org.jfree.chart.util.Size2D size2D33 = rectangleConstraint10.calculateConstrainedSize(size2D26);
        org.jfree.data.Range range34 = null;
        org.jfree.data.Range range36 = org.jfree.data.Range.expandToInclude(range34, (double) (short) -1);
        java.lang.String str37 = range36.toString();
        org.jfree.data.Range range39 = org.jfree.data.Range.shift(range36, (double) 10L);
        org.jfree.data.Range range41 = org.jfree.data.Range.expandToInclude(range36, (double) (short) 100);
        java.lang.String str42 = range41.toString();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint43 = rectangleConstraint10.toRangeHeight(range41);
        org.junit.Assert.assertNotNull(range3);
        org.junit.Assert.assertNotNull(rectangleConstraint6);
        org.junit.Assert.assertEquals("'" + str7 + "' != '" + "RectangleConstraint[LengthConstraintType.FIXED: width=10.0, height=0.0]" + "'", str7, "RectangleConstraint[LengthConstraintType.FIXED: width=10.0, height=0.0]");
        org.junit.Assert.assertNotNull(lengthConstraintType8);
        org.junit.Assert.assertNotNull(rectangleConstraint10);
        org.junit.Assert.assertNotNull(rectangleInsets14);
        org.junit.Assert.assertTrue("'" + double16 + "' != '" + 0.0d + "'", double16 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D20);
        org.junit.Assert.assertNotNull(rectangle2D23);
        org.junit.Assert.assertNotNull(rectangle2D24);
        org.junit.Assert.assertNotNull(size2D26);
        org.junit.Assert.assertTrue("'" + boolean28 + "' != '" + false + "'", boolean28 == false);
        org.junit.Assert.assertNotNull(size2D33);
        org.junit.Assert.assertNotNull(range36);
        org.junit.Assert.assertEquals("'" + str37 + "' != '" + "Range[-1.0,-1.0]" + "'", str37, "Range[-1.0,-1.0]");
        org.junit.Assert.assertNotNull(range39);
        org.junit.Assert.assertNotNull(range41);
        org.junit.Assert.assertEquals("'" + str42 + "' != '" + "Range[-1.0,100.0]" + "'", str42, "Range[-1.0,100.0]");
        org.junit.Assert.assertNotNull(rectangleConstraint43);
    }

    @Test
    public void test505() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test505");
        org.jfree.data.Range range0 = null;
        org.jfree.data.Range range2 = org.jfree.data.Range.expandToInclude(range0, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint4 = new org.jfree.chart.block.RectangleConstraint(range2, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint5 = rectangleConstraint4.toUnconstrainedWidth();
        org.jfree.data.Range range6 = rectangleConstraint5.getWidthRange();
        double double8 = range6.constrain((double) (byte) 1);
        org.jfree.data.Range range9 = null;
        org.jfree.data.Range range11 = org.jfree.data.Range.expandToInclude(range9, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint13 = new org.jfree.chart.block.RectangleConstraint(range11, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint14 = rectangleConstraint13.toUnconstrainedWidth();
        org.jfree.data.Range range15 = rectangleConstraint14.getWidthRange();
        double double16 = range15.getCentralValue();
        org.jfree.data.Range range17 = org.jfree.data.Range.combine(range6, range15);
        org.jfree.chart.util.RectangleInsets rectangleInsets18 = new org.jfree.chart.util.RectangleInsets();
        double double19 = rectangleInsets18.getLeft();
        org.jfree.chart.block.BorderArrangement borderArrangement20 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer21 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement20);
        org.jfree.chart.block.BlockBorder blockBorder22 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer21.setFrame((org.jfree.chart.block.BlockFrame) blockBorder22);
        java.awt.Paint paint24 = blockBorder22.getPaint();
        org.jfree.chart.block.BlockBorder blockBorder25 = new org.jfree.chart.block.BlockBorder(rectangleInsets18, paint24);
        org.jfree.chart.util.RectangleInsets rectangleInsets26 = blockBorder25.getInsets();
        boolean boolean27 = range15.equals((java.lang.Object) rectangleInsets26);
        org.junit.Assert.assertNotNull(range2);
        org.junit.Assert.assertNotNull(rectangleConstraint5);
        org.junit.Assert.assertNotNull(range6);
        org.junit.Assert.assertTrue("'" + double8 + "' != '" + (-1.0d) + "'", double8 == (-1.0d));
        org.junit.Assert.assertNotNull(range11);
        org.junit.Assert.assertNotNull(rectangleConstraint14);
        org.junit.Assert.assertNotNull(range15);
        org.junit.Assert.assertTrue("'" + double16 + "' != '" + (-1.0d) + "'", double16 == (-1.0d));
        org.junit.Assert.assertNotNull(range17);
        org.junit.Assert.assertTrue("'" + double19 + "' != '" + 1.0d + "'", double19 == 1.0d);
        org.junit.Assert.assertNotNull(blockBorder22);
        org.junit.Assert.assertNotNull(paint24);
        org.junit.Assert.assertNotNull(rectangleInsets26);
        org.junit.Assert.assertTrue("'" + boolean27 + "' != '" + false + "'", boolean27 == false);
    }

    @Test
    public void test506() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test506");
        org.jfree.chart.util.RectangleInsets rectangleInsets0 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double2 = rectangleInsets0.calculateBottomInset((double) 0.0f);
        org.jfree.chart.util.UnitType unitType3 = rectangleInsets0.getUnitType();
        java.lang.String str4 = unitType3.toString();
        org.jfree.chart.block.BlockBorder blockBorder5 = org.jfree.chart.block.BlockBorder.NONE;
        boolean boolean6 = unitType3.equals((java.lang.Object) blockBorder5);
        java.lang.String str7 = unitType3.toString();
        org.jfree.data.Range range9 = null;
        org.jfree.chart.block.RectangleConstraint rectangleConstraint10 = new org.jfree.chart.block.RectangleConstraint(0.0d, range9);
        java.lang.String str11 = rectangleConstraint10.toString();
        org.jfree.data.Range range12 = null;
        org.jfree.data.Range range14 = org.jfree.data.Range.expandToInclude(range12, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint16 = new org.jfree.chart.block.RectangleConstraint(range14, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint17 = rectangleConstraint10.toRangeWidth(range14);
        boolean boolean18 = unitType3.equals((java.lang.Object) rectangleConstraint10);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint20 = rectangleConstraint10.toFixedHeight((double) 0);
        java.lang.String str21 = rectangleConstraint10.toString();
        org.junit.Assert.assertNotNull(rectangleInsets0);
        org.junit.Assert.assertTrue("'" + double2 + "' != '" + 0.0d + "'", double2 == 0.0d);
        org.junit.Assert.assertNotNull(unitType3);
        org.junit.Assert.assertEquals("'" + str4 + "' != '" + "UnitType.ABSOLUTE" + "'", str4, "UnitType.ABSOLUTE");
        org.junit.Assert.assertNotNull(blockBorder5);
        org.junit.Assert.assertTrue("'" + boolean6 + "' != '" + false + "'", boolean6 == false);
        org.junit.Assert.assertEquals("'" + str7 + "' != '" + "UnitType.ABSOLUTE" + "'", str7, "UnitType.ABSOLUTE");
        org.junit.Assert.assertEquals("'" + str11 + "' != '" + "RectangleConstraint[LengthConstraintType.FIXED: width=0.0, height=0.0]" + "'", str11, "RectangleConstraint[LengthConstraintType.FIXED: width=0.0, height=0.0]");
        org.junit.Assert.assertNotNull(range14);
        org.junit.Assert.assertNotNull(rectangleConstraint17);
        org.junit.Assert.assertTrue("'" + boolean18 + "' != '" + false + "'", boolean18 == false);
        org.junit.Assert.assertNotNull(rectangleConstraint20);
        org.junit.Assert.assertEquals("'" + str21 + "' != '" + "RectangleConstraint[LengthConstraintType.FIXED: width=0.0, height=0.0]" + "'", str21, "RectangleConstraint[LengthConstraintType.FIXED: width=0.0, height=0.0]");
    }

    @Test
    public void test507() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test507");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.BlockBorder blockBorder2 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder2);
        java.util.List list4 = blockContainer1.getBlocks();
        double double6 = blockContainer1.calculateTotalWidth(33.0d);
        org.jfree.chart.block.BorderArrangement borderArrangement7 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer8 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement7);
        java.awt.geom.Rectangle2D rectangle2D9 = blockContainer8.getBounds();
        double double10 = blockContainer8.getContentYOffset();
        org.jfree.chart.util.RectangleInsets rectangleInsets11 = new org.jfree.chart.util.RectangleInsets();
        double double12 = rectangleInsets11.getRight();
        double double14 = rectangleInsets11.calculateLeftInset((double) (byte) 0);
        org.jfree.chart.util.Size2D size2D15 = new org.jfree.chart.util.Size2D();
        size2D15.setHeight((double) (short) 0);
        double double18 = size2D15.getWidth();
        double double19 = size2D15.getWidth();
        org.jfree.chart.util.RectangleInsets rectangleInsets20 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double22 = rectangleInsets20.calculateTopOutset((double) 1.0f);
        double double24 = rectangleInsets20.extendHeight((double) (byte) -1);
        boolean boolean25 = size2D15.equals((java.lang.Object) rectangleInsets20);
        org.jfree.chart.block.EmptyBlock emptyBlock28 = new org.jfree.chart.block.EmptyBlock((-1.0d), 0.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets29 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double31 = rectangleInsets29.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock34 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D35 = emptyBlock34.getBounds();
        java.awt.geom.Rectangle2D rectangle2D38 = rectangleInsets29.createOutsetRectangle(rectangle2D35, true, false);
        java.awt.geom.Rectangle2D rectangle2D39 = emptyBlock28.trimBorder(rectangle2D35);
        java.awt.geom.Rectangle2D rectangle2D40 = emptyBlock28.getBounds();
        java.awt.geom.Rectangle2D rectangle2D43 = rectangleInsets20.createInsetRectangle(rectangle2D40, true, false);
        rectangleInsets11.trim(rectangle2D40);
        double double46 = rectangleInsets11.trimWidth(33.0d);
        blockContainer8.setMargin(rectangleInsets11);
        blockContainer1.add((org.jfree.chart.block.Block) blockContainer8);
        java.lang.Object obj49 = blockContainer1.clone();
        org.junit.Assert.assertNotNull(blockBorder2);
        org.junit.Assert.assertNotNull(list4);
        org.junit.Assert.assertTrue("'" + double6 + "' != '" + 33.0d + "'", double6 == 33.0d);
        org.junit.Assert.assertNotNull(rectangle2D9);
        org.junit.Assert.assertTrue("'" + double10 + "' != '" + 0.0d + "'", double10 == 0.0d);
        org.junit.Assert.assertTrue("'" + double12 + "' != '" + 1.0d + "'", double12 == 1.0d);
        org.junit.Assert.assertTrue("'" + double14 + "' != '" + 1.0d + "'", double14 == 1.0d);
        org.junit.Assert.assertTrue("'" + double18 + "' != '" + 0.0d + "'", double18 == 0.0d);
        org.junit.Assert.assertTrue("'" + double19 + "' != '" + 0.0d + "'", double19 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets20);
        org.junit.Assert.assertTrue("'" + double22 + "' != '" + 0.0d + "'", double22 == 0.0d);
        org.junit.Assert.assertTrue("'" + double24 + "' != '" + (-1.0d) + "'", double24 == (-1.0d));
        org.junit.Assert.assertTrue("'" + boolean25 + "' != '" + false + "'", boolean25 == false);
        org.junit.Assert.assertNotNull(rectangleInsets29);
        org.junit.Assert.assertTrue("'" + double31 + "' != '" + 0.0d + "'", double31 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D35);
        org.junit.Assert.assertNotNull(rectangle2D38);
        org.junit.Assert.assertNotNull(rectangle2D39);
        org.junit.Assert.assertNotNull(rectangle2D40);
        org.junit.Assert.assertNotNull(rectangle2D43);
        org.junit.Assert.assertTrue("'" + double46 + "' != '" + 31.0d + "'", double46 == 31.0d);
        org.junit.Assert.assertNotNull(obj49);
    }

    @Test
    public void test508() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test508");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.BlockBorder blockBorder2 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder2);
        java.util.List list4 = blockContainer1.getBlocks();
        blockContainer1.setPadding((double) 100.0f, 33.0d, (double) (-1L), (double) 1L);
        org.jfree.chart.util.RectangleInsets rectangleInsets10 = new org.jfree.chart.util.RectangleInsets();
        double double11 = rectangleInsets10.getLeft();
        double double13 = rectangleInsets10.extendWidth((double) (-1));
        org.jfree.chart.util.RectangleInsets rectangleInsets14 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        org.jfree.chart.util.RectangleInsets rectangleInsets15 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double17 = rectangleInsets15.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock20 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D21 = emptyBlock20.getBounds();
        java.awt.geom.Rectangle2D rectangle2D24 = rectangleInsets15.createOutsetRectangle(rectangle2D21, true, false);
        java.awt.geom.Rectangle2D rectangle2D25 = rectangleInsets14.createInsetRectangle(rectangle2D24);
        java.awt.geom.Rectangle2D rectangle2D26 = rectangleInsets10.createInsetRectangle(rectangle2D25);
        org.jfree.chart.util.RectangleEdge rectangleEdge27 = org.jfree.chart.util.RectangleEdge.RIGHT;
        org.jfree.chart.block.AbstractBlock abstractBlock28 = new org.jfree.chart.block.AbstractBlock();
        abstractBlock28.setMargin(0.0d, 0.0d, 0.0d, 0.0d);
        boolean boolean34 = rectangleEdge27.equals((java.lang.Object) 0.0d);
        org.jfree.data.Range range35 = null;
        org.jfree.data.Range range37 = org.jfree.data.Range.expandToInclude(range35, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint39 = new org.jfree.chart.block.RectangleConstraint(range37, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint40 = rectangleConstraint39.toUnconstrainedWidth();
        org.jfree.data.Range range41 = rectangleConstraint40.getWidthRange();
        double double43 = range41.constrain((double) (byte) 1);
        boolean boolean44 = rectangleEdge27.equals((java.lang.Object) (byte) 1);
        double double45 = org.jfree.chart.util.RectangleEdge.coordinate(rectangle2D26, rectangleEdge27);
        blockContainer1.setBounds(rectangle2D26);
        org.jfree.chart.block.Block block47 = null;
        java.lang.Object obj48 = null;
        blockContainer1.add(block47, obj48);
        java.awt.Graphics2D graphics2D50 = null;
        org.jfree.chart.block.BorderArrangement borderArrangement51 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer52 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement51);
        java.awt.geom.Rectangle2D rectangle2D53 = blockContainer52.getBounds();
        // The following exception was thrown during execution in test generation
        try {
            blockContainer1.draw(graphics2D50, rectangle2D53);
            org.junit.Assert.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
            // Expected exception.
        }
        org.junit.Assert.assertNotNull(blockBorder2);
        org.junit.Assert.assertNotNull(list4);
        org.junit.Assert.assertTrue("'" + double11 + "' != '" + 1.0d + "'", double11 == 1.0d);
        org.junit.Assert.assertTrue("'" + double13 + "' != '" + 1.0d + "'", double13 == 1.0d);
        org.junit.Assert.assertNotNull(rectangleInsets14);
        org.junit.Assert.assertNotNull(rectangleInsets15);
        org.junit.Assert.assertTrue("'" + double17 + "' != '" + 0.0d + "'", double17 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D21);
        org.junit.Assert.assertNotNull(rectangle2D24);
        org.junit.Assert.assertNotNull(rectangle2D25);
        org.junit.Assert.assertNotNull(rectangle2D26);
        org.junit.Assert.assertNotNull(rectangleEdge27);
        org.junit.Assert.assertTrue("'" + boolean34 + "' != '" + false + "'", boolean34 == false);
        org.junit.Assert.assertNotNull(range37);
        org.junit.Assert.assertNotNull(rectangleConstraint40);
        org.junit.Assert.assertNotNull(range41);
        org.junit.Assert.assertTrue("'" + double43 + "' != '" + (-1.0d) + "'", double43 == (-1.0d));
        org.junit.Assert.assertTrue("'" + boolean44 + "' != '" + false + "'", boolean44 == false);
        org.junit.Assert.assertTrue("'" + double45 + "' != '" + (-1.0d) + "'", double45 == (-1.0d));
        org.junit.Assert.assertNotNull(rectangle2D53);
    }

    @Test
    public void test509() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test509");
        org.jfree.chart.util.RectangleInsets rectangleInsets0 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double2 = rectangleInsets0.calculateTopOutset((double) 1.0f);
        double double3 = rectangleInsets0.getRight();
        double double5 = rectangleInsets0.calculateBottomInset((double) 0);
        org.jfree.chart.util.RectangleInsets rectangleInsets6 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double8 = rectangleInsets6.calculateTopOutset((double) 1.0f);
        double double9 = rectangleInsets6.getRight();
        org.jfree.chart.util.RectangleInsets rectangleInsets10 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double12 = rectangleInsets10.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock15 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D16 = emptyBlock15.getBounds();
        java.awt.geom.Rectangle2D rectangle2D19 = rectangleInsets10.createOutsetRectangle(rectangle2D16, true, false);
        org.jfree.chart.util.LengthAdjustmentType lengthAdjustmentType20 = null;
        org.jfree.chart.util.LengthAdjustmentType lengthAdjustmentType21 = null;
        java.awt.geom.Rectangle2D rectangle2D22 = rectangleInsets6.createAdjustedRectangle(rectangle2D16, lengthAdjustmentType20, lengthAdjustmentType21);
        java.awt.geom.Rectangle2D rectangle2D23 = rectangleInsets0.createInsetRectangle(rectangle2D22);
        double double25 = rectangleInsets0.calculateRightOutset(31.0d);
        org.jfree.data.Range range27 = null;
        org.jfree.data.Range range29 = org.jfree.data.Range.expandToInclude(range27, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint31 = new org.jfree.chart.block.RectangleConstraint(range29, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint32 = rectangleConstraint31.toUnconstrainedWidth();
        org.jfree.data.Range range33 = rectangleConstraint32.getWidthRange();
        double double35 = range33.constrain((double) (byte) 1);
        org.jfree.data.Range range36 = null;
        org.jfree.data.Range range38 = org.jfree.data.Range.expandToInclude(range36, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint40 = new org.jfree.chart.block.RectangleConstraint(range38, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint41 = rectangleConstraint40.toUnconstrainedWidth();
        org.jfree.data.Range range42 = rectangleConstraint41.getWidthRange();
        double double43 = range42.getCentralValue();
        org.jfree.data.Range range44 = org.jfree.data.Range.combine(range33, range42);
        double double45 = range42.getCentralValue();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint46 = new org.jfree.chart.block.RectangleConstraint((double) (byte) 100, range42);
        double double47 = rectangleConstraint46.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint49 = rectangleConstraint46.toFixedWidth(100.0d);
        org.jfree.chart.block.LengthConstraintType lengthConstraintType50 = rectangleConstraint46.getWidthConstraintType();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint52 = rectangleConstraint46.toFixedWidth(32.0d);
        boolean boolean53 = rectangleInsets0.equals((java.lang.Object) rectangleConstraint46);
        double double55 = rectangleInsets0.calculateRightInset(0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets0);
        org.junit.Assert.assertTrue("'" + double2 + "' != '" + 0.0d + "'", double2 == 0.0d);
        org.junit.Assert.assertTrue("'" + double3 + "' != '" + 0.0d + "'", double3 == 0.0d);
        org.junit.Assert.assertTrue("'" + double5 + "' != '" + 0.0d + "'", double5 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets6);
        org.junit.Assert.assertTrue("'" + double8 + "' != '" + 0.0d + "'", double8 == 0.0d);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + 0.0d + "'", double9 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets10);
        org.junit.Assert.assertTrue("'" + double12 + "' != '" + 0.0d + "'", double12 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D16);
        org.junit.Assert.assertNotNull(rectangle2D19);
        org.junit.Assert.assertNotNull(rectangle2D22);
        org.junit.Assert.assertNotNull(rectangle2D23);
        org.junit.Assert.assertTrue("'" + double25 + "' != '" + 0.0d + "'", double25 == 0.0d);
        org.junit.Assert.assertNotNull(range29);
        org.junit.Assert.assertNotNull(rectangleConstraint32);
        org.junit.Assert.assertNotNull(range33);
        org.junit.Assert.assertTrue("'" + double35 + "' != '" + (-1.0d) + "'", double35 == (-1.0d));
        org.junit.Assert.assertNotNull(range38);
        org.junit.Assert.assertNotNull(rectangleConstraint41);
        org.junit.Assert.assertNotNull(range42);
        org.junit.Assert.assertTrue("'" + double43 + "' != '" + (-1.0d) + "'", double43 == (-1.0d));
        org.junit.Assert.assertNotNull(range44);
        org.junit.Assert.assertTrue("'" + double45 + "' != '" + (-1.0d) + "'", double45 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double47 + "' != '" + 100.0d + "'", double47 == 100.0d);
        org.junit.Assert.assertNotNull(rectangleConstraint49);
        org.junit.Assert.assertNotNull(lengthConstraintType50);
        org.junit.Assert.assertNotNull(rectangleConstraint52);
        org.junit.Assert.assertTrue("'" + boolean53 + "' != '" + false + "'", boolean53 == false);
        org.junit.Assert.assertTrue("'" + double55 + "' != '" + 0.0d + "'", double55 == 0.0d);
    }

    @Test
    public void test510() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test510");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement0.clear();
        org.jfree.chart.block.EmptyBlock emptyBlock4 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D5 = emptyBlock4.getBounds();
        org.jfree.data.Range range6 = null;
        org.jfree.data.Range range8 = org.jfree.data.Range.expandToInclude(range6, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint10 = new org.jfree.chart.block.RectangleConstraint(range8, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint11 = rectangleConstraint10.toUnconstrainedWidth();
        double double12 = rectangleConstraint11.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint13 = emptyBlock4.toContentConstraint(rectangleConstraint11);
        double double15 = emptyBlock4.trimToContentHeight((double) (byte) 0);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint18 = new org.jfree.chart.block.RectangleConstraint((double) (short) -1, (double) 100);
        double double19 = rectangleConstraint18.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint21 = rectangleConstraint18.toFixedHeight((double) 100);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint22 = emptyBlock4.toContentConstraint(rectangleConstraint21);
        java.lang.Object obj23 = null;
        borderArrangement0.add((org.jfree.chart.block.Block) emptyBlock4, obj23);
        org.jfree.chart.block.BorderArrangement borderArrangement25 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement25.clear();
        borderArrangement25.clear();
        borderArrangement25.clear();
        org.jfree.chart.block.BlockContainer blockContainer29 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement25);
        double double30 = blockContainer29.getWidth();
        blockContainer29.clear();
        java.awt.Graphics2D graphics2D32 = null;
        org.jfree.chart.util.Size2D size2D33 = blockContainer29.arrange(graphics2D32);
        double double35 = blockContainer29.calculateTotalHeight((double) (short) 0);
        java.awt.Graphics2D graphics2D36 = null;
        org.jfree.data.Range range38 = null;
        org.jfree.data.Range range40 = org.jfree.data.Range.expandToInclude(range38, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint42 = new org.jfree.chart.block.RectangleConstraint(range40, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint43 = rectangleConstraint42.toUnconstrainedWidth();
        org.jfree.data.Range range44 = rectangleConstraint43.getWidthRange();
        double double46 = range44.constrain((double) (byte) 1);
        org.jfree.data.Range range47 = null;
        org.jfree.data.Range range49 = org.jfree.data.Range.expandToInclude(range47, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint51 = new org.jfree.chart.block.RectangleConstraint(range49, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint52 = rectangleConstraint51.toUnconstrainedWidth();
        org.jfree.data.Range range53 = rectangleConstraint52.getWidthRange();
        double double54 = range53.getCentralValue();
        org.jfree.data.Range range55 = org.jfree.data.Range.combine(range44, range53);
        double double56 = range53.getCentralValue();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint57 = new org.jfree.chart.block.RectangleConstraint((double) (byte) 100, range53);
        double double58 = rectangleConstraint57.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint60 = rectangleConstraint57.toFixedWidth(100.0d);
        org.jfree.chart.block.LengthConstraintType lengthConstraintType61 = rectangleConstraint57.getWidthConstraintType();
        org.jfree.chart.util.Size2D size2D62 = borderArrangement0.arrangeFR(blockContainer29, graphics2D36, rectangleConstraint57);
        org.jfree.chart.block.BlockContainer blockContainer63 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.junit.Assert.assertNotNull(rectangle2D5);
        org.junit.Assert.assertNotNull(range8);
        org.junit.Assert.assertNotNull(rectangleConstraint11);
        org.junit.Assert.assertTrue("'" + double12 + "' != '" + 0.0d + "'", double12 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleConstraint13);
        org.junit.Assert.assertTrue("'" + double15 + "' != '" + 0.0d + "'", double15 == 0.0d);
        org.junit.Assert.assertTrue("'" + double19 + "' != '" + (-1.0d) + "'", double19 == (-1.0d));
        org.junit.Assert.assertNotNull(rectangleConstraint21);
        org.junit.Assert.assertNotNull(rectangleConstraint22);
        org.junit.Assert.assertTrue("'" + double30 + "' != '" + 0.0d + "'", double30 == 0.0d);
        org.junit.Assert.assertNotNull(size2D33);
        org.junit.Assert.assertTrue("'" + double35 + "' != '" + 0.0d + "'", double35 == 0.0d);
        org.junit.Assert.assertNotNull(range40);
        org.junit.Assert.assertNotNull(rectangleConstraint43);
        org.junit.Assert.assertNotNull(range44);
        org.junit.Assert.assertTrue("'" + double46 + "' != '" + (-1.0d) + "'", double46 == (-1.0d));
        org.junit.Assert.assertNotNull(range49);
        org.junit.Assert.assertNotNull(rectangleConstraint52);
        org.junit.Assert.assertNotNull(range53);
        org.junit.Assert.assertTrue("'" + double54 + "' != '" + (-1.0d) + "'", double54 == (-1.0d));
        org.junit.Assert.assertNotNull(range55);
        org.junit.Assert.assertTrue("'" + double56 + "' != '" + (-1.0d) + "'", double56 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double58 + "' != '" + 100.0d + "'", double58 == 100.0d);
        org.junit.Assert.assertNotNull(rectangleConstraint60);
        org.junit.Assert.assertNotNull(lengthConstraintType61);
        org.junit.Assert.assertNotNull(size2D62);
    }

    @Test
    public void test511() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test511");
        org.jfree.data.Range range0 = null;
        org.jfree.data.Range range2 = org.jfree.data.Range.expandToInclude(range0, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint4 = new org.jfree.chart.block.RectangleConstraint(range2, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint6 = rectangleConstraint4.toFixedHeight((double) 1L);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint8 = rectangleConstraint6.toFixedHeight((double) (-1L));
        org.jfree.chart.block.RectangleConstraint rectangleConstraint9 = rectangleConstraint6.toUnconstrainedHeight();
        org.junit.Assert.assertNotNull(range2);
        org.junit.Assert.assertNotNull(rectangleConstraint6);
        org.junit.Assert.assertNotNull(rectangleConstraint8);
        org.junit.Assert.assertNotNull(rectangleConstraint9);
    }

    @Test
    public void test512() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test512");
        org.jfree.chart.util.Size2D size2D0 = new org.jfree.chart.util.Size2D();
        size2D0.setHeight((double) (short) 0);
        double double3 = size2D0.getWidth();
        double double4 = size2D0.getWidth();
        org.jfree.chart.util.RectangleInsets rectangleInsets5 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double7 = rectangleInsets5.calculateTopOutset((double) 1.0f);
        double double9 = rectangleInsets5.extendHeight((double) (byte) -1);
        boolean boolean10 = size2D0.equals((java.lang.Object) rectangleInsets5);
        double double12 = rectangleInsets5.extendHeight(0.0d);
        org.jfree.chart.block.BorderArrangement borderArrangement13 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement13.clear();
        borderArrangement13.clear();
        borderArrangement13.clear();
        org.jfree.chart.block.BlockContainer blockContainer17 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement13);
        double double18 = blockContainer17.getWidth();
        blockContainer17.clear();
        boolean boolean20 = rectangleInsets5.equals((java.lang.Object) blockContainer17);
        org.jfree.chart.block.EmptyBlock emptyBlock23 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D24 = emptyBlock23.getBounds();
        java.awt.geom.Rectangle2D rectangle2D25 = emptyBlock23.getBounds();
        org.jfree.chart.block.BorderArrangement borderArrangement26 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer27 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement26);
        java.util.List list28 = blockContainer27.getBlocks();
        blockContainer27.clear();
        org.jfree.chart.util.RectangleInsets rectangleInsets30 = blockContainer27.getPadding();
        org.jfree.chart.block.Arrangement arrangement31 = blockContainer27.getArrangement();
        // The following exception was thrown during execution in test generation
        try {
            blockContainer17.add((org.jfree.chart.block.Block) emptyBlock23, (java.lang.Object) blockContainer27);
            org.junit.Assert.fail("Expected exception of type java.lang.ClassCastException; message: org.jfree.chart.block.BlockContainer cannot be cast to org.jfree.chart.util.RectangleEdge");
        } catch (java.lang.ClassCastException e) {
            // Expected exception.
        }
        org.junit.Assert.assertTrue("'" + double3 + "' != '" + 0.0d + "'", double3 == 0.0d);
        org.junit.Assert.assertTrue("'" + double4 + "' != '" + 0.0d + "'", double4 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets5);
        org.junit.Assert.assertTrue("'" + double7 + "' != '" + 0.0d + "'", double7 == 0.0d);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + (-1.0d) + "'", double9 == (-1.0d));
        org.junit.Assert.assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
        org.junit.Assert.assertTrue("'" + double12 + "' != '" + 0.0d + "'", double12 == 0.0d);
        org.junit.Assert.assertTrue("'" + double18 + "' != '" + 0.0d + "'", double18 == 0.0d);
        org.junit.Assert.assertTrue("'" + boolean20 + "' != '" + false + "'", boolean20 == false);
        org.junit.Assert.assertNotNull(rectangle2D24);
        org.junit.Assert.assertNotNull(rectangle2D25);
        org.junit.Assert.assertNotNull(list28);
        org.junit.Assert.assertNotNull(rectangleInsets30);
        org.junit.Assert.assertNotNull(arrangement31);
    }

    @Test
    public void test513() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test513");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.BlockBorder blockBorder2 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder2);
        java.util.List list4 = blockContainer1.getBlocks();
        double double6 = blockContainer1.calculateTotalWidth(33.0d);
        boolean boolean7 = blockContainer1.isEmpty();
        java.awt.Graphics2D graphics2D8 = null;
        org.jfree.data.Range range9 = null;
        org.jfree.data.Range range11 = org.jfree.data.Range.expandToInclude(range9, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint13 = new org.jfree.chart.block.RectangleConstraint(range11, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint14 = rectangleConstraint13.toUnconstrainedWidth();
        org.jfree.data.Range range15 = rectangleConstraint14.getWidthRange();
        org.jfree.data.Range range18 = org.jfree.data.Range.shift(range15, (double) (byte) 100, false);
        org.jfree.data.Range range19 = null;
        org.jfree.data.Range range21 = org.jfree.data.Range.expandToInclude(range19, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint23 = new org.jfree.chart.block.RectangleConstraint(range21, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint24 = rectangleConstraint23.toUnconstrainedWidth();
        org.jfree.data.Range range25 = rectangleConstraint24.getWidthRange();
        double double27 = range25.constrain((double) (byte) 1);
        org.jfree.data.Range range28 = null;
        org.jfree.data.Range range30 = org.jfree.data.Range.expandToInclude(range28, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint32 = new org.jfree.chart.block.RectangleConstraint(range30, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint33 = rectangleConstraint32.toUnconstrainedWidth();
        org.jfree.data.Range range34 = rectangleConstraint33.getWidthRange();
        double double35 = range34.getCentralValue();
        org.jfree.data.Range range36 = org.jfree.data.Range.combine(range25, range34);
        boolean boolean38 = range36.contains((double) (byte) 1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint39 = new org.jfree.chart.block.RectangleConstraint(range18, range36);
        org.jfree.data.Range range40 = null;
        org.jfree.data.Range range42 = org.jfree.data.Range.expandToInclude(range40, (double) (short) -1);
        java.lang.String str43 = range42.toString();
        double double44 = range42.getLowerBound();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint45 = rectangleConstraint39.toRangeHeight(range42);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint46 = rectangleConstraint45.toUnconstrainedWidth();
        org.jfree.chart.util.Size2D size2D47 = blockContainer1.arrange(graphics2D8, rectangleConstraint45);
        org.junit.Assert.assertNotNull(blockBorder2);
        org.junit.Assert.assertNotNull(list4);
        org.junit.Assert.assertTrue("'" + double6 + "' != '" + 33.0d + "'", double6 == 33.0d);
        org.junit.Assert.assertTrue("'" + boolean7 + "' != '" + true + "'", boolean7 == true);
        org.junit.Assert.assertNotNull(range11);
        org.junit.Assert.assertNotNull(rectangleConstraint14);
        org.junit.Assert.assertNotNull(range15);
        org.junit.Assert.assertNotNull(range18);
        org.junit.Assert.assertNotNull(range21);
        org.junit.Assert.assertNotNull(rectangleConstraint24);
        org.junit.Assert.assertNotNull(range25);
        org.junit.Assert.assertTrue("'" + double27 + "' != '" + (-1.0d) + "'", double27 == (-1.0d));
        org.junit.Assert.assertNotNull(range30);
        org.junit.Assert.assertNotNull(rectangleConstraint33);
        org.junit.Assert.assertNotNull(range34);
        org.junit.Assert.assertTrue("'" + double35 + "' != '" + (-1.0d) + "'", double35 == (-1.0d));
        org.junit.Assert.assertNotNull(range36);
        org.junit.Assert.assertTrue("'" + boolean38 + "' != '" + false + "'", boolean38 == false);
        org.junit.Assert.assertNotNull(range42);
        org.junit.Assert.assertEquals("'" + str43 + "' != '" + "Range[-1.0,-1.0]" + "'", str43, "Range[-1.0,-1.0]");
        org.junit.Assert.assertTrue("'" + double44 + "' != '" + (-1.0d) + "'", double44 == (-1.0d));
        org.junit.Assert.assertNotNull(rectangleConstraint45);
        org.junit.Assert.assertNotNull(rectangleConstraint46);
        org.junit.Assert.assertNotNull(size2D47);
    }

    @Test
    public void test514() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test514");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        java.util.List list2 = blockContainer1.getBlocks();
        java.util.List list3 = blockContainer1.getBlocks();
        java.awt.Graphics2D graphics2D4 = null;
        org.jfree.chart.util.Size2D size2D5 = blockContainer1.arrange(graphics2D4);
        double double6 = size2D5.height;
        java.lang.String str7 = size2D5.toString();
        org.junit.Assert.assertNotNull(list2);
        org.junit.Assert.assertNotNull(list3);
        org.junit.Assert.assertNotNull(size2D5);
        org.junit.Assert.assertTrue("'" + double6 + "' != '" + 0.0d + "'", double6 == 0.0d);
        org.junit.Assert.assertEquals("'" + str7 + "' != '" + "Size2D[width=0.0, height=0.0]" + "'", str7, "Size2D[width=0.0, height=0.0]");
    }

    @Test
    public void test515() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test515");
        org.jfree.chart.block.EmptyBlock emptyBlock2 = new org.jfree.chart.block.EmptyBlock((-1.0d), 0.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets3 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double5 = rectangleInsets3.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock8 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D9 = emptyBlock8.getBounds();
        java.awt.geom.Rectangle2D rectangle2D12 = rectangleInsets3.createOutsetRectangle(rectangle2D9, true, false);
        java.awt.geom.Rectangle2D rectangle2D13 = emptyBlock2.trimBorder(rectangle2D9);
        java.awt.Graphics2D graphics2D14 = null;
        org.jfree.chart.util.Size2D size2D15 = emptyBlock2.arrange(graphics2D14);
        java.awt.geom.Rectangle2D rectangle2D16 = emptyBlock2.getBounds();
        org.jfree.chart.util.RectangleInsets rectangleInsets17 = emptyBlock2.getPadding();
        org.jfree.chart.util.RectangleInsets rectangleInsets18 = new org.jfree.chart.util.RectangleInsets();
        double double19 = rectangleInsets18.getLeft();
        double double21 = rectangleInsets18.calculateTopInset((double) (byte) 10);
        double double22 = rectangleInsets18.getRight();
        emptyBlock2.setPadding(rectangleInsets18);
        org.jfree.chart.util.RectangleInsets rectangleInsets24 = emptyBlock2.getMargin();
        double double26 = rectangleInsets24.calculateBottomInset((double) 10L);
        double double28 = rectangleInsets24.calculateLeftOutset(50.0d);
        org.junit.Assert.assertNotNull(rectangleInsets3);
        org.junit.Assert.assertTrue("'" + double5 + "' != '" + 0.0d + "'", double5 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D9);
        org.junit.Assert.assertNotNull(rectangle2D12);
        org.junit.Assert.assertNotNull(rectangle2D13);
        org.junit.Assert.assertNotNull(size2D15);
        org.junit.Assert.assertNotNull(rectangle2D16);
        org.junit.Assert.assertNotNull(rectangleInsets17);
        org.junit.Assert.assertTrue("'" + double19 + "' != '" + 1.0d + "'", double19 == 1.0d);
        org.junit.Assert.assertTrue("'" + double21 + "' != '" + 1.0d + "'", double21 == 1.0d);
        org.junit.Assert.assertTrue("'" + double22 + "' != '" + 1.0d + "'", double22 == 1.0d);
        org.junit.Assert.assertNotNull(rectangleInsets24);
        org.junit.Assert.assertTrue("'" + double26 + "' != '" + 0.0d + "'", double26 == 0.0d);
        org.junit.Assert.assertTrue("'" + double28 + "' != '" + 0.0d + "'", double28 == 0.0d);
    }

    @Test
    public void test516() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test516");
        org.jfree.chart.block.RectangleConstraint rectangleConstraint2 = new org.jfree.chart.block.RectangleConstraint((double) (short) -1, (double) 100);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint3 = rectangleConstraint2.toUnconstrainedWidth();
        org.jfree.chart.block.LengthConstraintType lengthConstraintType4 = rectangleConstraint3.getHeightConstraintType();
        org.jfree.chart.block.BorderArrangement borderArrangement5 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer6 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement5);
        org.jfree.chart.block.BlockBorder blockBorder7 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer6.setFrame((org.jfree.chart.block.BlockFrame) blockBorder7);
        java.util.List list9 = blockContainer6.getBlocks();
        org.jfree.chart.util.RectangleInsets rectangleInsets10 = blockContainer6.getPadding();
        boolean boolean11 = lengthConstraintType4.equals((java.lang.Object) blockContainer6);
        java.lang.Object obj12 = blockContainer6.clone();
        org.junit.Assert.assertNotNull(rectangleConstraint3);
        org.junit.Assert.assertNotNull(lengthConstraintType4);
        org.junit.Assert.assertNotNull(blockBorder7);
        org.junit.Assert.assertNotNull(list9);
        org.junit.Assert.assertNotNull(rectangleInsets10);
        org.junit.Assert.assertTrue("'" + boolean11 + "' != '" + false + "'", boolean11 == false);
        org.junit.Assert.assertNotNull(obj12);
    }

    @Test
    public void test517() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test517");
        org.jfree.chart.block.EmptyBlock emptyBlock2 = new org.jfree.chart.block.EmptyBlock((-1.0d), 0.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets3 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double5 = rectangleInsets3.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock8 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D9 = emptyBlock8.getBounds();
        java.awt.geom.Rectangle2D rectangle2D12 = rectangleInsets3.createOutsetRectangle(rectangle2D9, true, false);
        java.awt.geom.Rectangle2D rectangle2D13 = emptyBlock2.trimBorder(rectangle2D9);
        java.awt.Graphics2D graphics2D14 = null;
        org.jfree.chart.util.Size2D size2D15 = emptyBlock2.arrange(graphics2D14);
        double double16 = size2D15.width;
        java.lang.Object obj17 = size2D15.clone();
        size2D15.height = (byte) 10;
        org.junit.Assert.assertNotNull(rectangleInsets3);
        org.junit.Assert.assertTrue("'" + double5 + "' != '" + 0.0d + "'", double5 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D9);
        org.junit.Assert.assertNotNull(rectangle2D12);
        org.junit.Assert.assertNotNull(rectangle2D13);
        org.junit.Assert.assertNotNull(size2D15);
        org.junit.Assert.assertTrue("'" + double16 + "' != '" + (-1.0d) + "'", double16 == (-1.0d));
        org.junit.Assert.assertNotNull(obj17);
        org.junit.Assert.assertEquals(obj17.toString(), "Size2D[width=-1.0, height=0.0]");
        org.junit.Assert.assertEquals(java.lang.String.valueOf(obj17), "Size2D[width=-1.0, height=0.0]");
        org.junit.Assert.assertEquals(java.util.Objects.toString(obj17), "Size2D[width=-1.0, height=0.0]");
    }

    @Test
    public void test518() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test518");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement0.clear();
        borderArrangement0.clear();
        org.jfree.chart.block.BorderArrangement borderArrangement3 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer4 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement3);
        org.jfree.chart.block.BlockBorder blockBorder5 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer4.setFrame((org.jfree.chart.block.BlockFrame) blockBorder5);
        java.util.List list7 = blockContainer4.getBlocks();
        blockContainer4.setPadding((double) 100.0f, 33.0d, (double) (-1L), (double) 1L);
        org.jfree.chart.block.BorderArrangement borderArrangement13 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer14 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement13);
        org.jfree.chart.block.BlockBorder blockBorder15 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer14.setFrame((org.jfree.chart.block.BlockFrame) blockBorder15);
        java.util.List list17 = blockContainer14.getBlocks();
        org.jfree.chart.block.EmptyBlock emptyBlock20 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.lang.Object obj21 = emptyBlock20.clone();
        emptyBlock20.setMargin((double) (-1.0f), (double) (byte) 10, (double) 'a', (double) 0L);
        org.jfree.chart.util.RectangleInsets rectangleInsets27 = emptyBlock20.getPadding();
        boolean boolean28 = blockContainer14.equals((java.lang.Object) emptyBlock20);
        org.jfree.chart.block.BlockFrame blockFrame29 = emptyBlock20.getFrame();
        java.awt.Graphics2D graphics2D30 = null;
        org.jfree.chart.block.RectangleConstraint rectangleConstraint33 = new org.jfree.chart.block.RectangleConstraint((double) (short) -1, (double) 100);
        double double34 = rectangleConstraint33.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint36 = rectangleConstraint33.toFixedHeight((double) 0);
        org.jfree.chart.util.Size2D size2D37 = emptyBlock20.arrange(graphics2D30, rectangleConstraint33);
        blockContainer4.add((org.jfree.chart.block.Block) emptyBlock20);
        double double40 = emptyBlock20.calculateTotalWidth((double) (byte) -1);
        emptyBlock20.setID("");
        java.lang.Object obj43 = emptyBlock20.clone();
        org.jfree.chart.block.BorderArrangement borderArrangement44 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement44.clear();
        borderArrangement44.clear();
        borderArrangement44.clear();
        org.jfree.chart.block.BlockContainer blockContainer48 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement44);
        double double49 = blockContainer48.getWidth();
        java.util.List list50 = blockContainer48.getBlocks();
        org.jfree.chart.block.Arrangement arrangement51 = blockContainer48.getArrangement();
        org.jfree.chart.util.RectangleInsets rectangleInsets52 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double54 = rectangleInsets52.calculateBottomInset((double) 0.0f);
        org.jfree.chart.util.RectangleInsets rectangleInsets55 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double57 = rectangleInsets55.calculateBottomInset((double) 0.0f);
        double double58 = rectangleInsets55.getBottom();
        double double59 = rectangleInsets55.getTop();
        org.jfree.chart.util.RectangleInsets rectangleInsets60 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double62 = rectangleInsets60.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock65 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D66 = emptyBlock65.getBounds();
        java.awt.geom.Rectangle2D rectangle2D69 = rectangleInsets60.createOutsetRectangle(rectangle2D66, true, false);
        java.awt.geom.Rectangle2D rectangle2D70 = rectangleInsets55.createInsetRectangle(rectangle2D66);
        java.awt.geom.Rectangle2D rectangle2D71 = rectangleInsets52.createInsetRectangle(rectangle2D70);
        java.awt.geom.Rectangle2D rectangle2D72 = blockContainer48.trimMargin(rectangle2D71);
        // The following exception was thrown during execution in test generation
        try {
            borderArrangement0.add((org.jfree.chart.block.Block) emptyBlock20, (java.lang.Object) blockContainer48);
            org.junit.Assert.fail("Expected exception of type java.lang.ClassCastException; message: org.jfree.chart.block.BlockContainer cannot be cast to org.jfree.chart.util.RectangleEdge");
        } catch (java.lang.ClassCastException e) {
            // Expected exception.
        }
        org.junit.Assert.assertNotNull(blockBorder5);
        org.junit.Assert.assertNotNull(list7);
        org.junit.Assert.assertNotNull(blockBorder15);
        org.junit.Assert.assertNotNull(list17);
        org.junit.Assert.assertNotNull(obj21);
        org.junit.Assert.assertNotNull(rectangleInsets27);
        org.junit.Assert.assertTrue("'" + boolean28 + "' != '" + false + "'", boolean28 == false);
        org.junit.Assert.assertNotNull(blockFrame29);
        org.junit.Assert.assertTrue("'" + double34 + "' != '" + (-1.0d) + "'", double34 == (-1.0d));
        org.junit.Assert.assertNotNull(rectangleConstraint36);
        org.junit.Assert.assertNotNull(size2D37);
        org.junit.Assert.assertTrue("'" + double40 + "' != '" + 9.0d + "'", double40 == 9.0d);
        org.junit.Assert.assertNotNull(obj43);
        org.junit.Assert.assertTrue("'" + double49 + "' != '" + 0.0d + "'", double49 == 0.0d);
        org.junit.Assert.assertNotNull(list50);
        org.junit.Assert.assertNotNull(arrangement51);
        org.junit.Assert.assertNotNull(rectangleInsets52);
        org.junit.Assert.assertTrue("'" + double54 + "' != '" + 0.0d + "'", double54 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets55);
        org.junit.Assert.assertTrue("'" + double57 + "' != '" + 0.0d + "'", double57 == 0.0d);
        org.junit.Assert.assertTrue("'" + double58 + "' != '" + 0.0d + "'", double58 == 0.0d);
        org.junit.Assert.assertTrue("'" + double59 + "' != '" + 0.0d + "'", double59 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets60);
        org.junit.Assert.assertTrue("'" + double62 + "' != '" + 0.0d + "'", double62 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D66);
        org.junit.Assert.assertNotNull(rectangle2D69);
        org.junit.Assert.assertNotNull(rectangle2D70);
        org.junit.Assert.assertNotNull(rectangle2D71);
        org.junit.Assert.assertNotNull(rectangle2D72);
    }

    @Test
    public void test519() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test519");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement0.clear();
        borderArrangement0.clear();
        borderArrangement0.clear();
        org.jfree.chart.block.BlockContainer blockContainer4 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.BlockContainer blockContainer5 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        java.awt.Graphics2D graphics2D6 = null;
        org.jfree.chart.block.EmptyBlock emptyBlock9 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.lang.Object obj10 = emptyBlock9.clone();
        emptyBlock9.setMargin((double) (-1.0f), (double) (byte) 10, (double) 'a', (double) 0L);
        org.jfree.chart.util.RectangleInsets rectangleInsets16 = emptyBlock9.getPadding();
        java.awt.geom.Rectangle2D rectangle2D17 = emptyBlock9.getBounds();
        org.jfree.chart.util.RectangleEdge rectangleEdge18 = org.jfree.chart.util.RectangleEdge.LEFT;
        java.lang.String str19 = rectangleEdge18.toString();
        org.jfree.chart.util.RectangleEdge rectangleEdge20 = org.jfree.chart.util.RectangleEdge.opposite(rectangleEdge18);
        double double21 = org.jfree.chart.util.RectangleEdge.coordinate(rectangle2D17, rectangleEdge18);
        // The following exception was thrown during execution in test generation
        try {
            blockContainer5.draw(graphics2D6, rectangle2D17);
            org.junit.Assert.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
            // Expected exception.
        }
        org.junit.Assert.assertNotNull(obj10);
        org.junit.Assert.assertNotNull(rectangleInsets16);
        org.junit.Assert.assertNotNull(rectangle2D17);
        org.junit.Assert.assertNotNull(rectangleEdge18);
        org.junit.Assert.assertEquals("'" + str19 + "' != '" + "RectangleEdge.LEFT" + "'", str19, "RectangleEdge.LEFT");
        org.junit.Assert.assertNotNull(rectangleEdge20);
        org.junit.Assert.assertTrue("'" + double21 + "' != '" + 0.0d + "'", double21 == 0.0d);
    }

    @Test
    public void test520() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test520");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement0.clear();
        borderArrangement0.clear();
        borderArrangement0.clear();
        org.jfree.chart.block.BlockContainer blockContainer4 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        double double5 = blockContainer4.getWidth();
        java.lang.Object obj6 = blockContainer4.clone();
        blockContainer4.setID("Size2D[width=0.0, height=0.0]");
        double double10 = blockContainer4.calculateTotalHeight(0.0d);
        org.jfree.chart.block.Arrangement arrangement11 = blockContainer4.getArrangement();
        java.awt.Graphics2D graphics2D12 = null;
        org.jfree.chart.util.RectangleInsets rectangleInsets13 = new org.jfree.chart.util.RectangleInsets();
        double double14 = rectangleInsets13.getLeft();
        double double16 = rectangleInsets13.calculateTopInset((double) (byte) 10);
        double double18 = rectangleInsets13.calculateBottomOutset(9.0d);
        org.jfree.data.Range range19 = null;
        org.jfree.data.Range range21 = org.jfree.data.Range.expandToInclude(range19, (double) (short) -1);
        double double23 = range21.constrain((double) (byte) -1);
        org.jfree.data.Range range25 = org.jfree.data.Range.expandToInclude(range21, (double) 100L);
        org.jfree.chart.block.BorderArrangement borderArrangement26 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement26.clear();
        borderArrangement26.clear();
        borderArrangement26.clear();
        org.jfree.chart.block.BlockContainer blockContainer30 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement26);
        double double31 = blockContainer30.getWidth();
        java.util.List list32 = blockContainer30.getBlocks();
        org.jfree.chart.block.Arrangement arrangement33 = blockContainer30.getArrangement();
        org.jfree.chart.util.RectangleInsets rectangleInsets34 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double36 = rectangleInsets34.calculateBottomInset((double) 0.0f);
        org.jfree.chart.util.RectangleInsets rectangleInsets37 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double39 = rectangleInsets37.calculateBottomInset((double) 0.0f);
        double double40 = rectangleInsets37.getBottom();
        double double41 = rectangleInsets37.getTop();
        org.jfree.chart.util.RectangleInsets rectangleInsets42 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double44 = rectangleInsets42.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock47 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D48 = emptyBlock47.getBounds();
        java.awt.geom.Rectangle2D rectangle2D51 = rectangleInsets42.createOutsetRectangle(rectangle2D48, true, false);
        java.awt.geom.Rectangle2D rectangle2D52 = rectangleInsets37.createInsetRectangle(rectangle2D48);
        java.awt.geom.Rectangle2D rectangle2D53 = rectangleInsets34.createInsetRectangle(rectangle2D52);
        java.awt.geom.Rectangle2D rectangle2D54 = blockContainer30.trimMargin(rectangle2D53);
        boolean boolean55 = range21.equals((java.lang.Object) rectangle2D53);
        rectangleInsets13.trim(rectangle2D53);
        // The following exception was thrown during execution in test generation
        try {
            blockContainer4.draw(graphics2D12, rectangle2D53);
            org.junit.Assert.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
            // Expected exception.
        }
        org.junit.Assert.assertTrue("'" + double5 + "' != '" + 0.0d + "'", double5 == 0.0d);
        org.junit.Assert.assertNotNull(obj6);
        org.junit.Assert.assertTrue("'" + double10 + "' != '" + 0.0d + "'", double10 == 0.0d);
        org.junit.Assert.assertNotNull(arrangement11);
        org.junit.Assert.assertTrue("'" + double14 + "' != '" + 1.0d + "'", double14 == 1.0d);
        org.junit.Assert.assertTrue("'" + double16 + "' != '" + 1.0d + "'", double16 == 1.0d);
        org.junit.Assert.assertTrue("'" + double18 + "' != '" + 1.0d + "'", double18 == 1.0d);
        org.junit.Assert.assertNotNull(range21);
        org.junit.Assert.assertTrue("'" + double23 + "' != '" + (-1.0d) + "'", double23 == (-1.0d));
        org.junit.Assert.assertNotNull(range25);
        org.junit.Assert.assertTrue("'" + double31 + "' != '" + 0.0d + "'", double31 == 0.0d);
        org.junit.Assert.assertNotNull(list32);
        org.junit.Assert.assertNotNull(arrangement33);
        org.junit.Assert.assertNotNull(rectangleInsets34);
        org.junit.Assert.assertTrue("'" + double36 + "' != '" + 0.0d + "'", double36 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets37);
        org.junit.Assert.assertTrue("'" + double39 + "' != '" + 0.0d + "'", double39 == 0.0d);
        org.junit.Assert.assertTrue("'" + double40 + "' != '" + 0.0d + "'", double40 == 0.0d);
        org.junit.Assert.assertTrue("'" + double41 + "' != '" + 0.0d + "'", double41 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets42);
        org.junit.Assert.assertTrue("'" + double44 + "' != '" + 0.0d + "'", double44 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D48);
        org.junit.Assert.assertNotNull(rectangle2D51);
        org.junit.Assert.assertNotNull(rectangle2D52);
        org.junit.Assert.assertNotNull(rectangle2D53);
        org.junit.Assert.assertNotNull(rectangle2D54);
        org.junit.Assert.assertTrue("'" + boolean55 + "' != '" + false + "'", boolean55 == false);
    }

    @Test
    public void test521() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test521");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        double double2 = blockContainer1.getContentXOffset();
        java.awt.Graphics2D graphics2D3 = null;
        org.jfree.chart.util.Size2D size2D4 = blockContainer1.arrange(graphics2D3);
        double double5 = size2D4.getWidth();
        org.junit.Assert.assertTrue("'" + double2 + "' != '" + 0.0d + "'", double2 == 0.0d);
        org.junit.Assert.assertNotNull(size2D4);
        org.junit.Assert.assertTrue("'" + double5 + "' != '" + 0.0d + "'", double5 == 0.0d);
    }

    @Test
    public void test522() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test522");
        org.jfree.data.Range range1 = null;
        org.jfree.data.Range range3 = org.jfree.data.Range.expandToInclude(range1, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint5 = new org.jfree.chart.block.RectangleConstraint(range3, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint6 = rectangleConstraint5.toUnconstrainedWidth();
        org.jfree.data.Range range7 = rectangleConstraint6.getWidthRange();
        double double9 = range7.constrain((double) (byte) 1);
        org.jfree.data.Range range10 = null;
        org.jfree.data.Range range12 = org.jfree.data.Range.expandToInclude(range10, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint14 = new org.jfree.chart.block.RectangleConstraint(range12, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint15 = rectangleConstraint14.toUnconstrainedWidth();
        org.jfree.data.Range range16 = rectangleConstraint15.getWidthRange();
        double double17 = range16.getCentralValue();
        org.jfree.data.Range range18 = org.jfree.data.Range.combine(range7, range16);
        double double19 = range16.getCentralValue();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint20 = new org.jfree.chart.block.RectangleConstraint((double) (byte) 100, range16);
        org.jfree.data.Range range23 = org.jfree.data.Range.expand(range16, 0.0d, (double) 1L);
        double double25 = range23.constrain((double) (short) 10);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint27 = new org.jfree.chart.block.RectangleConstraint(range23, (double) 1L);
        org.jfree.chart.block.LengthConstraintType lengthConstraintType28 = rectangleConstraint27.getHeightConstraintType();
        org.jfree.chart.util.RectangleInsets rectangleInsets29 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double31 = rectangleInsets29.calculateBottomInset((double) 0.0f);
        org.jfree.chart.util.UnitType unitType32 = rectangleInsets29.getUnitType();
        java.lang.String str33 = unitType32.toString();
        org.jfree.chart.block.BlockBorder blockBorder34 = org.jfree.chart.block.BlockBorder.NONE;
        boolean boolean35 = unitType32.equals((java.lang.Object) blockBorder34);
        org.jfree.chart.util.RectangleInsets rectangleInsets36 = blockBorder34.getInsets();
        java.awt.Paint paint37 = blockBorder34.getPaint();
        org.jfree.chart.block.BlockBorder blockBorder38 = new org.jfree.chart.block.BlockBorder(paint37);
        boolean boolean39 = lengthConstraintType28.equals((java.lang.Object) paint37);
        org.junit.Assert.assertNotNull(range3);
        org.junit.Assert.assertNotNull(rectangleConstraint6);
        org.junit.Assert.assertNotNull(range7);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + (-1.0d) + "'", double9 == (-1.0d));
        org.junit.Assert.assertNotNull(range12);
        org.junit.Assert.assertNotNull(rectangleConstraint15);
        org.junit.Assert.assertNotNull(range16);
        org.junit.Assert.assertTrue("'" + double17 + "' != '" + (-1.0d) + "'", double17 == (-1.0d));
        org.junit.Assert.assertNotNull(range18);
        org.junit.Assert.assertTrue("'" + double19 + "' != '" + (-1.0d) + "'", double19 == (-1.0d));
        org.junit.Assert.assertNotNull(range23);
        org.junit.Assert.assertTrue("'" + double25 + "' != '" + (-1.0d) + "'", double25 == (-1.0d));
        org.junit.Assert.assertNotNull(lengthConstraintType28);
        org.junit.Assert.assertNotNull(rectangleInsets29);
        org.junit.Assert.assertTrue("'" + double31 + "' != '" + 0.0d + "'", double31 == 0.0d);
        org.junit.Assert.assertNotNull(unitType32);
        org.junit.Assert.assertEquals("'" + str33 + "' != '" + "UnitType.ABSOLUTE" + "'", str33, "UnitType.ABSOLUTE");
        org.junit.Assert.assertNotNull(blockBorder34);
        org.junit.Assert.assertTrue("'" + boolean35 + "' != '" + false + "'", boolean35 == false);
        org.junit.Assert.assertNotNull(rectangleInsets36);
        org.junit.Assert.assertNotNull(paint37);
        org.junit.Assert.assertTrue("'" + boolean39 + "' != '" + false + "'", boolean39 == false);
    }

    @Test
    public void test523() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test523");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        java.util.List list2 = blockContainer1.getBlocks();
        blockContainer1.setMargin((double) ' ', (double) 100.0f, 0.0d, (double) 'a');
        org.jfree.chart.util.RectangleInsets rectangleInsets8 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double10 = rectangleInsets8.calculateBottomInset((double) 0.0f);
        org.jfree.chart.util.UnitType unitType11 = rectangleInsets8.getUnitType();
        java.lang.String str12 = unitType11.toString();
        org.jfree.chart.block.BlockBorder blockBorder13 = org.jfree.chart.block.BlockBorder.NONE;
        boolean boolean14 = unitType11.equals((java.lang.Object) blockBorder13);
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder13);
        java.lang.Object obj16 = blockContainer1.clone();
        org.junit.Assert.assertNotNull(list2);
        org.junit.Assert.assertNotNull(rectangleInsets8);
        org.junit.Assert.assertTrue("'" + double10 + "' != '" + 0.0d + "'", double10 == 0.0d);
        org.junit.Assert.assertNotNull(unitType11);
        org.junit.Assert.assertEquals("'" + str12 + "' != '" + "UnitType.ABSOLUTE" + "'", str12, "UnitType.ABSOLUTE");
        org.junit.Assert.assertNotNull(blockBorder13);
        org.junit.Assert.assertTrue("'" + boolean14 + "' != '" + false + "'", boolean14 == false);
        org.junit.Assert.assertNotNull(obj16);
    }

    @Test
    public void test524() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test524");
        org.jfree.data.Range range1 = null;
        org.jfree.data.Range range3 = org.jfree.data.Range.expandToInclude(range1, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint5 = new org.jfree.chart.block.RectangleConstraint(range3, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint6 = rectangleConstraint5.toUnconstrainedWidth();
        org.jfree.data.Range range7 = rectangleConstraint6.getWidthRange();
        double double9 = range7.constrain((double) (byte) 1);
        org.jfree.data.Range range10 = null;
        org.jfree.data.Range range12 = org.jfree.data.Range.expandToInclude(range10, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint14 = new org.jfree.chart.block.RectangleConstraint(range12, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint15 = rectangleConstraint14.toUnconstrainedWidth();
        org.jfree.data.Range range16 = rectangleConstraint15.getWidthRange();
        double double17 = range16.getCentralValue();
        org.jfree.data.Range range18 = org.jfree.data.Range.combine(range7, range16);
        boolean boolean20 = range16.contains((double) (short) 1);
        double double21 = range16.getCentralValue();
        double double23 = range16.constrain((double) (byte) 100);
        org.jfree.data.Range range25 = org.jfree.data.Range.shift(range16, (double) (byte) -1);
        java.lang.String str26 = range25.toString();
        org.jfree.data.Range range28 = org.jfree.data.Range.shift(range25, (double) (short) 0);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint29 = new org.jfree.chart.block.RectangleConstraint((-132.0d), range28);
        org.junit.Assert.assertNotNull(range3);
        org.junit.Assert.assertNotNull(rectangleConstraint6);
        org.junit.Assert.assertNotNull(range7);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + (-1.0d) + "'", double9 == (-1.0d));
        org.junit.Assert.assertNotNull(range12);
        org.junit.Assert.assertNotNull(rectangleConstraint15);
        org.junit.Assert.assertNotNull(range16);
        org.junit.Assert.assertTrue("'" + double17 + "' != '" + (-1.0d) + "'", double17 == (-1.0d));
        org.junit.Assert.assertNotNull(range18);
        org.junit.Assert.assertTrue("'" + boolean20 + "' != '" + false + "'", boolean20 == false);
        org.junit.Assert.assertTrue("'" + double21 + "' != '" + (-1.0d) + "'", double21 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double23 + "' != '" + (-1.0d) + "'", double23 == (-1.0d));
        org.junit.Assert.assertNotNull(range25);
        org.junit.Assert.assertEquals("'" + str26 + "' != '" + "Range[-2.0,-2.0]" + "'", str26, "Range[-2.0,-2.0]");
        org.junit.Assert.assertNotNull(range28);
    }

    @Test
    public void test525() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test525");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement0.clear();
        borderArrangement0.clear();
        borderArrangement0.clear();
        org.jfree.chart.block.BlockContainer blockContainer4 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        double double5 = blockContainer4.getWidth();
        blockContainer4.clear();
        double double8 = blockContainer4.calculateTotalHeight(90.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets9 = blockContainer4.getPadding();
        double double10 = blockContainer4.getContentXOffset();
        boolean boolean11 = blockContainer4.isEmpty();
        org.junit.Assert.assertTrue("'" + double5 + "' != '" + 0.0d + "'", double5 == 0.0d);
        org.junit.Assert.assertTrue("'" + double8 + "' != '" + 90.0d + "'", double8 == 90.0d);
        org.junit.Assert.assertNotNull(rectangleInsets9);
        org.junit.Assert.assertTrue("'" + double10 + "' != '" + 0.0d + "'", double10 == 0.0d);
        org.junit.Assert.assertTrue("'" + boolean11 + "' != '" + true + "'", boolean11 == true);
    }

    @Test
    public void test526() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test526");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.BlockBorder blockBorder2 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder2);
        java.util.List list4 = blockContainer1.getBlocks();
        org.jfree.chart.block.EmptyBlock emptyBlock7 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.lang.Object obj8 = emptyBlock7.clone();
        emptyBlock7.setMargin((double) (-1.0f), (double) (byte) 10, (double) 'a', (double) 0L);
        org.jfree.chart.util.RectangleInsets rectangleInsets14 = emptyBlock7.getPadding();
        boolean boolean15 = blockContainer1.equals((java.lang.Object) emptyBlock7);
        java.awt.Graphics2D graphics2D16 = null;
        org.jfree.chart.block.BorderArrangement borderArrangement17 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement17.clear();
        borderArrangement17.clear();
        borderArrangement17.clear();
        org.jfree.chart.block.BlockContainer blockContainer21 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement17);
        double double22 = blockContainer21.getWidth();
        java.lang.Object obj23 = blockContainer21.clone();
        blockContainer21.setID("Size2D[width=0.0, height=0.0]");
        java.awt.geom.Rectangle2D rectangle2D26 = blockContainer21.getBounds();
        // The following exception was thrown during execution in test generation
        try {
            emptyBlock7.drawBorder(graphics2D16, rectangle2D26);
            org.junit.Assert.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
            // Expected exception.
        }
        org.junit.Assert.assertNotNull(blockBorder2);
        org.junit.Assert.assertNotNull(list4);
        org.junit.Assert.assertNotNull(obj8);
        org.junit.Assert.assertNotNull(rectangleInsets14);
        org.junit.Assert.assertTrue("'" + boolean15 + "' != '" + false + "'", boolean15 == false);
        org.junit.Assert.assertTrue("'" + double22 + "' != '" + 0.0d + "'", double22 == 0.0d);
        org.junit.Assert.assertNotNull(obj23);
        org.junit.Assert.assertNotNull(rectangle2D26);
    }

    @Test
    public void test527() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test527");
        org.jfree.data.Range range1 = null;
        org.jfree.data.Range range3 = org.jfree.data.Range.expandToInclude(range1, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint5 = new org.jfree.chart.block.RectangleConstraint(range3, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint6 = rectangleConstraint5.toUnconstrainedWidth();
        org.jfree.data.Range range7 = rectangleConstraint6.getWidthRange();
        double double9 = range7.constrain((double) (byte) 1);
        org.jfree.data.Range range10 = null;
        org.jfree.data.Range range12 = org.jfree.data.Range.expandToInclude(range10, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint14 = new org.jfree.chart.block.RectangleConstraint(range12, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint15 = rectangleConstraint14.toUnconstrainedWidth();
        org.jfree.data.Range range16 = rectangleConstraint15.getWidthRange();
        double double17 = range16.getCentralValue();
        org.jfree.data.Range range18 = org.jfree.data.Range.combine(range7, range16);
        boolean boolean20 = range16.contains((double) (short) 1);
        org.jfree.data.Range range23 = org.jfree.data.Range.expand(range16, (double) (byte) 0, 0.0d);
        org.jfree.data.Range range25 = null;
        org.jfree.data.Range range27 = org.jfree.data.Range.expandToInclude(range25, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint29 = new org.jfree.chart.block.RectangleConstraint(range27, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint30 = rectangleConstraint29.toUnconstrainedWidth();
        org.jfree.data.Range range31 = rectangleConstraint30.getWidthRange();
        double double33 = range31.constrain((double) (byte) 1);
        org.jfree.chart.block.LengthConstraintType lengthConstraintType34 = org.jfree.chart.block.LengthConstraintType.FIXED;
        org.jfree.data.Range range36 = null;
        org.jfree.data.Range range38 = org.jfree.data.Range.expandToInclude(range36, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint40 = new org.jfree.chart.block.RectangleConstraint(range38, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint41 = rectangleConstraint40.toUnconstrainedWidth();
        org.jfree.data.Range range42 = rectangleConstraint41.getWidthRange();
        double double44 = range42.constrain((double) (byte) 1);
        org.jfree.data.Range range45 = null;
        org.jfree.data.Range range47 = org.jfree.data.Range.expandToInclude(range45, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint49 = new org.jfree.chart.block.RectangleConstraint(range47, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint50 = rectangleConstraint49.toUnconstrainedWidth();
        org.jfree.data.Range range51 = rectangleConstraint50.getWidthRange();
        double double52 = range51.getCentralValue();
        org.jfree.data.Range range53 = org.jfree.data.Range.combine(range42, range51);
        java.lang.String str54 = range53.toString();
        org.jfree.chart.block.LengthConstraintType lengthConstraintType55 = org.jfree.chart.block.LengthConstraintType.FIXED;
        org.jfree.chart.block.BorderArrangement borderArrangement56 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer57 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement56);
        java.awt.geom.Rectangle2D rectangle2D58 = blockContainer57.getBounds();
        org.jfree.chart.util.RectangleInsets rectangleInsets59 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double61 = rectangleInsets59.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock64 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D65 = emptyBlock64.getBounds();
        java.awt.geom.Rectangle2D rectangle2D68 = rectangleInsets59.createOutsetRectangle(rectangle2D65, true, false);
        blockContainer57.setBounds(rectangle2D68);
        boolean boolean70 = lengthConstraintType55.equals((java.lang.Object) blockContainer57);
        org.jfree.chart.util.RectangleInsets rectangleInsets71 = new org.jfree.chart.util.RectangleInsets();
        double double72 = rectangleInsets71.getTop();
        double double73 = rectangleInsets71.getLeft();
        boolean boolean74 = lengthConstraintType55.equals((java.lang.Object) rectangleInsets71);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint75 = new org.jfree.chart.block.RectangleConstraint((double) 10L, range31, lengthConstraintType34, (double) (short) 10, range53, lengthConstraintType55);
        org.jfree.data.Range range77 = null;
        org.jfree.chart.block.LengthConstraintType lengthConstraintType78 = org.jfree.chart.block.LengthConstraintType.FIXED;
        org.jfree.chart.block.RectangleConstraint rectangleConstraint79 = new org.jfree.chart.block.RectangleConstraint((double) (short) 1, range23, lengthConstraintType34, (double) (byte) -1, range77, lengthConstraintType78);
        java.lang.String str80 = lengthConstraintType34.toString();
        org.junit.Assert.assertNotNull(range3);
        org.junit.Assert.assertNotNull(rectangleConstraint6);
        org.junit.Assert.assertNotNull(range7);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + (-1.0d) + "'", double9 == (-1.0d));
        org.junit.Assert.assertNotNull(range12);
        org.junit.Assert.assertNotNull(rectangleConstraint15);
        org.junit.Assert.assertNotNull(range16);
        org.junit.Assert.assertTrue("'" + double17 + "' != '" + (-1.0d) + "'", double17 == (-1.0d));
        org.junit.Assert.assertNotNull(range18);
        org.junit.Assert.assertTrue("'" + boolean20 + "' != '" + false + "'", boolean20 == false);
        org.junit.Assert.assertNotNull(range23);
        org.junit.Assert.assertNotNull(range27);
        org.junit.Assert.assertNotNull(rectangleConstraint30);
        org.junit.Assert.assertNotNull(range31);
        org.junit.Assert.assertTrue("'" + double33 + "' != '" + (-1.0d) + "'", double33 == (-1.0d));
        org.junit.Assert.assertNotNull(lengthConstraintType34);
        org.junit.Assert.assertNotNull(range38);
        org.junit.Assert.assertNotNull(rectangleConstraint41);
        org.junit.Assert.assertNotNull(range42);
        org.junit.Assert.assertTrue("'" + double44 + "' != '" + (-1.0d) + "'", double44 == (-1.0d));
        org.junit.Assert.assertNotNull(range47);
        org.junit.Assert.assertNotNull(rectangleConstraint50);
        org.junit.Assert.assertNotNull(range51);
        org.junit.Assert.assertTrue("'" + double52 + "' != '" + (-1.0d) + "'", double52 == (-1.0d));
        org.junit.Assert.assertNotNull(range53);
        org.junit.Assert.assertEquals("'" + str54 + "' != '" + "Range[-1.0,-1.0]" + "'", str54, "Range[-1.0,-1.0]");
        org.junit.Assert.assertNotNull(lengthConstraintType55);
        org.junit.Assert.assertNotNull(rectangle2D58);
        org.junit.Assert.assertNotNull(rectangleInsets59);
        org.junit.Assert.assertTrue("'" + double61 + "' != '" + 0.0d + "'", double61 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D65);
        org.junit.Assert.assertNotNull(rectangle2D68);
        org.junit.Assert.assertTrue("'" + boolean70 + "' != '" + false + "'", boolean70 == false);
        org.junit.Assert.assertTrue("'" + double72 + "' != '" + 1.0d + "'", double72 == 1.0d);
        org.junit.Assert.assertTrue("'" + double73 + "' != '" + 1.0d + "'", double73 == 1.0d);
        org.junit.Assert.assertTrue("'" + boolean74 + "' != '" + false + "'", boolean74 == false);
        org.junit.Assert.assertNotNull(lengthConstraintType78);
        org.junit.Assert.assertEquals("'" + str80 + "' != '" + "LengthConstraintType.FIXED" + "'", str80, "LengthConstraintType.FIXED");
    }

    @Test
    public void test528() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test528");
        org.jfree.chart.block.RectangleConstraint rectangleConstraint2 = new org.jfree.chart.block.RectangleConstraint((double) (short) -1, (double) 100);
        double double3 = rectangleConstraint2.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint5 = rectangleConstraint2.toFixedHeight((double) 100);
        org.jfree.chart.block.LengthConstraintType lengthConstraintType6 = rectangleConstraint5.getHeightConstraintType();
        org.jfree.chart.block.LengthConstraintType lengthConstraintType7 = rectangleConstraint5.getHeightConstraintType();
        java.lang.String str8 = lengthConstraintType7.toString();
        java.lang.String str9 = lengthConstraintType7.toString();
        org.junit.Assert.assertTrue("'" + double3 + "' != '" + (-1.0d) + "'", double3 == (-1.0d));
        org.junit.Assert.assertNotNull(rectangleConstraint5);
        org.junit.Assert.assertNotNull(lengthConstraintType6);
        org.junit.Assert.assertNotNull(lengthConstraintType7);
        org.junit.Assert.assertEquals("'" + str8 + "' != '" + "LengthConstraintType.FIXED" + "'", str8, "LengthConstraintType.FIXED");
        org.junit.Assert.assertEquals("'" + str9 + "' != '" + "LengthConstraintType.FIXED" + "'", str9, "LengthConstraintType.FIXED");
    }

    @Test
    public void test529() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test529");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        java.util.List list2 = blockContainer1.getBlocks();
        blockContainer1.clear();
        java.awt.Graphics2D graphics2D4 = null;
        org.jfree.chart.util.RectangleInsets rectangleInsets5 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double7 = rectangleInsets5.calculateBottomInset((double) 0.0f);
        org.jfree.chart.util.UnitType unitType8 = rectangleInsets5.getUnitType();
        java.lang.String str9 = unitType8.toString();
        org.jfree.chart.block.BlockBorder blockBorder10 = org.jfree.chart.block.BlockBorder.NONE;
        boolean boolean11 = unitType8.equals((java.lang.Object) blockBorder10);
        java.lang.String str12 = unitType8.toString();
        org.jfree.data.Range range14 = null;
        org.jfree.chart.block.RectangleConstraint rectangleConstraint15 = new org.jfree.chart.block.RectangleConstraint(0.0d, range14);
        java.lang.String str16 = rectangleConstraint15.toString();
        org.jfree.data.Range range17 = null;
        org.jfree.data.Range range19 = org.jfree.data.Range.expandToInclude(range17, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint21 = new org.jfree.chart.block.RectangleConstraint(range19, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint22 = rectangleConstraint15.toRangeWidth(range19);
        boolean boolean23 = unitType8.equals((java.lang.Object) rectangleConstraint15);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint25 = rectangleConstraint15.toFixedHeight((double) 0);
        org.jfree.chart.util.Size2D size2D26 = blockContainer1.arrange(graphics2D4, rectangleConstraint25);
        org.junit.Assert.assertNotNull(list2);
        org.junit.Assert.assertNotNull(rectangleInsets5);
        org.junit.Assert.assertTrue("'" + double7 + "' != '" + 0.0d + "'", double7 == 0.0d);
        org.junit.Assert.assertNotNull(unitType8);
        org.junit.Assert.assertEquals("'" + str9 + "' != '" + "UnitType.ABSOLUTE" + "'", str9, "UnitType.ABSOLUTE");
        org.junit.Assert.assertNotNull(blockBorder10);
        org.junit.Assert.assertTrue("'" + boolean11 + "' != '" + false + "'", boolean11 == false);
        org.junit.Assert.assertEquals("'" + str12 + "' != '" + "UnitType.ABSOLUTE" + "'", str12, "UnitType.ABSOLUTE");
        org.junit.Assert.assertEquals("'" + str16 + "' != '" + "RectangleConstraint[LengthConstraintType.FIXED: width=0.0, height=0.0]" + "'", str16, "RectangleConstraint[LengthConstraintType.FIXED: width=0.0, height=0.0]");
        org.junit.Assert.assertNotNull(range19);
        org.junit.Assert.assertNotNull(rectangleConstraint22);
        org.junit.Assert.assertTrue("'" + boolean23 + "' != '" + false + "'", boolean23 == false);
        org.junit.Assert.assertNotNull(rectangleConstraint25);
        org.junit.Assert.assertNotNull(size2D26);
    }

    @Test
    public void test530() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test530");
        org.jfree.chart.block.EmptyBlock emptyBlock2 = new org.jfree.chart.block.EmptyBlock((-1.0d), 0.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets3 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double5 = rectangleInsets3.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock8 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D9 = emptyBlock8.getBounds();
        java.awt.geom.Rectangle2D rectangle2D12 = rectangleInsets3.createOutsetRectangle(rectangle2D9, true, false);
        java.awt.geom.Rectangle2D rectangle2D13 = emptyBlock2.trimBorder(rectangle2D9);
        java.awt.geom.Rectangle2D rectangle2D14 = emptyBlock2.getBounds();
        org.jfree.chart.util.RectangleInsets rectangleInsets15 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double17 = rectangleInsets15.calculateBottomInset((double) 0.0f);
        double double18 = rectangleInsets15.getBottom();
        emptyBlock2.setMargin(rectangleInsets15);
        double double21 = rectangleInsets15.calculateLeftOutset((double) (byte) -1);
        org.junit.Assert.assertNotNull(rectangleInsets3);
        org.junit.Assert.assertTrue("'" + double5 + "' != '" + 0.0d + "'", double5 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D9);
        org.junit.Assert.assertNotNull(rectangle2D12);
        org.junit.Assert.assertNotNull(rectangle2D13);
        org.junit.Assert.assertNotNull(rectangle2D14);
        org.junit.Assert.assertNotNull(rectangleInsets15);
        org.junit.Assert.assertTrue("'" + double17 + "' != '" + 0.0d + "'", double17 == 0.0d);
        org.junit.Assert.assertTrue("'" + double18 + "' != '" + 0.0d + "'", double18 == 0.0d);
        org.junit.Assert.assertTrue("'" + double21 + "' != '" + 0.0d + "'", double21 == 0.0d);
    }

    @Test
    public void test531() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test531");
        org.jfree.chart.util.RectangleInsets rectangleInsets0 = new org.jfree.chart.util.RectangleInsets();
        double double1 = rectangleInsets0.getRight();
        double double3 = rectangleInsets0.calculateLeftInset((double) (byte) 0);
        org.jfree.chart.util.Size2D size2D4 = new org.jfree.chart.util.Size2D();
        size2D4.setHeight((double) (short) 0);
        double double7 = size2D4.getWidth();
        double double8 = size2D4.getWidth();
        org.jfree.chart.util.RectangleInsets rectangleInsets9 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double11 = rectangleInsets9.calculateTopOutset((double) 1.0f);
        double double13 = rectangleInsets9.extendHeight((double) (byte) -1);
        boolean boolean14 = size2D4.equals((java.lang.Object) rectangleInsets9);
        org.jfree.chart.block.EmptyBlock emptyBlock17 = new org.jfree.chart.block.EmptyBlock((-1.0d), 0.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets18 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double20 = rectangleInsets18.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock23 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D24 = emptyBlock23.getBounds();
        java.awt.geom.Rectangle2D rectangle2D27 = rectangleInsets18.createOutsetRectangle(rectangle2D24, true, false);
        java.awt.geom.Rectangle2D rectangle2D28 = emptyBlock17.trimBorder(rectangle2D24);
        java.awt.geom.Rectangle2D rectangle2D29 = emptyBlock17.getBounds();
        java.awt.geom.Rectangle2D rectangle2D32 = rectangleInsets9.createInsetRectangle(rectangle2D29, true, false);
        rectangleInsets0.trim(rectangle2D29);
        double double35 = rectangleInsets0.calculateLeftOutset((double) 1.0f);
        org.junit.Assert.assertTrue("'" + double1 + "' != '" + 1.0d + "'", double1 == 1.0d);
        org.junit.Assert.assertTrue("'" + double3 + "' != '" + 1.0d + "'", double3 == 1.0d);
        org.junit.Assert.assertTrue("'" + double7 + "' != '" + 0.0d + "'", double7 == 0.0d);
        org.junit.Assert.assertTrue("'" + double8 + "' != '" + 0.0d + "'", double8 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets9);
        org.junit.Assert.assertTrue("'" + double11 + "' != '" + 0.0d + "'", double11 == 0.0d);
        org.junit.Assert.assertTrue("'" + double13 + "' != '" + (-1.0d) + "'", double13 == (-1.0d));
        org.junit.Assert.assertTrue("'" + boolean14 + "' != '" + false + "'", boolean14 == false);
        org.junit.Assert.assertNotNull(rectangleInsets18);
        org.junit.Assert.assertTrue("'" + double20 + "' != '" + 0.0d + "'", double20 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D24);
        org.junit.Assert.assertNotNull(rectangle2D27);
        org.junit.Assert.assertNotNull(rectangle2D28);
        org.junit.Assert.assertNotNull(rectangle2D29);
        org.junit.Assert.assertNotNull(rectangle2D32);
        org.junit.Assert.assertTrue("'" + double35 + "' != '" + 1.0d + "'", double35 == 1.0d);
    }

    @Test
    public void test532() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test532");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.EmptyBlock emptyBlock4 = new org.jfree.chart.block.EmptyBlock((double) (short) 0, (double) 1);
        boolean boolean5 = borderArrangement0.equals((java.lang.Object) emptyBlock4);
        org.jfree.chart.block.BlockContainer blockContainer6 = null;
        java.awt.Graphics2D graphics2D7 = null;
        org.jfree.data.Range range9 = null;
        org.jfree.chart.block.RectangleConstraint rectangleConstraint10 = new org.jfree.chart.block.RectangleConstraint(0.0d, range9);
        java.lang.String str11 = rectangleConstraint10.toString();
        org.jfree.data.Range range12 = null;
        org.jfree.data.Range range14 = org.jfree.data.Range.expandToInclude(range12, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint16 = new org.jfree.chart.block.RectangleConstraint(range14, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint17 = rectangleConstraint10.toRangeWidth(range14);
        org.jfree.chart.block.LengthConstraintType lengthConstraintType18 = rectangleConstraint17.getWidthConstraintType();
        double double19 = rectangleConstraint17.getWidth();
        org.jfree.chart.util.Size2D size2D20 = borderArrangement0.arrangeFF(blockContainer6, graphics2D7, rectangleConstraint17);
        org.junit.Assert.assertTrue("'" + boolean5 + "' != '" + false + "'", boolean5 == false);
        org.junit.Assert.assertEquals("'" + str11 + "' != '" + "RectangleConstraint[LengthConstraintType.FIXED: width=0.0, height=0.0]" + "'", str11, "RectangleConstraint[LengthConstraintType.FIXED: width=0.0, height=0.0]");
        org.junit.Assert.assertNotNull(range14);
        org.junit.Assert.assertNotNull(rectangleConstraint17);
        org.junit.Assert.assertNotNull(lengthConstraintType18);
        org.junit.Assert.assertTrue("'" + double19 + "' != '" + (-1.0d) + "'", double19 == (-1.0d));
        org.junit.Assert.assertNotNull(size2D20);
    }

    @Test
    public void test533() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test533");
        org.jfree.data.Range range1 = null;
        org.jfree.data.Range range3 = org.jfree.data.Range.expandToInclude(range1, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint5 = new org.jfree.chart.block.RectangleConstraint(range3, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint6 = rectangleConstraint5.toUnconstrainedWidth();
        org.jfree.data.Range range7 = rectangleConstraint6.getWidthRange();
        double double9 = range7.constrain((double) (byte) 1);
        org.jfree.data.Range range10 = null;
        org.jfree.data.Range range12 = org.jfree.data.Range.expandToInclude(range10, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint14 = new org.jfree.chart.block.RectangleConstraint(range12, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint15 = rectangleConstraint14.toUnconstrainedWidth();
        org.jfree.data.Range range16 = rectangleConstraint15.getWidthRange();
        double double17 = range16.getCentralValue();
        org.jfree.data.Range range18 = org.jfree.data.Range.combine(range7, range16);
        double double19 = range16.getCentralValue();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint20 = new org.jfree.chart.block.RectangleConstraint((double) (byte) 100, range16);
        double double21 = rectangleConstraint20.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint23 = rectangleConstraint20.toFixedWidth(100.0d);
        org.jfree.chart.block.LengthConstraintType lengthConstraintType24 = rectangleConstraint20.getWidthConstraintType();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint26 = rectangleConstraint20.toFixedWidth(32.0d);
        org.jfree.data.Range range27 = rectangleConstraint26.getWidthRange();
        java.lang.String str28 = rectangleConstraint26.toString();
        org.junit.Assert.assertNotNull(range3);
        org.junit.Assert.assertNotNull(rectangleConstraint6);
        org.junit.Assert.assertNotNull(range7);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + (-1.0d) + "'", double9 == (-1.0d));
        org.junit.Assert.assertNotNull(range12);
        org.junit.Assert.assertNotNull(rectangleConstraint15);
        org.junit.Assert.assertNotNull(range16);
        org.junit.Assert.assertTrue("'" + double17 + "' != '" + (-1.0d) + "'", double17 == (-1.0d));
        org.junit.Assert.assertNotNull(range18);
        org.junit.Assert.assertTrue("'" + double19 + "' != '" + (-1.0d) + "'", double19 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double21 + "' != '" + 100.0d + "'", double21 == 100.0d);
        org.junit.Assert.assertNotNull(rectangleConstraint23);
        org.junit.Assert.assertNotNull(lengthConstraintType24);
        org.junit.Assert.assertNotNull(rectangleConstraint26);
        org.junit.Assert.assertNull(range27);
        org.junit.Assert.assertEquals("'" + str28 + "' != '" + "RectangleConstraint[LengthConstraintType.FIXED: width=32.0, height=0.0]" + "'", str28, "RectangleConstraint[LengthConstraintType.FIXED: width=32.0, height=0.0]");
    }

    @Test
    public void test534() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test534");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement0.clear();
        org.jfree.chart.block.EmptyBlock emptyBlock4 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D5 = emptyBlock4.getBounds();
        org.jfree.data.Range range6 = null;
        org.jfree.data.Range range8 = org.jfree.data.Range.expandToInclude(range6, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint10 = new org.jfree.chart.block.RectangleConstraint(range8, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint11 = rectangleConstraint10.toUnconstrainedWidth();
        double double12 = rectangleConstraint11.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint13 = emptyBlock4.toContentConstraint(rectangleConstraint11);
        double double15 = emptyBlock4.trimToContentHeight((double) (byte) 0);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint18 = new org.jfree.chart.block.RectangleConstraint((double) (short) -1, (double) 100);
        double double19 = rectangleConstraint18.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint21 = rectangleConstraint18.toFixedHeight((double) 100);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint22 = emptyBlock4.toContentConstraint(rectangleConstraint21);
        java.lang.Object obj23 = null;
        borderArrangement0.add((org.jfree.chart.block.Block) emptyBlock4, obj23);
        org.jfree.data.Range range26 = null;
        org.jfree.data.Range range28 = org.jfree.data.Range.expandToInclude(range26, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint29 = new org.jfree.chart.block.RectangleConstraint(33.0d, range28);
        boolean boolean30 = borderArrangement0.equals((java.lang.Object) 33.0d);
        org.jfree.chart.block.BorderArrangement borderArrangement31 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer32 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement31);
        java.util.List list33 = blockContainer32.getBlocks();
        blockContainer32.setMargin((double) ' ', (double) 100.0f, 0.0d, (double) 'a');
        org.jfree.chart.block.Arrangement arrangement39 = blockContainer32.getArrangement();
        double double40 = blockContainer32.getHeight();
        java.awt.Graphics2D graphics2D41 = null;
        org.jfree.chart.block.RectangleConstraint rectangleConstraint44 = new org.jfree.chart.block.RectangleConstraint((double) (short) 1, (double) (-1));
        org.jfree.chart.util.Size2D size2D45 = borderArrangement0.arrangeFF(blockContainer32, graphics2D41, rectangleConstraint44);
        java.lang.Object obj46 = blockContainer32.clone();
        org.junit.Assert.assertNotNull(rectangle2D5);
        org.junit.Assert.assertNotNull(range8);
        org.junit.Assert.assertNotNull(rectangleConstraint11);
        org.junit.Assert.assertTrue("'" + double12 + "' != '" + 0.0d + "'", double12 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleConstraint13);
        org.junit.Assert.assertTrue("'" + double15 + "' != '" + 0.0d + "'", double15 == 0.0d);
        org.junit.Assert.assertTrue("'" + double19 + "' != '" + (-1.0d) + "'", double19 == (-1.0d));
        org.junit.Assert.assertNotNull(rectangleConstraint21);
        org.junit.Assert.assertNotNull(rectangleConstraint22);
        org.junit.Assert.assertNotNull(range28);
        org.junit.Assert.assertTrue("'" + boolean30 + "' != '" + false + "'", boolean30 == false);
        org.junit.Assert.assertNotNull(list33);
        org.junit.Assert.assertNotNull(arrangement39);
        org.junit.Assert.assertTrue("'" + double40 + "' != '" + 0.0d + "'", double40 == 0.0d);
        org.junit.Assert.assertNotNull(size2D45);
        org.junit.Assert.assertNotNull(obj46);
    }

    @Test
    public void test535() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test535");
        org.jfree.chart.util.RectangleInsets rectangleInsets4 = new org.jfree.chart.util.RectangleInsets((double) (byte) 0, (double) 10.0f, (double) 100L, (double) (short) 1);
        org.jfree.chart.block.BorderArrangement borderArrangement9 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer10 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement9);
        org.jfree.chart.block.BlockBorder blockBorder11 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer10.setFrame((org.jfree.chart.block.BlockFrame) blockBorder11);
        java.awt.Paint paint13 = blockBorder11.getPaint();
        org.jfree.chart.block.BlockBorder blockBorder14 = new org.jfree.chart.block.BlockBorder((double) (-1L), 90.0d, (double) (byte) 0, (double) 100L, paint13);
        java.awt.Paint paint15 = blockBorder14.getPaint();
        org.jfree.chart.block.BlockBorder blockBorder16 = new org.jfree.chart.block.BlockBorder(rectangleInsets4, paint15);
        org.junit.Assert.assertNotNull(blockBorder11);
        org.junit.Assert.assertNotNull(paint13);
        org.junit.Assert.assertNotNull(paint15);
    }

    @Test
    public void test536() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test536");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        java.util.List list2 = blockContainer1.getBlocks();
        java.util.List list3 = blockContainer1.getBlocks();
        java.awt.Graphics2D graphics2D4 = null;
        org.jfree.chart.util.Size2D size2D5 = blockContainer1.arrange(graphics2D4);
        size2D5.setWidth((double) 0.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint10 = new org.jfree.chart.block.RectangleConstraint((double) (short) -1, (double) 100);
        double double11 = rectangleConstraint10.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint13 = rectangleConstraint10.toFixedHeight((double) 100);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint15 = rectangleConstraint10.toFixedWidth(0.0d);
        boolean boolean16 = size2D5.equals((java.lang.Object) 0.0d);
        org.junit.Assert.assertNotNull(list2);
        org.junit.Assert.assertNotNull(list3);
        org.junit.Assert.assertNotNull(size2D5);
        org.junit.Assert.assertTrue("'" + double11 + "' != '" + (-1.0d) + "'", double11 == (-1.0d));
        org.junit.Assert.assertNotNull(rectangleConstraint13);
        org.junit.Assert.assertNotNull(rectangleConstraint15);
        org.junit.Assert.assertTrue("'" + boolean16 + "' != '" + false + "'", boolean16 == false);
    }

    @Test
    public void test537() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test537");
        org.jfree.chart.block.EmptyBlock emptyBlock2 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D3 = emptyBlock2.getBounds();
        org.jfree.data.Range range4 = null;
        org.jfree.data.Range range6 = org.jfree.data.Range.expandToInclude(range4, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint8 = new org.jfree.chart.block.RectangleConstraint(range6, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint9 = rectangleConstraint8.toUnconstrainedWidth();
        double double10 = rectangleConstraint9.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint11 = emptyBlock2.toContentConstraint(rectangleConstraint9);
        double double13 = emptyBlock2.trimToContentHeight((double) (byte) 0);
        java.awt.Graphics2D graphics2D14 = null;
        org.jfree.chart.block.BorderArrangement borderArrangement15 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer16 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement15);
        java.util.List list17 = blockContainer16.getBlocks();
        blockContainer16.clear();
        org.jfree.chart.util.RectangleInsets rectangleInsets19 = blockContainer16.getPadding();
        org.jfree.chart.util.RectangleInsets rectangleInsets20 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double22 = rectangleInsets20.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock25 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D26 = emptyBlock25.getBounds();
        java.awt.geom.Rectangle2D rectangle2D29 = rectangleInsets20.createOutsetRectangle(rectangle2D26, true, false);
        rectangleInsets19.trim(rectangle2D29);
        org.jfree.chart.util.UnitType unitType31 = rectangleInsets19.getUnitType();
        org.jfree.chart.block.EmptyBlock emptyBlock34 = new org.jfree.chart.block.EmptyBlock(0.0d, (double) 10);
        emptyBlock34.setID("RectangleConstraint[LengthConstraintType.FIXED: width=0.0, height=0.0]");
        emptyBlock34.setHeight((double) (-1L));
        emptyBlock34.setMargin((double) 0, 0.0d, (double) (byte) 0, (double) 100.0f);
        java.awt.geom.Rectangle2D rectangle2D44 = emptyBlock34.getBounds();
        java.awt.geom.Rectangle2D rectangle2D45 = rectangleInsets19.createInsetRectangle(rectangle2D44);
        // The following exception was thrown during execution in test generation
        try {
            emptyBlock2.draw(graphics2D14, rectangle2D45);
            org.junit.Assert.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
            // Expected exception.
        }
        org.junit.Assert.assertNotNull(rectangle2D3);
        org.junit.Assert.assertNotNull(range6);
        org.junit.Assert.assertNotNull(rectangleConstraint9);
        org.junit.Assert.assertTrue("'" + double10 + "' != '" + 0.0d + "'", double10 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleConstraint11);
        org.junit.Assert.assertTrue("'" + double13 + "' != '" + 0.0d + "'", double13 == 0.0d);
        org.junit.Assert.assertNotNull(list17);
        org.junit.Assert.assertNotNull(rectangleInsets19);
        org.junit.Assert.assertNotNull(rectangleInsets20);
        org.junit.Assert.assertTrue("'" + double22 + "' != '" + 0.0d + "'", double22 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D26);
        org.junit.Assert.assertNotNull(rectangle2D29);
        org.junit.Assert.assertNotNull(unitType31);
        org.junit.Assert.assertNotNull(rectangle2D44);
        org.junit.Assert.assertNotNull(rectangle2D45);
    }

    @Test
    public void test538() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test538");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        java.util.List list2 = blockContainer1.getBlocks();
        blockContainer1.clear();
        org.jfree.chart.util.RectangleInsets rectangleInsets4 = blockContainer1.getPadding();
        org.jfree.chart.block.BlockFrame blockFrame5 = blockContainer1.getFrame();
        double double6 = blockContainer1.getContentYOffset();
        org.jfree.chart.util.RectangleInsets rectangleInsets7 = blockContainer1.getMargin();
        double double8 = rectangleInsets7.getLeft();
        org.junit.Assert.assertNotNull(list2);
        org.junit.Assert.assertNotNull(rectangleInsets4);
        org.junit.Assert.assertNotNull(blockFrame5);
        org.junit.Assert.assertTrue("'" + double6 + "' != '" + 0.0d + "'", double6 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets7);
        org.junit.Assert.assertTrue("'" + double8 + "' != '" + 0.0d + "'", double8 == 0.0d);
    }

    @Test
    public void test539() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test539");
        org.jfree.data.Range range1 = null;
        org.jfree.data.Range range3 = org.jfree.data.Range.expandToInclude(range1, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint5 = new org.jfree.chart.block.RectangleConstraint(range3, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint6 = rectangleConstraint5.toUnconstrainedWidth();
        org.jfree.data.Range range7 = rectangleConstraint6.getWidthRange();
        double double9 = range7.constrain((double) (byte) 1);
        org.jfree.data.Range range10 = null;
        org.jfree.data.Range range12 = org.jfree.data.Range.expandToInclude(range10, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint14 = new org.jfree.chart.block.RectangleConstraint(range12, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint15 = rectangleConstraint14.toUnconstrainedWidth();
        org.jfree.data.Range range16 = rectangleConstraint15.getWidthRange();
        double double17 = range16.getCentralValue();
        org.jfree.data.Range range18 = org.jfree.data.Range.combine(range7, range16);
        double double19 = range16.getCentralValue();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint20 = new org.jfree.chart.block.RectangleConstraint((double) (byte) 100, range16);
        double double21 = rectangleConstraint20.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint23 = rectangleConstraint20.toFixedWidth(100.0d);
        org.jfree.chart.block.LengthConstraintType lengthConstraintType24 = rectangleConstraint20.getWidthConstraintType();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint26 = rectangleConstraint20.toFixedWidth(32.0d);
        org.jfree.data.Range range27 = rectangleConstraint26.getHeightRange();
        double double29 = range27.constrain((double) (short) -1);
        org.junit.Assert.assertNotNull(range3);
        org.junit.Assert.assertNotNull(rectangleConstraint6);
        org.junit.Assert.assertNotNull(range7);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + (-1.0d) + "'", double9 == (-1.0d));
        org.junit.Assert.assertNotNull(range12);
        org.junit.Assert.assertNotNull(rectangleConstraint15);
        org.junit.Assert.assertNotNull(range16);
        org.junit.Assert.assertTrue("'" + double17 + "' != '" + (-1.0d) + "'", double17 == (-1.0d));
        org.junit.Assert.assertNotNull(range18);
        org.junit.Assert.assertTrue("'" + double19 + "' != '" + (-1.0d) + "'", double19 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double21 + "' != '" + 100.0d + "'", double21 == 100.0d);
        org.junit.Assert.assertNotNull(rectangleConstraint23);
        org.junit.Assert.assertNotNull(lengthConstraintType24);
        org.junit.Assert.assertNotNull(rectangleConstraint26);
        org.junit.Assert.assertNotNull(range27);
        org.junit.Assert.assertTrue("'" + double29 + "' != '" + (-1.0d) + "'", double29 == (-1.0d));
    }

    @Test
    public void test540() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test540");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        java.util.List list2 = blockContainer1.getBlocks();
        blockContainer1.clear();
        org.jfree.chart.util.RectangleInsets rectangleInsets4 = blockContainer1.getPadding();
        org.jfree.chart.block.Arrangement arrangement5 = blockContainer1.getArrangement();
        java.util.List list6 = blockContainer1.getBlocks();
        org.junit.Assert.assertNotNull(list2);
        org.junit.Assert.assertNotNull(rectangleInsets4);
        org.junit.Assert.assertNotNull(arrangement5);
        org.junit.Assert.assertNotNull(list6);
    }

    @Test
    public void test541() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test541");
        org.jfree.chart.util.RectangleInsets rectangleInsets0 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double2 = rectangleInsets0.calculateTopOutset((double) 1.0f);
        double double3 = rectangleInsets0.getRight();
        org.jfree.chart.block.BorderArrangement borderArrangement4 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer5 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement4);
        java.util.List list6 = blockContainer5.getBlocks();
        blockContainer5.setMargin((double) ' ', (double) 100.0f, 0.0d, (double) 'a');
        org.jfree.chart.block.BorderArrangement borderArrangement12 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement12.clear();
        borderArrangement12.clear();
        borderArrangement12.clear();
        org.jfree.chart.block.BlockContainer blockContainer16 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement12);
        blockContainer5.add((org.jfree.chart.block.Block) blockContainer16);
        blockContainer5.setID("RectangleEdge.LEFT");
        java.awt.geom.Rectangle2D rectangle2D20 = blockContainer5.getBounds();
        rectangleInsets0.trim(rectangle2D20);
        double double23 = rectangleInsets0.trimHeight(31.0d);
        org.junit.Assert.assertNotNull(rectangleInsets0);
        org.junit.Assert.assertTrue("'" + double2 + "' != '" + 0.0d + "'", double2 == 0.0d);
        org.junit.Assert.assertTrue("'" + double3 + "' != '" + 0.0d + "'", double3 == 0.0d);
        org.junit.Assert.assertNotNull(list6);
        org.junit.Assert.assertNotNull(rectangle2D20);
        org.junit.Assert.assertTrue("'" + double23 + "' != '" + 31.0d + "'", double23 == 31.0d);
    }

    @Test
    public void test542() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test542");
        org.jfree.data.Range range0 = null;
        org.jfree.data.Range range2 = org.jfree.data.Range.expandToInclude(range0, (double) (short) -1);
        boolean boolean4 = range2.contains((double) (short) 1);
        org.jfree.data.Range range7 = org.jfree.data.Range.expand(range2, (double) (-1.0f), (double) (short) 100);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint9 = new org.jfree.chart.block.RectangleConstraint(range7, (double) 0);
        org.junit.Assert.assertNotNull(range2);
        org.junit.Assert.assertTrue("'" + boolean4 + "' != '" + false + "'", boolean4 == false);
        org.junit.Assert.assertNotNull(range7);
    }

    @Test
    public void test543() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test543");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement0.clear();
        borderArrangement0.clear();
        borderArrangement0.clear();
        org.jfree.chart.block.BlockContainer blockContainer4 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.util.RectangleInsets rectangleInsets5 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double7 = rectangleInsets5.calculateTopOutset((double) 1.0f);
        double double9 = rectangleInsets5.trimHeight((-1.0d));
        blockContainer4.setPadding(rectangleInsets5);
        org.jfree.chart.util.RectangleInsets rectangleInsets11 = blockContainer4.getMargin();
        double double13 = rectangleInsets11.calculateTopOutset(97.0d);
        org.junit.Assert.assertNotNull(rectangleInsets5);
        org.junit.Assert.assertTrue("'" + double7 + "' != '" + 0.0d + "'", double7 == 0.0d);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + (-1.0d) + "'", double9 == (-1.0d));
        org.junit.Assert.assertNotNull(rectangleInsets11);
        org.junit.Assert.assertTrue("'" + double13 + "' != '" + 0.0d + "'", double13 == 0.0d);
    }

    @Test
    public void test544() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test544");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        java.util.List list2 = blockContainer1.getBlocks();
        blockContainer1.clear();
        double double5 = blockContainer1.calculateTotalHeight(0.0d);
        double double6 = blockContainer1.getContentYOffset();
        org.jfree.chart.block.Arrangement arrangement7 = blockContainer1.getArrangement();
        org.jfree.chart.block.BlockContainer blockContainer8 = new org.jfree.chart.block.BlockContainer(arrangement7);
        org.junit.Assert.assertNotNull(list2);
        org.junit.Assert.assertTrue("'" + double5 + "' != '" + 0.0d + "'", double5 == 0.0d);
        org.junit.Assert.assertTrue("'" + double6 + "' != '" + 0.0d + "'", double6 == 0.0d);
        org.junit.Assert.assertNotNull(arrangement7);
    }

    @Test
    public void test545() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test545");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = null;
        java.awt.Graphics2D graphics2D2 = null;
        org.jfree.chart.block.RectangleConstraint rectangleConstraint5 = new org.jfree.chart.block.RectangleConstraint((double) (short) -1, (double) 100);
        org.jfree.chart.util.Size2D size2D6 = borderArrangement0.arrangeFF(blockContainer1, graphics2D2, rectangleConstraint5);
        double double7 = size2D6.getWidth();
        size2D6.width = (-1L);
        double double10 = size2D6.getWidth();
        org.junit.Assert.assertNotNull(size2D6);
        org.junit.Assert.assertTrue("'" + double7 + "' != '" + (-1.0d) + "'", double7 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double10 + "' != '" + (-1.0d) + "'", double10 == (-1.0d));
    }

    @Test
    public void test546() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test546");
        org.jfree.chart.util.RectangleEdge rectangleEdge0 = org.jfree.chart.util.RectangleEdge.RIGHT;
        org.jfree.chart.util.RectangleEdge rectangleEdge1 = org.jfree.chart.util.RectangleEdge.LEFT;
        boolean boolean2 = rectangleEdge0.equals((java.lang.Object) rectangleEdge1);
        boolean boolean3 = org.jfree.chart.util.RectangleEdge.isTopOrBottom(rectangleEdge1);
        org.jfree.chart.util.RectangleEdge rectangleEdge4 = org.jfree.chart.util.RectangleEdge.opposite(rectangleEdge1);
        org.jfree.chart.util.RectangleEdge rectangleEdge5 = org.jfree.chart.util.RectangleEdge.opposite(rectangleEdge1);
        boolean boolean6 = org.jfree.chart.util.RectangleEdge.isLeftOrRight(rectangleEdge1);
        org.jfree.data.Range range7 = null;
        org.jfree.data.Range range9 = org.jfree.data.Range.expandToInclude(range7, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint11 = new org.jfree.chart.block.RectangleConstraint(range9, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint12 = rectangleConstraint11.toUnconstrainedWidth();
        org.jfree.data.Range range13 = rectangleConstraint12.getWidthRange();
        double double15 = range13.constrain((double) (byte) 1);
        org.jfree.data.Range range16 = null;
        org.jfree.data.Range range18 = org.jfree.data.Range.expandToInclude(range16, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint20 = new org.jfree.chart.block.RectangleConstraint(range18, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint21 = rectangleConstraint20.toUnconstrainedWidth();
        org.jfree.data.Range range22 = rectangleConstraint21.getWidthRange();
        double double23 = range22.getCentralValue();
        org.jfree.data.Range range24 = org.jfree.data.Range.combine(range13, range22);
        boolean boolean26 = range22.contains((double) (short) 1);
        double double27 = range22.getCentralValue();
        double double29 = range22.constrain((double) (byte) 100);
        org.jfree.data.Range range31 = org.jfree.data.Range.shift(range22, (double) (byte) -1);
        boolean boolean32 = rectangleEdge1.equals((java.lang.Object) (byte) -1);
        org.junit.Assert.assertNotNull(rectangleEdge0);
        org.junit.Assert.assertNotNull(rectangleEdge1);
        org.junit.Assert.assertTrue("'" + boolean2 + "' != '" + false + "'", boolean2 == false);
        org.junit.Assert.assertTrue("'" + boolean3 + "' != '" + false + "'", boolean3 == false);
        org.junit.Assert.assertNotNull(rectangleEdge4);
        org.junit.Assert.assertNotNull(rectangleEdge5);
        org.junit.Assert.assertTrue("'" + boolean6 + "' != '" + true + "'", boolean6 == true);
        org.junit.Assert.assertNotNull(range9);
        org.junit.Assert.assertNotNull(rectangleConstraint12);
        org.junit.Assert.assertNotNull(range13);
        org.junit.Assert.assertTrue("'" + double15 + "' != '" + (-1.0d) + "'", double15 == (-1.0d));
        org.junit.Assert.assertNotNull(range18);
        org.junit.Assert.assertNotNull(rectangleConstraint21);
        org.junit.Assert.assertNotNull(range22);
        org.junit.Assert.assertTrue("'" + double23 + "' != '" + (-1.0d) + "'", double23 == (-1.0d));
        org.junit.Assert.assertNotNull(range24);
        org.junit.Assert.assertTrue("'" + boolean26 + "' != '" + false + "'", boolean26 == false);
        org.junit.Assert.assertTrue("'" + double27 + "' != '" + (-1.0d) + "'", double27 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double29 + "' != '" + (-1.0d) + "'", double29 == (-1.0d));
        org.junit.Assert.assertNotNull(range31);
        org.junit.Assert.assertTrue("'" + boolean32 + "' != '" + false + "'", boolean32 == false);
    }

    @Test
    public void test547() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test547");
        org.jfree.data.Range range1 = null;
        org.jfree.data.Range range3 = org.jfree.data.Range.expandToInclude(range1, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint5 = new org.jfree.chart.block.RectangleConstraint(range3, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint6 = rectangleConstraint5.toUnconstrainedWidth();
        org.jfree.data.Range range7 = rectangleConstraint6.getWidthRange();
        double double9 = range7.constrain((double) (byte) 1);
        org.jfree.data.Range range10 = null;
        org.jfree.data.Range range12 = org.jfree.data.Range.expandToInclude(range10, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint14 = new org.jfree.chart.block.RectangleConstraint(range12, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint15 = rectangleConstraint14.toUnconstrainedWidth();
        org.jfree.data.Range range16 = rectangleConstraint15.getWidthRange();
        double double17 = range16.getCentralValue();
        org.jfree.data.Range range18 = org.jfree.data.Range.combine(range7, range16);
        double double19 = range16.getCentralValue();
        boolean boolean21 = range16.equals((java.lang.Object) (byte) 1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint24 = new org.jfree.chart.block.RectangleConstraint((double) (short) -1, (double) 100);
        double double25 = rectangleConstraint24.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint27 = rectangleConstraint24.toFixedHeight((double) 100);
        org.jfree.chart.block.LengthConstraintType lengthConstraintType28 = rectangleConstraint27.getHeightConstraintType();
        java.lang.String str29 = lengthConstraintType28.toString();
        org.jfree.data.Range range32 = null;
        org.jfree.data.Range range34 = org.jfree.data.Range.expandToInclude(range32, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint36 = new org.jfree.chart.block.RectangleConstraint(range34, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint37 = rectangleConstraint36.toUnconstrainedWidth();
        org.jfree.data.Range range38 = rectangleConstraint37.getWidthRange();
        double double40 = range38.constrain((double) (byte) 1);
        org.jfree.chart.block.LengthConstraintType lengthConstraintType41 = org.jfree.chart.block.LengthConstraintType.FIXED;
        org.jfree.data.Range range43 = null;
        org.jfree.data.Range range45 = org.jfree.data.Range.expandToInclude(range43, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint47 = new org.jfree.chart.block.RectangleConstraint(range45, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint48 = rectangleConstraint47.toUnconstrainedWidth();
        org.jfree.data.Range range49 = rectangleConstraint48.getWidthRange();
        double double51 = range49.constrain((double) (byte) 1);
        org.jfree.data.Range range52 = null;
        org.jfree.data.Range range54 = org.jfree.data.Range.expandToInclude(range52, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint56 = new org.jfree.chart.block.RectangleConstraint(range54, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint57 = rectangleConstraint56.toUnconstrainedWidth();
        org.jfree.data.Range range58 = rectangleConstraint57.getWidthRange();
        double double59 = range58.getCentralValue();
        org.jfree.data.Range range60 = org.jfree.data.Range.combine(range49, range58);
        java.lang.String str61 = range60.toString();
        org.jfree.chart.block.LengthConstraintType lengthConstraintType62 = org.jfree.chart.block.LengthConstraintType.FIXED;
        org.jfree.chart.block.BorderArrangement borderArrangement63 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer64 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement63);
        java.awt.geom.Rectangle2D rectangle2D65 = blockContainer64.getBounds();
        org.jfree.chart.util.RectangleInsets rectangleInsets66 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double68 = rectangleInsets66.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock71 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D72 = emptyBlock71.getBounds();
        java.awt.geom.Rectangle2D rectangle2D75 = rectangleInsets66.createOutsetRectangle(rectangle2D72, true, false);
        blockContainer64.setBounds(rectangle2D75);
        boolean boolean77 = lengthConstraintType62.equals((java.lang.Object) blockContainer64);
        org.jfree.chart.util.RectangleInsets rectangleInsets78 = new org.jfree.chart.util.RectangleInsets();
        double double79 = rectangleInsets78.getTop();
        double double80 = rectangleInsets78.getLeft();
        boolean boolean81 = lengthConstraintType62.equals((java.lang.Object) rectangleInsets78);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint82 = new org.jfree.chart.block.RectangleConstraint((double) 10L, range38, lengthConstraintType41, (double) (short) 10, range60, lengthConstraintType62);
        org.jfree.chart.block.LengthConstraintType lengthConstraintType83 = org.jfree.chart.block.LengthConstraintType.NONE;
        boolean boolean85 = lengthConstraintType83.equals((java.lang.Object) 1L);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint86 = new org.jfree.chart.block.RectangleConstraint((double) (short) 10, range16, lengthConstraintType28, 10.0d, range60, lengthConstraintType83);
        org.jfree.data.Range range87 = null;
        org.jfree.data.Range range89 = org.jfree.data.Range.expandToInclude(range87, (double) (short) -1);
        java.lang.String str90 = range89.toString();
        org.jfree.data.Range range92 = org.jfree.data.Range.shift(range89, (double) 10L);
        org.jfree.data.Range range94 = org.jfree.data.Range.expandToInclude(range89, (double) (short) 100);
        double double95 = range89.getUpperBound();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint96 = new org.jfree.chart.block.RectangleConstraint(range60, range89);
        org.junit.Assert.assertNotNull(range3);
        org.junit.Assert.assertNotNull(rectangleConstraint6);
        org.junit.Assert.assertNotNull(range7);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + (-1.0d) + "'", double9 == (-1.0d));
        org.junit.Assert.assertNotNull(range12);
        org.junit.Assert.assertNotNull(rectangleConstraint15);
        org.junit.Assert.assertNotNull(range16);
        org.junit.Assert.assertTrue("'" + double17 + "' != '" + (-1.0d) + "'", double17 == (-1.0d));
        org.junit.Assert.assertNotNull(range18);
        org.junit.Assert.assertTrue("'" + double19 + "' != '" + (-1.0d) + "'", double19 == (-1.0d));
        org.junit.Assert.assertTrue("'" + boolean21 + "' != '" + false + "'", boolean21 == false);
        org.junit.Assert.assertTrue("'" + double25 + "' != '" + (-1.0d) + "'", double25 == (-1.0d));
        org.junit.Assert.assertNotNull(rectangleConstraint27);
        org.junit.Assert.assertNotNull(lengthConstraintType28);
        org.junit.Assert.assertEquals("'" + str29 + "' != '" + "LengthConstraintType.FIXED" + "'", str29, "LengthConstraintType.FIXED");
        org.junit.Assert.assertNotNull(range34);
        org.junit.Assert.assertNotNull(rectangleConstraint37);
        org.junit.Assert.assertNotNull(range38);
        org.junit.Assert.assertTrue("'" + double40 + "' != '" + (-1.0d) + "'", double40 == (-1.0d));
        org.junit.Assert.assertNotNull(lengthConstraintType41);
        org.junit.Assert.assertNotNull(range45);
        org.junit.Assert.assertNotNull(rectangleConstraint48);
        org.junit.Assert.assertNotNull(range49);
        org.junit.Assert.assertTrue("'" + double51 + "' != '" + (-1.0d) + "'", double51 == (-1.0d));
        org.junit.Assert.assertNotNull(range54);
        org.junit.Assert.assertNotNull(rectangleConstraint57);
        org.junit.Assert.assertNotNull(range58);
        org.junit.Assert.assertTrue("'" + double59 + "' != '" + (-1.0d) + "'", double59 == (-1.0d));
        org.junit.Assert.assertNotNull(range60);
        org.junit.Assert.assertEquals("'" + str61 + "' != '" + "Range[-1.0,-1.0]" + "'", str61, "Range[-1.0,-1.0]");
        org.junit.Assert.assertNotNull(lengthConstraintType62);
        org.junit.Assert.assertNotNull(rectangle2D65);
        org.junit.Assert.assertNotNull(rectangleInsets66);
        org.junit.Assert.assertTrue("'" + double68 + "' != '" + 0.0d + "'", double68 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D72);
        org.junit.Assert.assertNotNull(rectangle2D75);
        org.junit.Assert.assertTrue("'" + boolean77 + "' != '" + false + "'", boolean77 == false);
        org.junit.Assert.assertTrue("'" + double79 + "' != '" + 1.0d + "'", double79 == 1.0d);
        org.junit.Assert.assertTrue("'" + double80 + "' != '" + 1.0d + "'", double80 == 1.0d);
        org.junit.Assert.assertTrue("'" + boolean81 + "' != '" + false + "'", boolean81 == false);
        org.junit.Assert.assertNotNull(lengthConstraintType83);
        org.junit.Assert.assertTrue("'" + boolean85 + "' != '" + false + "'", boolean85 == false);
        org.junit.Assert.assertNotNull(range89);
        org.junit.Assert.assertEquals("'" + str90 + "' != '" + "Range[-1.0,-1.0]" + "'", str90, "Range[-1.0,-1.0]");
        org.junit.Assert.assertNotNull(range92);
        org.junit.Assert.assertNotNull(range94);
        org.junit.Assert.assertTrue("'" + double95 + "' != '" + (-1.0d) + "'", double95 == (-1.0d));
    }

    @Test
    public void test548() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test548");
        org.jfree.chart.block.BorderArrangement borderArrangement4 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer5 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement4);
        org.jfree.chart.block.BlockBorder blockBorder6 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer5.setFrame((org.jfree.chart.block.BlockFrame) blockBorder6);
        java.awt.Paint paint8 = blockBorder6.getPaint();
        org.jfree.chart.block.BlockBorder blockBorder9 = new org.jfree.chart.block.BlockBorder((double) 10L, 0.0d, (double) 1.0f, (double) 10L, paint8);
        java.awt.Graphics2D graphics2D10 = null;
        org.jfree.chart.util.Size2D size2D11 = new org.jfree.chart.util.Size2D();
        size2D11.setHeight((double) (short) 0);
        double double14 = size2D11.getWidth();
        double double15 = size2D11.getWidth();
        org.jfree.chart.util.RectangleInsets rectangleInsets16 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double18 = rectangleInsets16.calculateTopOutset((double) 1.0f);
        double double20 = rectangleInsets16.extendHeight((double) (byte) -1);
        boolean boolean21 = size2D11.equals((java.lang.Object) rectangleInsets16);
        org.jfree.chart.block.EmptyBlock emptyBlock24 = new org.jfree.chart.block.EmptyBlock((-1.0d), 0.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets25 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double27 = rectangleInsets25.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock30 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D31 = emptyBlock30.getBounds();
        java.awt.geom.Rectangle2D rectangle2D34 = rectangleInsets25.createOutsetRectangle(rectangle2D31, true, false);
        java.awt.geom.Rectangle2D rectangle2D35 = emptyBlock24.trimBorder(rectangle2D31);
        java.awt.geom.Rectangle2D rectangle2D36 = emptyBlock24.getBounds();
        java.awt.geom.Rectangle2D rectangle2D39 = rectangleInsets16.createInsetRectangle(rectangle2D36, true, false);
        // The following exception was thrown during execution in test generation
        try {
            blockBorder9.draw(graphics2D10, rectangle2D39);
            org.junit.Assert.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
            // Expected exception.
        }
        org.junit.Assert.assertNotNull(blockBorder6);
        org.junit.Assert.assertNotNull(paint8);
        org.junit.Assert.assertTrue("'" + double14 + "' != '" + 0.0d + "'", double14 == 0.0d);
        org.junit.Assert.assertTrue("'" + double15 + "' != '" + 0.0d + "'", double15 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets16);
        org.junit.Assert.assertTrue("'" + double18 + "' != '" + 0.0d + "'", double18 == 0.0d);
        org.junit.Assert.assertTrue("'" + double20 + "' != '" + (-1.0d) + "'", double20 == (-1.0d));
        org.junit.Assert.assertTrue("'" + boolean21 + "' != '" + false + "'", boolean21 == false);
        org.junit.Assert.assertNotNull(rectangleInsets25);
        org.junit.Assert.assertTrue("'" + double27 + "' != '" + 0.0d + "'", double27 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D31);
        org.junit.Assert.assertNotNull(rectangle2D34);
        org.junit.Assert.assertNotNull(rectangle2D35);
        org.junit.Assert.assertNotNull(rectangle2D36);
        org.junit.Assert.assertNotNull(rectangle2D39);
    }

    @Test
    public void test549() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test549");
        org.jfree.chart.block.EmptyBlock emptyBlock2 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets3 = emptyBlock2.getPadding();
        org.jfree.chart.block.BorderArrangement borderArrangement12 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer13 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement12);
        org.jfree.chart.block.BlockBorder blockBorder14 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer13.setFrame((org.jfree.chart.block.BlockFrame) blockBorder14);
        java.awt.Paint paint16 = blockBorder14.getPaint();
        org.jfree.chart.block.BlockBorder blockBorder17 = new org.jfree.chart.block.BlockBorder((double) 10L, 0.0d, (double) 1.0f, (double) 10L, paint16);
        org.jfree.chart.block.BlockBorder blockBorder18 = new org.jfree.chart.block.BlockBorder(0.0d, (double) (short) -1, (-1.0d), (double) 0.0f, paint16);
        emptyBlock2.setFrame((org.jfree.chart.block.BlockFrame) blockBorder18);
        org.junit.Assert.assertNotNull(rectangleInsets3);
        org.junit.Assert.assertNotNull(blockBorder14);
        org.junit.Assert.assertNotNull(paint16);
    }

    @Test
    public void test550() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test550");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        java.util.List list2 = blockContainer1.getBlocks();
        java.awt.Graphics2D graphics2D3 = null;
        org.jfree.chart.util.Size2D size2D4 = blockContainer1.arrange(graphics2D3);
        java.util.List list5 = blockContainer1.getBlocks();
        java.awt.Graphics2D graphics2D6 = null;
        org.jfree.chart.util.RectangleInsets rectangleInsets7 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double9 = rectangleInsets7.calculateBottomInset((double) 0.0f);
        double double10 = rectangleInsets7.getBottom();
        double double11 = rectangleInsets7.getTop();
        org.jfree.chart.util.RectangleInsets rectangleInsets12 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double14 = rectangleInsets12.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock17 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D18 = emptyBlock17.getBounds();
        java.awt.geom.Rectangle2D rectangle2D21 = rectangleInsets12.createOutsetRectangle(rectangle2D18, true, false);
        java.awt.geom.Rectangle2D rectangle2D22 = rectangleInsets7.createInsetRectangle(rectangle2D18);
        // The following exception was thrown during execution in test generation
        try {
            blockContainer1.draw(graphics2D6, rectangle2D18);
            org.junit.Assert.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
            // Expected exception.
        }
        org.junit.Assert.assertNotNull(list2);
        org.junit.Assert.assertNotNull(size2D4);
        org.junit.Assert.assertNotNull(list5);
        org.junit.Assert.assertNotNull(rectangleInsets7);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + 0.0d + "'", double9 == 0.0d);
        org.junit.Assert.assertTrue("'" + double10 + "' != '" + 0.0d + "'", double10 == 0.0d);
        org.junit.Assert.assertTrue("'" + double11 + "' != '" + 0.0d + "'", double11 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets12);
        org.junit.Assert.assertTrue("'" + double14 + "' != '" + 0.0d + "'", double14 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D18);
        org.junit.Assert.assertNotNull(rectangle2D21);
        org.junit.Assert.assertNotNull(rectangle2D22);
    }

    @Test
    public void test551() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test551");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        java.util.List list2 = blockContainer1.getBlocks();
        java.util.List list3 = blockContainer1.getBlocks();
        boolean boolean4 = blockContainer1.isEmpty();
        org.junit.Assert.assertNotNull(list2);
        org.junit.Assert.assertNotNull(list3);
        org.junit.Assert.assertTrue("'" + boolean4 + "' != '" + true + "'", boolean4 == true);
    }

    @Test
    public void test552() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test552");
        org.jfree.chart.block.BorderArrangement borderArrangement8 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer9 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement8);
        org.jfree.chart.block.BlockBorder blockBorder10 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer9.setFrame((org.jfree.chart.block.BlockFrame) blockBorder10);
        java.awt.Paint paint12 = blockBorder10.getPaint();
        org.jfree.chart.block.BlockBorder blockBorder13 = new org.jfree.chart.block.BlockBorder((double) (-1L), 90.0d, (double) (byte) 0, (double) 100L, paint12);
        org.jfree.chart.block.BlockBorder blockBorder14 = new org.jfree.chart.block.BlockBorder((double) 1, (double) 100.0f, (double) 10, (double) '#', paint12);
        org.junit.Assert.assertNotNull(blockBorder10);
        org.junit.Assert.assertNotNull(paint12);
    }

    @Test
    public void test553() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test553");
        org.jfree.chart.block.BorderArrangement borderArrangement12 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer13 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement12);
        org.jfree.chart.block.BlockBorder blockBorder14 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer13.setFrame((org.jfree.chart.block.BlockFrame) blockBorder14);
        java.awt.Paint paint16 = blockBorder14.getPaint();
        org.jfree.chart.block.BlockBorder blockBorder17 = new org.jfree.chart.block.BlockBorder((double) 10L, 0.0d, (double) 1.0f, (double) 10L, paint16);
        org.jfree.chart.block.BlockBorder blockBorder18 = new org.jfree.chart.block.BlockBorder(0.0d, (double) (short) -1, (-1.0d), (double) 0.0f, paint16);
        org.jfree.chart.block.BlockBorder blockBorder19 = new org.jfree.chart.block.BlockBorder((double) 1L, 0.0d, 0.0d, (double) 100L, paint16);
        org.junit.Assert.assertNotNull(blockBorder14);
        org.junit.Assert.assertNotNull(paint16);
    }

    @Test
    public void test554() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test554");
        org.jfree.data.Range range0 = null;
        org.jfree.data.Range range2 = org.jfree.data.Range.expandToInclude(range0, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint4 = new org.jfree.chart.block.RectangleConstraint(range2, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint5 = rectangleConstraint4.toUnconstrainedWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint7 = rectangleConstraint5.toFixedHeight((double) (short) 0);
        org.jfree.data.Range range8 = null;
        org.jfree.data.Range range10 = org.jfree.data.Range.expandToInclude(range8, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint12 = new org.jfree.chart.block.RectangleConstraint(range10, (double) 1.0f);
        double double13 = range10.getUpperBound();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint14 = rectangleConstraint5.toRangeHeight(range10);
        org.jfree.data.Range range17 = org.jfree.data.Range.shift(range10, (double) (byte) -1, false);
        org.junit.Assert.assertNotNull(range2);
        org.junit.Assert.assertNotNull(rectangleConstraint5);
        org.junit.Assert.assertNotNull(rectangleConstraint7);
        org.junit.Assert.assertNotNull(range10);
        org.junit.Assert.assertTrue("'" + double13 + "' != '" + (-1.0d) + "'", double13 == (-1.0d));
        org.junit.Assert.assertNotNull(rectangleConstraint14);
        org.junit.Assert.assertNotNull(range17);
    }

    @Test
    public void test555() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test555");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        double double2 = blockContainer1.getContentXOffset();
        java.awt.Graphics2D graphics2D3 = null;
        org.jfree.chart.util.Size2D size2D4 = blockContainer1.arrange(graphics2D3);
        blockContainer1.setHeight((double) (-1));
        org.junit.Assert.assertTrue("'" + double2 + "' != '" + 0.0d + "'", double2 == 0.0d);
        org.junit.Assert.assertNotNull(size2D4);
    }

    @Test
    public void test556() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test556");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        borderArrangement0.clear();
        org.jfree.chart.block.BorderArrangement borderArrangement3 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer4 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement3);
        org.jfree.chart.block.BlockBorder blockBorder5 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer4.setFrame((org.jfree.chart.block.BlockFrame) blockBorder5);
        java.util.List list7 = blockContainer4.getBlocks();
        boolean boolean9 = blockContainer4.equals((java.lang.Object) "Range[-1.0,-1.0]");
        org.jfree.chart.util.RectangleInsets rectangleInsets10 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double12 = rectangleInsets10.calculateBottomInset((double) 0.0f);
        org.jfree.chart.util.RectangleInsets rectangleInsets13 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double15 = rectangleInsets13.calculateBottomInset((double) 0.0f);
        double double16 = rectangleInsets13.getBottom();
        double double17 = rectangleInsets13.getTop();
        org.jfree.chart.util.RectangleInsets rectangleInsets18 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double20 = rectangleInsets18.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock23 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D24 = emptyBlock23.getBounds();
        java.awt.geom.Rectangle2D rectangle2D27 = rectangleInsets18.createOutsetRectangle(rectangle2D24, true, false);
        java.awt.geom.Rectangle2D rectangle2D28 = rectangleInsets13.createInsetRectangle(rectangle2D24);
        java.awt.geom.Rectangle2D rectangle2D29 = rectangleInsets10.createInsetRectangle(rectangle2D28);
        boolean boolean30 = blockContainer4.equals((java.lang.Object) rectangle2D28);
        java.lang.Object obj31 = blockContainer4.clone();
        blockContainer4.setID("Size2D[width=0.0, height=0.0]");
        org.jfree.data.Range range34 = null;
        org.jfree.data.Range range36 = org.jfree.data.Range.expandToInclude(range34, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint38 = new org.jfree.chart.block.RectangleConstraint(range36, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint39 = rectangleConstraint38.toUnconstrainedWidth();
        org.jfree.data.Range range40 = rectangleConstraint39.getWidthRange();
        double double41 = range40.getUpperBound();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint43 = new org.jfree.chart.block.RectangleConstraint(range40, (double) (byte) 1);
        // The following exception was thrown during execution in test generation
        try {
            borderArrangement0.add((org.jfree.chart.block.Block) blockContainer4, (java.lang.Object) range40);
            org.junit.Assert.fail("Expected exception of type java.lang.ClassCastException; message: org.jfree.data.Range cannot be cast to org.jfree.chart.util.RectangleEdge");
        } catch (java.lang.ClassCastException e) {
            // Expected exception.
        }
        org.junit.Assert.assertNotNull(blockBorder5);
        org.junit.Assert.assertNotNull(list7);
        org.junit.Assert.assertTrue("'" + boolean9 + "' != '" + false + "'", boolean9 == false);
        org.junit.Assert.assertNotNull(rectangleInsets10);
        org.junit.Assert.assertTrue("'" + double12 + "' != '" + 0.0d + "'", double12 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets13);
        org.junit.Assert.assertTrue("'" + double15 + "' != '" + 0.0d + "'", double15 == 0.0d);
        org.junit.Assert.assertTrue("'" + double16 + "' != '" + 0.0d + "'", double16 == 0.0d);
        org.junit.Assert.assertTrue("'" + double17 + "' != '" + 0.0d + "'", double17 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets18);
        org.junit.Assert.assertTrue("'" + double20 + "' != '" + 0.0d + "'", double20 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D24);
        org.junit.Assert.assertNotNull(rectangle2D27);
        org.junit.Assert.assertNotNull(rectangle2D28);
        org.junit.Assert.assertNotNull(rectangle2D29);
        org.junit.Assert.assertTrue("'" + boolean30 + "' != '" + false + "'", boolean30 == false);
        org.junit.Assert.assertNotNull(obj31);
        org.junit.Assert.assertNotNull(range36);
        org.junit.Assert.assertNotNull(rectangleConstraint39);
        org.junit.Assert.assertNotNull(range40);
        org.junit.Assert.assertTrue("'" + double41 + "' != '" + (-1.0d) + "'", double41 == (-1.0d));
    }

    @Test
    public void test557() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test557");
        org.jfree.chart.block.RectangleConstraint rectangleConstraint2 = new org.jfree.chart.block.RectangleConstraint(110.0d, 35.0d);
    }

    @Test
    public void test558() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test558");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.BlockBorder blockBorder2 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder2);
        java.util.List list4 = blockContainer1.getBlocks();
        boolean boolean6 = blockContainer1.equals((java.lang.Object) "Range[-1.0,-1.0]");
        org.jfree.chart.util.Size2D size2D7 = new org.jfree.chart.util.Size2D();
        size2D7.setHeight((double) (short) 0);
        double double10 = size2D7.getWidth();
        double double11 = size2D7.getWidth();
        org.jfree.chart.util.RectangleInsets rectangleInsets12 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double14 = rectangleInsets12.calculateTopOutset((double) 1.0f);
        double double16 = rectangleInsets12.extendHeight((double) (byte) -1);
        boolean boolean17 = size2D7.equals((java.lang.Object) rectangleInsets12);
        org.jfree.chart.block.EmptyBlock emptyBlock20 = new org.jfree.chart.block.EmptyBlock((-1.0d), 0.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets21 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double23 = rectangleInsets21.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock26 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D27 = emptyBlock26.getBounds();
        java.awt.geom.Rectangle2D rectangle2D30 = rectangleInsets21.createOutsetRectangle(rectangle2D27, true, false);
        java.awt.geom.Rectangle2D rectangle2D31 = emptyBlock20.trimBorder(rectangle2D27);
        java.awt.geom.Rectangle2D rectangle2D32 = emptyBlock20.getBounds();
        java.awt.geom.Rectangle2D rectangle2D35 = rectangleInsets12.createInsetRectangle(rectangle2D32, true, false);
        java.awt.geom.Rectangle2D rectangle2D36 = blockContainer1.trimBorder(rectangle2D32);
        blockContainer1.setPadding((double) 1L, (double) '4', (double) 0, (double) (short) 1);
        java.awt.Graphics2D graphics2D42 = null;
        org.jfree.chart.util.Size2D size2D43 = blockContainer1.arrange(graphics2D42);
        java.awt.geom.Rectangle2D rectangle2D44 = blockContainer1.getBounds();
        org.junit.Assert.assertNotNull(blockBorder2);
        org.junit.Assert.assertNotNull(list4);
        org.junit.Assert.assertTrue("'" + boolean6 + "' != '" + false + "'", boolean6 == false);
        org.junit.Assert.assertTrue("'" + double10 + "' != '" + 0.0d + "'", double10 == 0.0d);
        org.junit.Assert.assertTrue("'" + double11 + "' != '" + 0.0d + "'", double11 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets12);
        org.junit.Assert.assertTrue("'" + double14 + "' != '" + 0.0d + "'", double14 == 0.0d);
        org.junit.Assert.assertTrue("'" + double16 + "' != '" + (-1.0d) + "'", double16 == (-1.0d));
        org.junit.Assert.assertTrue("'" + boolean17 + "' != '" + false + "'", boolean17 == false);
        org.junit.Assert.assertNotNull(rectangleInsets21);
        org.junit.Assert.assertTrue("'" + double23 + "' != '" + 0.0d + "'", double23 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D27);
        org.junit.Assert.assertNotNull(rectangle2D30);
        org.junit.Assert.assertNotNull(rectangle2D31);
        org.junit.Assert.assertNotNull(rectangle2D32);
        org.junit.Assert.assertNotNull(rectangle2D35);
        org.junit.Assert.assertNotNull(rectangle2D36);
        org.junit.Assert.assertNotNull(size2D43);
        org.junit.Assert.assertNotNull(rectangle2D44);
    }

    @Test
    public void test559() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test559");
        org.jfree.chart.block.BorderArrangement borderArrangement4 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer5 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement4);
        org.jfree.chart.block.BlockBorder blockBorder6 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer5.setFrame((org.jfree.chart.block.BlockFrame) blockBorder6);
        java.awt.Paint paint8 = blockBorder6.getPaint();
        org.jfree.chart.block.BlockBorder blockBorder9 = new org.jfree.chart.block.BlockBorder((double) (-1L), 90.0d, (double) (byte) 0, (double) 100L, paint8);
        java.awt.Paint paint10 = blockBorder9.getPaint();
        org.jfree.chart.util.RectangleInsets rectangleInsets11 = blockBorder9.getInsets();
        java.lang.Class<?> wildcardClass12 = blockBorder9.getClass();
        org.junit.Assert.assertNotNull(blockBorder6);
        org.junit.Assert.assertNotNull(paint8);
        org.junit.Assert.assertNotNull(paint10);
        org.junit.Assert.assertNotNull(rectangleInsets11);
        org.junit.Assert.assertNotNull(wildcardClass12);
    }

    @Test
    public void test560() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test560");
        org.jfree.chart.block.RectangleConstraint rectangleConstraint2 = new org.jfree.chart.block.RectangleConstraint(48.0d, 50.0d);
    }

    @Test
    public void test561() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test561");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        java.awt.geom.Rectangle2D rectangle2D2 = blockContainer1.getBounds();
        double double3 = blockContainer1.getWidth();
        org.jfree.chart.block.Arrangement arrangement4 = blockContainer1.getArrangement();
        org.jfree.chart.block.BlockBorder blockBorder5 = org.jfree.chart.block.BlockBorder.NONE;
        org.jfree.chart.util.RectangleEdge rectangleEdge6 = org.jfree.chart.util.RectangleEdge.RIGHT;
        org.jfree.chart.util.RectangleEdge rectangleEdge7 = org.jfree.chart.util.RectangleEdge.LEFT;
        boolean boolean8 = rectangleEdge6.equals((java.lang.Object) rectangleEdge7);
        boolean boolean9 = blockBorder5.equals((java.lang.Object) rectangleEdge6);
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder5);
        org.junit.Assert.assertNotNull(rectangle2D2);
        org.junit.Assert.assertTrue("'" + double3 + "' != '" + 0.0d + "'", double3 == 0.0d);
        org.junit.Assert.assertNotNull(arrangement4);
        org.junit.Assert.assertNotNull(blockBorder5);
        org.junit.Assert.assertNotNull(rectangleEdge6);
        org.junit.Assert.assertNotNull(rectangleEdge7);
        org.junit.Assert.assertTrue("'" + boolean8 + "' != '" + false + "'", boolean8 == false);
        org.junit.Assert.assertTrue("'" + boolean9 + "' != '" + false + "'", boolean9 == false);
    }

    @Test
    public void test562() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test562");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        java.util.List list2 = blockContainer1.getBlocks();
        blockContainer1.setMargin((double) ' ', (double) 100.0f, 0.0d, (double) 'a');
        org.jfree.chart.block.BorderArrangement borderArrangement8 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement8.clear();
        borderArrangement8.clear();
        borderArrangement8.clear();
        org.jfree.chart.block.BlockContainer blockContainer12 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement8);
        blockContainer1.add((org.jfree.chart.block.Block) blockContainer12);
        blockContainer1.setID("RectangleEdge.LEFT");
        org.jfree.data.Range range17 = null;
        org.jfree.data.Range range19 = org.jfree.data.Range.expandToInclude(range17, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint20 = new org.jfree.chart.block.RectangleConstraint(33.0d, range19);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint22 = rectangleConstraint20.toFixedWidth((double) 10);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint23 = blockContainer1.toContentConstraint(rectangleConstraint22);
        org.jfree.chart.block.EmptyBlock emptyBlock26 = new org.jfree.chart.block.EmptyBlock((-1.0d), 0.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets27 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double29 = rectangleInsets27.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock32 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D33 = emptyBlock32.getBounds();
        java.awt.geom.Rectangle2D rectangle2D36 = rectangleInsets27.createOutsetRectangle(rectangle2D33, true, false);
        java.awt.geom.Rectangle2D rectangle2D37 = emptyBlock26.trimBorder(rectangle2D33);
        java.awt.geom.Rectangle2D rectangle2D38 = emptyBlock26.getBounds();
        org.jfree.chart.util.RectangleInsets rectangleInsets39 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double41 = rectangleInsets39.calculateBottomInset((double) 0.0f);
        double double42 = rectangleInsets39.getBottom();
        emptyBlock26.setMargin(rectangleInsets39);
        double double45 = rectangleInsets39.calculateTopOutset(50.0d);
        blockContainer1.setPadding(rectangleInsets39);
        java.awt.Graphics2D graphics2D47 = null;
        org.jfree.data.Range range49 = null;
        org.jfree.data.Range range51 = org.jfree.data.Range.expandToInclude(range49, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint52 = new org.jfree.chart.block.RectangleConstraint(33.0d, range51);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint54 = rectangleConstraint52.toFixedWidth((double) 10);
        java.lang.String str55 = rectangleConstraint54.toString();
        org.jfree.chart.block.LengthConstraintType lengthConstraintType56 = rectangleConstraint54.getHeightConstraintType();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint58 = rectangleConstraint54.toFixedHeight((double) 0L);
        org.jfree.chart.block.EmptyBlock emptyBlock61 = new org.jfree.chart.block.EmptyBlock((-1.0d), 0.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets62 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double64 = rectangleInsets62.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock67 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D68 = emptyBlock67.getBounds();
        java.awt.geom.Rectangle2D rectangle2D71 = rectangleInsets62.createOutsetRectangle(rectangle2D68, true, false);
        java.awt.geom.Rectangle2D rectangle2D72 = emptyBlock61.trimBorder(rectangle2D68);
        java.awt.Graphics2D graphics2D73 = null;
        org.jfree.chart.util.Size2D size2D74 = emptyBlock61.arrange(graphics2D73);
        org.jfree.chart.block.BorderArrangement borderArrangement75 = new org.jfree.chart.block.BorderArrangement();
        boolean boolean76 = size2D74.equals((java.lang.Object) borderArrangement75);
        size2D74.setHeight(100.0d);
        size2D74.height = 0L;
        org.jfree.chart.util.Size2D size2D81 = rectangleConstraint58.calculateConstrainedSize(size2D74);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint82 = rectangleConstraint58.toUnconstrainedWidth();
        // The following exception was thrown during execution in test generation
        try {
            org.jfree.chart.util.Size2D size2D83 = blockContainer1.arrange(graphics2D47, rectangleConstraint82);
            org.junit.Assert.fail("Expected exception of type java.lang.RuntimeException; message: Not implemented.");
        } catch (java.lang.RuntimeException e) {
            // Expected exception.
        }
        org.junit.Assert.assertNotNull(list2);
        org.junit.Assert.assertNotNull(range19);
        org.junit.Assert.assertNotNull(rectangleConstraint22);
        org.junit.Assert.assertNotNull(rectangleConstraint23);
        org.junit.Assert.assertNotNull(rectangleInsets27);
        org.junit.Assert.assertTrue("'" + double29 + "' != '" + 0.0d + "'", double29 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D33);
        org.junit.Assert.assertNotNull(rectangle2D36);
        org.junit.Assert.assertNotNull(rectangle2D37);
        org.junit.Assert.assertNotNull(rectangle2D38);
        org.junit.Assert.assertNotNull(rectangleInsets39);
        org.junit.Assert.assertTrue("'" + double41 + "' != '" + 0.0d + "'", double41 == 0.0d);
        org.junit.Assert.assertTrue("'" + double42 + "' != '" + 0.0d + "'", double42 == 0.0d);
        org.junit.Assert.assertTrue("'" + double45 + "' != '" + 0.0d + "'", double45 == 0.0d);
        org.junit.Assert.assertNotNull(range51);
        org.junit.Assert.assertNotNull(rectangleConstraint54);
        org.junit.Assert.assertEquals("'" + str55 + "' != '" + "RectangleConstraint[LengthConstraintType.FIXED: width=10.0, height=0.0]" + "'", str55, "RectangleConstraint[LengthConstraintType.FIXED: width=10.0, height=0.0]");
        org.junit.Assert.assertNotNull(lengthConstraintType56);
        org.junit.Assert.assertNotNull(rectangleConstraint58);
        org.junit.Assert.assertNotNull(rectangleInsets62);
        org.junit.Assert.assertTrue("'" + double64 + "' != '" + 0.0d + "'", double64 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D68);
        org.junit.Assert.assertNotNull(rectangle2D71);
        org.junit.Assert.assertNotNull(rectangle2D72);
        org.junit.Assert.assertNotNull(size2D74);
        org.junit.Assert.assertTrue("'" + boolean76 + "' != '" + false + "'", boolean76 == false);
        org.junit.Assert.assertNotNull(size2D81);
        org.junit.Assert.assertNotNull(rectangleConstraint82);
    }

    @Test
    public void test563() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test563");
        org.jfree.chart.block.EmptyBlock emptyBlock2 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.lang.Object obj3 = emptyBlock2.clone();
        emptyBlock2.setMargin((double) (-1.0f), (double) (byte) 10, (double) 'a', (double) 0L);
        org.jfree.chart.util.RectangleInsets rectangleInsets9 = emptyBlock2.getPadding();
        java.awt.geom.Rectangle2D rectangle2D10 = emptyBlock2.getBounds();
        org.jfree.chart.block.BorderArrangement borderArrangement11 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer12 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement11);
        java.util.List list13 = blockContainer12.getBlocks();
        blockContainer12.setMargin((double) ' ', (double) 100.0f, 0.0d, (double) 'a');
        org.jfree.chart.util.RectangleInsets rectangleInsets19 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double21 = rectangleInsets19.calculateBottomInset((double) 0.0f);
        org.jfree.chart.util.UnitType unitType22 = rectangleInsets19.getUnitType();
        java.lang.String str23 = unitType22.toString();
        org.jfree.chart.block.BlockBorder blockBorder24 = org.jfree.chart.block.BlockBorder.NONE;
        boolean boolean25 = unitType22.equals((java.lang.Object) blockBorder24);
        blockContainer12.setFrame((org.jfree.chart.block.BlockFrame) blockBorder24);
        emptyBlock2.setFrame((org.jfree.chart.block.BlockFrame) blockBorder24);
        org.jfree.chart.util.RectangleInsets rectangleInsets28 = blockBorder24.getInsets();
        double double29 = rectangleInsets28.getBottom();
        org.junit.Assert.assertNotNull(obj3);
        org.junit.Assert.assertNotNull(rectangleInsets9);
        org.junit.Assert.assertNotNull(rectangle2D10);
        org.junit.Assert.assertNotNull(list13);
        org.junit.Assert.assertNotNull(rectangleInsets19);
        org.junit.Assert.assertTrue("'" + double21 + "' != '" + 0.0d + "'", double21 == 0.0d);
        org.junit.Assert.assertNotNull(unitType22);
        org.junit.Assert.assertEquals("'" + str23 + "' != '" + "UnitType.ABSOLUTE" + "'", str23, "UnitType.ABSOLUTE");
        org.junit.Assert.assertNotNull(blockBorder24);
        org.junit.Assert.assertTrue("'" + boolean25 + "' != '" + false + "'", boolean25 == false);
        org.junit.Assert.assertNotNull(rectangleInsets28);
        org.junit.Assert.assertTrue("'" + double29 + "' != '" + 0.0d + "'", double29 == 0.0d);
    }

    @Test
    public void test564() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test564");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.BlockBorder blockBorder2 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder2);
        java.util.List list4 = blockContainer1.getBlocks();
        blockContainer1.setPadding((double) 100.0f, 33.0d, (double) (-1L), (double) 1L);
        org.jfree.chart.block.BorderArrangement borderArrangement10 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer11 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement10);
        org.jfree.chart.block.BlockBorder blockBorder12 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer11.setFrame((org.jfree.chart.block.BlockFrame) blockBorder12);
        java.util.List list14 = blockContainer11.getBlocks();
        org.jfree.chart.block.EmptyBlock emptyBlock17 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.lang.Object obj18 = emptyBlock17.clone();
        emptyBlock17.setMargin((double) (-1.0f), (double) (byte) 10, (double) 'a', (double) 0L);
        org.jfree.chart.util.RectangleInsets rectangleInsets24 = emptyBlock17.getPadding();
        boolean boolean25 = blockContainer11.equals((java.lang.Object) emptyBlock17);
        org.jfree.chart.block.BlockFrame blockFrame26 = emptyBlock17.getFrame();
        java.awt.Graphics2D graphics2D27 = null;
        org.jfree.chart.block.RectangleConstraint rectangleConstraint30 = new org.jfree.chart.block.RectangleConstraint((double) (short) -1, (double) 100);
        double double31 = rectangleConstraint30.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint33 = rectangleConstraint30.toFixedHeight((double) 0);
        org.jfree.chart.util.Size2D size2D34 = emptyBlock17.arrange(graphics2D27, rectangleConstraint30);
        blockContainer1.add((org.jfree.chart.block.Block) emptyBlock17);
        double double37 = emptyBlock17.trimToContentWidth((double) 100L);
        org.jfree.chart.block.EmptyBlock emptyBlock40 = new org.jfree.chart.block.EmptyBlock(0.0d, (double) 10);
        org.jfree.chart.util.Size2D size2D41 = new org.jfree.chart.util.Size2D();
        size2D41.setHeight((double) (short) 0);
        double double44 = size2D41.getWidth();
        double double45 = size2D41.getWidth();
        org.jfree.chart.util.RectangleInsets rectangleInsets46 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double48 = rectangleInsets46.calculateTopOutset((double) 1.0f);
        double double50 = rectangleInsets46.extendHeight((double) (byte) -1);
        boolean boolean51 = size2D41.equals((java.lang.Object) rectangleInsets46);
        org.jfree.chart.util.UnitType unitType52 = rectangleInsets46.getUnitType();
        org.jfree.chart.util.RectangleInsets rectangleInsets57 = new org.jfree.chart.util.RectangleInsets(unitType52, (double) 1, (double) ' ', (double) (short) 0, 100.0d);
        double double59 = rectangleInsets57.trimHeight((double) (short) 0);
        emptyBlock40.setPadding(rectangleInsets57);
        emptyBlock17.setMargin(rectangleInsets57);
        emptyBlock17.setID("");
        org.junit.Assert.assertNotNull(blockBorder2);
        org.junit.Assert.assertNotNull(list4);
        org.junit.Assert.assertNotNull(blockBorder12);
        org.junit.Assert.assertNotNull(list14);
        org.junit.Assert.assertNotNull(obj18);
        org.junit.Assert.assertNotNull(rectangleInsets24);
        org.junit.Assert.assertTrue("'" + boolean25 + "' != '" + false + "'", boolean25 == false);
        org.junit.Assert.assertNotNull(blockFrame26);
        org.junit.Assert.assertTrue("'" + double31 + "' != '" + (-1.0d) + "'", double31 == (-1.0d));
        org.junit.Assert.assertNotNull(rectangleConstraint33);
        org.junit.Assert.assertNotNull(size2D34);
        org.junit.Assert.assertTrue("'" + double37 + "' != '" + 90.0d + "'", double37 == 90.0d);
        org.junit.Assert.assertTrue("'" + double44 + "' != '" + 0.0d + "'", double44 == 0.0d);
        org.junit.Assert.assertTrue("'" + double45 + "' != '" + 0.0d + "'", double45 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets46);
        org.junit.Assert.assertTrue("'" + double48 + "' != '" + 0.0d + "'", double48 == 0.0d);
        org.junit.Assert.assertTrue("'" + double50 + "' != '" + (-1.0d) + "'", double50 == (-1.0d));
        org.junit.Assert.assertTrue("'" + boolean51 + "' != '" + false + "'", boolean51 == false);
        org.junit.Assert.assertNotNull(unitType52);
        org.junit.Assert.assertTrue("'" + double59 + "' != '" + (-1.0d) + "'", double59 == (-1.0d));
    }

    @Test
    public void test565() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test565");
        org.jfree.data.Range range2 = new org.jfree.data.Range(0.0d, (double) '#');
    }

    @Test
    public void test566() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test566");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.BlockBorder blockBorder2 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder2);
        java.util.List list4 = blockContainer1.getBlocks();
        boolean boolean6 = blockContainer1.equals((java.lang.Object) "Range[-1.0,-1.0]");
        blockContainer1.clear();
        java.lang.Object obj8 = null;
        boolean boolean9 = blockContainer1.equals(obj8);
        java.awt.Graphics2D graphics2D10 = null;
        org.jfree.chart.util.RectangleInsets rectangleInsets11 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double13 = rectangleInsets11.calculateTopOutset((double) 1.0f);
        double double14 = rectangleInsets11.getRight();
        double double16 = rectangleInsets11.calculateBottomInset((double) 0);
        org.jfree.chart.block.BorderArrangement borderArrangement17 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer18 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement17);
        org.jfree.chart.block.BlockBorder blockBorder19 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer18.setFrame((org.jfree.chart.block.BlockFrame) blockBorder19);
        java.util.List list21 = blockContainer18.getBlocks();
        boolean boolean23 = blockContainer18.equals((java.lang.Object) "Range[-1.0,-1.0]");
        org.jfree.chart.util.RectangleInsets rectangleInsets24 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double26 = rectangleInsets24.calculateBottomInset((double) 0.0f);
        org.jfree.chart.util.RectangleInsets rectangleInsets27 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double29 = rectangleInsets27.calculateBottomInset((double) 0.0f);
        double double30 = rectangleInsets27.getBottom();
        double double31 = rectangleInsets27.getTop();
        org.jfree.chart.util.RectangleInsets rectangleInsets32 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double34 = rectangleInsets32.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock37 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D38 = emptyBlock37.getBounds();
        java.awt.geom.Rectangle2D rectangle2D41 = rectangleInsets32.createOutsetRectangle(rectangle2D38, true, false);
        java.awt.geom.Rectangle2D rectangle2D42 = rectangleInsets27.createInsetRectangle(rectangle2D38);
        java.awt.geom.Rectangle2D rectangle2D43 = rectangleInsets24.createInsetRectangle(rectangle2D42);
        boolean boolean44 = blockContainer18.equals((java.lang.Object) rectangle2D42);
        org.jfree.chart.util.RectangleEdge rectangleEdge45 = org.jfree.chart.util.RectangleEdge.RIGHT;
        org.jfree.chart.util.RectangleEdge rectangleEdge46 = org.jfree.chart.util.RectangleEdge.LEFT;
        boolean boolean47 = rectangleEdge45.equals((java.lang.Object) rectangleEdge46);
        boolean boolean48 = org.jfree.chart.util.RectangleEdge.isTopOrBottom(rectangleEdge46);
        double double49 = org.jfree.chart.util.RectangleEdge.coordinate(rectangle2D42, rectangleEdge46);
        java.awt.geom.Rectangle2D rectangle2D50 = rectangleInsets11.createInsetRectangle(rectangle2D42);
        org.jfree.chart.block.EmptyBlock emptyBlock53 = new org.jfree.chart.block.EmptyBlock((-1.0d), 0.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets54 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double56 = rectangleInsets54.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock59 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D60 = emptyBlock59.getBounds();
        java.awt.geom.Rectangle2D rectangle2D63 = rectangleInsets54.createOutsetRectangle(rectangle2D60, true, false);
        java.awt.geom.Rectangle2D rectangle2D64 = emptyBlock53.trimBorder(rectangle2D60);
        java.awt.Graphics2D graphics2D65 = null;
        org.jfree.chart.util.Size2D size2D66 = emptyBlock53.arrange(graphics2D65);
        java.awt.geom.Rectangle2D rectangle2D67 = emptyBlock53.getBounds();
        org.jfree.chart.util.RectangleInsets rectangleInsets68 = emptyBlock53.getPadding();
        org.jfree.chart.util.RectangleInsets rectangleInsets69 = new org.jfree.chart.util.RectangleInsets();
        double double70 = rectangleInsets69.getLeft();
        double double72 = rectangleInsets69.calculateTopInset((double) (byte) 10);
        double double73 = rectangleInsets69.getRight();
        emptyBlock53.setPadding(rectangleInsets69);
        double double76 = rectangleInsets69.calculateRightInset((double) (byte) -1);
        double double78 = rectangleInsets69.trimHeight(32.0d);
        // The following exception was thrown during execution in test generation
        try {
            java.lang.Object obj79 = blockContainer1.draw(graphics2D10, rectangle2D50, (java.lang.Object) 32.0d);
            org.junit.Assert.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
            // Expected exception.
        }
        org.junit.Assert.assertNotNull(blockBorder2);
        org.junit.Assert.assertNotNull(list4);
        org.junit.Assert.assertTrue("'" + boolean6 + "' != '" + false + "'", boolean6 == false);
        org.junit.Assert.assertTrue("'" + boolean9 + "' != '" + false + "'", boolean9 == false);
        org.junit.Assert.assertNotNull(rectangleInsets11);
        org.junit.Assert.assertTrue("'" + double13 + "' != '" + 0.0d + "'", double13 == 0.0d);
        org.junit.Assert.assertTrue("'" + double14 + "' != '" + 0.0d + "'", double14 == 0.0d);
        org.junit.Assert.assertTrue("'" + double16 + "' != '" + 0.0d + "'", double16 == 0.0d);
        org.junit.Assert.assertNotNull(blockBorder19);
        org.junit.Assert.assertNotNull(list21);
        org.junit.Assert.assertTrue("'" + boolean23 + "' != '" + false + "'", boolean23 == false);
        org.junit.Assert.assertNotNull(rectangleInsets24);
        org.junit.Assert.assertTrue("'" + double26 + "' != '" + 0.0d + "'", double26 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets27);
        org.junit.Assert.assertTrue("'" + double29 + "' != '" + 0.0d + "'", double29 == 0.0d);
        org.junit.Assert.assertTrue("'" + double30 + "' != '" + 0.0d + "'", double30 == 0.0d);
        org.junit.Assert.assertTrue("'" + double31 + "' != '" + 0.0d + "'", double31 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets32);
        org.junit.Assert.assertTrue("'" + double34 + "' != '" + 0.0d + "'", double34 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D38);
        org.junit.Assert.assertNotNull(rectangle2D41);
        org.junit.Assert.assertNotNull(rectangle2D42);
        org.junit.Assert.assertNotNull(rectangle2D43);
        org.junit.Assert.assertTrue("'" + boolean44 + "' != '" + false + "'", boolean44 == false);
        org.junit.Assert.assertNotNull(rectangleEdge45);
        org.junit.Assert.assertNotNull(rectangleEdge46);
        org.junit.Assert.assertTrue("'" + boolean47 + "' != '" + false + "'", boolean47 == false);
        org.junit.Assert.assertTrue("'" + boolean48 + "' != '" + false + "'", boolean48 == false);
        org.junit.Assert.assertTrue("'" + double49 + "' != '" + 0.0d + "'", double49 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D50);
        org.junit.Assert.assertNotNull(rectangleInsets54);
        org.junit.Assert.assertTrue("'" + double56 + "' != '" + 0.0d + "'", double56 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D60);
        org.junit.Assert.assertNotNull(rectangle2D63);
        org.junit.Assert.assertNotNull(rectangle2D64);
        org.junit.Assert.assertNotNull(size2D66);
        org.junit.Assert.assertNotNull(rectangle2D67);
        org.junit.Assert.assertNotNull(rectangleInsets68);
        org.junit.Assert.assertTrue("'" + double70 + "' != '" + 1.0d + "'", double70 == 1.0d);
        org.junit.Assert.assertTrue("'" + double72 + "' != '" + 1.0d + "'", double72 == 1.0d);
        org.junit.Assert.assertTrue("'" + double73 + "' != '" + 1.0d + "'", double73 == 1.0d);
        org.junit.Assert.assertTrue("'" + double76 + "' != '" + 1.0d + "'", double76 == 1.0d);
        org.junit.Assert.assertTrue("'" + double78 + "' != '" + 30.0d + "'", double78 == 30.0d);
    }

    @Test
    public void test567() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test567");
        org.jfree.chart.util.RectangleInsets rectangleInsets0 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double2 = rectangleInsets0.calculateBottomInset((double) 0.0f);
        double double3 = rectangleInsets0.getBottom();
        double double4 = rectangleInsets0.getTop();
        double double6 = rectangleInsets0.calculateTopInset(0.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets7 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double9 = rectangleInsets7.calculateBottomInset((double) 0.0f);
        org.jfree.chart.util.UnitType unitType10 = rectangleInsets7.getUnitType();
        org.jfree.chart.util.RectangleInsets rectangleInsets15 = new org.jfree.chart.util.RectangleInsets(unitType10, 50.0d, (double) (short) 100, 0.0d, (double) (short) -1);
        org.jfree.chart.block.BorderArrangement borderArrangement16 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer17 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement16);
        blockContainer17.setHeight((double) 10L);
        blockContainer17.clear();
        boolean boolean21 = unitType10.equals((java.lang.Object) blockContainer17);
        org.jfree.chart.util.RectangleInsets rectangleInsets26 = new org.jfree.chart.util.RectangleInsets((double) 0, (double) (short) 1, (double) (-1L), (double) '#');
        org.jfree.chart.block.EmptyBlock emptyBlock29 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.lang.Object obj30 = emptyBlock29.clone();
        emptyBlock29.setMargin((double) (-1.0f), (double) (byte) 10, (double) 'a', (double) 0L);
        org.jfree.chart.util.RectangleInsets rectangleInsets36 = emptyBlock29.getPadding();
        java.awt.geom.Rectangle2D rectangle2D37 = emptyBlock29.getBounds();
        java.awt.geom.Rectangle2D rectangle2D38 = emptyBlock29.getBounds();
        org.jfree.chart.util.LengthAdjustmentType lengthAdjustmentType39 = null;
        org.jfree.chart.util.LengthAdjustmentType lengthAdjustmentType40 = null;
        java.awt.geom.Rectangle2D rectangle2D41 = rectangleInsets26.createAdjustedRectangle(rectangle2D38, lengthAdjustmentType39, lengthAdjustmentType40);
        blockContainer17.setBounds(rectangle2D41);
        rectangleInsets0.trim(rectangle2D41);
        org.junit.Assert.assertNotNull(rectangleInsets0);
        org.junit.Assert.assertTrue("'" + double2 + "' != '" + 0.0d + "'", double2 == 0.0d);
        org.junit.Assert.assertTrue("'" + double3 + "' != '" + 0.0d + "'", double3 == 0.0d);
        org.junit.Assert.assertTrue("'" + double4 + "' != '" + 0.0d + "'", double4 == 0.0d);
        org.junit.Assert.assertTrue("'" + double6 + "' != '" + 0.0d + "'", double6 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets7);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + 0.0d + "'", double9 == 0.0d);
        org.junit.Assert.assertNotNull(unitType10);
        org.junit.Assert.assertTrue("'" + boolean21 + "' != '" + false + "'", boolean21 == false);
        org.junit.Assert.assertNotNull(obj30);
        org.junit.Assert.assertNotNull(rectangleInsets36);
        org.junit.Assert.assertNotNull(rectangle2D37);
        org.junit.Assert.assertNotNull(rectangle2D38);
        org.junit.Assert.assertNotNull(rectangle2D41);
    }

    @Test
    public void test568() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test568");
        org.jfree.chart.block.EmptyBlock emptyBlock2 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.lang.Object obj3 = emptyBlock2.clone();
        emptyBlock2.setMargin((double) (-1.0f), (double) (byte) 10, (double) 'a', (double) 0L);
        org.jfree.chart.util.RectangleInsets rectangleInsets9 = emptyBlock2.getPadding();
        java.awt.geom.Rectangle2D rectangle2D10 = emptyBlock2.getBounds();
        double double11 = emptyBlock2.getContentXOffset();
        org.junit.Assert.assertNotNull(obj3);
        org.junit.Assert.assertNotNull(rectangleInsets9);
        org.junit.Assert.assertNotNull(rectangle2D10);
        org.junit.Assert.assertTrue("'" + double11 + "' != '" + 10.0d + "'", double11 == 10.0d);
    }

    @Test
    public void test569() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test569");
        org.jfree.chart.util.Size2D size2D0 = new org.jfree.chart.util.Size2D();
        size2D0.setHeight((double) (short) 0);
        double double3 = size2D0.getWidth();
        double double4 = size2D0.getWidth();
        org.jfree.chart.util.RectangleInsets rectangleInsets5 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double7 = rectangleInsets5.calculateTopOutset((double) 1.0f);
        double double9 = rectangleInsets5.extendHeight((double) (byte) -1);
        boolean boolean10 = size2D0.equals((java.lang.Object) rectangleInsets5);
        double double12 = rectangleInsets5.extendHeight(0.0d);
        org.jfree.chart.block.BorderArrangement borderArrangement13 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement13.clear();
        borderArrangement13.clear();
        borderArrangement13.clear();
        org.jfree.chart.block.BlockContainer blockContainer17 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement13);
        double double18 = blockContainer17.getWidth();
        blockContainer17.clear();
        boolean boolean20 = rectangleInsets5.equals((java.lang.Object) blockContainer17);
        double double22 = rectangleInsets5.calculateRightInset((double) ' ');
        org.jfree.chart.util.RectangleInsets rectangleInsets23 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double25 = rectangleInsets23.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock28 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D29 = emptyBlock28.getBounds();
        java.awt.geom.Rectangle2D rectangle2D32 = rectangleInsets23.createOutsetRectangle(rectangle2D29, true, false);
        java.awt.geom.Rectangle2D rectangle2D35 = rectangleInsets5.createOutsetRectangle(rectangle2D32, false, true);
        org.junit.Assert.assertTrue("'" + double3 + "' != '" + 0.0d + "'", double3 == 0.0d);
        org.junit.Assert.assertTrue("'" + double4 + "' != '" + 0.0d + "'", double4 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets5);
        org.junit.Assert.assertTrue("'" + double7 + "' != '" + 0.0d + "'", double7 == 0.0d);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + (-1.0d) + "'", double9 == (-1.0d));
        org.junit.Assert.assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
        org.junit.Assert.assertTrue("'" + double12 + "' != '" + 0.0d + "'", double12 == 0.0d);
        org.junit.Assert.assertTrue("'" + double18 + "' != '" + 0.0d + "'", double18 == 0.0d);
        org.junit.Assert.assertTrue("'" + boolean20 + "' != '" + false + "'", boolean20 == false);
        org.junit.Assert.assertTrue("'" + double22 + "' != '" + 0.0d + "'", double22 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets23);
        org.junit.Assert.assertTrue("'" + double25 + "' != '" + 0.0d + "'", double25 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D29);
        org.junit.Assert.assertNotNull(rectangle2D32);
        org.junit.Assert.assertNotNull(rectangle2D35);
    }

    @Test
    public void test570() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test570");
        org.jfree.data.Range range1 = null;
        org.jfree.data.Range range3 = null;
        org.jfree.data.Range range5 = org.jfree.data.Range.expandToInclude(range3, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint7 = new org.jfree.chart.block.RectangleConstraint(range5, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint8 = rectangleConstraint7.toUnconstrainedWidth();
        org.jfree.data.Range range9 = rectangleConstraint8.getWidthRange();
        double double11 = range9.constrain((double) (byte) 1);
        org.jfree.chart.block.LengthConstraintType lengthConstraintType12 = org.jfree.chart.block.LengthConstraintType.FIXED;
        org.jfree.data.Range range14 = null;
        org.jfree.data.Range range16 = org.jfree.data.Range.expandToInclude(range14, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint18 = new org.jfree.chart.block.RectangleConstraint(range16, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint19 = rectangleConstraint18.toUnconstrainedWidth();
        org.jfree.data.Range range20 = rectangleConstraint19.getWidthRange();
        double double22 = range20.constrain((double) (byte) 1);
        org.jfree.data.Range range23 = null;
        org.jfree.data.Range range25 = org.jfree.data.Range.expandToInclude(range23, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint27 = new org.jfree.chart.block.RectangleConstraint(range25, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint28 = rectangleConstraint27.toUnconstrainedWidth();
        org.jfree.data.Range range29 = rectangleConstraint28.getWidthRange();
        double double30 = range29.getCentralValue();
        org.jfree.data.Range range31 = org.jfree.data.Range.combine(range20, range29);
        java.lang.String str32 = range31.toString();
        org.jfree.chart.block.LengthConstraintType lengthConstraintType33 = org.jfree.chart.block.LengthConstraintType.FIXED;
        org.jfree.chart.block.BorderArrangement borderArrangement34 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer35 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement34);
        java.awt.geom.Rectangle2D rectangle2D36 = blockContainer35.getBounds();
        org.jfree.chart.util.RectangleInsets rectangleInsets37 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double39 = rectangleInsets37.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock42 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D43 = emptyBlock42.getBounds();
        java.awt.geom.Rectangle2D rectangle2D46 = rectangleInsets37.createOutsetRectangle(rectangle2D43, true, false);
        blockContainer35.setBounds(rectangle2D46);
        boolean boolean48 = lengthConstraintType33.equals((java.lang.Object) blockContainer35);
        org.jfree.chart.util.RectangleInsets rectangleInsets49 = new org.jfree.chart.util.RectangleInsets();
        double double50 = rectangleInsets49.getTop();
        double double51 = rectangleInsets49.getLeft();
        boolean boolean52 = lengthConstraintType33.equals((java.lang.Object) rectangleInsets49);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint53 = new org.jfree.chart.block.RectangleConstraint((double) 10L, range9, lengthConstraintType12, (double) (short) 10, range31, lengthConstraintType33);
        org.jfree.data.Range range57 = null;
        org.jfree.chart.block.RectangleConstraint rectangleConstraint58 = new org.jfree.chart.block.RectangleConstraint(0.0d, range57);
        java.lang.String str59 = rectangleConstraint58.toString();
        org.jfree.data.Range range60 = null;
        org.jfree.data.Range range62 = org.jfree.data.Range.expandToInclude(range60, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint64 = new org.jfree.chart.block.RectangleConstraint(range62, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint65 = rectangleConstraint58.toRangeWidth(range62);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint66 = new org.jfree.chart.block.RectangleConstraint((double) 100, range62);
        org.jfree.data.Range range68 = null;
        org.jfree.data.Range range70 = org.jfree.data.Range.expandToInclude(range68, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint72 = new org.jfree.chart.block.RectangleConstraint(range70, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint73 = rectangleConstraint72.toUnconstrainedWidth();
        org.jfree.data.Range range74 = rectangleConstraint73.getWidthRange();
        double double76 = range74.constrain((double) (byte) 1);
        org.jfree.data.Range range77 = null;
        org.jfree.data.Range range79 = org.jfree.data.Range.expandToInclude(range77, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint81 = new org.jfree.chart.block.RectangleConstraint(range79, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint82 = rectangleConstraint81.toUnconstrainedWidth();
        org.jfree.data.Range range83 = rectangleConstraint82.getWidthRange();
        double double84 = range83.getCentralValue();
        org.jfree.data.Range range85 = org.jfree.data.Range.combine(range74, range83);
        double double86 = range83.getCentralValue();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint87 = new org.jfree.chart.block.RectangleConstraint((double) (byte) 100, range83);
        org.jfree.data.Range range90 = org.jfree.data.Range.expand(range83, 0.0d, (double) 1L);
        double double92 = range90.constrain((double) (short) 10);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint94 = new org.jfree.chart.block.RectangleConstraint(range90, (double) 1L);
        org.jfree.chart.block.LengthConstraintType lengthConstraintType95 = rectangleConstraint94.getHeightConstraintType();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint96 = new org.jfree.chart.block.RectangleConstraint(0.0d, range1, lengthConstraintType12, (double) 'a', range62, lengthConstraintType95);
        java.lang.String str97 = lengthConstraintType12.toString();
        org.junit.Assert.assertNotNull(range5);
        org.junit.Assert.assertNotNull(rectangleConstraint8);
        org.junit.Assert.assertNotNull(range9);
        org.junit.Assert.assertTrue("'" + double11 + "' != '" + (-1.0d) + "'", double11 == (-1.0d));
        org.junit.Assert.assertNotNull(lengthConstraintType12);
        org.junit.Assert.assertNotNull(range16);
        org.junit.Assert.assertNotNull(rectangleConstraint19);
        org.junit.Assert.assertNotNull(range20);
        org.junit.Assert.assertTrue("'" + double22 + "' != '" + (-1.0d) + "'", double22 == (-1.0d));
        org.junit.Assert.assertNotNull(range25);
        org.junit.Assert.assertNotNull(rectangleConstraint28);
        org.junit.Assert.assertNotNull(range29);
        org.junit.Assert.assertTrue("'" + double30 + "' != '" + (-1.0d) + "'", double30 == (-1.0d));
        org.junit.Assert.assertNotNull(range31);
        org.junit.Assert.assertEquals("'" + str32 + "' != '" + "Range[-1.0,-1.0]" + "'", str32, "Range[-1.0,-1.0]");
        org.junit.Assert.assertNotNull(lengthConstraintType33);
        org.junit.Assert.assertNotNull(rectangle2D36);
        org.junit.Assert.assertNotNull(rectangleInsets37);
        org.junit.Assert.assertTrue("'" + double39 + "' != '" + 0.0d + "'", double39 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D43);
        org.junit.Assert.assertNotNull(rectangle2D46);
        org.junit.Assert.assertTrue("'" + boolean48 + "' != '" + false + "'", boolean48 == false);
        org.junit.Assert.assertTrue("'" + double50 + "' != '" + 1.0d + "'", double50 == 1.0d);
        org.junit.Assert.assertTrue("'" + double51 + "' != '" + 1.0d + "'", double51 == 1.0d);
        org.junit.Assert.assertTrue("'" + boolean52 + "' != '" + false + "'", boolean52 == false);
        org.junit.Assert.assertEquals("'" + str59 + "' != '" + "RectangleConstraint[LengthConstraintType.FIXED: width=0.0, height=0.0]" + "'", str59, "RectangleConstraint[LengthConstraintType.FIXED: width=0.0, height=0.0]");
        org.junit.Assert.assertNotNull(range62);
        org.junit.Assert.assertNotNull(rectangleConstraint65);
        org.junit.Assert.assertNotNull(range70);
        org.junit.Assert.assertNotNull(rectangleConstraint73);
        org.junit.Assert.assertNotNull(range74);
        org.junit.Assert.assertTrue("'" + double76 + "' != '" + (-1.0d) + "'", double76 == (-1.0d));
        org.junit.Assert.assertNotNull(range79);
        org.junit.Assert.assertNotNull(rectangleConstraint82);
        org.junit.Assert.assertNotNull(range83);
        org.junit.Assert.assertTrue("'" + double84 + "' != '" + (-1.0d) + "'", double84 == (-1.0d));
        org.junit.Assert.assertNotNull(range85);
        org.junit.Assert.assertTrue("'" + double86 + "' != '" + (-1.0d) + "'", double86 == (-1.0d));
        org.junit.Assert.assertNotNull(range90);
        org.junit.Assert.assertTrue("'" + double92 + "' != '" + (-1.0d) + "'", double92 == (-1.0d));
        org.junit.Assert.assertNotNull(lengthConstraintType95);
        org.junit.Assert.assertEquals("'" + str97 + "' != '" + "LengthConstraintType.FIXED" + "'", str97, "LengthConstraintType.FIXED");
    }

    @Test
    public void test571() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test571");
        // The following exception was thrown during execution in test generation
        try {
            org.jfree.data.Range range2 = new org.jfree.data.Range((double) 100, (double) (byte) -1);
            org.junit.Assert.fail("Expected exception of type java.lang.IllegalArgumentException; message: Range(double, double): require lower (100.0) <= upper (-1.0).");
        } catch (java.lang.IllegalArgumentException e) {
            // Expected exception.
        }
    }

    @Test
    public void test572() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test572");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement0.clear();
        borderArrangement0.clear();
        borderArrangement0.clear();
        org.jfree.chart.block.BlockContainer blockContainer4 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        double double5 = blockContainer4.getWidth();
        blockContainer4.clear();
        java.awt.Graphics2D graphics2D7 = null;
        org.jfree.chart.util.Size2D size2D8 = blockContainer4.arrange(graphics2D7);
        org.jfree.chart.block.Arrangement arrangement9 = blockContainer4.getArrangement();
        blockContainer4.setHeight((double) (-1L));
        org.junit.Assert.assertTrue("'" + double5 + "' != '" + 0.0d + "'", double5 == 0.0d);
        org.junit.Assert.assertNotNull(size2D8);
        org.junit.Assert.assertNotNull(arrangement9);
    }

    @Test
    public void test573() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test573");
        org.jfree.data.Range range1 = null;
        org.jfree.data.Range range3 = org.jfree.data.Range.expandToInclude(range1, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint4 = new org.jfree.chart.block.RectangleConstraint(33.0d, range3);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint6 = rectangleConstraint4.toFixedWidth((double) 10);
        java.lang.String str7 = rectangleConstraint6.toString();
        org.jfree.chart.block.LengthConstraintType lengthConstraintType8 = rectangleConstraint6.getHeightConstraintType();
        org.jfree.data.Range range9 = rectangleConstraint6.getWidthRange();
        org.junit.Assert.assertNotNull(range3);
        org.junit.Assert.assertNotNull(rectangleConstraint6);
        org.junit.Assert.assertEquals("'" + str7 + "' != '" + "RectangleConstraint[LengthConstraintType.FIXED: width=10.0, height=0.0]" + "'", str7, "RectangleConstraint[LengthConstraintType.FIXED: width=10.0, height=0.0]");
        org.junit.Assert.assertNotNull(lengthConstraintType8);
        org.junit.Assert.assertNull(range9);
    }

    @Test
    public void test574() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test574");
        org.jfree.chart.block.BorderArrangement borderArrangement8 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer9 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement8);
        org.jfree.chart.block.BlockBorder blockBorder10 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer9.setFrame((org.jfree.chart.block.BlockFrame) blockBorder10);
        java.awt.Paint paint12 = blockBorder10.getPaint();
        org.jfree.chart.block.BlockBorder blockBorder13 = new org.jfree.chart.block.BlockBorder((double) 10L, 0.0d, (double) 1.0f, (double) 10L, paint12);
        org.jfree.chart.block.BlockBorder blockBorder14 = new org.jfree.chart.block.BlockBorder(0.0d, (double) (short) -1, (-1.0d), (double) 0.0f, paint12);
        org.jfree.chart.block.BlockBorder blockBorder15 = new org.jfree.chart.block.BlockBorder(paint12);
        java.awt.Graphics2D graphics2D16 = null;
        org.jfree.data.Range range17 = null;
        org.jfree.data.Range range19 = org.jfree.data.Range.expandToInclude(range17, (double) (short) -1);
        double double21 = range19.constrain((double) (byte) -1);
        org.jfree.data.Range range23 = org.jfree.data.Range.expandToInclude(range19, (double) 100L);
        org.jfree.chart.block.BorderArrangement borderArrangement24 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement24.clear();
        borderArrangement24.clear();
        borderArrangement24.clear();
        org.jfree.chart.block.BlockContainer blockContainer28 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement24);
        double double29 = blockContainer28.getWidth();
        java.util.List list30 = blockContainer28.getBlocks();
        org.jfree.chart.block.Arrangement arrangement31 = blockContainer28.getArrangement();
        org.jfree.chart.util.RectangleInsets rectangleInsets32 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double34 = rectangleInsets32.calculateBottomInset((double) 0.0f);
        org.jfree.chart.util.RectangleInsets rectangleInsets35 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double37 = rectangleInsets35.calculateBottomInset((double) 0.0f);
        double double38 = rectangleInsets35.getBottom();
        double double39 = rectangleInsets35.getTop();
        org.jfree.chart.util.RectangleInsets rectangleInsets40 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double42 = rectangleInsets40.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock45 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D46 = emptyBlock45.getBounds();
        java.awt.geom.Rectangle2D rectangle2D49 = rectangleInsets40.createOutsetRectangle(rectangle2D46, true, false);
        java.awt.geom.Rectangle2D rectangle2D50 = rectangleInsets35.createInsetRectangle(rectangle2D46);
        java.awt.geom.Rectangle2D rectangle2D51 = rectangleInsets32.createInsetRectangle(rectangle2D50);
        java.awt.geom.Rectangle2D rectangle2D52 = blockContainer28.trimMargin(rectangle2D51);
        boolean boolean53 = range19.equals((java.lang.Object) rectangle2D51);
        // The following exception was thrown during execution in test generation
        try {
            blockBorder15.draw(graphics2D16, rectangle2D51);
            org.junit.Assert.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
            // Expected exception.
        }
        org.junit.Assert.assertNotNull(blockBorder10);
        org.junit.Assert.assertNotNull(paint12);
        org.junit.Assert.assertNotNull(range19);
        org.junit.Assert.assertTrue("'" + double21 + "' != '" + (-1.0d) + "'", double21 == (-1.0d));
        org.junit.Assert.assertNotNull(range23);
        org.junit.Assert.assertTrue("'" + double29 + "' != '" + 0.0d + "'", double29 == 0.0d);
        org.junit.Assert.assertNotNull(list30);
        org.junit.Assert.assertNotNull(arrangement31);
        org.junit.Assert.assertNotNull(rectangleInsets32);
        org.junit.Assert.assertTrue("'" + double34 + "' != '" + 0.0d + "'", double34 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets35);
        org.junit.Assert.assertTrue("'" + double37 + "' != '" + 0.0d + "'", double37 == 0.0d);
        org.junit.Assert.assertTrue("'" + double38 + "' != '" + 0.0d + "'", double38 == 0.0d);
        org.junit.Assert.assertTrue("'" + double39 + "' != '" + 0.0d + "'", double39 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets40);
        org.junit.Assert.assertTrue("'" + double42 + "' != '" + 0.0d + "'", double42 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D46);
        org.junit.Assert.assertNotNull(rectangle2D49);
        org.junit.Assert.assertNotNull(rectangle2D50);
        org.junit.Assert.assertNotNull(rectangle2D51);
        org.junit.Assert.assertNotNull(rectangle2D52);
        org.junit.Assert.assertTrue("'" + boolean53 + "' != '" + false + "'", boolean53 == false);
    }

    @Test
    public void test575() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test575");
        org.jfree.data.Range range1 = null;
        org.jfree.data.Range range3 = org.jfree.data.Range.expandToInclude(range1, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint5 = new org.jfree.chart.block.RectangleConstraint(range3, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint6 = rectangleConstraint5.toUnconstrainedWidth();
        org.jfree.data.Range range7 = rectangleConstraint6.getWidthRange();
        double double9 = range7.constrain((double) (byte) 1);
        org.jfree.chart.block.LengthConstraintType lengthConstraintType10 = org.jfree.chart.block.LengthConstraintType.FIXED;
        org.jfree.data.Range range12 = null;
        org.jfree.data.Range range14 = org.jfree.data.Range.expandToInclude(range12, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint16 = new org.jfree.chart.block.RectangleConstraint(range14, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint17 = rectangleConstraint16.toUnconstrainedWidth();
        org.jfree.data.Range range18 = rectangleConstraint17.getWidthRange();
        double double20 = range18.constrain((double) (byte) 1);
        org.jfree.data.Range range21 = null;
        org.jfree.data.Range range23 = org.jfree.data.Range.expandToInclude(range21, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint25 = new org.jfree.chart.block.RectangleConstraint(range23, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint26 = rectangleConstraint25.toUnconstrainedWidth();
        org.jfree.data.Range range27 = rectangleConstraint26.getWidthRange();
        double double28 = range27.getCentralValue();
        org.jfree.data.Range range29 = org.jfree.data.Range.combine(range18, range27);
        java.lang.String str30 = range29.toString();
        org.jfree.chart.block.LengthConstraintType lengthConstraintType31 = org.jfree.chart.block.LengthConstraintType.FIXED;
        org.jfree.chart.block.BorderArrangement borderArrangement32 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer33 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement32);
        java.awt.geom.Rectangle2D rectangle2D34 = blockContainer33.getBounds();
        org.jfree.chart.util.RectangleInsets rectangleInsets35 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double37 = rectangleInsets35.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock40 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D41 = emptyBlock40.getBounds();
        java.awt.geom.Rectangle2D rectangle2D44 = rectangleInsets35.createOutsetRectangle(rectangle2D41, true, false);
        blockContainer33.setBounds(rectangle2D44);
        boolean boolean46 = lengthConstraintType31.equals((java.lang.Object) blockContainer33);
        org.jfree.chart.util.RectangleInsets rectangleInsets47 = new org.jfree.chart.util.RectangleInsets();
        double double48 = rectangleInsets47.getTop();
        double double49 = rectangleInsets47.getLeft();
        boolean boolean50 = lengthConstraintType31.equals((java.lang.Object) rectangleInsets47);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint51 = new org.jfree.chart.block.RectangleConstraint((double) 10L, range7, lengthConstraintType10, (double) (short) 10, range29, lengthConstraintType31);
        org.jfree.data.Range range52 = null;
        org.jfree.data.Range range53 = org.jfree.data.Range.combine(range7, range52);
        org.jfree.data.Range range54 = null;
        org.jfree.data.Range range56 = org.jfree.data.Range.expandToInclude(range54, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint58 = new org.jfree.chart.block.RectangleConstraint(range56, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint59 = rectangleConstraint58.toUnconstrainedWidth();
        double double60 = rectangleConstraint59.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint61 = rectangleConstraint59.toUnconstrainedHeight();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint63 = rectangleConstraint61.toFixedHeight((double) 100.0f);
        org.jfree.data.Range range65 = null;
        org.jfree.data.Range range67 = org.jfree.data.Range.expandToInclude(range65, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint69 = new org.jfree.chart.block.RectangleConstraint(range67, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint70 = rectangleConstraint69.toUnconstrainedWidth();
        org.jfree.data.Range range71 = rectangleConstraint70.getWidthRange();
        double double73 = range71.constrain((double) (byte) 1);
        org.jfree.data.Range range74 = null;
        org.jfree.data.Range range76 = org.jfree.data.Range.expandToInclude(range74, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint78 = new org.jfree.chart.block.RectangleConstraint(range76, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint79 = rectangleConstraint78.toUnconstrainedWidth();
        org.jfree.data.Range range80 = rectangleConstraint79.getWidthRange();
        double double81 = range80.getCentralValue();
        org.jfree.data.Range range82 = org.jfree.data.Range.combine(range71, range80);
        double double83 = range80.getCentralValue();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint84 = new org.jfree.chart.block.RectangleConstraint((double) (byte) 100, range80);
        org.jfree.data.Range range87 = org.jfree.data.Range.expand(range80, 0.0d, (double) 1L);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint88 = rectangleConstraint61.toRangeWidth(range80);
        org.jfree.data.Range range89 = org.jfree.data.Range.combine(range7, range80);
        boolean boolean92 = range80.intersects((double) (-1), (double) (short) 100);
        org.junit.Assert.assertNotNull(range3);
        org.junit.Assert.assertNotNull(rectangleConstraint6);
        org.junit.Assert.assertNotNull(range7);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + (-1.0d) + "'", double9 == (-1.0d));
        org.junit.Assert.assertNotNull(lengthConstraintType10);
        org.junit.Assert.assertNotNull(range14);
        org.junit.Assert.assertNotNull(rectangleConstraint17);
        org.junit.Assert.assertNotNull(range18);
        org.junit.Assert.assertTrue("'" + double20 + "' != '" + (-1.0d) + "'", double20 == (-1.0d));
        org.junit.Assert.assertNotNull(range23);
        org.junit.Assert.assertNotNull(rectangleConstraint26);
        org.junit.Assert.assertNotNull(range27);
        org.junit.Assert.assertTrue("'" + double28 + "' != '" + (-1.0d) + "'", double28 == (-1.0d));
        org.junit.Assert.assertNotNull(range29);
        org.junit.Assert.assertEquals("'" + str30 + "' != '" + "Range[-1.0,-1.0]" + "'", str30, "Range[-1.0,-1.0]");
        org.junit.Assert.assertNotNull(lengthConstraintType31);
        org.junit.Assert.assertNotNull(rectangle2D34);
        org.junit.Assert.assertNotNull(rectangleInsets35);
        org.junit.Assert.assertTrue("'" + double37 + "' != '" + 0.0d + "'", double37 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D41);
        org.junit.Assert.assertNotNull(rectangle2D44);
        org.junit.Assert.assertTrue("'" + boolean46 + "' != '" + false + "'", boolean46 == false);
        org.junit.Assert.assertTrue("'" + double48 + "' != '" + 1.0d + "'", double48 == 1.0d);
        org.junit.Assert.assertTrue("'" + double49 + "' != '" + 1.0d + "'", double49 == 1.0d);
        org.junit.Assert.assertTrue("'" + boolean50 + "' != '" + false + "'", boolean50 == false);
        org.junit.Assert.assertNotNull(range53);
        org.junit.Assert.assertNotNull(range56);
        org.junit.Assert.assertNotNull(rectangleConstraint59);
        org.junit.Assert.assertTrue("'" + double60 + "' != '" + 0.0d + "'", double60 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleConstraint61);
        org.junit.Assert.assertNotNull(rectangleConstraint63);
        org.junit.Assert.assertNotNull(range67);
        org.junit.Assert.assertNotNull(rectangleConstraint70);
        org.junit.Assert.assertNotNull(range71);
        org.junit.Assert.assertTrue("'" + double73 + "' != '" + (-1.0d) + "'", double73 == (-1.0d));
        org.junit.Assert.assertNotNull(range76);
        org.junit.Assert.assertNotNull(rectangleConstraint79);
        org.junit.Assert.assertNotNull(range80);
        org.junit.Assert.assertTrue("'" + double81 + "' != '" + (-1.0d) + "'", double81 == (-1.0d));
        org.junit.Assert.assertNotNull(range82);
        org.junit.Assert.assertTrue("'" + double83 + "' != '" + (-1.0d) + "'", double83 == (-1.0d));
        org.junit.Assert.assertNotNull(range87);
        org.junit.Assert.assertNotNull(rectangleConstraint88);
        org.junit.Assert.assertNotNull(range89);
        org.junit.Assert.assertTrue("'" + boolean92 + "' != '" + true + "'", boolean92 == true);
    }

    @Test
    public void test576() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test576");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.BlockBorder blockBorder2 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder2);
        java.util.List list4 = blockContainer1.getBlocks();
        double double6 = blockContainer1.calculateTotalWidth(33.0d);
        java.awt.Graphics2D graphics2D7 = null;
        org.jfree.chart.util.UnitType unitType8 = org.jfree.chart.util.UnitType.RELATIVE;
        org.jfree.chart.block.RectangleConstraint rectangleConstraint11 = new org.jfree.chart.block.RectangleConstraint((double) (short) -1, (double) 100);
        double double12 = rectangleConstraint11.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint14 = rectangleConstraint11.toFixedHeight((double) 0);
        boolean boolean15 = unitType8.equals((java.lang.Object) rectangleConstraint14);
        org.jfree.data.Range range16 = rectangleConstraint14.getWidthRange();
        org.jfree.chart.util.Size2D size2D17 = blockContainer1.arrange(graphics2D7, rectangleConstraint14);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint19 = rectangleConstraint14.toFixedHeight(32.0d);
        org.junit.Assert.assertNotNull(blockBorder2);
        org.junit.Assert.assertNotNull(list4);
        org.junit.Assert.assertTrue("'" + double6 + "' != '" + 33.0d + "'", double6 == 33.0d);
        org.junit.Assert.assertNotNull(unitType8);
        org.junit.Assert.assertTrue("'" + double12 + "' != '" + (-1.0d) + "'", double12 == (-1.0d));
        org.junit.Assert.assertNotNull(rectangleConstraint14);
        org.junit.Assert.assertTrue("'" + boolean15 + "' != '" + false + "'", boolean15 == false);
        org.junit.Assert.assertNull(range16);
        org.junit.Assert.assertNotNull(size2D17);
        org.junit.Assert.assertNotNull(rectangleConstraint19);
    }

    @Test
    public void test577() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test577");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        java.util.List list2 = blockContainer1.getBlocks();
        blockContainer1.setMargin((double) ' ', (double) 100.0f, 0.0d, (double) 'a');
        org.jfree.chart.block.BorderArrangement borderArrangement8 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement8.clear();
        borderArrangement8.clear();
        borderArrangement8.clear();
        org.jfree.chart.block.BlockContainer blockContainer12 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement8);
        blockContainer1.add((org.jfree.chart.block.Block) blockContainer12);
        blockContainer1.setID("RectangleEdge.LEFT");
        java.awt.geom.Rectangle2D rectangle2D16 = blockContainer1.getBounds();
        org.jfree.chart.block.BlockBorder blockBorder21 = new org.jfree.chart.block.BlockBorder((double) (-1L), (double) (short) -1, 100.0d, (double) (short) 10);
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder21);
        org.junit.Assert.assertNotNull(list2);
        org.junit.Assert.assertNotNull(rectangle2D16);
    }

    @Test
    public void test578() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test578");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.BlockBorder blockBorder2 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder2);
        java.util.List list4 = blockContainer1.getBlocks();
        boolean boolean6 = blockContainer1.equals((java.lang.Object) "Range[-1.0,-1.0]");
        org.jfree.chart.util.Size2D size2D7 = new org.jfree.chart.util.Size2D();
        size2D7.setHeight((double) (short) 0);
        double double10 = size2D7.getWidth();
        double double11 = size2D7.getWidth();
        org.jfree.chart.util.RectangleInsets rectangleInsets12 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double14 = rectangleInsets12.calculateTopOutset((double) 1.0f);
        double double16 = rectangleInsets12.extendHeight((double) (byte) -1);
        boolean boolean17 = size2D7.equals((java.lang.Object) rectangleInsets12);
        org.jfree.chart.block.EmptyBlock emptyBlock20 = new org.jfree.chart.block.EmptyBlock((-1.0d), 0.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets21 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double23 = rectangleInsets21.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock26 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D27 = emptyBlock26.getBounds();
        java.awt.geom.Rectangle2D rectangle2D30 = rectangleInsets21.createOutsetRectangle(rectangle2D27, true, false);
        java.awt.geom.Rectangle2D rectangle2D31 = emptyBlock20.trimBorder(rectangle2D27);
        java.awt.geom.Rectangle2D rectangle2D32 = emptyBlock20.getBounds();
        java.awt.geom.Rectangle2D rectangle2D35 = rectangleInsets12.createInsetRectangle(rectangle2D32, true, false);
        java.awt.geom.Rectangle2D rectangle2D36 = blockContainer1.trimBorder(rectangle2D32);
        blockContainer1.setPadding((double) 1L, (double) '4', (double) 0, (double) (short) 1);
        java.awt.Graphics2D graphics2D42 = null;
        org.jfree.chart.util.Size2D size2D43 = blockContainer1.arrange(graphics2D42);
        blockContainer1.setMargin((double) 100.0f, (double) 0, (double) (byte) -1, 0.0d);
        boolean boolean49 = blockContainer1.isEmpty();
        org.junit.Assert.assertNotNull(blockBorder2);
        org.junit.Assert.assertNotNull(list4);
        org.junit.Assert.assertTrue("'" + boolean6 + "' != '" + false + "'", boolean6 == false);
        org.junit.Assert.assertTrue("'" + double10 + "' != '" + 0.0d + "'", double10 == 0.0d);
        org.junit.Assert.assertTrue("'" + double11 + "' != '" + 0.0d + "'", double11 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets12);
        org.junit.Assert.assertTrue("'" + double14 + "' != '" + 0.0d + "'", double14 == 0.0d);
        org.junit.Assert.assertTrue("'" + double16 + "' != '" + (-1.0d) + "'", double16 == (-1.0d));
        org.junit.Assert.assertTrue("'" + boolean17 + "' != '" + false + "'", boolean17 == false);
        org.junit.Assert.assertNotNull(rectangleInsets21);
        org.junit.Assert.assertTrue("'" + double23 + "' != '" + 0.0d + "'", double23 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D27);
        org.junit.Assert.assertNotNull(rectangle2D30);
        org.junit.Assert.assertNotNull(rectangle2D31);
        org.junit.Assert.assertNotNull(rectangle2D32);
        org.junit.Assert.assertNotNull(rectangle2D35);
        org.junit.Assert.assertNotNull(rectangle2D36);
        org.junit.Assert.assertNotNull(size2D43);
        org.junit.Assert.assertTrue("'" + boolean49 + "' != '" + true + "'", boolean49 == true);
    }

    @Test
    public void test579() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test579");
        org.jfree.chart.util.RectangleInsets rectangleInsets0 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double2 = rectangleInsets0.calculateTopOutset((double) 1L);
        double double4 = rectangleInsets0.trimHeight((double) 1.0f);
        double double5 = rectangleInsets0.getTop();
        org.junit.Assert.assertNotNull(rectangleInsets0);
        org.junit.Assert.assertTrue("'" + double2 + "' != '" + 0.0d + "'", double2 == 0.0d);
        org.junit.Assert.assertTrue("'" + double4 + "' != '" + 1.0d + "'", double4 == 1.0d);
        org.junit.Assert.assertTrue("'" + double5 + "' != '" + 0.0d + "'", double5 == 0.0d);
    }

    @Test
    public void test580() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test580");
        org.jfree.data.Range range0 = null;
        org.jfree.data.Range range2 = org.jfree.data.Range.expandToInclude(range0, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint4 = new org.jfree.chart.block.RectangleConstraint(range2, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint5 = rectangleConstraint4.toUnconstrainedWidth();
        org.jfree.data.Range range6 = rectangleConstraint5.getWidthRange();
        double double8 = range6.constrain((double) (byte) 1);
        org.jfree.data.Range range9 = null;
        org.jfree.data.Range range11 = org.jfree.data.Range.expandToInclude(range9, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint13 = new org.jfree.chart.block.RectangleConstraint(range11, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint14 = rectangleConstraint13.toUnconstrainedWidth();
        org.jfree.data.Range range15 = rectangleConstraint14.getWidthRange();
        double double16 = range15.getCentralValue();
        org.jfree.data.Range range17 = org.jfree.data.Range.combine(range6, range15);
        boolean boolean19 = range15.contains((double) (short) 1);
        double double20 = range15.getCentralValue();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint22 = new org.jfree.chart.block.RectangleConstraint(range15, (double) 1L);
        java.lang.String str23 = rectangleConstraint22.toString();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint24 = rectangleConstraint22.toUnconstrainedWidth();
        double double25 = rectangleConstraint22.getHeight();
        org.junit.Assert.assertNotNull(range2);
        org.junit.Assert.assertNotNull(rectangleConstraint5);
        org.junit.Assert.assertNotNull(range6);
        org.junit.Assert.assertTrue("'" + double8 + "' != '" + (-1.0d) + "'", double8 == (-1.0d));
        org.junit.Assert.assertNotNull(range11);
        org.junit.Assert.assertNotNull(rectangleConstraint14);
        org.junit.Assert.assertNotNull(range15);
        org.junit.Assert.assertTrue("'" + double16 + "' != '" + (-1.0d) + "'", double16 == (-1.0d));
        org.junit.Assert.assertNotNull(range17);
        org.junit.Assert.assertTrue("'" + boolean19 + "' != '" + false + "'", boolean19 == false);
        org.junit.Assert.assertTrue("'" + double20 + "' != '" + (-1.0d) + "'", double20 == (-1.0d));
        org.junit.Assert.assertEquals("'" + str23 + "' != '" + "RectangleConstraint[RectangleConstraintType.RANGE: width=0.0, height=1.0]" + "'", str23, "RectangleConstraint[RectangleConstraintType.RANGE: width=0.0, height=1.0]");
        org.junit.Assert.assertNotNull(rectangleConstraint24);
        org.junit.Assert.assertTrue("'" + double25 + "' != '" + 1.0d + "'", double25 == 1.0d);
    }

    @Test
    public void test581() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test581");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.BlockBorder blockBorder2 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder2);
        java.util.List list4 = blockContainer1.getBlocks();
        blockContainer1.setPadding((double) 100.0f, 33.0d, (double) (-1L), (double) 1L);
        double double10 = blockContainer1.getContentXOffset();
        double double12 = blockContainer1.trimToContentHeight((double) (short) 10);
        org.junit.Assert.assertNotNull(blockBorder2);
        org.junit.Assert.assertNotNull(list4);
        org.junit.Assert.assertTrue("'" + double10 + "' != '" + 33.0d + "'", double10 == 33.0d);
        org.junit.Assert.assertTrue("'" + double12 + "' != '" + 0.0d + "'", double12 == 0.0d);
    }

    @Test
    public void test582() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test582");
        org.jfree.chart.util.Size2D size2D2 = new org.jfree.chart.util.Size2D((double) '#', 0.0d);
        double double3 = size2D2.getHeight();
        org.junit.Assert.assertTrue("'" + double3 + "' != '" + 0.0d + "'", double3 == 0.0d);
    }

    @Test
    public void test583() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test583");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement0.clear();
        borderArrangement0.clear();
        borderArrangement0.clear();
    }

    @Test
    public void test584() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test584");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.BlockBorder blockBorder2 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder2);
        java.util.List list4 = blockContainer1.getBlocks();
        blockContainer1.setPadding((double) 100.0f, 33.0d, (double) (-1L), (double) 1L);
        double double10 = blockContainer1.getContentXOffset();
        java.awt.geom.Rectangle2D rectangle2D11 = null;
        // The following exception was thrown during execution in test generation
        try {
            blockContainer1.setBounds(rectangle2D11);
            org.junit.Assert.fail("Expected exception of type java.lang.IllegalArgumentException; message: Null 'bounds' argument.");
        } catch (java.lang.IllegalArgumentException e) {
            // Expected exception.
        }
        org.junit.Assert.assertNotNull(blockBorder2);
        org.junit.Assert.assertNotNull(list4);
        org.junit.Assert.assertTrue("'" + double10 + "' != '" + 33.0d + "'", double10 == 33.0d);
    }

    @Test
    public void test585() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test585");
        org.jfree.chart.util.RectangleInsets rectangleInsets4 = new org.jfree.chart.util.RectangleInsets((double) (-1), (double) 100, 0.0d, 3.0d);
        double double6 = rectangleInsets4.calculateBottomOutset((double) (byte) 1);
        double double8 = rectangleInsets4.calculateTopInset((double) (byte) -1);
        org.jfree.chart.block.BorderArrangement borderArrangement9 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer10 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement9);
        org.jfree.chart.block.BlockBorder blockBorder11 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer10.setFrame((org.jfree.chart.block.BlockFrame) blockBorder11);
        java.util.List list13 = blockContainer10.getBlocks();
        boolean boolean15 = blockContainer10.equals((java.lang.Object) "Range[-1.0,-1.0]");
        org.jfree.chart.util.RectangleInsets rectangleInsets16 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double18 = rectangleInsets16.calculateBottomInset((double) 0.0f);
        org.jfree.chart.util.RectangleInsets rectangleInsets19 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double21 = rectangleInsets19.calculateBottomInset((double) 0.0f);
        double double22 = rectangleInsets19.getBottom();
        double double23 = rectangleInsets19.getTop();
        org.jfree.chart.util.RectangleInsets rectangleInsets24 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double26 = rectangleInsets24.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock29 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D30 = emptyBlock29.getBounds();
        java.awt.geom.Rectangle2D rectangle2D33 = rectangleInsets24.createOutsetRectangle(rectangle2D30, true, false);
        java.awt.geom.Rectangle2D rectangle2D34 = rectangleInsets19.createInsetRectangle(rectangle2D30);
        java.awt.geom.Rectangle2D rectangle2D35 = rectangleInsets16.createInsetRectangle(rectangle2D34);
        boolean boolean36 = blockContainer10.equals((java.lang.Object) rectangle2D34);
        org.jfree.chart.util.RectangleEdge rectangleEdge37 = org.jfree.chart.util.RectangleEdge.RIGHT;
        org.jfree.chart.util.RectangleEdge rectangleEdge38 = org.jfree.chart.util.RectangleEdge.LEFT;
        boolean boolean39 = rectangleEdge37.equals((java.lang.Object) rectangleEdge38);
        boolean boolean40 = org.jfree.chart.util.RectangleEdge.isTopOrBottom(rectangleEdge38);
        double double41 = org.jfree.chart.util.RectangleEdge.coordinate(rectangle2D34, rectangleEdge38);
        java.awt.geom.Rectangle2D rectangle2D44 = rectangleInsets4.createInsetRectangle(rectangle2D34, false, true);
        org.junit.Assert.assertTrue("'" + double6 + "' != '" + 0.0d + "'", double6 == 0.0d);
        org.junit.Assert.assertTrue("'" + double8 + "' != '" + (-1.0d) + "'", double8 == (-1.0d));
        org.junit.Assert.assertNotNull(blockBorder11);
        org.junit.Assert.assertNotNull(list13);
        org.junit.Assert.assertTrue("'" + boolean15 + "' != '" + false + "'", boolean15 == false);
        org.junit.Assert.assertNotNull(rectangleInsets16);
        org.junit.Assert.assertTrue("'" + double18 + "' != '" + 0.0d + "'", double18 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets19);
        org.junit.Assert.assertTrue("'" + double21 + "' != '" + 0.0d + "'", double21 == 0.0d);
        org.junit.Assert.assertTrue("'" + double22 + "' != '" + 0.0d + "'", double22 == 0.0d);
        org.junit.Assert.assertTrue("'" + double23 + "' != '" + 0.0d + "'", double23 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets24);
        org.junit.Assert.assertTrue("'" + double26 + "' != '" + 0.0d + "'", double26 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D30);
        org.junit.Assert.assertNotNull(rectangle2D33);
        org.junit.Assert.assertNotNull(rectangle2D34);
        org.junit.Assert.assertNotNull(rectangle2D35);
        org.junit.Assert.assertTrue("'" + boolean36 + "' != '" + false + "'", boolean36 == false);
        org.junit.Assert.assertNotNull(rectangleEdge37);
        org.junit.Assert.assertNotNull(rectangleEdge38);
        org.junit.Assert.assertTrue("'" + boolean39 + "' != '" + false + "'", boolean39 == false);
        org.junit.Assert.assertTrue("'" + boolean40 + "' != '" + false + "'", boolean40 == false);
        org.junit.Assert.assertTrue("'" + double41 + "' != '" + 0.0d + "'", double41 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D44);
    }

    @Test
    public void test586() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test586");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement0.clear();
        borderArrangement0.clear();
        borderArrangement0.clear();
        org.jfree.chart.block.BlockContainer blockContainer4 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        double double5 = blockContainer4.getWidth();
        java.util.List list6 = blockContainer4.getBlocks();
        double double8 = blockContainer4.trimToContentHeight((double) 10.0f);
        java.awt.Graphics2D graphics2D9 = null;
        org.jfree.chart.util.Size2D size2D10 = blockContainer4.arrange(graphics2D9);
        org.junit.Assert.assertTrue("'" + double5 + "' != '" + 0.0d + "'", double5 == 0.0d);
        org.junit.Assert.assertNotNull(list6);
        org.junit.Assert.assertTrue("'" + double8 + "' != '" + 10.0d + "'", double8 == 10.0d);
        org.junit.Assert.assertNotNull(size2D10);
    }

    @Test
    public void test587() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test587");
        org.jfree.chart.util.RectangleInsets rectangleInsets4 = new org.jfree.chart.util.RectangleInsets((double) (-1), (double) 100, 0.0d, 3.0d);
        double double6 = rectangleInsets4.calculateBottomOutset((double) (byte) 1);
        double double8 = rectangleInsets4.calculateTopInset((double) (byte) -1);
        org.jfree.chart.util.UnitType unitType9 = rectangleInsets4.getUnitType();
        org.junit.Assert.assertTrue("'" + double6 + "' != '" + 0.0d + "'", double6 == 0.0d);
        org.junit.Assert.assertTrue("'" + double8 + "' != '" + (-1.0d) + "'", double8 == (-1.0d));
        org.junit.Assert.assertNotNull(unitType9);
    }

    @Test
    public void test588() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test588");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        java.util.List list2 = blockContainer1.getBlocks();
        blockContainer1.setMargin((double) ' ', (double) 100.0f, 0.0d, (double) 'a');
        org.jfree.chart.block.Arrangement arrangement8 = blockContainer1.getArrangement();
        double double9 = blockContainer1.getHeight();
        double double11 = blockContainer1.calculateTotalWidth((double) 0L);
        org.junit.Assert.assertNotNull(list2);
        org.junit.Assert.assertNotNull(arrangement8);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + 0.0d + "'", double9 == 0.0d);
        org.junit.Assert.assertTrue("'" + double11 + "' != '" + 197.0d + "'", double11 == 197.0d);
    }

    @Test
    public void test589() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test589");
        org.jfree.chart.util.UnitType unitType0 = org.jfree.chart.util.UnitType.RELATIVE;
        java.lang.String str1 = unitType0.toString();
        org.jfree.chart.util.RectangleInsets rectangleInsets6 = new org.jfree.chart.util.RectangleInsets(unitType0, (double) 100L, 10.0d, 1.0d, (double) (short) -1);
        org.junit.Assert.assertNotNull(unitType0);
        org.junit.Assert.assertEquals("'" + str1 + "' != '" + "UnitType.RELATIVE" + "'", str1, "UnitType.RELATIVE");
    }

    @Test
    public void test590() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test590");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.BorderArrangement borderArrangement2 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement2.clear();
        borderArrangement2.clear();
        borderArrangement2.clear();
        org.jfree.chart.block.BlockContainer blockContainer6 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement2);
        double double7 = blockContainer6.getWidth();
        java.util.List list8 = blockContainer6.getBlocks();
        java.awt.Graphics2D graphics2D9 = null;
        // The following exception was thrown during execution in test generation
        try {
            org.jfree.chart.util.Size2D size2D11 = borderArrangement0.arrangeFN(blockContainer6, graphics2D9, (double) (-1.0f));
            org.junit.Assert.fail("Expected exception of type java.lang.IllegalArgumentException; message: Range(double, double): require lower (0.0) <= upper (-1.0).");
        } catch (java.lang.IllegalArgumentException e) {
            // Expected exception.
        }
        org.junit.Assert.assertTrue("'" + double7 + "' != '" + 0.0d + "'", double7 == 0.0d);
        org.junit.Assert.assertNotNull(list8);
    }

    @Test
    public void test591() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test591");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        java.awt.geom.Rectangle2D rectangle2D2 = blockContainer1.getBounds();
        double double3 = blockContainer1.getContentYOffset();
        org.jfree.chart.util.RectangleInsets rectangleInsets4 = new org.jfree.chart.util.RectangleInsets();
        double double5 = rectangleInsets4.getRight();
        double double7 = rectangleInsets4.calculateLeftInset((double) (byte) 0);
        org.jfree.chart.util.Size2D size2D8 = new org.jfree.chart.util.Size2D();
        size2D8.setHeight((double) (short) 0);
        double double11 = size2D8.getWidth();
        double double12 = size2D8.getWidth();
        org.jfree.chart.util.RectangleInsets rectangleInsets13 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double15 = rectangleInsets13.calculateTopOutset((double) 1.0f);
        double double17 = rectangleInsets13.extendHeight((double) (byte) -1);
        boolean boolean18 = size2D8.equals((java.lang.Object) rectangleInsets13);
        org.jfree.chart.block.EmptyBlock emptyBlock21 = new org.jfree.chart.block.EmptyBlock((-1.0d), 0.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets22 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double24 = rectangleInsets22.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock27 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D28 = emptyBlock27.getBounds();
        java.awt.geom.Rectangle2D rectangle2D31 = rectangleInsets22.createOutsetRectangle(rectangle2D28, true, false);
        java.awt.geom.Rectangle2D rectangle2D32 = emptyBlock21.trimBorder(rectangle2D28);
        java.awt.geom.Rectangle2D rectangle2D33 = emptyBlock21.getBounds();
        java.awt.geom.Rectangle2D rectangle2D36 = rectangleInsets13.createInsetRectangle(rectangle2D33, true, false);
        rectangleInsets4.trim(rectangle2D33);
        double double39 = rectangleInsets4.trimWidth(33.0d);
        blockContainer1.setMargin(rectangleInsets4);
        org.jfree.chart.block.Arrangement arrangement41 = blockContainer1.getArrangement();
        org.junit.Assert.assertNotNull(rectangle2D2);
        org.junit.Assert.assertTrue("'" + double3 + "' != '" + 0.0d + "'", double3 == 0.0d);
        org.junit.Assert.assertTrue("'" + double5 + "' != '" + 1.0d + "'", double5 == 1.0d);
        org.junit.Assert.assertTrue("'" + double7 + "' != '" + 1.0d + "'", double7 == 1.0d);
        org.junit.Assert.assertTrue("'" + double11 + "' != '" + 0.0d + "'", double11 == 0.0d);
        org.junit.Assert.assertTrue("'" + double12 + "' != '" + 0.0d + "'", double12 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets13);
        org.junit.Assert.assertTrue("'" + double15 + "' != '" + 0.0d + "'", double15 == 0.0d);
        org.junit.Assert.assertTrue("'" + double17 + "' != '" + (-1.0d) + "'", double17 == (-1.0d));
        org.junit.Assert.assertTrue("'" + boolean18 + "' != '" + false + "'", boolean18 == false);
        org.junit.Assert.assertNotNull(rectangleInsets22);
        org.junit.Assert.assertTrue("'" + double24 + "' != '" + 0.0d + "'", double24 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D28);
        org.junit.Assert.assertNotNull(rectangle2D31);
        org.junit.Assert.assertNotNull(rectangle2D32);
        org.junit.Assert.assertNotNull(rectangle2D33);
        org.junit.Assert.assertNotNull(rectangle2D36);
        org.junit.Assert.assertTrue("'" + double39 + "' != '" + 31.0d + "'", double39 == 31.0d);
        org.junit.Assert.assertNotNull(arrangement41);
    }

    @Test
    public void test592() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test592");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement0.clear();
        borderArrangement0.clear();
        borderArrangement0.clear();
        org.jfree.chart.block.BlockContainer blockContainer4 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.util.RectangleInsets rectangleInsets5 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double7 = rectangleInsets5.calculateTopOutset((double) 1.0f);
        double double9 = rectangleInsets5.trimHeight((-1.0d));
        blockContainer4.setPadding(rectangleInsets5);
        double double11 = blockContainer4.getContentYOffset();
        org.jfree.chart.block.EmptyBlock emptyBlock14 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets15 = emptyBlock14.getPadding();
        org.jfree.chart.block.BlockFrame blockFrame16 = emptyBlock14.getFrame();
        blockContainer4.setFrame(blockFrame16);
        blockContainer4.setID("Size2D[width=0.0, height=0.0]");
        blockContainer4.setHeight((double) 1.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock24 = new org.jfree.chart.block.EmptyBlock(0.0d, (double) 10);
        java.awt.geom.Rectangle2D rectangle2D25 = emptyBlock24.getBounds();
        java.awt.geom.Rectangle2D rectangle2D26 = blockContainer4.trimMargin(rectangle2D25);
        blockContainer4.setID("Range[-2.0,-2.0]");
        blockContainer4.clear();
        org.junit.Assert.assertNotNull(rectangleInsets5);
        org.junit.Assert.assertTrue("'" + double7 + "' != '" + 0.0d + "'", double7 == 0.0d);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + (-1.0d) + "'", double9 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double11 + "' != '" + 0.0d + "'", double11 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets15);
        org.junit.Assert.assertNotNull(blockFrame16);
        org.junit.Assert.assertNotNull(rectangle2D25);
        org.junit.Assert.assertNotNull(rectangle2D26);
    }

    @Test
    public void test593() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test593");
        org.jfree.data.Range range0 = null;
        org.jfree.data.Range range2 = org.jfree.data.Range.expandToInclude(range0, (double) (short) -1);
        java.lang.String str3 = range2.toString();
        org.jfree.data.Range range5 = org.jfree.data.Range.expandToInclude(range2, (double) (-1L));
        org.jfree.data.Range range6 = null;
        org.jfree.data.Range range8 = org.jfree.data.Range.expandToInclude(range6, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint10 = new org.jfree.chart.block.RectangleConstraint(range8, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint11 = rectangleConstraint10.toUnconstrainedWidth();
        org.jfree.data.Range range12 = rectangleConstraint11.getWidthRange();
        double double13 = range12.getUpperBound();
        double double15 = range12.constrain((double) (-1L));
        double double16 = range12.getUpperBound();
        org.jfree.data.Range range17 = org.jfree.data.Range.combine(range2, range12);
        double double18 = range12.getCentralValue();
        org.junit.Assert.assertNotNull(range2);
        org.junit.Assert.assertEquals("'" + str3 + "' != '" + "Range[-1.0,-1.0]" + "'", str3, "Range[-1.0,-1.0]");
        org.junit.Assert.assertNotNull(range5);
        org.junit.Assert.assertNotNull(range8);
        org.junit.Assert.assertNotNull(rectangleConstraint11);
        org.junit.Assert.assertNotNull(range12);
        org.junit.Assert.assertTrue("'" + double13 + "' != '" + (-1.0d) + "'", double13 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double15 + "' != '" + (-1.0d) + "'", double15 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double16 + "' != '" + (-1.0d) + "'", double16 == (-1.0d));
        org.junit.Assert.assertNotNull(range17);
        org.junit.Assert.assertTrue("'" + double18 + "' != '" + (-1.0d) + "'", double18 == (-1.0d));
    }

    @Test
    public void test594() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test594");
        org.jfree.chart.block.BorderArrangement borderArrangement4 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer5 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement4);
        org.jfree.chart.block.BlockBorder blockBorder6 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer5.setFrame((org.jfree.chart.block.BlockFrame) blockBorder6);
        java.awt.Paint paint8 = blockBorder6.getPaint();
        org.jfree.chart.block.BlockBorder blockBorder9 = new org.jfree.chart.block.BlockBorder((double) 10L, 0.0d, (double) 1.0f, (double) 10L, paint8);
        org.jfree.chart.block.BlockBorder blockBorder10 = new org.jfree.chart.block.BlockBorder(paint8);
        org.jfree.chart.util.RectangleInsets rectangleInsets11 = blockBorder10.getInsets();
        org.junit.Assert.assertNotNull(blockBorder6);
        org.junit.Assert.assertNotNull(paint8);
        org.junit.Assert.assertNotNull(rectangleInsets11);
    }

    @Test
    public void test595() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test595");
        org.jfree.chart.util.RectangleInsets rectangleInsets0 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double2 = rectangleInsets0.calculateBottomInset((double) 0.0f);
        double double3 = rectangleInsets0.getBottom();
        double double4 = rectangleInsets0.getTop();
        org.jfree.chart.util.UnitType unitType5 = rectangleInsets0.getUnitType();
        org.jfree.chart.util.RectangleInsets rectangleInsets10 = new org.jfree.chart.util.RectangleInsets(unitType5, (double) 10, 52.0d, (double) (byte) 1, (double) 100.0f);
        double double12 = rectangleInsets10.calculateLeftInset(10.0d);
        org.junit.Assert.assertNotNull(rectangleInsets0);
        org.junit.Assert.assertTrue("'" + double2 + "' != '" + 0.0d + "'", double2 == 0.0d);
        org.junit.Assert.assertTrue("'" + double3 + "' != '" + 0.0d + "'", double3 == 0.0d);
        org.junit.Assert.assertTrue("'" + double4 + "' != '" + 0.0d + "'", double4 == 0.0d);
        org.junit.Assert.assertNotNull(unitType5);
        org.junit.Assert.assertTrue("'" + double12 + "' != '" + 52.0d + "'", double12 == 52.0d);
    }

    @Test
    public void test596() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test596");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.BlockBorder blockBorder2 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder2);
        java.util.List list4 = blockContainer1.getBlocks();
        blockContainer1.setPadding((double) 100.0f, 33.0d, (double) (-1L), (double) 1L);
        org.jfree.chart.block.BorderArrangement borderArrangement10 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer11 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement10);
        org.jfree.chart.block.BlockBorder blockBorder12 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer11.setFrame((org.jfree.chart.block.BlockFrame) blockBorder12);
        java.util.List list14 = blockContainer11.getBlocks();
        org.jfree.chart.block.EmptyBlock emptyBlock17 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.lang.Object obj18 = emptyBlock17.clone();
        emptyBlock17.setMargin((double) (-1.0f), (double) (byte) 10, (double) 'a', (double) 0L);
        org.jfree.chart.util.RectangleInsets rectangleInsets24 = emptyBlock17.getPadding();
        boolean boolean25 = blockContainer11.equals((java.lang.Object) emptyBlock17);
        org.jfree.chart.block.BlockFrame blockFrame26 = emptyBlock17.getFrame();
        java.awt.Graphics2D graphics2D27 = null;
        org.jfree.chart.block.RectangleConstraint rectangleConstraint30 = new org.jfree.chart.block.RectangleConstraint((double) (short) -1, (double) 100);
        double double31 = rectangleConstraint30.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint33 = rectangleConstraint30.toFixedHeight((double) 0);
        org.jfree.chart.util.Size2D size2D34 = emptyBlock17.arrange(graphics2D27, rectangleConstraint30);
        blockContainer1.add((org.jfree.chart.block.Block) emptyBlock17);
        double double37 = emptyBlock17.trimToContentWidth((double) 100L);
        emptyBlock17.setHeight((double) (-1L));
        org.jfree.chart.util.RectangleInsets rectangleInsets40 = emptyBlock17.getPadding();
        java.awt.Graphics2D graphics2D41 = null;
        org.jfree.chart.util.Size2D size2D42 = emptyBlock17.arrange(graphics2D41);
        org.junit.Assert.assertNotNull(blockBorder2);
        org.junit.Assert.assertNotNull(list4);
        org.junit.Assert.assertNotNull(blockBorder12);
        org.junit.Assert.assertNotNull(list14);
        org.junit.Assert.assertNotNull(obj18);
        org.junit.Assert.assertNotNull(rectangleInsets24);
        org.junit.Assert.assertTrue("'" + boolean25 + "' != '" + false + "'", boolean25 == false);
        org.junit.Assert.assertNotNull(blockFrame26);
        org.junit.Assert.assertTrue("'" + double31 + "' != '" + (-1.0d) + "'", double31 == (-1.0d));
        org.junit.Assert.assertNotNull(rectangleConstraint33);
        org.junit.Assert.assertNotNull(size2D34);
        org.junit.Assert.assertTrue("'" + double37 + "' != '" + 90.0d + "'", double37 == 90.0d);
        org.junit.Assert.assertNotNull(rectangleInsets40);
        org.junit.Assert.assertNotNull(size2D42);
    }

    @Test
    public void test597() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test597");
        org.jfree.data.Range range1 = null;
        org.jfree.data.Range range3 = org.jfree.data.Range.expandToInclude(range1, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint5 = new org.jfree.chart.block.RectangleConstraint(range3, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint6 = rectangleConstraint5.toUnconstrainedWidth();
        org.jfree.data.Range range7 = rectangleConstraint6.getWidthRange();
        double double9 = range7.constrain((double) (byte) 1);
        org.jfree.data.Range range10 = null;
        org.jfree.data.Range range12 = org.jfree.data.Range.expandToInclude(range10, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint14 = new org.jfree.chart.block.RectangleConstraint(range12, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint15 = rectangleConstraint14.toUnconstrainedWidth();
        org.jfree.data.Range range16 = rectangleConstraint15.getWidthRange();
        double double17 = range16.getCentralValue();
        org.jfree.data.Range range18 = org.jfree.data.Range.combine(range7, range16);
        double double19 = range16.getCentralValue();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint20 = new org.jfree.chart.block.RectangleConstraint((double) (byte) 100, range16);
        org.jfree.data.Range range23 = org.jfree.data.Range.expand(range16, 0.0d, (double) 1L);
        double double25 = range23.constrain((double) 'a');
        org.junit.Assert.assertNotNull(range3);
        org.junit.Assert.assertNotNull(rectangleConstraint6);
        org.junit.Assert.assertNotNull(range7);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + (-1.0d) + "'", double9 == (-1.0d));
        org.junit.Assert.assertNotNull(range12);
        org.junit.Assert.assertNotNull(rectangleConstraint15);
        org.junit.Assert.assertNotNull(range16);
        org.junit.Assert.assertTrue("'" + double17 + "' != '" + (-1.0d) + "'", double17 == (-1.0d));
        org.junit.Assert.assertNotNull(range18);
        org.junit.Assert.assertTrue("'" + double19 + "' != '" + (-1.0d) + "'", double19 == (-1.0d));
        org.junit.Assert.assertNotNull(range23);
        org.junit.Assert.assertTrue("'" + double25 + "' != '" + (-1.0d) + "'", double25 == (-1.0d));
    }

    @Test
    public void test598() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test598");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.BlockBorder blockBorder2 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder2);
        java.util.List list4 = blockContainer1.getBlocks();
        boolean boolean6 = blockContainer1.equals((java.lang.Object) "Range[-1.0,-1.0]");
        org.jfree.chart.util.RectangleInsets rectangleInsets7 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double9 = rectangleInsets7.calculateBottomInset((double) 0.0f);
        org.jfree.chart.util.RectangleInsets rectangleInsets10 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double12 = rectangleInsets10.calculateBottomInset((double) 0.0f);
        double double13 = rectangleInsets10.getBottom();
        double double14 = rectangleInsets10.getTop();
        org.jfree.chart.util.RectangleInsets rectangleInsets15 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double17 = rectangleInsets15.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock20 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D21 = emptyBlock20.getBounds();
        java.awt.geom.Rectangle2D rectangle2D24 = rectangleInsets15.createOutsetRectangle(rectangle2D21, true, false);
        java.awt.geom.Rectangle2D rectangle2D25 = rectangleInsets10.createInsetRectangle(rectangle2D21);
        java.awt.geom.Rectangle2D rectangle2D26 = rectangleInsets7.createInsetRectangle(rectangle2D25);
        boolean boolean27 = blockContainer1.equals((java.lang.Object) rectangle2D25);
        java.awt.Graphics2D graphics2D28 = null;
        org.jfree.chart.util.Size2D size2D29 = blockContainer1.arrange(graphics2D28);
        java.awt.Graphics2D graphics2D30 = null;
        org.jfree.data.Range range34 = new org.jfree.data.Range((double) (byte) -1, (double) 100.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint35 = new org.jfree.chart.block.RectangleConstraint(35.0d, range34);
        org.jfree.chart.util.Size2D size2D36 = blockContainer1.arrange(graphics2D30, rectangleConstraint35);
        org.junit.Assert.assertNotNull(blockBorder2);
        org.junit.Assert.assertNotNull(list4);
        org.junit.Assert.assertTrue("'" + boolean6 + "' != '" + false + "'", boolean6 == false);
        org.junit.Assert.assertNotNull(rectangleInsets7);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + 0.0d + "'", double9 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets10);
        org.junit.Assert.assertTrue("'" + double12 + "' != '" + 0.0d + "'", double12 == 0.0d);
        org.junit.Assert.assertTrue("'" + double13 + "' != '" + 0.0d + "'", double13 == 0.0d);
        org.junit.Assert.assertTrue("'" + double14 + "' != '" + 0.0d + "'", double14 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets15);
        org.junit.Assert.assertTrue("'" + double17 + "' != '" + 0.0d + "'", double17 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D21);
        org.junit.Assert.assertNotNull(rectangle2D24);
        org.junit.Assert.assertNotNull(rectangle2D25);
        org.junit.Assert.assertNotNull(rectangle2D26);
        org.junit.Assert.assertTrue("'" + boolean27 + "' != '" + false + "'", boolean27 == false);
        org.junit.Assert.assertNotNull(size2D29);
        org.junit.Assert.assertNotNull(size2D36);
    }

    @Test
    public void test599() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test599");
        org.jfree.data.Range range0 = null;
        org.jfree.data.Range range2 = org.jfree.data.Range.expandToInclude(range0, (double) (short) -1);
        java.lang.String str3 = range2.toString();
        org.jfree.data.Range range5 = org.jfree.data.Range.shift(range2, (double) 10L);
        org.jfree.data.Range range6 = null;
        org.jfree.data.Range range7 = org.jfree.data.Range.combine(range5, range6);
        org.jfree.data.Range range8 = null;
        org.jfree.data.Range range10 = org.jfree.data.Range.expandToInclude(range8, (double) (short) -1);
        java.lang.String str11 = range10.toString();
        org.jfree.data.Range range13 = org.jfree.data.Range.shift(range10, (double) 10L);
        org.jfree.data.Range range14 = org.jfree.data.Range.combine(range5, range13);
        org.jfree.data.Range range17 = org.jfree.data.Range.shift(range14, 35.0d, true);
        org.junit.Assert.assertNotNull(range2);
        org.junit.Assert.assertEquals("'" + str3 + "' != '" + "Range[-1.0,-1.0]" + "'", str3, "Range[-1.0,-1.0]");
        org.junit.Assert.assertNotNull(range5);
        org.junit.Assert.assertNotNull(range7);
        org.junit.Assert.assertNotNull(range10);
        org.junit.Assert.assertEquals("'" + str11 + "' != '" + "Range[-1.0,-1.0]" + "'", str11, "Range[-1.0,-1.0]");
        org.junit.Assert.assertNotNull(range13);
        org.junit.Assert.assertNotNull(range14);
        org.junit.Assert.assertNotNull(range17);
    }

    @Test
    public void test600() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test600");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.BlockBorder blockBorder2 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder2);
        java.util.List list4 = blockContainer1.getBlocks();
        double double6 = blockContainer1.calculateTotalWidth(33.0d);
        java.awt.Graphics2D graphics2D7 = null;
        org.jfree.chart.util.Size2D size2D8 = blockContainer1.arrange(graphics2D7);
        double double9 = size2D8.getWidth();
        org.junit.Assert.assertNotNull(blockBorder2);
        org.junit.Assert.assertNotNull(list4);
        org.junit.Assert.assertTrue("'" + double6 + "' != '" + 33.0d + "'", double6 == 33.0d);
        org.junit.Assert.assertNotNull(size2D8);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + 0.0d + "'", double9 == 0.0d);
    }

    @Test
    public void test601() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test601");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement0.clear();
        org.jfree.chart.block.EmptyBlock emptyBlock4 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D5 = emptyBlock4.getBounds();
        org.jfree.data.Range range6 = null;
        org.jfree.data.Range range8 = org.jfree.data.Range.expandToInclude(range6, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint10 = new org.jfree.chart.block.RectangleConstraint(range8, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint11 = rectangleConstraint10.toUnconstrainedWidth();
        double double12 = rectangleConstraint11.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint13 = emptyBlock4.toContentConstraint(rectangleConstraint11);
        double double15 = emptyBlock4.trimToContentHeight((double) (byte) 0);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint18 = new org.jfree.chart.block.RectangleConstraint((double) (short) -1, (double) 100);
        double double19 = rectangleConstraint18.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint21 = rectangleConstraint18.toFixedHeight((double) 100);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint22 = emptyBlock4.toContentConstraint(rectangleConstraint21);
        java.lang.Object obj23 = null;
        borderArrangement0.add((org.jfree.chart.block.Block) emptyBlock4, obj23);
        org.jfree.chart.block.BorderArrangement borderArrangement25 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement25.clear();
        borderArrangement25.clear();
        borderArrangement25.clear();
        org.jfree.chart.block.BlockContainer blockContainer29 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement25);
        double double30 = blockContainer29.getWidth();
        blockContainer29.clear();
        java.awt.Graphics2D graphics2D32 = null;
        org.jfree.chart.util.Size2D size2D33 = blockContainer29.arrange(graphics2D32);
        double double35 = blockContainer29.calculateTotalHeight((double) (short) 0);
        java.awt.Graphics2D graphics2D36 = null;
        org.jfree.data.Range range38 = null;
        org.jfree.data.Range range40 = org.jfree.data.Range.expandToInclude(range38, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint42 = new org.jfree.chart.block.RectangleConstraint(range40, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint43 = rectangleConstraint42.toUnconstrainedWidth();
        org.jfree.data.Range range44 = rectangleConstraint43.getWidthRange();
        double double46 = range44.constrain((double) (byte) 1);
        org.jfree.data.Range range47 = null;
        org.jfree.data.Range range49 = org.jfree.data.Range.expandToInclude(range47, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint51 = new org.jfree.chart.block.RectangleConstraint(range49, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint52 = rectangleConstraint51.toUnconstrainedWidth();
        org.jfree.data.Range range53 = rectangleConstraint52.getWidthRange();
        double double54 = range53.getCentralValue();
        org.jfree.data.Range range55 = org.jfree.data.Range.combine(range44, range53);
        double double56 = range53.getCentralValue();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint57 = new org.jfree.chart.block.RectangleConstraint((double) (byte) 100, range53);
        double double58 = rectangleConstraint57.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint60 = rectangleConstraint57.toFixedWidth(100.0d);
        org.jfree.chart.block.LengthConstraintType lengthConstraintType61 = rectangleConstraint57.getWidthConstraintType();
        org.jfree.chart.util.Size2D size2D62 = borderArrangement0.arrangeFR(blockContainer29, graphics2D36, rectangleConstraint57);
        org.jfree.chart.util.RectangleInsets rectangleInsets63 = new org.jfree.chart.util.RectangleInsets();
        double double64 = rectangleInsets63.getLeft();
        double double66 = rectangleInsets63.extendWidth((double) (-1));
        org.jfree.chart.util.RectangleInsets rectangleInsets67 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        org.jfree.chart.util.RectangleInsets rectangleInsets68 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double70 = rectangleInsets68.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock73 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D74 = emptyBlock73.getBounds();
        java.awt.geom.Rectangle2D rectangle2D77 = rectangleInsets68.createOutsetRectangle(rectangle2D74, true, false);
        java.awt.geom.Rectangle2D rectangle2D78 = rectangleInsets67.createInsetRectangle(rectangle2D77);
        java.awt.geom.Rectangle2D rectangle2D79 = rectangleInsets63.createInsetRectangle(rectangle2D78);
        java.awt.geom.Rectangle2D rectangle2D80 = blockContainer29.trimPadding(rectangle2D79);
        org.junit.Assert.assertNotNull(rectangle2D5);
        org.junit.Assert.assertNotNull(range8);
        org.junit.Assert.assertNotNull(rectangleConstraint11);
        org.junit.Assert.assertTrue("'" + double12 + "' != '" + 0.0d + "'", double12 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleConstraint13);
        org.junit.Assert.assertTrue("'" + double15 + "' != '" + 0.0d + "'", double15 == 0.0d);
        org.junit.Assert.assertTrue("'" + double19 + "' != '" + (-1.0d) + "'", double19 == (-1.0d));
        org.junit.Assert.assertNotNull(rectangleConstraint21);
        org.junit.Assert.assertNotNull(rectangleConstraint22);
        org.junit.Assert.assertTrue("'" + double30 + "' != '" + 0.0d + "'", double30 == 0.0d);
        org.junit.Assert.assertNotNull(size2D33);
        org.junit.Assert.assertTrue("'" + double35 + "' != '" + 0.0d + "'", double35 == 0.0d);
        org.junit.Assert.assertNotNull(range40);
        org.junit.Assert.assertNotNull(rectangleConstraint43);
        org.junit.Assert.assertNotNull(range44);
        org.junit.Assert.assertTrue("'" + double46 + "' != '" + (-1.0d) + "'", double46 == (-1.0d));
        org.junit.Assert.assertNotNull(range49);
        org.junit.Assert.assertNotNull(rectangleConstraint52);
        org.junit.Assert.assertNotNull(range53);
        org.junit.Assert.assertTrue("'" + double54 + "' != '" + (-1.0d) + "'", double54 == (-1.0d));
        org.junit.Assert.assertNotNull(range55);
        org.junit.Assert.assertTrue("'" + double56 + "' != '" + (-1.0d) + "'", double56 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double58 + "' != '" + 100.0d + "'", double58 == 100.0d);
        org.junit.Assert.assertNotNull(rectangleConstraint60);
        org.junit.Assert.assertNotNull(lengthConstraintType61);
        org.junit.Assert.assertNotNull(size2D62);
        org.junit.Assert.assertTrue("'" + double64 + "' != '" + 1.0d + "'", double64 == 1.0d);
        org.junit.Assert.assertTrue("'" + double66 + "' != '" + 1.0d + "'", double66 == 1.0d);
        org.junit.Assert.assertNotNull(rectangleInsets67);
        org.junit.Assert.assertNotNull(rectangleInsets68);
        org.junit.Assert.assertTrue("'" + double70 + "' != '" + 0.0d + "'", double70 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D74);
        org.junit.Assert.assertNotNull(rectangle2D77);
        org.junit.Assert.assertNotNull(rectangle2D78);
        org.junit.Assert.assertNotNull(rectangle2D79);
        org.junit.Assert.assertNotNull(rectangle2D80);
    }

    @Test
    public void test602() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test602");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        java.util.List list2 = blockContainer1.getBlocks();
        blockContainer1.setMargin((double) ' ', (double) 100.0f, 0.0d, (double) 'a');
        org.jfree.chart.block.BorderArrangement borderArrangement8 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement8.clear();
        borderArrangement8.clear();
        borderArrangement8.clear();
        org.jfree.chart.block.BlockContainer blockContainer12 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement8);
        blockContainer1.add((org.jfree.chart.block.Block) blockContainer12);
        blockContainer1.setID("RectangleEdge.LEFT");
        java.lang.Object obj16 = blockContainer1.clone();
        java.awt.Graphics2D graphics2D17 = null;
        org.jfree.chart.block.BorderArrangement borderArrangement18 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer19 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement18);
        java.util.List list20 = blockContainer19.getBlocks();
        blockContainer19.setMargin((double) ' ', (double) 100.0f, 0.0d, (double) 'a');
        org.jfree.chart.util.RectangleInsets rectangleInsets26 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double28 = rectangleInsets26.calculateBottomInset((double) 0.0f);
        org.jfree.chart.util.UnitType unitType29 = rectangleInsets26.getUnitType();
        java.lang.String str30 = unitType29.toString();
        org.jfree.chart.block.BlockBorder blockBorder31 = org.jfree.chart.block.BlockBorder.NONE;
        boolean boolean32 = unitType29.equals((java.lang.Object) blockBorder31);
        blockContainer19.setFrame((org.jfree.chart.block.BlockFrame) blockBorder31);
        org.jfree.chart.block.EmptyBlock emptyBlock36 = new org.jfree.chart.block.EmptyBlock((-1.0d), 0.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets37 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double39 = rectangleInsets37.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock42 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D43 = emptyBlock42.getBounds();
        java.awt.geom.Rectangle2D rectangle2D46 = rectangleInsets37.createOutsetRectangle(rectangle2D43, true, false);
        java.awt.geom.Rectangle2D rectangle2D47 = emptyBlock36.trimBorder(rectangle2D43);
        org.jfree.chart.util.RectangleEdge rectangleEdge48 = org.jfree.chart.util.RectangleEdge.RIGHT;
        org.jfree.chart.util.RectangleEdge rectangleEdge49 = org.jfree.chart.util.RectangleEdge.LEFT;
        boolean boolean50 = rectangleEdge48.equals((java.lang.Object) rectangleEdge49);
        double double51 = org.jfree.chart.util.RectangleEdge.coordinate(rectangle2D47, rectangleEdge49);
        blockContainer19.setBounds(rectangle2D47);
        org.jfree.chart.block.BlockBorder blockBorder53 = org.jfree.chart.block.BlockBorder.NONE;
        org.jfree.chart.util.RectangleEdge rectangleEdge54 = org.jfree.chart.util.RectangleEdge.RIGHT;
        org.jfree.chart.util.RectangleEdge rectangleEdge55 = org.jfree.chart.util.RectangleEdge.LEFT;
        boolean boolean56 = rectangleEdge54.equals((java.lang.Object) rectangleEdge55);
        boolean boolean57 = blockBorder53.equals((java.lang.Object) rectangleEdge54);
        org.jfree.chart.util.RectangleEdge rectangleEdge58 = org.jfree.chart.util.RectangleEdge.opposite(rectangleEdge54);
        double double59 = org.jfree.chart.util.RectangleEdge.coordinate(rectangle2D47, rectangleEdge58);
        org.jfree.chart.block.BorderArrangement borderArrangement60 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer61 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement60);
        java.util.List list62 = blockContainer61.getBlocks();
        java.lang.Object obj63 = blockContainer61.clone();
        org.jfree.chart.util.RectangleInsets rectangleInsets64 = new org.jfree.chart.util.RectangleInsets();
        double double65 = rectangleInsets64.getTop();
        double double66 = rectangleInsets64.getLeft();
        double double68 = rectangleInsets64.calculateTopOutset(33.0d);
        blockContainer61.setPadding(rectangleInsets64);
        // The following exception was thrown during execution in test generation
        try {
            java.lang.Object obj70 = blockContainer1.draw(graphics2D17, rectangle2D47, (java.lang.Object) rectangleInsets64);
            org.junit.Assert.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
            // Expected exception.
        }
        org.junit.Assert.assertNotNull(list2);
        org.junit.Assert.assertNotNull(obj16);
        org.junit.Assert.assertNotNull(list20);
        org.junit.Assert.assertNotNull(rectangleInsets26);
        org.junit.Assert.assertTrue("'" + double28 + "' != '" + 0.0d + "'", double28 == 0.0d);
        org.junit.Assert.assertNotNull(unitType29);
        org.junit.Assert.assertEquals("'" + str30 + "' != '" + "UnitType.ABSOLUTE" + "'", str30, "UnitType.ABSOLUTE");
        org.junit.Assert.assertNotNull(blockBorder31);
        org.junit.Assert.assertTrue("'" + boolean32 + "' != '" + false + "'", boolean32 == false);
        org.junit.Assert.assertNotNull(rectangleInsets37);
        org.junit.Assert.assertTrue("'" + double39 + "' != '" + 0.0d + "'", double39 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D43);
        org.junit.Assert.assertNotNull(rectangle2D46);
        org.junit.Assert.assertNotNull(rectangle2D47);
        org.junit.Assert.assertNotNull(rectangleEdge48);
        org.junit.Assert.assertNotNull(rectangleEdge49);
        org.junit.Assert.assertTrue("'" + boolean50 + "' != '" + false + "'", boolean50 == false);
        org.junit.Assert.assertTrue("'" + double51 + "' != '" + 0.0d + "'", double51 == 0.0d);
        org.junit.Assert.assertNotNull(blockBorder53);
        org.junit.Assert.assertNotNull(rectangleEdge54);
        org.junit.Assert.assertNotNull(rectangleEdge55);
        org.junit.Assert.assertTrue("'" + boolean56 + "' != '" + false + "'", boolean56 == false);
        org.junit.Assert.assertTrue("'" + boolean57 + "' != '" + false + "'", boolean57 == false);
        org.junit.Assert.assertNotNull(rectangleEdge58);
        org.junit.Assert.assertTrue("'" + double59 + "' != '" + 0.0d + "'", double59 == 0.0d);
        org.junit.Assert.assertNotNull(list62);
        org.junit.Assert.assertNotNull(obj63);
        org.junit.Assert.assertTrue("'" + double65 + "' != '" + 1.0d + "'", double65 == 1.0d);
        org.junit.Assert.assertTrue("'" + double66 + "' != '" + 1.0d + "'", double66 == 1.0d);
        org.junit.Assert.assertTrue("'" + double68 + "' != '" + 1.0d + "'", double68 == 1.0d);
    }

    @Test
    public void test603() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test603");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer1 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        org.jfree.chart.block.BlockBorder blockBorder2 = org.jfree.chart.block.BlockBorder.NONE;
        blockContainer1.setFrame((org.jfree.chart.block.BlockFrame) blockBorder2);
        java.awt.Paint paint4 = blockBorder2.getPaint();
        org.jfree.data.Range range5 = null;
        org.jfree.chart.block.RectangleConstraint rectangleConstraint7 = new org.jfree.chart.block.RectangleConstraint(range5, (-1.0d));
        org.jfree.chart.block.RectangleConstraint rectangleConstraint9 = rectangleConstraint7.toFixedHeight((double) (byte) 100);
        boolean boolean10 = blockBorder2.equals((java.lang.Object) rectangleConstraint9);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint11 = rectangleConstraint9.toUnconstrainedWidth();
        org.jfree.data.Range range12 = rectangleConstraint9.getWidthRange();
        org.junit.Assert.assertNotNull(blockBorder2);
        org.junit.Assert.assertNotNull(paint4);
        org.junit.Assert.assertNotNull(rectangleConstraint9);
        org.junit.Assert.assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
        org.junit.Assert.assertNotNull(rectangleConstraint11);
        org.junit.Assert.assertNull(range12);
    }

    @Test
    public void test604() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test604");
        org.jfree.chart.block.EmptyBlock emptyBlock2 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D3 = emptyBlock2.getBounds();
        org.jfree.data.Range range4 = null;
        org.jfree.data.Range range6 = org.jfree.data.Range.expandToInclude(range4, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint8 = new org.jfree.chart.block.RectangleConstraint(range6, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint9 = rectangleConstraint8.toUnconstrainedWidth();
        double double10 = rectangleConstraint9.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint11 = emptyBlock2.toContentConstraint(rectangleConstraint9);
        double double13 = emptyBlock2.trimToContentHeight((double) (byte) 0);
        java.awt.Graphics2D graphics2D14 = null;
        org.jfree.chart.block.EmptyBlock emptyBlock17 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D18 = emptyBlock17.getBounds();
        java.awt.geom.Rectangle2D rectangle2D19 = emptyBlock17.getBounds();
        // The following exception was thrown during execution in test generation
        try {
            emptyBlock2.draw(graphics2D14, rectangle2D19);
            org.junit.Assert.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
            // Expected exception.
        }
        org.junit.Assert.assertNotNull(rectangle2D3);
        org.junit.Assert.assertNotNull(range6);
        org.junit.Assert.assertNotNull(rectangleConstraint9);
        org.junit.Assert.assertTrue("'" + double10 + "' != '" + 0.0d + "'", double10 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleConstraint11);
        org.junit.Assert.assertTrue("'" + double13 + "' != '" + 0.0d + "'", double13 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D18);
        org.junit.Assert.assertNotNull(rectangle2D19);
    }

    @Test
    public void test605() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test605");
        org.jfree.chart.block.EmptyBlock emptyBlock2 = new org.jfree.chart.block.EmptyBlock((-1.0d), 0.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets3 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double5 = rectangleInsets3.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock8 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D9 = emptyBlock8.getBounds();
        java.awt.geom.Rectangle2D rectangle2D12 = rectangleInsets3.createOutsetRectangle(rectangle2D9, true, false);
        java.awt.geom.Rectangle2D rectangle2D13 = emptyBlock2.trimBorder(rectangle2D9);
        java.awt.Graphics2D graphics2D14 = null;
        org.jfree.chart.util.Size2D size2D15 = emptyBlock2.arrange(graphics2D14);
        org.jfree.chart.block.BorderArrangement borderArrangement16 = new org.jfree.chart.block.BorderArrangement();
        boolean boolean17 = size2D15.equals((java.lang.Object) borderArrangement16);
        borderArrangement16.clear();
        org.jfree.chart.util.RectangleInsets rectangleInsets19 = new org.jfree.chart.util.RectangleInsets();
        double double20 = rectangleInsets19.getTop();
        double double21 = rectangleInsets19.getLeft();
        boolean boolean22 = borderArrangement16.equals((java.lang.Object) double21);
        org.jfree.chart.block.BorderArrangement borderArrangement23 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement23.clear();
        borderArrangement23.clear();
        borderArrangement23.clear();
        org.jfree.chart.block.BlockContainer blockContainer27 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement23);
        org.jfree.chart.util.RectangleInsets rectangleInsets28 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double30 = rectangleInsets28.calculateTopOutset((double) 1.0f);
        double double32 = rectangleInsets28.trimHeight((-1.0d));
        blockContainer27.setPadding(rectangleInsets28);
        double double34 = blockContainer27.getContentYOffset();
        org.jfree.chart.block.EmptyBlock emptyBlock37 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        org.jfree.chart.util.RectangleInsets rectangleInsets38 = emptyBlock37.getPadding();
        org.jfree.chart.block.BlockFrame blockFrame39 = emptyBlock37.getFrame();
        blockContainer27.setFrame(blockFrame39);
        blockContainer27.setID("Size2D[width=0.0, height=0.0]");
        blockContainer27.setHeight((double) 1.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock47 = new org.jfree.chart.block.EmptyBlock(0.0d, (double) 10);
        java.awt.geom.Rectangle2D rectangle2D48 = emptyBlock47.getBounds();
        java.awt.geom.Rectangle2D rectangle2D49 = blockContainer27.trimMargin(rectangle2D48);
        java.awt.Graphics2D graphics2D50 = null;
        org.jfree.data.Range range51 = null;
        org.jfree.data.Range range53 = org.jfree.data.Range.expandToInclude(range51, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint55 = new org.jfree.chart.block.RectangleConstraint(range53, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint56 = rectangleConstraint55.toUnconstrainedWidth();
        org.jfree.data.Range range57 = rectangleConstraint56.getWidthRange();
        double double59 = range57.constrain((double) (byte) 1);
        org.jfree.data.Range range60 = null;
        org.jfree.data.Range range62 = org.jfree.data.Range.expandToInclude(range60, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint64 = new org.jfree.chart.block.RectangleConstraint(range62, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint65 = rectangleConstraint64.toUnconstrainedWidth();
        org.jfree.data.Range range66 = rectangleConstraint65.getWidthRange();
        double double67 = range66.getCentralValue();
        org.jfree.data.Range range68 = org.jfree.data.Range.combine(range57, range66);
        boolean boolean70 = range66.contains((double) (short) 1);
        double double71 = range66.getCentralValue();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint73 = new org.jfree.chart.block.RectangleConstraint(range66, (double) 1L);
        java.lang.String str74 = rectangleConstraint73.toString();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint75 = rectangleConstraint73.toUnconstrainedWidth();
        // The following exception was thrown during execution in test generation
        try {
            org.jfree.chart.util.Size2D size2D76 = borderArrangement16.arrangeFR(blockContainer27, graphics2D50, rectangleConstraint75);
            org.junit.Assert.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
            // Expected exception.
        }
        org.junit.Assert.assertNotNull(rectangleInsets3);
        org.junit.Assert.assertTrue("'" + double5 + "' != '" + 0.0d + "'", double5 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D9);
        org.junit.Assert.assertNotNull(rectangle2D12);
        org.junit.Assert.assertNotNull(rectangle2D13);
        org.junit.Assert.assertNotNull(size2D15);
        org.junit.Assert.assertTrue("'" + boolean17 + "' != '" + false + "'", boolean17 == false);
        org.junit.Assert.assertTrue("'" + double20 + "' != '" + 1.0d + "'", double20 == 1.0d);
        org.junit.Assert.assertTrue("'" + double21 + "' != '" + 1.0d + "'", double21 == 1.0d);
        org.junit.Assert.assertTrue("'" + boolean22 + "' != '" + false + "'", boolean22 == false);
        org.junit.Assert.assertNotNull(rectangleInsets28);
        org.junit.Assert.assertTrue("'" + double30 + "' != '" + 0.0d + "'", double30 == 0.0d);
        org.junit.Assert.assertTrue("'" + double32 + "' != '" + (-1.0d) + "'", double32 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double34 + "' != '" + 0.0d + "'", double34 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets38);
        org.junit.Assert.assertNotNull(blockFrame39);
        org.junit.Assert.assertNotNull(rectangle2D48);
        org.junit.Assert.assertNotNull(rectangle2D49);
        org.junit.Assert.assertNotNull(range53);
        org.junit.Assert.assertNotNull(rectangleConstraint56);
        org.junit.Assert.assertNotNull(range57);
        org.junit.Assert.assertTrue("'" + double59 + "' != '" + (-1.0d) + "'", double59 == (-1.0d));
        org.junit.Assert.assertNotNull(range62);
        org.junit.Assert.assertNotNull(rectangleConstraint65);
        org.junit.Assert.assertNotNull(range66);
        org.junit.Assert.assertTrue("'" + double67 + "' != '" + (-1.0d) + "'", double67 == (-1.0d));
        org.junit.Assert.assertNotNull(range68);
        org.junit.Assert.assertTrue("'" + boolean70 + "' != '" + false + "'", boolean70 == false);
        org.junit.Assert.assertTrue("'" + double71 + "' != '" + (-1.0d) + "'", double71 == (-1.0d));
        org.junit.Assert.assertEquals("'" + str74 + "' != '" + "RectangleConstraint[RectangleConstraintType.RANGE: width=0.0, height=1.0]" + "'", str74, "RectangleConstraint[RectangleConstraintType.RANGE: width=0.0, height=1.0]");
        org.junit.Assert.assertNotNull(rectangleConstraint75);
    }

    @Test
    public void test606() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test606");
        org.jfree.data.Range range0 = null;
        org.jfree.data.Range range2 = org.jfree.data.Range.expandToInclude(range0, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint4 = new org.jfree.chart.block.RectangleConstraint(range2, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint5 = rectangleConstraint4.toUnconstrainedWidth();
        org.jfree.data.Range range6 = rectangleConstraint5.getWidthRange();
        double double8 = range6.constrain((double) (byte) 1);
        org.jfree.data.Range range9 = null;
        org.jfree.data.Range range11 = org.jfree.data.Range.expandToInclude(range9, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint13 = new org.jfree.chart.block.RectangleConstraint(range11, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint14 = rectangleConstraint13.toUnconstrainedWidth();
        org.jfree.data.Range range15 = rectangleConstraint14.getWidthRange();
        double double16 = range15.getCentralValue();
        org.jfree.data.Range range17 = org.jfree.data.Range.combine(range6, range15);
        double double18 = range17.getLength();
        double double19 = range17.getUpperBound();
        org.junit.Assert.assertNotNull(range2);
        org.junit.Assert.assertNotNull(rectangleConstraint5);
        org.junit.Assert.assertNotNull(range6);
        org.junit.Assert.assertTrue("'" + double8 + "' != '" + (-1.0d) + "'", double8 == (-1.0d));
        org.junit.Assert.assertNotNull(range11);
        org.junit.Assert.assertNotNull(rectangleConstraint14);
        org.junit.Assert.assertNotNull(range15);
        org.junit.Assert.assertTrue("'" + double16 + "' != '" + (-1.0d) + "'", double16 == (-1.0d));
        org.junit.Assert.assertNotNull(range17);
        org.junit.Assert.assertTrue("'" + double18 + "' != '" + 0.0d + "'", double18 == 0.0d);
        org.junit.Assert.assertTrue("'" + double19 + "' != '" + (-1.0d) + "'", double19 == (-1.0d));
    }

    @Test
    public void test607() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test607");
        org.jfree.chart.util.RectangleInsets rectangleInsets0 = new org.jfree.chart.util.RectangleInsets();
        double double1 = rectangleInsets0.getLeft();
        double double3 = rectangleInsets0.calculateTopInset((double) (byte) 10);
        double double5 = rectangleInsets0.calculateBottomOutset(9.0d);
        org.jfree.data.Range range6 = null;
        org.jfree.data.Range range8 = org.jfree.data.Range.expandToInclude(range6, (double) (short) -1);
        double double10 = range8.constrain((double) (byte) -1);
        org.jfree.data.Range range12 = org.jfree.data.Range.expandToInclude(range8, (double) 100L);
        org.jfree.chart.block.BorderArrangement borderArrangement13 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement13.clear();
        borderArrangement13.clear();
        borderArrangement13.clear();
        org.jfree.chart.block.BlockContainer blockContainer17 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement13);
        double double18 = blockContainer17.getWidth();
        java.util.List list19 = blockContainer17.getBlocks();
        org.jfree.chart.block.Arrangement arrangement20 = blockContainer17.getArrangement();
        org.jfree.chart.util.RectangleInsets rectangleInsets21 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double23 = rectangleInsets21.calculateBottomInset((double) 0.0f);
        org.jfree.chart.util.RectangleInsets rectangleInsets24 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double26 = rectangleInsets24.calculateBottomInset((double) 0.0f);
        double double27 = rectangleInsets24.getBottom();
        double double28 = rectangleInsets24.getTop();
        org.jfree.chart.util.RectangleInsets rectangleInsets29 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double31 = rectangleInsets29.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock34 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D35 = emptyBlock34.getBounds();
        java.awt.geom.Rectangle2D rectangle2D38 = rectangleInsets29.createOutsetRectangle(rectangle2D35, true, false);
        java.awt.geom.Rectangle2D rectangle2D39 = rectangleInsets24.createInsetRectangle(rectangle2D35);
        java.awt.geom.Rectangle2D rectangle2D40 = rectangleInsets21.createInsetRectangle(rectangle2D39);
        java.awt.geom.Rectangle2D rectangle2D41 = blockContainer17.trimMargin(rectangle2D40);
        boolean boolean42 = range8.equals((java.lang.Object) rectangle2D40);
        rectangleInsets0.trim(rectangle2D40);
        double double45 = rectangleInsets0.trimHeight(90.0d);
        org.junit.Assert.assertTrue("'" + double1 + "' != '" + 1.0d + "'", double1 == 1.0d);
        org.junit.Assert.assertTrue("'" + double3 + "' != '" + 1.0d + "'", double3 == 1.0d);
        org.junit.Assert.assertTrue("'" + double5 + "' != '" + 1.0d + "'", double5 == 1.0d);
        org.junit.Assert.assertNotNull(range8);
        org.junit.Assert.assertTrue("'" + double10 + "' != '" + (-1.0d) + "'", double10 == (-1.0d));
        org.junit.Assert.assertNotNull(range12);
        org.junit.Assert.assertTrue("'" + double18 + "' != '" + 0.0d + "'", double18 == 0.0d);
        org.junit.Assert.assertNotNull(list19);
        org.junit.Assert.assertNotNull(arrangement20);
        org.junit.Assert.assertNotNull(rectangleInsets21);
        org.junit.Assert.assertTrue("'" + double23 + "' != '" + 0.0d + "'", double23 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets24);
        org.junit.Assert.assertTrue("'" + double26 + "' != '" + 0.0d + "'", double26 == 0.0d);
        org.junit.Assert.assertTrue("'" + double27 + "' != '" + 0.0d + "'", double27 == 0.0d);
        org.junit.Assert.assertTrue("'" + double28 + "' != '" + 0.0d + "'", double28 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets29);
        org.junit.Assert.assertTrue("'" + double31 + "' != '" + 0.0d + "'", double31 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D35);
        org.junit.Assert.assertNotNull(rectangle2D38);
        org.junit.Assert.assertNotNull(rectangle2D39);
        org.junit.Assert.assertNotNull(rectangle2D40);
        org.junit.Assert.assertNotNull(rectangle2D41);
        org.junit.Assert.assertTrue("'" + boolean42 + "' != '" + false + "'", boolean42 == false);
        org.junit.Assert.assertTrue("'" + double45 + "' != '" + 88.0d + "'", double45 == 88.0d);
    }

    @Test
    public void test608() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test608");
        org.jfree.data.Range range0 = null;
        org.jfree.data.Range range2 = org.jfree.data.Range.expandToInclude(range0, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint4 = new org.jfree.chart.block.RectangleConstraint(range2, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint5 = rectangleConstraint4.toUnconstrainedWidth();
        org.jfree.chart.util.Size2D size2D6 = new org.jfree.chart.util.Size2D();
        double double7 = size2D6.width;
        org.jfree.chart.util.Size2D size2D8 = rectangleConstraint4.calculateConstrainedSize(size2D6);
        java.lang.String str9 = size2D8.toString();
        size2D8.setHeight((double) ' ');
        double double12 = size2D8.width;
        org.junit.Assert.assertNotNull(range2);
        org.junit.Assert.assertNotNull(rectangleConstraint5);
        org.junit.Assert.assertTrue("'" + double7 + "' != '" + 0.0d + "'", double7 == 0.0d);
        org.junit.Assert.assertNotNull(size2D8);
        org.junit.Assert.assertEquals("'" + str9 + "' != '" + "Size2D[width=-1.0, height=1.0]" + "'", str9, "Size2D[width=-1.0, height=1.0]");
        org.junit.Assert.assertTrue("'" + double12 + "' != '" + (-1.0d) + "'", double12 == (-1.0d));
    }

    @Test
    public void test609() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test609");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement0.clear();
        borderArrangement0.clear();
        borderArrangement0.clear();
        org.jfree.chart.block.BlockContainer blockContainer4 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement0);
        double double5 = blockContainer4.getWidth();
        blockContainer4.clear();
        java.awt.Graphics2D graphics2D7 = null;
        org.jfree.chart.util.Size2D size2D8 = blockContainer4.arrange(graphics2D7);
        org.jfree.chart.block.EmptyBlock emptyBlock11 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D12 = emptyBlock11.getBounds();
        blockContainer4.setBounds(rectangle2D12);
        java.awt.Graphics2D graphics2D14 = null;
        org.jfree.data.Range range15 = null;
        org.jfree.data.Range range17 = org.jfree.data.Range.expandToInclude(range15, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint19 = new org.jfree.chart.block.RectangleConstraint(range17, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint20 = rectangleConstraint19.toUnconstrainedWidth();
        org.jfree.data.Range range21 = rectangleConstraint20.getWidthRange();
        // The following exception was thrown during execution in test generation
        try {
            org.jfree.chart.util.Size2D size2D22 = blockContainer4.arrange(graphics2D14, rectangleConstraint20);
            org.junit.Assert.fail("Expected exception of type java.lang.RuntimeException; message: Not implemented.");
        } catch (java.lang.RuntimeException e) {
            // Expected exception.
        }
        org.junit.Assert.assertTrue("'" + double5 + "' != '" + 0.0d + "'", double5 == 0.0d);
        org.junit.Assert.assertNotNull(size2D8);
        org.junit.Assert.assertNotNull(rectangle2D12);
        org.junit.Assert.assertNotNull(range17);
        org.junit.Assert.assertNotNull(rectangleConstraint20);
        org.junit.Assert.assertNotNull(range21);
    }

    @Test
    public void test610() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test610");
        org.jfree.data.Range range2 = new org.jfree.data.Range(52.0d, 90.0d);
        boolean boolean5 = range2.intersects(11.0d, (double) (byte) 10);
        org.junit.Assert.assertTrue("'" + boolean5 + "' != '" + false + "'", boolean5 == false);
    }

    @Test
    public void test611() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test611");
        org.jfree.chart.util.Size2D size2D0 = new org.jfree.chart.util.Size2D();
        java.lang.String str1 = size2D0.toString();
        double double2 = size2D0.getHeight();
        double double3 = size2D0.getHeight();
        org.junit.Assert.assertEquals("'" + str1 + "' != '" + "Size2D[width=0.0, height=0.0]" + "'", str1, "Size2D[width=0.0, height=0.0]");
        org.junit.Assert.assertTrue("'" + double2 + "' != '" + 0.0d + "'", double2 == 0.0d);
        org.junit.Assert.assertTrue("'" + double3 + "' != '" + 0.0d + "'", double3 == 0.0d);
    }

    @Test
    public void test612() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test612");
        org.jfree.chart.block.BorderArrangement borderArrangement0 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement0.clear();
        org.jfree.chart.block.BlockContainer blockContainer2 = new org.jfree.chart.block.BlockContainer();
        org.jfree.data.Range range3 = null;
        org.jfree.data.Range range5 = org.jfree.data.Range.expandToInclude(range3, (double) (short) -1);
        java.lang.String str6 = range5.toString();
        org.jfree.data.Range range8 = org.jfree.data.Range.expandToInclude(range5, (double) (-1L));
        org.jfree.data.Range range9 = null;
        org.jfree.data.Range range11 = org.jfree.data.Range.expandToInclude(range9, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint13 = new org.jfree.chart.block.RectangleConstraint(range11, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint14 = rectangleConstraint13.toUnconstrainedWidth();
        org.jfree.data.Range range15 = rectangleConstraint14.getWidthRange();
        double double16 = range15.getUpperBound();
        double double18 = range15.constrain((double) (-1L));
        double double19 = range15.getUpperBound();
        org.jfree.data.Range range20 = org.jfree.data.Range.combine(range5, range15);
        org.jfree.data.Range range21 = null;
        org.jfree.data.Range range23 = org.jfree.data.Range.expandToInclude(range21, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint25 = new org.jfree.chart.block.RectangleConstraint(range23, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint26 = rectangleConstraint25.toUnconstrainedWidth();
        org.jfree.data.Range range27 = rectangleConstraint26.getWidthRange();
        org.jfree.data.Range range28 = null;
        org.jfree.data.Range range30 = org.jfree.data.Range.expandToInclude(range28, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint32 = new org.jfree.chart.block.RectangleConstraint(range30, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint33 = rectangleConstraint32.toUnconstrainedWidth();
        double double34 = rectangleConstraint33.getWidth();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint35 = rectangleConstraint33.toUnconstrainedHeight();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint37 = rectangleConstraint35.toFixedHeight((double) 100.0f);
        org.jfree.data.Range range39 = null;
        org.jfree.data.Range range41 = org.jfree.data.Range.expandToInclude(range39, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint43 = new org.jfree.chart.block.RectangleConstraint(range41, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint44 = rectangleConstraint43.toUnconstrainedWidth();
        org.jfree.data.Range range45 = rectangleConstraint44.getWidthRange();
        double double47 = range45.constrain((double) (byte) 1);
        org.jfree.data.Range range48 = null;
        org.jfree.data.Range range50 = org.jfree.data.Range.expandToInclude(range48, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint52 = new org.jfree.chart.block.RectangleConstraint(range50, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint53 = rectangleConstraint52.toUnconstrainedWidth();
        org.jfree.data.Range range54 = rectangleConstraint53.getWidthRange();
        double double55 = range54.getCentralValue();
        org.jfree.data.Range range56 = org.jfree.data.Range.combine(range45, range54);
        double double57 = range54.getCentralValue();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint58 = new org.jfree.chart.block.RectangleConstraint((double) (byte) 100, range54);
        org.jfree.data.Range range61 = org.jfree.data.Range.expand(range54, 0.0d, (double) 1L);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint62 = rectangleConstraint35.toRangeWidth(range54);
        org.jfree.data.Range range65 = org.jfree.data.Range.shift(range54, (double) (short) 100, false);
        double double67 = range65.constrain((double) 1);
        java.lang.String str68 = range65.toString();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint69 = rectangleConstraint26.toRangeHeight(range65);
        double double71 = range65.constrain(3.0d);
        java.awt.Graphics2D graphics2D72 = null;
        org.jfree.chart.util.Size2D size2D73 = borderArrangement0.arrangeRR(blockContainer2, range15, range65, graphics2D72);
        org.junit.Assert.assertNotNull(range5);
        org.junit.Assert.assertEquals("'" + str6 + "' != '" + "Range[-1.0,-1.0]" + "'", str6, "Range[-1.0,-1.0]");
        org.junit.Assert.assertNotNull(range8);
        org.junit.Assert.assertNotNull(range11);
        org.junit.Assert.assertNotNull(rectangleConstraint14);
        org.junit.Assert.assertNotNull(range15);
        org.junit.Assert.assertTrue("'" + double16 + "' != '" + (-1.0d) + "'", double16 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double18 + "' != '" + (-1.0d) + "'", double18 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double19 + "' != '" + (-1.0d) + "'", double19 == (-1.0d));
        org.junit.Assert.assertNotNull(range20);
        org.junit.Assert.assertNotNull(range23);
        org.junit.Assert.assertNotNull(rectangleConstraint26);
        org.junit.Assert.assertNotNull(range27);
        org.junit.Assert.assertNotNull(range30);
        org.junit.Assert.assertNotNull(rectangleConstraint33);
        org.junit.Assert.assertTrue("'" + double34 + "' != '" + 0.0d + "'", double34 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleConstraint35);
        org.junit.Assert.assertNotNull(rectangleConstraint37);
        org.junit.Assert.assertNotNull(range41);
        org.junit.Assert.assertNotNull(rectangleConstraint44);
        org.junit.Assert.assertNotNull(range45);
        org.junit.Assert.assertTrue("'" + double47 + "' != '" + (-1.0d) + "'", double47 == (-1.0d));
        org.junit.Assert.assertNotNull(range50);
        org.junit.Assert.assertNotNull(rectangleConstraint53);
        org.junit.Assert.assertNotNull(range54);
        org.junit.Assert.assertTrue("'" + double55 + "' != '" + (-1.0d) + "'", double55 == (-1.0d));
        org.junit.Assert.assertNotNull(range56);
        org.junit.Assert.assertTrue("'" + double57 + "' != '" + (-1.0d) + "'", double57 == (-1.0d));
        org.junit.Assert.assertNotNull(range61);
        org.junit.Assert.assertNotNull(rectangleConstraint62);
        org.junit.Assert.assertNotNull(range65);
        org.junit.Assert.assertTrue("'" + double67 + "' != '" + 0.0d + "'", double67 == 0.0d);
        org.junit.Assert.assertEquals("'" + str68 + "' != '" + "Range[0.0,0.0]" + "'", str68, "Range[0.0,0.0]");
        org.junit.Assert.assertNotNull(rectangleConstraint69);
        org.junit.Assert.assertTrue("'" + double71 + "' != '" + 0.0d + "'", double71 == 0.0d);
        org.junit.Assert.assertNotNull(size2D73);
    }

    @Test
    public void test613() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test613");
        org.jfree.chart.util.RectangleInsets rectangleInsets0 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double2 = rectangleInsets0.calculateBottomInset((double) 0.0f);
        org.jfree.chart.util.UnitType unitType3 = rectangleInsets0.getUnitType();
        java.lang.String str4 = unitType3.toString();
        org.jfree.chart.block.BlockBorder blockBorder5 = org.jfree.chart.block.BlockBorder.NONE;
        boolean boolean6 = unitType3.equals((java.lang.Object) blockBorder5);
        org.jfree.chart.util.RectangleInsets rectangleInsets7 = blockBorder5.getInsets();
        org.jfree.chart.util.RectangleInsets rectangleInsets8 = blockBorder5.getInsets();
        java.awt.Paint paint9 = blockBorder5.getPaint();
        org.jfree.chart.util.RectangleInsets rectangleInsets10 = blockBorder5.getInsets();
        org.junit.Assert.assertNotNull(rectangleInsets0);
        org.junit.Assert.assertTrue("'" + double2 + "' != '" + 0.0d + "'", double2 == 0.0d);
        org.junit.Assert.assertNotNull(unitType3);
        org.junit.Assert.assertEquals("'" + str4 + "' != '" + "UnitType.ABSOLUTE" + "'", str4, "UnitType.ABSOLUTE");
        org.junit.Assert.assertNotNull(blockBorder5);
        org.junit.Assert.assertTrue("'" + boolean6 + "' != '" + false + "'", boolean6 == false);
        org.junit.Assert.assertNotNull(rectangleInsets7);
        org.junit.Assert.assertNotNull(rectangleInsets8);
        org.junit.Assert.assertNotNull(paint9);
        org.junit.Assert.assertNotNull(rectangleInsets10);
    }

    @Test
    public void test614() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test614");
        org.jfree.data.Range range1 = null;
        org.jfree.data.Range range3 = org.jfree.data.Range.expandToInclude(range1, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint5 = new org.jfree.chart.block.RectangleConstraint(range3, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint6 = rectangleConstraint5.toUnconstrainedWidth();
        org.jfree.data.Range range7 = rectangleConstraint6.getWidthRange();
        double double9 = range7.constrain((double) (byte) 1);
        org.jfree.chart.block.LengthConstraintType lengthConstraintType10 = org.jfree.chart.block.LengthConstraintType.FIXED;
        org.jfree.data.Range range12 = null;
        org.jfree.data.Range range14 = org.jfree.data.Range.expandToInclude(range12, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint16 = new org.jfree.chart.block.RectangleConstraint(range14, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint17 = rectangleConstraint16.toUnconstrainedWidth();
        org.jfree.data.Range range18 = rectangleConstraint17.getWidthRange();
        double double20 = range18.constrain((double) (byte) 1);
        org.jfree.data.Range range21 = null;
        org.jfree.data.Range range23 = org.jfree.data.Range.expandToInclude(range21, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint25 = new org.jfree.chart.block.RectangleConstraint(range23, (double) 1.0f);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint26 = rectangleConstraint25.toUnconstrainedWidth();
        org.jfree.data.Range range27 = rectangleConstraint26.getWidthRange();
        double double28 = range27.getCentralValue();
        org.jfree.data.Range range29 = org.jfree.data.Range.combine(range18, range27);
        java.lang.String str30 = range29.toString();
        org.jfree.chart.block.LengthConstraintType lengthConstraintType31 = org.jfree.chart.block.LengthConstraintType.FIXED;
        org.jfree.chart.block.BorderArrangement borderArrangement32 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer33 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement32);
        java.awt.geom.Rectangle2D rectangle2D34 = blockContainer33.getBounds();
        org.jfree.chart.util.RectangleInsets rectangleInsets35 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double37 = rectangleInsets35.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock40 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D41 = emptyBlock40.getBounds();
        java.awt.geom.Rectangle2D rectangle2D44 = rectangleInsets35.createOutsetRectangle(rectangle2D41, true, false);
        blockContainer33.setBounds(rectangle2D44);
        boolean boolean46 = lengthConstraintType31.equals((java.lang.Object) blockContainer33);
        org.jfree.chart.util.RectangleInsets rectangleInsets47 = new org.jfree.chart.util.RectangleInsets();
        double double48 = rectangleInsets47.getTop();
        double double49 = rectangleInsets47.getLeft();
        boolean boolean50 = lengthConstraintType31.equals((java.lang.Object) rectangleInsets47);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint51 = new org.jfree.chart.block.RectangleConstraint((double) 10L, range7, lengthConstraintType10, (double) (short) 10, range29, lengthConstraintType31);
        org.jfree.data.Range range52 = null;
        org.jfree.data.Range range53 = org.jfree.data.Range.combine(range7, range52);
        org.jfree.data.Range range56 = org.jfree.data.Range.shift(range7, (double) 10L, false);
        org.junit.Assert.assertNotNull(range3);
        org.junit.Assert.assertNotNull(rectangleConstraint6);
        org.junit.Assert.assertNotNull(range7);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + (-1.0d) + "'", double9 == (-1.0d));
        org.junit.Assert.assertNotNull(lengthConstraintType10);
        org.junit.Assert.assertNotNull(range14);
        org.junit.Assert.assertNotNull(rectangleConstraint17);
        org.junit.Assert.assertNotNull(range18);
        org.junit.Assert.assertTrue("'" + double20 + "' != '" + (-1.0d) + "'", double20 == (-1.0d));
        org.junit.Assert.assertNotNull(range23);
        org.junit.Assert.assertNotNull(rectangleConstraint26);
        org.junit.Assert.assertNotNull(range27);
        org.junit.Assert.assertTrue("'" + double28 + "' != '" + (-1.0d) + "'", double28 == (-1.0d));
        org.junit.Assert.assertNotNull(range29);
        org.junit.Assert.assertEquals("'" + str30 + "' != '" + "Range[-1.0,-1.0]" + "'", str30, "Range[-1.0,-1.0]");
        org.junit.Assert.assertNotNull(lengthConstraintType31);
        org.junit.Assert.assertNotNull(rectangle2D34);
        org.junit.Assert.assertNotNull(rectangleInsets35);
        org.junit.Assert.assertTrue("'" + double37 + "' != '" + 0.0d + "'", double37 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D41);
        org.junit.Assert.assertNotNull(rectangle2D44);
        org.junit.Assert.assertTrue("'" + boolean46 + "' != '" + false + "'", boolean46 == false);
        org.junit.Assert.assertTrue("'" + double48 + "' != '" + 1.0d + "'", double48 == 1.0d);
        org.junit.Assert.assertTrue("'" + double49 + "' != '" + 1.0d + "'", double49 == 1.0d);
        org.junit.Assert.assertTrue("'" + boolean50 + "' != '" + false + "'", boolean50 == false);
        org.junit.Assert.assertNotNull(range53);
        org.junit.Assert.assertNotNull(range56);
    }

    @Test
    public void test615() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test615");
        org.jfree.chart.block.EmptyBlock emptyBlock2 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.lang.Object obj3 = emptyBlock2.clone();
        emptyBlock2.setMargin((double) (-1.0f), (double) (byte) 10, (double) 'a', (double) 0L);
        org.jfree.chart.util.RectangleInsets rectangleInsets9 = emptyBlock2.getPadding();
        java.awt.geom.Rectangle2D rectangle2D10 = emptyBlock2.getBounds();
        org.jfree.chart.block.BorderArrangement borderArrangement11 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer12 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement11);
        java.util.List list13 = blockContainer12.getBlocks();
        blockContainer12.setMargin((double) ' ', (double) 100.0f, 0.0d, (double) 'a');
        org.jfree.chart.util.RectangleInsets rectangleInsets19 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double21 = rectangleInsets19.calculateBottomInset((double) 0.0f);
        org.jfree.chart.util.UnitType unitType22 = rectangleInsets19.getUnitType();
        java.lang.String str23 = unitType22.toString();
        org.jfree.chart.block.BlockBorder blockBorder24 = org.jfree.chart.block.BlockBorder.NONE;
        boolean boolean25 = unitType22.equals((java.lang.Object) blockBorder24);
        blockContainer12.setFrame((org.jfree.chart.block.BlockFrame) blockBorder24);
        emptyBlock2.setFrame((org.jfree.chart.block.BlockFrame) blockBorder24);
        org.jfree.chart.util.RectangleInsets rectangleInsets28 = blockBorder24.getInsets();
        org.jfree.data.Range range30 = null;
        org.jfree.data.Range range32 = org.jfree.data.Range.expandToInclude(range30, (double) (short) -1);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint33 = new org.jfree.chart.block.RectangleConstraint(33.0d, range32);
        org.jfree.chart.block.RectangleConstraint rectangleConstraint35 = rectangleConstraint33.toFixedWidth((double) 10);
        java.lang.String str36 = rectangleConstraint35.toString();
        org.jfree.chart.block.LengthConstraintType lengthConstraintType37 = rectangleConstraint35.getHeightConstraintType();
        org.jfree.chart.block.RectangleConstraint rectangleConstraint39 = rectangleConstraint35.toFixedHeight((double) 0L);
        org.jfree.data.Range range40 = rectangleConstraint39.getHeightRange();
        boolean boolean41 = blockBorder24.equals((java.lang.Object) rectangleConstraint39);
        org.junit.Assert.assertNotNull(obj3);
        org.junit.Assert.assertNotNull(rectangleInsets9);
        org.junit.Assert.assertNotNull(rectangle2D10);
        org.junit.Assert.assertNotNull(list13);
        org.junit.Assert.assertNotNull(rectangleInsets19);
        org.junit.Assert.assertTrue("'" + double21 + "' != '" + 0.0d + "'", double21 == 0.0d);
        org.junit.Assert.assertNotNull(unitType22);
        org.junit.Assert.assertEquals("'" + str23 + "' != '" + "UnitType.ABSOLUTE" + "'", str23, "UnitType.ABSOLUTE");
        org.junit.Assert.assertNotNull(blockBorder24);
        org.junit.Assert.assertTrue("'" + boolean25 + "' != '" + false + "'", boolean25 == false);
        org.junit.Assert.assertNotNull(rectangleInsets28);
        org.junit.Assert.assertNotNull(range32);
        org.junit.Assert.assertNotNull(rectangleConstraint35);
        org.junit.Assert.assertEquals("'" + str36 + "' != '" + "RectangleConstraint[LengthConstraintType.FIXED: width=10.0, height=0.0]" + "'", str36, "RectangleConstraint[LengthConstraintType.FIXED: width=10.0, height=0.0]");
        org.junit.Assert.assertNotNull(lengthConstraintType37);
        org.junit.Assert.assertNotNull(rectangleConstraint39);
        org.junit.Assert.assertNotNull(range40);
        org.junit.Assert.assertTrue("'" + boolean41 + "' != '" + false + "'", boolean41 == false);
    }

    @Test
    public void test616() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest1.test616");
        org.jfree.chart.util.Size2D size2D0 = new org.jfree.chart.util.Size2D();
        size2D0.setHeight((double) (short) 0);
        double double3 = size2D0.getWidth();
        double double4 = size2D0.getWidth();
        org.jfree.chart.util.RectangleInsets rectangleInsets5 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double7 = rectangleInsets5.calculateTopOutset((double) 1.0f);
        double double9 = rectangleInsets5.extendHeight((double) (byte) -1);
        boolean boolean10 = size2D0.equals((java.lang.Object) rectangleInsets5);
        org.jfree.chart.util.UnitType unitType11 = rectangleInsets5.getUnitType();
        org.jfree.chart.util.RectangleInsets rectangleInsets16 = new org.jfree.chart.util.RectangleInsets(unitType11, (double) 1, (double) ' ', (double) (short) 0, 100.0d);
        double double18 = rectangleInsets16.extendHeight((double) (short) 10);
        org.jfree.chart.block.BorderArrangement borderArrangement19 = new org.jfree.chart.block.BorderArrangement();
        org.jfree.chart.block.BlockContainer blockContainer20 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement19);
        java.util.List list21 = blockContainer20.getBlocks();
        blockContainer20.setMargin((double) ' ', (double) 100.0f, 0.0d, (double) 'a');
        org.jfree.chart.block.BorderArrangement borderArrangement27 = new org.jfree.chart.block.BorderArrangement();
        borderArrangement27.clear();
        borderArrangement27.clear();
        borderArrangement27.clear();
        org.jfree.chart.block.BlockContainer blockContainer31 = new org.jfree.chart.block.BlockContainer((org.jfree.chart.block.Arrangement) borderArrangement27);
        blockContainer20.add((org.jfree.chart.block.Block) blockContainer31);
        blockContainer20.setID("RectangleEdge.LEFT");
        org.jfree.chart.util.RectangleInsets rectangleInsets35 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double37 = rectangleInsets35.calculateBottomInset((double) 0.0f);
        org.jfree.chart.util.UnitType unitType38 = rectangleInsets35.getUnitType();
        java.lang.String str39 = unitType38.toString();
        org.jfree.chart.block.BlockBorder blockBorder40 = org.jfree.chart.block.BlockBorder.NONE;
        boolean boolean41 = unitType38.equals((java.lang.Object) blockBorder40);
        boolean boolean43 = blockBorder40.equals((java.lang.Object) 1.0f);
        blockContainer20.setFrame((org.jfree.chart.block.BlockFrame) blockBorder40);
        org.jfree.chart.util.RectangleInsets rectangleInsets45 = new org.jfree.chart.util.RectangleInsets();
        double double46 = rectangleInsets45.getLeft();
        double double48 = rectangleInsets45.extendWidth((double) (-1));
        org.jfree.chart.util.RectangleInsets rectangleInsets49 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        org.jfree.chart.util.RectangleInsets rectangleInsets50 = org.jfree.chart.util.RectangleInsets.ZERO_INSETS;
        double double52 = rectangleInsets50.calculateBottomInset((double) 0.0f);
        org.jfree.chart.block.EmptyBlock emptyBlock55 = new org.jfree.chart.block.EmptyBlock((double) (short) 10, 10.0d);
        java.awt.geom.Rectangle2D rectangle2D56 = emptyBlock55.getBounds();
        java.awt.geom.Rectangle2D rectangle2D59 = rectangleInsets50.createOutsetRectangle(rectangle2D56, true, false);
        java.awt.geom.Rectangle2D rectangle2D60 = rectangleInsets49.createInsetRectangle(rectangle2D59);
        java.awt.geom.Rectangle2D rectangle2D61 = rectangleInsets45.createInsetRectangle(rectangle2D60);
        java.awt.geom.Rectangle2D rectangle2D62 = blockContainer20.trimPadding(rectangle2D60);
        org.jfree.chart.util.LengthAdjustmentType lengthAdjustmentType63 = null;
        org.jfree.chart.util.LengthAdjustmentType lengthAdjustmentType64 = null;
        java.awt.geom.Rectangle2D rectangle2D65 = rectangleInsets16.createAdjustedRectangle(rectangle2D60, lengthAdjustmentType63, lengthAdjustmentType64);
        org.junit.Assert.assertTrue("'" + double3 + "' != '" + 0.0d + "'", double3 == 0.0d);
        org.junit.Assert.assertTrue("'" + double4 + "' != '" + 0.0d + "'", double4 == 0.0d);
        org.junit.Assert.assertNotNull(rectangleInsets5);
        org.junit.Assert.assertTrue("'" + double7 + "' != '" + 0.0d + "'", double7 == 0.0d);
        org.junit.Assert.assertTrue("'" + double9 + "' != '" + (-1.0d) + "'", double9 == (-1.0d));
        org.junit.Assert.assertTrue("'" + boolean10 + "' != '" + false + "'", boolean10 == false);
        org.junit.Assert.assertNotNull(unitType11);
        org.junit.Assert.assertTrue("'" + double18 + "' != '" + 11.0d + "'", double18 == 11.0d);
        org.junit.Assert.assertNotNull(list21);
        org.junit.Assert.assertNotNull(rectangleInsets35);
        org.junit.Assert.assertTrue("'" + double37 + "' != '" + 0.0d + "'", double37 == 0.0d);
        org.junit.Assert.assertNotNull(unitType38);
        org.junit.Assert.assertEquals("'" + str39 + "' != '" + "UnitType.ABSOLUTE" + "'", str39, "UnitType.ABSOLUTE");
        org.junit.Assert.assertNotNull(blockBorder40);
        org.junit.Assert.assertTrue("'" + boolean41 + "' != '" + false + "'", boolean41 == false);
        org.junit.Assert.assertTrue("'" + boolean43 + "' != '" + false + "'", boolean43 == false);
        org.junit.Assert.assertTrue("'" + double46 + "' != '" + 1.0d + "'", double46 == 1.0d);
        org.junit.Assert.assertTrue("'" + double48 + "' != '" + 1.0d + "'", double48 == 1.0d);
        org.junit.Assert.assertNotNull(rectangleInsets49);
        org.junit.Assert.assertNotNull(rectangleInsets50);
        org.junit.Assert.assertTrue("'" + double52 + "' != '" + 0.0d + "'", double52 == 0.0d);
        org.junit.Assert.assertNotNull(rectangle2D56);
        org.junit.Assert.assertNotNull(rectangle2D59);
        org.junit.Assert.assertNotNull(rectangle2D60);
        org.junit.Assert.assertNotNull(rectangle2D61);
        org.junit.Assert.assertNotNull(rectangle2D62);
        org.junit.Assert.assertNotNull(rectangle2D65);
    }
}

