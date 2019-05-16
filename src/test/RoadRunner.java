/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import Invokers.ApiClient;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static test.Utility.getRoads;

/**
 *
 * @author Kaushik Mahato
 */
@RunWith(Parameterized.class)
public class RoadRunner {

    private RoadInfo road;

    public RoadRunner(RoadInfo road) {
        this.road = road;
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
//    @Test
//    public void TestTest() {
//        roads = getRoads("roads.json");
//        //InvokeRunMethod("payments.coreServices.ProcessPayment");
//        for (RoadInfo road : roads) {
//            TestRoad(road);
//        }
//    }
    @Parameterized.Parameters
    public static Collection roads() {
        return getRoads("roads.json");
    }

//    public static void InvokeRunMethod(String className) {
//        String fqClassName = "samples." + className;
//
//        Class classType = null;
//        Class responseClassType = null;
//        try {
//            classType = Class.forName(fqClassName);
//            responseClassType = Class.forName("Model.PtsV2PaymentsPost201Response");
//
//        } catch (ClassNotFoundException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//
//        Method runMethod = null;
//        Method m = null;
//        String id = null;
//        try {
//            runMethod = classType.getMethod("process", boolean.class);
//            m = responseClassType.getMethod("get" + "Status");
//            //responseClassType.getD
//        } catch (NoSuchMethodException e1) {
//            // TODO Auto-generated catch block
////            Method[] methods = classType.getMethods();
//            e1.printStackTrace();
//        } catch (SecurityException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//
//        try {
//            //return (ANetApiResponse)runMethod.invoke(null, Constants.API_LOGIN_ID, Constants.TRANSACTION_KEY);
//            //responseClassType response;
//            Object response = runMethod.invoke(null, false);
//            id = m.invoke(response).toString();
//            System.out.println("KKM " + id);
////            System.out.println(ApiClient.responseCode);
////            System.out.println(ApiClient.responseBody);
////
////            System.out.println(ApiClient.status);
////            System.out.println(ApiClient.respBody);
//
////            Response<classT> response ;
////            = new Response<responseClassType>();
//        } catch (IllegalAccessException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        Assert.assertEquals(id, "AUTHORIZED");
//    }

    @Test
    public void RoadTest() {
        String reqClassName = "samples." + road.getRequestClassName();
        String respClassName = "Model." + road.getResponseClassName();

        Class requestClassType = null;
        Class responseClassType = null;
        try {
            requestClassType = Class.forName(reqClassName);
            responseClassType = Class.forName(respClassName);

        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {

            //Find & Execute SampleCode
            Method runMethod = requestClassType.getMethod("process", boolean.class);
            Object response = runMethod.invoke(null, false);

            //Get expecutedValues & Assert
            for (String expectedValueField : road.getExpectedValues().keySet()) {

                String[] fields = expectedValueField.split("\\.");
                Object r = response;
                Class rC = responseClassType;
                for (String field : fields) {
                    Map<Object, Class> output = callGetter(field, rC, r);
                    r = output.keySet().toArray()[0];
                    rC = output.get(r);
                }
                Assert.assertEquals(road.getExpectedValues().get(expectedValueField), r.toString());
//                    String getterName = "get" + expectedValueField.substring(0, 1).toUpperCase() + expectedValueField.substring(1);
//                    Method getter = responseClassType.getMethod(getterName);
//                    String fieldValue = getter.invoke(response).toString();
//                    Assert.assertEquals(road.getExpectedValues().get(expectedValueField), fieldValue);
            }

            //Get requiredValues & Assert
            for (String requiredField : road.getRequiredFields()) {
                String[] fields = requiredField.split("\\.");
                Object r = response;
                Class rC = responseClassType;
                for (String field : fields) {
                    Map<Object, Class> output = callGetter(field, rC, r);
                    r = output.keySet().toArray()[0];
                    rC = output.get(r);
                }
                Assert.assertNotNull(r);
//                String getterName = "get" + requiredField.substring(0, 1).toUpperCase() + requiredField.substring(1);
//                Method getter = responseClassType.getMethod(getterName);
//                Assert.assertNotNull(getter.invoke(response));
            }

        } catch (NoSuchMethodException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (SecurityException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //Assert.assertEquals(id, "AUTHORIZED");
    }

    private static Map<Object, Class> callGetter(String getterFieldName, Class classType, Object response) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String getterName = "get" + getterFieldName.substring(0, 1).toUpperCase() + getterFieldName.substring(1);
        Method getter = classType.getMethod(getterName);
        Map output = new HashMap<>();
        output.put(getter.invoke(response), getter.invoke(response).getClass());
        return output;
    }
}
