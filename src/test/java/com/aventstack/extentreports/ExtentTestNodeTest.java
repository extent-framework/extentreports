package com.aventstack.extentreports;

import java.io.UnsupportedEncodingException;

import org.testng.Assert;

import com.aventstack.extentreports.gherkin.GherkinDialectManager;
import com.aventstack.extentreports.gherkin.entity.Feature;
import com.aventstack.extentreports.gherkin.entity.Scenario;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.model.service.TestService;

public class ExtentTestNodeTest {
    private static final String TEST_NAME = "TEST";

    private ExtentReports extent() {
        try {
            GherkinDialectManager.setLanguage(GherkinDialectManager.getDefaultLanguage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new ExtentReports();
    }

    @org.testng.annotations.Test
    public void createNodeOverloadTypeNameDesc() {
        ExtentTest test = extent().createTest(Feature.class, TEST_NAME, "Description");
        ExtentTest node = test.createNode(Scenario.class, TEST_NAME, "Description");
        Test model = test.getModel();
        Assert.assertTrue(TestService.isBDD(model));
        Assert.assertEquals(model.getName(), TEST_NAME);
        Assert.assertEquals(model.getDescription(), "Description");
        Assert.assertEquals(model.getBddType(), Feature.class);
        Assert.assertFalse(model.isLeaf());

        model = node.getModel();
        Assert.assertTrue(TestService.isBDD(model));
        Assert.assertEquals(model.getName(), TEST_NAME);
        Assert.assertEquals(model.getDescription(), "Description");
        Assert.assertEquals(model.getBddType(), Scenario.class);
        Assert.assertTrue(model.isLeaf());
        Assert.assertEquals(model.getLevel().intValue(), 1);
    }

    @org.testng.annotations.Test
    public void createNodeOverloadTypeName() {
        ExtentTest node = extent().createTest(Feature.class, TEST_NAME)
                .createNode(Scenario.class, TEST_NAME);
        Test model = node.getModel();
        Assert.assertTrue(TestService.isBDD(model));
        Assert.assertEquals(model.getName(), TEST_NAME);
        Assert.assertNull(model.getDescription());
        Assert.assertEquals(model.getBddType(), Scenario.class);
        Assert.assertTrue(model.isLeaf());
    }

    @org.testng.annotations.Test
    public void createNodeOverloadKeywordNameDesc() throws ClassNotFoundException {
        ExtentTest test = extent().createTest(new GherkinKeyword("Feature"), TEST_NAME, "Description");
        ExtentTest node = test.createNode(new GherkinKeyword("Scenario"), TEST_NAME, "Description");
        Test model = test.getModel();
        Assert.assertTrue(TestService.isBDD(model));
        Assert.assertEquals(model.getName(), TEST_NAME);
        Assert.assertEquals(model.getDescription(), "Description");
        Assert.assertEquals(model.getBddType(), Feature.class);
        Assert.assertFalse(model.isLeaf());

        model = node.getModel();
        Assert.assertTrue(TestService.isBDD(model));
        Assert.assertEquals(model.getName(), TEST_NAME);
        Assert.assertEquals(model.getDescription(), "Description");
        Assert.assertEquals(model.getBddType(), Scenario.class);
        Assert.assertTrue(model.isLeaf());
        Assert.assertEquals(model.getLevel().intValue(), 1);
    }

    @org.testng.annotations.Test
    public void createNodeOverloadKeywordName() throws ClassNotFoundException {
        ExtentTest test = extent().createTest(new GherkinKeyword("Feature"), TEST_NAME);
        ExtentTest node = test.createNode(new GherkinKeyword("Scenario"), TEST_NAME);
        Test model = node.getModel();
        Assert.assertTrue(TestService.isBDD(model));
        Assert.assertEquals(model.getName(), TEST_NAME);
        Assert.assertNull(model.getDescription());
        Assert.assertEquals(model.getBddType(), Scenario.class);
        Assert.assertTrue(model.isLeaf());
    }

    @org.testng.annotations.Test
    public void createNodeOverloadNameDesc() throws ClassNotFoundException {
        ExtentTest test = extent().createTest(TEST_NAME, "Description");
        ExtentTest node = test.createNode(TEST_NAME, "Description");
        Test model = test.getModel();
        Assert.assertFalse(TestService.isBDD(model));
        Assert.assertEquals(model.getName(), TEST_NAME);
        Assert.assertEquals(model.getDescription(), "Description");
        Assert.assertFalse(model.isLeaf());

        model = node.getModel();
        Assert.assertFalse(TestService.isBDD(model));
        Assert.assertEquals(model.getName(), TEST_NAME);
        Assert.assertEquals(model.getDescription(), "Description");
        Assert.assertTrue(model.isLeaf());
        Assert.assertEquals(model.getLevel().intValue(), 1);
    }

    @org.testng.annotations.Test
    public void createNodeOverloadName() throws ClassNotFoundException {
        ExtentTest test = extent().createTest(TEST_NAME);
        ExtentTest node = test.createNode(TEST_NAME);
        Test model = node.getModel();
        Assert.assertFalse(TestService.isBDD(model));
        Assert.assertEquals(model.getName(), TEST_NAME);
        Assert.assertNull(model.getDescription());
        Assert.assertTrue(model.isLeaf());
    }
}
