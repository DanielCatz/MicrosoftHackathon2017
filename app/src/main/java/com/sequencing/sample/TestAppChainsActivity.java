package com.sequencing.sample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sequencing.androidoauth.core.OAuth2Parameters;
import com.sequencing.appchains.AndroidAppChainsImpl;
import com.sequencing.appchains.DefaultAppChainsImpl;
import com.sequencing.appchains.DefaultAppChainsImpl.Report;
import com.sequencing.appchains.DefaultAppChainsImpl.Result;
import com.sequencing.appchains.DefaultAppChainsImpl.ResultType;
import com.sequencing.appchains.DefaultAppChainsImpl.TextResultValue;
import com.sequencing.fileselector.FileEntity;
import com.sequencing.fileselector.core.ISQFileCallback;
import com.sequencing.fileselector.core.SQUIFileSelectHandler;
import com.sequencing.sample.models.AccessToken;
import com.sequencing.sample.models.Business;
import com.sequencing.sample.models.Businesses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class TestAppChainsActivity extends AppCompatActivity implements ISQFileCallback, View.OnClickListener {

    private static final String TAG = "TestAppChainsActivity";

    private SQUIFileSelectHandler fileSelectHandler;

    private Button btnSelectFile;
    private Button btnVitaminD;
    private Button btnMelanomaRisk;
    private Button btnBulkChain;
    private TextView tvTitle;
    private TextView tvFileName;
    private TextView tvResult;

    private String selectedFileId;
    private FileEntity entity;

    private AsyncTaskChain9 asyncTaskChain9;
    private AsyncTaskChain88 asyncTaskChain88;
    private AsyncTaskGetYelpToken yelpTask;
    private AsyncTaskBulkChains asyncTaskBulkChains;
    private String mAccessToken;
    private Subscription subscription;
    private String theThing="Nothing yet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_app_chains);

        btnSelectFile = (Button) findViewById(R.id.btnSelectFile);
        btnSelectFile.setOnClickListener(this);

        btnVitaminD = (Button) findViewById(R.id.btnVitaminD);
        btnVitaminD.setOnClickListener(this);

        btnMelanomaRisk = (Button) findViewById(R.id.btnMelanomaRisk);
        btnMelanomaRisk.setOnClickListener(this);

        btnBulkChain = (Button) findViewById(R.id.btnGetVitaminDMelanomaRisk);
        btnBulkChain.setOnClickListener(this);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvFileName = (TextView) findViewById(R.id.tvFileName);
        tvResult = (TextView) findViewById(R.id.tvResult);

        fileSelectHandler = new SQUIFileSelectHandler(this);
    }

    @Override
    public void onFileSelected(FileEntity entity, Activity activity) {
        Log.i(TAG, "File " + entity.getFriendlyDesc1() + " has been selected");
        activity.finish();
        this.entity = entity;
        selectedFileId = entity.getId();

        tvFileName.setText(entity.getFriendlyDesc1() + " - " + entity.getFriendlyDesc2());

        btnVitaminD.setVisibility(View.VISIBLE);
        btnMelanomaRisk.setVisibility(View.VISIBLE);
        btnBulkChain.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tvFileName.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btnSelectFile:
                fileSelectHandler.selectFile(OAuth2Parameters.getInstance().getOauth(), this, selectedFileId);
                tvResult.setVisibility(View.GONE);
            break;

            case R.id.btnVitaminD:
                tvResult.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Computing result...", Toast.LENGTH_LONG).show();

//                asyncTaskChain88 = new AsyncTaskChain88();
//                asyncTaskChain88.execute();

//               getSaltSensitivity();
//                yelpTask = new AsyncTaskGetYelpToken();
//                yelpTask.execute();

                String credentials,cid,secret;
                credentials="client_credentials";
                cid="eHbXciUvKAI3NrMiDB-PbQ";
                secret="nM4ZrDvDXvGYrP41e6NouiuwbHIiIFh5lXGn72mcSOhnAZUp1ZC9v420e8cuPIne";
//          RestClient.getInstance().requestYelpToken(credentials,id,secret);

                if (theThing.equals("Nothing yet")) {
                    getYelpToken(credentials, cid, secret, "apple");
                }else
                    getYelpBusinesses(theThing,"apple");
                break;

            case R.id.btnMelanomaRisk:
                tvResult.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Computing result...", Toast.LENGTH_LONG).show();

                asyncTaskChain9 = new AsyncTaskChain9();
                asyncTaskChain9.execute();
                break;
            case R.id.btnGetVitaminDMelanomaRisk:
                tvResult.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Computing result...", Toast.LENGTH_LONG).show();

                asyncTaskBulkChains = new AsyncTaskBulkChains();
                asyncTaskBulkChains.execute();
                break;
        }
    }

public String getChain(String chainNumber,String chainTitle){
    AndroidAppChainsImpl chains = new AndroidAppChainsImpl(OAuth2Parameters.getInstance().getOauth().getToken().getAccessToken(), "api.sequencing.com");
    Report resultChain;

    try {
        resultChain = chains.getReport("StartApp", chainNumber, entity.getId());
    } catch (Exception e) {
        Toast.makeText(this, "Failure to get "+chainTitle+" risk, please try again", Toast.LENGTH_SHORT).show();
        return null;
    }

    if (resultChain.isSucceeded() == false) {
        Toast.makeText(this, "Failure to get "+chainTitle+" risk, please try again", Toast.LENGTH_SHORT).show();
        return null;
    }

    for (Result r : resultChain.getResults()) {
        ResultType type = r.getValue().getType();

        if (type == ResultType.TEXT) {
            TextResultValue v = (TextResultValue) r.getValue();
            Log.i("it be",String.format(" -> text result type %s = %s", r.getName(), v.getData()));
            if (r.getName().equals("RiskDescription"))
                return v.getData();
        }
    }

    return null;

}

    private String getSaltSensitivity(){
        getChain("chain 83","High Blood Pressure");


        return null;
    }

    private String getHypertension(){
        return null;
    }



    private boolean hasVitD() {
        AndroidAppChainsImpl chains = new AndroidAppChainsImpl(OAuth2Parameters.getInstance().getOauth().getToken().getAccessToken(), "api.sequencing.com");
            Report resultChain88;
            try {
                resultChain88 = chains.getReport("StartApp", "Chain88", entity.getId());
            } catch (Exception e) {
                Toast.makeText(this, "Failure to get availability of vitamin D, please try again", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (resultChain88.isSucceeded() == false) {
                Toast.makeText(this, "Failure to get availability of vitamin D, please try again", Toast.LENGTH_SHORT).show();
                return false;
            }

            boolean result = false;
            for (Result r : resultChain88.getResults()) {
                ResultType type = r.getValue().getType();
                if (type == ResultType.TEXT) {
                    TextResultValue v = (TextResultValue) r.getValue();

                    if (r.getName().equals("result"))
                        result = v.getData().equals("No") ? false : true;
                }
            }

        return result;
    }

    private String getMelanomaRisk() {
        AndroidAppChainsImpl chains = new AndroidAppChainsImpl(OAuth2Parameters.getInstance().getOauth().getToken().getAccessToken(), "api.sequencing.com");
        Report resultChain9;

        try {
            resultChain9 = chains.getReport("StartApp", "Chain95", entity.getId());
        } catch (Exception e) {
            Toast.makeText(this, "Failure to get melanoma risk, please try again", Toast.LENGTH_SHORT).show();
            return null;
        }

        if (resultChain9.isSucceeded() == false) {
            Toast.makeText(this, "Failure to get melanoma risk, please try again", Toast.LENGTH_SHORT).show();
            return null;
        }

        for (Result r : resultChain9.getResults()) {
            ResultType type = r.getValue().getType();

            if (type == ResultType.TEXT) {
                TextResultValue v = (TextResultValue) r.getValue();
                Log.i("it be",String.format(" -> text result type %s = %s", r.getName(), v.getData()));
                if (r.getName().equals("RiskDescription")||
                        r.getName().equals("result"))
                    return v.getData();
            }
        }

        return null;
    }

    private Map<String, String> getBulkChains(){
        Map<String, String> bulkResult = new HashMap<>();
        AndroidAppChainsImpl chains = new AndroidAppChainsImpl(OAuth2Parameters.getInstance().getOauth().getToken().getAccessToken(), "api.sequencing.com");
        Map<String, String> appChainsParams = new HashMap<>();
        appChainsParams.put("Chain9", entity.getId());
        appChainsParams.put("Chain88", entity.getId());
        try {
            Map<String, Report> resultChain = chains.getReportBatch("StartAppBatch", appChainsParams);
            for (String key : resultChain.keySet()) {
                Report report = resultChain.get(key);
                List<Result> results = report.getResults();
                for (DefaultAppChainsImpl.Result result : results) {
                    ResultType type = result.getValue().getType();
                    if (type == ResultType.TEXT) {
                        DefaultAppChainsImpl.TextResultValue textResultValue = (DefaultAppChainsImpl.TextResultValue) result.getValue();
                        if (result.getName().equals("RiskDescription") && key.equals("Chain9")) {
                            String riskDescription = textResultValue.getData();
                            bulkResult.put("riskDescription", riskDescription);
                        }
                        if (result.getName().equals("result") && key.equals("Chain88")) {
                            String hasVitD = textResultValue.getData().equals("No") ? "False" : "True";
                            bulkResult.put("vitaminD", hasVitD);
                        }
                    }
                }
            }
        }catch (Exception e){
//            showError();
        }
        return bulkResult;
    }

    class AsyncTaskChain9 extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String afflictions = "High blood pressure chain83"+
                    " Primary Pulmonary Hypertension chain 95"+
                    "Emphysema chain 71"+
                    "Heart Attack chain 63"+
                    "brain aneurysm chain 58";

            getChain("chain83","Name");
            getChain("chain95","Name");
            getChain("chain71","Name");
            getChain("chain63","Name");

            return getChain("chain58","Name");//
        }

        @Override
        protected void onPostExecute(String result) {
            tvResult.setVisibility(View.VISIBLE);
            tvResult.setText("Melanoma issue level is: " + result);
        }
    }

    class AsyncTaskChain88 extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return hasVitD();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            tvResult.setVisibility(View.VISIBLE);
            if(result)
                tvResult.setText("There is issue with vitamin D");
            else
                tvResult.setText("There is no issue with vitamin D");
        }
    }

    class AsyncTaskGetYelpToken extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            tvResult.setVisibility(View.VISIBLE);
            if(result)
                tvResult.setText("There is issue with vitamin D");
            else
                tvResult.setText("There is no issue with vitamin D");
        }
    }


    class AsyncTaskBulkChains extends AsyncTask<Void, Void, Map<String, String>> {

        @Override
        protected Map<String, String> doInBackground(Void... params) {
            return getBulkChains();
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            tvResult.setVisibility(View.VISIBLE);
            boolean vitD = Boolean.parseBoolean(result.get("vitaminD"));
            String melanomaRisk = result.get("riskDescription");
            if(vitD){
                tvResult.setText("There is issue with vitamin D" + " \nMelanoma issue level is: " + melanomaRisk);
            } else{
                tvResult.setText("There is no issue with vitamin D" + " \nMelanoma issue level is: " + melanomaRisk);
            }

        }
    }


    public void getYelpToken(String credentials,String id, String secret, final String searchTerm) {
    Observable<AccessToken> call = (Observable<AccessToken>)
            RestClient.getInstance().getPreparedObservable(RestClient.getInstance().getYELP_API().getYelpToken(credentials, id, secret), AccessToken.class, false, false);
    subscription = call.subscribe(new Observer<AccessToken>() {
                                      @Override
                                      public void onCompleted() {
                                          //screen.showToast("Done");

                                      }

                                      @Override
                                      public void onError(Throwable e) {
                                          Log.e("THE THING", e.getMessage());
                                      }

                                      @Override
                                      public void onNext(AccessToken token) {
                                        Log.e("THE THING\nOUTPUT", token.getAccessToken());
                                        theThing = token.getAccessToken();
                                        getYelpBusinesses( theThing , searchTerm);
                                      }
                                  }
    );
}
    public void getYelpBusinesses(String token ,String searchTerm) {
       String longitude = "-122.201516";
        String latitude = "47.610150";
        String authorization = "Bearer "+ token;
        Observable<Businesses> call = (Observable<Businesses>)
                RestClient.getInstance().getPreparedObservable(RestClient.getInstance().getYELP_API().getBusinessesWithSearch(searchTerm,latitude,longitude,authorization),Businesses.class, false, false);
        subscription = call.subscribe(new Observer<Businesses>() {
                                          @Override
                                          public void onCompleted() {
                                              //screen.showToast("Done");
                                          }

                                          @Override
                                          public void onError(Throwable e) {
                                              Log.e("FWOCK", e.getMessage());
                                          }

                                          @Override
                                          public void onNext(Businesses businesses) {
                                              for(Business business : businesses.getBusinesses()){
                                              Log.i("BUSINESSES",business.getName());
                                              }
                                          }
                                      }
        );
    }

    private void buildAssociations(Businesses businesses){
        HashMap<String,<ArrayList<String>> 

    }
}
