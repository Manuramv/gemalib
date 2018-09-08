package gemalto.com.gemaltodatalib.serviceimpl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gemalto.com.gemaltodatalib.gemdatabase.DbHelper;
import gemalto.com.gemaltodatalib.gemdatabase.Employee;
import gemalto.com.gemaltodatalib.gemdatabase.EmployeeOperations;
import gemalto.com.gemaltodatalib.networking.response.genderquery.Dob;
import gemalto.com.gemaltodatalib.networking.response.genderquery.GetGenderQueryInfoResponse;
import gemalto.com.gemaltodatalib.networking.response.genderquery.UserInfo;
import gemalto.com.gemaltodatalib.networking.response.genderquery.UserName;
import gemalto.com.gemaltodatalib.networking.response.genderquery.UserResult;

/**
 * Created by Manuramv on 9/6/2018.
 */

public class CommonUtils {
    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectivity.getActiveNetworkInfo();

        boolean isConnected = info != null && info.isConnectedOrConnecting();
        return isConnected;

    }


    public static GetGenderQueryInfoResponse generateJsonObj(ArrayList<Employee> list) {
        JSONObject obj = new JSONObject();
        List<UserResult> userList = new ArrayList<>();
        GetGenderQueryInfoResponse getGenderQueryInfoResponseObj = new GetGenderQueryInfoResponse();
        UserInfo userInfo = new UserInfo();
        UserName userName = new UserName();
        UserResult userResult = new UserResult();
        ArrayList<UserResult> userResultsList = new ArrayList<>();
        Dob dob = new Dob();

        userInfo.setSeed(list.get(0).getSeed());
        userName.setFirst(list.get(0).getFirstname());
        userName.setLast(list.get(0).getLastname());
        userResult.setName(userName);
        userResult.setEmail(list.get(0).getEmail().toString());
        userResult.setGender("male");
        dob.setAge(18);
        dob.setDate(list.get(0).getDob().toString());
        userResult.setDob(dob);

        getGenderQueryInfoResponseObj.setInfo(userInfo);
        userResultsList.add(userResult);
        getGenderQueryInfoResponseObj.setResults(userResultsList);
        return getGenderQueryInfoResponseObj;
    }

    public static void insertEmployeeintoDb(GetGenderQueryInfoResponse value, AppCompatActivity mActivityObj) {
         EmployeeOperations employeeOps;
        employeeOps = new EmployeeOperations(mActivityObj);
        employeeOps.open();
        Employee newEmployee = new Employee();
        newEmployee.setSeed((value.getInfo().getSeed().toString()));
        ArrayList<UserResult> valueList = value.getResults();

        newEmployee.setFirstname(valueList.get(0).getName().getFirst());
        newEmployee.setLastname(valueList.get(0).getName().getLast());
        newEmployee.setEmail(valueList.get(0).getEmail());
        newEmployee.setDob(valueList.get(0).getDob().toString());
        // newEmployee.set(valueList.get(0).getDob().toString());
        newEmployee.setGender(valueList.get(0).getGender().toString());
        employeeOps.addEmployee(newEmployee);
    }


    public static GetGenderQueryInfoResponse returnSingleItemFromDb(AppCompatActivity mActivityObj, String columntoQuery, String queryString) {
        EmployeeOperations employeeOps;
        employeeOps = new EmployeeOperations(mActivityObj);
        employeeOps.open();
        /*if(employeeOps.getAllEmployees()!=null && employeeOps.getAllEmployees().size()>0){
            Log.d("ttt","Size of database:::"+employeeOps.getAllEmployees().size());
            ArrayList<Employee> emplSingle =  employeeOps.getAllEmployees();
            Log.d("ttt","Size of database:::"+emplSingle.get(0).getFirstname());
            Log.d("ttt","Size of database:::"+emplSingle.get(1).getFirstname());

            return CommonUtils.generateJsonObj(emplSingle);

        }*/

        if(columntoQuery.equalsIgnoreCase(DbHelper.COLUMN_GENDER)){
                return CommonUtils.generateJsonObj(employeeOps.getEmployeeGender(queryString));
        } else if(columntoQuery.equalsIgnoreCase(DbHelper.COLUMN_SEED)){
            return CommonUtils.generateJsonObj(employeeOps.getEmployeeSeed(queryString));
        }


        else {
            Log.d("ttt","empty");
            return null;
        }

    }

    private static GetGenderQueryInfoResponse generateJsonObj(Employee list) {
        JSONObject obj = new JSONObject();
        List<UserResult> userList = new ArrayList<>();
        GetGenderQueryInfoResponse getGenderQueryInfoResponseObj = new GetGenderQueryInfoResponse();
        UserInfo userInfo = new UserInfo();
        UserName userName = new UserName();
        UserResult userResult = new UserResult();
        ArrayList<UserResult> userResultsList = new ArrayList<>();
        Dob dob = new Dob();

        if(list.getSeed()!=null){
            userInfo.setSeed(list.getSeed());
        } else {
            userInfo.setSeed("000fesk");
        }

        userName.setFirst(list.getFirstname());
        userName.setLast(list.getLastname());
        userResult.setName(userName);
        userResult.setEmail(list.getEmail().toString());
        userResult.setGender("male");
        dob.setAge(18);
        dob.setDate(list.getDob().toString());
        userResult.setDob(dob);

        getGenderQueryInfoResponseObj.setInfo(userInfo);
        userResultsList.add(userResult);
        getGenderQueryInfoResponseObj.setResults(userResultsList);
        return getGenderQueryInfoResponseObj;

    }
}
