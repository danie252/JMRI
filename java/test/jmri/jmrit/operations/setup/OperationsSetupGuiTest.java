package jmri.jmrit.operations.setup;

import java.awt.GraphicsEnvironment;
import jmri.jmrit.display.LocoIcon;
import jmri.jmrit.operations.OperationsSwingTestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.junit.Assert;

/**
 * Tests for the Operations Setup GUI class
 *
 * @author Dan Boudreau Copyright (C) 2009
 */
public class OperationsSetupGuiTest extends OperationsSwingTestCase {

    public void testDirectionCheckBoxes() {
        // it may be possible to make this a headless test by only initializing the panel, not the frame
        if (GraphicsEnvironment.isHeadless()) {
            return; // can't use Assume in TestCase subclasses
        }
        OperationsSetupFrame f = new OperationsSetupFrame();
        f.setLocation(0, 0); // entire panel must be visible for tests to work properly
        f.initComponents();
        OperationsSetupPanel p = (OperationsSetupPanel) f.getContentPane();

        // first confirm that setup has all directions selected
        Assert.assertEquals("All directions selected", Setup.EAST + Setup.WEST + Setup.NORTH + Setup.SOUTH,
                Setup.getTrainDirection());

        // both east/west and north/south checkboxes should be set
        Assert.assertTrue("North selected", p.northCheckBox.isSelected());
        Assert.assertTrue("East selected", p.eastCheckBox.isSelected());

        enterClickAndLeave(p.northCheckBox);
        Assert.assertFalse("North deselected", p.northCheckBox.isSelected());
        Assert.assertTrue("East selected", p.eastCheckBox.isSelected());

        enterClickAndLeave(p.eastCheckBox);
        Assert.assertTrue("North selected", p.northCheckBox.isSelected());
        Assert.assertFalse("East deselected", p.eastCheckBox.isSelected());

        enterClickAndLeave(p.eastCheckBox);
        Assert.assertTrue("North selected", p.northCheckBox.isSelected());
        Assert.assertTrue("East selected", p.eastCheckBox.isSelected());

        // done
        f.dispose();
    }

    public void testSetupFrameWrite() {
        // it may be possible to make this a headless test by only initializing the panel, not the frame
        if (GraphicsEnvironment.isHeadless()) {
            return; // can't use Assume in TestCase subclasses
        }
        // force creation of backup
        Setup.setCarTypes(Setup.AAR);

        OperationsSetupFrame f = new OperationsSetupFrame();
        f.setLocation(0, 0); // entire panel must be visible for tests to work properly
        f.initComponents();
        OperationsSetupPanel p = (OperationsSetupPanel) f.getContentPane();

        p.railroadNameTextField.setText("Test Railroad Name");
        p.maxLengthTextField.setText("1234");
        p.maxEngineSizeTextField.setText("6");
        p.switchTimeTextField.setText("3");
        p.travelTimeTextField.setText("4");

        enterClickAndLeave(p.scaleHO);
        enterClickAndLeave(p.typeDesc);

        p.panelTextField.setText("Test Panel Name");

        p.eastComboBox.setSelectedItem(LocoIcon.RED);
        p.westComboBox.setSelectedItem(LocoIcon.BLUE);
        p.northComboBox.setSelectedItem(LocoIcon.WHITE);
        p.southComboBox.setSelectedItem(LocoIcon.GREEN);
        p.terminateComboBox.setSelectedItem(LocoIcon.GRAY);
        p.localComboBox.setSelectedItem(LocoIcon.YELLOW);

        enterClickAndLeave(p.saveButton);
        // dialog window should appear regarding train lengths
        pressDialogButton(f, "OK");
        // dialog window should appear regarding railroad name
        pressDialogButton(f, "No");
        // done
        f.dispose();

        // it may be possible to make this a headless test by only initializing the panel, not the frame
        OperationsSetupFrame frameRead = new OperationsSetupFrame();
        frameRead.setLocation(0, 0); // entire panel must be visible for tests to work properly
        frameRead.initComponents();
        OperationsSetupPanel panelRead = (OperationsSetupPanel) frameRead.getContentPane();

        Assert.assertEquals("railroad name", "Test Railroad Name", panelRead.railroadNameTextField.getText());
        Assert.assertEquals("max length", "1234", panelRead.maxLengthTextField.getText());
        Assert.assertEquals("max engines", "6", panelRead.maxEngineSizeTextField.getText());
        Assert.assertEquals("switch time", "3", panelRead.switchTimeTextField.getText());
        Assert.assertEquals("travel time", "4", panelRead.travelTimeTextField.getText());
        // Assert.assertEquals("owner", "Bob J", f.ownerTextField.getText());

        Assert.assertTrue("HO scale", panelRead.scaleHO.isSelected());
        Assert.assertFalse("N scale", panelRead.scaleN.isSelected());
        Assert.assertFalse("Z scale", panelRead.scaleZ.isSelected());
        Assert.assertFalse("TT scale", panelRead.scaleTT.isSelected());
        Assert.assertFalse("HOn3 scale", panelRead.scaleHOn3.isSelected());
        Assert.assertFalse("OO scale", panelRead.scaleOO.isSelected());
        Assert.assertFalse("Sn3 scale", panelRead.scaleSn3.isSelected());
        Assert.assertFalse("S scale", panelRead.scaleS.isSelected());
        Assert.assertFalse("On3 scale", panelRead.scaleOn3.isSelected());
        Assert.assertFalse("O scale", panelRead.scaleO.isSelected());
        Assert.assertFalse("G scale", panelRead.scaleG.isSelected());

        Assert.assertTrue("descriptive", panelRead.typeDesc.isSelected());
        Assert.assertFalse("AAR", panelRead.typeAAR.isSelected());

        Assert.assertEquals("panel name", "Test Panel Name", panelRead.panelTextField.getText());

        Assert.assertEquals("east color", LocoIcon.RED, panelRead.eastComboBox.getSelectedItem());
        Assert.assertEquals("west color", LocoIcon.BLUE, panelRead.westComboBox.getSelectedItem());
        Assert.assertEquals("north color", LocoIcon.WHITE, panelRead.northComboBox.getSelectedItem());
        Assert.assertEquals("south color", LocoIcon.GREEN, panelRead.southComboBox.getSelectedItem());
        Assert.assertEquals("terminate color", LocoIcon.GRAY, panelRead.terminateComboBox.getSelectedItem());
        Assert.assertEquals("local color", LocoIcon.YELLOW, panelRead.localComboBox.getSelectedItem());
        // done
        frameRead.dispose();
    }

    public void testOptionFrameWrite() {
        if (GraphicsEnvironment.isHeadless()) {
            return; // can't use Assume in TestCase subclasses
        }
        OptionFrame f = new OptionFrame();
        f.setLocation(0, 0); // entire panel must be visible for tests to work properly
        f.initComponents();
        OptionPanel p = (OptionPanel) f.getContentPane();

        // confirm defaults
        Assert.assertTrue("build normal", p.buildNormal.isSelected());
        Assert.assertFalse("build aggressive", p.buildAggressive.isSelected());
        Assert.assertFalse("local", p.localSpurCheckBox.isSelected());
        Assert.assertFalse("interchange", p.localInterchangeCheckBox.isSelected());
        Assert.assertFalse("yard", p.localYardCheckBox.isSelected());
        Assert.assertFalse("rfid", p.rfidCheckBox.isSelected());
        Assert.assertFalse("car logger", p.carLoggerCheckBox.isSelected());
        Assert.assertFalse("engine logger", p.engineLoggerCheckBox.isSelected());
        Assert.assertTrue("router", p.routerCheckBox.isSelected());

        enterClickAndLeave(p.buildAggressive);
        Assert.assertFalse("build normal", p.buildNormal.isSelected());
        Assert.assertTrue("build aggressive", p.buildAggressive.isSelected());

        enterClickAndLeave(p.localSpurCheckBox);
        Assert.assertTrue("local", p.localSpurCheckBox.isSelected());

        enterClickAndLeave(p.localInterchangeCheckBox);
        Assert.assertTrue("interchange", p.localInterchangeCheckBox.isSelected());

        enterClickAndLeave(p.localYardCheckBox);
        Assert.assertTrue("yard", p.localYardCheckBox.isSelected());

        //        enterClickAndLeave(p.rfidCheckBox);
        // use doClick() in case the checkbox isn't visible due to scrollbars.
        p.rfidCheckBox.doClick();
        Assert.assertTrue("rfid", p.rfidCheckBox.isSelected());

        enterClickAndLeave(p.carLoggerCheckBox);
        Assert.assertTrue("car logger", p.carLoggerCheckBox.isSelected());

        enterClickAndLeave(p.engineLoggerCheckBox);
        Assert.assertTrue("engine logger", p.engineLoggerCheckBox.isSelected());

        enterClickAndLeave(p.routerCheckBox);
        Assert.assertFalse("router", p.routerCheckBox.isSelected());

        enterClickAndLeave(p.saveButton);
        // done
        f.dispose();

        f = new OptionFrame();
        f.setLocation(0, 0); // entire panel must be visible for tests to work properly
        f.initComponents();
        p = (OptionPanel) f.getContentPane();

        Assert.assertFalse("build normal", p.buildNormal.isSelected());
        Assert.assertTrue("build aggressive", p.buildAggressive.isSelected());
        Assert.assertTrue("local", p.localSpurCheckBox.isSelected());
        Assert.assertTrue("interchange", p.localInterchangeCheckBox.isSelected());
        Assert.assertTrue("yard", p.localYardCheckBox.isSelected());
        Assert.assertTrue("rfid", p.rfidCheckBox.isSelected());
        Assert.assertTrue("car logger", p.carLoggerCheckBox.isSelected());
        Assert.assertTrue("engine logger", p.engineLoggerCheckBox.isSelected());
        Assert.assertFalse("router", p.routerCheckBox.isSelected());

        // done
        f.dispose();
    }
  
    public void testBuildReportOptionFrame() {
        if (GraphicsEnvironment.isHeadless()) {
            return; // can't use Assume in TestCase subclasses
        }
        BuildReportOptionFrame f = new BuildReportOptionFrame();
        f.setLocation(0, 0); // entire panel must be visible for tests to work properly
        f.initComponents();
        
        // TODO do more testing
        
        // done
        f.dispose();        
    }
      
    public void testPrintMoreOptionFrame() {
        if (GraphicsEnvironment.isHeadless()) {
            return; // can't use Assume in TestCase subclasses
        }
        PrintMoreOptionFrame f = new PrintMoreOptionFrame();
        f.setLocation(0, 0); // entire panel must be visible for tests to work properly
        f.initComponents();
        
        // TODO do more testing
        
        // done
        f.dispose();        
    }
    
    public void testEditManifestTextFrame() {
        if (GraphicsEnvironment.isHeadless()) {
            return; // can't use Assume in TestCase subclasses
        }
        EditManifestTextFrame f = new EditManifestTextFrame();
        f.setLocation(0, 0); // entire panel must be visible for tests to work properly
        f.initComponents();
        
        // TODO do more testing
        
        // done
        f.dispose();        
    }
    
    public void testEditSwitchListTextFrame() {
        if (GraphicsEnvironment.isHeadless()) {
            return; // can't use Assume in TestCase subclasses
        }
        EditSwitchListTextFrame f = new EditSwitchListTextFrame();
        f.setLocation(0, 0); // entire panel must be visible for tests to work properly
        f.initComponents();
        
        // TODO do more testing
        
        // done
        f.dispose();        
    }
    
    public void testEditManifestHeaderTextFrame() {
        if (GraphicsEnvironment.isHeadless()) {
            return; // can't use Assume in TestCase subclasses
        }
        EditManifestHeaderTextFrame f = new EditManifestHeaderTextFrame();
        f.setLocation(0, 0); // entire panel must be visible for tests to work properly
        f.initComponents();
        
        // TODO do more testing
        
        // done
        f.dispose();        
    }
    
    public void testPrintOptionFrame() {
        if (GraphicsEnvironment.isHeadless()) {
            return; // can't use Assume in TestCase subclasses
        }
        PrintOptionFrame f = new PrintOptionFrame();
        f.setLocation(0, 0); // entire panel must be visible for tests to work properly
        f.initComponents();
        PrintOptionPanel p = (PrintOptionPanel) f.getContentPane();

        // confirm defaults
        Assert.assertFalse(p.tabFormatCheckBox.isSelected());
        Assert.assertTrue(p.formatSwitchListCheckBox.isSelected());
        Assert.assertFalse(p.editManifestCheckBox.isSelected());
        Assert.assertFalse(p.printLocCommentsCheckBox.isSelected());
        Assert.assertFalse(p.printRouteCommentsCheckBox.isSelected());
        Assert.assertFalse(p.printLoadsEmptiesCheckBox.isSelected());
        Assert.assertFalse(p.printTimetableNameCheckBox.isSelected());
        Assert.assertTrue(p.printValidCheckBox.isSelected());
        Assert.assertFalse(p.sortByTrackCheckBox.isSelected());
        Assert.assertFalse(p.printHeadersCheckBox.isSelected());
        Assert.assertFalse(p.truncateCheckBox.isSelected());
        Assert.assertFalse(p.departureTimeCheckBox.isSelected());
        Assert.assertTrue(p.trackSummaryCheckBox.isSelected());
        Assert.assertTrue(p.routeLocationCheckBox.isSelected());

        // done
        f.dispose();
    }

    // Ensure minimal setup for log4J
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        new Setup();
    }

    public OperationsSetupGuiTest(String s) {
        super(s);
    }

    // Main entry point
    static public void main(String[] args) {
        String[] testCaseName = {"-noloading", OperationsSetupGuiTest.class.getName()};
        junit.textui.TestRunner.main(testCaseName);
    }

    // test suite from all defined tests
    public static Test suite() {
        TestSuite suite = new TestSuite(OperationsSetupGuiTest.class);
        return suite;
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
