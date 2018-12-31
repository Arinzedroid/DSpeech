package tech.arinzedroid.dspeech.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.arinzedroid.dspeech.R;
import tech.arinzedroid.dspeech.adapter.TextAdapter;
import tech.arinzedroid.dspeech.interfaces.ItemClickedListener;
import tech.arinzedroid.dspeech.logics.Credits;
import tech.arinzedroid.dspeech.utils.Languages;
import tech.arinzedroid.dspeech.utils.PrefUtils;
import tech.arinzedroid.dspeech.viewmodels.AppViewModel;

public class HomeFragment extends Fragment implements ItemClickedListener{


    @BindView(R.id.text_recycler_rv)
    RecyclerView TextRV;
    @BindView(R.id.record_button)
    Button RecordButton;
    @BindView(R.id.end_call_button)
    Button EndCallButton;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.record_image)
    ImageView recordImage;
    @BindView(R.id.language_spinner)
    Spinner languageSpinner;

    ArrayList<String> texts = new ArrayList<>();
    TextAdapter textAdapter;
    AppViewModel appViewModel; PrefUtils prefUtils;
    boolean done = false;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    private String getLanguageCode(String language){
        switch (language){
            case "English(Nigeria)": return Languages.ENGLISH_NG.languageCode();
            case "English(United Kingdom)": return Languages.ENGLISH_UK.languageCode();
            case "Spanish(Spain)": return Languages.SPANISH.languageCode();
            case "French(France)": return Languages.FRENCH.languageCode();
            case "German(Germany)": return Languages.GERMAN.languageCode();
            case "Italian(Italy)": return Languages.ITALIAN.languageCode();
            default: return Languages.ENGLISH_UK.languageCode();
        }

    }
    public void setTexts(String newText){
        progressBar.setVisibility(View.INVISIBLE);
        if(textAdapter != null){
            textAdapter.addTexts(newText);
        }else{
            texts.add(newText);
            textAdapter = new TextAdapter(texts, this);
            if(TextRV != null){
                TextRV.setAdapter(textAdapter);
            }
        }
    }

    public void clearTranscribedText(){
        if(textAdapter != null)
            textAdapter.clearContents();
    }

    private String startRecord(){
        recordImage.setVisibility(View.VISIBLE);
        mListener.onRecordStart(getLanguageCode(languageSpinner.getSelectedItem().toString()));
        return getActivity().getResources().getString(R.string.end_capture);
    }

    private String endRecord(){
        progressBar.setVisibility(View.VISIBLE);
        recordImage.setVisibility(View.INVISIBLE);
        mListener.onRecordStop(getLanguageCode(languageSpinner.getSelectedItem().toString()));
        return getActivity().getResources().getString(R.string.capture_voice);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        prefUtils = new PrefUtils(getActivity());
        appViewModel = ViewModelProviders.of(getActivity()).get(AppViewModel.class);
        Log.e(HomeFragment.class.getSimpleName(),"onCreate called");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(HomeFragment.class.getSimpleName(),"onCreateView called");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);

        appViewModel.getMainCredit().observe(this, amt -> {
            if(!TextUtils.isEmpty(amt)){
                try{
                    double amt1 = Double.parseDouble(amt);
                    if (amt1 <= 0){
                        RecordButton.setText(getActivity().getResources().getString(R.string.capture_voice));
                    }
                }catch (Exception ex){
                    Log.e(this.getClass().getSimpleName(), "Error >> ", ex);
                }
            }
        });

        appViewModel.getTranscribedList().observe(this, textList -> {
            if(textAdapter == null){
                textAdapter = new TextAdapter(textList, this);
                if(TextRV != null){
                    TextRV.setAdapter(textAdapter); done = true;
                }
            }
        });

        appViewModel.getTranscribedText().observe(this, transcribedText -> {
            if(!TextUtils.isEmpty(transcribedText)){
                if(!done)
                    setTexts(transcribedText);
                done = false;
                Log.e(HomeFragment.class.getSimpleName(),transcribedText);
            }else {
                Log.e(HomeFragment.class.getSimpleName(),"transcribedText is null");
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        RecordButton.setOnClickListener(buttonView -> {
            if(Credits.isEnoughCredit(prefUtils.getMainAmount())){
                RecordButton.setText(RecordButton.getText().toString()
                        .equalsIgnoreCase(getActivity().getResources().getString(R.string.capture_voice))?
                        startRecord(): endRecord());
            }else{
                Toast.makeText(getActivity(), "Insufficient credit", Toast.LENGTH_SHORT).show();
            }

        });

        EndCallButton.setOnClickListener(buttonView1 -> {
            mListener.onCallEnd();
        });

        return view;
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.e(HomeFragment.class.getSimpleName(),"onPause called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.e(HomeFragment.class.getSimpleName(),"onStop called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.e(HomeFragment.class.getSimpleName(),"onResume called");
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.e(HomeFragment.class.getSimpleName(),"onDestroyView called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.e(HomeFragment.class.getSimpleName(),"onDestroy called");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(HomeFragment.class.getSimpleName(),"onAttach called");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        menuInflater.inflate(R.menu.home_menu,menu);
        super.onCreateOptionsMenu(menu,menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == R.id.clear){
            clearTranscribedText();
            appViewModel.clearList();
            return true;
        }else if(menuItem.getItemId() == R.id.sign_out){
            mListener.signOut();
            return true;
        }
         return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onDetach() {
        Log.e(HomeFragment.class.getSimpleName(),"onDetach called");
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefreshButtonClicked(int position) {

    }


    public interface OnFragmentInteractionListener {
        void onRecordStart(String languageCode);
        void onRecordStop(String languageCode);
        void onCallEnd();
        void signOut();
    }
}
