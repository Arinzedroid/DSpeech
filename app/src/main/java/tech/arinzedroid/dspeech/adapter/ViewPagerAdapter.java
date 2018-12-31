package tech.arinzedroid.dspeech.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import tech.arinzedroid.dspeech.fragment.CreditsFragment;
import tech.arinzedroid.dspeech.fragment.HomeFragment;
import tech.arinzedroid.dspeech.fragment.SettingsFragment;

public class ViewPagerAdapter extends SmartFragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getRegisteredFragment(int position) {
        return super.getRegisteredFragment(position);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position){
            case 0:{
                fragment = new HomeFragment();
                return fragment;
            }case 1:{
                fragment = new SettingsFragment();
                return fragment;
            }case 2:{
                fragment = new CreditsFragment();
                return fragment;
            }default:{
                fragment = new HomeFragment();
                return fragment;
            }
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
