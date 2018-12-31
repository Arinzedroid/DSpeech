package tech.arinzedroid.dspeech.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.flutterwave.raveandroid.RavePayManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tech.arinzedroid.dspeech.R;
import tech.arinzedroid.dspeech.model.RaveModel;
import tech.arinzedroid.dspeech.utils.PrefUtils;
import tech.arinzedroid.dspeech.viewmodels.AppViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreditsFragment extends Fragment {


    @BindView(R.id.main)
    TextView MainCreditTv;
    @BindView(R.id.bonus)
    TextView BonusCreditTv;
    @BindView(R.id.total)
    TextView TotalCreditTv;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.card_info_layout)
    View CardInfoLayout;
    @BindView(R.id.credit_bal_layout)
    View CreditBalLayout;
    @BindView(R.id.buy_credit_button)
    Button BuyButton;
    @BindView(R.id.amount_et)
    EditText AmountET;
    @BindView(R.id.email_et)
    EditText EmailEt;
    @BindView(R.id.lname_et)
    EditText LastNameET;
    @BindView(R.id.fname_et)
    EditText FirstNameEt;

    AppViewModel appViewModel;
    PrefUtils prefUtils;

    public CreditsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_credits, container, false);
        ButterKnife.bind(this,view);
        appViewModel.getMainCredit().observe(this, mainCredit -> {
            MainCreditTv.setText(addSymbol(mainCredit));
            TotalCreditTv.setText(addSymbol(String.valueOf(removeSymbol(mainCredit) +
                    removeSymbol(BonusCreditTv.getText().toString()))));
            if(CardInfoLayout.getVisibility() == View.VISIBLE){
               HideLayout();
            }
        });
        appViewModel.getBonusCredit().observe(this, bonusCredit ->{
            BonusCreditTv.setText(addSymbol(bonusCredit));
            TotalCreditTv.setText(addSymbol(String.valueOf(removeSymbol(bonusCredit) + removeSymbol(MainCreditTv.getText().toString()))));
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        setHasOptionsMenu(true);
        appViewModel = ViewModelProviders.of(getActivity()).get(AppViewModel.class);
        prefUtils = new PrefUtils(getActivity());
        super.onCreate(savedInstanceState);
    }

    private void validateInput(){
        String fname,lname,email,amount;
        fname = FirstNameEt.getText().toString();
        lname = LastNameET.getText().toString();
        email = EmailEt.getText().toString();
        amount = AmountET.getText().toString();

        if(amount.isEmpty()){
            AmountET.setError("Value required");
            return;
        }
        if(email.isEmpty()){
            EmailEt.setError("Value required");
            return;
        }

        if(fname.isEmpty()){
            FirstNameEt.setError("Value required");
            return;
        }
        if(lname.isEmpty()){
            LastNameET.setError("Value required");
            return;
        }

        double amt = Double.valueOf(amount);
        prefUtils.setTxAmount(amt);

        RaveModel raveModel = new RaveModel();
        raveModel.setAmount(amt);
        raveModel.setEmail(email);
        raveModel.setFirstname(fname);
        raveModel.setLastname(lname);
        proceed(raveModel);

        Log.e(CreditsFragment.class.getSimpleName(),raveModel.getTxRef());
    }

    private String addSymbol(String value){
        double newValue = 0.00;
        if(value != null && !value.isEmpty()){
            try {
                newValue = Double.parseDouble(value);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return "₦" + newValue;
    }

    private double removeSymbol(String value){
        double newValue = 0.0;
        if(value != null && !value.isEmpty()){
            if(value.startsWith("₦")){
                value = value.substring(1);
            }
            try{
                newValue = Double.parseDouble(value);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return newValue;
    }

    @OnClick(R.id.buy_credit_button)
    public void BuyCredit(View view){
        CardInfoLayout.setVisibility(View.VISIBLE);
        int y_pos = AmountET.getBottom();
        scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
        Log.e(CreditsFragment.class.getSimpleName(),String.valueOf(y_pos));
        AmountET.requestFocus();
        BuyButton.setEnabled(false);
    }

    private void proceed(RaveModel raveModel){
        new RavePayManager(getActivity()).setAmount(raveModel.getAmount())
                .setCountry(raveModel.getCountry())
                .setCurrency(raveModel.getCurrency())
                .setEmail(raveModel.getEmail())
                .setfName(raveModel.getFirstname())
                .setlName(raveModel.getLastname())
                .setPublicKey("FLWPUBK-0b5983334ffc7bd74f69443fa0501b67-X")
                .setSecretKey("FLWSECK-ce73a9155b0a7c2ba6af3914780d2b54-X")
                .setTxRef(raveModel.getTxRef())
                .allowSaveCardFeature(false)
                .initialize();
    }

    @OnClick(R.id.cancel_button)
    public void CancelButton(View view){
        HideLayout();
    }

    private void HideLayout() {
        scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_UP));
        CardInfoLayout.setVisibility(View.GONE);
        BuyButton.setEnabled(true);
        AmountET.setText("");
        EmailEt.setText("");
        FirstNameEt.setText("");
        LastNameET.setText("");
    }

    @OnClick(R.id.proceed_button)
    public void ProceedButton(View view){
        validateInput();
    }

    private void refreshCreditInfo(){

    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater menuInflater){
        menuInflater.inflate(R.menu.credit_menu,menu);
        super.onCreateOptionsMenu(menu,menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
       int id = menuItem.getItemId();
       if(id == R.id.refresh_button){
           refreshCreditInfo();
           return true;
       }
      return super.onOptionsItemSelected(menuItem);
    }

}
