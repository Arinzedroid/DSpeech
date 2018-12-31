package tech.arinzedroid.dspeech.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.arinzedroid.dspeech.R;
import tech.arinzedroid.dspeech.utils.PrefUtils;
import tech.arinzedroid.dspeech.utils.Tlanguages;

import static tech.arinzedroid.dspeech.utils.Tlanguages.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    @BindView(R.id.language_rgp)
    RadioGroup LanguageRadioGroup;

    PrefUtils prefUtils;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        prefUtils = new PrefUtils(getActivity());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this,view);

        getLanguage(view);

        LanguageRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            Log.e(SettingsFragment.class.getSimpleName(), "position checked " + i);
            switch (radioGroup.getCheckedRadioButtonId()){
                case R.id.eng_rtn:{
                    prefUtils.setLanguage(ENGLISH.languageCode());
                    break;
                }case R.id.spa_rtn:{
                    prefUtils.setLanguage(SPANISH.languageCode());
                    break;
                }case R.id.fr_rtn:{
                    prefUtils.setLanguage(FRENCH.languageCode());
                    break;
                }case R.id.ge_rtn:{
                    prefUtils.setLanguage(GERMAN.languageCode());
                    break;
                }case R.id.it_rtn:{
                    prefUtils.setLanguage(ITALIAN.languageCode());
                    break;
                }case R.id.ig_rtn:{
                    prefUtils.setLanguage(IGBO.languageCode());
                    break;
                }case R.id.yo_rtn:{
                    prefUtils.setLanguage(YORUBA.languageCode());
                    break;
                }case R.id.ha_rtn:{
                    prefUtils.setLanguage(HAUSA.languageCode());
                    break;
                }
            }
        });

        return view;
    }

    private void getLanguage(View view){
        RadioButton button;
        switch (prefUtils.getLanguage().toLowerCase()){
            case "en":
                button = view.findViewById(R.id.eng_rtn);
                button.setChecked(true);
                break;
            case "de":
                button = view.findViewById(R.id.ge_rtn);
                button.setChecked(true);
                break;
            case "es":
                button = view.findViewById(R.id.spa_rtn);
                button.setChecked(true);
                break;
            case "fr":
                button = view.findViewById(R.id.fr_rtn);
                button.setChecked(true);
                break;
            case"it":
                button = view.findViewById(R.id.it_rtn);
                button.setChecked(true);
                break;
            case"ha":
                button = view.findViewById(R.id.ha_rtn);
                button.setChecked(true);
                break;
            case"yo":
                button = view.findViewById(R.id.yo_rtn);
                button.setChecked(true);
                break;
            case"ig":
                button = view.findViewById(R.id.ig_rtn);
                button.setChecked(true);
                break;
        }
    }



}
