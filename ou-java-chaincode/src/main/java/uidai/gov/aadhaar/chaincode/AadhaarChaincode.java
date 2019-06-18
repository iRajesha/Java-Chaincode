package uidai.gov.aadhaar.chaincode;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

import com.google.protobuf.ByteString;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;

import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.logging.Level;
import java.util.logging.Logger;

import uidai.gov.aadhaar.model.Aadhaar;

public class AadhaarChaincode extends ChaincodeBase {

    private static Log _logger = LogFactory.getLog(AadhaarChaincode.class);

    @Override
    public Response init(ChaincodeStub stub) {
        try {
            _logger.info("Init java  chaincode");
            String func = stub.getFunction();
            if (!func.equals("init")) {
                return newErrorResponse("function other than init is not supported");
            }
            List<String> args = stub.getParameters();
            if (args.size() != 3) {
                newErrorResponse("Incorrect number of arguments. Expecting 3");
            }
            // Initialize the chaincode
            String aadhaarNumber = args.get(0);
            String firstName = args.get(1);
            String lastName = args.get(2);
            //JSONObject aadhaarJsonObj = constructAadhaarJsonObject(aadhaarNumber,firstName,lastName,"");

            Aadhaar adam = new Aadhaar(aadhaarNumber, firstName, lastName);
            //byte[] data = SerializationUtils.serialize(president);

            System.out.print(adam);
            _logger.info(String.format("aadhaarNumber %s, firstName = %s; lasname = %s", aadhaarNumber, firstName, lastName));

            stub.putState(aadhaarNumber, new ObjectMapper().writeValueAsBytes(adam));

            return newSuccessResponse();
        } catch (Throwable e) {
            return newErrorResponse(e);
        }
    }
//    private JSONObject constructAadhaarJsonObject(String aadhaarNumber, String firstName, String lastName, String registeredBy) throws Exception{
//      JSONObject obj = new JSONObject();
//      obj.put("aadhaarNumber", aadhaarNumber);
//      obj.put("firstName", firstName);
//      obj.put("lastName", lastName);
//      obj.put("registeredBy", registeredBy);
//      return obj;
//    }

    @Override
    public Response invoke(ChaincodeStub stub) {
        try {
            _logger.info("Invoke java simple chaincode");
            String func = stub.getFunction();
            List<String> params = stub.getParameters();
            if (func.equals("registerAadhaar")) {
                return registerAadhaar(stub, params);
            }
            if (func.equals("deleteAadhaar")) {
                return deleteAadhaar(stub, params);
            }
            if (func.equals("queryAadhaar")) {
                return queryAadhaar(stub, params);
            }
            return newErrorResponse("Invalid invoke function name. Expecting one of: [\"registerAadhaar\", \"deleteAadhaar\", \"queryAadhaar\"]");
        } catch (Throwable e) {
            return newErrorResponse(e);
        }
    }

    private boolean isStringEmpty(String str) {
        if (str.trim().length() <= 0 || str == null) {
            return true;
        }
        return false;
    }

    private Response registerAadhaar(ChaincodeStub stub, List<String> args) {
        if (args.size() < 4) {
            return newErrorResponse("Incorrect number of arguments. Expecting 4");
        }
        String aadhaarNumber = args.get(0);
        String firstName = args.get(1);
        String lastName = args.get(2);
        String registerdBy = args.get(3);
        if(args.size()>3){
            try {
                String jsonString = args.get(4);
                System.out.println("uidai.gov.aadhaar.chaincode.AadhaarChaincode.registerAadhaar()- json string passed "+ jsonString);
                Aadhaar aadhaarFromJson = new ObjectMapper().readValue(jsonString, Aadhaar.class);
                System.out.println("uidai.gov.aadhaar.chaincode.AadhaarChaincode.registerAadhaar() from object -" + aadhaarFromJson.getAadhaarNumber() + aadhaarFromJson.getRegisteredBy());
            } catch (IOException ex) {
                Logger.getLogger(AadhaarChaincode.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
            
            String rigisterer = stub.getStringState(registerdBy);
            System.out.println("uidai.gov.aadhaar.chaincode.AadhaarChaincode.registerAadhaar() - " + rigisterer );
            _logger.info(String.format("Registerer is %s", rigisterer));
            // Aadhaar rigisterer = (Aadhaar)SerializationUtils.deserialize(rigistererByte);
            if (isStringEmpty(rigisterer)) {
                return newErrorResponse(String.format("rigisterer %s not found", registerdBy));
            }

            // JSONObject aadhaarJsonObj;
            // aadhaarJsonObj = constructAadhaarJsonObject(aadhaarNumber,firstName,lastName,registerdBy);
            Aadhaar newAadharEntry = new Aadhaar(aadhaarNumber, firstName, lastName,registerdBy);
            //byte[] data = SerializationUtils.serialize(president);

            System.out.println(newAadharEntry);
            _logger.info(String.format("aadhaarNumber %s, firstName = %s; lasname = %s", aadhaarNumber, firstName, lastName));

            stub.putState(aadhaarNumber, new ObjectMapper().writeValueAsBytes(newAadharEntry));

            _logger.info("Aadhaar Registration complete");

        } catch (Exception ex) {
            Logger.getLogger(AadhaarChaincode.class.getName()).log(Level.SEVERE, null, ex);
            return newErrorResponse("Transaction failed : Register aadhaar");
        }
        return newSuccessResponse("Aadhaar registration successful", ByteString.copyFrom(aadhaarNumber, UTF_8).toByteArray());

    }

    // Deletes an entity from state
    private Response deleteAadhaar(ChaincodeStub stub, List<String> args) {
        if (args.size() != 1) {
            return newErrorResponse("Incorrect number of arguments. Expecting 1");
        }
        String key = args.get(0);
        // Delete the key from the state in ledger
        stub.delState(key);
        return newSuccessResponse(String.format("Aadhaar deleted successfully %s", key));
    }

    // query callback representing the query of a chaincode
    private Response queryAadhaar(ChaincodeStub stub, List<String> args) {
        if (args.size() != 1) {
            return newErrorResponse("Incorrect number of arguments. Expecting aadhaar number to query");
        }
        String key = args.get(0);
        //byte[] stateBytes
        byte[] aadhaar = stub.getState(key);

        if (isStringEmpty(new String(aadhaar))) {
            return newErrorResponse(String.format("Error: state for %s is null", key));
        }
        //JSONObject aadhaar =null;
//        try {
//          //JSONObject  aadhaar=new JSONObject(new String(aadhaarBytes));
//          _logger.info(String.format("Query Response:\nFirstName: %s, LastName: %s\n", aadhaar.getString("firstName"), aadhaar.getString("lastName")));
//        } catch (JSONException ex) {
//            Logger.getLogger(AadhaarChaincode.class.getName()).log(Level.SEVERE, null, ex);
//        }    
        return newSuccessResponse(key, aadhaar);

    }

    public static void main(String[] args) {

        new AadhaarChaincode().start(args);
    }

}
